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
 * 对应io.swagger.annotations.ResponseHeader
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponseHeader extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String name;

  @AnnotationAttr
  private String description;

  @AnnotationAttr(show = true)
  private ClassName response;

  @AnnotationAttr
  private String responseContainer;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.RESPONSE_HEADER;
  }
}
