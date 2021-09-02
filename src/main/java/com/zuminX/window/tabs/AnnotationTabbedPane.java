package com.zuminX.window.tabs;

import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBTabbedPane;
import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.settings.SettingKey;
import com.zuminX.settings.Settings;
import com.zuminX.utils.CoreUtils;
import com.zuminX.utils.PublicUtils;
import com.zuminX.window.Option;
import com.zuminX.window.tabs.domain.AnnotationItem;
import com.zuminX.window.tabs.domain.AnnotationSettings;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

/**
 * Swagger注解设置选项卡式窗格类
 */
public class AnnotationTabbedPane extends JBTabbedPane implements Option {

  public final SettingKey<AnnotationSettings> settingKey;

  private final Map<String, AnnotationTable> table = new HashMap<>();

  private final Map<String, List<AnnotationItem>> defaultMap;

  public AnnotationTabbedPane(SettingKey<AnnotationSettings> settingKey, Map<String, List<AnnotationItem>> defaultMap) {
    this.settingKey = settingKey;
    this.defaultMap = defaultMap;

    settingKey.getData().getFullMap(defaultMap).forEach((key, value) -> {
      AnnotationTableModel model = new AnnotationTableModel(value);
      AnnotationTable jbTable = new AnnotationTable(model);

      table.put(key, jbTable);

      ToolbarDecorator decorator = ToolbarDecorator.createDecorator(jbTable);
      decorator.setMoveDownAction(button -> jbTable.moveDown());
      decorator.setMoveUpAction(button -> jbTable.moveUp());
      JPanel panel = decorator.createPanel();
      this.addTab(PublicUtils.getSimpleNameByQualifiedName(key), panel);
    });
  }

  /**
   * 显示设置
   *
   * @param setting 设置信息
   */
  @Override
  public void showSetting(@NotNull Settings setting) {
    setting.getData(settingKey).getFullMap(defaultMap).forEach((key, value) -> {
      AnnotationTable table = this.table.get(key);
      table.setItemList(value);
    });
  }

  /**
   * 应用设置
   *
   * @param setting 设置信息
   */
  @Override
  public void applySetting(@NotNull Settings setting) {
    Map<String, List<AnnotationItem>> map = new HashMap<>();
    table.forEach((key, value) -> map.put(key, value.getItemList()));
    setting.putData(settingKey, new AnnotationSettings(map));
  }
}
