package com.zuminX.window.tabs.domain;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationSettings {

  private Map<String, List<AnnotationItem>> map;
}
