package com.zuminX.names;

import java.util.List;

/**
 * Controller注解类名
 */
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

  /**
   * 获取所有的Controller注解类名列表
   *
   * @return Controller注解类名列表
   */
  public static List<ControllerAnnotation> getAll() {
    return ClassName.getAll();
  }
}
