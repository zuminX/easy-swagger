package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.RESPONSE_HEADER

/**
 * 对应io.swagger.annotations.ResponseHeader
 */
class ResponseHeader : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var name: String? = null

  @field:AnnotationAttr
  var description: String? = null

  @field:AnnotationAttr(show = true)
  var response: ClassName? = null

  @field:AnnotationAttr
  var responseContainer: String? = null
  
  override fun getClassName(): ClassName = RESPONSE_HEADER
}