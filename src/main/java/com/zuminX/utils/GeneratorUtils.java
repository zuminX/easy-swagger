package com.zuminX.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.domain.ProjectPsi;
import com.zuminX.names.ClassName;
import com.zuminX.names.ControllerAnnotation;
import com.zuminX.names.MappingAnnotation;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.service.Notify;
import com.zuminX.utils.builder.ApiGenerator;
import com.zuminX.utils.builder.ApiImplicitParamsGenerator;
import com.zuminX.utils.builder.ApiModelGenerator;
import com.zuminX.utils.builder.ApiModelPropertyGenerator;
import com.zuminX.utils.builder.ApiOperationGenerator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

/**
 * 生成Swagger注解的工具类
 */
@UtilityClass
public class GeneratorUtils {

  private static final String CONTROLLER = "Controller";

  private static final String NAME = "name";

  /**
   * 根据所选择的内容生成对应的Swagger注解
   *
   * @param selectionText 选择的文本
   */
  public static void generate(PsiFile psiFile, String selectionText) {
    ProjectPsi<PsiClass> projectPsi = ProjectPsi.build(psiFile);
    if (StrUtil.isBlank(selectionText)) {
      generate(() -> generateDefault(projectPsi), projectPsi);
    } else {
      generate(() -> generateSelection(selectionText, projectPsi), projectPsi);
    }
  }

