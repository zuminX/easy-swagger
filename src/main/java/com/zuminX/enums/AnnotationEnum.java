package com.zuminX.enums;

import com.zuminX.utils.PublicUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 枚举类型的注解属性
 */
@NoArgsConstructor
@AllArgsConstructor
public abstract class AnnotationEnum {

  private String value;

  /**
   * 获取该类型的所有可能值
   *
   * @return 所有属性值
   */
  public abstract List<AnnotationEnum> getAll();

  /**
   * 获取默认值
   *
   * @return 默认值
   */
  public AnnotationEnum getDefault() {
    return getAll().get(0);
  }

  /**
   * 获取该枚举类型的简单类名
   *
   * @return 简单类名
   */
  public String getClassName() {
    return getClass().getSimpleName();
  }

  /**
   * 获取该对象的注解表示形式
   *
   * @return 表示形式
   */
  public String getExpression() {
    return PublicUtils.concat(getClassName(), ".", value);
  }

}
