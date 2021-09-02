package com.zuminX.window.form;

import cn.hutool.core.annotation.AnnotationUtil;
import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.service.Information;
import com.zuminX.settings.SettingKey;
import com.zuminX.window.OptionForm;
import com.zuminX.window.tabs.AnnotationTabbedPane;
import com.zuminX.window.tabs.domain.AnnotationItem;
import com.zuminX.window.tabs.domain.AnnotationSettings;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.SneakyThrows;

/**
 * Swagger注解设置表单类
 */
public class AnnotationForm extends OptionForm {

  public static final SettingKey<AnnotationSettings> ANNOTATION_MAIN_SETTINGS = getSettingKey("Swagger Annotation Main Form",
      AnnotationSettings.MAIN_ANNOTATION_DEFAULT_MAP);
  public static final SettingKey<AnnotationSettings> ANNOTATION_PROPERTY_SETTINGS = getSettingKey("Swagger Annotation Property Form",
      AnnotationSettings.PROPERTY_ANNOTATION_DEFAULT_MAP);

  public AnnotationForm() {
    super(Information.message("settings.annotation.form.title"), 0);
  }

  /**
   * 加载设置数据
   */
  @SneakyThrows
  public static void loadSettingsData() {
    loadSettingsData(ANNOTATION_MAIN_SETTINGS);
    loadSettingsData(ANNOTATION_PROPERTY_SETTINGS);
  }

  /**
   * 加载设置数据
   *
   * @param key 设置键
   */
  @SneakyThrows
  private static void loadSettingsData(SettingKey<AnnotationSettings> key) {
    for (Entry<String, List<AnnotationItem>> entry : key.getData().getMap().entrySet()) {
      Class<?> clazz = Class.forName(entry.getKey());
      int sort = 1;
      for (AnnotationItem annotationItem : entry.getValue()) {
        Field field = clazz.getDeclaredField(annotationItem.getName());
        AnnotationAttr annotation = AnnotationStr.getAnnotationAttr(field);
        AnnotationUtil.setValue(annotation, "defaultText", annotationItem.getDefaultText());
        AnnotationUtil.setValue(annotation, "sort", sort++);
        AnnotationUtil.setValue(annotation, "show", annotationItem.getShow());
      }
    }
  }

  /**
   * 获取设置键
   *
   * @param name       名称
   * @param defaultMap 默认项
   * @return Swagger注解设置信息类的设置键
   */
  private static SettingKey<AnnotationSettings> getSettingKey(String name, Map<String, List<AnnotationItem>> defaultMap) {
    SettingKey<AnnotationSettings> settingKey = new SettingKey<>(name, new AnnotationSettings(defaultMap));
    settingKey.setOption(new AnnotationTabbedPane(settingKey, defaultMap));
    return settingKey;
  }
}