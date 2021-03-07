package com.zuminX.names;

import lombok.Getter;

public class SwaggerClassName extends ClassName {

  public static final SwaggerClassName API;
  public static final SwaggerClassName API_MODEL;
  public static final SwaggerClassName API_OPERATION;
  public static final SwaggerClassName API_MODEL_PROPERTY;
  public static final SwaggerClassName API_IMPLICIT_PARAM;
  public static final SwaggerClassName API_IMPLICIT_PARAMS;

  static {
    API = new SwaggerClassName("io.swagger.annotations.Api", "Api");
    API_MODEL = new SwaggerClassName("io.swagger.annotations.ApiModel", "ApiModel");
    API_OPERATION = new SwaggerClassName("io.swagger.annotations.ApiOperation", "ApiOperation");
    API_MODEL_PROPERTY = new SwaggerClassName("io.swagger.annotations.ApiModelProperty", "ApiModelProperty");
    API_IMPLICIT_PARAM = new SwaggerClassName("io.swagger.annotations.ApiImplicitParam", "ApiImplicitParam");
    API_IMPLICIT_PARAMS = new SwaggerClassName("io.swagger.annotations.ApiImplicitParams", "ApiImplicitParams");
  }

  @Getter
  private final String simpleName;

  public SwaggerClassName(String qualifiedName, String simpleName) {
    super(qualifiedName);
    this.simpleName = simpleName;
  }

}
