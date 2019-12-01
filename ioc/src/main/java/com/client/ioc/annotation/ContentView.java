package com.client.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 把layout注入到Activity（运行时期layoutId）,容器（拿到MainActivity）就能拿到layoutId.
 * 然后还可以去方式执行setContentView(layoutId)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {
    int value() default -1;
}
