package com.zuminX.window;

import com.intellij.util.ui.FormBuilder;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class OptionForm {

  private final FormBuilder formBuilder;
  @Getter
  private final int sort;
  private JPanel content;
  private JLabel titleName;
  private JPanel optionItem;

  public OptionForm(@NotNull String titleName) {
    this(titleName, 0);
  }

  public OptionForm(@NotNull String titleName, int sort) {
    this.titleName.setText(titleName);
    this.sort = sort;
    formBuilder = FormBuilder.createFormBuilder();
  }

  public final void addOptionItem(@NotNull JComponent component) {
    formBuilder.addComponent(component);
  }

  public final void addLabeledOptionItem(@NotNull String name, @NotNull JComponent component) {
    formBuilder.addLabeledComponent(name, component);
  }

  public final void addLabeledOptionItem(@NotNull String name, @NotNull JComponent component, boolean labelOnTop) {
    formBuilder.addLabeledComponent(name, component, labelOnTop);
  }

  public final JComponent getContent() {
    optionItem.add(formBuilder.getPanel(), BorderLayout.CENTER);
    return content;
  }

  @NotNull
  public final String getName() {
    return this.titleName.getText();
  }
}
