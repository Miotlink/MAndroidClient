package com.homepaas.sls.data.entity;

import java.util.List;

/**
 * Created by mhy on 2017/12/28.
 */

public class BusinessServiceListEntity {

    private java.util.List<ListBean> List;

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * Id (string, optional): 服务Id ,
         * Name (string, optional): 服务名字 ,
         * Price (string, optional): 价格 ,
         * Unit (string, optional): 单位 ,
         * PriceType (string, optional): 0：面议，1：固定价格 ,
         * IconUrl (string, optional): 服务图标
         *
         ParentId (string, optional): 父级Id ,
         ParentName (string, optional): 父级名字
         */

        private String Id;
        private String Name;
        private int Price;
        private String Unit;
        private String PriceType;
        private String IconUrl;
        private String ParentId;
        private String ParentName;

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String parentId) {
            ParentId = parentId;
        }

        public String getParentName() {
            return ParentName;
        }

        public void setParentName(String parentName) {
            ParentName = parentName;
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

        public int getPrice() {
            return Price;
        }

        public void setPrice(int Price) {
            this.Price = Price;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getPriceType() {
            return PriceType;
        }

        public void setPriceType(String PriceType) {
            this.PriceType = PriceType;
        }

        public String getIconUrl() {
            return IconUrl;
        }

        public void setIconUrl(String IconUrl) {
            this.IconUrl = IconUrl;
        }
    }
}
