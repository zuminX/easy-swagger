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
 * 对应io.swagger.annotations.ApiOperation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiOperation extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String value;

  @AnnotationAttr(show = true)
  private String notes;

  @AnnotationAttr(show = true)
  private List<String> tags;

  @AnnotationAttr
  private Class<?> response;

  @AnnotationAttr
  private String responseContainer;

  @AnnotationAttr
  private String responseReference;

  @AnnotationAttr(show = true)
  private String httpMethod;

  @AnnotationAttr
  private String nickname;

  @AnnotationAttr
  private String produces;

  @AnnotationAttr
  private String consumes;

  @AnnotationAttr
  private String protocols;

  @AnnotationAttr
  private Boolean hidden;

  @AnnotationAttr
  private Integer code;

  @AnnotationAttr
  private Boolean ignoreJsonView;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_OPERATION;
  }

}