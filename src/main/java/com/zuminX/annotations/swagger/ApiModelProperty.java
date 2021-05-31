package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;

/**
 * 对应io.swagger.annotations.ApiModelProperty
 */
public class ApiModelProperty extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String value;

  @AnnotationAttr(show = true)
  private String name;

  @AnnotationAttr
  private String allowableValues;

  @AnnotationAttr
  private String access;

  @AnnotationAttr
  private String notes;

  @AnnotationAttr
  private String dataType;

  @AnnotationAttr(show = true)
  private Boolean required;

  @AnnotationAttr
  private Integer position;

  @AnnotationAttr
  private Boolean hidden;

  @AnnotationAttr
  private String example;

  @AnnotationAttr
  private String reference;

  @AnnotationAttr
  private Boolean allowEmptyValue;

  public ApiModelProperty(String value, String name, String allowableValues, String access, String notes, String dataType, Boolean required, Integer position,
      Boolean hidden, String example, String reference, Boolean allowEmptyValue) {
    this.value = value;
    this.name = name;
    this.allowableValues = allowableValues;
    this.access = access;
    this.notes = notes;
    this.dataType = dataType;
    this.required = required;
    this.position = position;
    this.hidden = hidden;
    this.example = example;
    this.reference = reference;
    this.allowEmptyValue = allowEmptyValue;
  }

  public ApiModelProperty() {
  }

  public static ApiModelPropertyBuilder builder() {
    return new ApiModelPropertyBuilder();
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_MODEL_PROPERTY;
  }

  public String getValue() {
    return this.value;
  }

  public String getName() {
    return this.name;
  }

  public String getAllowableValues() {
    return this.allowableValues;
  }

  public String getAccess() {
    return this.access;
  }

  public String getNotes() {
    return this.notes;
  }

  public String getDataType() {
    return this.dataType;
  }

  public Boolean getRequired() {
    return this.required;
  }

  public Integer getPosition() {
    return this.position;
  }

  public Boolean getHidden() {
    return this.hidden;
  }

  public String getExample() {
    return this.example;
  }

  public String getReference() {
    return this.reference;
  }

  public Boolean getAllowEmptyValue() {
    return this.allowEmptyValue;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAllowableValues(String allowableValues) {
    this.allowableValues = allowableValues;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public void setRequired(Boolean required) {
    this.required = required;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }

  public void setExample(String example) {
    this.example = example;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public void setAllowEmptyValue(Boolean allowEmptyValue) {
    this.allowEmptyValue = allowEmptyValue;
  }

  public String toString() {
    return "ApiModelProperty(value=" + this.getValue() + ", name=" + this.getName() + ", allowableValues=" + this.getAllowableValues() + ", access="
        + this.getAccess() + ", notes=" + this.getNotes() + ", dataType=" + this.getDataType() + ", required=" + this.getRequired() + ", position="
        + this.getPosition() + ", hidden=" + this.getHidden() + ", example=" + this.getExample() + ", reference=" + this.getReference() + ", allowEmptyValue="
        + this.getAllowEmptyValue() + ")";
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ApiModelProperty)) {
      return false;
    }
    final ApiModelProperty other = (ApiModelProperty) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final Object this$value = this.getValue();
    final Object other$value = other.getValue();
    if (this$value == null ? other$value != null : !this$value.equals(other$value)) {
      return false;
    }
    final Object this$name = this.getName();
    final Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
      return false;
    }
    final Object this$allowableValues = this.getAllowableValues();
    final Object other$allowableValues = other.getAllowableValues();
    if (this$allowableValues == null ? other$allowableValues != null : !this$allowableValues.equals(other$allowableValues)) {
      return false;
    }
    final Object this$access = this.getAccess();
    final Object other$access = other.getAccess();
    if (this$access == null ? other$access != null : !this$access.equals(other$access)) {
      return false;
    }
    final Object this$notes = this.getNotes();
    final Object other$notes = other.getNotes();
    if (this$notes == null ? other$notes != null : !this$notes.equals(other$notes)) {
      return false;
    }
    final Object this$dataType = this.getDataType();
    final Object other$dataType = other.getDataType();
    if (this$dataType == null ? other$dataType != null : !this$dataType.equals(other$dataType)) {
      return false;
    }
    final Object this$required = this.getRequired();
    final Object other$required = other.getRequired();
    if (this$required == null ? other$required != null : !this$required.equals(other$required)) {
      return false;
    }
    final Object this$position = this.getPosition();
    final Object other$position = other.getPosition();
    if (this$position == null ? other$position != null : !this$position.equals(other$position)) {
      return false;
    }
    final Object this$hidden = this.getHidden();
    final Object other$hidden = other.getHidden();
    if (this$hidden == null ? other$hidden != null : !this$hidden.equals(other$hidden)) {
      return false;
    }
    final Object this$example = this.getExample();
    final Object other$example = other.getExample();
    if (this$example == null ? other$example != null : !this$example.equals(other$example)) {
      return false;
    }
    final Object this$reference = this.getReference();
    final Object other$reference = other.getReference();
    if (this$reference == null ? other$reference != null : !this$reference.equals(other$reference)) {
      return false;
    }
    final Object this$allowEmptyValue = this.getAllowEmptyValue();
    final Object other$allowEmptyValue = other.getAllowEmptyValue();
    if (this$allowEmptyValue == null ? other$allowEmptyValue != null : !this$allowEmptyValue.equals(other$allowEmptyValue)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof ApiModelProperty;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = super.hashCode();
    final Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    final Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    final Object $allowableValues = this.getAllowableValues();
    result = result * PRIME + ($allowableValues == null ? 43 : $allowableValues.hashCode());
    final Object $access = this.getAccess();
    result = result * PRIME + ($access == null ? 43 : $access.hashCode());
    final Object $notes = this.getNotes();
    result = result * PRIME + ($notes == null ? 43 : $notes.hashCode());
    final Object $dataType = this.getDataType();
    result = result * PRIME + ($dataType == null ? 43 : $dataType.hashCode());
    final Object $required = this.getRequired();
    result = result * PRIME + ($required == null ? 43 : $required.hashCode());
    final Object $position = this.getPosition();
    result = result * PRIME + ($position == null ? 43 : $position.hashCode());
    final Object $hidden = this.getHidden();
    result = result * PRIME + ($hidden == null ? 43 : $hidden.hashCode());
    final Object $example = this.getExample();
    result = result * PRIME + ($example == null ? 43 : $example.hashCode());
    final Object $reference = this.getReference();
    result = result * PRIME + ($reference == null ? 43 : $reference.hashCode());
    final Object $allowEmptyValue = this.getAllowEmptyValue();
    result = result * PRIME + ($allowEmptyValue == null ? 43 : $allowEmptyValue.hashCode());
    return result;
  }

  public static class ApiModelPropertyBuilder {

    private String value;
    private String name;
    private String allowableValues;
    private String access;
    private String notes;
    private String dataType;
    private Boolean required;
    private Integer position;
    private Boolean hidden;
    private String example;
    private String reference;
    private Boolean allowEmptyValue;

    ApiModelPropertyBuilder() {
    }

    public ApiModelPropertyBuilder value(String value) {
      this.value = value;
      return this;
    }

    public ApiModelPropertyBuilder name(String name) {
      this.name = name;
      return this;
    }

    public ApiModelPropertyBuilder allowableValues(String allowableValues) {
      this.allowableValues = allowableValues;
      return this;
    }

    public ApiModelPropertyBuilder access(String access) {
      this.access = access;
      return this;
    }

    public ApiModelPropertyBuilder notes(String notes) {
      this.notes = notes;
      return this;
    }

    public ApiModelPropertyBuilder dataType(String dataType) {
      this.dataType = dataType;
      return this;
    }

    public ApiModelPropertyBuilder required(Boolean required) {
      this.required = required;
      return this;
    }

    public ApiModelPropertyBuilder position(Integer position) {
      this.position = position;
      return this;
    }

    public ApiModelPropertyBuilder hidden(Boolean hidden) {
      this.hidden = hidden;
      return this;
    }

    public ApiModelPropertyBuilder example(String example) {
      this.example = example;
      return this;
    }

    public ApiModelPropertyBuilder reference(String reference) {
      this.reference = reference;
      return this;
    }

    public ApiModelPropertyBuilder allowEmptyValue(Boolean allowEmptyValue) {
      this.allowEmptyValue = allowEmptyValue;
      return this;
    }

    public ApiModelProperty build() {
      return new ApiModelProperty(value, name, allowableValues, access, notes, dataType, required, position, hidden, example, reference, allowEmptyValue);
    }

    public String toString() {
      return "ApiModelProperty.ApiModelPropertyBuilder(value=" + this.value + ", name=" + this.name + ", allowableValues=" + this.allowableValues + ", access="
          + this.access + ", notes=" + this.notes + ", dataType=" + this.dataType + ", required=" + this.required + ", position=" + this.position + ", hidden="
          + this.hidden + ", example=" + this.example + ", reference=" + this.reference + ", allowEmptyValue=" + this.allowEmptyValue + ")";
    }
  }
}
