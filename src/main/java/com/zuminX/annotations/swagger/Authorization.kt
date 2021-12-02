package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.AUTHORIZATION

/**
 * 对应io.swagger.annotations.Authorization
 */
class Authorization : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var value: String? = null

  @field:AnnotationAttr(show = true)
  var scopes: List<AuthorizationScope>? = null
  
  override fun getClassName(): ClassName = AUTHORIZATION
}