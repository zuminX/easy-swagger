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
 * 对应io.swagger.annotations.Api
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Api extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String value;

  @AnnotationAttr(show = true)
  private List<String> tags;

  @AnnotationAttr
  private String produces;

  @AnnotationAttr
  private String consumes;

  @AnnotationAttr
  private String protocols;

  @AnnotationAttr
  private List<Authorization> authorizations;

  @AnnotationAttr
  private Boolean hidden;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API;
  }
}
