package com.zuminX.settings

import cn.hutool.core.util.ReflectUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.zuminX.config.systemSettingInstance
import com.zuminX.utils.getProjectClasses
import com.zuminX.window.Option
import com.zuminX.window.OptionForm
import javax.swing.JComponent

/**
 * 设置类
 */
class Settings {

  /**
   * 数据存储
   */
  var properties = mutableMapOf<String, String>()

  /**
   * 根据设置键获取数据
   *
   * @param key 设置键
   * @param <T> 数据类型
   * @return 数据
   */
  fun <T> getData(key: SettingKey<T>): T {
    val value = properties[key.name]
    val defaultValue = key.defaultData
    if (value == null) {
      return defaultValue
    }
    return ObjectMapper().readValue(value, defaultValue!!::class.java) as T
  }

  /**
   * 初始化值
   */
  fun initValue() {
    ALL_KEYS.values.forEach { putData(it, it.defaultData!!) }
  }

  /**
   * 存放数据
   * 以JSON形式存放数据
   *
   * @param key   键
   * @param value 值
   * @param <T>   数据类型
   */
  fun putData(key: Key<*>, value: Any) {
    properties[key.name] = ObjectMapper().writeValueAsString(value)
  }

  /**
   * 应用设置
   *
   * @param setting 设置对象
   */
  fun applySetting(setting: Settings?) {
    if (setting == null) {
      return
    }
    properties.putAll(setting.properties)
    systemSettingInstance.setting = this
  }

  /**
   * 判断设置是否被修改
   *
   * @param settings 设置
   * @return 若被修改则返回true，否则返回false
   */
  fun isModified(settings: Settings?): Boolean {
    if (settings == null) {
      return false
    }
    return settings.properties.any { (key, value) -> value != properties[key] }
  }

  companion object {
    /**
     * 获取所有设置项
     *
     * @return 设置项列表
     */
    @JvmStatic
    fun getAllSettingItems(): List<SettingItem> {
      return OptionForm::class.java.getProjectClasses()
        .map { clazz ->
          val item = SettingItem(ReflectUtil.newInstance(clazz))
          clazz.declaredFields
            .filter { it.type == SettingKey::class.java }
            .map { it.get(null) as SettingKey<*> }
            .map(SettingKey<*>::option)
            .filterIsInstance<JComponent>()
            .forEach { item.addOption(it as Option) }
          item
        }
        .sortedWith(Comparator.comparingInt { it.form.sort })
    }
  }
}