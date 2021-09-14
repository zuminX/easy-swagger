package com.zuminX.utils;

import cn.hutool.core.bean.OptionalBean;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

/**
 * PSI元素工具类
 */
@UtilityClass
public class PsiUtils {

  /**
   * 根据PSI元素获取PSI元素工厂
   *
   * @param psiElement PSI元素
   * @return PSI元素工厂
   */
  public static PsiElementFactory getPsiElementFactory(PsiElement psiElement) {
    return JavaPsiFacade.getElementFactory(psiElement.getProject());
  }

  /**
   * 查看导入列表中是否存在指定类名的类
   *
   * @param importList    导入列表
   * @param qualifiedName 导入类的全限定类名
   * @return 若存在则返回true，不存在则返回false
   */
  public static boolean hasImportClass(PsiImportList importList, String qualifiedName) {
    return Arrays.stream(importList.getAllImportStatements())
        .map(is -> OptionalBean.ofNullable(is.getImportReference()).getBean(PsiJavaCodeReferenceElement::getQualifiedName).get())
        .anyMatch(qualifiedName::equals);
  }

  /**
   * 通过名称找到对应的PSI注解
   *
   * @param name                 注解名称
   * @param psiModifierListOwner 当前写入对象
   * @return PSI注解
   */
  public static PsiAnnotation findAnnotationByName(String name, PsiModifierListOwner psiModifierListOwner) {
    PsiModifierList list = psiModifierListOwner.getModifierList();
    if (list == null) {
      return null;
    }
    PsiAnnotation[] annotations = list.getAnnotations();
    return Arrays.stream(annotations)
        .filter(annotation -> name.equals(PublicUtils.getSimpleNameByQualifiedName(annotation.getQualifiedName())))
        .findFirst()
        .orElse(null);
  }

  /**
   * 从PSI类中获取指定名称的PSI方法
   *
   * @param psiClass PSI类
   * @param name     方法名
   * @return PSI方法
   */
  public static PsiMethod findMethodByName(PsiClass psiClass, String name) {
    PsiMethod[] methods = psiClass.getMethods();
    return Arrays.stream(methods)
        .filter(psiMethod -> ObjectUtil.equals(name, psiMethod.getName()))
        .findFirst()
        .orElse(null);
  }

  /**
   * 从PSI类中获取指定名称标识符的PSI字段
   *
   * @param psiClass       PSI类
   * @param nameIdentifier 名称标识符
   * @return PSI字段
   */
  public static PsiField findFieldByNameIdentifier(PsiClass psiClass, String nameIdentifier) {
    PsiField[] fields = psiClass.getAllFields();
    return Arrays.stream(fields)
        .filter(psiField -> ObjectUtil.equals(nameIdentifier, psiField.getNameIdentifier().getText()))
        .findFirst()
        .orElse(null);
  }

  /**
   * 从PSI元素中获取注释
   *
   * @param psiElement PSI元素数组
   * @return PSI注释列表
   */
  public static List<PsiComment> getCommentByElement(PsiElement[] psiElement) {
    return Arrays.stream(psiElement)
        .filter(child -> (child instanceof PsiComment))
        .map(child -> (PsiComment) child)
        .collect(Collectors.toList());
  }

  /**
   * 获取注解属性值
   *
   * @param psiAnnotation 注解全路径
   * @param attributeName 注解属性名
   */
  public static List<String> getValueOfAnnotationMemberValue(PsiAnnotation psiAnnotation, String attributeName) {
    PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findDeclaredAttributeValue(attributeName);
    if (psiAnnotationMemberValue == null) {
      return ListUtil.empty();
    }
    return Arrays.stream(psiAnnotationMemberValue.getChildren())
        .flatMap(child -> Arrays.stream(child.getReferences()))
        .map(PsiReference::resolve)
        .filter(Objects::nonNull)
        .map(PsiElement::getText)
        .collect(Collectors.toList());
  }

  /**
   * 获取注解属性文本
   *
   * @param psiAnnotation 注解全路径
   * @param attributeName 注解属性名
   * @return 属性值
   */
  public static String getTextOfAnnotationMemberValue(PsiAnnotation psiAnnotation, String attributeName) {
    if (psiAnnotation == null) {
      return null;
    }
    PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findDeclaredAttributeValue(attributeName);
    return psiAnnotationMemberValue == null ? null : PublicUtils.unwrapInDoubleQuotes(psiAnnotationMemberValue.getText());
  }

  /**
   * 从PSI元素中获取注释
   * <p>
   * 若存在多个注释，则选取第一个
   *
   * @param psiElement PSI元素
   * @return 注释
   */
  public static String getFirstComment(PsiElement psiElement) {
    if (PublicUtils.isAssignable(PsiParameter.class, psiElement.getClass())) {
      PsiElement comment = psiElement.getParent().getParent();
      PsiIdentifier nameIdentifier = ((PsiParameter) psiElement).getNameIdentifier();
      if (comment == null || nameIdentifier == null) {
        return null;
      }
      return CoreUtils.getParamComment(comment.getText(), nameIdentifier.getText());
    }
    PsiComment comment = CollUtil.getFirst(getCommentByElement(psiElement.getChildren()));
    return comment != null ? CoreUtils.getCommentDesc(comment.getText()) : null;
  }

  /**
   * 获取指定注解的属性的名称到值的映射
   *
   * @param psiAnnotation Psi注解
   * @return 注解中属性的名称到值的映射
   */
  public static Map<String, PsiAnnotationMemberValue> getAnnotationNameToValue(PsiAnnotation psiAnnotation) {
    return Arrays.stream(psiAnnotation.getParameterList().getAttributes())
        .filter(pair -> pair.getValue() != null)
        .collect(Collectors.toMap(PsiNameValuePair::getName, PsiNameValuePair::getValue, (a, b) -> b));
  }
}
