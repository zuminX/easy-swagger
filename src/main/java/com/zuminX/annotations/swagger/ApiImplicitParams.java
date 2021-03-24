package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationItem;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiImplicitParams extends AnnotationStr {

  private AnnotationItem<List<ApiImplicitParam>> value;

  public static AnnotationStr getDefaultInstance() {
    ApiImplicitParams apiImplicitParamAnnotation = new ApiImplicitParams();
    apiImplicitParamAnnotation.setValue(new AnnotationItem<>("value", true));
    return apiImplicitParamAnnotation;
  }

  public static void main(String[] args) {
    ApiImplicitParams apiImplicitParams = (ApiImplicitParams) getDefaultInstance();
    System.out.println(Arrays.toString(apiImplicitParams.getClass().getTypeParameters()));
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_IMPLICIT_PARAMS;
  }
}
