package com.zuminX.utils;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import com.zuminX.constant.SystemConstants;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

/**
 * 核心工具类
 */
public final class CoreUtils {

  private static final Set<String> DESCRIPTION_NAME = Set.of("desc", "describe", "description");

  private static final Set<String> PARAM_NAME = Set.of("param");

  private CoreUtils() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * 获取项目的所有实现clazz的Class
   *
   * @param clazz 类对象
   * @return Class集合
   */
  public static Set<Class<?>> getClasses(Class<?> clazz) {
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    Thread.currentThread().setContextClassLoader(ClassLoaderUtil.class.getClassLoader());

    Set<Class<?>> result = ClassUtil.scanPackageBySuper(SystemConstants.PROJECT_PACKAGE_NAME, clazz);

    Thread.currentThread().setContextClassLoader(contextClassLoader);
    return result;
  }

  /**
   * 获取注释内容
   *
   * @param comment 注释
   * @return 注释内容
   */
  public static String getCommentDesc(String comment) {
    return Arrays.stream(comment.split("\n"))
        .map(StringUtils::trim)
        .map(s -> removeCommentSymbol(s, DESCRIPTION_NAME))
        .filter(StringUtils::isNotEmpty)
        .collect(Collectors.joining(" "));
  }

  /**
   * 获取参数注释
   *
   * @param comment   注释
   * @param paramName 参数名称
   * @return 注释内容
   */
  public static String getParamComment(String comment, String paramName) {
    Optional<String> optional = Arrays.stream(comment.split("\n"))
        .filter(s -> PublicUtils.containsAny(s, PARAM_NAME) && StringUtils.contains(s, paramName))
        .map(StringUtils::trim).findFirst();
    if (optional.isEmpty()) {
      return "";
    }
    String str = removeCommentSymbol(optional.get(), PARAM_NAME);
    return StringUtils.removeStart(str, paramName).trim();
  }

  /**
   * 删除注释符号
   *
   * @param origin      待处理的字符串
   * @param allowSymbol 允许的符号
   * @return 移除注释符号的字符串
   */
  private static String removeCommentSymbol(String origin, Set<String> allowSymbol) {
    if (origin.isEmpty()) {
      return "";
    }
    int start = 0, end = origin.length() - 1;
    while (start <= end) {
      char c = origin.charAt(start);
      if (c == '*' || c == '/' || c == ' ') {
        ++start;
        continue;
      }
      if (c == '@') {
        int spaceIndex = origin.indexOf(' ', start + 1);
        if (spaceIndex != -1) {
          String symbol = origin.substring(start + 1, spaceIndex);
          if (allowSymbol.contains(symbol)) {
            start = spaceIndex + 1;
            while (start <= end && origin.charAt(start) == ' ') {
              ++start;
            }
            break;
          }
        }
        return "";
      }
      break;
    }
    while (start <= end) {
      char c = origin.charAt(end);
      if (c != '*' && c != '/' && c != ' ') {
        break;
      }
      --end;
    }
    return origin.substring(start, end + 1);
  }
}
