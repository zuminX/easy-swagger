package com.zuminX.annotations

/**
 * 生成注解的配置属性
 */
@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class AnnotationAttr(
  /**
   * 生成注解属性时的默认文本
   * 当前仅当生成该注解属性且该属性的值为null时使用
   */
  val defaultText: String = "",

  /**
   * 控制生成注解属性的先后顺序
   * 排序值越小，显示越靠前
   */
  val sort: Int = Int.MAX_VALUE,

  /**
   * 控制是否生成该注解属性
   */
  val show: Boolean = false,
)