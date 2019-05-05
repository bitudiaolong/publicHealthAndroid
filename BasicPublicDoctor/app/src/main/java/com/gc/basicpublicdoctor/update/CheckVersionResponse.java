package com.gc.basicpublicdoctor.update;

import com.gagc.httplibrary.BaseResponse;

/**
 * Author:Created by zhurui
 * Time:2018/8/7 下午2:15
 * Description:This is CheckVersionResponse
 * 自升级实体类
 */
public class CheckVersionResponse extends BaseResponse {

    /**
     * data : {"update":1,"desc":"123","name":"1.0.0","md5":"fd9a69300534b7872cce50eb7bd8a898","code":"10","url":"http://package.gagctv.com/version/file/2017/11/24/16/20171124163203895218.apk"}
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
         * update : 1
         * desc : 123
         * name : 1.0.0
         * md5 : fd9a69300534b7872cce50eb7bd8a898
         * code : 10
         * url : http://package.gagctv.com/version/file/2017/11/24/16/20171124163203895218.apk
         */

        private int update;
        private String desc;
        private String name;
        private String md5;
        private String code;
        private String url;

        public int getUpdate() {
            return update;
        }

        public void setUpdate(int update) {
            this.update = update;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
