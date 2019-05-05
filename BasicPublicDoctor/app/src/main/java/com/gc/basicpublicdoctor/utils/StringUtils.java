package com.gc.basicpublicdoctor.utils;

/**
 * Created by Administrator on 2018/9/4.
 */

public class StringUtils {
    public static boolean isBank(String s) {
        if (s == null || s.trim().length() == 0)
            return true;
        return false;
    }
}
