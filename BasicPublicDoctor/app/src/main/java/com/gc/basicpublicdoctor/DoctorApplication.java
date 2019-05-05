package com.gc.basicpublicdoctor;

import android.content.Context;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;

import com.gagc.httplibrary.HttpManager;
import com.gc.basicpublicdoctor.cons.Cons;
import com.gc.basicpublicdoctor.service.rongimservice.MyConnectionStatusListener;
import com.gc.utils.AndroidDes3Util;
import com.gc.utils.AppUtils;
import com.gc.utils.LogUtil;
import com.gc.utils.MacUtils;
import com.gc.utils.SystemPropertiesInvoker;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Author:Created by zhurui
 * Time:2018/7/10 上午10:53
 * Description:This is DoctorApplication
 */
public class DoctorApplication extends MultiDexApplication {
    public static Context context;
    /**
     * 用户融云token
     */
    public static String RY_TOKEN;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MultiDex.install(this);
        HttpManager.initX(this);
        setBaseUrl();

        getDeviceId();

        // 融云初始化
//        RongIM.init(this);
        RongIM.init(this, Cons.RongYunKey);
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
    }


    public void setBaseUrl() {
//        String type = "测试";
//        String type = "开发";
        String type = "正式";
//        String type = "本地、服务器";
//          String type = "演示";

        if ("测试".equals(type)) {
            Cons.URL_BASE = "http://192.168.10.14:8093/open/";
            Cons.FILE_SERVER_URL = "http://192.168.10.14:8099/open/";
            AndroidDes3Util.secretKey = "gagc#2017ABCDgagc#2017ABCD";
            AndroidDes3Util.secretKeyFile = "gagc#2017ABCDgagc#2017ABCD";
            Cons.RongYunKey = "kj7swf8ok1m02";
        } else if ("开发".equals(type)) {
            Cons.URL_BASE = "https://192.168.10.12:8084/open/";
            Cons.FILE_SERVER_URL = "https://192.168.10.12:8099/open/";
            AndroidDes3Util.secretKey = "gagc#2017ABCDgagc#2017ABCD";
            AndroidDes3Util.secretKeyFile = "gagc#2017ABCDgagc#2017ABCD";
            Cons.RongYunKey = "kj7swf8ok1m02";
        } else if ("正式".equals(type)) {
            Cons.URL_BASE = "https://family.health.gagctv.com/open/";
            Cons.FILE_SERVER_URL = "https://img.health.gagctv.com/open/";
            AndroidDes3Util.secretKey = "H5ZLBACzrQARj7FOtpwIpvnP";
            AndroidDes3Util.secretKeyFile = "H5ZLBACzrQARj7FOtpwIpvnP";
            Cons.RongYunKey = "qf3d5gbjqh4mh";
        } else if ("本地服务器".equals(type)) {
            Cons.URL_BASE = "https://192.168.10.103:8081/open/";
            Cons.FILE_SERVER_URL = "https://192.168.10.12:8099/open/";
            AndroidDes3Util.secretKey = "gagc#2017ABCDgagc#2017ABCD";
            AndroidDes3Util.secretKeyFile = "gagc#2017ABCDgagc#2017ABCD";
            Cons.RongYunKey = "kj7swf8ok1m02";
        } else if (type.equals("演示")) {
            Cons.URL_BASE = "https://family.healthdemo.gagctv.com/open/";
            Cons.FILE_SERVER_URL = "https://img.health.gagctv.com/open/";
            AndroidDes3Util.secretKey = "H5ZLBACzrQARj7FOtpwIpvnP";
            AndroidDes3Util.secretKeyFile = "H5ZLBACzrQARj7FOtpwIpvnP";
            Cons.RongYunKey = "qf3d5gbjqh4mh";
        }
    }

    public void getHomeData() {

        String[] params = {
                "areaAlis", "0",
                "level", "2",

        };
    }

    public void getDeviceId() {
        Cons.SYS_DEVICEID = SystemPropertiesInvoker.get(getApplicationContext(), "persist.sys.hwconfig.stb_id");
        if (TextUtils.isEmpty(Cons.SYS_DEVICEID)) {
            MacUtils utils = new MacUtils();
            Cons.SYS_DEVICEID = utils.getMacAddressString();
        }
        Log.e("DoctorApplication", Cons.SYS_DEVICEID);
        LogUtil.e("DoctorApplication-----SYS_DEVICEID", Cons.SYS_DEVICEID);
    }

    public static Map<String, String> getBaseMapList() {
        Map<String, String> mapList = new HashMap<>();
        mapList.put("clientTime", String.valueOf(System.currentTimeMillis()));
        mapList.put("deviceType", "android");
        mapList.put("cityLcode", "110100");
        mapList.put("version", AppUtils.getVerName(context));
        mapList.put("deviceId", Cons.SYS_DEVICEID);
        return mapList;
    }

    /**
     * 融云连接
     *
     * @author zhurui
     * @time 2018/8/3 上午10:59
     */
    public static void connect(final String ry_token) {
        Log.e("infoRY--connect", "connect: " + ry_token);
        RongIM.connect(ry_token,
                new RongIMClient.ConnectCallback() {

                    /**
                     * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                     *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                     */
                    @Override
                    public void onTokenIncorrect() {
                        Log.e("infoRY", "onTokenIncorrect: ");
                    }

                    @Override
                    public void onSuccess(String userId) {
                        Log.e("infoRY", "onSuccess: [" + userId + "]");

                        if (RongIM.getInstance() != null) {
                            String targetID = RongIM.getInstance().getCurrentUserId();
                            String userRYName = Cons.userName;
                            String userRYHeadImage = Cons.userPicture;

                            Log.e("infoRY", "onSuccess: [" + targetID + "--" + userRYName + "--" + userRYHeadImage + "]");
                            RongIM.getInstance().setCurrentUserInfo(new UserInfo(targetID, userRYName, Uri.parse(userRYHeadImage)));
                            RongIM.getInstance().setMessageAttachedUserInfo(true);
                        }
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.e("infoRY", "onError: [" + errorCode + "]");
                    }
                });

    }
}
