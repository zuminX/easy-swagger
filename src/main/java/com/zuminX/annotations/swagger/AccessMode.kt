package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationEnum

/**
 * 对应io.swagger.annotations.ApiModelProperty.AccessMode
 */
class AccessMode(value: String) : AnnotationEnum(value) {

  override fun getAll() = values

  override fun getExpression() = "ApiModelProperty.${super.getExpression()}"
}

private val values = listOf(AccessMode("AUTO"), AccessMode("READ_ONLY"), AccessMode("READ_WRITE"))