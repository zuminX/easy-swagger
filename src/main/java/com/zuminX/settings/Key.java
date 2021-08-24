/*
  Copyright (C), 2018-2020, ZhangYuanSheng
  FileName: Key
  Author:   ZhangYuanSheng
  Date:     2020/8/6 16:24
  Description: 
  History:
  <author>          <time>          <version>          <desc>
  作者姓名            修改时间           版本号              描述
 */
package com.zuminX.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 键
 *
 * @param <T> 值类型
 */
@Getter
public class Key<T> {

  private static final AtomicInteger OUR_KEYS_COUNTER = new AtomicInteger();
  private static final Map<Integer, Key<?>> ALL_KEYS = new HashMap<>();

  private final int index = OUR_KEYS_COUNTER.getAndIncrement();

  private final String name;
  private final T defaultData;

  protected Key(String name, T defaultData) {
    this.name = name;
    this.defaultData = defaultData;
    ALL_KEYS.put(getIndex(), this);
  }

  /**
   * 获取所有的键
   *
   * @return 以Map形式封装的所有键
   */
  @NotNull
  public static Map<Integer, Key<?>> getAllKeys() {
    return Key.ALL_KEYS;
  }

  @Override
  public int hashCode() {
    return this.index;
  }

  @Override
  public final boolean equals(Object obj) {
    return obj == this;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
