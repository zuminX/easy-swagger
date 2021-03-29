package com.zuminX.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 生成注解的配置属性
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationAttr {

  /**
   * 生成注解属性时的默认文本
   * <p>
   * 当前仅当生成该注解属性且该属性的值为null时使用
   *
   * @return 默认文本
   */
  String defaultText() default "";

  /**
   * 控制生成注解属性的先后顺序
   * <p>
   * 排序值越小，显示越靠前
   *
   * @return 排序值
   */
  int sort() default Integer.MAX_VALUE;

  /**
   * 控制是否生成该注解属性
   *
   * @return 是否生成
   */
  boolean show() default false;
}
