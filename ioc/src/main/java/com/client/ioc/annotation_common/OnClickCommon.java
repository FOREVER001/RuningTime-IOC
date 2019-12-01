package com.client.ioc.annotation_common;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnBaseCommon(setCommonListener = "setOnClickListener",
        setCommonObjectListener = View.OnClickListener.class,
       callBackMethod = "onClick(View v)")
public @interface OnClickCommon {
    int value();
}
