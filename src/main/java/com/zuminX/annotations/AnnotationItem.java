package com.zuminX.annotations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.reflect.Type;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnnotationItem<T> {

  private String name;

  private T data;

  private String defaultText;

  private Integer sort;

  private Boolean show;

  @JsonIgnore
  private Type type;

  public AnnotationItem(String name) {
    this(name, Integer.MAX_VALUE, false);
  }

  public AnnotationItem(String name, Integer sort) {
    this(name, sort, false);
  }

  public AnnotationItem(String name, Boolean show) {
    this(name, Integer.MAX_VALUE, show);
  }

  public AnnotationItem(String name, Integer sort, Boolean show) {
    this.name = name;
    this.sort = sort;
    this.show = show;
    this.defaultText = "";
  }
}
