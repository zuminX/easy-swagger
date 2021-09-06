package com.zuminX.utils.builder;

import com.intellij.psi.PsiClass;
import com.zuminX.annotations.swagger.Api;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.utils.GeneratorUtils;
import java.util.Collections;
import java.util.List;

/**
 * Api注解的生成器类
 */
public class ApiGenerator implements AnnotationGenerator<PsiClass, Api> {

  public static final ApiGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiGenerator.class, Api.class);

  /**
   * 根据psi类对象构建Api注解对象
   *
   * @param psiClass psi类
   * @return Api注解对象
   */
  public final Api build(PsiClass psiClass) {
    return Api.builder().value(getValue(psiClass)).tags(getTags(psiClass)).build();
  }

  /**
   * 从psi类对象中获取Api类的value属性
   *
   * @param psiClass psi类
   * @return Api类的value属性
   */
  protected String getValue(PsiClass psiClass) {
    return GeneratorUtils.getValueOfRequestMapping(psiClass);
  }

  /**
   * 从psi类对象中获取Api类的tags属性
   *
   * @param psiClass psi类
   * @return Api类的tags属性
   */
  protected List<String> getTags(PsiClass psiClass) {
    String comment = GeneratorUtils.getFirstComment(psiClass);
    return comment == null ? null : Collections.singletonList(comment);
  }
}
