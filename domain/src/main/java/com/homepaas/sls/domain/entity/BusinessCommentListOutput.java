package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * Created by mhy on 2017/12/28.
 */

public class BusinessCommentListOutput {

    /**
     * Count : : 评论总数 ,
     * List : [{"Id":"string","Star":"string","Date":"string","Content":"string","Tag":"string","City":"string","ServiceName":"string"}]
     */

    private int Count;
    private java.util.List<ListBean> List;

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {

        /**
         Id (string, optional): Id ,
         Star (string, optional): 星级 ,
         Date (string, optional): 评论日期 ,
         Content (string, optional): 内容，如：服务不错，很干净 ,
         Tag (string, optional): 标签，如：非常满意,一般, 多个标签以,分开 ,
         City (string, optional): 城市 ,
         Phone (string, optional): 手机号 ,
         AvatarUrl (string, optional): 头像Url ,
         ServiceName (string, optional): 二级服务名字
         */

        private String Id;
        private float Star;
        private String Date;
        private String Content;
        private String Tag;
        private String City;
        private String Phone;
        private String AvatarUrl;
        private String ServiceName;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public float getStar() {
            return Star;
        }

        public void setStar(float Star) {
            this.Star = Star;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getTag() {
            return Tag;
        }

        public void setTag(String Tag) {
            this.Tag = Tag;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getAvatarUrl() {
            return AvatarUrl;
        }

        public void setAvatarUrl(String AvatarUrl) {
            this.AvatarUrl = AvatarUrl;
        }

        public String getServiceName() {
            return ServiceName;
        }

        public void setServiceName(String ServiceName) {
            this.ServiceName = ServiceName;
        }
    }
}
