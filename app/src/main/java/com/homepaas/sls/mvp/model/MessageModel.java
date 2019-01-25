package com.homepaas.sls.mvp.model;

import android.support.annotation.DrawableRes;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
public class MessageModel {

    private final String id;

    private String title;

    private String content;

    private String date;

    private String jumpCode;

    private String jumpParam;

    private boolean read;

    private @DrawableRes int iconResource;

    public MessageModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJumpParam() {
        return jumpParam;
    }

    public void setJumpParam(String jumpParam) {
        this.jumpParam = jumpParam;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJumpCode() {
        return jumpCode;
    }

    public void setJumpCode(String jumpCode) {
        this.jumpCode = jumpCode;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(@DrawableRes int iconResource) {
        this.iconResource = iconResource;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageModel{");
        sb.append("read=").append(read);
        sb.append(", id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", jumpCode='").append(jumpCode).append('\'');
        sb.append(", jumpParam='").append(jumpParam).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
