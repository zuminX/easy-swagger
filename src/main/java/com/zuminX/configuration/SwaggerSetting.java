/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: AppSettingsState
  Author:   ZhangYuanSheng
  Date:     2020/5/27 18:08
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.configuration;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.zuminX.beans.settings.Settings;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "SwaggerSetting", storages = @Storage(SwaggerSetting.IDE))
public class SwaggerSetting implements PersistentStateComponent<SwaggerSetting> {

  public static final String IDE = "SwaggerSetting.xml";

  @Getter
  private final Settings setting;

  public SwaggerSetting() {
    this.setting = new Settings();
    this.setting.initValue();
  }

  public static SwaggerSetting getInstance() {
    return ServiceManager.getService(SwaggerSetting.class);
  }

  public boolean isModified(Settings changedSetting) {
    if (changedSetting == null) {
      return false;
    }
    return this.setting.isModified(changedSetting);
  }

  @Nullable
  @Override
  public SwaggerSetting getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull SwaggerSetting state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  public void setAppSetting(Settings setting) {
    this.setting.applySetting(setting);
  }
}
