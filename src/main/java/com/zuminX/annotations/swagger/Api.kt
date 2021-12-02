package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.API

/**
 * 对应io.swagger.annotations.Api
 */
class Api : AnnotationStr() {
  @field:AnnotationAttr(show = true)
  var value: String? = null

  @field:AnnotationAttr(show = true)
  var tags: List<String>? = null

  @field:AnnotationAttr
  var produces: String? = null

  @field:AnnotationAttr
  var consumes: String? = null

  @field:AnnotationAttr
  var protocols: String? = null

  @field:AnnotationAttr
  var authorizations: List<Authorization>? = null

  @field:AnnotationAttr
  var hidden: Boolean? = null

  override fun getClassName(): ClassName = API
}