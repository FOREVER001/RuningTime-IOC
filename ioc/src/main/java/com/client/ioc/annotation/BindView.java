package com.client.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 运行时期注入控件
 * 把一系列控件注入到Activity的字段中，（注解（运行期viewId）），容器（拿到MainActivity就能够拿到viewId）
 * 然后可以执行findViewById（viewId），返回的resulview赋值给MainActivity里面的字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
    int value() default -1;
}
