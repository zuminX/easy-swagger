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
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 数字输入框模板
 */
public class NumberInputTemplate extends BaseInputTemplate<Number> {

  private boolean withDouble = false;

  public NumberInputTemplate(@Nullable Integer defaultValue, @NotNull SettingKey<Number> key, Matcher<String> verify, boolean withDouble) {
    super(defaultValue, key, verify, false);
    this.withDouble = withDouble;
    initVerify();
  }

  public NumberInputTemplate(@NotNull String label, @Nullable Integer defaultValue, @NotNull SettingKey<Number> key, Matcher<String> verify,
      boolean withDouble) {
    super(label, defaultValue, key, verify, false);
    this.withDouble = withDouble;
    initVerify();
  }

  public NumberInputTemplate(@Nullable Integer defaultValue, @NotNull SettingKey<Number> key, Matcher<String> verify) {
    super(defaultValue, key, verify, false);
    initVerify();
  }

  public NumberInputTemplate(@NotNull String label, @Nullable Integer defaultValue, @NotNull SettingKey<Number> key, Matcher<String> verify) {
    super(label, defaultValue, key, verify, false);
    initVerify();
  }

  private void initVerify() {
    getTextField().setColumns(5);

    appendInputVerify(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent event) {
        char keyChar = event.getKeyChar();
        if (keyChar < KeyEvent.VK_0 || keyChar > KeyEvent.VK_9) {
          if (withDouble && keyChar == KeyEvent.VK_PERIOD) {
            Component component = event.getComponent();
            if (component instanceof JTextField) {
              JTextField field = (JTextField) component;
              if (field.getText() != null && field.getText().contains(String.valueOf((char) KeyEvent.VK_PERIOD))) {
                event.consume();
              }
            }
            return;
          }
          event.consume();
        }
      }
    });
  }
}
