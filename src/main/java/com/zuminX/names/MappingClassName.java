package com.zuminX.names;

import lombok.Getter;

public class MappingClassName extends ClassName {

  public static final MappingClassName REQUEST_MAPPING;
  public static final MappingClassName GET_MAPPING;
  public static final MappingClassName POST_MAPPING;
  public static final MappingClassName DELETE_MAPPING;
  public static final MappingClassName PATCH_MAPPING;
  public static final MappingClassName PUT_MAPPING;

  static {
    REQUEST_MAPPING = new MappingClassName("org.springframework.web.bind.annotation.RequestMapping", "REQUEST");
    GET_MAPPING = new MappingClassName("org.springframework.web.bind.annotation.GetMapping", "GET");
    POST_MAPPING = new MappingClassName("org.springframework.web.bind.annotation.PostMapping", "POST");
    DELETE_MAPPING = new MappingClassName("org.springframework.web.bind.annotation.DeleteMapping", "DELETE");
    PATCH_MAPPING = new MappingClassName("org.springframework.web.bind.annotation.PatchMapping", "PATCH");
    PUT_MAPPING = new MappingClassName("org.springframework.web.bind.annotation.PutMapping", "PUT");
  }

  @Getter
  private final String type;

  public MappingClassName(String qualifiedName, String type) {
    super(qualifiedName);
    this.type = type;
  }

  public static MappingClassName findByQualifiedName(String qualifiedName) {
    return ClassName.findByQualifiedName(MappingClassName.class, qualifiedName);
  }


}
