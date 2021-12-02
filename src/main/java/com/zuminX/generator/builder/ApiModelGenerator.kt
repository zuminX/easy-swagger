package com.zuminX.generator.builder

import com.intellij.psi.PsiClass
import com.zuminX.annotations.swagger.ApiModel
import com.zuminX.generator.AnnotationGenerator
import com.zuminX.generator.AnnotationGeneratorInterceptor
import com.zuminX.names.ClassName
import com.zuminX.names.OBJECT_CLASS_NAME
import com.zuminX.utils.getFirstComment

val ApiModelGeneratorInstance = AnnotationGeneratorInterceptor.create(ApiModelGenerator::class.java, ApiModel::class.java)

/**
 * ApiModel注解的生成器类
 */
open class ApiModelGenerator : AnnotationGenerator<PsiClass, ApiModel> {

  /**
   * 根据psi类对象构建ApiModel对象
   *
   * @param psiElement psi类
   * @return ApiModel注解对象
   */
  final override fun build(psiElement: PsiClass): ApiModel {
    return ApiModel().apply {
      value = getValue(psiElement)
      description = getDescription(psiElement)
      parent = getParent(psiElement)
      subTypes = getSubTypes(psiElement)
    }
  }

  /**
   * 从psi类中获取ApiModel类的value属性
   *
   * @param psiClass psi类
   * @return ApiModel类的value属性
   */
  protected fun getValue(psiClass: PsiClass): String? {
    return psiClass.name
  }

  /**
   * 从psi类中获取ApiModel类的description属性
   *
   * @param psiClass psi类
   * @return ApiModel类的description属性
   */
  protected fun getDescription(psiClass: PsiClass): String? {
    return psiClass.getFirstComment()
  }

  /**
   * 从psi类中获取ApiModel类的parent属性
   *
   * @param psiClass psi类
   * @return ApiModel类的parent属性
   */
  protected fun getParent(psiClass: PsiClass): ClassName {
    val superClass = psiClass.superClass ?: return OBJECT_CLASS_NAME
    return ClassName(superClass.qualifiedName ?: "")
  }

  /**
   * 从psi类中获取ApiModel类的subTypes属性
   *
   * @param psiClass psi类
   * @return ApiModel类的subTypes属性
   */
  protected fun getSubTypes(psiClass: PsiClass): List<ClassName> {
    val result: MutableList<ClassName> = ArrayList()
    var nowClass: PsiClass = psiClass
    while (nowClass.superClass != null) {
      if (OBJECT_CLASS_NAME.qualifiedName != nowClass.superClass!!.qualifiedName) {
        result.add(getParent(nowClass))
      }
      nowClass = nowClass.superClass!!
    }
    // 当且仅当该类无显式父类时，才视为其父类包括Object
    return if (result.isEmpty()) listOf(OBJECT_CLASS_NAME) else result
  }

}