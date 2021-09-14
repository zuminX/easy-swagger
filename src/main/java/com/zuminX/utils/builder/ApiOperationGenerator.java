package com.zuminX.utils.builder;

import com.intellij.psi.PsiMethod;
import com.zuminX.annotations.swagger.ApiOperation;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.utils.GeneratorUtils;
import com.zuminX.utils.PsiUtils;

/**
 * ApiOperation注解的生成器类
 */
public class ApiOperationGenerator implements AnnotationGenerator<PsiMethod, ApiOperation> {

  public static final ApiOperationGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiOperationGenerator.class, ApiOperation.class);

  /**
   * 根据psi方法对象构建ApiOperation对象
   *
   * @param psiMethod psi方法
   * @return ApiOperation注解对象
   */
  public final ApiOperation build(PsiMethod psiMethod) {
    return ApiOperation.builder()
        .value(getValue(psiMethod))
        .httpMethod(getHttpMethod(psiMethod)).build();
  }

  /**
   * 从psi方法中获取ApiOperation类的httpMethod属性
   *
   * @param psiMethod psi方法
   * @return ApiOperation类的httpMethod属性
   */
  protected String getHttpMethod(PsiMethod psiMethod) {
    return GeneratorUtils.getMethodOfRequestMapping(psiMethod);
  }

  /**
   * 从psi方法中获取ApiOperation类的value属性
   *
   * @param psiMethod psi方法
   * @return ApiOperation类的value属性
   */
  protected String getValue(PsiMethod psiMethod) {
    return PsiUtils.getFirstComment(psiMethod);
  }

}
