package com.zuminX.swagger;

import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.utils.AnnotationStr;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Builder
public class ApiModelPropertyAnnotation extends AnnotationStr {

  public static final SwaggerAnnotation API_MODEL_PROPERTY = new SwaggerAnnotation("io.swagger.annotations.ApiModelProperty",
      "ApiModelProperty");

  private final String value;

  private final String name;

  private final String allowableValues;

  private final String access;

  private final String notes;

  private final String dataType;

  private final Boolean required;

  private final Integer position;

  private final Boolean hidden;

  private final String example;

  private final String reference;

  private final Boolean allowEmptyValue;

  @Override
  protected String getSimpleName() {
    return API_MODEL_PROPERTY.getSimpleName();
  }
}
