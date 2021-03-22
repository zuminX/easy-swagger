package com.zuminX.window.tabs;

import com.intellij.ui.table.JBTable;
import com.zuminX.annotations.AnnotationItem;
import java.util.Collections;
import java.util.List;

public class SwaggerAnnotationTable extends JBTable {

  private final SwaggerAnnotationTableModel tableModel;

  public SwaggerAnnotationTable(SwaggerAnnotationTableModel tableModel) {
    super(tableModel);
    this.tableModel = tableModel;
  }

  public List<AnnotationItem> getItemList() {
    return tableModel.getAnnotationItems();
  }

  public void setItemList(List<AnnotationItem> itemList) {
    tableModel.setAnnotationItems(itemList);
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


}
