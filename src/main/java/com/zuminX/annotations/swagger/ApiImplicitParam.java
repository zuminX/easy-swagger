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
public class ApiImplicitParam extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String name;

  @AnnotationAttr
  private String value;

  @AnnotationAttr(show = true)
  private String defaultValue;

  @AnnotationAttr(show = true)
  private String allowableValues;

  @AnnotationAttr
  private Boolean required;

  @AnnotationAttr
  private String access;

  @AnnotationAttr
  private Boolean allowMultiple;

  @AnnotationAttr
  private String dataType;

  @AnnotationAttr(show = true)
  private Class<?> dataTypeClass;

  @AnnotationAttr
  private String paramType;

  @AnnotationAttr
  private String example;

  @AnnotationAttr
  private String type;

  @AnnotationAttr
  private String format;

  @AnnotationAttr
  private Boolean allowEmptyValue;

  @AnnotationAttr
  private Boolean readOnly;

  @AnnotationAttr
  private String collectionFormat;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_IMPLICIT_PARAM;
  }
}
