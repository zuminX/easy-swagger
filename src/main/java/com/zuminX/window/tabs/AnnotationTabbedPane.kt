package com.zuminX.window.tabs

import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBTabbedPane
import com.zuminX.settings.SettingKey
import com.zuminX.settings.Settings
import com.zuminX.utils.getSimpleName
import com.zuminX.window.Option

/**
 * Swagger注解设置选项卡式窗格类
 */
class AnnotationTabbedPane(
  val settingKey: SettingKey<AnnotationSettings>,
  private val defaultMap: Map<String, List<AnnotationItem>>,
) : JBTabbedPane(), Option {

  private val table: MutableMap<String, AnnotationTable> = HashMap()

  init {
    settingKey.getData().getFullMap(defaultMap).forEach { (key, value) ->
      val model = AnnotationTableModel(value)
      val jbTable = AnnotationTable(model)

      table[key] = jbTable

      val decorator = ToolbarDecorator.createDecorator(jbTable)
      decorator.setMoveDownAction { jbTable.moveDown() }
      decorator.setMoveUpAction { jbTable.moveUp() }
      val panel = decorator.createPanel()
      this.addTab(key.getSimpleName(), panel)
    }
  }

  /**
   * 显示设置
   *
   * @param setting 设置信息
   */
  override fun showSetting(setting: Settings) {
    setting.getData(settingKey).getFullMap(defaultMap)
      .forEach { (key, value) -> table[key]!!.setItemList(value) }
  }

  /**
   * 应用设置
   *
   * @param setting 设置信息
   */
  override fun applySetting(setting: Settings) {
    val map: MutableMap<String, List<AnnotationItem>> = HashMap()
    table.forEach { (key, value) -> map[key] = value.getItemList() }
    setting.putData(settingKey, AnnotationSettings(map))
  }
}