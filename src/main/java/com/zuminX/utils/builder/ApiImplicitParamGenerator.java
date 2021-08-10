package com.zuminX.utils.builder;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.zuminX.annotations.swagger.ApiImplicitParam;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.names.RequestAnnotation;
import com.zuminX.utils.GeneratorUtils;
import com.zuminX.utils.PublicUtils;
import java.util.Arrays;
import java.util.Objects;

public class ApiImplicitParamGenerator implements AnnotationGenerator<PsiParameter, ApiImplicitParam> {

  public static final ApiImplicitParamGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiImplicitParamGenerator.class, ApiImplicitParam.class);

  public final ApiImplicitParam build(PsiParameter psiParameter) {
    return ApiImplicitParam.builder()
        .paramType(getParamType(psiParameter))
        .dataType(getDataType(psiParameter))
        .name(getName(psiParameter))
        .value(getValue(psiParameter))
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

  protected String getValue(PsiParameter psiParameter) {
    return GeneratorUtils.getFirstComment(psiParameter);
  }

}
