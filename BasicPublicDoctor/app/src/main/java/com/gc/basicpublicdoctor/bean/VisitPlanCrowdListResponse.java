package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/30 上午9:57
 * Description:This is VisitPlanCrowdListResponse
 * 随访计划人群列表实体类
 */
public class VisitPlanCrowdListResponse extends BaseResponse {
    /**
     * data : {"page":{"pageSize":"10","totalPageCount":1,"currentPage":"1","totalCount":"5"},"list":[{"classificationList":"","address":"山西省武乡县丰州镇太行街377号","phone":"13821864182","idCard":"140429199301088437","sex":"男","name":"王洋","id":"81e0d7a4a98748a682a657a5591c6662","age":25},{"classificationList":"","address":"江苏省常州市新北区国宾花园32幢甲单元100室","phone":"15090543298","idCard":"321284198901127616","sex":"男","name":"杨凌","id":"320d49e9810e49688e88f28a23d0c2b2","age":29},{"classificationList":"","address":"江苏省常州市天宁区丽垡三午41幢乙单元501室","phone":"15065325423","idCard":"321321199207184033","sex":"男","name":"朱瑞","id":"e6fca78dcb474e37bdfe78bc102ccb15","age":26},{"classificationList":"","address":"奖励你您","phone":"15098542356","idCard":"411423199004180525","sex":"男","name":"猪瑞","id":"223d3cc5f3fe45a8aac63133d7364d45","age":28},{"classificationList":"","address":"河南省商丘市","phone":"15090543298","idCard":"411423199404180525","sex":"女","name":"吕雪莉","id":"da2e7de3fd7e4d70b61bb7cee4deeccf","age":24}]}
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
         * page : {"pageSize":"10","totalPageCount":1,"currentPage":"1","totalCount":"5"}
         * list : [{"classificationList":"","address":"山西省武乡县丰州镇太行街377号","phone":"13821864182","idCard":"140429199301088437","sex":"男","name":"王洋","id":"81e0d7a4a98748a682a657a5591c6662","age":25},{"classificationList":"","address":"江苏省常州市新北区国宾花园32幢甲单元100室","phone":"15090543298","idCard":"321284198901127616","sex":"男","name":"杨凌","id":"320d49e9810e49688e88f28a23d0c2b2","age":29},{"classificationList":"","address":"江苏省常州市天宁区丽垡三午41幢乙单元501室","phone":"15065325423","idCard":"321321199207184033","sex":"男","name":"朱瑞","id":"e6fca78dcb474e37bdfe78bc102ccb15","age":26},{"classificationList":"","address":"奖励你您","phone":"15098542356","idCard":"411423199004180525","sex":"男","name":"猪瑞","id":"223d3cc5f3fe45a8aac63133d7364d45","age":28},{"classificationList":"","address":"河南省商丘市","phone":"15090543298","idCard":"411423199404180525","sex":"女","name":"吕雪莉","id":"da2e7de3fd7e4d70b61bb7cee4deeccf","age":24}]
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
             * pageSize : 10
             * totalPageCount : 1
             * currentPage : 1
             * totalCount : 5
             */

            private String pageSize;
            private int totalPageCount;
            private String currentPage;
            private String totalCount;

            public String getPageSize() {
                return pageSize;
            }

            public void setPageSize(String pageSize) {
                this.pageSize = pageSize;
            }

            public int getTotalPageCount() {
                return totalPageCount;
            }

            public void setTotalPageCount(int totalPageCount) {
                this.totalPageCount = totalPageCount;
            }

            public String getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(String currentPage) {
                this.currentPage = currentPage;
            }

            public String getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(String totalCount) {
                this.totalCount = totalCount;
            }
        }

        public static class ListBean {
            /**
             * classificationList :
             * address : 山西省武乡县丰州镇太行街377号
             * phone : 13821864182
             * idCard : 140429199301088437
             * sex : 男
             * name : 王洋
             * id : 81e0d7a4a98748a682a657a5591c6662
             * age : 25
             */

            private String classificationList;
            private String address;
            private String phone;
            private String idCard;
            private String sex;
            private String name;
            private String id;
            private int age;

            public String getClassificationList() {
                return classificationList;
            }

            public void setClassificationList(String classificationList) {
                this.classificationList = classificationList;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }
        }
    }


