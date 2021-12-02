package com.zuminX.window

import com.intellij.util.ui.FormBuilder
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

open class OptionForm(titleName: String, val sort: Int = 0) {

  private val formBuilder: FormBuilder
  private var content: JPanel? = null
  private var titleName: JLabel? = null
  private var optionItem: JPanel? = null

  init {
    this.titleName!!.text = titleName
    formBuilder = FormBuilder.createFormBuilder()
  }

  fun addOptionItem(component: JComponent) {
    formBuilder.addComponent(component)
  }

  fun addLabeledOptionItem(name: String, component: JComponent) {
    formBuilder.addLabeledComponent(name, component)
  }

  fun addLabeledOptionItem(name: String, component: JComponent, labelOnTop: Boolean) {
    formBuilder.addLabeledComponent(name, component, labelOnTop)
  }

  fun getContent(): JComponent {
    optionItem!!.add(formBuilder.panel, BorderLayout.CENTER)
    return content!!
  }

  fun getName(): String = titleName!!.text
}