package com.zuminX.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationSettings {

  @Getter
  private Map<String, List<AnnotationItem<?>>> map;

  public <T extends AnnotationStr> List<AnnotationItem<?>> getAnnotationItemList(Class<T> clazz) {
    return map.get(clazz.getSimpleName());
  }

  @SneakyThrows
  public <T extends AnnotationStr> T getAnnotationInstance(Class<T> clazz) {
    List<AnnotationItem<?>> annotationItemList = getAnnotationItemList(clazz);
    if (annotationItemList == null) {
      return null;
    }
    Method method = clazz.getDeclaredMethod("getDefaultInstance");
    AnnotationStr defaultInstance = (AnnotationStr) method.invoke(null);
    int index = 0;
    for (AnnotationItem<?> annotationItem : annotationItemList) {
      Field field = clazz.getDeclaredField(annotationItem.getName());
      field.setAccessible(true);
      AnnotationItem<?> item = (AnnotationItem) field.get(defaultInstance);
      if (annotationItem.getShow()) {
        item.setSort(index++);
      } else {
        item.setShow(false);
      }
      item.setDefaultText(annotationItem.getDefaultText());
      field.set(defaultInstance, item);
    }
    return (T) defaultInstance;
  }

}
