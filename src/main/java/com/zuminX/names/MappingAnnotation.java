package com.zuminX.names;

import lombok.Getter;

/**
 * Mapping注解类名
 */
public class MappingAnnotation extends ClassName {

  public static final MappingAnnotation REQUEST_MAPPING;
  public static final MappingAnnotation GET_MAPPING;
  public static final MappingAnnotation POST_MAPPING;
  public static final MappingAnnotation DELETE_MAPPING;
  public static final MappingAnnotation PATCH_MAPPING;
  public static final MappingAnnotation PUT_MAPPING;

  static {
    REQUEST_MAPPING = new MappingAnnotation("org.springframework.web.bind.annotation.RequestMapping", "REQUEST");
    GET_MAPPING = new MappingAnnotation("org.springframework.web.bind.annotation.GetMapping", "GET");
    POST_MAPPING = new MappingAnnotation("org.springframework.web.bind.annotation.PostMapping", "POST");
    DELETE_MAPPING = new MappingAnnotation("org.springframework.web.bind.annotation.DeleteMapping", "DELETE");
    PATCH_MAPPING = new MappingAnnotation("org.springframework.web.bind.annotation.PatchMapping", "PATCH");
    PUT_MAPPING = new MappingAnnotation("org.springframework.web.bind.annotation.PutMapping", "PUT");
  }

  @Getter
  private final String type;

  /**
   * 该类的构造方法
   *
   * @param qualifiedName 全限定类名
   * @param type          请求类型
   */
  public MappingAnnotation(String qualifiedName, String type) {
    super(qualifiedName);
    this.type = type;
  }

  /**
   * 根据全限定类名查找对应的Mapping注解类名
   *
   * @param qualifiedName 全限定类名
   * @return Mapping注解类名
   */
  public static MappingAnnotation findByQualifiedName(String qualifiedName) {
    return ClassName.findByQualifiedName(qualifiedName);
  }
}
