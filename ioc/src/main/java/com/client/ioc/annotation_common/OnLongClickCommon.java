package com.client.ioc.annotation_common;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnBaseCommon(setCommonListener = "setOnLongClickListener",
        setCommonObjectListener =View.OnLongClickListener.class,
        callBackMethod = "onLongClick(View v)")
public @interface OnLongClickCommon {
    int value();
}
