package com.zuminX.names;

import lombok.Getter;

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

  public MappingAnnotation(String qualifiedName, String type) {
    super(qualifiedName);
    this.type = type;
  }

  public static MappingAnnotation findByQualifiedName(String qualifiedName) {
    return ClassName.findByQualifiedName(MappingAnnotation.class, qualifiedName);
  }


}
