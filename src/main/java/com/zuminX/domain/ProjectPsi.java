package com.zuminX.domain;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.util.PsiTreeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 携带项目信息的psi元素类
 *
 * @param <T> psi元素类型
 */
@Getter
@AllArgsConstructor
public class ProjectPsi<T extends PsiModifierListOwner> {

  private final PsiFile psiFile;

  private final T element;

  public static ProjectPsi<PsiClass> build(PsiFile psiFile) {
    return new ProjectPsi<>(psiFile, PsiTreeUtil.findChildOfAnyType(psiFile, PsiClass.class));
  }

  public <V extends PsiModifierListOwner> ProjectPsi<V> replace(V element) {
    return new ProjectPsi<V>(psiFile, element);
  }

  public Project getProject() {
    return psiFile.getProject();
  }
}
