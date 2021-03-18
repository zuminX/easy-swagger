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

import com.zuminX.beans.settings.SettingKey;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NumberInput extends BaseInput<Number> {

  private boolean withDouble = false;

  public NumberInput(@Nullable Integer defaultValue, @NotNull SettingKey<Number> key, Verify<Number> verify, boolean withDouble) {
    super(defaultValue, key, verify, false);
    this.withDouble = withDouble;
    initVerify();
  }

  public NumberInput(@NotNull String label, @Nullable Integer defaultValue, @NotNull SettingKey<Number> key, Integer topInset,
      Verify<Number> verify, boolean withDouble) {
    super(label, defaultValue, key, topInset, verify, false);
    this.withDouble = withDouble;
    initVerify();
  }

  public NumberInput(@Nullable Integer defaultValue, @NotNull SettingKey<Number> key, Verify<Number> verify) {
    super(defaultValue, key, verify, false);
    initVerify();
  }

  public NumberInput(@NotNull String label, @Nullable Integer defaultValue, @NotNull SettingKey<Number> key, Integer topInset,
      Verify<Number> verify) {
    super(label, defaultValue, key, topInset, verify, false);
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

  @Nullable
  @Override
  protected String toString(Number data) {
    return data != null ? data.toString() : null;
  }

  @Nullable
  @Override
  protected Number fromString(String data) {
    try {
      return Integer.parseInt(data);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
