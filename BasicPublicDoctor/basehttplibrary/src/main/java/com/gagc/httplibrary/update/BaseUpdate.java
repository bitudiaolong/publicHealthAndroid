package com.gagc.httplibrary.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.gagc.httplibrary.HttpCallBack;
import com.gagc.httplibrary.HttpManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WUQING on 2017/9/19.
 */

public class BaseUpdate {

    private static String TAG = "GAGC_UPDATE";

    public static void updateNew(final Context context,String url,String appKeyCode){
        int nowVersionCode = getAppVersionName(context);
        if (nowVersionCode != -1) {
//            Map<String,String> map = new HashMap<>();
//            map.put("appKeyCode",appKeyCode);
//            map.put("appVersionCode",String.valueOf(nowVersionCode));
//            String data = null;
//            try {
//                data = URLEncoder.encode(Des3Util.encodeUpdate(new JSONObject(map).toString()),"UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                Log.e(TAG, "updateNew: UrlEncodeError["+e+"]");
//            }
            String[] params = {
//                    "data", data
                    "appKeyCode",appKeyCode,
                    "appVersionCode",String.valueOf(nowVersionCode),
                    "debug","1"
            };
            HttpManager.getData("updateNew", params, url, new HttpCallBack() {
                @Override
                public void success(String result) {
                    try {
                        Log.e(TAG, "success:before result["+result+"]");
                        //result = Des3Util.decodeUpdate(result);
                        //Log.e(TAG, "success:after result["+result+"]");

                        JSONObject jsonObject = new JSONObject(result);
                        int errCode = jsonObject.getInt("errcode");
                        String errmsg = jsonObject.getString("errmsg");
                        if (errCode == 200) {
                            JSONObject dataObj = jsonObject.getJSONObject("data");
                            String vc = dataObj.getString("code");
                            int versionCode = Integer.parseInt(vc);
                            String versionName = dataObj.getString("name");
                            String versionDesc = dataObj.getString("desc");
                            String md5 = dataObj.getString("md5");
                            String url = dataObj.getString("url");
                            int mustUpdate = dataObj.getInt("update");
                            checkUpdateNew(context, versionCode, versionName, versionDesc, md5, url,mustUpdate);
                        } else {
                            Log.e(TAG, "Update Error[" + errCode + "][" + errmsg + "]");
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Update JSON Error[" + result + "]e[" + e + "]");
                        Toast.makeText(context, "数据解析出错[" + result + "]", Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Log.e(TAG, "Update Exception[" + result + "]e[" + e + "]");
                        Toast.makeText(context, "升级数据出错[" + result + "]", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void error(String errorCode, String errorMsg) {
                    Log.e(TAG, "Update Error[" + errorCode + "][" + errorMsg + "]");
                    //Toast.makeText(context, "升级出错["+errorCode+"]["+errorMsg+"]", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Log.e(TAG, "update:得到versionCode=-1");
        }
    }

    private static AlertDialog alertDialog = null;
    private static void checkUpdateNew(Context context, int versionCode, String versionName,
                                    String versionDesc, final String md5, final String url,
                                       int mustUpdate) {
        int nowVersionCode = getAppVersionName(context.getApplicationContext());
        Log.e(TAG, "now["+nowVersionCode+"]get["+versionCode+"]");
        if (nowVersionCode != -1 && nowVersionCode < versionCode){
            Log.e(TAG, "开始升级");
            final BaseUpdateDownload download = new BaseUpdateDownload(context);
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(versionDesc);
            if (mustUpdate == 1){
                builder.setCancelable(false);
                builder.setTitle("发现新版本,5秒后自动下载");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (alertDialog != null){
                            alertDialog.dismiss();
                        }
                        download.downloadFile(url, md5);
                    }
                },5000);
            }else{
                builder.setTitle("发现新版本");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        download.downloadFile(url,md5);
                    }
                });
                builder.setNegativeButton("取消",null);
            }

            alertDialog = builder.show();
//            new AlertDialog.Builder(context)
//                    .setTitle("有新版本是否更新")
//                    .setMessage(versionDesc)
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            download.downloadFile(url, md5);
//                        }
//                    })
//                    .setNegativeButton("取消",null)
//                    .show();
        }
    }

    public static void update(final Context context, String url){

        HttpManager.getData("update", null, url, new HttpCallBack() {
            @Override
            public void success(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int errCode = jsonObject.getInt("errcode");
                    String errmsg = jsonObject.getString("errmsg");
                    if (errCode == 0) {
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                        int versionCode = dataObj.getInt("versionCode");
                        String versionName = dataObj.getString("versionName");
                        String versionDesc = dataObj.getString("versionDesc");
                        String md5 = dataObj.getString("md5");
                        String url = dataObj.getString("url");
                        checkUpdate(context, versionCode, versionName, versionDesc, md5, url);
                    } else {
                        Log.e(TAG, "Update Error[" + errCode + "][" + errmsg + "]");
                        Toast.makeText(context, "数据请求出错[" + errCode + "][" + errmsg + "]", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Update JSON Error[" + result + "]e[" + e + "]");
                    Toast.makeText(context, "数据解析出错[" + result + "]", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void error(String errorCode, String errorMsg) {
                Log.e(TAG, "Update Error[" + errorCode + "][" + errorMsg + "]");
                //Toast.makeText(context, "升级出错["+errorCode+"]["+errorMsg+"]", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void checkUpdate(Context context, int versionCode, String versionName,
                                    String versionDesc, final String md5, final String url) {
        int nowVersionCode = getAppVersionName(context.getApplicationContext());
        Log.e(TAG, "now["+nowVersionCode+"]get["+versionCode+"]");
        if (nowVersionCode != -1 && nowVersionCode < versionCode){
            Log.e(TAG, "开始升级");
            final BaseUpdateDownload download = new BaseUpdateDownload(context);
            new AlertDialog.Builder(context)
                    .setTitle("有新版本是否更新")
                    .setMessage(versionDesc)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            download.downloadFile(url, md5);
                        }
                    })
                    .setNegativeButton("取消",null)
                    .show();
        }
    }

    /**
     * 返回当前程序版本名
     */
    private static int getAppVersionName(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                versioncode = -1;
            }
        } catch (Exception e) {
            versioncode = -1;
        }
        return versioncode;
    }

}
