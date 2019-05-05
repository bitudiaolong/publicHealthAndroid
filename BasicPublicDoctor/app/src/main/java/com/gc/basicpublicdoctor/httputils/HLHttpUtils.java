package com.gc.basicpublicdoctor.httputils;

import android.content.Intent;
import android.util.Log;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gagc.httplibrary.BaseResponse;
import com.gagc.httplibrary.BaseUpdateDownload;
import com.gagc.httplibrary.UploadMoreResponse;
import com.gc.basicpublicdoctor.DoctorApplication;
import com.gc.basicpublicdoctor.activity.MainActivity;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.utils.AndroidDes3Util;
import com.gc.utils.GsonUtil;
import com.gc.utils.LogUtil;
import com.gc.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by wangqiao  on 23/02/2017.
 */

public class HLHttpUtils<T extends BaseResponse> extends AbstarctGenericityHttpUtils<T> {

    public static String TAG = "HLHttpUtils";
    boolean isUpdate = false;

    private String api;

    RequestParams request;

    public HLHttpUtils post(Map<String, String> map, String url) {
        request = new RequestParams(url);
        String input = GsonUtil.toJson(map);
        LogUtil.e("HLHttpUtils-接口请求入参：==", "HLHttpUtils-接口请求入参：==  \"" + url + "\"  入参==  " + input);

        String data = AndroidDes3Util.encode(GsonUtil.toJson(map));
        LogUtil.e("HLHttpUtils-接口请求入加密：==", "HLHttpUtils-接口请求入参：==  \"" + url + "\"  data==  " + data);
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addBodyParameter("data", data);
        return this;
    }

    public HLHttpUtils postUpdate(Map<String, String> map, String url, boolean isUpdate) {
        this.isUpdate = isUpdate;
        request = new RequestParams(url);
        String input = GsonUtil.toJson(map);
        LogUtil.e("HLHttpUtils-接口请求入参：==", "HLHttpUtils-接口请求入参：==  \"" + url + "\"  入参==  " + input);
        String data = AndroidDes3Util.encodeUpdate(GsonUtil.toJson(map));
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addBodyParameter("data", data);
        return this;
    }

    public void setCallBack(CallBack<T> callBack) {
        Log.e(TAG, "setCallBack: ");
        x.http().post(request, getCommonCallback(callBack));
    }

    @Override
    public void post(CallBack<T> callBack, String... params) {
        RequestParams requestParams = new RequestParams(params[0]);
        api = params[0];

        requestParams.addHeader("Content-Type", "application/x-www-form-urlencoded");
        for (int i = 1; i < params.length; i++) {
            String key = params[i].substring(0, params[i].indexOf("="));
            String value = params[i].substring(params[i].indexOf("=") + 1, params[i].length());
            requestParams.addBodyParameter(key, value);
        }
        Callback.Cancelable post = x.http().post(requestParams, getCommonCallback(callBack));
    }

    @Override
    public void get(CallBack<T> callBack, String... params) {

        RequestParams requestParams = new RequestParams(params[0]);
        api = params[0];
        for (int i = 1; i < params.length; i++) {
            String key = params[i].substring(0, params[i].indexOf("="));
            String value = params[i].substring(params[i].indexOf("=") + 1, params[i].length());
            requestParams.addQueryStringParameter(key, value);
        }
        Callback.Cancelable get = x.http().get(requestParams, getCommonCallback(callBack));
    }


