package com.zuminX.window.tabs;

import com.zuminX.annotations.AnnotationItem;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nls;

public class SwaggerAnnotationTableModel extends AbstractTableModel {

  private static final long serialVersionUID = -857103884902733635L;

  @Getter
  @Setter
  private List<AnnotationItem<?>> annotationNameItems;

  private final List<String> columnName = Arrays.asList("AnnotationName", "Defaults", "Whether to generate");

  public SwaggerAnnotationTableModel(List<AnnotationItem<?>> annotationNameItems) {
    this.annotationNameItems = annotationNameItems;
  }

  @Override
  public int getRowCount() {
    return annotationNameItems.size();
  }

  @Override
  public int getColumnCount() {
    return columnName.size();
  }

  @Nls
  @Override
  public String getColumnName(int columnIndex) {
    return columnName.get(columnIndex);
  }

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

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    AnnotationItem<?> item = annotationNameItems.get(rowIndex);
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

  @Override
  public void setValueAt(Object value, int rowIndex, int columnIndex) {
    AnnotationItem<?> item = annotationNameItems.get(rowIndex);
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