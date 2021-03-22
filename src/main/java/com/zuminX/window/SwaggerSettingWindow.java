/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: AppSettingsComponent
  Author:   ZhangYuanSheng
  Date:     2020/5/27 18:06
  Description:
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.window;

import com.intellij.util.ui.FormBuilder;
import com.zuminX.settings.SettingItem;
import com.zuminX.settings.Settings;
import com.zuminX.window.tabs.SwaggerAnnotationTabbedPane;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class SwaggerSettingWindow {

  public static final int VERTICAL_CLEARANCE = 30;

  @Getter
  private final JPanel content;
  private final List<Option> optionList;

  public SwaggerSettingWindow() {
    optionList = new ArrayList<>();
    FormBuilder builder = FormBuilder.createFormBuilder();

    List<SettingItem> allSettingItems = Settings.getAllSettingItems();
    allSettingItems.forEach(item -> {
      builder.addComponent(item.getForm().getContent(), VERTICAL_CLEARANCE);
      optionList.addAll(item.getOptions());
    });

    content = builder.addComponentFillVertically(new JPanel(), VERTICAL_CLEARANCE).getPanel();
  }

  @NotNull
  public Settings getAppSetting() {
    Settings setting = new Settings();
    optionList.forEach(item -> item.applySetting(setting));
    return setting;
  }

  public void setAppSetting(Settings setting) {
    if (setting == null) {
      return;
    }
    optionList.forEach(item -> item.showSetting(setting));
  }
}
