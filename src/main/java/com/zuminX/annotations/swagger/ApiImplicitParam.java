package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;

/**
 * 对应io.swagger.annotations.ApiImplicitParam
 */
public class ApiImplicitParam extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String name;

  @AnnotationAttr
  private String value;

  @AnnotationAttr(show = true)
  private String defaultValue;

  @AnnotationAttr(show = true)
  private String allowableValues;

  @AnnotationAttr
  private Boolean required;

  @AnnotationAttr
  private String access;

  @AnnotationAttr
  private Boolean allowMultiple;

  @AnnotationAttr
  private String dataType;

  @AnnotationAttr(show = true)
  private Class<?> dataTypeClass;

  @AnnotationAttr
  private String paramType;

  @AnnotationAttr
  private String example;

  @AnnotationAttr
  private String type;

  @AnnotationAttr
  private String format;

  @AnnotationAttr
  private Boolean allowEmptyValue;

  @AnnotationAttr
  private Boolean readOnly;

  @AnnotationAttr
  private String collectionFormat;

  public ApiImplicitParam(String name, String value, String defaultValue, String allowableValues, Boolean required, String access, Boolean allowMultiple,
      String dataType, Class<?> dataTypeClass, String paramType, String example, String type, String format, Boolean allowEmptyValue, Boolean readOnly,
      String collectionFormat) {
    this.name = name;
    this.value = value;
    this.defaultValue = defaultValue;
    this.allowableValues = allowableValues;
    this.required = required;
    this.access = access;
    this.allowMultiple = allowMultiple;
    this.dataType = dataType;
    this.dataTypeClass = dataTypeClass;
    this.paramType = paramType;
    this.example = example;
    this.type = type;
    this.format = format;
    this.allowEmptyValue = allowEmptyValue;
    this.readOnly = readOnly;
    this.collectionFormat = collectionFormat;
  }

  public ApiImplicitParam() {
  }

  public static ApiImplicitParamBuilder builder() {
    return new ApiImplicitParamBuilder();
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_IMPLICIT_PARAM;
  }

  public String getName() {
    return this.name;
  }

  public String getValue() {
    return this.value;
  }

  public String getDefaultValue() {
    return this.defaultValue;
  }

  public String getAllowableValues() {
    return this.allowableValues;
  }

  public Boolean getRequired() {
    return this.required;
  }

  public String getAccess() {
    return this.access;
  }

  public Boolean getAllowMultiple() {
    return this.allowMultiple;
  }

  public String getDataType() {
    return this.dataType;
  }

  public Class<?> getDataTypeClass() {
    return this.dataTypeClass;
  }

  public String getParamType() {
    return this.paramType;
  }

  public String getExample() {
    return this.example;
  }

  public String getType() {
    return this.type;
  }

  public String getFormat() {
    return this.format;
  }

  public Boolean getAllowEmptyValue() {
    return this.allowEmptyValue;
  }

  public Boolean getReadOnly() {
    return this.readOnly;
  }

  public String getCollectionFormat() {
    return this.collectionFormat;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public void setAllowableValues(String allowableValues) {
    this.allowableValues = allowableValues;
  }

  public void setRequired(Boolean required) {
    this.required = required;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public void setAllowMultiple(Boolean allowMultiple) {
    this.allowMultiple = allowMultiple;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public void setDataTypeClass(Class<?> dataTypeClass) {
    this.dataTypeClass = dataTypeClass;
  }

  public void setParamType(String paramType) {
    this.paramType = paramType;
  }

  public void setExample(String example) {
    this.example = example;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public void setAllowEmptyValue(Boolean allowEmptyValue) {
    this.allowEmptyValue = allowEmptyValue;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }

  public void setCollectionFormat(String collectionFormat) {
    this.collectionFormat = collectionFormat;
  }

  public String toString() {
    return "ApiImplicitParam(name=" + this.getName() + ", value=" + this.getValue() + ", defaultValue=" + this.getDefaultValue() + ", allowableValues="
        + this.getAllowableValues() + ", required=" + this.getRequired() + ", access=" + this.getAccess() + ", allowMultiple=" + this.getAllowMultiple()
        + ", dataType=" + this.getDataType() + ", dataTypeClass=" + this.getDataTypeClass() + ", paramType=" + this.getParamType() + ", example="
        + this.getExample() + ", type=" + this.getType() + ", format=" + this.getFormat() + ", allowEmptyValue=" + this.getAllowEmptyValue() + ", readOnly="
        + this.getReadOnly() + ", collectionFormat=" + this.getCollectionFormat() + ")";
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ApiImplicitParam)) {
      return false;
    }
    final ApiImplicitParam other = (ApiImplicitParam) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final Object this$name = this.getName();
    final Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
      return false;
    }
    final Object this$value = this.getValue();
    final Object other$value = other.getValue();
    if (this$value == null ? other$value != null : !this$value.equals(other$value)) {
      return false;
    }
    final Object this$defaultValue = this.getDefaultValue();
    final Object other$defaultValue = other.getDefaultValue();
    if (this$defaultValue == null ? other$defaultValue != null : !this$defaultValue.equals(other$defaultValue)) {
      return false;
    }
    final Object this$allowableValues = this.getAllowableValues();
    final Object other$allowableValues = other.getAllowableValues();
    if (this$allowableValues == null ? other$allowableValues != null : !this$allowableValues.equals(other$allowableValues)) {
      return false;
    }
    final Object this$required = this.getRequired();
    final Object other$required = other.getRequired();
    if (this$required == null ? other$required != null : !this$required.equals(other$required)) {
      return false;
    }
    final Object this$access = this.getAccess();
    final Object other$access = other.getAccess();
    if (this$access == null ? other$access != null : !this$access.equals(other$access)) {
      return false;
    }
    final Object this$allowMultiple = this.getAllowMultiple();
    final Object other$allowMultiple = other.getAllowMultiple();
    if (this$allowMultiple == null ? other$allowMultiple != null : !this$allowMultiple.equals(other$allowMultiple)) {
      return false;
    }
    final Object this$dataType = this.getDataType();
    final Object other$dataType = other.getDataType();
    if (this$dataType == null ? other$dataType != null : !this$dataType.equals(other$dataType)) {
      return false;
    }
    final Object this$dataTypeClass = this.getDataTypeClass();
    final Object other$dataTypeClass = other.getDataTypeClass();
    if (this$dataTypeClass == null ? other$dataTypeClass != null : !this$dataTypeClass.equals(other$dataTypeClass)) {
      return false;
    }
    final Object this$paramType = this.getParamType();
    final Object other$paramType = other.getParamType();
    if (this$paramType == null ? other$paramType != null : !this$paramType.equals(other$paramType)) {
      return false;
    }
    final Object this$example = this.getExample();
    final Object other$example = other.getExample();
    if (this$example == null ? other$example != null : !this$example.equals(other$example)) {
      return false;
    }
    final Object this$type = this.getType();
    final Object other$type = other.getType();
    if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
      return false;
    }
    final Object this$format = this.getFormat();
    final Object other$format = other.getFormat();
    if (this$format == null ? other$format != null : !this$format.equals(other$format)) {
      return false;
    }
    final Object this$allowEmptyValue = this.getAllowEmptyValue();
    final Object other$allowEmptyValue = other.getAllowEmptyValue();
    if (this$allowEmptyValue == null ? other$allowEmptyValue != null : !this$allowEmptyValue.equals(other$allowEmptyValue)) {
      return false;
    }
    final Object this$readOnly = this.getReadOnly();
    final Object other$readOnly = other.getReadOnly();
    if (this$readOnly == null ? other$readOnly != null : !this$readOnly.equals(other$readOnly)) {
      return false;
    }
    final Object this$collectionFormat = this.getCollectionFormat();
    final Object other$collectionFormat = other.getCollectionFormat();
    if (this$collectionFormat == null ? other$collectionFormat != null : !this$collectionFormat.equals(other$collectionFormat)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof ApiImplicitParam;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = super.hashCode();
    final Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    final Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    final Object $defaultValue = this.getDefaultValue();
    result = result * PRIME + ($defaultValue == null ? 43 : $defaultValue.hashCode());
    final Object $allowableValues = this.getAllowableValues();
    result = result * PRIME + ($allowableValues == null ? 43 : $allowableValues.hashCode());
    final Object $required = this.getRequired();
    result = result * PRIME + ($required == null ? 43 : $required.hashCode());
    final Object $access = this.getAccess();
    result = result * PRIME + ($access == null ? 43 : $access.hashCode());
    final Object $allowMultiple = this.getAllowMultiple();
    result = result * PRIME + ($allowMultiple == null ? 43 : $allowMultiple.hashCode());
    final Object $dataType = this.getDataType();
    result = result * PRIME + ($dataType == null ? 43 : $dataType.hashCode());
    final Object $dataTypeClass = this.getDataTypeClass();
    result = result * PRIME + ($dataTypeClass == null ? 43 : $dataTypeClass.hashCode());
    final Object $paramType = this.getParamType();
    result = result * PRIME + ($paramType == null ? 43 : $paramType.hashCode());
    final Object $example = this.getExample();
    result = result * PRIME + ($example == null ? 43 : $example.hashCode());
    final Object $type = this.getType();
    result = result * PRIME + ($type == null ? 43 : $type.hashCode());
    final Object $format = this.getFormat();
    result = result * PRIME + ($format == null ? 43 : $format.hashCode());
    final Object $allowEmptyValue = this.getAllowEmptyValue();
    result = result * PRIME + ($allowEmptyValue == null ? 43 : $allowEmptyValue.hashCode());
    final Object $readOnly = this.getReadOnly();
    result = result * PRIME + ($readOnly == null ? 43 : $readOnly.hashCode());
    final Object $collectionFormat = this.getCollectionFormat();
    result = result * PRIME + ($collectionFormat == null ? 43 : $collectionFormat.hashCode());
    return result;
  }

  public static class ApiImplicitParamBuilder {

    private String name;
    private String value;
    private String defaultValue;
    private String allowableValues;
    private Boolean required;
    private String access;
    private Boolean allowMultiple;
    private String dataType;
    private Class<?> dataTypeClass;
    private String paramType;
    private String example;
    private String type;
    private String format;
    private Boolean allowEmptyValue;
    private Boolean readOnly;
    private String collectionFormat;

    ApiImplicitParamBuilder() {
    }

    public ApiImplicitParamBuilder name(String name) {
      this.name = name;
      return this;
    }

    public ApiImplicitParamBuilder value(String value) {
      this.value = value;
      return this;
    }

    public ApiImplicitParamBuilder defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    public ApiImplicitParamBuilder allowableValues(String allowableValues) {
      this.allowableValues = allowableValues;
      return this;
    }

    public ApiImplicitParamBuilder required(Boolean required) {
      this.required = required;
      return this;
    }

    public ApiImplicitParamBuilder access(String access) {
      this.access = access;
      return this;
    }

    public ApiImplicitParamBuilder allowMultiple(Boolean allowMultiple) {
      this.allowMultiple = allowMultiple;
      return this;
    }

    public ApiImplicitParamBuilder dataType(String dataType) {
      this.dataType = dataType;
      return this;
    }

    public ApiImplicitParamBuilder dataTypeClass(Class<?> dataTypeClass) {
      this.dataTypeClass = dataTypeClass;
      return this;
    }

    public ApiImplicitParamBuilder paramType(String paramType) {
      this.paramType = paramType;
      return this;
    }

    public ApiImplicitParamBuilder example(String example) {
      this.example = example;
      return this;
    }

    public ApiImplicitParamBuilder type(String type) {
      this.type = type;
      return this;
    }

    public ApiImplicitParamBuilder format(String format) {
      this.format = format;
      return this;
    }

    public ApiImplicitParamBuilder allowEmptyValue(Boolean allowEmptyValue) {
      this.allowEmptyValue = allowEmptyValue;
      return this;
    }

    public ApiImplicitParamBuilder readOnly(Boolean readOnly) {
      this.readOnly = readOnly;
      return this;
    }

    public ApiImplicitParamBuilder collectionFormat(String collectionFormat) {
      this.collectionFormat = collectionFormat;
      return this;
    }

    public ApiImplicitParam build() {
      return new ApiImplicitParam(name, value, defaultValue, allowableValues, required, access, allowMultiple, dataType, dataTypeClass, paramType, example,
          type,
          format, allowEmptyValue, readOnly, collectionFormat);
    }

    public String toString() {
      return "ApiImplicitParam.ApiImplicitParamBuilder(name=" + this.name + ", value=" + this.value + ", defaultValue=" + this.defaultValue
          + ", allowableValues="
          + this.allowableValues + ", required=" + this.required + ", access=" + this.access + ", allowMultiple=" + this.allowMultiple + ", dataType="
          + this.dataType + ", dataTypeClass=" + this.dataTypeClass + ", paramType=" + this.paramType + ", example=" + this.example + ", type=" + this.type
          + ", format=" + this.format + ", allowEmptyValue=" + this.allowEmptyValue + ", readOnly=" + this.readOnly + ", collectionFormat="
          + this.collectionFormat + ")";
    }
  }
}
