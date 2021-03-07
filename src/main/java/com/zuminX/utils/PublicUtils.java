package com.zuminX.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PublicUtils {

  /**
   * 判断value对象是否为数字或布尔值
   *
   * @param value 对象
   * @return 若是数字或布尔值则返回true，否则返回false
   */
  public static boolean isNumOrBool(Object value) {
    return value instanceof Number || value instanceof Boolean;
  }

  /**
   * 获取指定类的非静态成员字段
   *
   * @param clazz Class对象
   * @return Class对象的非静态成员字段列表
   */
  public static List<Field> getNotStaticField(Class<?> clazz) {
    return getField(clazz, field -> !Modifier.isStatic(field.getModifiers()));
  }

  /**
   * 获取指定类的成员字段
   *
   * @param clazz     Class对象
   * @param predicate 过滤条件
   * @return 符合过滤条件的成员字段列表
   */
  public static List<Field> getField(Class<?> clazz, Predicate<Field> predicate) {
    Field[] fields = clazz.getDeclaredFields();
    return Arrays.stream(fields)
        .filter(predicate)
        .collect(Collectors.toList());
  }
}