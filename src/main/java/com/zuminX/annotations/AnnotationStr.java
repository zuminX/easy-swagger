package com.zuminX.annotations;

import static com.zuminX.utils.PublicUtils.wrapInCurlyBraces;
import static com.zuminX.utils.PublicUtils.wrapInDoubleQuotes;

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
 * 注解字符串类
 */
public abstract class AnnotationStr {

  /**
   * 获取指定字段上的AnnotationAttr注解
   *
   * @param field 字段
   * @return AnnotationAttr注解
   */
  public static AnnotationAttr getAnnotationAttr(Field field) {
    return AnnotationUtil.getAnnotation(field, AnnotationAttr.class);
  }

  /**
   * 获取AnnotationStr的子类对象的排序字段
   *
   * @param annotationStr 实现AnnotationStr的子类对象
   * @return 排序后的字段列表
   */
  public static List<Field> getSortFields(AnnotationStr annotationStr) {
    return getSortFields(annotationStr, null);
  }

  /**
   * 获取AnnotationStr的子类对象的排序字段，并使用predicate进行条件过滤
   *
   * @param annotationStr 实现AnnotationStr的子类对象
   * @param predicate     条件过滤
   * @return 排序后且符合过滤条件的字段列表
   */
  public static List<Field> getSortFields(AnnotationStr annotationStr, Predicate<AnnotationAttr> predicate) {
    return Arrays.stream(annotationStr.getClass().getDeclaredFields()).filter(field -> {
      AnnotationAttr annotationAttr = getAnnotationAttr(field);
      return annotationAttr != null && (predicate == null || predicate.test(annotationAttr));
    }).sorted(Comparator.comparingInt(f -> getAnnotationAttr(f).sort())).collect(Collectors.toList());
  }

  /**
   * 获取AnnotationStr的子类对象的排序且显示的字段
   *
   * @param annotationStr 实现AnnotationStr的子类对象
   * @return 排序后的显示字段列表
   */
  private static List<Field> getSortAndShowFields(AnnotationStr annotationStr) {
    return getSortFields(annotationStr, AnnotationAttr::show);
  }

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

  /**
   * 生成注解字符串
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
      String content;
      if (value == null) {
        String defaultText = getAnnotationAttr(field).defaultText();
        content = StrUtil.isBlank(defaultText) ? getDefaultTextByType(field.getType()) : defaultText;
      } else if (PublicUtils.isNumOrBool(value)) {
        content = value.toString();
      } else if (value instanceof List) {
        Class<?> valueClass = (Class<?>) TypeUtil.getTypeArgument(field.getGenericType());
        if (PublicUtils.isAssignable(AnnotationStr.class, valueClass)) {
          sb.append(wrapInCurlyBraces(listDeepToStr((List<AnnotationStr>) value)));
          continue;
        }
        content = wrapInCurlyBraces(listToStr((List<Object>) value));
      } else if (value instanceof ClassName) {
        content = ((ClassName) value).getClassName();
      } else {
        content = wrapInDoubleQuotes(value);
      }
      sb.append(field.getName()).append(" = ").append(content).append(", ");
    }
    return StrUtil.removeSuffix(sb, ", ") + ")";
  }

  /**
   * 生成列表中所有AnnotationStr对象的注解字符串
   *
   * @param value AnnotationStr对象列表
   * @return 注解字符串
   */
  private String listDeepToStr(List<AnnotationStr> value) {
    return value.stream()
        .map(this::toStr)
        .collect(Collectors.joining(",\n", "\n", "\n"));
  }

  /**
   * 生成列表中所有对象的注解字符串
   *
   * @param value 非AnnotationStr对象列表
   * @return 注解字符串
   */
  private String listToStr(List<Object> value) {
    return value.stream()
        .map(object -> {
          if (PublicUtils.isNumOrBool(object)) {
            return object.toString();
          }
          if (object instanceof ClassName) {
            return ((ClassName) object).getClassName();
          }
          return wrapInDoubleQuotes(object);
        })
        .collect(Collectors.joining(", "));
  }

  /**
   * 根据类型获取对应的默认文本
   *
   * @param clazz 类型
   * @return 默认文本
   */
  private String getDefaultTextByType(Class<?> clazz) {
    if (PublicUtils.isAssignable(Number.class, clazz)) {
      return "0";
    }
    if (clazz == Boolean.class) {
      return "false";
    }
    if (clazz == List.class) {
      return wrapInCurlyBraces("");
    }
    if (clazz == ClassName.class) {
      return "Void.class";
    }
    return wrapInDoubleQuotes("");
  }
}
