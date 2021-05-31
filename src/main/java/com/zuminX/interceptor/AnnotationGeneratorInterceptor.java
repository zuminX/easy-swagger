package com.zuminX.interceptor;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.domain.GeneratorPsi;
import com.zuminX.utils.GeneratorUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class AnnotationGeneratorInterceptor implements MethodInterceptor {

  private final Class<? extends AnnotationStr> annotationClazz;

  public AnnotationGeneratorInterceptor(Class<? extends AnnotationStr> annotationClazz) {
    this.annotationClazz = annotationClazz;
  }

  public static <T> T create(Class<T> target, Class<? extends AnnotationStr> annotationClazz) {
    AnnotationGeneratorInterceptor interceptor = new AnnotationGeneratorInterceptor(annotationClazz);
    return (T) interceptor.cglibProxyGenerator(target);
  }

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
    String name = method.getName();
    if (name.startsWith("get")) {
      String fieldName = StrUtil.lowerFirst(name.substring(3));
      Field field = ClassUtil.getDeclaredField(annotationClazz, fieldName);
      if (field != null && !AnnotationStr.getAnnotationAttr(field).show()) {
        return null;
      }
    }
    Object result = methodProxy.invokeSuper(o, objects);
    if (name.equals("add") && result != null) {
      GeneratorUtils.doWrite((AnnotationStr) result, (GeneratorPsi<?>) objects[0]);
    }
    return result;
  }

  /**
   * 用于生成Cglib动态代理类
   *
   * @param target 被代理的委托类的Class对象
   * @return 动态代理类
   */
  private Object cglibProxyGenerator(Class<?> target) {
    //创建加强类，用来创建动态代理类
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(target);
    enhancer.setCallback(this);
    return enhancer.create();
  }
}
