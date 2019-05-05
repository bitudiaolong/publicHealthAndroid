package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

/**
 * Author:Created by zhurui
 * Time:2018/8/3 上午11:23
 * Description:This is GetChatTokenResponse
 * 获取融云聊天token
 */
public class GetChatTokenResponse extends BaseResponse {

    /**
     * data : {"userToken":"9989d9yas9saydf9a9f"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userToken : 9989d9yas9saydf9a9f
         */

        private String userToken;

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }
    }
}
