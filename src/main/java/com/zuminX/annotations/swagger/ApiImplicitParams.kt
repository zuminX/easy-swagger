package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.API_IMPLICIT_PARAMS

/**
 * 对应io.swagger.annotations.ApiImplicitParams
 */
class ApiImplicitParams : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var value: List<ApiImplicitParam>? = null

  override fun getClassName(): ClassName = API_IMPLICIT_PARAMS
}