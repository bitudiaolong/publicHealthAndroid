package com.gagc.httplibrary;

/**
 * Created by guoyuan on 07/12/2016.
 */

public class BaseResponse {
        private int errcode;
        private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
