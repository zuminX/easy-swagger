package com.zuminX.names;

import lombok.Getter;

public class RequestAnnotation extends ClassName {

  public static final RequestAnnotation REQUEST_PARAM;
  public static final RequestAnnotation REQUEST_HEADER;
  public static final RequestAnnotation PATH_VARIABLE;
  public static final RequestAnnotation REQUEST_BODY;

  static {
    REQUEST_PARAM = new RequestAnnotation("org.springframework.web.bind.annotation.RequestParam", "query");
    REQUEST_HEADER = new RequestAnnotation("org.springframework.web.bind.annotation.RequestHeader", "header");
    PATH_VARIABLE = new RequestAnnotation("org.springframework.web.bind.annotation.PathVariable", "path");
    REQUEST_BODY = new RequestAnnotation("org.springframework.web.bind.annotation.RequestBody", "body");
  }

  @Getter
  private final String type;

  public RequestAnnotation(String qualifiedName, String type) {
    super(qualifiedName);
    this.type = type;
  }

  public static RequestAnnotation findByQualifiedName(String qualifiedName) {
    return ClassName.findByQualifiedName(RequestAnnotation.class, qualifiedName);
  }
}
