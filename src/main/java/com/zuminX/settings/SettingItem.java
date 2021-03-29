package com.zuminX.settings;

import com.zuminX.window.Option;
import com.zuminX.window.OptionForm;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JComponent;
import lombok.Getter;

/**
 * 设置项
 */
@Getter
public class SettingItem {

  private final List<Option> options;

  private final OptionForm form;

  public SettingItem(OptionForm form) {
    options = new CopyOnWriteArrayList<>();
    this.form = form;
  }

  /**
   * 添加选项
   *
   * @param option 选项
   * @return 当前对象
   */
  @SuppressWarnings("UnusedReturnValue")
  public SettingItem addOption(Option option) {
    options.add(option);
    if (option instanceof JComponent) {
      form.addOptionItem((JComponent) option);
    }
    return this;
  }
}