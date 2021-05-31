package com.zuminX.domain;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * 生成Swagger注解的PSI元素
 *
 * @param <T>
 */
public class GeneratorPsi<T extends PsiModifierListOwner> {

  private final PsiFile psiFile;

  private final T element;

  public GeneratorPsi(PsiFile psiFile, T element) {
    this.psiFile = psiFile;
    this.element = element;
  }

  public <V extends PsiModifierListOwner> GeneratorPsi<V> replace(V element) {
    return new GeneratorPsi<V>(psiFile, element);
  }

  public static GeneratorPsi<PsiClass> build(PsiFile psiFile) {
    return new GeneratorPsi<>(psiFile, PsiTreeUtil.findChildOfAnyType(psiFile, PsiClass.class));
  }

  public Project getProject() {
    return psiFile.getProject();
  }

  public PsiFile getPsiFile() {
    return this.psiFile;
  }

  public T getElement() {
    return this.element;
  }
}
