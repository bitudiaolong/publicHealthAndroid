package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/25 下午1:40
 * Description:This is LoginResponse
 * 登录
 */
public class ChatUserInfoResponse extends BaseResponse {

    /**
     * data : {"userChatList":[{"userChatId":"12g3jh1gjhg","userChatName":"丽萨","userChatHeadUrl":"http://touxiang1.jpg"},{"userChatId":"2k2hk1hkj","userChatName":"丽是","userChatHeadUrl":"http://touxiang1.jpg"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<UserChatListBean> userChatList;

        public List<UserChatListBean> getUserChatList() {
            return userChatList;
        }

        public void setUserChatList(List<UserChatListBean> userChatList) {
            this.userChatList = userChatList;
        }

        public static class UserChatListBean {
            /**
             * userChatId : 12g3jh1gjhg
             * userChatName : 丽萨
             * userChatHeadUrl : http://touxiang1.jpg
             */

            private String userChatId;
            private String userChatName;
            private String userChatHeadUrl;

            public String getUserChatId() {
                return userChatId;
            }

            public void setUserChatId(String userChatId) {
                this.userChatId = userChatId;
            }

            public String getUserChatName() {
                return userChatName;
            }

            public void setUserChatName(String userChatName) {
                this.userChatName = userChatName;
            }

            public String getUserChatHeadUrl() {
                return userChatHeadUrl;
            }

            public void setUserChatHeadUrl(String userChatHeadUrl) {
                this.userChatHeadUrl = userChatHeadUrl;
            }
        }
    }
}
