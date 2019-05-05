package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

import java.util.List;

public class SignDetailResponse extends BaseResponse {


    /**
     * data : {"signId":"203498","signState":"1","submitDate":"2018-05-22","auditDate":"2018-05-23","signDate":"2018-05-24","overdueDate":"2019-05-24","docTeamId":"122","docTeamName":"朱医生团队","mainDocName":"朱瑞","mainDocTel":"19911111111","healthRoomName":"新北区辽河卫生室","healthRoomImageUrl":"http://www.gagctv.com/023984.jpg","signCount":"2309","teamMembers":"喻越，王俏，大雄","userName":"张三丰","identityCard":"320312199512205409","sex":"男","raceName":"汉族","birthday":"2010-02-12","age":"22","userPhone":"18812341242","userAddress":"江苏省常州","classificationList":["高血压","糖尿病","冠心病"],"signAgreementUrl":"http://img.gagctv.com/17103114214744738","userSignAgrennment":"用户协议富文本","relationship":"父子","serviceTotalPrice":20000,"remarks":"备注说明内容详情","rejectReason":"驳回原因","pictureOneName":"http://img.gagctv.com/17103114214744738","pictureTwoName":"http://img.gagctv.com/17103114214744738","pictureSignName":"http://img.gagctv.com/17103114214744738","servicePackageList":[{"servicePackageName":"高级包","expiryDate":"2017-12-31~2018-12-31","suitablePerson":"所有人","servicePackDescribe":"该服务包内容是XXXXXXXXXXXXXXXXXXXXXXXX","totalPrice":20000,"serviceContent":[{"serviceItem":"健康宣传册","projectContent":"每年得到免费发放的健康宣传单一份","unit":"2次/年","expenses":2000}]},{"servicePackageName":"高级包","expiryDate":"2017-12-31~2018-12-31","suitablePerson":"所有人","servicePackDescribe":"该服务包内容是XXXXXXXXXXXXXXXXXXXXXXXX","totalPrice":0,"serviceContent":[{"serviceItem":"健康宣传册","projectContent":"每年得到免费发放的健康宣传单一份","unit":"2次/年","expenses":0},{"serviceItem":"健康宣传册","projectContent":"每年得到免费发放的健康宣传单一份","unit":"2次/年","expenses":0}]}]}
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
         * signId : 203498
         * signState : 1
         * submitDate : 2018-05-22
         * auditDate : 2018-05-23
         * signDate : 2018-05-24
         * overdueDate : 2019-05-24
         * docTeamId : 122
         * docTeamName : 朱医生团队
         * mainDocName : 朱瑞
         * mainDocTel : 19911111111
         * healthRoomName : 新北区辽河卫生室
         * healthRoomImageUrl : http://www.gagctv.com/023984.jpg
         * signCount : 2309
         * teamMembers : 喻越，王俏，大雄
         * userName : 张三丰
         * identityCard : 320312199512205409
         * sex : 男
         * raceName : 汉族
         * birthday : 2010-02-12
         * age : 22
         * userPhone : 18812341242
         * userAddress : 江苏省常州
         * classificationList : ["高血压","糖尿病","冠心病"]
         * signAgreementUrl : http://img.gagctv.com/17103114214744738
         * userSignAgrennment : 用户协议富文本
         * relationship : 父子
         * serviceTotalPrice : 20000
         * remarks : 备注说明内容详情
         * rejectReason : 驳回原因
         * pictureOneName : http://img.gagctv.com/17103114214744738
         * pictureTwoName : http://img.gagctv.com/17103114214744738
         * pictureSignName : http://img.gagctv.com/17103114214744738
         * servicePackageList : [{"servicePackageName":"高级包","expiryDate":"2017-12-31~2018-12-31","suitablePerson":"所有人","servicePackDescribe":"该服务包内容是XXXXXXXXXXXXXXXXXXXXXXXX","totalPrice":20000,"serviceContent":[{"serviceItem":"健康宣传册","projectContent":"每年得到免费发放的健康宣传单一份","unit":"2次/年","expenses":2000}]},{"servicePackageName":"高级包","expiryDate":"2017-12-31~2018-12-31","suitablePerson":"所有人","servicePackDescribe":"该服务包内容是XXXXXXXXXXXXXXXXXXXXXXXX","totalPrice":0,"serviceContent":[{"serviceItem":"健康宣传册","projectContent":"每年得到免费发放的健康宣传单一份","unit":"2次/年","expenses":0},{"serviceItem":"健康宣传册","projectContent":"每年得到免费发放的健康宣传单一份","unit":"2次/年","expenses":0}]}]
         */

