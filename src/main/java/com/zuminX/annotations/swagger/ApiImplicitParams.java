package com.zuminX.annotations.swagger;

import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.names.ClassName;
import com.zuminX.names.SwaggerAnnotation;
import java.util.List;

/**
 * 对应io.swagger.annotations.ApiImplicitParams
 */
public class ApiImplicitParams extends AnnotationStr {

  @AnnotationAttr(show = true)
  private List<ApiImplicitParam> value;

  public ApiImplicitParams(List<ApiImplicitParam> value) {
    this.value = value;
  }

  public ApiImplicitParams() {
  }

  public static ApiImplicitParamsBuilder builder() {
    return new ApiImplicitParamsBuilder();
  }

  @Override
  public ClassName getClassName() {
    return SwaggerAnnotation.API_IMPLICIT_PARAMS;
  }

  public List<ApiImplicitParam> getValue() {
    return this.value;
  }

  public void setValue(List<ApiImplicitParam> value) {
    this.value = value;
  }

  public String toString() {
    return "ApiImplicitParams(value=" + this.getValue() + ")";
  }

  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ApiImplicitParams)) {
      return false;
    }
    final ApiImplicitParams other = (ApiImplicitParams) o;
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
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof ApiImplicitParams;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = super.hashCode();
    final Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    return result;
  }

  public static class ApiImplicitParamsBuilder {

    private List<ApiImplicitParam> value;

    ApiImplicitParamsBuilder() {
    }

    public ApiImplicitParamsBuilder value(List<ApiImplicitParam> value) {
      this.value = value;
      return this;
    }

    public ApiImplicitParams build() {
      return new ApiImplicitParams(value);
    }

    public String toString() {
      return "ApiImplicitParams.ApiImplicitParamsBuilder(value=" + this.value + ")";
    }
  }
}
