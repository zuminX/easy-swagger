package com.zuminX.service

import com.intellij.AbstractBundle
import com.zuminX.utils.getService
import org.jetbrains.annotations.PropertyKey

/**
 * 信息类
 */
class Information : AbstractBundle(BUNDLE) {
  companion object {
    /**
     * 获取信息
     *
     * @param key    该消息对应的键
     * @param params 参数
     * @return 信息
     */
    @JvmStatic
    fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String {
      return Information::class.java.getService().getMessage(key, params)
    }
  }
}

const val BUNDLE = "messages.Information"