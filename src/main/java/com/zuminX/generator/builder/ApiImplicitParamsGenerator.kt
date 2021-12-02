package com.zuminX.generator.builder

import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiParameter
import com.zuminX.annotations.AnnotationStr
import com.zuminX.annotations.swagger.ApiImplicitParam
import com.zuminX.annotations.swagger.ApiImplicitParams
import com.zuminX.generator.AnnotationGenerator
import com.zuminX.generator.AnnotationGeneratorInterceptor

val ApiImplicitParamsGeneratorInstance =
  AnnotationGeneratorInterceptor.create(ApiImplicitParamsGenerator::class.java, ApiImplicitParams::class.java)

/**
 * ApiImplicitParams注解的生成器类
 */
open class ApiImplicitParamsGenerator : AnnotationGenerator<PsiMethod, AnnotationStr> {

  /**
   * 根据psi方法对象构建注解字符串对象
   *
   * @param psiElement psi方法
   * @return 注解字符串对象
   */
  final override fun build(psiElement: PsiMethod): AnnotationStr? {
    // 根据方法的参数个数选择生成的注解类型
    return when (psiElement.parameterList.parametersCount) {
      // 若无参数，则不生成
      0 -> null
      // 若仅有一个参数，则生成单一的@ApiImplicitParam
      1 -> ApiImplicitParamGeneratorInstance.build(psiElement.parameterList.getParameter(0)!!)
      // 若有多个参数，则生成复合注解@ApiImplicitParams
      else -> {
        val psiParameters = psiElement.parameterList.parameters
        if (psiParameters.isEmpty()) {
          return null
        }
        return ApiImplicitParams().apply { value = getValue(psiParameters) }
      }
    }
  }

  /**
   * 从psi参数数组中获取ApiImplicitParams类的value属性
   *
   * @param psiParameters psi参数数组
   * @return ApiImplicitParams类的value属性
   */
  protected fun getValue(psiParameters: Array<PsiParameter>): List<ApiImplicitParam> {
    return psiParameters.map { ApiImplicitParamGeneratorInstance.build(it) }
  }
}