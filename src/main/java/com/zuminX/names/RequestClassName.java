package com.zuminX.names;

import lombok.Getter;

public class RequestClassName extends ClassName {

  public static final RequestClassName REQUEST_PARAM;
  public static final RequestClassName REQUEST_HEADER;
  public static final RequestClassName PATH_VARIABLE;
  public static final RequestClassName REQUEST_BODY;

  static {
    REQUEST_PARAM = new RequestClassName("org.springframework.web.bind.annotation.RequestParam", "query");
    REQUEST_HEADER = new RequestClassName("org.springframework.web.bind.annotation.RequestHeader", "header");
    PATH_VARIABLE = new RequestClassName("org.springframework.web.bind.annotation.PathVariable", "path");
    REQUEST_BODY = new RequestClassName("org.springframework.web.bind.annotation.RequestBody", "body");
  }

  @Getter
  private final String type;

  public RequestClassName(String qualifiedName, String type) {
    super(qualifiedName);
    this.type = type;
  }

  public static RequestClassName findByQualifiedName(String qualifiedName) {
    return ClassName.findByQualifiedName(RequestClassName.class, qualifiedName);
  }
}
