package com.homepaas.sls.jsinterface.entity;

/**
 * Created by Administrator on 2016/7/11.
 */
public class SuccessCommand {
    public Data data;
    public Status status;

    public Data getData() {
        return data;
    }

    public Status getStatus() {
        return status;
    }

    public static class Data{
        public String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    public static class Status {
        public String code;
        public String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
