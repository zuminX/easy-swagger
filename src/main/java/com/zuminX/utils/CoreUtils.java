package com.zuminX.utils;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import com.zuminX.constant.SystemConstants;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

/**
 * 核心工具类
 */
public final class CoreUtils {

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
        .map(StringUtils::deleteWhitespace)
        .map(CoreUtils::removeCommentSymbol)
        .filter(StringUtils::isNotEmpty)
        .collect(Collectors.joining(" "));
  }

  /**
   * 删除注释符号
   *
   * @param origin 待处理的字符串
   * @return 移除注释符号的字符串
   */
  private static String removeCommentSymbol(String origin) {
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
