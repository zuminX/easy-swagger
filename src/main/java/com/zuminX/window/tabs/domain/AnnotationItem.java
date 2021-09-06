package com.zuminX.window.tabs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注解项
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationItem {

  private String name;

  private String defaultText;

  private Integer sort;

  private Boolean show;

}