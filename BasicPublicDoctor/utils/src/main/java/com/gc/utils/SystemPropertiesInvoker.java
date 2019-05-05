package com.gc.utils;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * Author:Created by zhurui
 * Time:2018/7/18 下午1:57
 * Description:This is SystemPropertiesInvoker
 * 系统参数获取
 */
public class SystemPropertiesInvoker {

    /**
     * 根据给定Key获取值.
     *
     * @return 如果不存在该key则返回空字符串
     * @throws IllegalArgumentException 如果key超过32个字符则抛出该异常
     */
    public static String get(Context context, String key)
            throws IllegalArgumentException {
        String ret = "";
        try {
            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = cl
                    .loadClass("android.os.SystemProperties");
            // 参数类型
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = new Class[1];
            paramTypes[0] = String.class;
            Method get = SystemProperties.getMethod("get", paramTypes);
            // 参数
            Object[] params = new Object[1];
            params[0] = new String(key);
            ret = (String) get.invoke(SystemProperties, params);
        } catch (IllegalArgumentException iAE) {
            throw iAE;
        } catch (Exception e) {
            ret = "";
        }

        return ret;

    }

    public static String getSystemProperties(String key) {
        String value = "";
        Class<?> SystemPropertiesClass;

        try {
            SystemPropertiesClass = Class
                    .forName("android.os.SystemProperties");
            Object SystemPropertiesObject = SystemPropertiesClass.newInstance();

            Class[] args1 = new Class[1];
            args1[0] = String.class;

            Method getMethod = SystemPropertiesClass.getDeclaredMethod("get",
                    args1);

            getMethod.setAccessible(true);

            Object[] argments = new String[1];
//			String[] argments = new String[1];
            argments[0] = key;
            value = (String) getMethod.invoke(SystemPropertiesObject, new Object[]{key});

//			value = (String) getMethod.invoke(SystemPropertiesObject, argments);
        } catch (Exception e) {
            value = null;
            e.printStackTrace();
        }

        return value;
    }

}

/*
 * package com.example.smartcommunity.util;
 *
 * import java.lang.reflect.Method;
 *
 * import android.util.Log;
 *
 * public class SystemPropertiesInvoker {
 *
 * private static final String TAG = "SystemPropertiesInvoke"; private static
 * Method getLongMethod = null; private static Method getBooleanMethod = null;
 *
 * public static long getLong(final String key, final long def) { try { if
 * (getLongMethod == null) { getLongMethod =
 * Class.forName("android.os.SystemProperties") .getMethod("getLong",
 * String.class, long.class); }
 *
 * return ((Long) getLongMethod.invoke(null, key, def)).longValue(); } catch
 * (Exception e) { Log.e(TAG, "Platform error: " + e.toString()); return def; }
 * }
 *
 * public static boolean getBoolean(final String key, final boolean def) { try {
 * if (getBooleanMethod == null) { getBooleanMethod =
 * Class.forName("android.os.SystemProperties") .getMethod("getBoolean",
 * String.class, boolean.class); }
 *
 * // Log.i(TAG,"getBoolean:"+"key:"+key+" def:"+def); // Log.i(TAG,
 * "getBoolean:" + getBooleanMethod.invoke(null, key, def));
 *
 * return (Boolean) getBooleanMethod.invoke(null, key, def); } catch (Exception
 * e) { Log.e(TAG, "Platform error: " + e.toString()); return def; } }
 *
 * // getString(persist.sys.hwconfig.stb_id,"") public static String
 * getString(final String key, final String def) { try { if (getBooleanMethod ==
 * null) { getBooleanMethod = Class.forName("android.os.SystemProperties")
 * .getMethod("getString", String.class, boolean.class); }
 *
 * // Log.i(TAG,"getBoolean:"+"key:"+key+" def:"+def); // Log.i(TAG,
 * "getBoolean:" + getBooleanMethod.invoke(null, key, def));
 *
 * return (String) getBooleanMethod.invoke(null, key); } catch (Exception e) {
 * Log.e(TAG, "Platform error: " + e.toString()); return def; } }
 *
 * }
 */