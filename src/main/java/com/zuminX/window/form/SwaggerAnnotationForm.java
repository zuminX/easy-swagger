package com.zuminX.window.form;

import com.zuminX.domain.AnnotationItemMap;
import com.zuminX.settings.SettingKey;
import com.zuminX.window.OptionForm;
import com.zuminX.window.tabs.SwaggerAnnotationTabbedPane;

public class SwaggerAnnotationForm extends OptionForm {

  public static final SettingKey<AnnotationItemMap> ANNOTATION_SETTINGS = getSettingKey();

  public SwaggerAnnotationForm() {
    super("Swagger Annotation", 0);
  }

  private static SettingKey<AnnotationItemMap> getSettingKey() {
    SettingKey<AnnotationItemMap> settingKey = new SettingKey<>("Set Swagger annotation generation rules"
        , SwaggerAnnotationTabbedPane.getDefaultItems());
    settingKey.setOption(new SwaggerAnnotationTabbedPane(settingKey));
    return settingKey;
  }
}
