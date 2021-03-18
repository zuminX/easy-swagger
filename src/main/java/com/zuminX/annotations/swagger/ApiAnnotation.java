package com.zuminX.annotations.swagger;

import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.annotations.AnnotationStr;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiAnnotation extends AnnotationStr {

  private final String value;

  private final List<String> tags;

  private final String produces;

  private final String consumes;

  private final String protocols;

  private final Boolean hidden;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API;
  }
}
