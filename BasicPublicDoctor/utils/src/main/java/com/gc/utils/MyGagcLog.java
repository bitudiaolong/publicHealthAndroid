package com.gc.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by WUQING on 2017/12/6.
 */

public class MyGagcLog {

    private static String TAG = "gagcLog";
    public static void initTAG(String tag){
        MyGagcLog.TAG = tag;
    }

    private boolean isShow = true;
    private boolean isShowContent = true;
    private static MyGagcLog myMessageUtils;
    private MyGagcLog(){}
    public static MyGagcLog getInstance(){
        if (myMessageUtils == null){
            myMessageUtils = new MyGagcLog();
        }
        return myMessageUtils;
    }

    public void showLogI(String msg){
        if (isShow){
            Log.i(TAG, msg);
        }
    }

    public void showLogD(String msg){
        if (isShow){
            Log.d(TAG, msg);
        }
    }

    public void showLogE(String msg){
        if (isShow){
            Log.e(TAG,msg);
        }
    }

    public void showContent(String msg){
        if (isShowContent){
            Log.e(TAG, msg);
        }
    }

    public void showTask(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void showTaskGetInfoError(Context context){
        showTask(context,"获取信息出错啦~");
    }

}
