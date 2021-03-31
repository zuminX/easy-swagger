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

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuminX.config.SystemSetting;
import com.zuminX.utils.CoreUtils;
import com.zuminX.window.Option;
import com.zuminX.window.OptionForm;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 设置类
 */
public class Settings {

  /**
   * 数据存储
   */
  @NotNull
  public Map<String, String> properties = new HashMap<>();

  /**
   * 获取所有设置项
   *
   * @return 设置项列表
   */
  @NotNull
  @SneakyThrows
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
        item.addOption(option);
      }
      options.add(item);
    }

    options.sort(Comparator.comparingInt(o -> o.getForm().getSort()));
    return options;
  }

  /**
   * 应用设置
   *
   * @param setting 设置对象
   */
  public void applySetting(@Nullable Settings setting) {
    if (setting == null) {
      return;
    }
    setting.properties.forEach(this.properties::put);
    //TODO 临时解决setting无法持久化的问题
    SystemSetting.getInstance().setSetting(this);
  }

  /**
   * 根据设置键获取数据
   *
   * @param key 设置键
   * @param <T> 数据类型
   * @return 数据
   */
  @SneakyThrows
  public <T> T getData(@NotNull SettingKey<T> key) {
    String value = properties.get(key.getName());
    T defaultValue = key.getDefaultData();
    if (value == null) {
      return defaultValue;
    }
    return (T) new ObjectMapper().readValue(value, defaultValue.getClass());
  }

  /**
   * 存放数据
   * <p>
   * 以JSON形式存放数据
   *
   * @param key   键
   * @param value 值
   * @param <T>   数据类型
   */
  @SneakyThrows
  public <T> void putData(@NotNull Key<T> key, @NotNull Object value) {
    properties.put(key.getName(), new ObjectMapper().writeValueAsString(value));
  }

  /**
   * 判断设置是否被修改
   *
   * @param settings 设置
   * @return 若被修改则返回true，否则返回false
   */
  public boolean isModified(Settings settings) {
    if (settings == null) {
      return false;
    }
    return settings.properties.entrySet().stream().anyMatch(entry -> !ObjectUtil.equals(entry.getValue(), this.properties.get(entry.getKey())));
  }

  /**
   * 初始化值
   */
  public void initValue() {
    Key.getAllKeys().values().forEach(key -> putData(key, key.getDefaultData()));
  }

}

