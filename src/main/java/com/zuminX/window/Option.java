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

import com.zuminX.beans.settings.Settings;
import javax.swing.JComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

  /**
   * 获取顶部间距
   *
   * @return int
   */
  @Nullable
  default Integer getTopInset() {
    return null;
  }

  interface Custom<T extends JComponent> {

    /**
     * 显示设置
     *
     * @param setting   设置信息
     * @param component 组件
     * @return true: 启用 | false: 执行原本逻辑
     */
    boolean showSetting(@NotNull Settings setting, @NotNull T component);

    /**
     * 应用设置
     *
     * @param setting   设置信息
     * @param component 组件
     * @return true: 启用 | false: 执行原本逻辑
     */
    boolean applySetting(@NotNull Settings setting, @NotNull T component);

    /**
     * 获取顶部间距
     *
     * @return int
     */
    @Nullable
    default Integer getTopInset() {
      return null;
    }
  }
}
