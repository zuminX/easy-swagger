package com.zuminX.swagger;

import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.utils.AnnotationStr;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Builder
public class ApiAnnotation extends AnnotationStr {

  public static final SwaggerAnnotation API = new SwaggerAnnotation("io.swagger.annotations.Api", "Api");

  private final String value;

  private final List<String> tags;

  private final String produces;

  private final String consumes;

  private final String protocols;

  private final Boolean hidden;

  @Override
  protected String getSimpleName() {
    return API.getSimpleName();
  }
}
