package com.zuminX.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.zuminX.settings.Settings;
import com.zuminX.window.form.SwaggerAnnotationForm;
import com.zuminX.window.tabs.domain.AnnotationItem;
import com.zuminX.window.tabs.domain.AnnotationSettings;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "SwaggerSetting", storages = @Storage(SwaggerSetting.IDE))
public class SwaggerSetting implements PersistentStateComponent<SwaggerSetting> {

  public static final String IDE = "SwaggerSetting.xml";

  @Getter
  private final Settings setting;

  public SwaggerSetting() {
    this.setting = new Settings();
    this.setting.initValue();
  }

  public static SwaggerSetting getInstance() {
    return ServiceManager.getService(SwaggerSetting.class);
  }

  public boolean isModified(Settings changedSetting) {
    if (changedSetting == null) {
      return false;
    }
    return this.setting.isModified(changedSetting);
  }

  @Nullable
  @Override
  public SwaggerSetting getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull SwaggerSetting state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  public void setAppSetting(Settings setting) {
    this.setting.applySetting(setting);
  }
}
