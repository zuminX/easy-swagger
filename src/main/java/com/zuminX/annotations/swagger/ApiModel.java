package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationItem;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiModel extends AnnotationStr {

  private AnnotationItem<String> description;

  private AnnotationItem<Class<?>> parent;

  private AnnotationItem<String> discriminator;

  private AnnotationItem<List<Class<?>>> subTypes;

  private AnnotationItem<String> reference;

  public static AnnotationStr getDefaultInstance() {
    ApiModel apiModel = new ApiModel();
    apiModel.setDescription(new AnnotationItem<>("description", true));
    apiModel.setParent(new AnnotationItem<>("parent"));
    apiModel.setDiscriminator(new AnnotationItem<>("discriminator"));
    apiModel.setSubTypes(new AnnotationItem<>("subTypes"));
    apiModel.setReference(new AnnotationItem<>("reference"));
    return apiModel;
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_MODEL;
  }
}