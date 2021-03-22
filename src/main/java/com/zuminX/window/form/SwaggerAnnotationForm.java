package com.zuminX.window.form;

import com.zuminX.annotations.AnnotationItem;
import com.zuminX.domain.AnnotationItemMap;
import com.zuminX.settings.SettingKey;
import com.zuminX.window.OptionForm;
import com.zuminX.window.tabs.SwaggerAnnotationTabbedPane;
import java.util.List;
import java.util.Map;

public class SwaggerAnnotationForm extends OptionForm {

  public static final SettingKey<AnnotationItemMap> ANNOTATION_SETTINGS = getSettingKey();

  public SwaggerAnnotationForm() {
    super("Swagger Annotation", 0);
  }

  private static SettingKey<AnnotationItemMap> getSettingKey() {
    SettingKey<AnnotationItemMap> settingKey = new SettingKey<>("Scan service with library on application default (全局配置)"
        , SwaggerAnnotationTabbedPane.getDefaultItems());
    settingKey.setOption(new SwaggerAnnotationTabbedPane(settingKey));
    return settingKey;
  }
}
