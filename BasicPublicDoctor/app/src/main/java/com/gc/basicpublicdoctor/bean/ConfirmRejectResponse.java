package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

public class ConfirmRejectResponse extends BaseResponse {

    /**
     * data : {}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
