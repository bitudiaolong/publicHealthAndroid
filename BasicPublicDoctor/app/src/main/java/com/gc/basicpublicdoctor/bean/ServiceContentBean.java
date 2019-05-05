package com.gc.basicpublicdoctor.bean;

import java.io.Serializable;

public class ServiceContentBean implements Serializable {
    /**
     * serviceItemId : 2342354234
     * serviceItem : 健康宣传册
     * projectContent : 每年得到免费发放的健康宣传单一份
     * unit : 2次/年
     * expenses : 2000
     */

    private String serviceId;
    private String serviceItem;
    private String projectContent;
    private String unit;
    private long expenses;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public String getserviceId() {
        return serviceId;
    }

    public void setserviceId(String serviceItemId) {
        this.serviceId = serviceItemId;
    }

    public String getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(String serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(String projectContent) {
        this.projectContent = projectContent;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getExpenses() {
        return expenses;
    }

    public void setExpenses(long expenses) {
        this.expenses = expenses;
    }

}
