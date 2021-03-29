package com.zuminX.service;

import com.intellij.AbstractBundle;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.PropertyKey;

/**
 * 信息类
 */
public class Information extends AbstractBundle {

  public static final String BUNDLE = "messages.Information";

  public Information() {
    super(BUNDLE);
  }

  /**
   * 获取信息
   *
   * @param key    该消息对应的键
   * @param params 参数
   * @return 信息
   */
  public static String message(@PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
    return getInstance().getMessage(key, params);
  }

  /**
   * 获取实例对象
   *
   * @return 信息类对象
   */
  private static Information getInstance() {
    return ServiceManager.getService(Information.class);
  }

}
