package com.client.ioc.annotation_common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnBaseCommon {
    //事件三要素1 订阅方式 setOnClickListener ,setOnLongClickListener..
    String setCommonListener();
    //事件三要素2 事件源对象 View.OnclickListener
    Class setCommonObjectListener();
    //事件三要素3 具体执行的方法（消费事件的方法） onClick(View v)
    String callBackMethod();

}
