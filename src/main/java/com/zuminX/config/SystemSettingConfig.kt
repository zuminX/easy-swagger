package com.zuminX.config

import com.intellij.openapi.options.Configurable
import com.zuminX.window.SettingWindow
import javax.swing.JComponent

/**
 * 系统设置配置类
 */
class SystemSettingConfig : Configurable {

  private var settingWindow: SettingWindow? = null

  /**
   * 创建组件
   *
   * @return 设置页视图的内容
   */
  override fun createComponent(): JComponent {
    settingWindow = SettingWindow()
    return settingWindow!!.content
  }

  /**
   * 判断设置是否被修改了
   *
   * @return 若修改了则返回true，否则返回false
   */
  override fun isModified(): Boolean {
    if (settingWindow == null) {
      return false
    }
    return systemSettingInstance.isModified(settingWindow!!.getAppSetting())
  }

  /**
   * 应用设置
   */
  override fun apply() {
    systemSettingInstance.applySetting(settingWindow!!.getAppSetting())
  }

  /**
   * 重置设置
   */
  override fun reset() {
    settingWindow!!.applySetting(systemSettingInstance.setting)
  }

  /**
   * 销毁UI资源
   */
  override fun disposeUIResources() {
    settingWindow = null
  }

  /**
   * 获取首选组件
   *
   * @return 设置页视图的内容
   */
  override fun getPreferredFocusedComponent(): JComponent = settingWindow!!.content

  /**
   * 获取显示的名字
   *
   * @return 设置名
   */
  override fun getDisplayName() = SYSTEM_SETTING_NAME
}

/**
 * 设置名
 */
const val SYSTEM_SETTING_NAME = "Easy Swagger"

/**
 * 项目包名
 */
const val PROJECT_PACKAGE_NAME = "com.zuminX"