package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import java.util.List;

/**
 * 对应io.swagger.annotations.ApiModel
 */
public class ApiModel extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String description;

  @AnnotationAttr
  private Class<?> parent;

  @AnnotationAttr
  private String discriminator;

  @AnnotationAttr
  private List<Class<?>> subTypes;

  @AnnotationAttr
  private String reference;

  public ApiModel(String description, Class<?> parent, String discriminator, List<Class<?>> subTypes, String reference) {
    this.description = description;
    this.parent = parent;
    this.discriminator = discriminator;
    this.subTypes = subTypes;
    this.reference = reference;
  }

  public ApiModel() {
  }

  public static ApiModelBuilder builder() {
    return new ApiModelBuilder();
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_MODEL;
  }

  public String getDescription() {
    return this.description;
  }

  public Class<?> getParent() {
    return this.parent;
  }

  public String getDiscriminator() {
    return this.discriminator;
  }

  public List<Class<?>> getSubTypes() {
    return this.subTypes;
  }

  public String getReference() {
    return this.reference;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setParent(Class<?> parent) {
    this.parent = parent;
  }

  public void setDiscriminator(String discriminator) {
    this.discriminator = discriminator;
  }

  public void setSubTypes(List<Class<?>> subTypes) {
    this.subTypes = subTypes;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String toString() {
    return "ApiModel(description=" + this.getDescription() + ", parent=" + this.getParent() + ", discriminator=" + this.getDiscriminator() + ", subTypes="
        + this.getSubTypes() + ", reference=" + this.getReference() + ")";
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ApiModel)) {
      return false;
    }
    final ApiModel other = (ApiModel) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final Object this$description = this.getDescription();
    final Object other$description = other.getDescription();
    if (this$description == null ? other$description != null : !this$description.equals(other$description)) {
      return false;
    }
    final Object this$parent = this.getParent();
    final Object other$parent = other.getParent();
    if (this$parent == null ? other$parent != null : !this$parent.equals(other$parent)) {
      return false;
    }
    final Object this$discriminator = this.getDiscriminator();
    final Object other$discriminator = other.getDiscriminator();
    if (this$discriminator == null ? other$discriminator != null : !this$discriminator.equals(other$discriminator)) {
      return false;
    }
    final Object this$subTypes = this.getSubTypes();
    final Object other$subTypes = other.getSubTypes();
    if (this$subTypes == null ? other$subTypes != null : !this$subTypes.equals(other$subTypes)) {
      return false;
    }
    final Object this$reference = this.getReference();
    final Object other$reference = other.getReference();
    if (this$reference == null ? other$reference != null : !this$reference.equals(other$reference)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof ApiModel;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = super.hashCode();
    final Object $description = this.getDescription();
    result = result * PRIME + ($description == null ? 43 : $description.hashCode());
    final Object $parent = this.getParent();
    result = result * PRIME + ($parent == null ? 43 : $parent.hashCode());
    final Object $discriminator = this.getDiscriminator();
    result = result * PRIME + ($discriminator == null ? 43 : $discriminator.hashCode());
    final Object $subTypes = this.getSubTypes();
    result = result * PRIME + ($subTypes == null ? 43 : $subTypes.hashCode());
    final Object $reference = this.getReference();
    result = result * PRIME + ($reference == null ? 43 : $reference.hashCode());
    return result;
  }

  public static class ApiModelBuilder {

    private String description;
    private Class<?> parent;
    private String discriminator;
    private List<Class<?>> subTypes;
    private String reference;

    ApiModelBuilder() {
    }

    public ApiModelBuilder description(String description) {
      this.description = description;
      return this;
    }

    public ApiModelBuilder parent(Class<?> parent) {
      this.parent = parent;
      return this;
    }

    public ApiModelBuilder discriminator(String discriminator) {
      this.discriminator = discriminator;
      return this;
    }

    public ApiModelBuilder subTypes(List<Class<?>> subTypes) {
      this.subTypes = subTypes;
      return this;
    }

    public ApiModelBuilder reference(String reference) {
      this.reference = reference;
      return this;
    }

    public ApiModel build() {
      return new ApiModel(description, parent, discriminator, subTypes, reference);
    }

    public String toString() {
      return "ApiModel.ApiModelBuilder(description=" + this.description + ", parent=" + this.parent + ", discriminator=" + this.discriminator + ", subTypes="
          + this.subTypes + ", reference=" + this.reference + ")";
    }
  }
}