  /**
   * 获取RequestMapping注解中的method属性值
   *
   * @param modifierListOwner Psi元素的修饰符列表
   * @return method属性值
   */
  public static String getMethodOfRequestMapping(PsiModifierListOwner modifierListOwner) {
    PsiAnnotation annotation = findRequestMapping(modifierListOwner);
    if (annotation == null) {
      return null;
    }
    MappingAnnotation mapping = MappingAnnotation.findByQualifiedName(annotation.getQualifiedName());
    if (!MappingAnnotation.REQUEST_MAPPING.equals(mapping)) {
      return mapping.getType();
    }
    List<String> values = PsiUtils.getValueOfAnnotationMemberValue(annotation, "method");
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
  public static String getValueOfRequestMapping(PsiModifierListOwner modifierListOwner) {
    PsiAnnotation annotation = findRequestMapping(modifierListOwner);
    return annotation == null ? null : PsiUtils.getTextOfAnnotationMemberValue(annotation, "value");
  }

  /**
   * 寻找RequestMapping注解
   *
   * @param modifierListOwner Psi元素的修饰符列表
   * @return RequestMapping注解对应的Psi注解
   */
  @Nullable
  public static PsiAnnotation findRequestMapping(PsiModifierListOwner modifierListOwner) {
    PsiModifierList list = modifierListOwner.getModifierList();
    if (list == null) {
      return null;
    }
    return Arrays.stream(list.getAnnotations())
        .filter(psiAnnotation -> MappingAnnotation.findByQualifiedName(psiAnnotation.getQualifiedName()) != null)
        .findAny()
        .orElse(null);
  }

  /**
   * 根据所选择的内容生成对应的Swagger注解
   */
  public static void generateSelection(String selectionText, ProjectPsi<PsiClass> projectPsi) {
    PsiClass psiClass = projectPsi.getElement();
    if (ObjectUtil.equals(selectionText, psiClass.getName())) {
      generateClassAnnotation(projectPsi);
      return;
    }
    PsiMethod method = PsiUtils.findMethodByName(psiClass, selectionText);
    if (method != null) {
      generateMethodAnnotation(projectPsi.replace(method));
      return;
    }
    PsiField field = PsiUtils.findFieldByNameIdentifier(psiClass, selectionText);
    if (field != null) {
      generateFieldAnnotation(projectPsi.replace(field));
      return;
    }
    Notify.getInstance(projectPsi.getProject()).warning("generator.annotation.warning.chooseIllegal");
  }

  /**
   * 写入Swagger注解到文件
   *
   * @param annotationStr 注解字符串对象
   * @param projectPsi    生成注解的PSI元素
   */
  public static void doWrite(AnnotationStr annotationStr, ProjectPsi<?> projectPsi) {
    ClassName className = annotationStr.getClassName();
    addImport(projectPsi.getPsiFile(), className);
    updateAnnotation(className.getSimpleName(), annotationStr.toStr(), projectPsi.getElement());
  }

  /**
   * 生成Swagger注解
   *
   * @param runnable   任务
   * @param projectPsi 生成注解的PSI元素
   */
  private static void generate(Runnable runnable, ProjectPsi<PsiClass> projectPsi) {
    if (projectPsi.getElement().getModifierList() == null) {
      Notify.getInstance(projectPsi.getProject()).error("generator.annotation.error.unableGenerate");
      return;
    }
    WriteCommandAction.runWriteCommandAction(projectPsi.getProject(), runnable);
  }

  /**
   * 根据当前类生成Swagger注解
   *
   * @param projectPsi 生成注解的PSI元素
   */
  private static void generateDefault(ProjectPsi<PsiClass> projectPsi) {
    PsiClass psiClass = projectPsi.getElement();
    if (isController(psiClass)) {
      generateControllerClassAnnotation(projectPsi);
      Arrays.stream(psiClass.getMethods()).forEach(psiMethod -> generateMethodAnnotation(projectPsi.replace(psiMethod)));
    } else {
      generateDomainClassAnnotation(projectPsi);
      Arrays.stream(psiClass.getAllFields()).forEach(psiField -> generateFieldAnnotation(projectPsi.replace(psiField)));
    }
  }

  /**
   * 更新注解
   *
   * @param simpleName           注解的简单类名
   * @param annotationText       注解内容
   * @param psiModifierListOwner 当前写入对象
   */
  private static void updateAnnotation(String simpleName, String annotationText, PsiModifierListOwner psiModifierListOwner) {
    PsiModifierList modifierList = psiModifierListOwner.getModifierList();
    if (modifierList == null) {
      return;
    }
    PsiAnnotation psiAnnotation = PsiUtils.findAnnotationByName(simpleName, psiModifierListOwner);
    if (psiAnnotation == null) {
      psiAnnotation = modifierList.addAnnotation(simpleName);
    }
    PsiAnnotation psiAnnotationDeclare = PsiUtils.getPsiElementFactory(psiAnnotation).createAnnotationFromText(annotationText, psiModifierListOwner);
    Map<String, PsiAnnotationMemberValue> map = PsiUtils.getAnnotationNameToValue(psiAnnotation);
    if (map == null) {
      return;
    }
    for (PsiNameValuePair pair : psiAnnotationDeclare.getParameterList().getAttributes()) {
      String name = pair.getName();
      if (map.containsKey(name) && name != null) {
        continue;
      }
      PsiAnnotationMemberValue value = pair.getValue();
      // 处理多重注解
      if (value instanceof PsiArrayInitializerMemberValue && map.get(name) != null) {
        Map<String, Map<String, PsiAnnotationMemberValue>> multipleAnnotationsMap = resolveMultipleAnnotations((PsiArrayInitializerMemberValue) map.get(null));
        for (PsiAnnotationMemberValue initializer : ((PsiArrayInitializerMemberValue) value).getInitializers()) {
          PsiNameValuePair[] psiNameValuePairs = ((PsiAnnotation) initializer).getParameterList().getAttributes();
          PsiAnnotationMemberValue memberValue = findAnnotationMemberValueByName(psiNameValuePairs, NAME);
          if (memberValue == null) {
            continue;
          }
          Map<String, PsiAnnotationMemberValue> memberValueMap = multipleAnnotationsMap.get(memberValue.getText());
          if (memberValueMap == null) {
            continue;
          }
          Arrays.stream(psiNameValuePairs).forEach(psiNameValuePair -> ((PsiAnnotation) initializer).setDeclaredAttributeValue(psiNameValuePair.getName(),
              memberValueMap.getOrDefault(psiNameValuePair.getName(), psiNameValuePair.getValue())));
        }
      }
      psiAnnotation.setDeclaredAttributeValue(name, value);
    }
  }

  /**
   * 解析多重注解
   *
   * @param memberValue Psi数组成员
   * @return key为注解的name属性的值，value为注解中属性的名称到值的映射
   */
  private static Map<String, Map<String, PsiAnnotationMemberValue>> resolveMultipleAnnotations(PsiArrayInitializerMemberValue memberValue) {
    PsiAnnotationMemberValue[] initializers = memberValue.getInitializers();
    Map<String, Map<String, PsiAnnotationMemberValue>> result = new HashMap<>();
    Arrays.stream(initializers)
        .filter(initializer -> initializer instanceof PsiAnnotation)
        .forEach(initializer -> {
          PsiNameValuePair[] psiNameValuePairs = ((PsiAnnotation) initializer).getParameterList().getAttributes();
          PsiAnnotationMemberValue value = findAnnotationMemberValueByName(psiNameValuePairs, NAME);
          if (value != null) {
            result.put(value.getText(), PsiUtils.getAnnotationNameToValue((PsiAnnotation) initializer));
          }
        });
    return result;
  }

  /**
   * 寻找注解中指定名称的成员
   *
   * @param psiNameValuePairs Psi名称值对数组
   * @param name              名称
   * @return Psi注解属性成员
   */
  private static PsiAnnotationMemberValue findAnnotationMemberValueByName(PsiNameValuePair[] psiNameValuePairs, String name) {
    return Arrays.stream(psiNameValuePairs)
        .filter(pair -> name.equals(pair.getName()))
        .findAny().map(PsiNameValuePair::getValue).orElse(null);
  }

  /**
   * 判断给定类是否为controller
   *
   * @param psiClass Psi类
   * @return 若是controller则返回true，否则返回false
   */
  private static boolean isController(PsiClass psiClass) {
    PsiModifierList list = psiClass.getModifierList();
    if (list == null) {
      return false;
    }
    PsiAnnotation[] psiAnnotations = list.getAnnotations();
    List<ControllerAnnotation> controller = ControllerAnnotation.getAll();
    if (StrUtil.endWith(psiClass.getName(), CONTROLLER)) {
      return true;
    }
    return Arrays.stream(psiAnnotations)
        .anyMatch(psiAnnotation -> controller.stream()
            .anyMatch(className -> className.equals(psiAnnotation.getQualifiedName())));
  }

  /**
   * 生成类注解
   *
   * @param projectPsi 生成注解的PSI元素
   */
  private static void generateClassAnnotation(ProjectPsi<PsiClass> projectPsi) {
    if (isController(projectPsi.getElement())) {
      generateControllerClassAnnotation(projectPsi);
    } else {
      generateDomainClassAnnotation(projectPsi);
    }
  }

  /**
   * 生成控制类注解
   *
   * @param projectPsi 生成注解的PSI元素
   */
  private static void generateControllerClassAnnotation(ProjectPsi<PsiClass> projectPsi) {
    ApiGenerator.INSTANCE.add(projectPsi);
  }

  /**
   * 生成实体类注解
   *
   * @param projectPsi 生成注解的PSI元素
   */
  private static void generateDomainClassAnnotation(ProjectPsi<PsiClass> projectPsi) {
    ApiModelGenerator.INSTANCE.add(projectPsi);
  }

  /**
   * 生成方法注解
   *
   * @param projectPsi 生成注解的PSI元素
   */
  private static void generateMethodAnnotation(ProjectPsi<PsiMethod> projectPsi) {
    generateMethodOperationAnnotation(projectPsi);
    generateMethodParameterAnnotation(projectPsi);
    addImport(projectPsi.getPsiFile(), SwaggerAnnotation.API_IMPLICIT_PARAM);
  }

  /**
   * 生成方法操作注解
   *
   * @param projectPsi 生成注解的PSI元素
   */
  private static void generateMethodOperationAnnotation(ProjectPsi<PsiMethod> projectPsi) {
    ApiOperationGenerator.INSTANCE.add(projectPsi);
  }

  /**
   * 生成方法参数注解
   *
   * @param projectPsi 生成注解的PSI元素
   */
  private static void generateMethodParameterAnnotation(ProjectPsi<PsiMethod> projectPsi) {
    ApiImplicitParamsGenerator.INSTANCE.add(projectPsi);
  }

  /**
   * 生成属性注解
   *
   * @param projectPsi 生成注解的PSI元素
   */
  private static void generateFieldAnnotation(ProjectPsi<PsiField> projectPsi) {
    ApiModelPropertyGenerator.INSTANCE.add(projectPsi);
  }

  /**
   * 导入类依赖
   *
   * @param file      当前文件对象
   * @param className 类名对象
   */
  private static void addImport(PsiFile file, ClassName className) {
    if (!(file instanceof PsiJavaFile)) {
      return;
    }
    PsiImportList importList = ((PsiJavaFile) file).getImportList();
    if (importList == null) {
      return;
    }
    PsiClass wantImportClass = getWantImportClass(file.getProject(), className);
    if (wantImportClass == null || PsiUtils.hasImportClass(importList, className.getQualifiedName())) {
      return;
    }
    importList.add(PsiUtils.getPsiElementFactory(file).createImportStatement(wantImportClass));
  }

  /**
   * 获取期望导入的类
   *
   * @param className 类名对象
   * @return Psi类
   */
  @Nullable
  private static PsiClass getWantImportClass(Project project, ClassName className) {
    PsiClass[] psiClasses = PsiShortNamesCache.getInstance(project).getClassesByName(className.getSimpleName(), GlobalSearchScope.allScope(project));
    return Arrays.stream(psiClasses)
        .filter(psiClass -> StrUtil.equals(psiClass.getQualifiedName(), className.getQualifiedName()))
        .findAny()
        .orElse(null);
  }

}
