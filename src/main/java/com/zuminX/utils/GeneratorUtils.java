package com.zuminX.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
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
import com.intellij.psi.PsiReference;
import com.intellij.psi.infos.CandidateInfo;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.util.PsiTreeUtil;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.annotations.swagger.Api;
import com.zuminX.annotations.swagger.ApiImplicitParam;
import com.zuminX.annotations.swagger.ApiImplicitParams;
import com.zuminX.annotations.swagger.ApiModel;
import com.zuminX.annotations.swagger.ApiModelProperty;
import com.zuminX.annotations.swagger.ApiOperation;
import com.zuminX.names.ClassName;
import com.zuminX.names.ControllerAnnotation;
import com.zuminX.names.MappingAnnotation;
import com.zuminX.names.RequestAnnotation;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.service.Notify;
import com.zuminX.window.form.SwaggerAnnotationForm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Nullable;

public class GeneratorUtils {

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

  private static <T extends AnnotationStr> T getSettingsAnnotation(Class<T> clazz) {
    return SwaggerAnnotationForm.getSettingsData().getAnnotationInstance(clazz);
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
    if (isController(psiClass)) {
      generateControllerClassAnnotation(psiClass);
      Arrays.stream(psiClass.getMethods()).forEach(this::generateMethodAnnotation);
    } else {
      generateDomainClassAnnotation(psiClass);
      Arrays.stream(psiClass.getAllFields()).forEach(this::generateFieldAnnotation);
    }
  }

