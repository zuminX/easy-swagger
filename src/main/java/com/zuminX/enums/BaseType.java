package com.zuminX.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * 基本类型枚举
 */
public enum BaseType {
  BYTE("byte", "java.lang.Byte"),
  CHAR("char", "java.lang.Character"),
  DOUBLE("double", "java.lang.Double"),
  FLOAT("float", "java.lang.Float"),
  INT("int", "java.lang.Integer"),
  LONG("long", "java.lang.Long"),
  SHORT("short", "java.lang.Short"),
  BOOLEAN("boolean", "java.lang.Boolean"),

  STRING("string", "java.lang.String"),
  ;

  private String name;
  private String boxedTypeName;

  BaseType(String name, String boxedTypeName) {
    this.name = name;
    this.boxedTypeName = boxedTypeName;
  }

  public static String findByName(String boxedTypeName) {
    return Arrays.stream(values())
        .filter(type -> Objects.equals(type.getBoxedTypeName(), boxedTypeName))
        .findFirst()
        .map(BaseType::getName)
        .orElse("");
  }

  public static boolean isName(String name) {
    return Arrays.stream(values()).anyMatch(type -> Objects.equals(type.getName(), name));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBoxedTypeName() {
    return boxedTypeName;
  }

  public void setBoxedTypeName(String boxedTypeName) {
    this.boxedTypeName = boxedTypeName;
  }
}
