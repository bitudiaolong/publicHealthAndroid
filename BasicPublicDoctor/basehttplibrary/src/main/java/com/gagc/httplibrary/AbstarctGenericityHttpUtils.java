package com.gagc.httplibrary;


/**
 * Created by guoyuan on 06/02/2017.
 */

public abstract class AbstarctGenericityHttpUtils<T> {

    public abstract void post(CallBack<T> callBack, String... params);

    public abstract void get(CallBack<T> callBack, String... params);

    public interface CallBack<T> {
        void onSuccess(T bean);

        void onFailure(String code, String errormsg);

        void result(String result);
    }
}
