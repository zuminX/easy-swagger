package com.zuminX.enums;

import java.util.Arrays;
import lombok.Getter;

/**
 * 基本类型枚举
 */
@Getter
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

  private final String unboxedName;
  private final String qualifiedName;

  BaseType(String unboxedName, String qualifiedName) {
    this.unboxedName = unboxedName;
    this.qualifiedName = qualifiedName;
  }

  public static BaseType findByName(String name) {
    return Arrays.stream(values())
        .filter(type -> type.getQualifiedName().equals(name) || type.getUnboxedName().equals(name))
        .findFirst()
        .orElse(null);
  }

}
