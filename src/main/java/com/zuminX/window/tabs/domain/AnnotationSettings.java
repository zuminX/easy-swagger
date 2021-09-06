package com.zuminX.window.tabs.domain;

import cn.hutool.core.util.ReflectUtil;
import com.zuminX.annotations.AnnotationAttr;
import com.zuminX.annotations.AnnotationStr;
import com.zuminX.annotations.swagger.Api;
import com.zuminX.annotations.swagger.ApiImplicitParam;
import com.zuminX.annotations.swagger.ApiImplicitParams;
import com.zuminX.annotations.swagger.ApiModel;
import com.zuminX.annotations.swagger.ApiModelProperty;
import com.zuminX.annotations.swagger.ApiOperation;
import com.zuminX.annotations.swagger.Authorization;
import com.zuminX.annotations.swagger.AuthorizationScope;
import com.zuminX.annotations.swagger.Example;
import com.zuminX.annotations.swagger.ExampleProperty;
import com.zuminX.annotations.swagger.Extension;
import com.zuminX.annotations.swagger.ExtensionProperty;
import com.zuminX.annotations.swagger.ResponseHeader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 注解设置信息类
 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationSettings {

  private static final List<Class<? extends AnnotationStr>> MAIN_ANNOTATIONS = List.of(Api.class, ApiImplicitParam.class, ApiImplicitParams.class,
      ApiModel.class, ApiModelProperty.class, ApiOperation.class);
  public static final Map<String, List<AnnotationItem>> MAIN_ANNOTATION_DEFAULT_MAP = getMapByClassList(MAIN_ANNOTATIONS);
  private static final List<Class<? extends AnnotationStr>> PROPERTY_ANNOTATIONS = List.of(Authorization.class, AuthorizationScope.class, Example.class,
      ExampleProperty.class, Extension.class, ExtensionProperty.class, ResponseHeader.class);
  public static final Map<String, List<AnnotationItem>> PROPERTY_ANNOTATION_DEFAULT_MAP = getMapByClassList(PROPERTY_ANNOTATIONS);

  @Getter
  private Map<String, List<AnnotationItem>> map;

  /**
   * 获取注解设置信息的默认项
   *
   * @param list 注解Class列表
   * @return 默认项
   */
  private static Map<String, List<AnnotationItem>> getMapByClassList(List<Class<? extends AnnotationStr>> list) {
    return list.stream().collect(Collectors.toMap(Class::getName, clazz -> getAnnotationItemList(ReflectUtil.newInstance(clazz)), (a, b) -> b));
  }

  /**
   * 根据注解字符串类获取注解设置信息列表
   *
   * @param annotationStr 注解字符串对象
   * @return 注解设置信息列表
   */
  private static List<AnnotationItem> getAnnotationItemList(AnnotationStr annotationStr) {
    return AnnotationStr.getSortFields(annotationStr).stream().map(field -> {
      AnnotationAttr annotationAttr = AnnotationStr.getAnnotationAttr(field);
      return new AnnotationItem(field.getName(), annotationAttr.defaultText(), annotationAttr.sort(), annotationAttr.show());
    }).collect(Collectors.toList());
  }

  /**
   * 获取完整的注解设置信息
   *
   * @param defaultMap 注解设置信息的默认项
   * @return key为注解名，value为注解项集合的映射
   */
  public Map<String, List<AnnotationItem>> getFullMap(Map<String, List<AnnotationItem>> defaultMap) {
    // TODO 临时解决始终使用旧设置信息的问题
    Map<String, List<AnnotationItem>> result = new HashMap<>();
    defaultMap.forEach((key, value) -> {
      List<AnnotationItem> annotationItems = map.get(key);
      if (annotationItems == null) {
        result.put(key, value);
        return;
      }
      Map<String, AnnotationItem> itemNameMap = annotationItems.stream().collect(Collectors.toMap(AnnotationItem::getName, annotationItem -> annotationItem,
          (a, b) -> b));
      List<AnnotationItem> itemList = value.stream().map(item -> itemNameMap.getOrDefault(item.getName(), item)).collect(Collectors.toList());
      result.put(key, itemList);
    });
    return result;
  }
}
