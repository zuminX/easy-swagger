package com.zuminX.annotations;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.zuminX.names.ClassName;
import com.zuminX.utils.PublicUtils;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

/**
 * 注解字符串
 */
public abstract class AnnotationStr {


  /**
   * 获取该注解的类名对象
   *
   * @return 类名对象
   */
  public abstract ClassName getClassName();

  /**
   * 获取无内容的注解字符串
   *
   * @return 注解字符串
   */
  public String empty() {
    return "@" + getClassName().getSimpleName();
  }

  /**
   * 获取注解字符串
   *
   * @param <T> 父类或超类是注解字符串类的类
   * @return 注解字符串
   */
  public <T extends AnnotationStr> String toStr() {
    return toStr(this);
  }

  public static AnnotationAttr getAnnotationAttr(Field field) {
    return AnnotationUtil.getAnnotation(field, AnnotationAttr.class);
  }

  public static List<Field> getSortFields(AnnotationStr annotationStr) {
    return getSortFields(annotationStr, null);
  }

  public static List<Field> getSortFields(AnnotationStr annotationStr, Predicate<AnnotationAttr> predicate) {
    return Arrays.stream(annotationStr.getClass().getDeclaredFields()).filter(field -> {
      AnnotationAttr annotationAttr = getAnnotationAttr(field);
      return annotationAttr != null && (predicate == null || predicate.test(annotationAttr));
    }).sorted(Comparator.comparingInt(f -> getAnnotationAttr(f).sort())).collect(Collectors.toList());
  }

  /**
   * 递归获取注解字符串
   *
   * @param annotationStr 注解字符串对象
   * @return 注解字符串
   */
  @SneakyThrows
  @SuppressWarnings("unchecked")
  private <T extends AnnotationStr> String toStr(AnnotationStr annotationStr) {
    List<Field> fields = getSortAndShowFields(annotationStr);
    if (fields.isEmpty()) {
      return annotationStr.empty();
    }
    StringBuilder sb = new StringBuilder(annotationStr.empty());
    sb.append("(");
    for (Field field : fields) {
      field.setAccessible(true);
      Object value = field.get(annotationStr);
      AnnotationAttr annotationAttr = getAnnotationAttr(field);
      String content;
      if (value == null) {
        content = annotationAttr.defaultText();
      } else if (PublicUtils.isNumOrBool(value)) {
        content = value.toString();
      } else if (value instanceof List) {
        Class<?> valueClass = (Class<?>) TypeUtil.getTypeArgument(field.getGenericType());
        if (AnnotationStr.class.isAssignableFrom(valueClass)) {
          String childContent = ((List<AnnotationStr>) value).stream()
              .map(this::toStr)
              .collect(Collectors.joining(",\n", "\n", ""));
          sb.append(PublicUtils.wrapInCurlyBraces(childContent));
          continue;
        }
        String objectsStr = ((List<Object>) value).stream()
            .map(object -> PublicUtils.isNumOrBool(object) ? object.toString() : PublicUtils.wrapInDoubleQuotes(object))
            .collect(Collectors.joining(", "));
        content = PublicUtils.wrapInCurlyBraces(objectsStr);
      } else {
        content = PublicUtils.wrapInDoubleQuotes(value);
      }
      sb.append(field.getName()).append(" = ").append(content).append(", ");
    }
    return StrUtil.removeSuffix(sb, ", ") + ")";
  }

  private static List<Field> getSortAndShowFields(AnnotationStr annotationStr) {
    return getSortFields(annotationStr, AnnotationAttr::show);
  }

}
