package com.zuminX.swagger;

import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.utils.AnnotationStr;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Builder
public class ApiImplicitParamAnnotation extends AnnotationStr {

  public static final SwaggerAnnotation API_IMPLICIT_PARAM = new SwaggerAnnotation("io.swagger.annotations.ApiImplicitParam",
      "ApiImplicitParam");

  private final String name;

  private final String value;

  private final String defaultValue;

  private final String allowableValues;

  private final Boolean required;

  private final String access;

  private final Boolean allowMultiple;

  private final String dataType;

  private final Class<?> dataTypeClass;

  private final String paramType;

  private final String example;

  private final String type;

  private final String format;

  private final Boolean allowEmptyValue;

  private final Boolean readOnly;

  private final String collectionFormat;

  @Override
  protected String getSimpleName() {
    return API_IMPLICIT_PARAM.getSimpleName();
  }
}
