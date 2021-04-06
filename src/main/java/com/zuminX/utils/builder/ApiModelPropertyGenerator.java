package com.zuminX.utils.builder;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.zuminX.annotations.swagger.ApiModel;
import com.zuminX.annotations.swagger.ApiModelProperty;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.utils.GeneratorUtils;

public class ApiModelPropertyGenerator implements AnnotationGenerator<PsiField, ApiModelProperty> {

  public static final ApiModelPropertyGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiModelPropertyGenerator.class, ApiModelProperty.class);

  public final ApiModelProperty build(PsiField psiField) {
    return ApiModelProperty.builder().value(getValue(psiField)).build();
  }

  protected String getValue(PsiField psiField) {
    return GeneratorUtils.getFirstComment(psiField);
  }
}
