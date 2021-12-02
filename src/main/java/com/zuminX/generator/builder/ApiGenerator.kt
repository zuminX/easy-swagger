package com.zuminX.generator.builder

import com.intellij.psi.PsiClass
import com.zuminX.annotations.swagger.Api
import com.zuminX.generator.AnnotationGenerator
import com.zuminX.generator.AnnotationGeneratorInterceptor
import com.zuminX.utils.getFirstComment
import com.zuminX.utils.getValueOfRequestMapping

val ApiGeneratorInstance = AnnotationGeneratorInterceptor.create(ApiGenerator::class.java, Api::class.java)

/**
 * Api注解的生成器类
 */
open class ApiGenerator : AnnotationGenerator<PsiClass, Api> {

  /**
   * 根据psi类对象构建Api注解对象
   *
   * @param psiElement psi类
   * @return Api注解对象
   */
  final override fun build(psiElement: PsiClass): Api {
    return Api().apply {
      value = getValue(psiElement)
      tags = getTags(psiElement)
    }
  }

  /**
   * 从psi类对象中获取Api类的value属性
   *
   * @param psiClass psi类
   * @return Api类的value属性
   */
  protected fun getValue(psiClass: PsiClass): String? {
    return psiClass.getValueOfRequestMapping()
  }

  /**
   * 从psi类对象中获取Api类的tags属性
   *
   * @param psiClass psi类
   * @return Api类的tags属性
   */
  protected fun getTags(psiClass: PsiClass): List<String>? {
    val comment = psiClass.getFirstComment()
    return comment?.let { listOf(it) }
  }
}

