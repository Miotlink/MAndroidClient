package com.homepaas.sls.domain.entity.embedded_class;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/9/14.
 */

public class Note {

    @SerializedName("CreateTime")
    public String createTime;
    @SerializedName("Content")
    String content;
    @SerializedName("CreateUserName")
    String noteAuthor;

    public Note() {
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNoteAuthor() {
        return noteAuthor;
    }

    public void setNoteAuthor(String noteAuthor) {
        this.noteAuthor = noteAuthor;
    }
}
