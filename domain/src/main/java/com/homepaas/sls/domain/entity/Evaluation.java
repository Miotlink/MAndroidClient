package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * comment message
 *
 * @author zhudongjie
 */
public class Evaluation {

    @SerializedName("ClientName")
    private String clientName;

    @SerializedName("ClientPic")
    private String clientPhoto;

    @SerializedName("ClientPhone")
    private String clientPhone;

    @SerializedName("Score")
    private String score;

    @SerializedName("Date")
    private String date;

    @SerializedName("Content")
    private String content;

    @SerializedName("IsCanBeAddtion")
    private String canBeAdded;

    @SerializedName("Reply")
    private Reply reply;

    @SerializedName("AdditionalEvaluation")
    private AdditionalEvaluation additionalEva;

    @SerializedName("Pictures")
    private List<Picture> pictures;

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhoto() {
        return clientPhoto;
    }

    public void setClientPhoto(String clientPhoto) {
        this.clientPhoto = clientPhoto;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCanBeAdded() {
        return canBeAdded;
    }

    public boolean canBeAdded(){
        return "1".equals(canBeAdded);
    }

    public void setCanBeAdded(String canBeAdded) {
        this.canBeAdded = canBeAdded;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public AdditionalEvaluation getAdditionalEva() {
        return additionalEva;
    }

    public void setAdditionalEva(AdditionalEvaluation additionalEva) {
        this.additionalEva = additionalEva;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public static class Reply{

        @SerializedName("Content")
        private String content;

        @SerializedName("Date")
        private String date;

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
    }
    public static class AdditionalEvaluation{

        @SerializedName("Content")
        private String content;

        @SerializedName("Date")
        private String date;

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
    }

    public static class Picture{

        @SerializedName("PictureId")
        private String pictureId;

        @SerializedName("SmallPic")
        private String smallPic;

        @SerializedName("HqPic")
        private String hqPic;

        public String getPictureId() {
            return pictureId;
        }

        public void setPictureId(String pictureId) {
            this.pictureId = pictureId;
        }

        public String getSmallPic() {
            return smallPic;
        }

        public void setSmallPic(String smallPic) {
            this.smallPic = smallPic;
        }

        public String getHqPic() {
            return hqPic;
        }

        public void setHqPic(String hqPic) {
            this.hqPic = hqPic;
        }
    }

}
