package com.zuminX.utils.builder;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.zuminX.annotations.swagger.ApiOperation;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.utils.GeneratorUtils;

public class ApiOperationGenerator implements AnnotationGenerator<PsiMethod, ApiOperation> {

  public static final ApiOperationGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiOperationGenerator.class, ApiOperation.class);

  public final ApiOperation build(PsiMethod psiMethod) {
    PsiAnnotation apiOperationExist = psiMethod.getModifierList().findAnnotation(SwaggerAnnotation.API_OPERATION.getQualifiedName());
    return ApiOperation.builder()
        .value(getValue(psiMethod))
        .notes(getNotes(apiOperationExist))
        .httpMethod(getHttpMethod(psiMethod)).build();
  }

  protected String getHttpMethod(PsiMethod psiMethod) {
    return GeneratorUtils.getMethodOfRequestMapping(psiMethod);
  }

  protected String getNotes(PsiAnnotation apiOperationExist) {
    return GeneratorUtils.getTextOfAnnotationMemberValue(apiOperationExist, "notes");
  }

  protected String getValue(PsiMethod psiMethod) {
    return GeneratorUtils.getFirstComment(psiMethod);
  }

}
