package com.zuminX.utils;

import cn.hutool.core.util.ObjectUtil;
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
import com.intellij.ui.render.LabelBasedRenderer;
import com.jgoodies.common.base.Objects;
import com.zuminX.names.ControllerAnnotation;
import com.zuminX.names.MappingAnnotation;
import com.zuminX.names.RequestAnnotation;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.swagger.ApiAnnotation;
import com.zuminX.swagger.ApiImplicitParamAnnotation;
import com.zuminX.swagger.ApiImplicitParamsAnnotation;
import com.zuminX.swagger.ApiModelAnnotation;
import com.zuminX.swagger.ApiModelPropertyAnnotation;
import com.zuminX.swagger.ApiOperationAnnotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

public class GeneratorUtils {

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

  /**
   * 根据当前类生成Swagger注解
   */
  public void generate() {
    generate(this::generateDefault);
  }

  /**
   * 根据所选择的内容生成对应的Swagger注解
   *
   * @param selectionText 选择的文本
   */
  public void generate(String selectionText) {
    generate(() -> generateSelection(selectionText));
  }

  /**
   * 根据当前类生成Swagger注解
   */
  private void generateDefault() {
    generateClassAnnotation(psiClass);
    if (isController(psiClass)) {
      Arrays.stream(psiClass.getMethods()).forEach(this::generateMethodAnnotation);
    } else {
      Arrays.stream(psiClass.getAllFields()).forEach(this::generateFieldAnnotation);
    }
  }

