package com.homepaas.sls.jsinterface.entity;

import com.google.gson.annotations.SerializedName;


/**
 * Created by JWC on 2017/3/13.
 */

public class RedCoupsEntity {
    @SerializedName("Meta")
    private  Meta meta;
    @SerializedName("Body")
    private  Body body;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Meta{
        @SerializedName("ErrorCode")
        String errorCode;
        @SerializedName("ErrorMsg")
        String ErrorMsg;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return ErrorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            ErrorMsg = errorMsg;
        }
    }
    public static class  Body{
        @SerializedName("Title")
        String title;
        @SerializedName("Url")
        String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