        private String signId;
        private String signState;
        private String submitDate;
        private String auditDate;
        private String signDate;
        private String overdueDate;
        private String docTeamId;
        private String docTeamName;
        private String mainDocName;
        private String mainDocTel;
        private String healthRoomName;
        private String healthRoomImageUrl;
        private String signCount;
        private String teamMembers;
        private String userName;
        private String householderIDCard;
        private String identityCard;
        private String sex;
        private String raceName;
        private String birthday;
        private String age;
        private String userPhone;
        private String userAddress;
        private String signAgreementUrl;
        private String userSignAgrennment;
        private String relationship;
        private int serviceTotalPrice;
        private String remarks;
        private String rejectReason;
        private String pictureOneName;
        private String pictureTwoName;
        private String pictureSignName;
        private List<String> classificationList;
        private List<ServicePackageListBean> servicePackageList;
        private String signType;

        public String getHouseholderIDCard() {
            return householderIDCard;
        }

        public void setHouseholderIDCard(String householderIDCard) {
            this.householderIDCard = householderIDCard;
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

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignState(String signState) {
            this.signState = signState;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public void setSubmitDate(String submitDate) {
            this.submitDate = submitDate;
        }

        public String getAuditDate() {
            return auditDate;
        }

        public void setAuditDate(String auditDate) {
            this.auditDate = auditDate;
        }

        public String getSignDate() {
            return signDate;
        }

        public void setSignDate(String signDate) {
            this.signDate = signDate;
        }

        public String getOverdueDate() {
            return overdueDate;
        }

        public void setOverdueDate(String overdueDate) {
            this.overdueDate = overdueDate;
        }

        public String getDocTeamId() {
            return docTeamId;
        }

        public void setDocTeamId(String docTeamId) {
            this.docTeamId = docTeamId;
        }

        public String getDocTeamName() {
            return docTeamName;
        }

        public void setDocTeamName(String docTeamName) {
            this.docTeamName = docTeamName;
        }

        public String getMainDocName() {
            return mainDocName;
        }

        public void setMainDocName(String mainDocName) {
            this.mainDocName = mainDocName;
        }

        public String getMainDocTel() {
            return mainDocTel;
        }

        public void setMainDocTel(String mainDocTel) {
            this.mainDocTel = mainDocTel;
        }

        public String getHealthRoomName() {
            return healthRoomName;
        }

        public void setHealthRoomName(String healthRoomName) {
            this.healthRoomName = healthRoomName;
        }

        public String getHealthRoomImageUrl() {
            return healthRoomImageUrl;
        }

        public void setHealthRoomImageUrl(String healthRoomImageUrl) {
            this.healthRoomImageUrl = healthRoomImageUrl;
        }

        public String getSignCount() {
            return signCount;
        }

        public void setSignCount(String signCount) {
            this.signCount = signCount;
        }

        public String getTeamMembers() {
            return teamMembers;
        }

        public void setTeamMembers(String teamMembers) {
            this.teamMembers = teamMembers;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIdentityCard() {
            return identityCard;
        }

        public void setIdentityCard(String identityCard) {
            this.identityCard = identityCard;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getRaceName() {
            return raceName;
        }

        public void setRaceName(String raceName) {
            this.raceName = raceName;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getSignAgreementUrl() {
            return signAgreementUrl;
        }

        public void setSignAgreementUrl(String signAgreementUrl) {
            this.signAgreementUrl = signAgreementUrl;
        }

        public String getUserSignAgrennment() {
            return userSignAgrennment;
        }

        public void setUserSignAgrennment(String userSignAgrennment) {
            this.userSignAgrennment = userSignAgrennment;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public int getServiceTotalPrice() {
            return serviceTotalPrice;
        }

        public void setServiceTotalPrice(int serviceTotalPrice) {
            this.serviceTotalPrice = serviceTotalPrice;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public String getPictureOneName() {
            return pictureOneName;
        }

        public void setPictureOneName(String pictureOneName) {
            this.pictureOneName = pictureOneName;
        }

        public String getPictureTwoName() {
            return pictureTwoName;
        }

        public void setPictureTwoName(String pictureTwoName) {
            this.pictureTwoName = pictureTwoName;
        }

        public String getPictureSignName() {
            return pictureSignName;
        }

        public void setPictureSignName(String pictureSignName) {
            this.pictureSignName = pictureSignName;
        }

        public List<String> getClassificationList() {
            return classificationList;
        }

        public void setClassificationList(List<String> classificationList) {
            this.classificationList = classificationList;
        }

        public List<ServicePackageListBean> getServicePackageList() {
            return servicePackageList;
        }

        public void setServicePackageList(List<ServicePackageListBean> servicePackageList) {
            this.servicePackageList = servicePackageList;
        }

//
    }
}
