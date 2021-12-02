package com.zuminX.window.tabs

import com.intellij.ui.table.JBTable
import java.util.*
import javax.swing.JLabel
import javax.swing.table.DefaultTableCellRenderer

/**
 * Swagger注解设置表格
 */
class AnnotationTable(private val tableModel: AnnotationTableModel) : JBTable(tableModel) {
  init {
    setTableToCentered()
  }

  /**
   * 获取注解设置信息列表
   *
   * @return 注解设置信息列表
   */
  fun getItemList() = tableModel.annotationNameItems

  /**
   * 设置注解设置信息列表
   *
   * @param itemList 注解设置信息列表
   */
  fun setItemList(itemList: List<AnnotationItem>) {
    tableModel.annotationNameItems = itemList
  }

  /**
   * 向上移动一行
   */
  fun moveUp() = move(-1)

  /**
   * 向下移动一行
   */
  fun moveDown() = move(1)

  /**
   * 移动行
   *
   * @param offset 移动量
   */
  private fun move(offset: Int) {
    val index = selectedRow + offset
    if (selectedRow != -1) {
      Collections.swap(getItemList(), selectedRow, index)
    }
    setRowSelectionInterval(index, index)
  }

  /**
   * 设置表格内容为居中显示
   */
  private fun setTableToCentered() {
    //TODO 编辑默认值时内容无法居中显示
    val tcr = DefaultTableCellRenderer()
    tcr.horizontalAlignment = JLabel.CENTER
    setDefaultRenderer(Any::class.java, tcr)
    getTableHeader().defaultRenderer = tcr
  }
}