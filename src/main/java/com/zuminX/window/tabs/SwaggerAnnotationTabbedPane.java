package com.zuminX.window.tabs;

import cn.hutool.json.JSONUtil;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBTabbedPane;
import com.zuminX.annotations.AnnotationItem;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.domain.AnnotationItemMap;
import com.zuminX.settings.SettingKey;
import com.zuminX.settings.Settings;
import com.zuminX.utils.CoreUtils;
import com.zuminX.window.Option;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;

public class SwaggerAnnotationTabbedPane extends JBTabbedPane implements Option {

  public final SettingKey<AnnotationItemMap> settingKey;

  private final Map<String, SwaggerAnnotationTable> table = new HashMap<>();

  public SwaggerAnnotationTabbedPane(SettingKey<AnnotationItemMap> settingKey) {
    this.settingKey = settingKey;

    getDefaultItems().getMap().forEach((key, value) -> {
      SwaggerAnnotationTableModel model = new SwaggerAnnotationTableModel(value);
      SwaggerAnnotationTable jbTable = new SwaggerAnnotationTable(model);

      table.put(key, jbTable);

      ToolbarDecorator decorator = ToolbarDecorator.createDecorator(jbTable);
      decorator.setMoveDownAction(button -> jbTable.moveDown());
      decorator.setMoveUpAction(button -> jbTable.moveUp());
      JPanel panel = decorator.createPanel();
      this.addTab(key, panel);
    });
  }

  public static AnnotationItemMap getDefaultItems() {
    Map<String, List<AnnotationItem>> map = new HashMap<>();
    CoreUtils.getClasses(AnnotationStr.class).forEach(clazz -> {
      List<AnnotationItem> items = Arrays.stream(clazz.getDeclaredFields())
          .map(Field::getName)
          .map(name -> new AnnotationItem(name, "Test", true))
          .collect(Collectors.toList());
      map.put(clazz.getSimpleName(), items);
    });
    return new AnnotationItemMap(map);
  }

  @Override
  public void showSetting(@NotNull Settings setting) {
    setting.getData(settingKey).getMap().forEach((key, value) -> {
      SwaggerAnnotationTable table = this.table.get(key);
      table.setItemList(value);
    });
  }

  @Override
  public void applySetting(@NotNull Settings setting) {
    Map<String, List<AnnotationItem>> map = new HashMap<>();
    table.forEach((key, value) -> map.put(key, value.getItemList()));
    setting.putData(settingKey, JSONUtil.parseObj(new AnnotationItemMap(map)).toString());
  }
}
