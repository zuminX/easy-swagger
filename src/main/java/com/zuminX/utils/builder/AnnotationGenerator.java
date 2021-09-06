package com.zuminX.utils.builder;

import com.intellij.psi.PsiModifierListOwner;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.domain.ProjectPsi;

/**
 * 注解生成器
 *
 * @param <T> psi元素类型
 * @param <V> 注解类型
 */
public interface AnnotationGenerator<T extends PsiModifierListOwner, V extends AnnotationStr> {

  /**
   * 根据psi元素构建指定的注解
   *
   * @param psiElement psi元素
   * @return 注解实例
   */
  V build(T psiElement);

  /**
   * 根据psi元素给项目添加注解
   *
   * @param projectPsi 项目psi元素
   * @return 注解实例
   */
  default V add(ProjectPsi<T> projectPsi) {
    return build(projectPsi.getElement());
  }

}