  /**
   * 根据所选择的内容生成对应的Swagger注解
   */
  public void generateSelection(String selectionText) {
    if (ObjectUtil.equals(selectionText, psiClass.getName())) {
      this.generateClassAnnotation(psiClass);
      return;
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
      Notify.getInstance(project).error("无法生成Swagger注解");
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
        .filter(psiMethod -> ObjectUtil.equals(name, psiMethod.getName()))
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
        .filter(psiField -> ObjectUtil.equals(nameIdentifier, psiField.getNameIdentifier().getText()))
        .findFirst()
        .orElse(null);
  }

  /**
   * 写入Swagger注解到文件
   *
   * @param annotationStr        注解字符串对象
   * @param psiModifierListOwner 当前写入对象
   */
  private void doWrite(AnnotationStr annotationStr, PsiModifierListOwner psiModifierListOwner) {
    ClassName className = annotationStr.getClassName();
    removeAnnotation(className.getQualifiedName(), psiModifierListOwner);
    addImport(elementFactory, psiFile, className);
    addAnnotation(className.getSimpleName(), annotationStr.toStr(), psiModifierListOwner);
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
    if (isController(psiClass)) {
      generateControllerClassAnnotation(psiClass);
    } else {
      generateDomainClassAnnotation(psiClass);
    }
  }

  /**
   * 生成控制类注解
   *
   * @param psiClass Psi类
   */
  private void generateControllerClassAnnotation(PsiClass psiClass) {
    String value = getValueOfRequestMapping(psiClass);
    String comment = getFirstComment(psiClass);
    List<String> tags = comment == null ? null : Collections.singletonList(comment);

    Api api = getSettingsAnnotation(Api.class);
    api.getValue().setData(value);
    api.getTags().setData(tags);

    doWrite(api, psiClass);
  }

  /**
   * 生成实体类注解
   *
   * @param psiClass Psi类
   */
  private void generateDomainClassAnnotation(PsiClass psiClass) {
    ApiModel apiModel = getSettingsAnnotation(ApiModel.class);
    apiModel.getDescription().setData(getFirstComment(psiClass));

    doWrite(apiModel, psiClass);
  }

  /**
   * 生成方法注解
   *
   * @param psiMethod Psi方法
   */
  private void generateMethodAnnotation(PsiMethod psiMethod) {
    generateMethodOperationAnnotation(psiMethod);
    generateMethodParameterAnnotation(psiMethod);
    addImport(elementFactory, psiFile, SwaggerAnnotation.API_IMPLICIT_PARAM);
  }

  /**
   * 生成方法操作注解
   *
   * @param psiMethod Psi方法
   */
  private void generateMethodOperationAnnotation(PsiMethod psiMethod) {
    PsiAnnotation apiOperationExist = psiMethod.getModifierList().findAnnotation(SwaggerAnnotation.API_OPERATION.getQualifiedName());
    String value = Convert.toStr(getTextOfAnnotationMemberValue(apiOperationExist, "value"), "");
    String notes = getTextOfAnnotationMemberValue(apiOperationExist, "notes");
    String httpMethod = getMethodOfRequestMapping(psiMethod);

    ApiOperation apiOperation = getSettingsAnnotation(ApiOperation.class);
    apiOperation.getValue().setData(value);
    apiOperation.getNotes().setData(notes);
    apiOperation.getHttpMethod().setData(httpMethod);

    doWrite(apiOperation, psiMethod);
  }

  /**
   * 生成方法参数注解
   *
   * @param psiMethod Psi方法
   */
  private void generateMethodParameterAnnotation(PsiMethod psiMethod) {
    PsiParameter[] psiParameters = psiMethod.getParameterList().getParameters();
    if (ArrayUtil.isEmpty(psiParameters)) {
      return;
    }
    List<ApiImplicitParam> apiImplicitParamList = new ArrayList<>(psiParameters.length);
    Arrays.stream(psiParameters).forEach(psiParameter -> {
      String dataType = PublicUtils.getSimpleNameByQualifiedName(psiParameter.getType().getCanonicalText());
      String paramType = Arrays.stream(psiParameter.getModifierList().getAnnotations())
          .map(PsiAnnotation::getQualifiedName)
          .map(RequestAnnotation::findByQualifiedName)
          .filter(Objects::nonNull)
          .findAny()
          .map(RequestAnnotation::getType)
          .orElse(null);

      ApiImplicitParam apiImplicitParam = getSettingsAnnotation(ApiImplicitParam.class);
      apiImplicitParam.getParamType().setData(paramType);
      apiImplicitParam.getDataType().setData(dataType);
      apiImplicitParam.getName().setData(psiParameter.getNameIdentifier().getText());
      apiImplicitParam.getValue().setData("");

      apiImplicitParamList.add(apiImplicitParam);
    });

    ApiImplicitParams apiImplicitParams = getSettingsAnnotation(ApiImplicitParams.class);
    apiImplicitParams.getValue().setData(apiImplicitParamList);

    doWrite(apiImplicitParams, psiMethod);
  }

  /**
   * 生成属性注解
   *
   * @param psiField Psi字段
   */
  private void generateFieldAnnotation(PsiField psiField) {
    ApiModelProperty apiModelProperty = getSettingsAnnotation(ApiModelProperty.class);
    apiModelProperty.getValue().setData(getFirstComment(psiField));

    doWrite(apiModelProperty, psiField);
  }

  /**
   * 从Psi元素中获取注释
   * <p/>
   * 若存在多个注释，则选取第一个
   *
   * @param psiElement Psi元素
   * @return 注释
   */
  private String getFirstComment(PsiElement psiElement) {
    PsiComment comment = CollUtil.getFirst(getCommentByElement(psiElement.getChildren()));
    return comment != null ? CoreUtils.getCommentDesc(comment.getText()) : null;
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
   * 获取RequestMapping注解中的method属性值
   *
   * @param modifierListOwner Psi元素的修饰符列表
   * @return method属性值
   */
  private String getMethodOfRequestMapping(PsiModifierListOwner modifierListOwner) {
    PsiAnnotation annotation = findRequestMapping(modifierListOwner);
    if (annotation == null) {
      return null;
    }
    MappingAnnotation mapping = MappingAnnotation.findByQualifiedName(annotation.getQualifiedName());
    if (!MappingAnnotation.REQUEST_MAPPING.equals(mapping)) {
      return mapping.getType();
    }
    List<PsiField> psiFields = getFieldOfAnnotationMemberValue(annotation, "method");
    if (psiFields.isEmpty()) {
      return null;
    }
    if (psiFields.size() > 1) {
      Notify.getInstance(project).warning("@ApiOperation中的httpMethod属性无法支持多个请求类型");
    }
    return psiFields.get(0).getName();
  }

  /**
   * 获取RequestMapping注解中的val属性值
   *
   * @param modifierListOwner Psi元素的修饰符列表
   * @return val属性值
   */
  @Nullable
  private String getValueOfRequestMapping(PsiModifierListOwner modifierListOwner) {
    PsiAnnotation annotation = findRequestMapping(modifierListOwner);
    return annotation == null ? null : getTextOfAnnotationMemberValue(annotation, "value");
  }

  /**
   * 寻找RequestMapping注解
   *
   * @param modifierListOwner Psi元素的修饰符列表
   * @return RequestMapping注解对应的Psi注解
   */
  @Nullable
  private PsiAnnotation findRequestMapping(PsiModifierListOwner modifierListOwner) {
    return Arrays.stream(modifierListOwner.getModifierList().getAnnotations())
        .filter(psiAnnotation -> MappingAnnotation.findByQualifiedName(psiAnnotation.getQualifiedName()) != null)
        .findAny()
        .orElse(null);
  }

  /**
   * 获取注解属性值
   *
   * @param psiAnnotation 注解全路径
   * @param attributeName 注解属性名
   * @return 属性值
   */
  @Nullable
  private String getTextOfAnnotationMemberValue(PsiAnnotation psiAnnotation, String attributeName) {
    if (psiAnnotation == null) {
      return null;
    }
    PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findDeclaredAttributeValue(attributeName);
    return psiAnnotationMemberValue == null ? null : PublicUtils.unwrapInDoubleQuotes(psiAnnotationMemberValue.getText());
  }

  /**
   * 获取注释成员值的Psi字段
   *
   * @param psiAnnotation 注解全路径
   * @param attributeName 注解属性名
   */
  private List<PsiField> getFieldOfAnnotationMemberValue(PsiAnnotation psiAnnotation, String attributeName) {
    PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findDeclaredAttributeValue(attributeName);
    if (psiAnnotationMemberValue == null) {
      return ListUtil.empty();
    }
    return Arrays.stream(psiAnnotationMemberValue.getChildren())
        .flatMap(child -> Arrays.stream(child.getReferences()))
        .map(PsiReference::resolveReference)
        .flatMap(Collection::stream)
        .filter(resolveResult -> resolveResult instanceof CandidateInfo)
        .map(resolveResult -> (CandidateInfo) resolveResult)
        .map(CandidateInfo::getElement)
        .filter(psiElement -> psiElement instanceof PsiField)
        .map(psiElement -> (PsiField) psiElement)
        .collect(Collectors.toList());
  }

  /**
   * 导入类依赖
   *
   * @param elementFactory 元素Factory
   * @param file           当前文件对象
   * @param className      类名对象
   */
  private void addImport(PsiElementFactory elementFactory, PsiFile file, ClassName className) {
    if (!(file instanceof PsiJavaFile)) {
      return;
    }

    PsiImportList importList = ((PsiJavaFile) file).getImportList();
    if (importList == null) {
      return;
    }

    PsiClass wantImportClass = getWantImportClass(className);
    if (wantImportClass == null || hasImportClass(importList, className.getQualifiedName())) {
      return;
    }
    importList.add(elementFactory.createImportStatement(wantImportClass));
  }

  /**
   * 获取期望导入的类
   *
   * @param className 类名对象
   * @return Psi类
   */
  @Nullable
  private PsiClass getWantImportClass(ClassName className) {
    PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project)
        .getClassesByName(className.getSimpleName(), GlobalSearchScope.allScope(project));
    return Arrays.stream(psiClasses)
        .filter(psiClass -> StrUtil.equals(psiClass.getQualifiedName(), className.getQualifiedName()))
        .findAny()
        .orElse(null);
  }

  /**
   * 查看导入列表中是否存在指定类名的类
   *
   * @param importList    导入列表
   * @param qualifiedName 导入类的全限定类名
   * @return 若存在则返回true，不存在则返回false
   */
  private boolean hasImportClass(PsiImportList importList, String qualifiedName) {
    return Arrays.stream(importList.getAllImportStatements())
        .map(is -> is.getImportReference().getQualifiedName())
        .anyMatch(qualifiedName::equals);
  }
}
