package com.zuminX.utils;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import com.zuminX.constant.SystemConstants;
import java.util.Set;
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
   * 获取注释说明
   * <p/>
   * 不写/@desc/@describe/@description
   *
   * @param comment 所有注释
   * @return 注释
   */
  public static String getCommentDesc(String comment) {
    String[] strings = comment.split("\n");
    if (strings.length == 0) {
      return "";
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (String string : strings) {
      String row = StringUtils.deleteWhitespace(string);
      if (StringUtils.isEmpty(row) || StringUtils.startsWith(row, "/**")) {
        continue;
      }
      if (StringUtils.startsWithIgnoreCase(row, "*@desc")
          && !StringUtils.startsWithIgnoreCase(row, "*@describe")
          && !StringUtils.startsWithIgnoreCase(row, "*@description")) {
        appendComment(string, stringBuilder, 5);
      }
      if (StringUtils.startsWithIgnoreCase(row, "*@description")) {
        appendComment(string, stringBuilder, 12);
      }
      if (StringUtils.startsWithIgnoreCase(row, "*@describe")) {
        appendComment(string, stringBuilder, 9);
      }
      if (StringUtils.startsWith(row, "*@") || StringUtils.startsWith(row, "*/")) {
        continue;
      }
      int descIndex = StringUtils.ordinalIndexOf(string, "*", 1);
      if (descIndex == -1) {
        descIndex = StringUtils.ordinalIndexOf(string, "//", 1);
        descIndex += 1;
      }
      String desc = string.substring(descIndex + 1);
      stringBuilder.append(desc);
    }
    return StringUtils.trim(stringBuilder.toString());
  }

  /**
   * 追加注释
   *
   * @param s     字符串
   * @param sb    字符串构建对象
   * @param index 下标
   */
  private static void appendComment(String s, StringBuilder sb, int index) {
    String lowerCaseStr = s.toLowerCase();
    int descIndex = StringUtils.ordinalIndexOf(lowerCaseStr, "@", 1);
    descIndex += index;
    String desc = s.substring(descIndex);
    sb.append(desc);
  }
}