    private Callback.CommonCallback<String> getCommonCallback(final CallBack<T> callBack) {
        return new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                String resultJson = null;
                try {
                    if (isUpdate) {
                        resultJson = AndroidDes3Util.decodeUpdate(result);
                    } else {
                        resultJson = AndroidDes3Util.decode(result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                callBack.result(resultJson);
                LogUtil.e(HLHttpUtils.class, "resultJson=" + resultJson);
                int errorCode = 0;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(resultJson);
                    errorCode = jsonObject.getInt("errcode");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (errorCode == 200) {
                    Type[] type = callBack.getClass().getGenericInterfaces();
                    ParameterizedType parameterizedType = (ParameterizedType) type[0];
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    Class<T> tClass = (Class<T>) actualTypeArguments[0];
                    T infoBean = GsonUtil.gsonToBean(resultJson, tClass);
                    callBack.onSuccess(infoBean);
                } else if (errorCode == 401) {
                    try {
                        callBack.onFailure(jsonObject.getString("errcode"), jsonObject.getString("errmsg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(MainActivity.IN_TOKEN);
                    Cons.doctorToken = null;
                    SPUtils.remove(DoctorApplication.context, "USERTOKEN");
                    DoctorApplication.context.sendBroadcast(intent);
                } else {
                    try {
                        callBack.onFailure(jsonObject.getString("errcode"), jsonObject.getString("errmsg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onFailure("-1", "很抱歉，网络故障，请稍后访问!");
                ex.printStackTrace();
            }

            @Override

            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        };

    }


    public void uploadFiles(String url, Map<String, String> map, final CallBack<UploadMoreResponse> callBack) {
        LogUtil.e("HLHttpUtils-接口请求入参：==", "HLHttpUtils-接口请求入参：==  \"" + url + "\"  入参==  " + url);

        String data = AndroidDes3Util.encode(GsonUtil.toJson(map));

        LogUtil.e("HLHttpUtils-接口请求入加密：==", "HLHttpUtils-接口请求入参：==  \"" + url + "\"  data==  " + data);



        RequestParams params = new RequestParams(url);
        for (String key : map.keySet()) {
            File file = new File(map.get(key));
            params.addBodyParameter(key, file, null, file.getName());
        }

        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String json = AndroidDes3Util.decode(result);
                LogUtil.e(BaseUpdateDownload.class, "json==" + json);
                UploadMoreResponse baseResponse = GsonUtil.gsonToBean(json, UploadMoreResponse.class);
                callBack.onSuccess(baseResponse);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.d(BaseUpdateDownload.class, "错误信息" + ex.getMessage());
                callBack.onFailure("-1", "很抱歉，网络故障，请稍后访问!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

            }
        });
    }
}


//package com.gagc.httplibrary;

//
//
//import android.content.Context;
//
//
//import com.gc.utils.AndroidDes3Util;
//import com.gc.utils.AppUtils;
//import com.gc.utils.GsonUtil;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.xutils.common.Callback;
//import org.xutils.http.RequestParams;
//import org.xutils.x;
//
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * Created by guoyuan on 23/02/2017.
// */
//
//public class HLHttpUtils<T extends BaseResponse> {
//    private String api;
//    String[] params;
//
//    private HLHttpUtils() {
//    }
//
//    public static class SingletonHolder {
//        public static final HLHttpUtils hLHttpUtils = new HLHttpUtils();
//    }
//
//    public static HLHttpUtils getInstance() {
//        return SingletonHolder.hLHttpUtils;
//    }
//
//    public HLHttpUtils setUrl(String url) {
//        this.api = url;
//        return SingletonHolder.hLHttpUtils;
//    }
//
//    public HLHttpUtils setParams(String... params) {
//        this.params = params;
//        return SingletonHolder.hLHttpUtils;
//    }
//
//    CallBack<T> callBack;
//
//    public HLHttpUtils setCallBack(CallBack<T> callBack) {
//        this.callBack = callBack;
//
//        return SingletonHolder.hLHttpUtils;
//    }
//
//
//    public void post() {
//        RequestParams requestParams = new RequestParams(api);
//        requestParams.addHeader("Content-Type", "application/x-www-form-urlencoded");
//        for (int i = 1; i < params.length; i++) {
//            String key = params[i].substring(0, params[i].indexOf("="));
//            String value = params[i].substring(params[i].indexOf("=") + 1, params[i].length());
//            requestParams.addBodyParameter(key, value);
//        }
//        x.http().post(requestParams, getCommonCallback(callBack));
//    }
//
//    public void get() {
//        RequestParams requestParams = new RequestParams(api);
//        for (int i = 1; i < params.length; i++) {
//            String key = params[i].substring(0, params[i].indexOf("="));
//            String value = params[i].substring(params[i].indexOf("=") + 1, params[i].length());
//            requestParams.addQueryStringParameter(key, value);
//        }
//        x.http().get(requestParams, getCommonCallback(callBack));
//    }
//
//
//    private Callback.CommonCallback<String> getCommonCallback(final CallBack<T> callBack) {
//        return new Callback.CommonCallback<String>() {
//
//            @Override
//            public void onSuccess(String result) {
//
//                String resultJson = null;
//                try {
//                    resultJson = AndroidDes3Util.decode(result);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                callBack.result(resultJson);
////                LogUtil.d(HLHttpUtils.class, "resultJson=" + resultJson);
//                int errorCode = 0;
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(resultJson);
//                    errorCode = jsonObject.getInt("errcode");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                if (errorCode == 200) {
//                    Type[] type = callBack.getClass().getGenericInterfaces();
//                    ParameterizedType parameterizedType = (ParameterizedType) type[0];
//                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//                    Class<T> tClass = (Class<T>) actualTypeArguments[0];
//
//                    T infoBean = GsonUtil.gsonToBean(resultJson, tClass);
//                    callBack.onSuccess(infoBean);
//                } else {
//                    try {
//                        callBack.onFailure(jsonObject.getString("errcode"), jsonObject.getString("errmsg"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                callBack.onFailure("-1", "很抱歉，网络故障，请稍后访问!");
//                ex.printStackTrace();
//            }
//
//            @Override
//
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        };
//
//    }
//
//    public static Map<String, String> getBaseMapList(Context context) {
//        Map<String, String> mapList = new HashMap<>();
//        mapList.put("clientTime", String.valueOf(System.currentTimeMillis()));
//        mapList.put("deviceType", "android");
//        mapList.put("cityLcode", "110100");
//        mapList.put("version", AppUtils.getVerName(context));
//        return mapList;
//    }
//
//    public interface CallBack<T> {
//        void onSuccess(T bean);
//
//        void onFailure(String code, String errormsg);
//
//        void result(String result);
//    }
//}
