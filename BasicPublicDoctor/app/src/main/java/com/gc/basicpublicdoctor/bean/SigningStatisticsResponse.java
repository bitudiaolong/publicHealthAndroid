package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

/**
 * Author:Created by zhurui
 * Time:2018/7/25 下午1:40
 * Description:This is SigningStatisticsResponse
 * 首页签约统计
 */
public class SigningStatisticsResponse extends BaseResponse {


    /**
     * data : {"newSigningNumber":200,"cumulativeNumber":500,"overdueNumber":200,"auditsNumber":333}
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
         * newSigningNumber : 200
         * cumulativeNumber : 500
         * overdueNumber : 200
         * auditsNumber : 333
         */

        private int newSigningNumber;
        private int cumulativeNumber;
        private int overdueNumber;
        private int auditsNumber;

        public int getNewSigningNumber() {
            return newSigningNumber;
        }

        public void setNewSigningNumber(int newSigningNumber) {
            this.newSigningNumber = newSigningNumber;
        }

        public int getCumulativeNumber() {
            return cumulativeNumber;
        }

        public void setCumulativeNumber(int cumulativeNumber) {
            this.cumulativeNumber = cumulativeNumber;
        }

        public int getOverdueNumber() {
            return overdueNumber;
        }

        public void setOverdueNumber(int overdueNumber) {
            this.overdueNumber = overdueNumber;
        }

        public int getAuditsNumber() {
            return auditsNumber;
        }

        public void setAuditsNumber(int auditsNumber) {
            this.auditsNumber = auditsNumber;
        }
    }
}
