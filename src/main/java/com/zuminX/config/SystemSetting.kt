package com.zuminX.config

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.zuminX.settings.Settings
import com.zuminX.utils.getService

/**
 * 系统设置类
 */
@State(name = "SystemSetting", storages = [Storage("EasySwaggerSetting.xml")])
class SystemSetting() : PersistentStateComponent<SystemSetting> {

  var setting: Settings = Settings().apply {
    initValue()
  }

  /**
   * 获取状态
   *
   * @return 当前对象
   */
  override fun getState() = this

  /**
   * 加载状态
   *
   * @param state 系统设置类对象
   */
  override fun loadState(state: SystemSetting) = XmlSerializerUtil.copyBean(state, this)

  /**
   * 判断设置是否被修改了
   *
   * @param settings 设置类对象
   * @return 若修改了则返回true，否则返回false
   */
  fun isModified(settings: Settings?) = settings != null && setting.isModified(settings)

  /**
   * 应用设置
   *
   * @param settings 设置类对象
   */
  fun applySetting(settings: Settings?) = setting.applySetting(settings)

}

/**
 * 获取当前类的实例
 */
val systemSettingInstance = SystemSetting::class.java.getService()


