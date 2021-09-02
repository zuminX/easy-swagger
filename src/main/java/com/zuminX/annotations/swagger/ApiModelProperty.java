package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 对应io.swagger.annotations.ApiModelProperty
 */
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

  @AnnotationAttr(show = true, defaultText = "true")
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

  @AnnotationAttr
  private List<Extension> extensions;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_MODEL_PROPERTY;
  }
}
