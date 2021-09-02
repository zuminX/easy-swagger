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

/**
 * 对应io.swagger.annotations.ExtensionProperty
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExtensionProperty extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String name;

  @AnnotationAttr(show = true)
  private String value;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.EXTENSION_PROPERTY;
  }
}
