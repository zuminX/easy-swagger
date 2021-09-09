package com.zuminX.enums;

import com.zuminX.utils.PublicUtils;
import java.util.List;
import lombok.NoArgsConstructor;

/**
 * 对应io.swagger.annotations.ApiModelProperty.AccessMode
 */
@NoArgsConstructor
public class AccessMode extends AnnotationEnum {

  private static final List<AnnotationEnum> values = List.of(new AccessMode("AUTO"), new AccessMode("READ_ONLY"), new AccessMode("READ_WRITE"));

  /**
   * 该类的构造方法
   *
   * @param value 值
   */
  public AccessMode(String value) {
    super(value);
  }

  /**
   * 获取AccessMode的所有值
   *
   * @return 值列表
   */
  @Override
  public List<AnnotationEnum> getAll() {
    return values;
  }

  /**
   * 获取AccessMode的注解表示形式
   *
   * @return 表示形式
   */
  @Override
  public String getExpression() {
    return PublicUtils.concat("ApiModelProperty", ".", super.getExpression());
  }
}
