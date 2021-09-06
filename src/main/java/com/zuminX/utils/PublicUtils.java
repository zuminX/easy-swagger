package com.zuminX.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 公共工具类
 */
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
   * 判断指定成员是否被static修饰
   *
   * @param member 成员对象
   * @return 若被static修饰则返回true，否则返回false
   */
  public static boolean isStatic(@NotNull Member member) {
    return Modifier.isStatic(member.getModifiers());
  }

  /**
   * 判断class1是否为class2的超类或超接口
   *
   * @param class1 类对象
   * @param class2 类对象
   * @return 若是则返回true，否则返回false
   */
  public static boolean isAssignable(Class<?> class1, Class<?> class2) {
    return class1.isAssignableFrom(class2);
  }

  /**
   * 获取静态字段的值
   *
   * @param field 静态字段
   * @param <T>   值的类型
   * @return 该字段的值
   */
  @SneakyThrows
  @SuppressWarnings("unchecked")
  public static <T> T getStaticFieldValue(@NotNull Field field) {
    if (!isStatic(field)) {
      return null;
    }
    field.setAccessible(true);
    return (T) field.get(null);
  }

  /**
   * 根据全限定类名获取其的简单类名
   *
   * @param qualifiedName 全限定类名
   * @return 简单类名
   */
  public static String getSimpleNameByQualifiedName(String qualifiedName) {
    if (StrUtil.isEmpty(qualifiedName)) {
      return null;
    }
    int index = qualifiedName.lastIndexOf('.');
    return index == -1 ? qualifiedName : qualifiedName.substring(index + 1);
  }

  /**
   * 获取方法调用链上其父类是clazz的Class对象
   *
   * @param clazz 父类的Class对象
   * @param <T>   父类类型
   * @return Class对象
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<? extends T> getCallSubclass(Class<T> clazz) {
    return (Class<? extends T>) getCallClass(callClazz -> callClazz.getSuperclass() == clazz);
  }

  /**
   * 获取方法调用链上符合条件的Class对象
   *
   * @param predicate 过滤条件
   * @return Class对象
   */
  @SneakyThrows
  public static Class<?> getCallClass(Predicate<Class<?>> predicate) {
    StackTraceElement[] elements = (new Throwable()).getStackTrace();
    for (StackTraceElement ele : elements) {
      Class<?> clazz = Class.forName(ele.getClassName());
      if (predicate.test(clazz)) {
        return clazz;
      }
    }
    return null;
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

  /**
   * 检查目标字符串中是否至少含有指定集合中字符串的一个
   *
   * @param target      待检查的字符串
   * @param allowString 期望出现的字符串
   * @return 若至少出现一次则返回true，否则返回false
   */
  public static boolean containsAny(String target, Set<String> allowString) {
    return allowString.stream().anyMatch(s -> StringUtils.contains(target, s));
  }

  /**
   * 使用双引号包裹字符串
   *
   * @param value 字符串
   * @return 包裹后的字符串
   */
  public static String wrapInDoubleQuotes(Object value) {
    return wrap(value, "\"");
  }

  /**
   * 去除包裹字符串的双引号
   * <p/>
   * 若该字符串没被双引号包裹，则不进行任何处理
   *
   * @param value 字符串
   * @return 去除后的字符串
   */
  public static String unwrapInDoubleQuotes(Object value) {
    String str = Convert.toStr(value);
    if (str == null) {
      return null;
    }
    if (str.length() < 2) {
      return str;
    }
    if (str.charAt(0) != '"' || str.charAt(str.length() - 1) != '"') {
      return str;
    }
    return str.substring(1, str.length() - 1);
  }

  /**
   * 使用花括号包裹字符串
   *
   * @param value 字符串
   * @return 包裹后的字符串
   */
  public static String wrapInCurlyBraces(Object value) {
    return wrap(value, "{", "}");
  }

  /**
   * 使用wrap字符串包裹字符串value
   *
   * @param value 待包裹的字符串
   * @param wrap  包裹字符串
   * @return 包裹后的字符串
   */
  private static String wrap(Object value, String wrap) {
    return wrap(value, wrap, wrap);
  }

  /**
   * 将字符串leftWrap添加到value的首部，将字符串rightWrap添加到value的尾部
   *
   * @param value     待包裹的字符串
   * @param leftWrap  首部字符串
   * @param rightWrap 尾部字符串
   * @return 包裹后的字符串
   */
  private static String wrap(Object value, String leftWrap, String rightWrap) {
    String str = Convert.toStr(value, "");
    return leftWrap + str + rightWrap;
  }

}