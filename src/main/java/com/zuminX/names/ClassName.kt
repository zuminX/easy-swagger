package com.zuminX.names

import com.zuminX.utils.getSimpleName

/**
 * 类名
 */
open class ClassName(val qualifiedName: String) {

  /**
   * 获取当前类名对象的简单类名
   *
   * @return 简单类名
   */
  fun getSimpleName(): String = qualifiedName.getSimpleName()

  /**
   * 获取当前类名对象的带.class后缀名称
   *
   * @return 带.class后缀名称
   */
  fun getClassName() = "${getSimpleName()}.class"

  /**
   * 判断当前类名对象的全限定类名与name是否一致
   *
   * @param name 类名
   * @return 若相同则返回true，否则返回false
   */
  fun isEquals(name: String) = qualifiedName == name
}

val OBJECT_CLASS_NAME = ClassName("java.lang.Object")

/**
 * 查找clazz的指定类名的静态ClassName字段值
 *
 * @param qualifiedName 全限定类名
 * @return 指定类名的静态ClassName字段值
 */
fun <T : ClassName> findClassNameByQualifiedName(classNameList: List<T>, qualifiedName: String?): T? {
  if (qualifiedName == null) {
    return null
  }
  return classNameList.firstOrNull { it.isEquals(qualifiedName) }
}
