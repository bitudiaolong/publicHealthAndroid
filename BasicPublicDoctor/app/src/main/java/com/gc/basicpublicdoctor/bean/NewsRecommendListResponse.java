package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/30 上午9:58
 * Description:This is NewsRecommendListResponse
 * 咨询列表
 */
public class NewsRecommendListResponse extends BaseResponse {

    /**
     * errcod : 200
     * data : {"page":{"totalCount":111,"totalPageCount":222,"currentPage":333,"pageSize":4444},"list":[{"id":"1","title":"国务院新规定","simpleInfo":"简介你懂得","imageList":["http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png","http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png"],"detailUrl":"http://209384209.com/2093o84jlkf"},{"id":"2","title":"国务院新规定","simpleInfo":"简介你懂得","imageList":["http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png","http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png"],"detailUrl":"http://209384209.com/2093o84jlkf"}]}
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
         * page : {"totalCount":111,"totalPageCount":222,"currentPage":333,"pageSize":4444}
         * list : [{"id":"1","title":"国务院新规定","simpleInfo":"简介你懂得","imageList":["http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png","http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png"],"detailUrl":"http://209384209.com/2093o84jlkf"},{"id":"2","title":"国务院新规定","simpleInfo":"简介你懂得","imageList":["http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png","http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png"],"detailUrl":"http://209384209.com/2093o84jlkf"}]
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
             * totalCount : 111
             * totalPageCount : 222
             * currentPage : 333
             * pageSize : 4444
             */

            private int totalCount;
            private int totalPageCount;
            private int currentPage;
            private int pageSize;

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getTotalPageCount() {
                return totalPageCount;
            }

            public void setTotalPageCount(int totalPageCount) {
                this.totalPageCount = totalPageCount;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }
        }

        public static class ListBean {
            /**
             * id : 1
             * title : 国务院新规定
             * simpleInfo : 简介你懂得
             * imageList : ["http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png","http://223.68.161.54:8085/bbs/resources/images/weather/day/00.png"]
             * detailUrl : http://209384209.com/2093o84jlkf
             */

            private String id;
            private String title;
            private String simpleInfo;
            private String detailUrl;
            private List<String> imageList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSimpleInfo() {
                return simpleInfo;
            }

            public void setSimpleInfo(String simpleInfo) {
                this.simpleInfo = simpleInfo;
            }

            public String getDetailUrl() {
                return detailUrl;
            }

            public void setDetailUrl(String detailUrl) {
                this.detailUrl = detailUrl;
            }

            public List<String> getImageList() {
                return imageList;
            }

            public void setImageList(List<String> imageList) {
                this.imageList = imageList;
            }
        }
    }
}


