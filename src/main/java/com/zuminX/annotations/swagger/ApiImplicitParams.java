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
 * 对应io.swagger.annotations.ApiImplicitParams
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiImplicitParams extends AnnotationStr {

  @AnnotationAttr(show = true)
  private List<ApiImplicitParam> value;

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_IMPLICIT_PARAMS;
  }
}
