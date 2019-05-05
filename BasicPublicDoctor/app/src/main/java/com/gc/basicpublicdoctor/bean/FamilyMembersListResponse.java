package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/25 下午1:42
 * Description:This is SignFamilyListResponse
 * 家庭成员列表
 */
public class FamilyMembersListResponse extends BaseResponse {

    /**
     * data : {"userPhone":"18812345678","userName":"张三","healthRoomName":"江苏省常州市天宁区社区服务中心","teamMembers":"医生a,医生b,医生c","list":[{"contractedUserIDCard":"321321199207184033","signId":"334334344","signState":"1","name":"朱瑞","age":"26","sex":"男","relationship":"户主本人","classificationList":["高血压","糖尿病","冠心病"],"servicePackageList":["初级包"]},{"contractedUserIDCard":"321321199207184033","signId":"5343434434","signState":"9","name":"朱瑞111","age":"26","sex":"男","relationship":"兄弟","classificationList":["高血压","糖尿病","冠心病"],"servicePackageList":["初级包","高级包","vip包"]}]}
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
         * userPhone : 18812345678
         * userName : 张三
         * healthRoomName : 江苏省常州市天宁区社区服务中心
         * teamMembers : 医生a,医生b,医生c
         * list : [{"contractedUserIDCard":"321321199207184033","signId":"334334344","signState":"1","name":"朱瑞","age":"26","sex":"男","relationship":"户主本人","classificationList":["高血压","糖尿病","冠心病"],"servicePackageList":["初级包"]},{"contractedUserIDCard":"321321199207184033","signId":"5343434434","signState":"9","name":"朱瑞111","age":"26","sex":"男","relationship":"兄弟","classificationList":["高血压","糖尿病","冠心病"],"servicePackageList":["初级包","高级包","vip包"]}]
         */

        private String userPhone;
        private String userName;
        private String healthRoomName;
        private String teamMembers;
        private List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * contractedUserIDCard : 321321199207184033
             * signId : 334334344
             * signState : 1
             * name : 朱瑞
             * age : 26
             * sex : 男
             * relationship : 户主本人
             * classificationList : ["高血压","糖尿病","冠心病"]
             * servicePackageList : ["初级包"]
             */

            private String contractedUserIDCard;
            private String signId;
            private String signState;
            private String name;
            private String age;
            private String sex;
            private String relationship;
            private List<String> classificationList;
            private List<String> servicePackageList;
            private String rongCloudToken;

            public String getContractedUserIDCard() {
                return contractedUserIDCard;
            }

            public void setContractedUserIDCard(String contractedUserIDCard) {
                this.contractedUserIDCard = contractedUserIDCard;
            }

            public String getSignId() {
                return signId;
            }

            public void setSignId(String signId) {
                this.signId = signId;
            }

            public String getSignState() {
                return signState;
            }

            public void setSignState(String signState) {
                this.signState = signState;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getRelationship() {
                return relationship;
            }

            public void setRelationship(String relationship) {
                this.relationship = relationship;
            }

            public List<String> getClassificationList() {
                return classificationList;
            }

            public void setClassificationList(List<String> classificationList) {
                this.classificationList = classificationList;
            }

            public List<String> getServicePackageList() {
                return servicePackageList;
            }

            public void setServicePackageList(List<String> servicePackageList) {
                this.servicePackageList = servicePackageList;
            }

            public String getRongCloudToken() {
                return rongCloudToken;
            }

            public void setRongCloudToken(String rongCloudToken) {
                this.rongCloudToken = rongCloudToken;
            }
        }
    }
}
