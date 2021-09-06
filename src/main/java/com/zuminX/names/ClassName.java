package com.zuminX.names;

import static com.zuminX.utils.PublicUtils.isAssignable;
import static com.zuminX.utils.PublicUtils.isStatic;

import cn.hutool.core.util.StrUtil;
import com.zuminX.utils.PublicUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类名
 */
@Getter
@AllArgsConstructor
public class ClassName {

  public static final ClassName OBJECT_CLASS_NAME = new ClassName("java.lang.Object");
  /**
   * 全限定类名
   */
  private final String qualifiedName;

  /**
   * 获取指定Class的所有静态ClassName字段值
   *
   * @param <T> ClassName字段的实际类型
   * @return 静态ClassName字段值列表
   */
  public static <T extends ClassName> List<T> getAll() {
    Class<? extends ClassName> clazz = PublicUtils.getCallSubclass(ClassName.class);
    List<Field> fields = PublicUtils.getField(clazz, field -> isAssignable(ClassName.class, field.getType()) && isStatic(field));
    Stream<T> stream = fields.stream().map(PublicUtils::getStaticFieldValue);
    return stream.collect(Collectors.toList());
  }

  /**
   * 查找clazz的指定类名的静态ClassName字段值
   *
   * @param qualifiedName 全限定类名
   * @param <T>           ClassName字段的实际类型
   * @return 指定类名的静态ClassName字段值
   */
  @SuppressWarnings("unchecked")
  public static <T extends ClassName> T findByQualifiedName(String qualifiedName) {
    if (StrUtil.isBlank(qualifiedName)) {
      return null;
    }
    return (T) getAll().stream().filter(t -> t.equals(qualifiedName)).findFirst().orElse(null);
  }

  /**
   * 获取当前类名对象的简单类名
   *
   * @return 简单类名
   */
  public String getSimpleName() {
    return PublicUtils.getSimpleNameByQualifiedName(qualifiedName);
  }

  /**
   * 获取当前类名对象的带.class后缀名称
   *
   * @return 带.class后缀名称
   */
  public String getClassName() {
    return getSimpleName() + ".class";
  }

  /**
   * 判断当前类名对象的全限定类名与name是否一致
   *
   * @param name 类名
   * @return 若相同则返回true，否则返回false
   */
  public boolean equals(String name) {
    return qualifiedName.equals(name);
  }
}
