package com.zuminX.generator.builder

import com.intellij.psi.PsiParameter
import com.zuminX.annotations.swagger.ApiImplicitParam
import com.zuminX.generator.AnnotationGenerator
import com.zuminX.generator.AnnotationGeneratorInterceptor
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.RequestAnnotation
import com.zuminX.utils.getFirstComment
import com.zuminX.utils.getSimpleName

val ApiImplicitParamGeneratorInstance =
  AnnotationGeneratorInterceptor.create(ApiImplicitParamGenerator::class.java, ApiImplicitParam::class.java)

/**
 * ApiImplicitParam注解的生成器类
 */
open class ApiImplicitParamGenerator :
  AnnotationGenerator<PsiParameter, ApiImplicitParam> {

  /**
   * 根据psi参数对象构建ApiImplicitParam注解对象
   *
   * @param psiElement psi参数
   * @return ApiImplicitParam注解对象
   */
  final override fun build(psiElement: PsiParameter): ApiImplicitParam {
    return ApiImplicitParam().apply {
      paramType = getParamType(psiElement)
      dataType = getDataType(psiElement)
      dataTypeClass = getDataTypeClass(psiElement)
      name = getName(psiElement)
      value = getValue(psiElement)
    }
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的name属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的name属性
   */
  protected fun getName(psiParameter: PsiParameter): String? {
    return psiParameter.nameIdentifier?.text
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的dataType属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的dataType属性
   */
  protected fun getDataType(psiParameter: PsiParameter): String {
    return getParameterType(psiParameter).getSimpleName()
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的dataTypeClass属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的dataTypeClass属性
   */
  protected fun getDataTypeClass(psiParameter: PsiParameter): ClassName {
    return ClassName(getParameterType(psiParameter))
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的paramType属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的paramType属性
   */
  protected fun getParamType(psiParameter: PsiParameter): String? {
    val modifierList = psiParameter.modifierList ?: return null
    return modifierList.annotations
      .map { it.qualifiedName }
      .mapNotNull { RequestAnnotation.findByQualifiedName(it) }
      .map { it.type }
      .firstOrNull()
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的value属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的value属性
   */
  protected fun getValue(psiParameter: PsiParameter): String? {
    return psiParameter.getFirstComment()
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的parameterType属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的parameterType属性
   */
  private fun getParameterType(psiParameter: PsiParameter): String {
    return psiParameter.type.canonicalText
  }
}