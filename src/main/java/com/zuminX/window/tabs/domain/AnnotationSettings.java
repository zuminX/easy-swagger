package com.zuminX.window.tabs.domain;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 注解设置信息类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationSettings {

  private Map<String, List<AnnotationItem>> map;
}
