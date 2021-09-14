package com.zuminX.utils.builder;

import com.intellij.psi.PsiField;
import com.zuminX.annotations.swagger.ApiModelProperty;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.utils.PsiUtils;

/**
 * ApiModelProperty注解的生成器类
 */
public class ApiModelPropertyGenerator implements AnnotationGenerator<PsiField, ApiModelProperty> {

  public static final ApiModelPropertyGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiModelPropertyGenerator.class, ApiModelProperty.class);

  /**
   * 根据psi字段对象构建ApiModelProperty对象
   *
   * @param psiField psi字段
   * @return ApiModelProperty注解对象
   */
  public final ApiModelProperty build(PsiField psiField) {
    return ApiModelProperty.builder().value(getValue(psiField)).build();
  }

  /**
   * 从psi字段中获取ApiModel类的value属性
   *
   * @param psiField psi字段
   * @return ApiModelProperty类的value属性
   */
  protected String getValue(PsiField psiField) {
    return PsiUtils.getFirstComment(psiField);
  }
}
