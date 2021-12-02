package com.zuminX.settings

import com.zuminX.window.Option
import com.zuminX.window.OptionForm
import javax.swing.JComponent

/**
 * 设置项
 */
class SettingItem(val form: OptionForm, val options: MutableList<Option> = mutableListOf()) {

  /**
   * 添加选项
   *
   * @param option 选项
   * @return 当前对象
   */
  fun addOption(option: Option): SettingItem {
    options.add(option)
    if (option is JComponent) {
      form.addOptionItem(option)
    }
    return this
  }
}