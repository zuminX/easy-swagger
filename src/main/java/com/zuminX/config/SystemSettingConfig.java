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
import com.zuminX.window.SettingWindow;
import javax.swing.JComponent;
import org.jetbrains.annotations.Nullable;

/**
 * 系统设置配置类
 */
public class SystemSettingConfig implements Configurable {

  private SettingWindow settingWindow;

  /**
   * 获取显示的名字
   *
   * @return 设置名
   */
  @Override
  public String getDisplayName() {
    return SystemConstants.SETTING;
  }

  /**
   * 获取首选组件
   *
   * @return 设置页视图的内容
   */
  @Override
  public JComponent getPreferredFocusedComponent() {
    return settingWindow.getContent();
  }

  /**
   * 创建组件
   *
   * @return 设置页视图的内容
   */
  @Nullable
  @Override
  public JComponent createComponent() {
    settingWindow = new SettingWindow();
    return settingWindow.getContent();
  }

  /**
   * 判断设置是否被修改了
   *
   * @return 若修改了则返回true，否则返回false
   */
  @Override
  public boolean isModified() {
    if (settingWindow == null) {
      return false;
    }
    SystemSetting instance = SystemSetting.getInstance();
    Settings appSetting = settingWindow.getAppSetting();
    return instance.isModified(appSetting);
  }

  /**
   * 应用设置
   */
  @Override
  public void apply() {
    SystemSetting.getInstance().applySetting(settingWindow.getAppSetting());
  }

  /**
   * 重置设置
   */
  @Override
  public void reset() {
    SystemSetting settings = SystemSetting.getInstance();
    settingWindow.setAppSetting(settings.getSetting());
  }

  /**
   * 销毁UI资源
   */
  @Override
  public void disposeUIResources() {
    settingWindow = null;
  }
}
