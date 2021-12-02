package com.zuminX.names.annotation

import com.zuminX.names.ClassName
import com.zuminX.names.findClassNameByQualifiedName

/**
 * Request注解类名
 */
class RequestAnnotation(qualifiedName: String, val type: String) : ClassName(qualifiedName) {

  companion object {
    /**
     * 获取所有的Request注解类名列表
     *
     * @return Request注解类名列表
     */
    @JvmStatic
    fun getAll() = listOf(REQUEST_PARAM, REQUEST_HEADER, PATH_VARIABLE, REQUEST_BODY)

    /**
     * 根据全限定类名查找对应的Request注解类名
     *
     * @param qualifiedName 全限定类名
     * @return Request注解类名
     */
    @JvmStatic
    fun findByQualifiedName(qualifiedName: String?) = findClassNameByQualifiedName(getAll(), qualifiedName)
  }
}

val REQUEST_PARAM = RequestAnnotation("org.springframework.web.bind.annotation.RequestParam", "query")
val REQUEST_HEADER = RequestAnnotation("org.springframework.web.bind.annotation.RequestHeader", "header")
val PATH_VARIABLE = RequestAnnotation("org.springframework.web.bind.annotation.PathVariable", "path")
val REQUEST_BODY = RequestAnnotation("org.springframework.web.bind.annotation.RequestBody", "body")
