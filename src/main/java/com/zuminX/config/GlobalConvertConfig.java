package com.zuminX.config;

import cn.hutool.core.convert.Converter;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.util.TypeUtil;
import com.intellij.ide.ApplicationInitializedListener;
import com.zuminX.utils.CoreUtils;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;

public class GlobalConvertConfig implements ApplicationInitializedListener {

  @Override
  public void componentsInitialized() {
    ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
    Set<Class<?>> classSet = CoreUtils.getClasses(Converter.class);
    classSet.forEach(aClass -> {
      Type[] genericInterfaces = aClass.getGenericInterfaces();
      Type type = Arrays.stream(genericInterfaces).map(TypeUtil::getTypeArgument).findFirst().orElse(Object.class);
      converterRegistry.putCustom(type, (Class<? extends Converter<?>>) aClass);
    });
  }

}
