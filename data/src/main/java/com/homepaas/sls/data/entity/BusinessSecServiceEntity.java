package com.homepaas.sls.data.entity;

import java.util.List;

/**
 * Created by mhy on 2017/12/28.
 */

public class BusinessSecServiceEntity {
    private List<SecServicesBean> SecServices;

    public List<SecServicesBean> getSecServices() {
        return SecServices;
    }

    public void setSecServices(List<SecServicesBean> SecServices) {
        this.SecServices = SecServices;
    }

    public static class SecServicesBean {
        /**
         * 商家或工人的二级服务分类集合
         * Id (string, optional): Id ,
         * Name (string, optional): 名字
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
}
