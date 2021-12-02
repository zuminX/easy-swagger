package com.zuminX.utils

import cn.hutool.core.util.ClassLoaderUtil
import cn.hutool.core.util.ClassUtil
import com.intellij.openapi.application.ApplicationManager
import com.zuminX.config.PROJECT_PACKAGE_NAME

private val DESCRIPTION_NAME = setOf("desc", "describe", "description")

private const val PARAM_NAME = "param"

/**
 * 获取指定类的实例
 *
 * @param <T>   类型
 * @return clazz对应的实例对象
 */
fun <T> Class<T>.getService(): T {
  return ApplicationManager.getApplication().getService(this)
}

/**
 * 获取项目的所有实现clazz的Class
 *
 * @return Class集合
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any?> Class<T>.getProjectClasses(): Set<Class<T>> {
  val contextClassLoader = Thread.currentThread().contextClassLoader
  // 替换类加载器，以确保能正确扫描到项目的Class
  Thread.currentThread().contextClassLoader = ClassLoaderUtil::class.java.classLoader
  val result = ClassUtil.scanPackageBySuper(PROJECT_PACKAGE_NAME, this)
  // 还原设置的类加载器
  Thread.currentThread().contextClassLoader = contextClassLoader
  return result as Set<Class<T>>
}

/**
 * 获取注释内容
 *
 * @return 注释内容
 */
fun String.getCommentDesc(): String {
  val unmarkedComment = split("\n")
    .map(String::trim)
    .filter { it.startWithAll("*", "@") == -1 }
    .map { it.removeCommentSymbol() }
  val descComment = DESCRIPTION_NAME.map { getMarkComment("*", "@${it}") }
  return (unmarkedComment + descComment).filter { it.isNotBlank() }.joinToString(" ")
}

/**
 * 获取参数注释
 *
 * @param paramName 参数名称
 * @return 注释内容
 */
fun String.getParamComment(paramName: String): String {
  return getMarkComment("*", "@${PARAM_NAME}", paramName)
}

/**
 * 获取以特定字符串开头的注释
 *
 * @param marks 开头的字符串
 * @return 注释内容
 */
fun String.getMarkComment(vararg marks: String): String {
  split("\n").map(String::trim).forEach { str ->
    val end = str.startWithAll(*marks)
    if (end != -1) {
      return str.substring(end).trim()
    }
  }
  return ""
}

/**
 * 删除注释符号
 *
 * @return 移除注释符号的字符串
 */
private fun String.removeCommentSymbol(): String {
  if (isEmpty()) {
    return ""
  }
  var start = 0
  var end = length - 1
  while (start <= end) {
    val c = this[start]
    if (c == '*' || c == '/' || c == ' ') {
      ++start
      continue
    }
    break
  }
  while (start <= end) {
    val c = this[end]
    if (c != '*' && c != '/' && c != ' ') {
      break
    }
    --end
  }
  return substring(start, end + 1)
}
