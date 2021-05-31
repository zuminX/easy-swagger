package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import java.util.List;

/**
 * 对应io.swagger.annotations.Api
 */
public class Api extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String value;

  @AnnotationAttr(show = true)
  private List<String> tags;

  @AnnotationAttr
  private String produces;

  @AnnotationAttr
  private String consumes;

  @AnnotationAttr
  private String protocols;

  @AnnotationAttr
  private Boolean hidden;

  public Api(String value, List<String> tags, String produces, String consumes, String protocols, Boolean hidden) {
    this.value = value;
    this.tags = tags;
    this.produces = produces;
    this.consumes = consumes;
    this.protocols = protocols;
    this.hidden = hidden;
  }

  public Api() {
  }

  public static ApiBuilder builder() {
    return new ApiBuilder();
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API;
  }

  public String getValue() {
    return this.value;
  }

  public List<String> getTags() {
    return this.tags;
  }

  public String getProduces() {
    return this.produces;
  }

  public String getConsumes() {
    return this.consumes;
  }

  public String getProtocols() {
    return this.protocols;
  }

  public Boolean getHidden() {
    return this.hidden;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public void setProduces(String produces) {
    this.produces = produces;
  }

  public void setConsumes(String consumes) {
    this.consumes = consumes;
  }

  public void setProtocols(String protocols) {
    this.protocols = protocols;
  }

  public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }

  public String toString() {
    return "Api(value=" + this.getValue() + ", tags=" + this.getTags() + ", produces=" + this.getProduces() + ", consumes=" + this.getConsumes()
        + ", protocols=" + this.getProtocols() + ", hidden=" + this.getHidden() + ")";
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Api)) {
      return false;
    }
    final Api other = (Api) o;
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
    final Object this$tags = this.getTags();
    final Object other$tags = other.getTags();
    if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) {
      return false;
    }
    final Object this$produces = this.getProduces();
    final Object other$produces = other.getProduces();
    if (this$produces == null ? other$produces != null : !this$produces.equals(other$produces)) {
      return false;
    }
    final Object this$consumes = this.getConsumes();
    final Object other$consumes = other.getConsumes();
    if (this$consumes == null ? other$consumes != null : !this$consumes.equals(other$consumes)) {
      return false;
    }
    final Object this$protocols = this.getProtocols();
    final Object other$protocols = other.getProtocols();
    if (this$protocols == null ? other$protocols != null : !this$protocols.equals(other$protocols)) {
      return false;
    }
    final Object this$hidden = this.getHidden();
    final Object other$hidden = other.getHidden();
    if (this$hidden == null ? other$hidden != null : !this$hidden.equals(other$hidden)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Api;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = super.hashCode();
    final Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    final Object $tags = this.getTags();
    result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
    final Object $produces = this.getProduces();
    result = result * PRIME + ($produces == null ? 43 : $produces.hashCode());
    final Object $consumes = this.getConsumes();
    result = result * PRIME + ($consumes == null ? 43 : $consumes.hashCode());
    final Object $protocols = this.getProtocols();
    result = result * PRIME + ($protocols == null ? 43 : $protocols.hashCode());
    final Object $hidden = this.getHidden();
    result = result * PRIME + ($hidden == null ? 43 : $hidden.hashCode());
    return result;
  }

  public static class ApiBuilder {

    private String value;
    private List<String> tags;
    private String produces;
    private String consumes;
    private String protocols;
    private Boolean hidden;

    ApiBuilder() {
    }

    public ApiBuilder value(String value) {
      this.value = value;
      return this;
    }

    public ApiBuilder tags(List<String> tags) {
      this.tags = tags;
      return this;
    }

    public ApiBuilder produces(String produces) {
      this.produces = produces;
      return this;
    }

    public ApiBuilder consumes(String consumes) {
      this.consumes = consumes;
      return this;
    }

    public ApiBuilder protocols(String protocols) {
      this.protocols = protocols;
      return this;
    }

    public ApiBuilder hidden(Boolean hidden) {
      this.hidden = hidden;
      return this;
    }

    public Api build() {
      return new Api(value, tags, produces, consumes, protocols, hidden);
    }

    public String toString() {
      return "Api.ApiBuilder(value=" + this.value + ", tags=" + this.tags + ", produces=" + this.produces + ", consumes=" + this.consumes + ", protocols="
          + this.protocols + ", hidden=" + this.hidden + ")";
    }
  }
}
