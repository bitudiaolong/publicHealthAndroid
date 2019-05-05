package com.gc.basicpublicdoctor.bean;

import com.gagc.httplibrary.BaseResponse;

import java.util.List;

/**
 * Author:Created by zhurui
 * Time:2018/7/25 下午1:40
 * Description:This is NewsClassesListResponse
 * 新闻分类列表
 */
public class NewsClassesListResponse extends BaseResponse {


    /**
     * data : {"list":[{"id":"1","name":"时政要闻"},{"id":"2","name":"政务公开"},{"id":"3","name":"基层动态"},{"id":"4","name":"公示公告"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * name : 时政要闻
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
