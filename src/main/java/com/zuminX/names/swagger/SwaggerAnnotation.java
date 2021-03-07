package com.zuminX.names.swagger;

import com.zuminX.names.ClassName;
import lombok.Getter;

public class SwaggerAnnotation extends ClassName {

  public static final SwaggerAnnotation API;
  public static final SwaggerAnnotation API_MODEL;
  public static final SwaggerAnnotation API_OPERATION;
  public static final SwaggerAnnotation API_MODEL_PROPERTY;
  public static final SwaggerAnnotation API_IMPLICIT_PARAM;
  public static final SwaggerAnnotation API_IMPLICIT_PARAMS;

  static {
    API = new SwaggerAnnotation("io.swagger.annotations.Api", "Api");
    API_MODEL = new SwaggerAnnotation("io.swagger.annotations.ApiModel", "ApiModel");
    API_OPERATION = new SwaggerAnnotation("io.swagger.annotations.ApiOperation", "ApiOperation");
    API_MODEL_PROPERTY = new SwaggerAnnotation("io.swagger.annotations.ApiModelProperty", "ApiModelProperty");
    API_IMPLICIT_PARAM = new SwaggerAnnotation("io.swagger.annotations.ApiImplicitParam", "ApiImplicitParam");
    API_IMPLICIT_PARAMS = new SwaggerAnnotation("io.swagger.annotations.ApiImplicitParams", "ApiImplicitParams");
  }

  @Getter
  private final String simpleName;

  public SwaggerAnnotation(String qualifiedName, String simpleName) {
    super(qualifiedName);
    this.simpleName = simpleName;
  }

}
