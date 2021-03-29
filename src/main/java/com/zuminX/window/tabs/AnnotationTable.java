package com.zuminX.window.tabs;

import com.intellij.ui.table.JBTable;
import com.zuminX.window.tabs.domain.AnnotationItem;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Swagger注解设置表格
 */
public class AnnotationTable extends JBTable {

  private final AnnotationTableModel tableModel;

  public AnnotationTable(AnnotationTableModel tableModel) {
    super(tableModel);
    this.tableModel = tableModel;
    setTableToCentered();
  }

  /**
   * 获取注解设置信息列表
   *
   * @return 注解设置信息列表
   */
  public List<AnnotationItem> getItemList() {
    return tableModel.getAnnotationNameItems();
  }

  /**
   * 设置注解设置信息列表
   *
   * @param itemList 注解设置信息列表
   */
  public void setItemList(List<AnnotationItem> itemList) {
    tableModel.setAnnotationNameItems(itemList);
  }

  /**
   * 向上移动一行
   */
  public void moveUp() {
    move(-1);
  }

  /**
   * 向下移动一行
   */
  public void moveDown() {
    move(1);
  }

  /**
   * 移动行
   *
   * @param offset 移动量
   */
  private void move(int offset) {
    int selectedRow = getSelectedRow();
    int index = selectedRow + offset;
    if (selectedRow != -1) {
      Collections.swap(getItemList(), selectedRow, index);
    }
    setRowSelectionInterval(index, index);
  }

  /**
   * 设置表格内容为居中显示
   */
  private void setTableToCentered() {
    //TODO 编辑默认值时内容无法居中显示
    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
    tcr.setHorizontalAlignment(JLabel.CENTER);
    setDefaultRenderer(Object.class, tcr);
    getTableHeader().setDefaultRenderer(tcr);
  }

}
