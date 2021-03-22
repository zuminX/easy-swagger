package com.zuminX.settings;

import com.zuminX.window.Option;
import com.zuminX.window.OptionForm;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JComponent;
import lombok.Getter;

@Getter
public class SettingItem {

  private final List<Option> options;

  private final OptionForm form;

  public SettingItem(OptionForm form) {
    options = new CopyOnWriteArrayList<>();
    this.form = form;
  }

  @SuppressWarnings("UnusedReturnValue")
  public SettingItem option(Option option) {
    this.options.add(option);
    if (option instanceof JComponent) {
      this.form.addOptionItem((JComponent) option);
    }
    return this;
  }
}