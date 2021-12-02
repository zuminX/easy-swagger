package com.zuminX.names.annotation

import com.zuminX.names.ClassName

/**
 * Swagger注解类名
 */
class SwaggerAnnotation(qualifiedName: String) : ClassName(qualifiedName) {
  companion object {
    /**
     * 获取所有的Swagger注解类名列表
     *
     * @return Swagger注解类名列表
     */
    @JvmStatic
    fun getAll() = listOf(API, API_IMPLICIT_PARAM, API_IMPLICIT_PARAMS, API_MODEL, API_MODEL_PROPERTY, API_OPERATION,
      AUTHORIZATION, AUTHORIZATION_SCOPE, EXAMPLE, EXAMPLE_PROPERTY, EXTENSION, EXTENSION_PROPERTY, RESPONSE_HEADER)
  }
}

val API = SwaggerAnnotation("io.swagger.annotations.Api")
val API_IMPLICIT_PARAM = SwaggerAnnotation("io.swagger.annotations.ApiImplicitParam")
val API_IMPLICIT_PARAMS = SwaggerAnnotation("io.swagger.annotations.ApiImplicitParams")
val API_MODEL = SwaggerAnnotation("io.swagger.annotations.ApiModel")
val API_MODEL_PROPERTY = SwaggerAnnotation("io.swagger.annotations.ApiModelProperty")
val API_OPERATION = SwaggerAnnotation("io.swagger.annotations.ApiOperation")
val AUTHORIZATION = SwaggerAnnotation("io.swagger.annotations.Authorization")
val AUTHORIZATION_SCOPE = SwaggerAnnotation("io.swagger.annotations.AuthorizationScope")
val EXAMPLE = SwaggerAnnotation("io.swagger.annotations.Example")
val EXAMPLE_PROPERTY = SwaggerAnnotation("io.swagger.annotations.ExampleProperty")
val EXTENSION = SwaggerAnnotation("io.swagger.annotations.Extension")
val EXTENSION_PROPERTY = SwaggerAnnotation("io.swagger.annotations.ExtensionProperty")
val RESPONSE_HEADER = SwaggerAnnotation("io.swagger.annotations.ResponseHeader")
