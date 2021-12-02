package com.zuminX.window.form

import cn.hutool.core.annotation.AnnotationUtil
import com.zuminX.annotations.getAnnotationAttr
import com.zuminX.service.Information
import com.zuminX.settings.SettingKey
import com.zuminX.window.OptionForm
import com.zuminX.window.tabs.*

/**
 * Swagger注解设置表单类
 */
class AnnotationForm : OptionForm(Information.message("settings.annotation.form.title"), 0) {

  companion object {
    @JvmField
    val ANNOTATION_PROPERTY_SETTINGS = getSettingKey("Swagger Annotation Property Form",
      PROPERTY_ANNOTATION_DEFAULT_MAP)

    @JvmField
    val ANNOTATION_MAIN_SETTINGS = getSettingKey("Swagger Annotation Main Form",
      MAIN_ANNOTATION_DEFAULT_MAP)

    /**
     * 加载设置数据
     */
    @JvmStatic
    fun loadSettingsData() {
      ANNOTATION_MAIN_SETTINGS.loadSettingsData();
      ANNOTATION_PROPERTY_SETTINGS.loadSettingsData();
    }

    /**
     * 获取设置键
     *
     * @param name       名称
     * @param defaultMap 默认项
     * @return Swagger注解设置信息类的设置键
     */
    @JvmStatic
    internal fun getSettingKey(name: String, defaultMap: Map<String, List<AnnotationItem>>): SettingKey<AnnotationSettings> {
      return SettingKey(name, AnnotationSettings(defaultMap)).apply { option = AnnotationTabbedPane(this, defaultMap) }
    }
  }
}

/**
 * 加载设置数据
 */
private fun SettingKey<AnnotationSettings>.loadSettingsData() {
  for (entry in getData().map!!.entries) {
    val clazz = Class.forName(entry.key)
    var sort = 1
    for (annotationItem in entry.value) {
      val field = clazz.getDeclaredField(annotationItem.name)
      val annotation = field.getAnnotationAttr()!!
      AnnotationUtil.setValue(annotation, "defaultText", annotationItem.defaultText)
      AnnotationUtil.setValue(annotation, "sort", sort++)
      AnnotationUtil.setValue(annotation, "show", annotationItem.show)
    }
  }
}
