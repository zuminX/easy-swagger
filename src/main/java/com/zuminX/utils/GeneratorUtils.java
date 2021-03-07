package com.zuminX.utils;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.util.PsiTreeUtil;
import com.zuminX.names.ControllerClassName;
import com.zuminX.names.MappingClassName;
import com.zuminX.names.RequestClassName;
import com.zuminX.names.SwaggerClassName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

public class GeneratorUtils {

  public static final String DOUBLE_QUOTES = "\"\"";
  private static final String MAPPING_VALUE = "value";
  private static final String MAPPING_METHOD = "method";
  private final Project project;
  private final PsiFile psiFile;
  private final PsiClass psiClass;
  private final PsiElementFactory elementFactory;

  public GeneratorUtils(Project project, PsiFile psiFile) {
    this.project = project;
    this.psiFile = psiFile;
    this.psiClass = PsiTreeUtil.findChildOfAnyType(psiFile, PsiClass.class);
    this.elementFactory = JavaPsiFacade.getElementFactory(project);
  }

  public void generateDefault() {
    generate(() -> {
      this.generateClassAnnotation(psiClass);
      if (this.isController(psiClass)) {
        // 类方法列表
        Arrays.stream(psiClass.getMethods()).forEach(this::generateMethodAnnotation);
      } else {
        // 类属性列表
        Arrays.stream(psiClass.getAllFields()).forEach(this::generateFieldAnnotation);
      }
    });
  }

  public void generateSelection(String selectionText) {
    generate(() -> {
      if (Objects.equals(selectionText, psiClass.getName())) {
        this.generateClassAnnotation(psiClass);
      }
      PsiMethod method = findMethodByName(psiClass, selectionText);
      if (method != null) {
        this.generateMethodAnnotation(method);
        return;
      }
      PsiField field = findFieldByNameIdentifier(psiClass, selectionText);
      if (field != null) {
        this.generateFieldAnnotation(field);
      }
    });
  }

  private void generate(Runnable runnable) {
    WriteCommandAction.runWriteCommandAction(project, runnable);
  }

  private PsiMethod findMethodByName(PsiClass psiClass, String name) {
    PsiMethod[] methods = psiClass.getMethods();
    return Arrays.stream(methods)
        .filter(psiMethod -> Objects.equals(name, psiMethod.getName()))
        .findFirst()
        .orElse(null);
  }

  private PsiField findFieldByNameIdentifier(PsiClass psiClass, String nameIdentifier) {
    PsiField[] fields = psiClass.getAllFields();
    return Arrays.stream(fields)
        .filter(psiField -> Objects.equals(nameIdentifier, psiField.getNameIdentifier().getText()))
        .findFirst()
        .orElse(null);
  }

  /**
   * 写入到文件
   *
   * @param name                 注解名
   * @param qualifiedName        注解全包名
   * @param annotationText       生成注解文本
   * @param psiModifierListOwner 当前写入对象
   */
  private void doWrite(String name, String qualifiedName, String annotationText, PsiModifierListOwner psiModifierListOwner) {
    PsiAnnotation psiAnnotationDeclare = elementFactory.createAnnotationFromText(annotationText, psiModifierListOwner);
    final PsiNameValuePair[] attributes = psiAnnotationDeclare.getParameterList().getAttributes();
    PsiAnnotation existAnnotation = psiModifierListOwner.getModifierList().findAnnotation(qualifiedName);
    if (existAnnotation != null) {
      existAnnotation.delete();
    }
    addImport(elementFactory, psiFile, name);
    PsiAnnotation psiAnnotation = psiModifierListOwner.getModifierList().addAnnotation(name);
    Arrays.stream(attributes).forEach(pair -> psiAnnotation.setDeclaredAttributeValue(pair.getName(), pair.getValue()));
  }

  /**
   * 判断给定类是否为controller
   *
   * @param psiClass 类元素
   * @return boolean
   */
  private boolean isController(PsiClass psiClass) {
    PsiAnnotation[] psiAnnotations = psiClass.getModifierList().getAnnotations();
    List<ControllerClassName> controller = ControllerClassName.getAll();
    return Arrays.stream(psiAnnotations)
        .anyMatch(psiAnnotation -> controller.stream()
            .anyMatch(className -> className.getQualifiedName().equals(psiAnnotation.getQualifiedName())));
  }

