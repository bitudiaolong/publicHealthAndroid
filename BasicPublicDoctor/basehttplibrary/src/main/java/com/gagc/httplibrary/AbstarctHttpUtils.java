package com.gagc.httplibrary;


/**
 * Created by guoyuan on 06/02/2017.
 */

public abstract class AbstarctHttpUtils {

    public abstract void post(CallBack callBack, String... params);

    public abstract void get(CallBack callBack, String... params);

    public interface CallBack{
        void onSuccess(String res);
        void onFailure(int code, String errormsg);
    }
}
