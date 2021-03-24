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
public class ApiOperation extends AnnotationStr {

  private AnnotationItem<String> value;

  private AnnotationItem<String> notes;

  private AnnotationItem<List<String>> tags;

  private AnnotationItem<Class<?>> response;

  private AnnotationItem<String> responseContainer;

  private AnnotationItem<String> responseReference;

  private AnnotationItem<String> httpMethod;

  private AnnotationItem<String> nickname;

  private AnnotationItem<String> produces;

  private AnnotationItem<String> consumes;

  private AnnotationItem<String> protocols;

  private AnnotationItem<Boolean> hidden;

  private AnnotationItem<Integer> code;

  private AnnotationItem<Boolean> ignoreJsonView;

  public static AnnotationStr getDefaultInstance() {
    ApiOperation apiOperation = new ApiOperation();
    apiOperation.setValue(new AnnotationItem<>("value", true));
    apiOperation.setNotes(new AnnotationItem<>("notes", true));
    apiOperation.setTags(new AnnotationItem<>("tags", true));
    apiOperation.setResponse(new AnnotationItem<>("response"));
    apiOperation.setResponseContainer(new AnnotationItem<>("responseContainer"));
    apiOperation.setResponseReference(new AnnotationItem<>("responseReference"));
    apiOperation.setHttpMethod(new AnnotationItem<>("httpMethod", true));
    apiOperation.setNickname(new AnnotationItem<>("nickname"));
    apiOperation.setProduces(new AnnotationItem<>("produces"));
    apiOperation.setConsumes(new AnnotationItem<>("consumes"));
    apiOperation.setProtocols(new AnnotationItem<>("protocols"));
    apiOperation.setHidden(new AnnotationItem<>("hidden"));
    apiOperation.setCode(new AnnotationItem<>("code"));
    apiOperation.setIgnoreJsonView(new AnnotationItem<>("ignoreJsonView"));
    return apiOperation;
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_OPERATION;
  }

}