  /**
   * 获取RequestMapping注解属性
   *
   * @param psiAnnotations 注解元素数组
   * @param attributeName  属性名
   * @return String 属性值
   */
  private String getMappingAttribute(PsiAnnotation[] psiAnnotations, String attributeName) {
    for (PsiAnnotation psiAnnotation : psiAnnotations) {
      String qualifiedName = psiAnnotation.getQualifiedName();
      if (MappingClassName.REQUEST_MAPPING.equals(qualifiedName)) {
        String attribute = getAttribute(psiAnnotation, attributeName);
        return Objects.equals(DOUBLE_QUOTES, attribute) ? "" : attribute;
      }
      MappingClassName mapping = MappingClassName.findByQualifiedName(qualifiedName);
      if (mapping != null) {
        return mapping.getType();
      }
    }
    return "";
  }

  /**
   * 获取注解属性
   *
   * @param psiAnnotation 注解全路径
   * @param attributeName 注解属性名
   * @return 属性值
   */
  private String getAttribute(PsiAnnotation psiAnnotation, String attributeName) {
    if (Objects.isNull(psiAnnotation)) {
      return DOUBLE_QUOTES;
    }
    PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findDeclaredAttributeValue(attributeName);
    return psiAnnotationMemberValue == null ? DOUBLE_QUOTES : psiAnnotationMemberValue.getText();
  }

  /**
   * 生成类注解
   *
   * @param psiClass 类元素
   */
  private void generateClassAnnotation(PsiClass psiClass) {
    boolean isController = this.isController(psiClass);
    PsiComment classComment = null;
    for (PsiElement tmpEle : psiClass.getChildren()) {
      if (tmpEle instanceof PsiComment) {
        classComment = (PsiComment) tmpEle;
        // 注释的内容
        String tmpText = classComment.getText();
        String commentDesc = CommentUtils.getCommentDesc(tmpText);
        String annotationFromText;
        SwaggerClassName className;
        if (isController) {
          className = SwaggerClassName.API;
          String fieldValue = this.getMappingAttribute(psiClass.getModifierList().getAnnotations(), MAPPING_VALUE);
          annotationFromText = String.format("@%s(value = %s, tags = {\"%s\"})", className.getSimpleName(), fieldValue, commentDesc);
        } else {
          className = SwaggerClassName.API_MODEL;
          annotationFromText = String.format("@%s(description = \"%s\")", className.getSimpleName(), commentDesc);
        }
        this.doWrite(className.getSimpleName(), className.getQualifiedName(), annotationFromText, psiClass);
      }
    }
    if (classComment == null) {
      String annotationFromText;
      SwaggerClassName className;
      if (isController) {
        className = SwaggerClassName.API;
        String fieldValue = this.getMappingAttribute(psiClass.getModifierList().getAnnotations(), MAPPING_VALUE);
        annotationFromText = String.format("@%s(value = %s)", className.getSimpleName(), fieldValue);
      } else {
        className = SwaggerClassName.API_MODEL;
        annotationFromText = String.format("@%s", className.getSimpleName());
      }
      this.doWrite(className.getSimpleName(), className.getQualifiedName(), annotationFromText, psiClass);
    }
  }

