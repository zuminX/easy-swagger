# Swagger Annotation Generation Tool

## 功能
1. 若当前类标注了@RestController或@Controller注解，则生成Controller注解。否则生成Model注解；
2. 可选择类名、字段名或方法名生成指定的注解；
3. 能通过Settings-Tools-Easy Swagger页中设置是否生成指定注解和默认值。
4. 注解信息可以从Javadoc中获取，写入Swagger注解的value属性中。

------

1. If the current class is marked with @RestController or @Controller annotation, the Controller annotation is generated. 
   Otherwise, the Model annotation is generated;
2. You can select a class name, field name or method name to generate the specified annotation;
3. The ability to set whether to generate specified annotations and default values via the Settings-Tools-Easy Swagger page.
4. The annotation information can be obtained from the Javadoc and written to the value attribute of the Swagger annotation.

## 参考项目
- swagger-tool：https://github.com/Pwhxbdk/swagger-tool
- RestfulTool：https://github.com/ZhangYuanSheng1217/RestfulTool