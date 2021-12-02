package com.zuminX.generator.builder

import com.intellij.psi.PsiField
import com.zuminX.annotations.swagger.ApiModelProperty
import com.zuminX.generator.AnnotationGenerator
import com.zuminX.generator.AnnotationGeneratorInterceptor
import com.zuminX.utils.getFirstComment

val ApiModelPropertyGeneratorInstance =
  AnnotationGeneratorInterceptor.create(ApiModelPropertyGenerator::class.java, ApiModelProperty::class.java)

/**
 * ApiModelProperty注解的生成器类
 */
open class ApiModelPropertyGenerator : AnnotationGenerator<PsiField, ApiModelProperty> {

  /**
   * 根据psi字段对象构建ApiModelProperty对象
   *
   * @param psiElement psi字段
   * @return ApiModelProperty注解对象
   */
  final override fun build(psiElement: PsiField): ApiModelProperty {
    return ApiModelProperty().apply { value = getValue(psiElement) }
  }

  /**
   * 从psi字段中获取ApiModel类的value属性
   *
   * @param psiField psi字段
   * @return ApiModelProperty类的value属性
   */
  protected fun getValue(psiField: PsiField): String? {
    return psiField.getFirstComment()
  }
}