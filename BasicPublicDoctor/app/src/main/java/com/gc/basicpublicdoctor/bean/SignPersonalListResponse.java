package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/30 上午9:58
 * Description:This is SignPersonalListResponse
 * 签约列表个人
 */
public class SignPersonalListResponse extends BaseResponse {

    /**
     * data : {"page":{"totalPageCount":1,"totalCount":2,"pageSize":100,"currentPage":1},"list":[{"contractedUserIDCard":"321321199207184033","signId":"232323232444","signState":"1","name":"朱瑞","age":"26","sex":"男","phone":"19975128086","address":"江苏省常州市天宁区社区服务中心"},{"contractedUserIDCard":"321321199207184033","signId":"333423434","signState":"9","name":"朱瑞111","age":"26","sex":"男","phone":"19975128086","address":"江苏省常州市天宁区社区服务中心"}]}
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
         * list : [{"contractedUserIDCard":"321321199207184033","signId":"232323232444","signState":"1","name":"朱瑞","age":"26","sex":"男","phone":"19975128086","address":"江苏省常州市天宁区社区服务中心"},{"contractedUserIDCard":"321321199207184033","signId":"333423434","signState":"9","name":"朱瑞111","age":"26","sex":"男","phone":"19975128086","address":"江苏省常州市天宁区社区服务中心"}]
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
             * contractedUserIDCard : 321321199207184033
             * signId : 232323232444
             * signState : 1
             * name : 朱瑞
             * age : 26
             * sex : 男
             * phone : 19975128086
             * address : 江苏省常州市天宁区社区服务中心
             */

            private String contractedUserIDCard;
            private String signId;
            private String signState;
            private String name;
            private String age;
            private String sex;
            private String phone;
            private String address;
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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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
