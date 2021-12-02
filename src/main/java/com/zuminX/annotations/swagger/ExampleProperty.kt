package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.EXAMPLE_PROPERTY

/**
 * 对应io.swagger.annotations.ExampleProperty
 */
class ExampleProperty : AnnotationStr() {
  
  @field:AnnotationAttr(show = true)
  var mediaType: String? = null

  @field:AnnotationAttr(show = true)
  var value: String? = null

  override fun getClassName(): ClassName = EXAMPLE_PROPERTY
}