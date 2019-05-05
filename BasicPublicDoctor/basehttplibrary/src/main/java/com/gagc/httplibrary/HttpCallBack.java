package com.gagc.httplibrary;

import org.xutils.common.Callback;

/**
 * Created by WUQING on 2017/7/25.
 */

public abstract class HttpCallBack implements Callback.CommonCallback<String> {
    @Override
    public void onSuccess(String result) {
        success(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        error("",ex.getMessage());
    }

    @Override
    public void onCancelled(CancelledException cex) {
        cancelled(cex.getMessage());
    }

    @Override
    public void onFinished() {
        finished();
    }

    public abstract void success(String result);
    public abstract void error(String errorCode,String errorMsg);
    public void cancelled(String errorMsg){}
    public void finished(){}
}
