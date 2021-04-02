package com.zuminX.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
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
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.domain.GeneratorPsi;
import com.zuminX.names.ClassName;
import com.zuminX.names.ControllerAnnotation;
import com.zuminX.names.MappingAnnotation;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.service.Notify;
import com.zuminX.utils.builder.ApiBuilder;
import com.zuminX.utils.builder.ApiImplicitParamsBuilder;
import com.zuminX.utils.builder.ApiModelBuilder;
import com.zuminX.utils.builder.ApiModelPropertyBuilder;
import com.zuminX.utils.builder.ApiOperationBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

/**
 * 生成Swagger注解的工具类
 */
@UtilityClass
public class GeneratorUtils {

  /**
   * 根据当前类生成Swagger注解
   */
  public void generate(PsiFile psiFile) {
    GeneratorPsi<PsiClass> generatorPsi = GeneratorPsi.build(psiFile);
    generate(() -> generateDefault(generatorPsi), generatorPsi);
  }

  /**
   * 根据所选择的内容生成对应的Swagger注解
   *
   * @param selectionText 选择的文本
   */
  public void generate(PsiFile psiFile, String selectionText) {
    GeneratorPsi<PsiClass> generatorPsi = GeneratorPsi.build(psiFile);
    generate(() -> generateSelection(selectionText, generatorPsi), generatorPsi);
  }

  /**
   * 从Psi元素中获取注释
   * <p/>
   * 若存在多个注释，则选取第一个
   *
   * @param psiElement Psi元素
   * @return 注释
   */
  public String getFirstComment(PsiElement psiElement) {
    PsiComment comment = CollUtil.getFirst(getCommentByElement(psiElement.getChildren()));
    return comment != null ? CoreUtils.getCommentDesc(comment.getText()) : null;
  }

