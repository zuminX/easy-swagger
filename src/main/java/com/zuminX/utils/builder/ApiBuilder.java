package com.zuminX.utils.builder;

import com.intellij.psi.PsiClass;
import com.zuminX.annotations.swagger.Api;
import com.zuminX.interceptor.AnnotationBuilderInterceptor;
import com.zuminX.utils.GeneratorUtils;
import java.util.Collections;
import java.util.List;

public class ApiBuilder {

  public static final ApiBuilder BUILDER = AnnotationBuilderInterceptor.create(ApiBuilder.class, Api.class);

  public final Api build(PsiClass psiClass) {
    return Api.builder().value(getValue(psiClass)).tags(getTags(psiClass)).build();
  }

  protected String getValue(PsiClass psiClass) {
    return GeneratorUtils.getValueOfRequestMapping(psiClass);
  }

  protected List<String> getTags(PsiClass psiClass) {
    String comment = GeneratorUtils.getFirstComment(psiClass);
    return comment == null ? null : Collections.singletonList(comment);
  }
}
