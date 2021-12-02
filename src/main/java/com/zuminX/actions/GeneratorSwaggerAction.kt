package com.zuminX.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.util.PsiUtilBase
import com.zuminX.service.getNotify
import com.zuminX.utils.generateAnnotation
import com.zuminX.window.form.AnnotationForm

/**
 * 生成Swagger注解的动作类
 */
class GeneratorSwaggerAction : AnAction() {

  /**
   * 执行生成Swagger注解
   *
   * @param e 动作事件
   */
  override fun actionPerformed(e: AnActionEvent) {
    val project = e.project!!
    // 获取当前文件对象
    val editor = e.getData(PlatformDataKeys.EDITOR)!!
    val psiFile = PsiUtilBase.getPsiFileInEditor(editor, project)!!
    val selectedText = editor.selectionModel.selectedText

    AnnotationForm.loadSettingsData()

    try {
      psiFile.generateAnnotation(selectedText)
    } catch (e: Exception) {
      project.getNotify().error("generator.annotation.error.unknown")
      e.printStackTrace()
    }
  }


}