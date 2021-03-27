package com.zuminX.window.form;

import cn.hutool.core.annotation.AnnotationUtil;
import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.service.Information;
import com.zuminX.settings.SettingKey;
import com.zuminX.window.OptionForm;
import com.zuminX.window.tabs.SwaggerAnnotationTabbedPane;
import com.zuminX.window.tabs.domain.AnnotationItem;
import com.zuminX.window.tabs.domain.AnnotationSettings;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.SneakyThrows;

public class SwaggerAnnotationForm extends OptionForm {

  private static final SettingKey<AnnotationSettings> ANNOTATION_SETTINGS = getSettingKey();

  public SwaggerAnnotationForm() {
    super(Information.message("settings.annotation.form.title"), 0);
  }

  @SneakyThrows
  public static void loadSettingsData() {
    Map<String, List<AnnotationItem>> map = ANNOTATION_SETTINGS.getData().getMap();
    for (Entry<String, List<AnnotationItem>> entry : map.entrySet()) {
      Class<?> clazz = Class.forName(entry.getKey());
      for (AnnotationItem annotationItem : entry.getValue()) {
        Field field = clazz.getDeclaredField(annotationItem.getName());
        AnnotationAttr annotation = AnnotationStr.getAnnotationAttr(field);
        AnnotationUtil.setValue(annotation, "defaultText", annotationItem.getDefaultText());
        AnnotationUtil.setValue(annotation, "sort", annotationItem.getSort());
        AnnotationUtil.setValue(annotation, "show", annotationItem.getShow());
      }
    }
  }

  private static SettingKey<AnnotationSettings> getSettingKey() {
    SettingKey<AnnotationSettings> settingKey = new SettingKey<>("Set Swagger annotation generation rules"
        , SwaggerAnnotationTabbedPane.getDefaultItems());
    settingKey.setOption(new SwaggerAnnotationTabbedPane(settingKey));
    return settingKey;
  }
}
