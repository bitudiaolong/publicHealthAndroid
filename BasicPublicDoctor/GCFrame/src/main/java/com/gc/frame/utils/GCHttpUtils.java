package com.gc.frame.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 此工具类只是个示例
 * 具体项目请仿照此类写工具类，实现自己需要使用的网络回调
 * 可以选用别的网络框架
 * Created by guoyuan on 06/02/2017.
 */

public class GCHttpUtils extends AbstarctHttpUtils{
    @Override
    public void post(final CallBack callBack, String... params) {
        RequestParams requestParams = new RequestParams(params[0]);
        for (int i = 1; i < params.length; i++) {
            String key = params[i].substring(0, params[i].indexOf("="));
            String value = params[i].substring(params[i].indexOf("=")+1, params[i].length());
           requestParams.addBodyParameter(key, value);
        }
        Callback.Cancelable post = x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onFailure(-1, "很抱歉，网络故障，请稍后访问");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void get(final CallBack callBack, String... params) {
        RequestParams requestParams = new RequestParams(params[0]);
        for (int i = 1; i < params.length; i++) {
            String key = params[i].substring(0, params[i].indexOf("="));
            String value = params[i].substring(params[i].indexOf("=")+1, params[i].length());
            requestParams.addQueryStringParameter(key, value);
        }
        Callback.Cancelable get = x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onFailure(-1, "很抱歉，网络故障，请稍后访问");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void post(CallBack callBack, String url, JSONObject params){
        List<String> paramList= new ArrayList<>();
        paramList.add(url);
        Iterator iterator = params.keys();
        try {
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = params.get(key).toString();
                paramList.add(key + "=" + value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        post(callBack, paramList.toArray(new String[paramList.size()]));

    }

    public void get(CallBack callBack, String url, JSONObject params){
        List<String> paramList= new ArrayList<>();
        paramList.add(url);
        Iterator iterator = params.keys();
        try {
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = params.get(key).toString();
                paramList.add(key + "=" + value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        get(callBack, paramList.toArray(new String[paramList.size()]));
    }
}
