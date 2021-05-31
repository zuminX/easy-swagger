package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import java.util.List;

/**
 * 对应io.swagger.annotations.ApiOperation
 */
public class ApiOperation extends AnnotationStr {

  @AnnotationAttr(show = true)
  private String value;

  @AnnotationAttr(show = true)
  private String notes;

  @AnnotationAttr(show = true)
  private List<String> tags;

  @AnnotationAttr
  private Class<?> response;

  @AnnotationAttr
  private String responseContainer;

  @AnnotationAttr
  private String responseReference;

  @AnnotationAttr(show = true)
  private String httpMethod;

  @AnnotationAttr
  private String nickname;

  @AnnotationAttr
  private String produces;

  @AnnotationAttr
  private String consumes;

  @AnnotationAttr
  private String protocols;

  @AnnotationAttr
  private Boolean hidden;

  @AnnotationAttr
  private Integer code;

  @AnnotationAttr
  private Boolean ignoreJsonView;

  public ApiOperation(String value, String notes, List<String> tags, Class<?> response, String responseContainer, String responseReference, String httpMethod,
      String nickname, String produces, String consumes, String protocols, Boolean hidden, Integer code, Boolean ignoreJsonView) {
    this.value = value;
    this.notes = notes;
    this.tags = tags;
    this.response = response;
    this.responseContainer = responseContainer;
    this.responseReference = responseReference;
    this.httpMethod = httpMethod;
    this.nickname = nickname;
    this.produces = produces;
    this.consumes = consumes;
    this.protocols = protocols;
    this.hidden = hidden;
    this.code = code;
    this.ignoreJsonView = ignoreJsonView;
  }

  public ApiOperation() {
  }

  public static ApiOperationBuilder builder() {
    return new ApiOperationBuilder();
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_OPERATION;
  }

  public String getValue() {
    return this.value;
  }

  public String getNotes() {
    return this.notes;
  }

  public List<String> getTags() {
    return this.tags;
  }

  public Class<?> getResponse() {
    return this.response;
  }

  public String getResponseContainer() {
    return this.responseContainer;
  }

  public String getResponseReference() {
    return this.responseReference;
  }

  public String getHttpMethod() {
    return this.httpMethod;
  }

