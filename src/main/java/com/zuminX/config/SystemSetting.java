package com.zuminX.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.zuminX.settings.Settings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 系统设置类
 */
@State(name = "SystemSetting", storages = @Storage(SystemSetting.IDE))
public class SystemSetting implements PersistentStateComponent<SystemSetting> {

  public static final String IDE = "EasySwaggerSetting.xml";

  private Settings setting;

  public SystemSetting() {
    setting = new Settings();
    setting.initValue();
  }

  /**
   * 获取当前类的实例
   *
   * @return 系统设置类对象
   */
  public static SystemSetting getInstance() {
    return ServiceManager.getService(SystemSetting.class);
  }

  /**
   * 判断设置是否被修改了
   *
   * @param settings 设置类对象
   * @return 若修改了则返回true，否则返回false
   */
  public boolean isModified(Settings settings) {
    return settings != null && setting.isModified(settings);
  }

  /**
   * 应用设置
   *
   * @param setting 设置类对象
   */
  public void applySetting(Settings setting) {
    this.setting.applySetting(setting);
  }

  /**
   * 获取状态
   *
   * @return 当前对象
   */
  @Nullable
  @Override
  public SystemSetting getState() {
    return this;
  }

  /**
   * 加载状态
   *
   * @param state 系统设置类对象
   */
  @Override
  public void loadState(@NotNull SystemSetting state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  public Settings getSetting() {
    return this.setting;
  }

  public void setSetting(Settings setting) {
    this.setting = setting;
  }
}
