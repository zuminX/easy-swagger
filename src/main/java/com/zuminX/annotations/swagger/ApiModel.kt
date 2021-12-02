package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.API_MODEL

/**
 * 对应io.swagger.annotations.ApiModel
 */
class ApiModel : AnnotationStr() {

  @field:AnnotationAttr
  var value: String? = null

  @field:AnnotationAttr(show = true)
  var description: String? = null

  @field:AnnotationAttr
  var parent: ClassName? = null

  @field:AnnotationAttr
  var discriminator: String? = null

  @field:AnnotationAttr
  var subTypes: List<ClassName>? = null

  @field:AnnotationAttr
  var reference: String? = null

  override fun getClassName(): ClassName = API_MODEL
}