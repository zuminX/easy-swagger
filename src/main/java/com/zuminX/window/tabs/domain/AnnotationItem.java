package com.zuminX.window.tabs.domain;

/**
 * 注解项
 */
public class AnnotationItem {

  private String name;

  private String defaultText;

  private Integer sort;

  private Boolean show;

  public AnnotationItem(String name, String defaultText, Integer sort, Boolean show) {
    this.name = name;
    this.defaultText = defaultText;
    this.sort = sort;
    this.show = show;
  }

  public AnnotationItem() {
  }

  public String getName() {
    return this.name;
  }

  public String getDefaultText() {
    return this.defaultText;
  }

  public Integer getSort() {
    return this.sort;
  }

  public Boolean getShow() {
    return this.show;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDefaultText(String defaultText) {
    this.defaultText = defaultText;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public void setShow(Boolean show) {
    this.show = show;
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof AnnotationItem)) {
      return false;
    }
    final AnnotationItem other = (AnnotationItem) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$name = this.getName();
    final Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
      return false;
    }
    final Object this$defaultText = this.getDefaultText();
    final Object other$defaultText = other.getDefaultText();
    if (this$defaultText == null ? other$defaultText != null : !this$defaultText.equals(other$defaultText)) {
      return false;
    }
    final Object this$sort = this.getSort();
    final Object other$sort = other.getSort();
    if (this$sort == null ? other$sort != null : !this$sort.equals(other$sort)) {
      return false;
    }
    final Object this$show = this.getShow();
    final Object other$show = other.getShow();
    if (this$show == null ? other$show != null : !this$show.equals(other$show)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof AnnotationItem;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    final Object $defaultText = this.getDefaultText();
    result = result * PRIME + ($defaultText == null ? 43 : $defaultText.hashCode());
    final Object $sort = this.getSort();
    result = result * PRIME + ($sort == null ? 43 : $sort.hashCode());
    final Object $show = this.getShow();
    result = result * PRIME + ($show == null ? 43 : $show.hashCode());
    return result;
  }

  public String toString() {
    return "AnnotationItem(name=" + this.getName() + ", defaultText=" + this.getDefaultText() + ", sort=" + this.getSort() + ", show=" + this.getShow() + ")";
  }
}