  /**
   * 根据所选择的内容生成对应的Swagger注解
   */
  public void generateSelection(String selectionText) {
    if (ObjectUtil.equals(selectionText, psiClass.getName())) {
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
  }

  /**
   * 生成Swagger注解
   *
   * @param runnable 任务
   */
  private void generate(Runnable runnable) {
    if (unableToGenerate()) {
      return;
    }
    WriteCommandAction.runWriteCommandAction(project, runnable);
  }

  /**
   * 判断Psi类是否无法生成Swagger注解
   * <p/>
   * 当类无修饰符列表时，则无法生成Swagger注解
   *
   * @return 若无法生成，则返回true；否则返回false
   */
  private boolean unableToGenerate() {
    return psiClass.getModifierList() == null;
  }

  /**
   * 从Psi类中获取指定名称的Psi方法
   *
   * @param psiClass Psi类
   * @param name     方法名
   * @return Psi方法
   */
  private PsiMethod findMethodByName(PsiClass psiClass, String name) {
    PsiMethod[] methods = psiClass.getMethods();
    return Arrays.stream(methods)
        .filter(psiMethod -> com.jgoodies.common.base.Objects.equals(name, psiMethod.getName()))
        .findFirst()
        .orElse(null);
  }

  /**
   * 从Psi类中获取指定名称标识符的Psi字段
   *
   * @param psiClass       Psi类
   * @param nameIdentifier 名称标识符
   * @return Psi字段
   */
  private PsiField findFieldByNameIdentifier(PsiClass psiClass, String nameIdentifier) {
    PsiField[] fields = psiClass.getAllFields();
    return Arrays.stream(fields)
        .filter(psiField -> com.jgoodies.common.base.Objects.equals(nameIdentifier, psiField.getNameIdentifier().getText()))
        .findFirst()
        .orElse(null);
  }

  /**
   * 写入Swagger注解到文件
   *
   * @param className            注解类名
   * @param annotationText       生成注解文本
   * @param psiModifierListOwner 当前写入对象
   */
  private void doWrite(SwaggerAnnotation className, String annotationText, PsiModifierListOwner psiModifierListOwner) {
    removeAnnotation(className.getQualifiedName(), psiModifierListOwner);
    addImport(elementFactory, psiFile, className.getSimpleName());
    addAnnotation(className.getSimpleName(), annotationText, psiModifierListOwner);
  }

  /**
   * 删除指定注解
   *
   * @param qualifiedName        注解的全限定类名
   * @param psiModifierListOwner 当前写入对象
   */
  private void removeAnnotation(String qualifiedName, PsiModifierListOwner psiModifierListOwner) {
    PsiAnnotation annotation = psiModifierListOwner.getModifierList().findAnnotation(qualifiedName);
    if (annotation != null) {
      annotation.delete();
    }
  }

  /**
   * 添加注解
   *
   * @param simpleName           注解的简单类名
   * @param annotationText       注解内容
   * @param psiModifierListOwner 当前写入对象
   */
  private void addAnnotation(String simpleName, String annotationText, PsiModifierListOwner psiModifierListOwner) {
    PsiAnnotation psiAnnotation = psiModifierListOwner.getModifierList().addAnnotation(simpleName);
    PsiAnnotation psiAnnotationDeclare = elementFactory.createAnnotationFromText(annotationText, psiModifierListOwner);
    PsiNameValuePair[] attributes = psiAnnotationDeclare.getParameterList().getAttributes();
    Arrays.stream(attributes).forEach(pair -> psiAnnotation.setDeclaredAttributeValue(pair.getName(), pair.getValue()));
  }

  /**
   * 判断给定类是否为controller
   *
   * @param psiClass Psi类
   * @return 若是controller则返回true，否则返回false
   */
  private boolean isController(PsiClass psiClass) {
    PsiAnnotation[] psiAnnotations = psiClass.getModifierList().getAnnotations();
    List<ControllerAnnotation> controller = ControllerAnnotation.getAll();
    return Arrays.stream(psiAnnotations)
        .anyMatch(psiAnnotation -> controller.stream()
            .anyMatch(className -> className.equals(psiAnnotation.getQualifiedName())));
  }

  /**
   * 生成类注解
   *
   * @param psiClass Psi类
   */
  private void generateClassAnnotation(PsiClass psiClass) {
    boolean isController = isController(psiClass);
    List<PsiComment> commentList = getCommentByClass(psiClass);
    if (commentList.isEmpty()) {
      String annotationFromText;
      SwaggerAnnotation annotation;
      if (isController) {
        annotation = ApiAnnotation.API;
        String fieldValue = getMappingAttribute(psiClass.getModifierList().getAnnotations(), MAPPING_VALUE);
        annotationFromText = ApiAnnotation.builder().value(fieldValue).build().toStr();
      } else {
        annotation = ApiModelAnnotation.API_MODEL;
        annotationFromText = ApiModelAnnotation.builder().build().toStr();
      }
      this.doWrite(annotation, annotationFromText, psiClass);
      return;
    }
    for (PsiComment comment : commentList) {
      String tmpText = comment.getText();
      String commentDesc = CoreUtils.getCommentDesc(tmpText);
      String annotationFromText;
      SwaggerAnnotation className;
      if (isController) {
        className = ApiAnnotation.API;
        String fieldValue = getMappingAttribute(psiClass.getModifierList().getAnnotations(), MAPPING_VALUE);
        annotationFromText = ApiAnnotation.builder().value(fieldValue).tags(Collections.singletonList(commentDesc)).build().toStr();
      } else {
        className = ApiModelAnnotation.API_MODEL;
        annotationFromText = ApiModelAnnotation.builder().description(commentDesc).build().toStr();
      }
      this.doWrite(className, annotationFromText, psiClass);
    }
  }

  /**
   * 生成方法注解
   *
   * @param psiMethod Psi方法
   */
  private void generateMethodAnnotation(PsiMethod psiMethod) {
    PsiAnnotation[] psiAnnotations = psiMethod.getModifierList().getAnnotations();
    String methodValue = this.getMappingAttribute(psiAnnotations, MAPPING_METHOD);
    if (StringUtils.isNotEmpty(methodValue)) {
      methodValue = methodValue.substring(methodValue.indexOf(".") + 1);
    }
    // 如果存在注解，获取注解原本的value和notes内容
    PsiAnnotation apiOperationExist = psiMethod.getModifierList().findAnnotation(ApiOperationAnnotation.API_OPERATION.getQualifiedName());
    String apiOperationAnnotationText = ApiOperationAnnotation.builder()
        .value(getAttribute(apiOperationExist, "value"))
        .notes(getAttribute(apiOperationExist, "notes"))
        .httpMethod(methodValue).build().toStr();
    String apiImplicitParamsAnnotationText = null;
    PsiParameter[] psiParameters = psiMethod.getParameterList().getParameters();
    List<String> apiImplicitParamList = new ArrayList<>(psiParameters.length);
    for (PsiParameter psiParameter : psiParameters) {
      PsiType psiType = psiParameter.getType();
      String dataType = CoreUtils.getDataType(psiType.getCanonicalText(), psiType);
      if (StringUtils.isEmpty(dataType)) {
        continue;
      }
      String paramType = "query";
      if (Objects.equals(dataType, "file")) {
        paramType = "form";
      }

      for (PsiAnnotation psiAnnotation : psiParameter.getModifierList().getAnnotations()) {
        if (StrUtil.isEmpty(psiAnnotation.getQualifiedName())) {
          break;
        }
        String qualifiedName = psiAnnotation.getQualifiedName();
        RequestAnnotation className = RequestAnnotation.findByQualifiedName(qualifiedName);
        if (className != null) {
          paramType = className.getType();
        }
      }
      apiImplicitParamList.add(ApiImplicitParamAnnotation.builder()
          .paramType(paramType)
          .dataType(dataType)
          .name(psiParameter.getNameIdentifier().getText())
          .value("")
          .build()
          .toStr());
    }
    if (!apiImplicitParamList.isEmpty()) {
      apiImplicitParamsAnnotationText = apiImplicitParamList.stream().collect(Collectors.joining(",\n", "@ApiImplicitParams({\n", "\n})"));
    }

    this.doWrite(ApiOperationAnnotation.API_OPERATION, apiOperationAnnotationText, psiMethod);
    if (StrUtil.isNotEmpty(apiImplicitParamsAnnotationText)) {
      this.doWrite(ApiImplicitParamsAnnotation.API_IMPLICIT_PARAMS, apiImplicitParamsAnnotationText, psiMethod);
    }
    addImport(elementFactory, psiFile, ApiImplicitParamAnnotation.API_IMPLICIT_PARAM.getSimpleName());
  }

  /**
   * 生成属性注解
   *
   * @param psiField Psi字段
   */
  private void generateFieldAnnotation(PsiField psiField) {
    List<PsiComment> commentList = getCommentByField(psiField);
    SwaggerAnnotation annotation = ApiModelPropertyAnnotation.API_MODEL_PROPERTY;
    if (commentList.isEmpty()) {
      this.doWrite(annotation, ApiModelPropertyAnnotation.builder().value("").build().toStr(), psiField);
      return;
    }
    commentList.stream()
        .map(PsiElement::getText)
        .map(CoreUtils::getCommentDesc)
        .map(commentDesc -> ApiModelPropertyAnnotation.builder().value(commentDesc).build().toStr())
        .forEach(apiModelPropertyText -> this.doWrite(annotation, apiModelPropertyText, psiField));
  }

  /**
   * 从Psi字段中获取注释
   *
   * @param psiField Psi字段
   * @return Psi注释列表
   */
  private List<PsiComment> getCommentByField(PsiField psiField) {
    return getCommentByElement(psiField.getChildren());
  }

  /**
   * 从Psi类中获取注释
   *
   * @param psiClass Psi方法
   * @return Psi注释列表
   */
  private List<PsiComment> getCommentByClass(PsiClass psiClass) {
    return getCommentByElement(psiClass.getChildren());
  }

  /**
   * 从Psi元素中获取注释
   *
   * @param psiElement Psi元素数组
   * @return Psi注释列表
   */
  private List<PsiComment> getCommentByElement(PsiElement[] psiElement) {
    return Arrays.stream(psiElement)
        .filter(child -> (child instanceof PsiComment))
        .map(child -> (PsiComment) child)
        .collect(Collectors.toList());
  }

  /**
   * 获取注解属性
   *
   * @param psiAnnotation 注解全路径
   * @param attributeName 注解属性名
   * @return 属性值
   */
  private String getAttribute(PsiAnnotation psiAnnotation, String attributeName) {
    if (psiAnnotation == null) {
      return "";
    }
    PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findDeclaredAttributeValue(attributeName);
    return psiAnnotationMemberValue == null ? "" : psiAnnotationMemberValue.getText();
  }

  /**
   * 获取RequestMapping注解属性
   *
   * @param psiAnnotations 注解元素数组
   * @param attributeName  属性名
   * @return 属性值
   */
  private String getMappingAttribute(PsiAnnotation[] psiAnnotations, String attributeName) {
    for (PsiAnnotation psiAnnotation : psiAnnotations) {
      String qualifiedName = psiAnnotation.getQualifiedName();
      if (MappingAnnotation.REQUEST_MAPPING.equals(qualifiedName)) {
        return getAttribute(psiAnnotation, attributeName);
      }
      MappingAnnotation mapping = MappingAnnotation.findByQualifiedName(qualifiedName);
      if (mapping != null) {
        return mapping.getType();
      }
    }
    return "";
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
    //获取所有导入的包
    final PsiImportList importList = javaFile.getImportList();
    if (importList == null) {
      return;
    }
    PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(className, GlobalSearchScope.allScope(project));
    //待导入类有多个同名类或没有时，让用户自行处理
    if (psiClasses.length != 1) {
      return;
    }
    PsiClass waiteImportClass = psiClasses[0];
    //已经导入
    if (Arrays.stream(importList.getAllImportStatements())
        .map(is -> is.getImportReference().getQualifiedName())
        .anyMatch(impQualifiedName -> waiteImportClass.getQualifiedName().equals(impQualifiedName))) {
      return;
    }
    importList.add(elementFactory.createImportStatement(waiteImportClass));
  }
}
