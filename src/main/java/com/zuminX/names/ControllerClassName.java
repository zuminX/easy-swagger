package com.zuminX.names;

import java.util.List;

public class ControllerClassName extends ClassName {

  public static final ControllerClassName CONTROLLER;
  public static final ControllerClassName REST_CONTROLLER;

  static {
    CONTROLLER = new ControllerClassName("org.springframework.stereotype.Controller");
    REST_CONTROLLER = new ControllerClassName("org.springframework.web.bind.annotation.RestController");
  }

  public ControllerClassName(String qualifiedName) {
    super(qualifiedName);
  }

  public static List<ControllerClassName> getAll() {
    return ClassName.getAll(ControllerClassName.class);
  }
}
