package com.zuminX.annotations;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.zuminX.names.ClassName;
import com.zuminX.utils.PublicUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
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
   * 递归获取注解字符串
   *
   * @param annotationStr 注解字符串对象
   * @return 注解字符串
   */
  @SneakyThrows
  @SuppressWarnings("unchecked")
  private <T extends AnnotationStr> String toStr(AnnotationStr annotationStr) {
    List<AnnotationItem<?>> annotationItems = getSortItem(annotationStr);
    if (annotationItems.isEmpty()) {
      return annotationStr.empty();
    }
    StringBuilder sb = new StringBuilder(annotationStr.empty());
    sb.append("(");
    for (AnnotationItem<?> item : annotationItems) {
      Object value = item.getData();
      String content;
      if (value == null) {
        content = item.getDefaultText();
      } else if (PublicUtils.isNumOrBool(value)) {
        content = value.toString();
      } else if (value instanceof List) {
        Class<?> valueClass = (Class<?>) TypeUtil.getTypeArgument(item.getType());
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
      sb.append(item.getName()).append(" = ").append(content).append(", ");
    }
    return StrUtil.removeSuffix(sb, ", ") + ")";
  }


  @SneakyThrows
  private List<AnnotationItem<?>> getSortItem(AnnotationStr annotationStr) {
    List<AnnotationItem<?>> itemList = new ArrayList<>();
    for (Field field : annotationStr.getClass().getDeclaredFields()) {
      if (!AnnotationItem.class.isAssignableFrom(field.getType())) {
        continue;
      }
      field.setAccessible(true);
      AnnotationItem<?> value = (AnnotationItem<?>) field.get(annotationStr);
      if (value == null || !value.getShow()) {
        continue;
      }
      value.setType(TypeUtil.getTypeArgument(TypeUtil.getType(field)));
      itemList.add(value);
    }
    itemList.sort(Comparator.comparingInt(AnnotationItem::getSort));
    return itemList;
  }

}
