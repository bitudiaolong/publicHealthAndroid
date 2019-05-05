package com.gc.utils;

import android.util.Log;

/**
 * Author:Created by zhurui
 * Time:2018/7/17 上午10:01
 * Description:This is LogUtil
 * 打印日志工具类
 */
public class LogUtil {
    /**
     * 对自己的app添加一个整体的标识，方便日志检索，例如App名称为DemoApp，TAG可以设置为 DemoApplog，在日志检索的时候可以用DemoApplog关键字搜索
     */
    private static final String TAG = "way";

    public static void setIsDebug(boolean isDebug) {
        LogUtil.isDebug = isDebug;
    }

    private static boolean isDebug = true;

    private LogUtil() {

    }

    public static final String APP_NAME = "huihealth ";

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(APP_NAME + "->" + tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(APP_NAME + "->" + tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.e(APP_NAME + "->" + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(APP_NAME + "->" + tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(APP_NAME + "->" + tag, msg);
        }
    }

    public static void d(Class<?> clazz, String msg) {
        if (isDebug) {
            Log.d(TAG + "->" + clazz.getSimpleName(), msg);
        }
    }

    public static void e(Class<?> clazz, String msg) {
        if (isDebug) {
            Log.e(TAG + "->" + clazz.getSimpleName(), msg);
        }
    }

    public static void v(Class<?> clazz, String msg) {
        if (isDebug) {
            Log.v(TAG + "->" + clazz.getSimpleName(), msg);
        }
    }

    public static void i(Class<?> clazz, String msg) {
        if (isDebug) {
            Log.i(TAG + "->" + clazz.getSimpleName(), msg);
        }
    }
}
