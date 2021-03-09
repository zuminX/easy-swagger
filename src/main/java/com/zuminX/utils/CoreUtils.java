package com.zuminX.utils;

import com.intellij.psi.PsiType;
import com.zuminX.enums.BaseType;
import com.zuminX.names.ClassName;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

@UtilityClass
public class CoreUtils {

  private static final ClassName MULTIPART_FILE = new ClassName("org.springframework.web.multipart.MultipartFile");
  private static final ClassName FILE = new ClassName("java.io.File");

  public static String getDataType(String dataType, PsiType psiType) {
    BaseType type = BaseType.findByName(dataType);
    if (type != null) {
      return type.getUnboxedName();
    }
    if (MULTIPART_FILE.equals(psiType.getCanonicalText()) || FILE.equals(psiType.getCanonicalText())) {
      return "file";
    }
    for (PsiType superType : psiType.getSuperTypes()) {
      if (MULTIPART_FILE.equals(superType.getCanonicalText()) || FILE.equals(superType.getCanonicalText())) {
        return "file";
      }
    }
    return null;
  }

  /**
   * 获取注解说明  不写/@desc/@describe/@description
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
