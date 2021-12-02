package com.zuminX.utils

/**
 * 判断当前对象是否为数字或布尔值
 *
 * @return 若是数字或布尔值则返回true，否则返回false
 */
fun Any.isNumOrBool(): Boolean {
  return this is Number || this is Boolean
}

/**
 * 根据全限定类名获取其的简单类名
 *
 * @return 简单类名
 */
fun String?.getSimpleName(): String {
  if (this == null) {
    return ""
  }
  val index = lastIndexOf('.')
  return if (index == -1) this else substring(index + 1)
}

/**
 * 使用双引号包裹字符串
 *
 * @return 包裹后的字符串
 */
fun Any?.wrapInDoubleQuotes() = wrap("\"")

/**
 * 使用花括号包裹字符串
 *
 * @return 包裹后的字符串
 */
fun Any?.wrapInCurlyBraces() = wrap("{", "}")

/**
 * 使用wrap字符串包裹字符串
 *
 * @param wrap  包裹字符串
 * @return 包裹后的字符串
 */
fun Any?.wrap(wrap: String) = wrap(wrap, wrap)

/**
 * 将字符串leftWrap添加到字符串的首部，将字符串rightWrap添加到字符串的尾部
 *
 * @param leftWrap  首部字符串
 * @param rightWrap 尾部字符串
 * @return 包裹后的字符串
 */
fun Any?.wrap(leftWrap: String, rightWrap: String) = "${leftWrap}${toString()}${rightWrap}"

/**
 * 去除包裹字符串的双引号
 * 若该字符串没被双引号包裹，则不进行任何处理
 *
 * @return 去除后的字符串
 */
fun Any?.unwrapInDoubleQuotes(): String {
  val str = toString()
  return when {
    str.length < 2 -> str
    str.first() != '"' || str.last() != '"' -> str
    else -> str.substring(1, str.length - 1)
  }
}

/**
 * 判定当前字符串是否以连续的strings开头
 * 每个string必须空格进行间隔
 *
 * @return 若以strings开头则返回当前字符串检查的末尾后一位，否则返回-1
 */
fun String.startWithAll(vararg strings: String): Int {
  var index = 0
  for (str in strings) {
    for (c in str) {
      if (index == length || this[index++] != c) {
        return -1
      }
    }
    while (index < length) {
      if (this[index] != ' ') {
        break
      }
      index++
    }
  }
  return if (index < length) index else -1
}