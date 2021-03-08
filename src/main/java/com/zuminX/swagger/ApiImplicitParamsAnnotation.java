package com.zuminX.swagger;

import com.zuminX.names.SwaggerAnnotation;
import com.zuminX.utils.AnnotationStr;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiImplicitParamsAnnotation extends AnnotationStr {

  public static final SwaggerAnnotation API_IMPLICIT_PARAMS = new SwaggerAnnotation("io.swagger.annotations.ApiImplicitParams",
      "ApiImplicitParams");

  private final List<ApiImplicitParamAnnotation> value;

  @Override
  protected String getSimpleName() {
    return API_IMPLICIT_PARAMS.getSimpleName();
  }
}
