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
  public static final SwaggerAnnotation AUTHORIZATION;
  public static final SwaggerAnnotation AUTHORIZATION_SCOPE;
  public static final SwaggerAnnotation EXAMPLE;
  public static final SwaggerAnnotation EXAMPLE_PROPERTY;
  public static final SwaggerAnnotation EXTENSION;
  public static final SwaggerAnnotation EXTENSION_PROPERTY;
  public static final SwaggerAnnotation RESPONSE_HEADER;

  static {
    API = new SwaggerAnnotation("io.swagger.annotations.Api");
    API_IMPLICIT_PARAM = new SwaggerAnnotation("io.swagger.annotations.ApiImplicitParam");
    API_IMPLICIT_PARAMS = new SwaggerAnnotation("io.swagger.annotations.ApiImplicitParams");
    API_MODEL = new SwaggerAnnotation("io.swagger.annotations.ApiModel");
    API_MODEL_PROPERTY = new SwaggerAnnotation("io.swagger.annotations.ApiModelProperty");
    API_OPERATION = new SwaggerAnnotation("io.swagger.annotations.ApiOperation");
    AUTHORIZATION = new SwaggerAnnotation("io.swagger.annotations.Authorization");
    AUTHORIZATION_SCOPE = new SwaggerAnnotation("io.swagger.annotations.AuthorizationScope");
    EXAMPLE = new SwaggerAnnotation("io.swagger.annotations.Example");
    EXAMPLE_PROPERTY = new SwaggerAnnotation("io.swagger.annotations.ExampleProperty");
    EXTENSION = new SwaggerAnnotation("io.swagger.annotations.Extension");
    EXTENSION_PROPERTY = new SwaggerAnnotation("io.swagger.annotations.ExtensionProperty");
    RESPONSE_HEADER = new SwaggerAnnotation("io.swagger.annotations.ResponseHeader");
  }

  public SwaggerAnnotation(String qualifiedName) {
    super(qualifiedName);
  }

}
