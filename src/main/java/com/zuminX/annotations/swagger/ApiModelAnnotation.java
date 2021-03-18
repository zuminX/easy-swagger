package com.zuminX.annotations.swagger;

import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.annotations.AnnotationStr;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiModelAnnotation extends AnnotationStr {

  private final String description;

  private final Class<?> parent;

  private final String discriminator;

  private final List<Class<?>> subTypes;

  private final String reference;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_MODEL;
  }
}