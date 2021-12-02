package com.zuminX.names.annotation

import com.zuminX.names.ClassName
import com.zuminX.names.findClassNameByQualifiedName

/**
 * Mapping注解类名
 */
class MappingAnnotation(qualifiedName: String, val type: String) : ClassName(qualifiedName) {

  companion object {
    /**
     * 获取所有的Mapping注解类名列表
     *
     * @return Mapping注解类名列表
     */
    @JvmStatic
    fun getAll() = listOf(REQUEST_MAPPING, GET_MAPPING, POST_MAPPING, DELETE_MAPPING, PATCH_MAPPING, PUT_MAPPING)

    /**
     * 根据全限定类名查找对应的Mapping注解类名
     *
     * @param qualifiedName 全限定类名
     * @return Mapping注解类名
     */
    @JvmStatic
    fun findByQualifiedName(qualifiedName: String?) = findClassNameByQualifiedName(getAll(), qualifiedName)
  }
}

val REQUEST_MAPPING = MappingAnnotation("org.springframework.web.bind.annotation.RequestMapping", "REQUEST")
val GET_MAPPING = MappingAnnotation("org.springframework.web.bind.annotation.GetMapping", "GET")
val POST_MAPPING = MappingAnnotation("org.springframework.web.bind.annotation.PostMapping", "POST")
val DELETE_MAPPING = MappingAnnotation("org.springframework.web.bind.annotation.DeleteMapping", "DELETE")
val PATCH_MAPPING = MappingAnnotation("org.springframework.web.bind.annotation.PatchMapping", "PATCH")
val PUT_MAPPING = MappingAnnotation("org.springframework.web.bind.annotation.PutMapping", "PUT")