package com.zuminX.names;

import java.util.List;

public class ControllerAnnotation extends ClassName {

  public static final ControllerAnnotation CONTROLLER;
  public static final ControllerAnnotation REST_CONTROLLER;

  static {
    CONTROLLER = new ControllerAnnotation("org.springframework.stereotype.Controller");
    REST_CONTROLLER = new ControllerAnnotation("org.springframework.web.bind.annotation.RestController");
  }

  public ControllerAnnotation(String qualifiedName) {
    super(qualifiedName);
  }

  public static List<ControllerAnnotation> getAll() {
    return ClassName.getAll(ControllerAnnotation.class);
  }
}
