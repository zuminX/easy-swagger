package com.zuminX.window.tabs;

import com.intellij.ui.table.JBTable;
import com.zuminX.window.tabs.domain.AnnotationItem;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

public class SwaggerAnnotationTable extends JBTable {

  private final SwaggerAnnotationTableModel tableModel;

  public SwaggerAnnotationTable(SwaggerAnnotationTableModel tableModel) {
    super(tableModel);
    this.tableModel = tableModel;
    setTableToCentered();
  }

  public List<AnnotationItem> getItemList() {
    return tableModel.getAnnotationNameItems();
  }

  public void setItemList(List<AnnotationItem> itemList) {
    tableModel.setAnnotationNameItems(itemList);
  }

  public void moveUp() {
    move(-1);
  }

  public void moveDown() {
    move(1);
  }

  private void move(int offset) {
    int selectedRow = getSelectedRow();
    int index = selectedRow + offset;
    if (selectedRow != -1) {
      Collections.swap(getItemList(), selectedRow, index);
    }
    setRowSelectionInterval(index, index);
  }

  private void setTableToCentered() {
    //TODO 编辑默认值时内容无法居中显示
    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
    tcr.setHorizontalAlignment(JLabel.CENTER);
    setDefaultRenderer(Object.class, tcr);
    getTableHeader().setDefaultRenderer(tcr);
  }

}
