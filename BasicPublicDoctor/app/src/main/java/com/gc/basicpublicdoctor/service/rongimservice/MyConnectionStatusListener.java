package com.gc.basicpublicdoctor.service.rongimservice;

import android.util.Log;

import io.rong.imlib.RongIMClient.ConnectionStatusListener;

/**
 * Created by guanxindashi on 2018/5/25.
 */

public class MyConnectionStatusListener implements ConnectionStatusListener {
    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus) {
            case CONNECTED:             //  连接成功
                Log.d("infoD", "连接成功");
                break;
            case DISCONNECTED:          //  断开连接
                Log.d("infoD", "断开连接");
                break;
            case CONNECTING:            //  连接中
                Log.d("infoD", "连接中");
                break;
            case NETWORK_UNAVAILABLE:   //  网络不可用
                Log.d("infoD", "网络不可用");
                break;

            case KICKED_OFFLINE_BY_OTHER_CLIENT:    // 用户帐户在其他设备上登录,本机会被踢掉线
                Log.d("infoD", "用户帐户在其他设备上登录");
                break;
        }
    }
}
