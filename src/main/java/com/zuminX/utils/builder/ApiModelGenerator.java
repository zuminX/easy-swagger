package com.zuminX.utils.builder;

import com.intellij.psi.PsiClass;
import com.zuminX.annotations.swagger.ApiModel;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.utils.GeneratorUtils;

public class ApiModelGenerator implements AnnotationGenerator<PsiClass, ApiModel> {

  public static final ApiModelGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiModelGenerator.class, ApiModel.class);

  public final ApiModel build(PsiClass psiClass) {
    return ApiModel.builder().description(getDescription(psiClass)).build();
  }

  protected String getDescription(PsiClass psiClass) {
    return GeneratorUtils.getFirstComment(psiClass);
  }
}
