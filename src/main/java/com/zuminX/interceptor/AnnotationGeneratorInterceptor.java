package com.zuminX.interceptor;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.domain.ProjectPsi;
import com.zuminX.utils.GeneratorUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 注解生成器的拦截器
 */
public class AnnotationGeneratorInterceptor implements MethodInterceptor {

  private final Class<? extends AnnotationStr> annotationClazz;

  public AnnotationGeneratorInterceptor(Class<? extends AnnotationStr> annotationClazz) {
    this.annotationClazz = annotationClazz;
  }

  /**
   * 创建被代理的生成器对象
   *
   * @param target          目标代理类型
   * @param annotationClazz 生成器的注解类型
   * @param <T>             生成器类
   * @return 生成器对象
   */
  @SuppressWarnings("unchecked")
  public static <T> T create(Class<T> target, Class<? extends AnnotationStr> annotationClazz) {
    AnnotationGeneratorInterceptor interceptor = new AnnotationGeneratorInterceptor(annotationClazz);
    return (T) interceptor.cglibProxyGenerator(target);
  }

  /**
   * 对注解生成器对象的方法调用进行拦截处理
   *
   * @param o           对象
   * @param method      方法
   * @param objects     参数
   * @param methodProxy 方法代理
   * @return 方法返回值
   * @throws Throwable 方法异常
   */
  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
    String name = method.getName();
    if (name.startsWith("get") && isNotShowField(StrUtil.lowerFirst(name.substring(3)))) {
      return null;
    }
    Object result = methodProxy.invokeSuper(o, objects);
    if (name.equals("add") && result != null) {
      GeneratorUtils.doWrite((AnnotationStr) result, (ProjectPsi<?>) objects[0]);
    }
    return result;
  }

  /**
   * 判断该字段是否不显示
   *
   * @param fieldName 字段名称
   * @return 若不显示则返回true，否则返回false
   */
  private boolean isNotShowField(String fieldName) {
    Field field = ClassUtil.getDeclaredField(annotationClazz, fieldName);
    return field != null && !AnnotationStr.getAnnotationAttr(field).show();
  }

  /**
   * 用于生成Cglib动态代理类
   *
   * @param target 被代理的委托类的Class对象
   * @return 动态代理类
   */
  private Object cglibProxyGenerator(Class<?> target) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(target);
    enhancer.setCallback(this);
    return enhancer.create();
  }
}
