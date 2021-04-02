package com.zuminX.interceptor;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.zuminX.annotations.AnnotationStr;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

@AllArgsConstructor
public class AnnotationBuilderInterceptor implements MethodInterceptor {

  private final Class<? extends AnnotationStr> annotationClazz;

  public static <T> T create(Class<T> target, Class<? extends AnnotationStr> annotationClazz) {
    AnnotationBuilderInterceptor interceptor = new AnnotationBuilderInterceptor(annotationClazz);
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
    return methodProxy.invokeSuper(o, objects);
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
