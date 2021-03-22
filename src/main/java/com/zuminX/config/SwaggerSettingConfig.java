/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: AppSettingsConfigurable
  Author:   ZhangYuanSheng
  Date:     2020/5/27 18:06
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.config;

import com.intellij.openapi.options.Configurable;
import com.zuminX.constant.SystemConstants;
import com.zuminX.settings.Settings;
import com.zuminX.window.SwaggerSettingWindow;
import javax.swing.JComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

public class SwaggerSettingConfig implements Configurable {

  private SwaggerSettingWindow settingsComponent ;

  @Nls(capitalization = Nls.Capitalization.Title)
  @Override
  public String getDisplayName() {
    return SystemConstants.SETTING;
  }

  @Override
  public JComponent getPreferredFocusedComponent() {
    return settingsComponent.getContent();
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    settingsComponent = new SwaggerSettingWindow();
    return settingsComponent.getContent();
  }

  @Override
  public boolean isModified() {
    if (settingsComponent == null) {
      return false;
    }
    SwaggerSetting instance = SwaggerSetting.getInstance();
    Settings appSetting = settingsComponent.getAppSetting();
    return instance.isModified(appSetting);
  }

  @Override
  public void apply() {
    SwaggerSetting.getInstance().setAppSetting(settingsComponent.getAppSetting());
  }

  @Override
  public void reset() {
    SwaggerSetting settings = SwaggerSetting.getInstance();
    settingsComponent.setAppSetting(settings.getSetting());
  }

  @Override
  public void disposeUIResources() {
    settingsComponent = null;
  }
}
