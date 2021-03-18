package com.zuminX.utils;

import cn.hutool.core.collection.EnumerationIter;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ClassUtil;
import com.zuminX.constant.consist.SystemConstants;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

/**
 * 核心工具类
 */
@UtilityClass
public class CoreUtils {

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
   * @return String
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

  private static void appendComment(String string, StringBuilder stringBuilder, int index) {
    String lowerCaseStr = string.toLowerCase();
    int descIndex = StringUtils.ordinalIndexOf(lowerCaseStr, "@", 1);
    descIndex += index;
    String desc = string.substring(descIndex);
    stringBuilder.append(desc);
  }
}
