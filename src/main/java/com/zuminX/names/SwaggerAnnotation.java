package com.zuminX.names;

import lombok.Getter;

public class SwaggerAnnotation extends ClassName {

  @Getter
  private final String simpleName;

  public SwaggerAnnotation(String qualifiedName, String simpleName) {
    super(qualifiedName);
    this.simpleName = simpleName;
  }

}
