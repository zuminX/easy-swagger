/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: CheckBox
  Author:   ZhangYuanSheng
  Date:     2020/8/6 17:31
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.window.template;

import com.intellij.ui.components.JBCheckBox;
import com.zuminX.settings.SettingKey;
import com.zuminX.settings.Settings;
import com.zuminX.window.Option;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * 复选框模板
 */
public class CheckBoxTemplate extends JBCheckBox implements Option {

  public final SettingKey<Boolean> key;

  public CheckBoxTemplate(@NotNull SettingKey<Boolean> key) {
    this(key.getName(), key);
  }

  public CheckBoxTemplate(@Nls @NotNull String title, @NotNull SettingKey<Boolean> key) {
    super(title);
    this.key = key;
  }

  @Override
  public void showSetting(@NotNull Settings setting) {
    this.setSelected(setting.getData(this.key));
  }

  @Override
  public void applySetting(@NotNull Settings setting) {
    setting.putData(this.key, this.isSelected());
  }
}
