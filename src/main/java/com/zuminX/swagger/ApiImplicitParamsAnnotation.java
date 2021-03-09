package com.zuminX.swagger;

import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.utils.AnnotationStr;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiImplicitParamsAnnotation extends AnnotationStr {

  private final List<ApiImplicitParamAnnotation> value;

  @Override
  protected ClassName getClassName() {
    return SwaggerAnnotation.API_IMPLICIT_PARAMS;
  }
}
