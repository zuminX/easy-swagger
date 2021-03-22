package com.zuminX.domain;

import com.zuminX.annotations.AnnotationItem;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationItemMap {

  @Getter
  private Map<String, List<AnnotationItem>> map;

}
