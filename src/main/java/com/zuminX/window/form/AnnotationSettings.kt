package com.zuminX.window.tabs

import cn.hutool.core.util.ReflectUtil
import com.zuminX.annotations.AnnotationStr
import com.zuminX.annotations.getAnnotationAttr
import com.zuminX.annotations.getSortFields
import com.zuminX.annotations.swagger.*

/**
 * 注解项
 */
data class AnnotationItem(
  var name: String = "",
  var defaultText: String = "",
  var sort: Int = Int.MAX_VALUE,
  var show: Boolean = false,
)

/**
 * 注解设置信息类
 */
class AnnotationSettings(val map: Map<String, List<AnnotationItem>>? = null) {

  /**
   * 获取完整的注解设置信息
   *
   * @param defaultMap 注解设置信息的默认项
   * @return key为注解名，value为注解项集合的映射
   */
  fun getFullMap(defaultMap: Map<String, List<AnnotationItem>>): Map<String, List<AnnotationItem>> {
    // TODO 临时解决始终使用旧设置信息的问题
    val result = mutableMapOf<String, List<AnnotationItem>>()
    defaultMap.forEach { (key, value) ->
      val annotationItems = map!![key]
      if (annotationItems == null) {
        result[key] = value
        return@forEach
      }
      val itemNameMap = annotationItems.associateBy { it.name }
      val itemList = value.map { itemNameMap.getOrDefault(it.name, it) }
      result[key] = itemList
    }
    return result
  }
}

private val MAIN_ANNOTATIONS = listOf(
  Api::class.java, ApiImplicitParam::class.java, ApiImplicitParams::class.java,
  ApiModel::class.java, ApiModelProperty::class.java, ApiOperation::class.java)
private val PROPERTY_ANNOTATIONS = listOf(
  Authorization::class.java, AuthorizationScope::class.java, Example::class.java,
  ExampleProperty::class.java, Extension::class.java, ExtensionProperty::class.java, ResponseHeader::class.java)

val MAIN_ANNOTATION_DEFAULT_MAP = MAIN_ANNOTATIONS.toMap()
val PROPERTY_ANNOTATION_DEFAULT_MAP = PROPERTY_ANNOTATIONS.toMap()

/**
 * 获取注解设置信息的默认项
 *
 * @return 默认项
 */
private fun List<Class<out AnnotationStr>>.toMap(): Map<String, List<AnnotationItem>> {
  return associate { it.name to ReflectUtil.newInstance(it).getAnnotationItemList() }
}

/**
 * 根据注解字符串类获取注解设置信息列表
 *
 * @return 注解设置信息列表
 */
private fun AnnotationStr.getAnnotationItemList(): List<AnnotationItem> {
  return getSortFields().map {
    val annotationAttr = it.getAnnotationAttr()!!
    AnnotationItem(it.name, annotationAttr.defaultText, annotationAttr.sort, annotationAttr.show)
  }
}