package com.zuminX.utils.builder;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.zuminX.annotations.swagger.ApiModel;
import com.zuminX.annotations.swagger.ApiModelProperty;
import com.zuminX.interceptor.AnnotationBuilderInterceptor;
import com.zuminX.utils.GeneratorUtils;
import org.jetbrains.annotations.Nullable;

public class ApiModelPropertyBuilder {

  public static final ApiModelPropertyBuilder BUILDER = AnnotationBuilderInterceptor.create(ApiModelPropertyBuilder.class, ApiModelProperty.class);

  public final ApiModelProperty build(PsiField psiField) {
    return ApiModelProperty.builder().value(getValue(psiField)).build();
  }

  protected String getValue(PsiField psiField) {
    return GeneratorUtils.getFirstComment(psiField);
  }
}
