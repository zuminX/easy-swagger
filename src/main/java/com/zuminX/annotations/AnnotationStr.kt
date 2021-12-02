package com.zuminX.annotations

import cn.hutool.core.util.ReflectUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.core.util.TypeUtil
import com.zuminX.names.ClassName
import com.zuminX.utils.isNumOrBool
import com.zuminX.utils.wrapInCurlyBraces
import com.zuminX.utils.wrapInDoubleQuotes
import java.lang.reflect.Field
import java.util.function.Predicate

/**
 * 注解字符串类
 */
abstract class AnnotationStr {

  /**
   * 获取该注解的类名对象
   *
   * @return 类名对象
   */
  abstract fun getClassName(): ClassName

  /**
   * 获取无内容的注解字符串
   *
   * @return 注解字符串
   */
  open fun empty() = "@${getClassName().getSimpleName()}"

  /**
   * 获取注解字符串
   *
   * @param <T> 父类或超类是注解字符串类的类
   * @return 注解字符串
   */
  @Suppress("UNCHECKED_CAST")
  open fun toStr(): String {
    val fields = getSortAndShowFields()
    if (fields.isEmpty()) {
      return empty()
    }
    val sb: StringBuilder = StringBuilder(empty())
    sb.append("(")
    for (field in fields) {
      field.isAccessible = true
      val value = field[this]
      var content: String
      when (value) {
        null -> {
          val defaultText = field.getAnnotationAttr()?.defaultText ?: ""
          content = if (defaultText == "") field.type.getDefaultTextByType() else defaultText
        }
        this::isNumOrBool -> content = value.toString()
        is List<*> -> {
          val valueClass = TypeUtil.getTypeArgument(field.genericType) as Class<*>
          if (AnnotationStr::class.java.isAssignableFrom(valueClass)) {
            sb.append((value as List<AnnotationStr>).listDeepToStr().wrapInCurlyBraces())
            continue
          }
          content = (value as List<Any>).listToStr().wrapInCurlyBraces()
        }
        is ClassName -> content = value.getClassName()
        is AnnotationEnum -> content = value.getExpression()
        else -> content = value.wrapInDoubleQuotes()
      }
      sb.append("${field.name} = ${content}, ")
    }
    return "${StrUtil.removeSuffix(sb, ", ")})"
  }

  /**
   * 生成列表中所有AnnotationStr对象的注解字符串
   *
   * @param value AnnotationStr对象列表
   * @return 注解字符串
   */
  private fun List<AnnotationStr>.listDeepToStr(): String {
    return this.joinToString(",\n", "\n", "\n") { it.toStr() }
  }

  /**
   * 生成列表中所有对象的注解字符串
   *
   * @return 注解字符串
   */
  private fun List<Any>.listToStr(): String {
    return joinToString(", ") {
      when (it) {
        this::isNumOrBool -> it.toString()
        is ClassName -> it.getClassName()
        is AnnotationEnum -> it.getExpression()
        else -> it.wrapInDoubleQuotes()
      }
    }
  }

  /**
   * 根据类型获取对应的默认文本
   *
   * @param clazz 类型
   * @return 默认文本
   */
  private fun Class<*>.getDefaultTextByType(): String {
    return when {
      Number::class.java.isAssignableFrom(this) -> "0"
      this == Boolean::class.java -> "false"
      this == List::class.java -> "".wrapInCurlyBraces()
      this == ClassName::class.java -> "Void.class"
      AnnotationEnum::class.java.isAssignableFrom(this) ->
        ReflectUtil.newInstance<AnnotationEnum>(name).getDefault().getExpression()
      else -> "".wrapInDoubleQuotes()
    }
  }
}

/**
 * 获取指定字段上的AnnotationAttr注解
 *
 * @return AnnotationAttr注解
 */
fun Field.getAnnotationAttr(): AnnotationAttr? {
  return annotations.filterIsInstance<AnnotationAttr>().firstOrNull()
}

/**
 * 获取AnnotationStr的子类对象的排序字段
 *
 * @return 排序后的字段列表
 */
fun AnnotationStr.getSortFields(): List<Field> {
  return getSortFields { _ -> true }
}

/**
 * 获取AnnotationStr的子类对象的排序且显示的字段
 *
 * @return 排序后的显示字段列表
 */
fun AnnotationStr.getSortAndShowFields(): List<Field> {
  return getSortFields(AnnotationAttr::show)
}

/**
 * 获取AnnotationStr的子类对象的排序字段，并使用predicate进行条件过滤
 *
 * @param predicate     条件过滤
 * @return 排序后且符合过滤条件的字段列表
 */
fun AnnotationStr.getSortFields(predicate: Predicate<AnnotationAttr>): List<Field> {
  return javaClass.declaredFields
    .filter {
      val annotationAttr = it.getAnnotationAttr()
      annotationAttr != null && predicate.test(annotationAttr)
    }
    .sortedWith(Comparator.comparingInt { it.getAnnotationAttr()!!.sort })
}