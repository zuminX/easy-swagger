package com.zuminX.utils.builder;

import com.intellij.psi.PsiClass;
import com.zuminX.annotations.swagger.ApiModel;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.names.ClassName;
import com.zuminX.utils.GeneratorUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * ApiModel注解的生成器类
 */
public class ApiModelGenerator implements AnnotationGenerator<PsiClass, ApiModel> {

  public static final ApiModelGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiModelGenerator.class, ApiModel.class);

  /**
   * 根据psi类对象构建ApiModel对象
   *
   * @param psiClass psi类
   * @return ApiModel注解对象
   */
  public final ApiModel build(PsiClass psiClass) {
    return ApiModel.builder()
        .value(getValue(psiClass))
        .description(getDescription(psiClass))
        .parent(getParent(psiClass))
        .subTypes(getSubTypes(psiClass))
        .build();
  }

  /**
   * 从psi类中获取ApiModel类的value属性
   *
   * @param psiClass psi类
   * @return ApiModel类的value属性
   */
  protected String getValue(PsiClass psiClass) {
    return psiClass.getName();
  }

  /**
   * 从psi类中获取ApiModel类的description属性
   *
   * @param psiClass psi类
   * @return ApiModel类的description属性
   */
  protected String getDescription(PsiClass psiClass) {
    return GeneratorUtils.getFirstComment(psiClass);
  }

  /**
   * 从psi类中获取ApiModel类的parent属性
   *
   * @param psiClass psi类
   * @return ApiModel类的parent属性
   */
  protected ClassName getParent(PsiClass psiClass) {
    PsiClass superClass = psiClass.getSuperClass();
    return superClass == null ? ClassName.OBJECT_CLASS_NAME : new ClassName(superClass.getQualifiedName());
  }

  /**
   * 从psi类中获取ApiModel类的subTypes属性
   *
   * @param psiClass psi类
   * @return ApiModel类的subTypes属性
   */
  protected List<ClassName> getSubTypes(PsiClass psiClass) {
    List<ClassName> result = new ArrayList<>();
    PsiClass nowClass = psiClass;
    while (nowClass.getSuperClass() != null) {
      if (!ClassName.OBJECT_CLASS_NAME.getQualifiedName().equals(nowClass.getSuperClass().getQualifiedName())) {
        result.add(getParent(nowClass));
      }
      nowClass = nowClass.getSuperClass();
    }
    // 当且仅当该类无显式父类时，才视为其父类包括Object
    return result.isEmpty() ? List.of(ClassName.OBJECT_CLASS_NAME) : result;
  }
}
