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
public class ApiImplicitParam extends AnnotationStr {

  private AnnotationItem<String> name;

  private AnnotationItem<String> value;

  private AnnotationItem<String> defaultValue;

  private AnnotationItem<String> allowableValues;

  private AnnotationItem<Boolean> required;

  private AnnotationItem<String> access;

  private AnnotationItem<Boolean> allowMultiple;

  private AnnotationItem<String> dataType;

  private AnnotationItem<Class<?>> dataTypeClass;

  private AnnotationItem<String> paramType;

  private AnnotationItem<String> example;

  private AnnotationItem<String> type;

  private AnnotationItem<String> format;

  private AnnotationItem<Boolean> allowEmptyValue;

  private AnnotationItem<Boolean> readOnly;

  private AnnotationItem<String> collectionFormat;

  public static AnnotationStr getDefaultInstance() {
    ApiImplicitParam apiImplicitParam = new ApiImplicitParam();
    apiImplicitParam.setName(new AnnotationItem<>("name", true));
    apiImplicitParam.setValue(new AnnotationItem<>("value", true));
    apiImplicitParam.setDefaultValue(new AnnotationItem<>("defaultValue", true));
    apiImplicitParam.setAllowableValues(new AnnotationItem<>("allowableValues", true));
    apiImplicitParam.setRequired(new AnnotationItem<>("required", true));
    apiImplicitParam.setAccess(new AnnotationItem<>("access"));
    apiImplicitParam.setAllowMultiple(new AnnotationItem<>("allowMultiple"));
    apiImplicitParam.setDataType(new AnnotationItem<>("dataType"));
    apiImplicitParam.setDataTypeClass(new AnnotationItem<>("dataTypeClass", true));
    apiImplicitParam.setParamType(new AnnotationItem<>("paramType"));
    apiImplicitParam.setExample(new AnnotationItem<>("example"));
    apiImplicitParam.setType(new AnnotationItem<>("type"));
    apiImplicitParam.setFormat(new AnnotationItem<>("format"));
    apiImplicitParam.setAllowEmptyValue(new AnnotationItem<>("allowEmptyValue"));
    apiImplicitParam.setReadOnly(new AnnotationItem<>("readOnly"));
    apiImplicitParam.setCollectionFormat(new AnnotationItem<>("collectionFormat"));
    return apiImplicitParam;
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_IMPLICIT_PARAM;
  }
}
