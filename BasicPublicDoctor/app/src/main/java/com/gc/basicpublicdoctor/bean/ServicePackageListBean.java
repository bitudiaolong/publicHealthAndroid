package com.gc.basicpublicdoctor.bean;

import java.io.Serializable;
import java.util.List;

public class ServicePackageListBean implements Serializable {
    /**
     * servicePackageId : 234089243809
     * servicePackageName : 高级包
     * expiryDate : 2017-12-31~2018-12-31
     * suitablePerson : 所有人
     * servicePackDescribe : 该服务包内容是XXXXXXXXXXXXXXXXXXXXXXXX
     * totalPrice : 20000
     * serviceContent : [{"serviceItemId":"2342354234","serviceItem":"健康宣传册","projectContent":"每年得到免费发放的健康宣传单一份","unit":"2次/年","expenses":"2000"}]
     */

    private String servicePackageId;
    private String servicePackageName;
    private String expiryDate;
    private String suitablePerson;
    private String servicePackDescribe;
    private long totalPrice;
    private List<ServiceContentBean> serviceContent;

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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSuitablePerson() {
        return suitablePerson;
    }

    public void setSuitablePerson(String suitablePerson) {
        this.suitablePerson = suitablePerson;
    }

    public String getServicePackDescribe() {
        return servicePackDescribe;
    }

    public void setServicePackDescribe(String servicePackDescribe) {
        this.servicePackDescribe = servicePackDescribe;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ServiceContentBean> getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(List<ServiceContentBean> serviceContent) {
        this.serviceContent = serviceContent;
    }

}
