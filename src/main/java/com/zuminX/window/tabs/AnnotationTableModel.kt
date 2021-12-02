package com.zuminX.window.tabs

import com.zuminX.service.Information
import java.io.Serial
import javax.swing.table.AbstractTableModel

/**
 * Swagger注解设置表格模型
 */
class AnnotationTableModel(var annotationNameItems: List<AnnotationItem>) : AbstractTableModel() {

  /**
   * 获取行数
   *
   * @return 行数
   */
  override fun getRowCount() = annotationNameItems.size

  /**
   * 获取列数
   *
   * @return 列数
   */
  override fun getColumnCount() = columnName.size

  /**
   * 获取列名
   *
   * @param columnIndex 列下标
   * @return 列名
   */
  override fun getColumnName(columnIndex: Int): String = columnName[columnIndex]

  /**
   * 获取列类型
   *
   * @param columnIndex 列下标
   * @return 类型
   */
  override fun getColumnClass(columnIndex: Int): Class<*> {
    return when(columnIndex) {
      0, 1 -> String::class.java
      // 此处不能使用Boolean::class.java，其编译后的java代码为Boolean.TYPE
      2 -> Boolean::class.javaObjectType
      else -> throw IllegalStateException("Unexpected value: $columnIndex")
    }
  }

  /**
   * 设置指定格子的内容是否可被修改
   *
   * @param rowIndex    行下标
   * @param columnIndex 列下标
   * @return 若可被修改则返回true，否则返回false
   */
  override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean {
    return when (columnIndex) {
      0 -> false
      1, 2 -> true
      else -> throw IllegalStateException("Unexpected value: $columnIndex")
    }
  }

  /**
   * 获取指定格子的值
   *
   * @param rowIndex    行下标
   * @param columnIndex 列下标
   * @return 显示值
   */
  override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
    val item = annotationNameItems[rowIndex]
    return when (columnIndex) {
      0 -> item.name
      1 -> item.defaultText
      2 -> item.show
      else -> throw IllegalStateException("Unexpected value: $columnIndex")
    }
  }

  /**
   * 设置指定格子的值
   *
   * @param value 设置值
   * @param rowIndex    行下标
   * @param columnIndex 列下标
   */
  override fun setValueAt(value: Any?, rowIndex: Int, columnIndex: Int) {
    val item = annotationNameItems[rowIndex]
    when (columnIndex) {
      0 -> item.name = value.toString()
      1 -> item.defaultText = value.toString()
      2 -> item.show = value as Boolean
      else -> throw IllegalStateException("Unexpected value: $columnIndex")
    }
  }

  companion object {
    @Serial
    private const val serialVersionUID: Long = -2981551000574909166L
  }

}

private val columnName = listOf(
  Information.message("settings.annotation.table.row.name"),
  Information.message("settings.annotation.table.row.default"),
  Information.message("settings.annotation.table.row.enable"))