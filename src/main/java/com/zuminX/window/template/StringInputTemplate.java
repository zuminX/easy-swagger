/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: TextInput
  Author:   ZhangYuanSheng
  Date:     2020/9/1 20:21
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.window.template;

import cn.hutool.core.lang.Matcher;
import com.zuminX.settings.SettingKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 字符串输入框模板
 */
public class StringInputTemplate extends BaseInputTemplate<String> {

  public StringInputTemplate(@Nullable String defaultValue, @NotNull SettingKey<String> key, Matcher<String> verify) {
    super(defaultValue, key, verify, true);
    initInput();
  }

  public StringInputTemplate(@NotNull String label, @Nullable String defaultValue, @NotNull SettingKey<String> key, Matcher<String> verify) {
    super(label, defaultValue, key, verify, true);
    initInput();
  }

  private void initInput() {
    getTextField().setColumns(16);
  }
}
