/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: Key
  Author:   ZhangYuanSheng
  Date:     2020/8/6 16:24
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.settings;

import cn.hutool.core.lang.Matcher;
import com.zuminX.config.SwaggerSetting;
import com.zuminX.utils.PublicUtils;
import com.zuminX.window.Option;
import com.zuminX.window.template.CheckBoxTemplate;
import com.zuminX.window.template.ComboBoxTemplate;
import com.zuminX.window.template.NumberInputTemplate;
import com.zuminX.window.template.StringInputTemplate;
import javax.swing.JComponent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class SettingKey<T> extends Key<T> {

  @Getter
  @Setter
  private Option option;

  public SettingKey(String name, T defaultData) {
    super(name, defaultData);
  }

  @NotNull
  public static SettingKey<Boolean> createCheckBox(@NotNull String name, @NotNull Boolean defaultData) {
    SettingKey<Boolean> settingKey = new SettingKey<>(name, defaultData);
    settingKey.option = new CheckBoxTemplate(settingKey);
    return settingKey;
  }

  @NotNull
  public static <T> SettingKey<T> createComboBox(@NotNull String name, @NotNull final T[] dataArray, int defaultDataIndex) {
    SettingKey<T> settingKey = new SettingKey<>(name, dataArray[PublicUtils.getLegalSubscript(dataArray, defaultDataIndex)]);
    settingKey.option = new ComboBoxTemplate<>(dataArray, settingKey);
    return settingKey;
  }

  @NotNull
  public static <T> SettingKey<T> createComboBox(@NotNull String name, @NotNull final T[] dataArray, @NotNull JComponent[] components,
      int defaultDataIndex) {
    SettingKey<T> settingKey = new SettingKey<>(name, dataArray[PublicUtils.getLegalSubscript(dataArray, defaultDataIndex)]);
    settingKey.option = new ComboBoxTemplate<>(dataArray, settingKey, components);
    return settingKey;
  }

  @NotNull
  public static SettingKey<String> createInputString(@NotNull String name, @NotNull String defaultData,
      @NotNull Matcher<String> verify) {
    SettingKey<String> settingKey = new SettingKey<>(name, defaultData);
    settingKey.option = new StringInputTemplate(defaultData, settingKey, verify);
    return settingKey;
  }

  @NotNull
  public static SettingKey<Number> createInputNumber(@NotNull String name, @NotNull Integer defaultData,
      @NotNull Matcher<String> verify) {
    SettingKey<Number> settingKey = new SettingKey<>(name, defaultData);
    settingKey.option = new NumberInputTemplate(defaultData, settingKey, verify);
    return settingKey;
  }

  public T getData() {
    Settings appSetting = SwaggerSetting.getInstance().getSetting();
    return appSetting.getData(this);
  }

  public void setData(@NotNull T data) {
    Settings appSetting = SwaggerSetting.getInstance().getSetting();
    appSetting.putData(this, data);
  }
}