  public String getNickname() {
    return this.nickname;
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

  public Integer getCode() {
    return this.code;
  }

  public Boolean getIgnoreJsonView() {
    return this.ignoreJsonView;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public void setResponse(Class<?> response) {
    this.response = response;
  }

  public void setResponseContainer(String responseContainer) {
    this.responseContainer = responseContainer;
  }

  public void setResponseReference(String responseReference) {
    this.responseReference = responseReference;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
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

  public void setCode(Integer code) {
    this.code = code;
  }

  public void setIgnoreJsonView(Boolean ignoreJsonView) {
    this.ignoreJsonView = ignoreJsonView;
  }

  public String toString() {
    return "ApiOperation(value=" + this.getValue() + ", notes=" + this.getNotes() + ", tags=" + this.getTags() + ", response=" + this.getResponse()
        + ", responseContainer=" + this.getResponseContainer() + ", responseReference=" + this.getResponseReference() + ", httpMethod=" + this.getHttpMethod()
        + ", nickname=" + this.getNickname() + ", produces=" + this.getProduces() + ", consumes=" + this.getConsumes() + ", protocols=" + this.getProtocols()
        + ", hidden=" + this.getHidden() + ", code=" + this.getCode() + ", ignoreJsonView=" + this.getIgnoreJsonView() + ")";
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ApiOperation)) {
      return false;
    }
    final ApiOperation other = (ApiOperation) o;
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
    final Object this$notes = this.getNotes();
    final Object other$notes = other.getNotes();
    if (this$notes == null ? other$notes != null : !this$notes.equals(other$notes)) {
      return false;
    }
    final Object this$tags = this.getTags();
    final Object other$tags = other.getTags();
    if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) {
      return false;
    }
    final Object this$response = this.getResponse();
    final Object other$response = other.getResponse();
    if (this$response == null ? other$response != null : !this$response.equals(other$response)) {
      return false;
    }
    final Object this$responseContainer = this.getResponseContainer();
    final Object other$responseContainer = other.getResponseContainer();
    if (this$responseContainer == null ? other$responseContainer != null : !this$responseContainer.equals(other$responseContainer)) {
      return false;
    }
    final Object this$responseReference = this.getResponseReference();
    final Object other$responseReference = other.getResponseReference();
    if (this$responseReference == null ? other$responseReference != null : !this$responseReference.equals(other$responseReference)) {
      return false;
    }
    final Object this$httpMethod = this.getHttpMethod();
    final Object other$httpMethod = other.getHttpMethod();
    if (this$httpMethod == null ? other$httpMethod != null : !this$httpMethod.equals(other$httpMethod)) {
      return false;
    }
    final Object this$nickname = this.getNickname();
    final Object other$nickname = other.getNickname();
    if (this$nickname == null ? other$nickname != null : !this$nickname.equals(other$nickname)) {
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
    final Object this$code = this.getCode();
    final Object other$code = other.getCode();
    if (this$code == null ? other$code != null : !this$code.equals(other$code)) {
      return false;
    }
    final Object this$ignoreJsonView = this.getIgnoreJsonView();
    final Object other$ignoreJsonView = other.getIgnoreJsonView();
    if (this$ignoreJsonView == null ? other$ignoreJsonView != null : !this$ignoreJsonView.equals(other$ignoreJsonView)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof ApiOperation;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = super.hashCode();
    final Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    final Object $notes = this.getNotes();
    result = result * PRIME + ($notes == null ? 43 : $notes.hashCode());
    final Object $tags = this.getTags();
    result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
    final Object $response = this.getResponse();
    result = result * PRIME + ($response == null ? 43 : $response.hashCode());
    final Object $responseContainer = this.getResponseContainer();
    result = result * PRIME + ($responseContainer == null ? 43 : $responseContainer.hashCode());
    final Object $responseReference = this.getResponseReference();
    result = result * PRIME + ($responseReference == null ? 43 : $responseReference.hashCode());
    final Object $httpMethod = this.getHttpMethod();
    result = result * PRIME + ($httpMethod == null ? 43 : $httpMethod.hashCode());
    final Object $nickname = this.getNickname();
    result = result * PRIME + ($nickname == null ? 43 : $nickname.hashCode());
    final Object $produces = this.getProduces();
    result = result * PRIME + ($produces == null ? 43 : $produces.hashCode());
    final Object $consumes = this.getConsumes();
    result = result * PRIME + ($consumes == null ? 43 : $consumes.hashCode());
    final Object $protocols = this.getProtocols();
    result = result * PRIME + ($protocols == null ? 43 : $protocols.hashCode());
    final Object $hidden = this.getHidden();
    result = result * PRIME + ($hidden == null ? 43 : $hidden.hashCode());
    final Object $code = this.getCode();
    result = result * PRIME + ($code == null ? 43 : $code.hashCode());
    final Object $ignoreJsonView = this.getIgnoreJsonView();
    result = result * PRIME + ($ignoreJsonView == null ? 43 : $ignoreJsonView.hashCode());
    return result;
  }

  public static class ApiOperationBuilder {

    private String value;
    private String notes;
    private List<String> tags;
    private Class<?> response;
    private String responseContainer;
    private String responseReference;
    private String httpMethod;
    private String nickname;
    private String produces;
    private String consumes;
    private String protocols;
    private Boolean hidden;
    private Integer code;
    private Boolean ignoreJsonView;

    ApiOperationBuilder() {
    }

    public ApiOperationBuilder value(String value) {
      this.value = value;
      return this;
    }

    public ApiOperationBuilder notes(String notes) {
      this.notes = notes;
      return this;
    }

    public ApiOperationBuilder tags(List<String> tags) {
      this.tags = tags;
      return this;
    }

    public ApiOperationBuilder response(Class<?> response) {
      this.response = response;
      return this;
    }

    public ApiOperationBuilder responseContainer(String responseContainer) {
      this.responseContainer = responseContainer;
      return this;
    }

    public ApiOperationBuilder responseReference(String responseReference) {
      this.responseReference = responseReference;
      return this;
    }

    public ApiOperationBuilder httpMethod(String httpMethod) {
      this.httpMethod = httpMethod;
      return this;
    }

    public ApiOperationBuilder nickname(String nickname) {
      this.nickname = nickname;
      return this;
    }

    public ApiOperationBuilder produces(String produces) {
      this.produces = produces;
      return this;
    }

    public ApiOperationBuilder consumes(String consumes) {
      this.consumes = consumes;
      return this;
    }

    public ApiOperationBuilder protocols(String protocols) {
      this.protocols = protocols;
      return this;
    }

    public ApiOperationBuilder hidden(Boolean hidden) {
      this.hidden = hidden;
      return this;
    }

    public ApiOperationBuilder code(Integer code) {
      this.code = code;
      return this;
    }

    public ApiOperationBuilder ignoreJsonView(Boolean ignoreJsonView) {
      this.ignoreJsonView = ignoreJsonView;
      return this;
    }

    public ApiOperation build() {
      return new ApiOperation(value, notes, tags, response, responseContainer, responseReference, httpMethod, nickname, produces, consumes, protocols, hidden,
          code, ignoreJsonView);
    }

    public String toString() {
      return "ApiOperation.ApiOperationBuilder(value=" + this.value + ", notes=" + this.notes + ", tags=" + this.tags + ", response=" + this.response
          + ", responseContainer=" + this.responseContainer + ", responseReference=" + this.responseReference + ", httpMethod=" + this.httpMethod
          + ", nickname="
          + this.nickname + ", produces=" + this.produces + ", consumes=" + this.consumes + ", protocols=" + this.protocols + ", hidden=" + this.hidden
          + ", code=" + this.code + ", ignoreJsonView=" + this.ignoreJsonView + ")";
    }
  }
}