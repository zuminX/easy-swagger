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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiModel extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String description;

  @AnnotationAttr
  private Class<?> parent;

  @AnnotationAttr
  private String discriminator;

  @AnnotationAttr
  private List<Class<?>> subTypes;

  @AnnotationAttr
  private String reference;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_MODEL;
  }
}