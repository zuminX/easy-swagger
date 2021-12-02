package com.zuminX.utils

import cn.hutool.core.util.StrUtil
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.zuminX.annotations.AnnotationStr
import com.zuminX.generator.builder.*
import com.zuminX.names.ClassName
import com.zuminX.names.annotation.API_IMPLICIT_PARAM
import com.zuminX.names.annotation.ControllerAnnotation
import com.zuminX.names.annotation.MappingAnnotation
import com.zuminX.names.annotation.REQUEST_MAPPING
import com.zuminX.service.getNotify

private const val CONTROLLER = "Controller"

private const val NAME = "name"

/**
 * 根据所选择的内容生成对应的Swagger注解
 *
 * @param selectionText 选择的文本
 */
fun PsiFile.generateAnnotation(selectionText: String?) {
  val projectPsi = toProjectPsi()
  if (StrUtil.isBlank(selectionText)) {
    projectPsi.generateAnnotation { projectPsi.generateDefault() }
  } else {
    projectPsi.generateAnnotation { projectPsi.generateSelection(selectionText) }
  }
}

/**
 * 生成Swagger注解
 *
 * @param runnable   任务
 */
private fun ProjectPsi<PsiClass>.generateAnnotation(runnable: Runnable) {
  if (element.modifierList == null) {
    project.getNotify().error("generator.annotation.error.unableGenerate")
    return
  }
  WriteCommandAction.runWriteCommandAction(project, runnable)
}

/**
 * 根据当前类生成Swagger注解
 */
private fun ProjectPsi<PsiClass>.generateDefault() {
  if (element.isController()) {
    generateControllerClassAnnotation()
    element.methods.forEach { replace(it).generateMethodAnnotation() }
  } else {
    generateDomainClassAnnotation()
    element.allFields.forEach { replace(it).generateFieldAnnotation() }
  }
}

/**
 * 根据所选择的内容生成对应的Swagger注解
 */
private fun ProjectPsi<PsiClass>.generateSelection(selectionText: String?) {
  if (selectionText == element.name) {
    generateClassAnnotation()
    return
  }
  val method = element.findMethodByName(selectionText)
  if (method != null) {
    replace(method).generateMethodAnnotation()
    return
  }
  val field = element.findFieldByNameIdentifier(selectionText)
  if (field != null) {
    replace(field).generateFieldAnnotation()
    return
  }
  project.getNotify().warning("generator.annotation.warning.chooseIllegal")
}

/**
 * 判断给定类是否为controller
 *
 * @return 若是controller则返回true，否则返回false
 */
private fun PsiClass.isController(): Boolean {
  val psiAnnotations = modifierList?.annotations ?: return false
  if (StrUtil.endWith(name, CONTROLLER)) {
    return true
  }
  return psiAnnotations.any { annotation ->
    ControllerAnnotation.getAll().any { it.equals(annotation.qualifiedName) }
  }
}

/**
 * 生成类注解
 */
private fun ProjectPsi<PsiClass>.generateClassAnnotation() =
  if (element.isController()) generateControllerClassAnnotation() else generateDomainClassAnnotation()

/**
 * 生成控制类注解
 */
private fun ProjectPsi<PsiClass>.generateControllerClassAnnotation() = ApiGeneratorInstance.add(this)

/**
 * 生成实体类注解
 */
private fun ProjectPsi<PsiClass>.generateDomainClassAnnotation() = ApiModelGeneratorInstance.add(this)

/**
 * 生成方法注解
 */
private fun ProjectPsi<PsiMethod>.generateMethodAnnotation() {
  generateMethodOperationAnnotation()
  generateMethodParameterAnnotation()
  psiFile.addImport(API_IMPLICIT_PARAM)
}

/**
 * 生成方法操作注解
 */
private fun ProjectPsi<PsiMethod>.generateMethodOperationAnnotation() = ApiOperationGeneratorInstance.add(this)

/**
 * 生成方法参数注解
 */
private fun ProjectPsi<PsiMethod>.generateMethodParameterAnnotation() = ApiImplicitParamsGeneratorInstance.add(this)

/**
 * 生成属性注解
 */
private fun ProjectPsi<PsiField>.generateFieldAnnotation() = ApiModelPropertyGeneratorInstance.add(this)

/**
 * 写入Swagger注解到文件
 *
 * @param annotationStr 注解字符串对象
 */
fun ProjectPsi<*>.doWrite(annotationStr: AnnotationStr) {
  val className = annotationStr.getClassName()
  psiFile.addImport(className)
  element.updateAnnotation(className.getSimpleName(), annotationStr.toStr())
}

