package com.zuminX.names.swagger;

import com.zuminX.utils.AnnotationStr;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ApiAnnotationStr extends AnnotationStr {

  private final List<String> tags;

  private final String produces;

  private final String consumes;

  private final String protocols;

  private final Boolean hidden;

  /**
   * 获取注解字符串
   *
   * @return 注解字符串
   */
  public String toString() {
    return super.toString(ApiAnnotationStr.class);
  }
}
