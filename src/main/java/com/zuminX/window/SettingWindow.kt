package com.zuminX.window

import com.intellij.util.ui.FormBuilder
import com.zuminX.settings.Settings
import javax.swing.JPanel

/**
 * 设置页视图
 */
class SettingWindow() {
  val content: JPanel
  private val optionList: MutableList<Option> = mutableListOf()

  init {
    val builder = FormBuilder.createFormBuilder()
    Settings.getAllSettingItems().forEach {
      builder.addComponent(it.form.getContent(), VERTICAL_CLEARANCE)
      optionList.addAll(it.options)
    }
    content = builder.addComponentFillVertically(JPanel(), VERTICAL_CLEARANCE).panel
  }

  /**
   * 获取应用设置
   *
   * @return 设置
   */
  fun getAppSetting(): Settings {
    val setting = Settings()
    optionList.forEach { it.applySetting(setting) }
    return setting
  }

  /**
   * 应用设置
   *
   * @param setting 设置
   */
  fun applySetting(setting: Settings?) {
    if (setting == null) {
      return
    }
    optionList.forEach { it.showSetting(setting) }
  }
}

const val VERTICAL_CLEARANCE = 30