package com.zuminX.generator.builder

import com.intellij.psi.PsiMethod
import com.zuminX.annotations.swagger.ApiOperation
import com.zuminX.generator.AnnotationGenerator
import com.zuminX.generator.AnnotationGeneratorInterceptor
import com.zuminX.utils.getFirstComment
import com.zuminX.utils.getMethodOfRequestMapping

val ApiOperationGeneratorInstance =
  AnnotationGeneratorInterceptor.create(ApiOperationGenerator::class.java, ApiOperation::class.java)

/**
 * ApiOperation注解的生成器类
 */
open class ApiOperationGenerator : AnnotationGenerator<PsiMethod, ApiOperation> {

  /**
   * 根据psi方法对象构建ApiOperation对象
   *
   * @param psiElement psi方法
   * @return ApiOperation注解对象
   */
  override fun build(psiElement: PsiMethod): ApiOperation {
    return ApiOperation().apply {
      value = getValue(psiElement)
      httpMethod = getHttpMethod(psiElement)
    }
  }

  /**
   * 从psi方法中获取ApiOperation类的httpMethod属性
   *
   * @param psiMethod psi方法
   * @return ApiOperation类的httpMethod属性
   */
  protected fun getHttpMethod(psiMethod: PsiMethod): String? {
    return psiMethod.getMethodOfRequestMapping()
  }

  /**
   * 从psi方法中获取ApiOperation类的value属性
   *
   * @param psiMethod psi方法
   * @return ApiOperation类的value属性
   */
  protected fun getValue(psiMethod: PsiMethod): String? {
    return psiMethod.getFirstComment()
  }
}