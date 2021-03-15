package com.zuminX.names;

import cn.hutool.core.util.StrUtil;
import com.zuminX.utils.PublicUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class ClassName {

  private final String qualifiedName;

  public String getSimpleName() {
    return PublicUtils.getSimpleNameByQualifiedName(qualifiedName);
  }

  @SneakyThrows
  protected static <T extends ClassName> List<T> getAll(@NotNull Class<T> clazz) {
    List<Field> fields = PublicUtils.getField(clazz,
        field -> ClassName.class.isAssignableFrom(field.getType()) && Modifier.isStatic(field.getModifiers()));
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

  @SneakyThrows
  private static <T extends ClassName> T getStaticFieldValue(Field field) {
    return (T) field.get(null);
  }

  public boolean equals(String name) {
    return qualifiedName.equals(name);
  }

}
