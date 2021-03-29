package com.zuminX.window.tabs;

import com.zuminX.service.Information;
import com.zuminX.window.tabs.domain.AnnotationItem;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Swagger注解设置表格模型
 */
public class AnnotationTableModel extends AbstractTableModel {

  private static final long serialVersionUID = -857103884902733635L;

  @Getter
  @Setter
  private List<AnnotationItem> annotationNameItems;

  private final List<String> columnName = Arrays.asList(
      Information.message("settings.annotation.table.row.name"),
      Information.message("settings.annotation.table.row.default"),
      Information.message("settings.annotation.table.row.enable"));

  public AnnotationTableModel(List<AnnotationItem> annotationNameItems) {
    this.annotationNameItems = annotationNameItems;
  }

  /**
   * 获取行数
   *
   * @return 行数
   */
  @Override
  public int getRowCount() {
    return annotationNameItems.size();
  }

  /**
   * 获取列数
   *
   * @return 列数
   */
  @Override
  public int getColumnCount() {
    return columnName.size();
  }

  /**
   * 获取列名
   *
   * @param columnIndex 列下标
   * @return 列名
   */
  @Override
  public String getColumnName(int columnIndex) {
    return columnName.get(columnIndex);
  }

  /**
   * 获取列类型
   *
   * @param columnIndex 列下标
   * @return 类型
   */
  @Override
  public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
      case 0:
      case 1:
        return String.class;
      case 2:
        return Boolean.class;
      default:
        throw new IllegalStateException("Unexpected value: " + columnIndex);
    }
  }

  /**
   * 设置指定格子的内容是否可被修改
   *
   * @param rowIndex    行下标
   * @param columnIndex 列下标
   * @return 若可被修改则返回true，否则返回false
   */
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    switch (columnIndex) {
      case 0:
        return false;
      case 1:
      case 2:
        return true;
      default:
        throw new IllegalStateException("Unexpected value: " + columnIndex);
    }
  }

  /**
   * 获取指定格子的值
   *
   * @param rowIndex    行下标
   * @param columnIndex 列下标
   * @return 显示值
   */
  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    AnnotationItem item = annotationNameItems.get(rowIndex);
    switch (columnIndex) {
      case 0:
        return item.getName();
      case 1:
        return item.getDefaultText();
      case 2:
        return item.getShow();
      default:
        throw new IllegalStateException("Unexpected value: " + columnIndex);
    }
  }

  /**
   * 设置指定格子的值
   *
   * @param value 设置值
   * @param rowIndex    行下标
   * @param columnIndex 列下标
   */
  @Override
  public void setValueAt(Object value, int rowIndex, int columnIndex) {
    AnnotationItem item = annotationNameItems.get(rowIndex);
    switch (columnIndex) {
      case 0:
        item.setName(value.toString());
        return;
      case 1:
        item.setDefaultText(value.toString());
        return;
      case 2:
        item.setShow((Boolean) value);
        return;
      default:
        throw new IllegalStateException("Unexpected value: " + columnIndex);
    }
  }

}