/**
 * 导入类依赖
 *
 * @param className 类名对象
 */
private fun PsiFile.addImport(className: ClassName) {
  if (this !is PsiJavaFile) {
    return
  }
  val wantImportClass = getProject().getWantImportClass(className) ?: return
  val list = importList ?: return
  if (list.hasImportClass(className.qualifiedName)) {
    return
  }
  list.add(getPsiElementFactory().createImportStatement(wantImportClass))
}

/**
 * 获取期望导入的类
 *
 * @param className 类名对象
 * @return Psi类
 */
private fun Project.getWantImportClass(className: ClassName): PsiClass? {
  val psiClasses =
    PsiShortNamesCache.getInstance(this).getClassesByName(className.getSimpleName(), GlobalSearchScope.allScope(this))
  return psiClasses.firstOrNull { it.qualifiedName == className.qualifiedName }
}

/**
 * 更新注解
 *
 * @param simpleName           注解的简单类名
 * @param annotationText       注解内容
 */
private fun PsiModifierListOwner.updateAnnotation(simpleName: String, annotationText: String) {
  val modifierList = modifierList ?: return
  val psiAnnotation = findAnnotationByName(simpleName) ?: modifierList.addAnnotation(simpleName)
  val map = psiAnnotation.toMap()
  val attributes =
    psiAnnotation.getPsiElementFactory().createAnnotationFromText(annotationText, this).parameterList.attributes
  for (pair in attributes) {
    val name = pair.name
    if (map.containsKey(name) && name != null) {
      continue
    }
    val value = pair.value
    // 处理多重注解
    if (value is PsiArrayInitializerMemberValue && map[name] != null) {
      val multipleAnnotationsMap = (map[null] as PsiArrayInitializerMemberValue).resolveMultipleAnnotations()
      value.initializers.filterIsInstance<PsiAnnotation>().forEach { initializer ->
        val psiNameValuePairs = initializer.parameterList.attributes
        val memberValue = psiNameValuePairs.findMemberValueByName(NAME) ?: return@forEach
        val memberValueMap = multipleAnnotationsMap[memberValue.text] ?: return@forEach
        psiNameValuePairs.forEach {
          initializer.setDeclaredAttributeValue(it.name, memberValueMap.getOrDefault(it.name, it.value))
        }
      }
    }
    psiAnnotation.setDeclaredAttributeValue(name, value)
  }
}

/**
 * 解析多重注解
 *
 * @return key为注解的name属性的值，value为注解中属性的名称到值的映射
 */
private fun PsiArrayInitializerMemberValue.resolveMultipleAnnotations(): Map<String, Map<String?, PsiAnnotationMemberValue>> {
  return initializers.filterIsInstance<PsiAnnotation>()
    .filter { it.parameterList.attributes.findMemberValueByName(NAME) != null }
    .associate { it.text to it.toMap() }
}

/**
 * 寻找注解中指定名称的成员
 *
 * @param name              名称
 * @return Psi注解属性成员
 */
private fun Array<PsiNameValuePair>.findMemberValueByName(name: String): PsiAnnotationMemberValue? {
  return filter { name == it.name }.map { it.value }.firstOrNull()
}

/**
 * 获取RequestMapping注解中的method属性值
 *
 * @return method属性值
 */
fun PsiModifierListOwner.getMethodOfRequestMapping(): String? {
  val annotation = findRequestMapping() ?: return null
  val mapping = MappingAnnotation.findByQualifiedName(annotation.qualifiedName) ?: return null
  if (REQUEST_MAPPING != mapping) {
    return mapping.type
  }
  val values = annotation.getMemberValueByName("method")
  if (values.isEmpty()) {
    return null
  }
  if (values.size > 1) {
    annotation.project.getNotify().warning("generator.annotation.warning.multipleMethod")
  }
  return values[0]
}

/**
 * 获取RequestMapping注解中的value属性值
 *
 * @return val属性值
 */
fun PsiModifierListOwner.getValueOfRequestMapping(): String? {
  val annotation = findRequestMapping() ?: return null
  return annotation.getMemberTextByName("value")
}

/**
 * 寻找RequestMapping注解
 *
 * @return RequestMapping注解对应的Psi注解
 */
fun PsiModifierListOwner.findRequestMapping(): PsiAnnotation? {
  val list = modifierList ?: return null
  return list.annotations.firstOrNull { MappingAnnotation.findByQualifiedName(it.qualifiedName) != null }
}
