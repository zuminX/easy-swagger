package com.zuminX.utils.builder;

import cn.hutool.core.util.ArrayUtil;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.annotations.swagger.ApiImplicitParam;
import com.zuminX.annotations.swagger.ApiImplicitParams;
import com.zuminX.interceptor.AnnotationGeneratorInterceptor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ApiImplicitParams注解的生成器类
 */
public class ApiImplicitParamsGenerator implements AnnotationGenerator<PsiMethod, AnnotationStr> {

  public static final ApiImplicitParamsGenerator INSTANCE = AnnotationGeneratorInterceptor.create(ApiImplicitParamsGenerator.class, ApiImplicitParams.class);

  /**
   * 根据psi方法对象构建注解字符串对象
   *
   * @param psiMethod psi方法
   * @return 注解字符串对象
   */
  public final AnnotationStr build(PsiMethod psiMethod) {
    // 根据方法的参数个数选择生成的注解类型
    int parametersCount = psiMethod.getParameterList().getParametersCount();
    // 若无参数，则不生成
    if (parametersCount == 0) {
      return null;
    }
    // 若仅有一个参数，则生成单一的@ApiImplicitParam
    if (parametersCount == 1) {
      PsiParameter parameter = psiMethod.getParameterList().getParameter(0);
      return ApiImplicitParamGenerator.INSTANCE.build(parameter);
    }
    // 若有多个参数，则生成复合注解@ApiImplicitParams
    PsiParameter[] psiParameters = psiMethod.getParameterList().getParameters();
    if (ArrayUtil.isEmpty(psiParameters)) {
      return null;
    }
    return ApiImplicitParams.builder().value(getValue(psiParameters)).build();
  }

  /**
   * 从psi参数数组中获取ApiImplicitParams类的value属性
   *
   * @param psiParameters psi参数数组
   * @return ApiImplicitParams类的value属性
   */
  protected List<ApiImplicitParam> getValue(PsiParameter[] psiParameters) {
    return Arrays.stream(psiParameters)
        .map(ApiImplicitParamGenerator.INSTANCE::build)
        .collect(Collectors.toList());
  }

}
