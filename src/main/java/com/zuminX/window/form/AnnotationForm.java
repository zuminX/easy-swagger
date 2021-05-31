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
import java.util.Map.Entry;
import lombok.SneakyThrows;

/**
 * Swagger注解设置表单类
 */
public class AnnotationForm extends OptionForm {

  public static final SettingKey<AnnotationSettings> ANNOTATION_SETTINGS = getSettingKey();

  public AnnotationForm() {
    super(Information.message("settings.annotation.form.title"), 0);
  }

  /**
   * 加载设置数据
   */
  @SneakyThrows
  public static void loadSettingsData() {
    for (Entry<String, List<AnnotationItem>> entry : ANNOTATION_SETTINGS.getData().getMap().entrySet()) {
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

  /**
   * 获取设置键
   *
   * @return Swagger注解设置信息类的设置键
   */
  private static SettingKey<AnnotationSettings> getSettingKey() {
    SettingKey<AnnotationSettings> settingKey = new SettingKey<>("Swagger Annotation Form", AnnotationTabbedPane.getDefaultItems());
    settingKey.setOption(new AnnotationTabbedPane(settingKey));
    return settingKey;
  }
}