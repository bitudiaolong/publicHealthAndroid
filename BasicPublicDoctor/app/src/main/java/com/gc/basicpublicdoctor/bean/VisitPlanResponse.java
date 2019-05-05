package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

/**
 * Author:Created by zhurui
 * Time:2018/7/25 下午1:41
 * Description:This is VisitPlanResponse
 * 随访计划
 */
public class VisitPlanResponse extends BaseResponse {

    /**
     * data : {"firstVisit":200,"dayVisit":500,"weekVisit":200,"auditsVisit":333}
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
         * firstVisit : 200
         * dayVisit : 500
         * weekVisit : 200
         * auditsVisit : 333
         */

        private int firstVisit;
        private int dayVisit;
        private int weekVisit;
        private int auditsVisit;

        public int getFirstVisit() {
            return firstVisit;
        }

        public void setFirstVisit(int firstVisit) {
            this.firstVisit = firstVisit;
        }

        public int getDayVisit() {
            return dayVisit;
        }

        public void setDayVisit(int dayVisit) {
            this.dayVisit = dayVisit;
        }

        public int getWeekVisit() {
            return weekVisit;
        }

        public void setWeekVisit(int weekVisit) {
            this.weekVisit = weekVisit;
        }

        public int getAuditsVisit() {
            return auditsVisit;
        }

        public void setAuditsVisit(int auditsVisit) {
            this.auditsVisit = auditsVisit;
        }
    }
}
