package com.zuminX.annotations.swagger

import com.zuminX.annotations.AnnotationAttr
import com.zuminX.annotations.AnnotationStr
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.EXTENSION

/**
 * 对应io.swagger.annotations.Extension
 */
class Extension : AnnotationStr() {

  @field:AnnotationAttr(show = true)
  var name: String? = null

  @field:AnnotationAttr(show = true)
  var properties: List<ExtensionProperty>? = null
  
  override fun getClassName(): ClassName = EXTENSION
}