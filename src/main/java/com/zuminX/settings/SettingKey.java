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

import com.zuminX.config.SystemSetting;
import com.zuminX.window.Option;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * 设置项的键
 *
 * @param <T> 值类型
 */
public class SettingKey<T> extends Key<T> {

  @Getter
  @Setter
  private Option option;

  public SettingKey(String name, T defaultData) {
    super(name, defaultData);
  }

  /**
   * 获取数据
   *
   * @return 数据
   */
  public T getData() {
    Settings appSetting = SystemSetting.getInstance().getSetting();
    return appSetting.getData(this);
  }

  /**
   * 设置数据
   *
   * @param data 数据
   */
  public void setData(@NotNull T data) {
    Settings appSetting = SystemSetting.getInstance().getSetting();
    appSetting.putData(this, data);
  }
}
