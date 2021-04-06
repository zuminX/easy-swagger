package com.zuminX.utils.builder;

import com.intellij.psi.PsiModifierListOwner;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.domain.GeneratorPsi;

public interface AnnotationGenerator<T extends PsiModifierListOwner, V extends AnnotationStr> {

  V build(T psiElement);

  default V add(GeneratorPsi<T> generatorPsi) {
    return build(generatorPsi.getElement());
  }

}
