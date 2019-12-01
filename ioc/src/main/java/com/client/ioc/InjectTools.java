package com.client.ioc;

import android.view.View;

import com.client.ioc.annotation.BindView;
import com.client.ioc.annotation.ContentView;
import com.client.ioc.annotation.OnClick;
import com.client.ioc.annotation_common.OnBaseCommon;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代码注入工具
 *
 * @author zhuxiaohui
 * @version 1.0
 * @date 2019-12-01
 */
public class InjectTools {
    public static void inject(Object object) {
        injectContentView(object);
        injectWidget(object);
        injectClick(object);
        injectCommonEvent(object);
    }

    /**
     * 运行时候注入兼容Android事件
     * @param object
     */
    private static void injectCommonEvent(final Object object) {
        Class<?> mMainActivityClass = object.getClass();
        Method[] declaredMethods = mMainActivityClass.getDeclaredMethods();
        for (final Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);
            //onBaseCommon
            Annotation[] annotations = declaredMethod.getAnnotations();//可能一个方法上有多个注解
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //寻找是否有onBaseCommon注解
                OnBaseCommon onBaseCommon = annotationType.getAnnotation(OnBaseCommon.class);
                if(onBaseCommon==null){
                    continue;
                }

                //证明已经找到了含有onBaseCommon的注解
                //获取事件三要素
                String setCommonListener = onBaseCommon.setCommonListener();
                Class setCommonObjectListener = onBaseCommon.setCommonObjectListener();
                String callBackMethod = onBaseCommon.callBackMethod();


                //get R.id.btn
                try {
                    Method valueMethod = annotationType.getMethod("value");
                    valueMethod.setAccessible(true);
                   int value = (int) valueMethod.invoke(annotation);
                    Method findViewByIdMethod = mMainActivityClass.getMethod("findViewById", int.class);
                    //View view =findViewById(R.id.xx)
                    Object viewObject = findViewByIdMethod.invoke(object, value);

                    Method mViewMethod = viewObject.getClass().getMethod(setCommonListener, setCommonObjectListener);
                    //view.setOnClickListener(new View.onclickListener..)
                    Object proxy=  Proxy.newProxyInstance(setCommonObjectListener.getClassLoader()
                            ,new Class[]{setCommonObjectListener},
                            new InvocationHandler(){
                                @Override
                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                  //执行MainActivity里面的方法
                                    return declaredMethod.invoke(object,null);
                                }
                            });
                    //动态代理
                    //狸猫换太子，换成我们的动态代理
                    mViewMethod.invoke(viewObject,proxy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //实例化控件

            }
        }
    }

    /**
     * 运行时候注入点击事件
     *
     * @param object
     */
    private static void injectClick(final Object object) {
        Class<?> mMainActivityClass = object.getClass();
        Method[] declaredMethods = mMainActivityClass.getDeclaredMethods();
        for (final Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);
            OnClick annotationClick = declaredMethod.getAnnotation(OnClick.class);
            if (annotationClick == null) {
                continue;
            }
            int viewId = annotationClick.value();

            try {
                Method findViewByIdMethod = mMainActivityClass.getMethod("findViewById", int.class);
                Object resultView = findViewByIdMethod.invoke(object, viewId);//findViewById(viewId)
                View view = (View) resultView;//View view=findViewById(viewId)
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            declaredMethod.invoke(object);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 运行时注入控件
     *
     * @param object
     */
    private static void injectWidget(Object object) {
        Class<?> mMainActivityClass = object.getClass();
        Field[] declaredFields = mMainActivityClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            BindView annotation = declaredField.getAnnotation(BindView.class);
            if (annotation == null) {
                continue;
            }
            int value = annotation.value();
            try {
                Method findViewByIdMethod = mMainActivityClass.getMethod("findViewById", int.class);
                Object invoke = findViewByIdMethod.invoke(object, value);
                declaredField.set(object, invoke);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 运行时注入布局
     *
     * @param object
     */
    private static void injectContentView(Object object) {
        Class<?> mMainActivityClass = object.getClass();
        ContentView contentView = mMainActivityClass.getAnnotation(ContentView.class);
        if (contentView == null) {
            return;
        }
        int value = contentView.value();
        try {
            Method setContentViewMethod = mMainActivityClass.getMethod("setContentView", int.class);
            setContentViewMethod.invoke(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
