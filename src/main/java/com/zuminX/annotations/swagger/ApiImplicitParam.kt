package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.API_IMPLICIT_PARAM

/**
 * 对应io.swagger.annotations.ApiImplicitParam
 */
class ApiImplicitParam : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var name: String? = null

  @field:AnnotationAttr(show = true)
  var value: String? = null

  @field:AnnotationAttr(show = true)
  var defaultValue: String? = null

  @field:AnnotationAttr(show = true)
  var allowableValues: String? = null

  @field:AnnotationAttr
  var required: Boolean? = null

  @field:AnnotationAttr
  var access: String? = null

  @field:AnnotationAttr
  var allowMultiple: Boolean? = null

  @field:AnnotationAttr
  var dataType: String? = null

  @field:AnnotationAttr(show = true)
  var dataTypeClass: ClassName? = null

  @field:AnnotationAttr
  var paramType: String? = null

  @field:AnnotationAttr
  var example: String? = null

  @field:AnnotationAttr
  var examples: List<Example>? = null

  @field:AnnotationAttr
  var type: String? = null

  @field:AnnotationAttr
  var format: String? = null

  @field:AnnotationAttr
  var allowEmptyValue: Boolean? = null

  @field:AnnotationAttr
  var readOnly: Boolean? = null

  @field:AnnotationAttr
  var collectionFormat: String? = null

  override fun getClassName(): ClassName = API_IMPLICIT_PARAM
}