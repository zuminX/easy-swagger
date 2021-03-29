/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: Option
  Author:   ZhangYuanSheng
  Date:     2020/8/6 17:30
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.window;

import com.zuminX.settings.Settings;
import org.jetbrains.annotations.NotNull;

/**
 * 选项类
 */
public interface Option {

  /**
   * 显示设置
   *
   * @param setting 设置信息
   */
  void showSetting(@NotNull Settings setting);

  /**
   * 应用设置
   *
   * @param setting 设置信息
   */
  void applySetting(@NotNull Settings setting);

}
