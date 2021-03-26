/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: AppSetting
  Author:   ZhangYuanSheng
  Date:     2020/5/27 18:27
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuminX.utils.CoreUtils;
import com.zuminX.window.Option;
import com.zuminX.window.OptionForm;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JComponent;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Settings {

  /**
   * 数据存储
   */
  @NotNull
  public Map<String, String> properties = new HashMap<>();

  /**
   * 获取所有设置项
   *
   * @return list views
   */
  @SneakyThrows
  @NotNull
  public static List<SettingItem> getAllSettingItems() {
    List<SettingItem> options = new ArrayList<>();

    for (Class<?> declaredClass : CoreUtils.getClasses(OptionForm.class)) {
      OptionForm instance = (OptionForm) declaredClass.getConstructor().newInstance();
      SettingItem item = new SettingItem(instance);
      for (Field field : declaredClass.getDeclaredFields()) {
        if (!field.getType().equals(SettingKey.class)) {
          continue;
        }
        field.setAccessible(true);
        Object value = field.get(null);
        if (!(value instanceof SettingKey)) {
          continue;
        }
        SettingKey<?> settingKey = (SettingKey<?>) value;
        Option option = settingKey.getOption();
        if (!(option instanceof JComponent)) {
          continue;
        }
        item.option(option);
      }
      options.add(item);
    }

    options.sort(Comparator.comparingInt(o -> o.getForm().getSort()));
    return options;
  }

  public void applySetting(@Nullable Settings setting) {
    if (setting == null) {
      return;
    }
    setting.properties.forEach(this.properties::put);
  }

  @SneakyThrows
  public <T> T getData(@NotNull SettingKey<T> key) {
    String value = this.properties.get(key.getName());
    T defaultValue = key.getDefaultData();
    if (value == null) {
      return defaultValue;
    }
    return (T) new ObjectMapper().readValue(value, defaultValue.getClass());
  }

  public <T> void putData(@NotNull Key<T> key, @NotNull Object value) {
    try {
      this.properties.put(key.getName(), new ObjectMapper().writeValueAsString(value));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public boolean isModified(Settings changedSetting) {
    if (changedSetting == null) {
      return false;
    }
    return changedSetting.properties.entrySet()
        .stream()
        .anyMatch(entry -> !Objects.equals(entry.getValue(), this.properties.get(entry.getKey())));
  }

  public void initValue() {
    Key.getAllKeys().values().forEach(key -> putData(key, key.getDefaultData()));
  }

}

