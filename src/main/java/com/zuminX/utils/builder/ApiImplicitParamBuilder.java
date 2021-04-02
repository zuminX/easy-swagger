package com.zuminX.utils.builder;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiParameter;
import com.zuminX.annotations.swagger.ApiImplicitParam;
import com.zuminX.interceptor.AnnotationBuilderInterceptor;
import com.zuminX.names.RequestAnnotation;
import com.zuminX.utils.PublicUtils;
import java.util.Arrays;
import java.util.Objects;

public class ApiImplicitParamBuilder {

  public static final ApiImplicitParamBuilder BUILDER = AnnotationBuilderInterceptor.create(ApiImplicitParamBuilder.class, ApiImplicitParam.class);

  public final ApiImplicitParam build(PsiParameter psiParameter) {
    return ApiImplicitParam.builder()
        .paramType(getParamType(psiParameter))
        .dataType(getDataType(psiParameter))
        .name(getName(psiParameter))
        .value("")
        .build();
  }

  protected String getName(PsiParameter psiParameter) {
    return psiParameter.getNameIdentifier().getText();
  }

  protected String getDataType(PsiParameter psiParameter) {
    return PublicUtils.getSimpleNameByQualifiedName(psiParameter.getType().getCanonicalText());
  }

  protected String getParamType(PsiParameter psiParameter) {
    return Arrays.stream(psiParameter.getModifierList().getAnnotations())
        .map(PsiAnnotation::getQualifiedName)
        .map(RequestAnnotation::findByQualifiedName)
        .filter(Objects::nonNull)
        .findAny()
        .map(RequestAnnotation::getType)
        .orElse(null);
  }
  
}
