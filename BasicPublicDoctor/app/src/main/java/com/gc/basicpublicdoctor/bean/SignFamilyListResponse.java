package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/25 下午1:42
 * Description:This is SignFamilyListResponse
 * 签约家庭
 */
public class SignFamilyListResponse extends BaseResponse {

    /**
     * data : {"page":{"totalPageCount":1,"totalCount":2,"pageSize":100,"currentPage":1},"list":[{"householderIDCard":"321321199207184033","householderName":"朱瑞","familyMembers":"朱瑞0,朱瑞1,朱瑞2"},{"householderIDCard":"321321199207184033","householderName":"张三丰","familyMembers":"朱瑞0,朱瑞1,朱瑞2"}]}
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
         * page : {"totalPageCount":1,"totalCount":2,"pageSize":100,"currentPage":1}
         * list : [{"householderIDCard":"321321199207184033","householderName":"朱瑞","familyMembers":"朱瑞0,朱瑞1,朱瑞2"},{"householderIDCard":"321321199207184033","householderName":"张三丰","familyMembers":"朱瑞0,朱瑞1,朱瑞2"}]
         */

        private PageBean page;
        private List<ListBean> list;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * totalPageCount : 1
             * totalCount : 2
             * pageSize : 100
             * currentPage : 1
             */

            private int totalPageCount;
            private int totalCount;
            private int pageSize;
            private int currentPage;

            public int getTotalPageCount() {
                return totalPageCount;
            }

            public void setTotalPageCount(int totalPageCount) {
                this.totalPageCount = totalPageCount;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }
        }

        public static class ListBean {
            /**
             * householderIDCard : 321321199207184033
             * householderName : 朱瑞
             * familyMembers : 朱瑞0,朱瑞1,朱瑞2
             */

            private String householderIDCard;
            private String householderName;
            private String familyMembers;

            public String getHouseholderIDCard() {
                return householderIDCard;
            }

            public void setHouseholderIDCard(String householderIDCard) {
                this.householderIDCard = householderIDCard;
            }

            public String getHouseholderName() {
                return householderName;
            }

            public void setHouseholderName(String householderName) {
                this.householderName = householderName;
            }

            public String getFamilyMembers() {
                return familyMembers;
            }

            public void setFamilyMembers(String familyMembers) {
                this.familyMembers = familyMembers;
            }
        }
    }
}
