package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.API_OPERATION

/**
 * 对应io.swagger.annotations.ApiOperation
 */
class ApiOperation : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var value: String? = null

  @field:AnnotationAttr(show = true)
  var notes: String? = null

  @field:AnnotationAttr(show = true)
  var tags: List<String>? = null

  @field:AnnotationAttr
  var response: ClassName? = null

  @field:AnnotationAttr
  var responseContainer: String? = null

  @field:AnnotationAttr
  var responseReference: String? = null

  @field:AnnotationAttr(show = true)
  var httpMethod: String? = null

  @field:AnnotationAttr
  var nickname: String? = null

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

  @field:AnnotationAttr
  var responseHeaders: List<ResponseHeader>? = null

  @field:AnnotationAttr(defaultText = "200")
  var code: Int? = null

  @field:AnnotationAttr
  var extensions: List<Extension>? = null

  @field:AnnotationAttr
  var ignoreJsonView: Boolean? = null

  override fun getClassName(): ClassName = API_OPERATION
}