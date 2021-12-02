package com.zuminX.settings

import java.util.concurrent.atomic.AtomicInteger

/**
 * 键
 *
 * @param <T> 值类型
 */
open class Key<T>(val name: String, val defaultData: T) {

  private val index: Int = OUR_KEYS_COUNTER.getAndIncrement()

  init {
    ALL_KEYS[this.index] = this
  }

  override fun hashCode() = index

  override fun equals(other: Any?) = other === this

  override fun toString() = name
}

val ALL_KEYS = mutableMapOf<Int, Key<*>>()

private val OUR_KEYS_COUNTER = AtomicInteger()
