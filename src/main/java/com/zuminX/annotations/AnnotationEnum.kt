package com.zuminX.annotations

/**
 * 枚举类型的注解属性
 */
abstract class AnnotationEnum(value: String) {

  private var value: String? = value

  /**
   * 获取该类型的所有可能值
   *
   * @return 所有属性值
   */
  abstract fun getAll(): List<AnnotationEnum>

  /**
   * 获取默认值
   *
   * @return 默认值
   */
  open fun getDefault() = getAll()[0]

  /**
   * 获取该枚举类型的简单类名
   *
   * @return 简单类名
   */
  open fun getClassName(): String = javaClass.simpleName

  /**
   * 获取该对象的注解表示形式
   *
   * @return 表示形式
   */
  open fun getExpression() = "${getClassName()}.${value}"
}