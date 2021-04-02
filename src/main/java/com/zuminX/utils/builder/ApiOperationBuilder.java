package com.zuminX.utils.builder;

import cn.hutool.core.convert.Convert;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.zuminX.annotations.swagger.ApiOperation;
import com.zuminX.interceptor.AnnotationBuilderInterceptor;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.utils.GeneratorUtils;

public class ApiOperationBuilder {

  public static final ApiOperationBuilder BUILDER = AnnotationBuilderInterceptor.create(ApiOperationBuilder.class, ApiOperation.class);

  public final ApiOperation build(PsiMethod psiMethod) {
    PsiAnnotation apiOperationExist = psiMethod.getModifierList().findAnnotation(SwaggerAnnotation.API_OPERATION.getQualifiedName());
    return ApiOperation.builder()
        .value(getValue(apiOperationExist))
        .notes(getNotes(apiOperationExist))
        .httpMethod(getHttpMethod(psiMethod)).build();
  }

  protected String getHttpMethod(PsiMethod psiMethod) {
    return GeneratorUtils.getMethodOfRequestMapping(psiMethod);
  }

  protected String getNotes(PsiAnnotation apiOperationExist) {
    return GeneratorUtils.getTextOfAnnotationMemberValue(apiOperationExist, "notes");
  }

  protected String getValue(PsiAnnotation apiOperationExist) {
    return Convert.toStr(GeneratorUtils.getTextOfAnnotationMemberValue(apiOperationExist, "value"), "");
  }

}
