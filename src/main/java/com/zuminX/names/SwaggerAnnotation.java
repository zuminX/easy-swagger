package com.zuminX.names;

/**
 * Swagger注解类名
 */
public class SwaggerAnnotation extends ClassName {

  public static final SwaggerAnnotation API;
  public static final SwaggerAnnotation API_IMPLICIT_PARAM;
  public static final SwaggerAnnotation API_IMPLICIT_PARAMS;
  public static final SwaggerAnnotation API_MODEL;
  public static final SwaggerAnnotation API_MODEL_PROPERTY;
  public static final SwaggerAnnotation API_OPERATION;

  static {
    API = new SwaggerAnnotation("io.swagger.annotations.Api");
    API_IMPLICIT_PARAM = new SwaggerAnnotation("io.swagger.annotations.ApiImplicitParam");
    API_IMPLICIT_PARAMS = new SwaggerAnnotation("io.swagger.annotations.ApiImplicitParams");
    API_MODEL = new SwaggerAnnotation("io.swagger.annotations.ApiModel");
    API_MODEL_PROPERTY = new SwaggerAnnotation("io.swagger.annotations.ApiModelProperty");
    API_OPERATION = new SwaggerAnnotation("io.swagger.annotations.ApiOperation");
  }

  public SwaggerAnnotation(String qualifiedName) {
    super(qualifiedName);
  }

}
