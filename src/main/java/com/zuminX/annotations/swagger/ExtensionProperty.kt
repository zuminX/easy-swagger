package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.EXTENSION_PROPERTY

/**
 * 对应io.swagger.annotations.ExtensionProperty
 */
class ExtensionProperty : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var name: String? = null

  @field:AnnotationAttr(show = true)
  var value: String? = null
  
  override fun getClassName(): ClassName = EXTENSION_PROPERTY
}