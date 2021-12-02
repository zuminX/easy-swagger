package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.API_MODEL_PROPERTY

/**
 * 对应io.swagger.annotations.ApiModelProperty
 */
class ApiModelProperty : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var value: String? = null

  @field:AnnotationAttr(show = true)
  var name: String? = null

  @field:AnnotationAttr
  var allowableValues: String? = null

  @field:AnnotationAttr
  var access: String? = null

  @field:AnnotationAttr
  var notes: String? = null

  @field:AnnotationAttr
  var dataType: String? = null

  @field:AnnotationAttr(show = true, defaultText = "true")
  var required: Boolean? = null

  @field:AnnotationAttr
  var position: Int? = null

  @field:AnnotationAttr
  var hidden: Boolean? = null

  @field:AnnotationAttr
  var example: String? = null

  @field:AnnotationAttr
  var accessMode: AccessMode? = null

  @field:AnnotationAttr
  var reference: String? = null

  @field:AnnotationAttr
  var allowEmptyValue: Boolean? = null

  @field:AnnotationAttr
  var extensions: List<Extension>? = null

  override fun getClassName(): ClassName = API_MODEL_PROPERTY
}