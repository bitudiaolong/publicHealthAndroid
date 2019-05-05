package com.gagc.httplibrary;

import android.app.Application;
import android.util.Log;

import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by WUQING on 2017/7/25.
 */

public class HttpManager {
    private String base_url;

    public static void initX(Application app) {
        x.Ext.init(app);
    }

    /**
     *
     * @param functionName  方法名  方便输出log定位
     * @param paramsStrs    键值对数组 new String[]{"key","value","key","value"}
     * @param url           请求的地址
     * @param callBack      请求回调
     */
    public static RequestParams getData(String functionName,String[] paramsStrs,String url,HttpCallBack callBack){
        RequestParams requestParams = new RequestParams(url);
        requestParams.setUseCookie(false);
        if (paramsStrs != null && paramsStrs.length > 1){
            for (int i = 0; i < paramsStrs.length; i+=2) {
                requestParams.addBodyParameter(paramsStrs[i],paramsStrs[i+1]);
            }
        }
        Log.e("INFOD",functionName + "_Url:["+requestParams+"]");
        x.http().get(requestParams,callBack);
        return requestParams;
    }
    public static void getData(String functionName,RequestParams requestParams,HttpCallBack callBack){
        Log.e("INFOD",functionName + "_Url:["+requestParams+"]");
        x.http().get(requestParams,callBack);
    }

    /**
     *
     * @param functionName  方法名  方便输出log定位
     * @param paramsStrs    键值对数组 new String[]{"key","value","key","value"}
     * @param url           请求的地址
     * @param callBack      请求回调
     */
    public static void postData(String functionName,String[] paramsStrs,String url,HttpCallBack callBack){
        RequestParams requestParams = new RequestParams(url);
        requestParams.setUseCookie(false);
        if (paramsStrs != null && paramsStrs.length > 1){
            for (int i = 0; i < paramsStrs.length; i+=2) {
                requestParams.addBodyParameter(paramsStrs[i],paramsStrs[i+1]);
            }
        }
        Log.e("INFOD",functionName + "_Url:["+requestParams+"]");
        x.http().post(requestParams,callBack);
    }

    public static void postData(String functionName,RequestParams requestParams,HttpCallBack callBack){
        Log.e("INFOD",functionName + "_Url:["+requestParams+"]");
        x.http().post(requestParams,callBack);
    }

}
