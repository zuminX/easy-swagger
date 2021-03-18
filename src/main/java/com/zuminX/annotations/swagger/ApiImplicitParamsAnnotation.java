package com.zuminX.annotations.swagger;

import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.annotations.AnnotationStr;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiImplicitParamsAnnotation extends AnnotationStr {

  private final List<ApiImplicitParamAnnotation> value;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_IMPLICIT_PARAMS;
  }
}
