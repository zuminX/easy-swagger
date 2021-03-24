package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationItem;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiModelProperty extends AnnotationStr {

  private AnnotationItem<String> value;

  private AnnotationItem<String> name;

  private AnnotationItem<String> allowableValues;

  private AnnotationItem<String> access;

  private AnnotationItem<String> notes;

  private AnnotationItem<String> dataType;

  private AnnotationItem<Boolean> required;

  private AnnotationItem<Integer> position;

  private AnnotationItem<Boolean> hidden;

  private AnnotationItem<String> example;

  private AnnotationItem<String> reference;

  private AnnotationItem<Boolean> allowEmptyValue;

  public static AnnotationStr getDefaultInstance() {
    ApiModelProperty apiModelAnnotation = new ApiModelProperty();
    apiModelAnnotation.setValue(new AnnotationItem<>("value", true));
    apiModelAnnotation.setName(new AnnotationItem<>("name", true));
    apiModelAnnotation.setAllowableValues(new AnnotationItem<>("allowableValues"));
    apiModelAnnotation.setAccess(new AnnotationItem<>("access"));
    apiModelAnnotation.setNotes(new AnnotationItem<>("notes"));
    apiModelAnnotation.setDataType(new AnnotationItem<>("dataType"));
    apiModelAnnotation.setRequired(new AnnotationItem<>("required", true));
    apiModelAnnotation.setPosition(new AnnotationItem<>("position"));
    apiModelAnnotation.setHidden(new AnnotationItem<>("hidden"));
    apiModelAnnotation.setExample(new AnnotationItem<>("example"));
    apiModelAnnotation.setReference(new AnnotationItem<>("reference"));
    apiModelAnnotation.setAllowEmptyValue(new AnnotationItem<>("allowEmptyValue"));
    return apiModelAnnotation;
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_MODEL_PROPERTY;
  }

}
