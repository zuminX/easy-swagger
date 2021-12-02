package com.zuminX.generator

import com.intellij.psi.PsiModifierListOwner
import com.zuminX.annotations.AnnotationStr
import com.zuminX.utils.ProjectPsi

/**
 * 注解生成器
 *
 * @param <T> psi元素类型
 * @param <V> 注解类型
 */
interface AnnotationGenerator<T : PsiModifierListOwner, V : AnnotationStr> {

  /**
   * 根据psi元素构建指定的注解
   *
   * @param psiElement psi元素
   * @return 注解实例
   */
  fun build(psiElement: T): V?

  /**
   * 根据psi元素给项目添加注解
   *
   * @param projectPsi 项目psi元素
   * @return 注解实例
   */
  fun add(projectPsi: ProjectPsi<T>) = build(projectPsi.element)

}