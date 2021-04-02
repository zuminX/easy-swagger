package com.zuminX.utils.builder;

import com.intellij.psi.PsiClass;
import com.zuminX.annotations.swagger.ApiModel;
import com.zuminX.interceptor.AnnotationBuilderInterceptor;
import com.zuminX.utils.GeneratorUtils;

public class ApiModelBuilder {

  public static final ApiModelBuilder BUILDER = AnnotationBuilderInterceptor.create(ApiModelBuilder.class, ApiModel.class);

  public final ApiModel build(PsiClass psiClass) {
    return ApiModel.builder().description(getDescription(psiClass)).build();
  }

  protected String getDescription(PsiClass psiClass) {
    return GeneratorUtils.getFirstComment(psiClass);
  }
}
