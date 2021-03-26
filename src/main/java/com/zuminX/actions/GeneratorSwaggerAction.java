package com.zuminX.actions;

import cn.hutool.core.util.StrUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.zuminX.utils.GeneratorUtils;
import com.zuminX.window.form.SwaggerAnnotationForm;
import com.zuminX.window.tabs.domain.AnnotationSettings;

public class GeneratorSwaggerAction extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent anActionEvent) {
    Project project = anActionEvent.getProject();
    // 获取当前文件对象
    Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
    assert editor != null;
    assert project != null;

    PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
    String selectionText = editor.getSelectionModel().getSelectedText();

    SwaggerAnnotationForm.loadSettingsData();

    GeneratorUtils generatorUtils = new GeneratorUtils(project, psiFile);
    if (StrUtil.isBlank(selectionText)) {
      generatorUtils.generate();
    } else {
      generatorUtils.generate(selectionText);
    }
  }

}
