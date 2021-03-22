package com.zuminX.convert;

import cn.hutool.core.convert.Converter;
import cn.hutool.json.JSONUtil;
import com.zuminX.domain.AnnotationItemMap;

public class AnnotationItemMapConvert implements Converter<AnnotationItemMap> {

  @Override
  public AnnotationItemMap convert(Object value, AnnotationItemMap defaultValue)
      throws IllegalArgumentException {
    return JSONUtil.parseObj(value).toBean(AnnotationItemMap.class);
  }
}
