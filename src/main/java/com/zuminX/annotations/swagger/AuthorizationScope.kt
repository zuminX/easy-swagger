package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.AUTHORIZATION_SCOPE

/**
 * 对应io.swagger.annotations.AuthorizationScope
 */
class AuthorizationScope : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var scope: String? = null
  
  override fun getClassName(): ClassName = AUTHORIZATION_SCOPE
}