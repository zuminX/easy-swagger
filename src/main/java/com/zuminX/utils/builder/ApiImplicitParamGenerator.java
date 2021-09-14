package com.zuminX.utils.builder;

import cn.hutool.core.bean.OptionalBean;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiParameter;
import com.zuminX.annotations.swagger.ApiImplicitParam;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import com.zuminX.names.ClassName;
import com.zuminX.names.RequestAnnotation;
import com.zuminX.utils.PsiUtils;
import com.zuminX.utils.PublicUtils;
import java.util.Arrays;
import java.util.Objects;

/**
 * ApiImplicitParam注解的生成器类
 */
public class ApiImplicitParamGenerator implements AnnotationGenerator<PsiParameter, ApiImplicitParam> {

  public static final ApiImplicitParamGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiImplicitParamGenerator.class, ApiImplicitParam.class);

  /**
   * 根据psi参数对象构建ApiImplicitParam注解对象
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam注解对象
   */
  public final ApiImplicitParam build(PsiParameter psiParameter) {
    return ApiImplicitParam.builder()
        .paramType(getParamType(psiParameter))
        .dataType(getDataType(psiParameter))
        .dataTypeClass(getDataTypeClass(psiParameter))
        .name(getName(psiParameter))
        .value(getValue(psiParameter))
        .build();
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的name属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的name属性
   */
  protected String getName(PsiParameter psiParameter) {
    return OptionalBean.ofNullable(psiParameter.getNameIdentifier()).getBean(PsiIdentifier::getText).get();
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的dataType属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的dataType属性
   */
  protected String getDataType(PsiParameter psiParameter) {
    return PublicUtils.getSimpleNameByQualifiedName(getParameterType(psiParameter));
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的dataTypeClass属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的dataTypeClass属性
   */
  protected ClassName getDataTypeClass(PsiParameter psiParameter) {
    return new ClassName(getParameterType(psiParameter));
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的paramType属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的paramType属性
   */
  protected String getParamType(PsiParameter psiParameter) {
    PsiModifierList modifierList = psiParameter.getModifierList();
    if (modifierList == null) {
      return null;
    }
    return Arrays.stream(modifierList.getAnnotations())
        .map(PsiAnnotation::getQualifiedName)
        .map(RequestAnnotation::findByQualifiedName)
        .filter(Objects::nonNull)
        .findAny()
        .map(RequestAnnotation::getType)
        .orElse(null);
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的value属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的value属性
   */
  protected String getValue(PsiParameter psiParameter) {
    return PsiUtils.getFirstComment(psiParameter);
  }

  /**
   * 从psi参数对象中获取ApiImplicitParam类的parameterType属性
   *
   * @param psiParameter psi参数
   * @return ApiImplicitParam类的parameterType属性
   */
  private String getParameterType(PsiParameter psiParameter) {
    return psiParameter.getType().getCanonicalText();
  }

}
