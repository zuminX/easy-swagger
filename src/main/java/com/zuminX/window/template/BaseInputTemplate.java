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

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Matcher;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.zuminX.settings.SettingKey;
import com.zuminX.settings.Settings;
import com.zuminX.utils.PublicUtils;
import com.zuminX.window.Option;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import javax.swing.JPanel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseInputTemplate<T> extends JPanel implements Option {

  public final SettingKey<T> key;
  @Getter
  private final JBTextField textField;

  private final Matcher<T> verify;

  protected BaseInputTemplate(@Nullable T defaultValue, @NotNull SettingKey<T> key, Matcher<T> verify, boolean labelOnTop) {
    this(key.getName(), defaultValue, key, verify, labelOnTop);
  }

  protected BaseInputTemplate(@NotNull String label, @Nullable T defaultValue, @NotNull SettingKey<T> key, Matcher<T> verify,
      boolean labelOnTop) {
    super(new FlowLayout(FlowLayout.LEFT));
    this.key = key;
    this.textField = new JBTextField(toString(defaultValue));
    this.verify = verify;

    this.add(
        FormBuilder.createFormBuilder()
            .addLabeledComponent(label, textField, labelOnTop)
            .getPanel()
    );
  }

  @Override
  public void showSetting(@NotNull Settings setting) {
    this.textField.setText(toString(setting.getData(this.key)));
  }

  @Override
  public void applySetting(@NotNull Settings setting) {
    T value = fromString(textField.getText());
    if (value == null) {
      return;
    }
    if (verify != null && !verify.match(value)) {
      return;
    }
    setting.putData(this.key, value);
  }

  /**
   * T -> String
   *
   * @param data data
   * @return String
   */
  @Nullable
  protected String toString(T data) {
    return Convert.convertQuietly(String.class, data);
  }

  /**
   * String -> T
   *
   * @param data data
   * @return T
   */
  @Nullable
  protected T fromString(String data) {
    //TODO 问题？
    return Convert.convertQuietly(PublicUtils.getFirstGenericType(this), data);
  }

  protected void appendInputVerify(@NotNull KeyAdapter adapter) {
    this.textField.addKeyListener(adapter);
  }
}
