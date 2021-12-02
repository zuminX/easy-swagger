package com.zuminX.names.annotation

import com.zuminX.names.ClassName

/**
 * Controller注解类名
 */
class ControllerAnnotation(qualifiedName: String) : ClassName(qualifiedName) {
  companion object {
    /**
     * 获取所有的Controller注解类名列表
     *
     * @return Controller注解类名列表
     */
    @JvmStatic
    fun getAll() = listOf(CONTROLLER, REST_CONTROLLER)
  }
}

val CONTROLLER = ControllerAnnotation("org.springframework.stereotype.Controller")
val REST_CONTROLLER = ControllerAnnotation("org.springframework.web.bind.annotation.RestController")
