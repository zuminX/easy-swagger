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

import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.IntObjectMap;
import com.intellij.util.containers.IntObjectMap.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Key<T> {

  private static final AtomicInteger OUR_KEYS_COUNTER = new AtomicInteger();
  private static final IntObjectMap<Key<?>> ALL_KEYS = ContainerUtil.createConcurrentIntObjectWeakValueMap();

  @Getter
  private final int index = OUR_KEYS_COUNTER.getAndIncrement();

  @Getter
  private final String name;
  @Getter
  private final T defaultData;

  protected Key(String name, T defaultData) {
    this.name = name;
    this.defaultData = defaultData;
    ALL_KEYS.put(getIndex(), this);
  }

  @NotNull
  public static <T> Key<T> create(@NotNull String name, @NotNull T defaultData) {
    return new Key<>(name, defaultData);
  }

  @Nullable
  public static Key<?> findKeyByName(@NotNull String name) {
    return ALL_KEYS.entrySet().stream().filter(key -> name.equals(key.getValue().name)).findFirst().map(Entry::getValue).orElse(null);
  }

  @NotNull
  public static IntObjectMap<Key<?>> getAllKeys() {
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
