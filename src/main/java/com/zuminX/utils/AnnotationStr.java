package com.zuminX.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.zuminX.names.ClassName;
import java.lang.reflect.Field;
import java.util.List;
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
  protected abstract ClassName getClassName();

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
    return toStr(this.getClass(), this);
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
  private <T extends AnnotationStr> String toStr(Class<T> clazz, AnnotationStr annotationStr) {
    List<Field> fields = PublicUtils.getNotStaticField(clazz);
    if (fields.isEmpty()) {
      return annotationStr.empty();
    }
    StringBuilder sb = new StringBuilder(annotationStr.empty());
    sb.append("(");
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
              .map(annotation -> toStr((Class<? extends AnnotationStr>) valueClass, annotation))
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
      sb.append(name).append(" = ").append(content).append(", ");
    }
    return StrUtil.removeSuffix(sb, ", ") + ")";
  }


}
