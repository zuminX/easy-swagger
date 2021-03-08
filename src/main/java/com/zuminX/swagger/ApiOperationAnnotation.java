package com.zuminX.swagger;

import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.utils.AnnotationStr;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiOperationAnnotation extends AnnotationStr {

  private final String value;

  private final String notes;

  private final List<String> tags;

  private final Class<?> response;

  private final String responseContainer;

  private final String responseReference;

  private final String httpMethod;

  private final String nickname;

  private final String produces;

  private final String consumes;

  private final String protocols;

  private final Boolean hidden;

  private final Integer code;

  private final Boolean ignoreJsonView;

  @Override
  protected String getSimpleName() {
    return SwaggerAnnotation.API_OPERATION.getSimpleName();
  }
}