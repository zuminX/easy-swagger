package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiModelProperty extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String value;

  @AnnotationAttr(show = true)
  private String name;

  @AnnotationAttr
  private String allowableValues;

  @AnnotationAttr
  private String access;

  @AnnotationAttr
  private String notes;

  @AnnotationAttr
  private String dataType;

  @AnnotationAttr(show = true)
  private Boolean required;

  @AnnotationAttr
  private Integer position;

  @AnnotationAttr
  private Boolean hidden;

  @AnnotationAttr
  private String example;

  @AnnotationAttr
  private String reference;

  @AnnotationAttr
  private Boolean allowEmptyValue;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_MODEL_PROPERTY;
  }

}
