package com.zuminX.window.form;

import com.zuminX.annotations.AnnotationSettings;
import com.zuminX.settings.SettingKey;
import com.zuminX.window.OptionForm;
import com.zuminX.window.tabs.SwaggerAnnotationTabbedPane;

public class SwaggerAnnotationForm extends OptionForm {

  public static final SettingKey<AnnotationSettings> ANNOTATION_SETTINGS = getSettingKey();

  public SwaggerAnnotationForm() {
    super("Swagger Annotation", 0);
  }

  public static AnnotationSettings getSettingsData() {
    return ANNOTATION_SETTINGS.getData();
  }

  private static SettingKey<AnnotationSettings> getSettingKey() {
    SettingKey<AnnotationSettings> settingKey = new SettingKey<>("Set Swagger annotation generation rules"
        , SwaggerAnnotationTabbedPane.getDefaultItems());
    settingKey.setOption(new SwaggerAnnotationTabbedPane(settingKey));
    return settingKey;
  }
}
