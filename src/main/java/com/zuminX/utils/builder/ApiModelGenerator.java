package com.zuminX.utils.builder;

import com.intellij.psi.PsiClass;
import com.zuminX.annotations.swagger.ApiModel;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.names.ClassName;
import com.zuminX.utils.GeneratorUtils;
import java.util.ArrayList;
import java.util.List;

public class ApiModelGenerator implements AnnotationGenerator<PsiClass, ApiModel> {

  public static final ApiModelGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiModelGenerator.class, ApiModel.class);

  public final ApiModel build(PsiClass psiClass) {
    return ApiModel.builder()
        .value(getValue(psiClass))
        .description(getDescription(psiClass))
        .parent(getParent(psiClass))
        .subTypes(getSubTypes(psiClass))
        .build();
  }

  protected String getValue(PsiClass psiClass) {
    return psiClass.getName();
  }

  protected String getDescription(PsiClass psiClass) {
    return GeneratorUtils.getFirstComment(psiClass);
  }

  protected ClassName getParent(PsiClass psiClass) {
    PsiClass superClass = psiClass.getSuperClass();
    return superClass == null ? ClassName.OBJECT_CLASS_NAME : new ClassName(superClass.getQualifiedName());
  }

  protected List<ClassName> getSubTypes(PsiClass psiClass) {
    List<ClassName> result = new ArrayList<>();
    PsiClass nowClass = psiClass;
    while (nowClass.getSuperClass() != null) {
      if (!ClassName.OBJECT_CLASS_NAME.getQualifiedName().equals(nowClass.getSuperClass().getQualifiedName())) {
        result.add(getParent(nowClass));
      }
      nowClass = nowClass.getSuperClass();
    }
    // 当且仅当该类无显式父类时，才视为其父类包括Object
    return result.isEmpty() ? List.of(ClassName.OBJECT_CLASS_NAME) : result;
  }
}
