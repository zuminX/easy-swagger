package com.zuminX.names;

import cn.hutool.core.util.StrUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
public abstract class ClassName {

  private final String qualifiedName;

  public boolean equals(String name) {
    return qualifiedName.equals(name);
  }

  @SneakyThrows
  protected static <T extends ClassName> List<T> getAll(@NotNull Class<T> clazz) {
    List<Field> fields = getClassNameStaticField(clazz);
    Stream<T> stream = fields.stream().map(ClassName::getStaticFieldValue);
    return stream.collect(Collectors.toList());
  }

  protected static <T extends ClassName> T findByQualifiedName(@NotNull Class<T> clazz, String qualifiedName) {
    if (StrUtil.isBlank(qualifiedName)) {
      return null;
    }
    List<T> all = getAll(clazz);
    return all.stream().filter(t -> t.equals(qualifiedName)).findFirst().orElse(null);
  }

  private static List<Field> getClassNameStaticField(Class<?> clazz) {
    Field[] fields = clazz.getDeclaredFields();
    return Arrays.stream(fields)
        .filter(field -> field.getType().isAssignableFrom(clazz) && Modifier.isStatic(field.getModifiers()))
        .collect(Collectors.toList());
  }

  @SneakyThrows
  private static <T extends ClassName> T getStaticFieldValue(Field field) {
    return (T) field.get(null);
  }

}
