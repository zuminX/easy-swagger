package com.zuminX.window

import com.zuminX.settings.Settings

/**
 * 选项类
 */
interface Option {

  /**
   * 显示设置
   *
   * @param setting 设置信息
   */
  fun showSetting(setting: Settings)

  /**
   * 应用设置
   *
   * @param setting 设置信息
   */
  fun applySetting(setting: Settings)
}