  /**
   * 生成方法注解
   *
   * @param psiMethod 类方法元素
   */
  private void generateMethodAnnotation(PsiMethod psiMethod) {
    PsiAnnotation[] psiAnnotations = psiMethod.getModifierList().getAnnotations();
    String methodValue = this.getMappingAttribute(psiAnnotations, MAPPING_METHOD);
    if (StringUtils.isNotEmpty(methodValue)) {
      methodValue = methodValue.substring(methodValue.indexOf(".") + 1);
    }
    SwaggerClassName operation = SwaggerClassName.API_OPERATION;
    // 如果存在注解，获取注解原本的value和notes内容
    PsiAnnotation apiOperationExist = psiMethod.getModifierList().findAnnotation(operation.getQualifiedName());
    String apiOperationAttrValue = this.getAttribute(apiOperationExist, "value");
    String apiOperationAttrNotes = this.getAttribute(apiOperationExist, "notes");
    String apiOperationAnnotationText = String.format("@ApiOperation(value = %s, notes = %s,httpMethod = \"%s\")", apiOperationAttrValue,
        apiOperationAttrNotes, methodValue);
    String apiImplicitParamsAnnotationText = null;
    PsiParameter[] psiParameters = psiMethod.getParameterList().getParameters();
    List<String> apiImplicitParamList = new ArrayList<>(psiParameters.length);
    for (PsiParameter psiParameter : psiParameters) {
      PsiType psiType = psiParameter.getType();
      String dataType = CommentUtils.getDataType(psiType.getCanonicalText(), psiType);
      if (StringUtils.isEmpty(dataType)) {
        continue;
      }
      String paramType = "query";
      if (Objects.equals(dataType, "file")) {
        paramType = "form";
      }

      for (PsiAnnotation psiAnnotation : psiParameter.getModifierList().getAnnotations()) {
        if (StringUtils.isEmpty(psiAnnotation.getQualifiedName())) {
          break;
        }
        String qualifiedName = psiAnnotation.getQualifiedName();
        RequestClassName className = RequestClassName.findByQualifiedName(qualifiedName);
        if (className != null) {
          paramType = className.getType();
        }
      }
      String apiImplicitParamText =
          String.format("@ApiImplicitParam(paramType = \"%s\", dataType = \"%s\", name = \"%s\", value = \"\")", paramType, dataType,
              psiParameter.getNameIdentifier().getText());
      apiImplicitParamList.add(apiImplicitParamText);
    }
    if (apiImplicitParamList.size() != 0) {
      apiImplicitParamsAnnotationText = apiImplicitParamList.stream().collect(Collectors.joining(",\n", "@ApiImplicitParams({\n", "\n})"));
    }

    this.doWrite(operation.getSimpleName(), operation.getQualifiedName(), apiOperationAnnotationText, psiMethod);
    if (StrUtil.isNotEmpty(apiImplicitParamsAnnotationText)) {
      SwaggerClassName params = SwaggerClassName.API_IMPLICIT_PARAMS;
      this.doWrite(params.getSimpleName(), params.getQualifiedName(), apiImplicitParamsAnnotationText, psiMethod);
    }
    addImport(elementFactory, psiFile, SwaggerClassName.API_IMPLICIT_PARAM.getSimpleName());
  }

  /**
   * 生成属性注解
   *
   * @param psiField 类属性元素
   */
  private void generateFieldAnnotation(PsiField psiField) {
    List<PsiComment> commentList = getCommentByField(psiField);
    SwaggerClassName modelProperty = SwaggerClassName.API_MODEL_PROPERTY;
    if (commentList.isEmpty()) {
      this.doWrite(modelProperty.getSimpleName(), modelProperty.getQualifiedName(), "@ApiModelProperty(\"\")", psiField);
      return;
    }
    commentList.stream()
        .map(PsiElement::getText)
        .map(CommentUtils::getCommentDesc)
        .map(commentDesc -> String.format("@ApiModelProperty(value=\"%s\")", commentDesc))
        .forEach(apiModelPropertyText -> this.doWrite(modelProperty.getSimpleName(), modelProperty.getQualifiedName(), apiModelPropertyText,
            psiField));
  }

  /**
   * 从类字段元素中获取注释
   *
   * @return 注释列表
   */
  private List<PsiComment> getCommentByField(PsiField psiField) {
    return Arrays.stream(psiField.getChildren())
        .filter(child -> (child instanceof PsiComment))
        .map(child -> (PsiComment) child)
        .collect(Collectors.toList());
  }

  /**
   * 导入类依赖
   *
   * @param elementFactory 元素Factory
   * @param file           当前文件对象
   * @param className      类名
   */
  private void addImport(PsiElementFactory elementFactory, PsiFile file, String className) {
    if (!(file instanceof PsiJavaFile)) {
      return;
    }
    final PsiJavaFile javaFile = (PsiJavaFile) file;
    // 获取所有导入的包
    final PsiImportList importList = javaFile.getImportList();
    if (importList == null) {
      return;
    }
    PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(className, GlobalSearchScope.allScope(project));
    // 待导入类有多个同名类或没有时 让用户自行处理
    if (psiClasses.length != 1) {
      return;
    }
    PsiClass waiteImportClass = psiClasses[0];
    // 已经导入
    if (Arrays.stream(importList.getAllImportStatements())
        .map(is -> is.getImportReference().getQualifiedName())
        .anyMatch(impQualifiedName -> waiteImportClass.getQualifiedName().equals(impQualifiedName))) {
      return;
    }
    importList.add(elementFactory.createImportStatement(waiteImportClass));
  }
}
