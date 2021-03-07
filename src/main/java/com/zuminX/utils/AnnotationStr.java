package com.zuminX.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * 注解字符串
 */
@Getter
@SuperBuilder
public class AnnotationStr {

  private final String qualifiedName;

  private final String value;

  /**
   * 获取无内容的注解字符串
   *
   * @param qualifiedName 全限定类名
   * @return 注解字符串
   */
  public static String empty(String qualifiedName) {
    return "@" + qualifiedName;
  }

  /**
   * 获取注解字符串
   *
   * @param clazz 父类或超类是注解字符串类的Class对象
   * @param <T>   父类或超类是注解字符串类的类
   * @return 注解字符串
   */
  protected <T extends AnnotationStr> String toString(@NotNull Class<T> clazz) {
    return toString(clazz, this);
  }

  /**
   * 递归获取注解字符串
   *
   * @param clazz         父类或超类是注解字符串类的Class对象
   * @param annotationStr 注解字符串对象
   * @return 注解字符串
   */
  @SneakyThrows
  @SuppressWarnings("unchecked")
  private static <T extends AnnotationStr> String toString(Class<T> clazz, AnnotationStr annotationStr) {
    List<Field> fields = PublicUtils.getNotStaticField(clazz);
    if (fields.isEmpty()) {
      return empty(annotationStr.qualifiedName);
    }
    StringBuilder sb = new StringBuilder(empty(annotationStr.qualifiedName));
    sb.append("(");
    if (annotationStr.value != null) {
      sb.append("value = \"").append(annotationStr.value).append("\", ");
    }
    for (Field field : fields) {
      field.setAccessible(true);
      Object value = field.get(annotationStr);
      if (value == null) {
        continue;
      }
      String name = field.getName();
      String content;
      if (PublicUtils.isNumOrBool(value)) {
        content = value.toString();
      } else if (value instanceof List) {
        Class<?> valueClass = (Class<?>) TypeUtil.getTypeArgument(field.getGenericType());
        if (AnnotationStr.class.isAssignableFrom(valueClass)) {
          String childContent = ((List<AnnotationStr>) value).stream()
              .map(annotation -> toString((Class<? extends AnnotationStr>) valueClass, annotation))
              .collect(Collectors.joining(",\n"));
          sb.append('{').append(childContent).append('}');
          continue;
        }
        String objectsStr = ((List<Object>) value).stream()
            .map(object -> PublicUtils.isNumOrBool(object) ? object.toString() : "\"" + object.toString() + "\"")
            .collect(Collectors.joining(", "));
        content = "{" + objectsStr + "}";
      } else {
        content = "\"" + value.toString() + "\"";
      }
      sb.append(name).append(" = ").append(content).append(", ");
    }
    return StrUtil.removeSuffix(sb, ", ") + ")";
  }
}
