package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

/**
 * Author:Created by zhurui
 * Time:2018/7/25 下午1:40
 * Description:This is LoginResponse
 * 登录
 */
public class LoginResponse extends BaseResponse {

    /**
     * data : {"token":"sjdhfakldhflkadsfhljkas==","userUid":"666","userPicture":"http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png","userPhone":"18812345678","userName":"张三","userSex":"男","userAddress":"江苏省常州市天宁区社区服务中心","healthRoomName":"江苏省常州市天宁区社区服务中心","teamMembers":"医生a,医生b,医生c"}
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
         * token : sjdhfakldhflkadsfhljkas==
         * userUid : 666
         * userPicture : http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png
         * userPhone : 18812345678
         * userName : 张三
         * userSex : 男
         * userAddress : 江苏省常州市天宁区社区服务中心
         * healthRoomName : 江苏省常州市天宁区社区服务中心
         * teamMembers : 医生a,医生b,医生c
         */

        private String doctorToken;
        private String userUid;
        private String userPicture;
        private String userPhone;
        private String userName;
        private String userSex;
        private String userAddress;
        private String healthRoomName;
        private String teamMembers;
        private String docTeamName;
        private String doctorSignQRCode;

        public String getDoctorToken() {
            return doctorToken;
        }

        public void setDoctorToken(String doctorToken) {
            this.doctorToken = doctorToken;
        }

        public String getDoctorSignQRCode() {
            return doctorSignQRCode;
        }

        public void setDoctorSignQRCode(String doctorSignQRCode) {
            this.doctorSignQRCode = doctorSignQRCode;
        }

        public String getUserUid() {
            return userUid;
        }

        public void setUserUid(String userUid) {
            this.userUid = userUid;
        }

        public String getUserPicture() {
            return userPicture;
        }

        public void setUserPicture(String userPicture) {
            this.userPicture = userPicture;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getHealthRoomName() {
            return healthRoomName;
        }

        public void setHealthRoomName(String healthRoomName) {
            this.healthRoomName = healthRoomName;
        }

        public String getTeamMembers() {
            return teamMembers;
        }

        public void setTeamMembers(String teamMembers) {
            this.teamMembers = teamMembers;
        }

        public String getDocTeamName() {
            return docTeamName;
        }

        public void setDocTeamName(String docTeamName) {
            this.docTeamName = docTeamName;
        }
    }
}
