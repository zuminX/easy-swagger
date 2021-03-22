package com.zuminX.annotations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationItem {

  private String name;

  private String defaultValue;

  private Boolean show;
}
