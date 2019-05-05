package com.gc.basicpublicdoctor.bean;

import java.io.Serializable;
import java.util.List;

public class SubmitSign implements Serializable{
    /**
     * age : 26
     * birthday : 1992-07-18
     * classificationList : ["高血压"]
     * docTeamId : 3be9aed27d0c4af79f60c58ab837d944
     * householderIDCard : 321321199207184033
     * identityCard : 321321199207184036
     * identityCardImage : 图片4
     * pictureOneName : 图片1
     * pictureSignName : 图片3
     * pictureTwoName : 图片2
     * raceName : 汉族
     * relationship : 母亲
     * remarks : 备注
     * servicePackageList : [{"expiryDate":"2017-12-31~2018-12-31","serviceContent":[{"serviceItem":"3.5.4备选项 孕妇免疫九项","serviceItemId":"a7496d9dce1846b9bfa921cd9660b490"},{"serviceItem":"早孕甲功三项","serviceItemId":"941e65066ad541e3b14683b94c236b27"}],"servicePackageId":"adb90633e9d44ccb9e12b728e42c61c1","servicePackageName":"高级包-3.5 孕产妇"},{"expiryDate":"2017-12-31~2018-12-31","serviceContent":[{"serviceItem":"3.4.3 胸部CT","serviceItemId":"eac5acd94b53448f87f38af6fa919275"},{"serviceItem":"3.4.2 电解质","serviceItemId":"e453cfba21e64ecca16202eb26ac7054"}],"servicePackageId":"3a43f580cef7421085ee4baf1c6fd378","servicePackageName":"高级包-3.4 慢性呼吸道疾病型"}]
     * serviceTotalPrice : 5000
     * sex : 男
     * signTime : 2018-07-31
     * userAddress : 常州市
     * userName : 朱瑞
     * userPhone : 19975128086
     * userUid : 62059e4d4bba44578da9d9320923e838
     */

    private String age;
    private String birthday;
    private String docTeamId;
    private String householderIDCard;
    private String identityCard;
    private String identityCardImage;
    private String pictureOneName;
    private String pictureSignName;
    private String pictureTwoName;
    private String raceName;
    private String relationship;
    private String remarks;
    private String serviceTotalPrice;
    private String sex;
    private String signTime;
    private String userAddress;
    private String userName;
    private String userPhone;
    private String userUid;
    private List<String> classificationList;
    private List< ServicePackageListBean> servicePackageList;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDocTeamId() {
        return docTeamId;
    }

    public void setDocTeamId(String docTeamId) {
        this.docTeamId = docTeamId;
    }

    public String getHouseholderIDCard() {
        return householderIDCard;
    }

    public void setHouseholderIDCard(String householderIDCard) {
        this.householderIDCard = householderIDCard;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getIdentityCardImage() {
        return identityCardImage;
    }

    public void setIdentityCardImage(String identityCardImage) {
        this.identityCardImage = identityCardImage;
    }

    public String getPictureOneName() {
        return pictureOneName;
    }

    public void setPictureOneName(String pictureOneName) {
        this.pictureOneName = pictureOneName;
    }

    public String getPictureSignName() {
        return pictureSignName;
    }

    public void setPictureSignName(String pictureSignName) {
        this.pictureSignName = pictureSignName;
    }

    public String getPictureTwoName() {
        return pictureTwoName;
    }

    public void setPictureTwoName(String pictureTwoName) {
        this.pictureTwoName = pictureTwoName;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getServiceTotalPrice() {
        return serviceTotalPrice;
    }

    public void setServiceTotalPrice(String serviceTotalPrice) {
        this.serviceTotalPrice = serviceTotalPrice;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
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
/*

    public static class ServicePackageListBean {
        */
/**
 * expiryDate : 2017-12-31~2018-12-31
 * serviceContent : [{"serviceItem":"3.5.4备选项 孕妇免疫九项","serviceItemId":"a7496d9dce1846b9bfa921cd9660b490"},{"serviceItem":"早孕甲功三项","serviceItemId":"941e65066ad541e3b14683b94c236b27"}]
 * servicePackageId : adb90633e9d44ccb9e12b728e42c61c1
 * servicePackageName : 高级包-3.5 孕产妇
 *//*


        private String expiryDate;
        private String servicePackageId;
        private String servicePackageName;
        private List<ServiceContentBean> serviceContent;

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getServicePackageId() {
            return servicePackageId;
        }

        public void setServicePackageId(String servicePackageId) {
            this.servicePackageId = servicePackageId;
        }

        public String getServicePackageName() {
            return servicePackageName;
        }

        public void setServicePackageName(String servicePackageName) {
            this.servicePackageName = servicePackageName;
        }

        public List<ServiceContentBean> getServiceContent() {
            return serviceContent;
        }

        public void setServiceContent(List<ServiceContentBean> serviceContent) {
            this.serviceContent = serviceContent;
        }

        public static class ServiceContentBean {
            */
/**
 * serviceItem : 3.5.4备选项 孕妇免疫九项
 * serviceItemId : a7496d9dce1846b9bfa921cd9660b490
 *//*


            private String serviceItem;
            private String serviceItemId;

            public String getServiceItem() {
                return serviceItem;
            }

            public void setServiceItem(String serviceItem) {
                this.serviceItem = serviceItem;
            }

            public String getServiceItemId() {
                return serviceItemId;
            }

            public void setServiceItemId(String serviceItemId) {
                this.serviceItemId = serviceItemId;
            }
        }
    }
*/

}
