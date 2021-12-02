package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.EXAMPLE

/**
 * 对应io.swagger.annotations.Example
 */
class Example : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var values: List<ExampleProperty>? = null

  override fun getClassName(): ClassName = EXAMPLE
}