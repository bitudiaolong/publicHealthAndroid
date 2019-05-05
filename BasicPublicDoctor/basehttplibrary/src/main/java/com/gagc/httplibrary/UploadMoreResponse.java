package com.gagc.httplibrary;

import java.util.Map;

public class UploadMoreResponse extends BaseResponse {


    Map<String, String> data;


    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
