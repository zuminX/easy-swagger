package com.zuminX.utils

import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil

/**
 * 携带项目信息的psi元素类
 *
 * @param <T> psi元素类型
 */
class ProjectPsi<T : PsiModifierListOwner>(val psiFile: PsiFile, val element: T) {

  val project: Project
    get() = psiFile.project

  fun <V : PsiModifierListOwner> replace(element: V): ProjectPsi<V> {
    return ProjectPsi(psiFile, element)
  }

}

fun PsiFile.toProjectPsi() = ProjectPsi(this, PsiTreeUtil.findChildOfAnyType(this, PsiClass::class.java)!!)

/**
 * 根据PSI元素获取PSI元素工厂
 *
 * @return PSI元素工厂
 */
fun PsiElement.getPsiElementFactory(): PsiElementFactory {
  return JavaPsiFacade.getElementFactory(project)
}

/**
 * 查看导入列表中是否存在指定类名的类
 *
 * @param qualifiedName 导入类的全限定类名
 * @return 若存在则返回true，不存在则返回false
 */
fun PsiImportList.hasImportClass(qualifiedName: String): Boolean {
  return allImportStatements
    .mapNotNull { it.importReference?.qualifiedName }
    .any { it == qualifiedName }
}

/**
 * 通过名称找到对应的PSI注解
 *
 * @param name                 注解名称
 * @return PSI注解
 */
fun PsiModifierListOwner.findAnnotationByName(name: String): PsiAnnotation? {
  val list = modifierList ?: return null
  return list.annotations.firstOrNull { it.qualifiedName.getSimpleName() == name }
}

/**
 * 从PSI类中获取指定名称的PSI方法
 *
 * @param name     方法名
 * @return PSI方法
 */
fun PsiClass.findMethodByName(name: String?): PsiMethod? {
  return methods.firstOrNull { it.name == name }
}

/**
 * 从PSI类中获取指定名称标识符的PSI字段
 *
 * @param nameIdentifier 名称标识符
 * @return PSI字段
 */
fun PsiClass.findFieldByNameIdentifier(nameIdentifier: String?): PsiField? {
  return allFields.firstOrNull { it.nameIdentifier.text == nameIdentifier }
}

/**
 * 获取注解属性值
 *
 * @param attributeName 注解属性名
 */
fun PsiAnnotation.getMemberValueByName(attributeName: String): List<String> {
  val psiAnnotationMemberValue = findDeclaredAttributeValue(attributeName) ?: return emptyList()
  return psiAnnotationMemberValue.children
    .map { it.references.toList() }
    .flatten()
    .mapNotNull(PsiReference::resolve)
    .map(PsiElement::getText)
}

/**
 * 获取注解属性文本
 *
 * @param attributeName 注解属性名
 * @return 属性值
 */
fun PsiAnnotation?.getMemberTextByName(attributeName: String): String? {
  if (this == null) {
    return null
  }
  val psiAnnotationMemberValue = findDeclaredAttributeValue(attributeName) ?: return null
  return psiAnnotationMemberValue.text.unwrapInDoubleQuotes()
}

/**
 * 从PSI元素中获取注释
 * 若存在多个注释，则选取第一个
 *
 * @return 注释
 */
fun PsiElement.getFirstComment(): String? {
  if (this is PsiParameter) {
    val commentText = parent?.parent?.text ?: return null
    val nameIdentifierText = nameIdentifier?.text ?: return null
    return commentText.getParamComment(nameIdentifierText)
  }
  val text = children.filterIsInstance<PsiComment>().firstOrNull()?.text ?: return null
  return text.getCommentDesc()
}

/**
 * 获取指定注解的属性的名称到值的映射
 *
 * @return 注解中属性的名称到值的映射
 */
fun PsiAnnotation.toMap(): Map<String?, PsiAnnotationMemberValue> {
  return parameterList.attributes
    .filter { it.name != null && it.value != null }
    .associate { it.name!! to it.value!! }
}

