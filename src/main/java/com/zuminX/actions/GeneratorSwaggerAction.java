package com.zuminX.actions;

import cn.hutool.core.util.StrUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.zuminX.config.SystemSetting;
import com.zuminX.service.Notify;
import com.zuminX.settings.Settings;
import com.zuminX.utils.GeneratorUtils;
import com.zuminX.window.form.AnnotationForm;

/**
 * 生成Swagger注解的动作类
 */
public class GeneratorSwaggerAction extends AnAction {

  /**
   * 执行生成Swagger注解
   *
   * @param anActionEvent 动作事件
   */
  @Override
  public void actionPerformed(AnActionEvent anActionEvent) {
    Project project = anActionEvent.getProject();
    // 获取当前文件对象
    Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
    assert editor != null;
    assert project != null;

    PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
    String selectionText = editor.getSelectionModel().getSelectedText();

    AnnotationForm.loadSettingsData();

    try {
      if (StrUtil.isBlank(selectionText)) {
        GeneratorUtils.generate(psiFile);
      } else {
        GeneratorUtils.generate(psiFile, selectionText);
      }
    } catch (Exception e) {
      Notify.getInstance(project).error("generator.annotation.error.unknown");
      e.printStackTrace();
    }
  }

}