  /**
   * 从Psi元素中获取注释
   *
   * @param psiElement Psi元素数组
   * @return Psi注释列表
   */
  public List<PsiComment> getCommentByElement(PsiElement[] psiElement) {
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
  public String getMethodOfRequestMapping(PsiModifierListOwner modifierListOwner) {
    PsiAnnotation annotation = findRequestMapping(modifierListOwner);
    if (annotation == null) {
      return null;
    }
    MappingAnnotation mapping = MappingAnnotation.findByQualifiedName(annotation.getQualifiedName());
    if (!MappingAnnotation.REQUEST_MAPPING.equals(mapping)) {
      return mapping.getType();
    }
    List<String> values = getValueOfAnnotationMemberValue(annotation, "method");
    if (values.isEmpty()) {
      return null;
    }
    if (values.size() > 1) {
      Notify.getInstance(annotation.getProject()).warning("generator.annotation.warning.multipleMethod");
    }
    return values.get(0);
  }

  /**
   * 获取RequestMapping注解中的val属性值
   *
   * @param modifierListOwner Psi元素的修饰符列表
   * @return val属性值
   */
  @Nullable
  public String getValueOfRequestMapping(PsiModifierListOwner modifierListOwner) {
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
  public PsiAnnotation findRequestMapping(PsiModifierListOwner modifierListOwner) {
    return Arrays.stream(modifierListOwner.getModifierList().getAnnotations())
        .filter(psiAnnotation -> MappingAnnotation.findByQualifiedName(psiAnnotation.getQualifiedName()) != null)
        .findAny()
        .orElse(null);
  }

  /**
   * 获取注解属性文本
   *
   * @param psiAnnotation 注解全路径
   * @param attributeName 注解属性名
   * @return 属性值
   */
  @Nullable
  public String getTextOfAnnotationMemberValue(PsiAnnotation psiAnnotation, String attributeName) {
    if (psiAnnotation == null) {
      return null;
    }
    PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findDeclaredAttributeValue(attributeName);
    return psiAnnotationMemberValue == null ? null : PublicUtils.unwrapInDoubleQuotes(psiAnnotationMemberValue.getText());
  }

  /**
   * 获取注解属性值
   *
   * @param psiAnnotation 注解全路径
   * @param attributeName 注解属性名
   */
  public List<String> getValueOfAnnotationMemberValue(PsiAnnotation psiAnnotation, String attributeName) {
    PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findDeclaredAttributeValue(attributeName);
    if (psiAnnotationMemberValue == null) {
      return ListUtil.empty();
    }
    //获取实际类型：((ClsEnumConstantImpl) resolve).getStub().getType(false).toString()
    return Arrays.stream(psiAnnotationMemberValue.getChildren())
        .flatMap(child -> Arrays.stream(child.getReferences()))
        .map(PsiReference::resolve)
        .filter(Objects::nonNull)
        .map(PsiElement::getText)
        .collect(Collectors.toList());
  }

  /**
   * 根据当前类生成Swagger注解
   *
   * @param generatorPsi 生成注解的PSI元素
   */
  private void generateDefault(GeneratorPsi<PsiClass> generatorPsi) {
    PsiClass psiClass = generatorPsi.getElement();
    if (isController(psiClass)) {
      generateControllerClassAnnotation(generatorPsi);
      Arrays.stream(psiClass.getMethods()).forEach(psiMethod -> generateMethodAnnotation(generatorPsi.replace(psiMethod)));
    } else {
      generateDomainClassAnnotation(generatorPsi);
      Arrays.stream(psiClass.getAllFields()).forEach(psiField -> generateFieldAnnotation(generatorPsi.replace(psiField)));
    }
  }

  /**
   * 根据所选择的内容生成对应的Swagger注解
   */
  public void generateSelection(String selectionText, GeneratorPsi<PsiClass> generatorPsi) {
    PsiClass psiClass = generatorPsi.getElement();
    if (ObjectUtil.equals(selectionText, psiClass.getName())) {
      generateClassAnnotation(generatorPsi);
      return;
    }
    PsiMethod method = findMethodByName(psiClass, selectionText);
    if (method != null) {
      generateMethodAnnotation(generatorPsi.replace(method));
      return;
    }
    PsiField field = findFieldByNameIdentifier(psiClass, selectionText);
    if (field != null) {
      generateFieldAnnotation(generatorPsi.replace(field));
    }
  }

  /**
   * 生成Swagger注解
   *
   * @param runnable     任务
   * @param generatorPsi 生成注解的PSI元素
   */
  private void generate(Runnable runnable, GeneratorPsi<PsiClass> generatorPsi) {
    if (generatorPsi.getElement().getModifierList() == null) {
      Notify.getInstance(generatorPsi.getProject()).error("generator.annotation.error.unableGenerate");
      return;
    }
    WriteCommandAction.runWriteCommandAction(generatorPsi.getProject(), runnable);
  }

  /**
   * 写入Swagger注解到文件
   *
   * @param annotationStr        注解字符串对象
   * @param generatorPsi 生成注解的PSI元素
   */
  private void doWrite(AnnotationStr annotationStr, GeneratorPsi<?> generatorPsi) {
    ClassName className = annotationStr.getClassName();
    removeAnnotation(className.getQualifiedName(), generatorPsi.getElement());
    addImport(generatorPsi.getPsiFile(), className);
    addAnnotation(className.getSimpleName(), annotationStr.toStr(), generatorPsi.getElement());
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
    PsiAnnotation psiAnnotationDeclare = getPsiElementFactory(psiAnnotation).createAnnotationFromText(annotationText, psiModifierListOwner);
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
   * @param generatorPsi 生成注解的PSI元素
   */
  private void generateClassAnnotation(GeneratorPsi<PsiClass> generatorPsi) {
    if (isController(generatorPsi.getElement())) {
      generateControllerClassAnnotation(generatorPsi);
    } else {
      generateDomainClassAnnotation(generatorPsi);
    }
  }

  /**
   * 生成控制类注解
   *
   * @param generatorPsi 生成注解的PSI元素
   */
  private void generateControllerClassAnnotation(GeneratorPsi<PsiClass> generatorPsi) {
    doWrite(ApiBuilder.BUILDER.build(generatorPsi.getElement()), generatorPsi);
  }

  /**
   * 生成实体类注解
   *
   * @param generatorPsi 生成注解的PSI元素
   */
  private void generateDomainClassAnnotation(GeneratorPsi<PsiClass> generatorPsi) {
    doWrite(ApiModelBuilder.BUILDER.build(generatorPsi.getElement()), generatorPsi);
  }

  /**
   * 生成方法注解
   *
   * @param generatorPsi 生成注解的PSI元素
   */
  private void generateMethodAnnotation(GeneratorPsi<PsiMethod> generatorPsi) {
    generateMethodOperationAnnotation(generatorPsi);
    generateMethodParameterAnnotation(generatorPsi);
    addImport(generatorPsi.getPsiFile(), SwaggerAnnotation.API_IMPLICIT_PARAM);
  }

  /**
   * 生成方法操作注解
   *
   * @param generatorPsi 生成注解的PSI元素
   */
  private void generateMethodOperationAnnotation(GeneratorPsi<PsiMethod> generatorPsi) {
    doWrite(ApiOperationBuilder.BUILDER.build(generatorPsi.getElement()), generatorPsi);
  }

  /**
   * 生成方法参数注解
   *
   * @param generatorPsi 生成注解的PSI元素
   */
  private void generateMethodParameterAnnotation(GeneratorPsi<PsiMethod> generatorPsi) {
    PsiParameter[] psiParameters = generatorPsi.getElement().getParameterList().getParameters();
    if (ArrayUtil.isEmpty(psiParameters)) {
      return;
    }
    doWrite(ApiImplicitParamsBuilder.BUILDER.build(psiParameters), generatorPsi);
  }

  /**
   * 生成属性注解
   *
   * @param generatorPsi 生成注解的PSI元素
   */
  private void generateFieldAnnotation(GeneratorPsi<PsiField> generatorPsi) {
    doWrite(ApiModelPropertyBuilder.BUILDER.build(generatorPsi.getElement()), generatorPsi);
  }

  /**
   * 导入类依赖
   *
   * @param file      当前文件对象
   * @param className 类名对象
   */
  private void addImport(PsiFile file, ClassName className) {
    if (!(file instanceof PsiJavaFile)) {
      return;
    }
    PsiImportList importList = ((PsiJavaFile) file).getImportList();
    if (importList == null) {
      return;
    }
    PsiClass wantImportClass = getWantImportClass(file.getProject(), className);
    if (wantImportClass == null || hasImportClass(importList, className.getQualifiedName())) {
      return;
    }
    importList.add(getPsiElementFactory(file).createImportStatement(wantImportClass));
  }

  /**
   * 获取期望导入的类
   *
   * @param className 类名对象
   * @return Psi类
   */
  @Nullable
  private PsiClass getWantImportClass(Project project, ClassName className) {
    PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(className.getSimpleName(), GlobalSearchScope.allScope(project));
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

  private PsiElementFactory getPsiElementFactory(PsiElement psiElement) {
    return JavaPsiFacade.getElementFactory(psiElement.getProject());
  }
}
