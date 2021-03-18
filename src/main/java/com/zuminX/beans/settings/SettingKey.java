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
package com.zuminX.beans.settings;

import cn.hutool.core.convert.Converter;
import cn.hutool.core.convert.impl.BooleanConverter;
import cn.hutool.core.convert.impl.NumberConverter;
import cn.hutool.core.convert.impl.StringConverter;
import com.zuminX.beans.Key;
import com.zuminX.configuration.SwaggerSetting;
import com.zuminX.utils.PublicUtils;
import com.zuminX.window.Option;
import com.zuminX.window.template.BaseInput;
import com.zuminX.window.template.CheckBox;
import com.zuminX.window.template.ComboBox;
import com.zuminX.window.template.NumberInput;
import com.zuminX.window.template.StringInput;
import javax.swing.JComponent;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
public class SettingKey<T> extends Key<T> {

  public static final Converter<String> STRING_CONVERTER = new StringConverter();
  public static final Converter<Boolean> BOOLEAN_CONVERTER = new BooleanConverter();
  public static final Converter<Number> NUMBER_CONVERTER = new NumberConverter();

  private final Converter<T> converter;
  private Option option;

  private SettingKey(String name, T defaultData, @NotNull Converter<T> converter) {
    super(name, defaultData);
    this.converter = converter;
  }

  @NotNull
  @Contract(value = "_, _ -> new", pure = true)
  public static SettingKey<Boolean> createCheckBox(@NotNull String name, @NotNull Boolean defaultData) {
    SettingKey<Boolean> settingKey = new SettingKey<>(name, defaultData, BOOLEAN_CONVERTER);
    settingKey.option = new CheckBox(settingKey, null);
    return settingKey;
  }

  @NotNull
  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static <T> SettingKey<T> createComboBox(@NotNull String name, @NotNull final T[] dataArray, @NotNull Converter<T> converter,
      int... defaultDataIndex) {
    SettingKey<T> settingKey = new SettingKey<>(name, dataArray[PublicUtils.getLegalSubscript(
        dataArray,
        defaultDataIndex != null && defaultDataIndex.length > 0 ? defaultDataIndex[0] : null
    )], converter);
    settingKey.option = new ComboBox<>(dataArray, settingKey);
    return settingKey;
  }

  @NotNull
  @Contract(value = "_, _, _, _, _ -> new", pure = true)
  public static <T> SettingKey<T> createComboBox(@NotNull String name, @NotNull final T[] dataArray, @NotNull Converter<T> converter,
      @NotNull JComponent[] components, int... defaultDataIndex) {
    SettingKey<T> settingKey = new SettingKey<>(name, dataArray[PublicUtils.getLegalSubscript(
        dataArray,
        defaultDataIndex != null && defaultDataIndex.length > 0 ? defaultDataIndex[0] : null
    )], converter);
    settingKey.option = new ComboBox<>(dataArray, settingKey, null, components);
    return settingKey;
  }

  @NotNull
  public static SettingKey<String> createInputString(@NotNull String name, @NotNull String defaultData,
      @NotNull BaseInput.Verify<String> verify) {
    SettingKey<String> settingKey = new SettingKey<>(name, defaultData, STRING_CONVERTER);
    settingKey.option = new StringInput(defaultData, settingKey, verify);
    return settingKey;
  }

  @NotNull
  public static SettingKey<Number> createInputNumber(@NotNull String name, @NotNull Integer defaultData,
      @NotNull BaseInput.Verify<Number> verify) {
    SettingKey<Number> settingKey = new SettingKey<>(name, defaultData, NUMBER_CONVERTER);
    settingKey.option = new NumberInput(defaultData, settingKey, verify);
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
