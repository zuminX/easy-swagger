package com.zuminX.utils.builder;

import cn.hutool.core.util.ArrayUtil;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.zuminX.annotations.swagger.ApiImplicitParam;
import com.zuminX.annotations.swagger.ApiImplicitParams;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiImplicitParamsGenerator implements AnnotationGenerator<PsiMethod, ApiImplicitParams> {

  public static final ApiImplicitParamsGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiImplicitParamsGenerator.class, ApiImplicitParams.class);

  public final ApiImplicitParams build(PsiMethod psiMethod) {
    PsiParameter[] psiParameters = psiMethod.getParameterList().getParameters();
    if (ArrayUtil.isEmpty(psiParameters)) {
      return null;
    }
    return ApiImplicitParams.builder().value(getValue(psiParameters)).build();
  }

  protected List<ApiImplicitParam> getValue(PsiParameter[] psiParameters) {
    return Arrays.stream(psiParameters)
        .map(ApiImplicitParamGenerator.INSTANCE::build)
        .collect(Collectors.toList());
  }

}
