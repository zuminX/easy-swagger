package com.zuminX.settings

import com.zuminX.config.systemSettingInstance
import com.zuminX.window.Option

/**
 * 设置项的键
 *
 * @param <T> 值类型
 */
class SettingKey<T>(name: String, defaultData: T) : Key<T>(name, defaultData) {

  var option: Option? = null

  /**
   * 获取数据
   *
   * @return 数据
   */
  fun getData(): T {
    return systemSettingInstance.setting.getData(this)
  }

  /**
   * 设置数据
   *
   * @param data 数据
   */
  fun setData(data: T) {
    return systemSettingInstance.setting.putData(this, data!!)
  }
}