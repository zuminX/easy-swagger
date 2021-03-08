package com.zuminX.swagger;

import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.utils.AnnotationStr;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Builder
public class ApiModelAnnotation extends AnnotationStr {

  public static final SwaggerAnnotation API_MODEL = new SwaggerAnnotation("io.swagger.annotations.ApiModel", "ApiModel");

  private final String description;

  private final Class<?> parent;

  private final String discriminator;

  private final List<Class<?>> subTypes;

  private final String reference;

  @Override
  protected String getSimpleName() {
    return API_MODEL.getSimpleName();
  }
}