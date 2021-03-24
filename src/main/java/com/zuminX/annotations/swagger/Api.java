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
public class Api extends AnnotationStr {

  private AnnotationItem<String> value;

  private AnnotationItem<List<String>> tags;

  private AnnotationItem<String> produces;

  private AnnotationItem<String> consumes;

  private AnnotationItem<String> protocols;

  private AnnotationItem<Boolean> hidden;

  public static AnnotationStr getDefaultInstance() {
    Api api = new Api();
    api.setValue(new AnnotationItem<>("value", true));
    api.setTags(new AnnotationItem<>("tags", true));
    api.setProduces(new AnnotationItem<>("produces"));
    api.setConsumes(new AnnotationItem<>("consumes"));
    api.setProtocols(new AnnotationItem<>("protocols"));
    api.setHidden(new AnnotationItem<>("hidden"));
    return api;
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API;
  }
}
