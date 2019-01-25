package com.homepaas.sls.data.entity;

import java.util.List;

/**
 * Created by mhy on 2018/1/2.
 */

public class BusinessOrderServiceListEntity {
    /**
     * Count :  总数 ,
     * List : [{"Id":"string","ServiceName":"string","IconUrl":"string","PriceType":"string","Price":"string","Unit":"string","Grade":"string","ProviderUserId":"string","ProviderUserType":"string","ProviderName":"string","ProviderPhone":"string","Distance":"string"}]
     * Tags: 用户标签筛选的tags 集合
     */

    private int Count;
    private java.util.List<ListBean> List;
    private java.util.List<ServiceTag> Tags;

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public java.util.List<ServiceTag> getTags() {
        return Tags;
    }

    public void setTags(java.util.List<ServiceTag> tags) {
        Tags = tags;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ServiceTag {
        public ServiceTag(String id, String name, boolean isSelect) {
            Id = id;
            Name = name;
            this.isSelect = isSelect;
        }

        /**
         * Id (string, optional): 标签Id ,
         * Name (string, optional): 标签名字
         */

        private String Id;
        private String Name;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }

    public static class ListBean {
        /**
         * Id (string, optional): 三级服务的Id,直接可以下单的 ,
         * ServiceName (string, optional): 三级服务的名字 ,
         * ServiceTagId (string, optional): 标签Id ,
         * ServiceTagName (string, optional): 标签名字 ,
         * IconUrl (string, optional): 图标 ,
         * PriceType (string, optional): 0：面议，1：固定价格 ,
         * Price (string, optional): 单价 ,
         * Unit (string, optional): 单位 ,
         * Grade (string, optional): 评分 ,
         * ProviderUserId (string, optional): 商户或者工人的Id ,  下单使用
         * ProviderUserType (string, optional): 商户或者工人的类型 2：工人，3：商户 ,
         * ProviderName (string, optional): 商户或者工人的名字 ,
         * ProviderPhone (string, optional): 商户或者工人的电话 ,
         * Distance (string, optional): 商户或者工人的距离
         */

        private String Id;
        private String ServiceName;
        private String IconUrl;
        private String PriceType;
        private String Price;
        private String Unit;
        private float Grade;
        private String ProviderUserId;
        private String ProviderId;
        private String ProviderUserType;
        private String ProviderName;
        private String ProviderPhone;
        private String Distance;
        private String ServiceTagId;//(string, optional): 标签Id ,
        private String ServiceTagName;//(string, optional): 标签名字 ,

        public String getServiceTagId() {
            return ServiceTagId;
        }

        public void setServiceTagId(String serviceTagId) {
            ServiceTagId = serviceTagId;
        }

        public String getServiceTagName() {
            return ServiceTagName;
        }

        public void setServiceTagName(String serviceTagName) {
            ServiceTagName = serviceTagName;
        }

        public String getProviderId() {
            return ProviderId;
        }

        public void setProviderId(String providerId) {
            ProviderId = providerId;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getServiceName() {
            return ServiceName;
        }

        public void setServiceName(String ServiceName) {
            this.ServiceName = ServiceName;
        }

        public String getIconUrl() {
            return IconUrl;
        }

        public void setIconUrl(String IconUrl) {
            this.IconUrl = IconUrl;
        }

        public String getPriceType() {
            return PriceType;
        }

        public void setPriceType(String PriceType) {
            this.PriceType = PriceType;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public float getGrade() {
            return Grade;
        }

        public void setGrade(float Grade) {
            this.Grade = Grade;
        }

        public String getProviderUserId() {
            return ProviderUserId;
        }

        public void setProviderUserId(String ProviderUserId) {
            this.ProviderUserId = ProviderUserId;
        }

        public String getProviderUserType() {
            return ProviderUserType;
        }

        public void setProviderUserType(String ProviderUserType) {
            this.ProviderUserType = ProviderUserType;
        }

        public String getProviderName() {
            return ProviderName;
        }

        public void setProviderName(String ProviderName) {
            this.ProviderName = ProviderName;
        }

        public String getProviderPhone() {
            return ProviderPhone;
        }

        public void setProviderPhone(String ProviderPhone) {
            this.ProviderPhone = ProviderPhone;
        }

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String Distance) {
            this.Distance = Distance;
        }
    }
}
