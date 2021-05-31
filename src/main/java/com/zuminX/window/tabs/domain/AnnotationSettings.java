package com.zuminX.window.tabs.domain;

import java.util.List;
import java.util.Map;

/**
 * 注解设置信息类
 */
public class AnnotationSettings {

  private Map<String, List<AnnotationItem>> map;

  public AnnotationSettings(Map<String, List<AnnotationItem>> map) {
    this.map = map;
  }

  public AnnotationSettings() {
  }

  public Map<String, List<AnnotationItem>> getMap() {
    return this.map;
  }

  public void setMap(Map<String, List<AnnotationItem>> map) {
    this.map = map;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof AnnotationSettings)) {
      return false;
    }
    final AnnotationSettings other = (AnnotationSettings) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$map = this.getMap();
    final Object other$map = other.getMap();
    if (this$map == null ? other$map != null : !this$map.equals(other$map)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof AnnotationSettings;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $map = this.getMap();
    result = result * PRIME + ($map == null ? 43 : $map.hashCode());
    return result;
  }

  public String toString() {
    return "AnnotationSettings(map=" + this.getMap() + ")";
  }
}
