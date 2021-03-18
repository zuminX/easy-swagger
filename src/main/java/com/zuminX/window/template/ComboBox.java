/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: CheckBox
  Author:   ZhangYuanSheng
  Date:     2020/8/6 17:31
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.window.template;

import com.intellij.util.ui.FormBuilder;
import com.zuminX.beans.settings.SettingKey;
import com.zuminX.beans.settings.Settings;
import com.zuminX.window.Option;
import java.awt.FlowLayout;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ComboBox<T> extends JPanel implements Option {

  public final SettingKey<T> key;
  private final com.intellij.openapi.ui.ComboBox<T> comboBox;
  private final Integer topInset;

  public ComboBox(@NotNull T[] data, @NotNull SettingKey<T> key) {
    this(data, key, null);
  }

  public ComboBox(@NotNull T[] data, @NotNull SettingKey<T> key, @Nullable Integer topInset) {
    this(data, key, topInset, new JComponent[0]);
  }

  public ComboBox(@NotNull T[] data, @NotNull SettingKey<T> key, @Nullable Integer topInset, @NotNull JComponent... components) {
    super();
    comboBox = new com.intellij.openapi.ui.ComboBox<>(data);
    this.key = key;
    this.topInset = topInset;

    FormBuilder builder = FormBuilder.createFormBuilder()
        .addLabeledComponent(key.getName(), comboBox);
    Arrays.stream(components).forEach(builder::addComponent);
    this.setLayout(new FlowLayout(FlowLayout.LEFT));
    this.add(builder.getPanel());
  }

  @Override
  public void showSetting(@NotNull Settings setting) {
    setSelectItem(setting.getData(this.key));
  }

  @Override
  public void applySetting(@NotNull Settings setting) {
    T selectedItem = getSelectItem();
    if (selectedItem == null) {
      return;
    }
    setting.putData(this.key, selectedItem);
  }

  @Nullable
  public final T getSelectItem() {
    Object selectedItem = comboBox.getSelectedItem();
    try {
      //noinspection unchecked
      return (T) selectedItem;
    } catch (Exception ignore) {
    }
    return null;
  }

  public final void setSelectItem(@NotNull T item) {
    comboBox.setSelectedItem(item);
  }

  @Nullable
  @Override
  public Integer getTopInset() {
    return this.topInset;
  }
}
