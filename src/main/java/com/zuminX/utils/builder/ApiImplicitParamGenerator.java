package com.zuminX.utils.builder;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiParameter;
import com.zuminX.annotations.swagger.ApiImplicitParam;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.names.ClassName;
import com.zuminX.names.RequestAnnotation;
import com.zuminX.utils.GeneratorUtils;
import com.zuminX.utils.PublicUtils;
import java.util.Arrays;
import java.util.Objects;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class ApiImplicitParamGenerator implements AnnotationGenerator<PsiParameter, ApiImplicitParam> {

  public static final ApiImplicitParamGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiImplicitParamGenerator.class, ApiImplicitParam.class);

  public final ApiImplicitParam build(PsiParameter psiParameter) {
    return ApiImplicitParam.builder()
        .paramType(getParamType(psiParameter))
        .dataType(getDataType(psiParameter))
        .dataTypeClass(getDataTypeClass(psiParameter))
        .name(getName(psiParameter))
        .value(getValue(psiParameter))
        .build();
  }

  protected String getName(PsiParameter psiParameter) {
    return psiParameter.getNameIdentifier().getText();
  }

  protected String getDataType(PsiParameter psiParameter) {
    return PublicUtils.getSimpleNameByQualifiedName(getParameterType(psiParameter));
  }

  @SneakyThrows
  protected ClassName getDataTypeClass(PsiParameter psiParameter) {
    return new ClassName(getParameterType(psiParameter));
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

  @NotNull
  private String getParameterType(PsiParameter psiParameter) {
    return psiParameter.getType().getCanonicalText();
  }

}
