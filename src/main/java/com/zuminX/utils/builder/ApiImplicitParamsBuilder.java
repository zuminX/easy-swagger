package com.zuminX.utils.builder;

import com.intellij.psi.PsiParameter;
import com.zuminX.annotations.swagger.ApiImplicitParam;
import com.zuminX.annotations.swagger.ApiImplicitParams;
import com.zuminX.interceptor.AnnotationBuilderInterceptor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiImplicitParamsBuilder {

  public static final ApiImplicitParamsBuilder BUILDER = AnnotationBuilderInterceptor.create(ApiImplicitParamsBuilder.class, ApiImplicitParams.class);

  public final ApiImplicitParams build(PsiParameter[] psiParameters) {
    return ApiImplicitParams.builder().value(getValue(psiParameters)).build();
  }

  protected List<ApiImplicitParam> getValue(PsiParameter[] psiParameters) {
    return Arrays.stream(psiParameters)
        .map(ApiImplicitParamBuilder.BUILDER::build)
        .collect(Collectors.toList());
  }

}
