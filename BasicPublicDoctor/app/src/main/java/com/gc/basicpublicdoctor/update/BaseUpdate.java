package com.gc.basicpublicdoctor.update;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.gagc.httplibrary.AbstarctGenericityHttpUtils;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.httputils.HLHttpUtils;
import com.gc.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by WUQING on 2017/9/19.
 */

public class BaseUpdate {

    public static void update(final Context context) {
        final int appVersionCode = getAPPVersionCode(context);
        Map<String, String> map = new HashMap<>();
        map.put("appKeyCode", Cons.APPKEYCODE);
        map.put("appVersionCode", String.valueOf(appVersionCode));
        new HLHttpUtils().postUpdate(map, Cons.UPDATE_URL, true).setCallBack(new AbstarctGenericityHttpUtils.CallBack<CheckVersionResponse>() {
            @Override
            public void onSuccess(final CheckVersionResponse bean) {
                int servicesCode = Integer.parseInt(bean.getData().getCode());
                if (servicesCode > appVersionCode) {
                    Log.e("infoD", "onSuccess: " + bean.getData().getUpdate());
                    if ("1".equals(bean.getData().getUpdate()) ||
                            bean.getData().getUpdate() == 1) {//强制升级
                        new AlertDialog.Builder(context)
                                .setTitle("有新版本请更新")
                                .setCancelable(false)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final BaseUpdateDownload download = new BaseUpdateDownload(context);
                                        download.downloadFile(bean.getData().getUrl(), bean.getData().getMd5());
                                    }
                                })
                                .show();
                    } else {//用户选择升级
                        new AlertDialog.Builder(context)
                                .setTitle("有新版本是否更新")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final BaseUpdateDownload download = new BaseUpdateDownload(context);
                                        download.downloadFile(bean.getData().getUrl(), bean.getData().getMd5());
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(String code, String errormsg) {
                LogUtil.e("BaseUpdate", "errormsg=" + errormsg);
            }

            @Override
            public void result(String result) {
                LogUtil.e("BaseUpdate", "result=" + result);

            }
        });
    }

    public static int getAPPVersionCode(Context ctx) {
        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
            System.out.println(currentVersionCode + " " + appVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    private static void checkUpdate(Context context, int versionCode, String versionName,
                                    String versionDesc, final String md5, final String url) {
        int nowVersionCode = getAppVersionName(context.getApplicationContext());
        Log.e("INFOD", "now[" + nowVersionCode + "]get[" + versionCode + "]");
        if (nowVersionCode != -1 && nowVersionCode < versionCode) {
            Log.e("INFOD", "开始升级");
            final BaseUpdateDownload download = new BaseUpdateDownload(context);
            new AlertDialog.Builder(context)
                    .setTitle("有新版本是否更新")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            download.downloadFile(url, md5);
                        }
                    })
                    .setNegativeButton("取消", null)
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