//    /**
//     * errcode : 0
//     * errmsg :
//     * data : {"page":{"totalPageCount":1,"totalCount":2,"pageSize":100,"currentPage":1},"list":[{"id":"334334344","IDCard":"321321199207184033","name":"朱瑞","age":"25","sex":"男","phone":"19912121212","address":"江苏省常州市新北区","classificationList":["高血压","糖尿病","冠心病"]},{"id":"r2323r23","IDCard":"321321199207184033","name":"朱瑞","age":"25","sex":"男","phone":"19912121212","address":"江苏省常州市新北区","classificationList":["高血压","糖尿病"]}]}
//     */
//
//    private DataBean data;
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        /**
//         * page : {"totalPageCount":1,"totalCount":2,"pageSize":100,"currentPage":1}
//         * list : [{"id":"334334344","IDCard":"321321199207184033","name":"朱瑞","age":"25","sex":"男","phone":"19912121212","address":"江苏省常州市新北区","classificationList":["高血压","糖尿病","冠心病"]},{"id":"r2323r23","IDCard":"321321199207184033","name":"朱瑞","age":"25","sex":"男","phone":"19912121212","address":"江苏省常州市新北区","classificationList":["高血压","糖尿病"]}]
//         */
//
//        private PageBean page;
//        private List<ListBean> list;
//
//        public PageBean getPage() {
//            return page;
//        }
//
//        public void setPage(PageBean page) {
//            this.page = page;
//        }
//
//        public List<ListBean> getList() {
//            return list;
//        }
//
//        public void setList(List<ListBean> list) {
//            this.list = list;
//        }
//
//        public static class PageBean {
//            /**
//             * totalPageCount : 1
//             * totalCount : 2
//             * pageSize : 100
//             * currentPage : 1
//             */
//
//            private int totalPageCount;
//            private int totalCount;
//            private int pageSize;
//            private int currentPage;
//
//            public int getTotalPageCount() {
//                return totalPageCount;
//            }
//
//            public void setTotalPageCount(int totalPageCount) {
//                this.totalPageCount = totalPageCount;
//            }
//
//            public int getTotalCount() {
//                return totalCount;
//            }
//
//            public void setTotalCount(int totalCount) {
//                this.totalCount = totalCount;
//            }
//
//            public int getPageSize() {
//                return pageSize;
//            }
//
//            public void setPageSize(int pageSize) {
//                this.pageSize = pageSize;
//            }
//
//            public int getCurrentPage() {
//                return currentPage;
//            }
//
//            public void setCurrentPage(int currentPage) {
//                this.currentPage = currentPage;
//            }
//        }
//
//        public static class ListBean {
//            /**
//             * id : 334334344
//             * IDCard : 321321199207184033
//             * name : 朱瑞
//             * age : 25
//             * sex : 男
//             * phone : 19912121212
//             * address : 江苏省常州市新北区
//             * classificationList : ["高血压","糖尿病","冠心病"]
//             */
//
//            private String id;
//            private String IDCard;
//            private String name;
//            private String age;
//            private String sex;
//            private String phone;
//            private String address;
//            private List<String> classificationList;
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getIDCard() {
//                return IDCard;
//            }
//
//            public void setIDCard(String IDCard) {
//                this.IDCard = IDCard;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getAge() {
//                return age;
//            }
//
//            public void setAge(String age) {
//                this.age = age;
//            }
//
//            public String getSex() {
//                return sex;
//            }
//
//            public void setSex(String sex) {
//                this.sex = sex;
//            }
//
//            public String getPhone() {
//                return phone;
//            }
//
//            public void setPhone(String phone) {
//                this.phone = phone;
//            }
//
//            public String getAddress() {
//                return address;
//            }
//
//            public void setAddress(String address) {
//                this.address = address;
//            }
//
//            public List<String> getClassificationList() {
//                return classificationList;
//            }
//
//            public void setClassificationList(List<String> classificationList) {
//                this.classificationList = classificationList;
//            }
//        }
//    }
}
