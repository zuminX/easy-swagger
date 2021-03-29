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

import com.intellij.openapi.ui.ComboBox;
import com.intellij.util.ui.FormBuilder;
import com.zuminX.settings.SettingKey;
import com.zuminX.settings.Settings;
import com.zuminX.window.Option;
import java.awt.FlowLayout;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 组合框模板
 *
 * @param <T> 数据类型
 */
public class ComboBoxTemplate<T> extends JPanel implements Option {

  public final SettingKey<T> key;
  private final ComboBox<T> comboBox;

  public ComboBoxTemplate(@NotNull T[] data, @NotNull SettingKey<T> key) {
    this(data, key, new JComponent[0]);
  }

  public ComboBoxTemplate(@NotNull T[] data, @NotNull SettingKey<T> key, @NotNull JComponent... components) {
    super();
    comboBox = new ComboBox<>(data);
    this.key = key;

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
    return (T) comboBox.getSelectedItem();
  }

  public final void setSelectItem(@NotNull T item) {
    comboBox.setSelectedItem(item);
  }

}
