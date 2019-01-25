package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
public class Message {

    /**
     * Id : 1
     * Title : 标题
     * Content : 消息
     * Date : 2016-01-07 17:07:08
     * JumpCode : 3
     */

    @SerializedName("Id")
    private String id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Content")
    private String content;

    @SerializedName("Date")
    private String date;

    @SerializedName("JumpCode")
    private String jumpCode;

//    @SerializedName("JumpParam")
//    private String jumpParam;

    @SerializedName("MessageType")
    private String type;

    @SerializedName("IsRead")
    private boolean read;

    @SerializedName("JumpParam")
    PushInfo webViewParam;
/*
    @SerializedName("AppParam")
    AppParam appParam;*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setJumpCode(String jumpCode) {
        this.jumpCode = jumpCode;
    }



    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getJumpCode() {
        return jumpCode;
    }

    public PushInfo getWebViewParam() {
        return webViewParam;
    }

    public void setWebViewParam(PushInfo webViewParam) {
        this.webViewParam = webViewParam;
    }
}
