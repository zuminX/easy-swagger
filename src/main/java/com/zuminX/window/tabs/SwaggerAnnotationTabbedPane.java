package com.zuminX.window.tabs;

import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBTabbedPane;
import com.zuminX.annotations.AnnotationItem;
import com.zuminX.annotations.AnnotationSettings;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.settings.SettingKey;
import com.zuminX.settings.Settings;
import com.zuminX.utils.CoreUtils;
import com.zuminX.window.Option;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class SwaggerAnnotationTabbedPane extends JBTabbedPane implements Option {

  public final SettingKey<AnnotationSettings> settingKey;

  private final Map<String, SwaggerAnnotationTable> table = new HashMap<>();

  public SwaggerAnnotationTabbedPane(SettingKey<AnnotationSettings> settingKey) {
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

  @SneakyThrows
  public static AnnotationSettings getDefaultItems() {
    Map<String, List<AnnotationItem<?>>> map = new HashMap<>();
    for (Class<?> clazz : CoreUtils.getClasses(AnnotationStr.class)) {
      Method method = clazz.getDeclaredMethod("getDefaultInstance");
      AnnotationStr defaultInstance = (AnnotationStr) method.invoke(null);
      map.put(clazz.getSimpleName(), getAnnotationItemList(defaultInstance));
    }
    return new AnnotationSettings(map);
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
    Map<String, List<AnnotationItem<?>>> map = new HashMap<>();
    table.forEach((key, value) -> map.put(key, value.getItemList()));
    setting.putData(settingKey, new AnnotationSettings(map));
  }

  @SneakyThrows
  private static List<AnnotationItem<?>> getAnnotationItemList(AnnotationStr annotationStr) {
    List<AnnotationItem<?>> itemList = new ArrayList<>();
    for (Field field : annotationStr.getClass().getDeclaredFields()) {
      if (!AnnotationItem.class.isAssignableFrom(field.getType())) {
        continue;
      }
      field.setAccessible(true);
      itemList.add((AnnotationItem<?>) field.get(annotationStr));
    }
    itemList.sort(Comparator.comparingInt(AnnotationItem::getSort));
    return itemList;
  }
}
