package com.zuminX.generator

import cn.hutool.core.util.ClassUtil
import cn.hutool.core.util.StrUtil
import com.zuminX.annotations.AnnotationStr
import com.zuminX.annotations.getAnnotationAttr
import com.zuminX.utils.ProjectPsi
import com.zuminX.utils.doWrite
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.MethodInterceptor
import net.sf.cglib.proxy.MethodProxy
import java.lang.reflect.Method

/**
 * 注解生成器的拦截器
 */
class AnnotationGeneratorInterceptor(private val annotationClazz: Class<out AnnotationStr>) : MethodInterceptor {

  companion object {
    /**
     * 创建被代理的生成器对象
     *
     * @param target          目标代理类型
     * @param annotationClazz 生成器的注解类型
     * @param <T>             生成器类
     * @return 生成器对象
     */
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> create(target: Class<T>, annotationClazz: Class<out AnnotationStr>): T {
      return AnnotationGeneratorInterceptor(annotationClazz).cglibProxyGenerator(target) as T
    }
  }

  /**
   * 对注解生成器对象的方法调用进行拦截处理
   *
   * @param o           对象
   * @param method      方法
   * @param objects     参数
   * @param methodProxy 方法代理
   * @return 方法返回值
   */
  override fun intercept(o: Any, method: Method, objects: Array<out Any>, methodProxy: MethodProxy): Any? {
    val name = method.name
    if (name.startsWith("get") && isNotShowField(StrUtil.lowerFirst(name.substring(3)))) {
      return null
    }
    val result = methodProxy.invokeSuper(o, objects)
    if (name == "add" && result != null) {
      (objects[0] as ProjectPsi<*>).doWrite(result as AnnotationStr)
    }
    return result
  }

  /**
   * 判断该字段是否不显示
   *
   * @param fieldName 字段名称
   * @return 若不显示则返回true，否则返回false
   */
  private fun isNotShowField(fieldName: String): Boolean {
    return ClassUtil.getDeclaredField(annotationClazz, fieldName)?.getAnnotationAttr()?.show ?: false
  }

  /**
   * 用于生成Cglib动态代理类
   *
   * @param target 被代理的委托类的Class对象
   * @return 动态代理类
   */
  private fun cglibProxyGenerator(target: Class<*>): Any {
    val enhancer = Enhancer()
    enhancer.setSuperclass(target)
    enhancer.setCallback(this)
    return enhancer.create()
  }
}