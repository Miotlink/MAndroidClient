package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * Created by mhy on 2017/9/14.
 */

public class SelectServiceOrWorkerEntity {
    private List<ChooseWorkerOrMerchantInfosBean> ChooseWorkerOrMerchantInfos;

    public List<ChooseWorkerOrMerchantInfosBean> getChooseWorkerOrMerchantInfos() {
        return ChooseWorkerOrMerchantInfos;
    }

    public void setChooseWorkerOrMerchantInfos(List<ChooseWorkerOrMerchantInfosBean> ChooseWorkerOrMerchantInfos) {
        this.ChooseWorkerOrMerchantInfos = ChooseWorkerOrMerchantInfos;
    }

    public static class ChooseWorkerOrMerchantInfosBean {
        /**
         * UserId (string, optional): 工人或者商户的UserId 选择某个工人或者商户后，使用UserId参数传递,
         * <p>
         * Name (string, optional): 名字,
         * <p>
         * Grade (string, optional): 评分，按最近20单平均算，精确到小数点后一位，小数点第二位四舍五入 默认初始值为5分,
         * <p>
         * Distance (string, optional): 距离，如100米,
         * <p>
         * Photo (string, optional): 工人头像或者商户门面照，可空,
         * <p>
         * IsWorker (string, optional): 是否是工人，0：不是，1：是,
         * <p>
         * Age (string, optional): 工作年限 IsWorker = 1， 则 Age 有值，如：26岁
         * UserId : string
         * Name : string
         * Grade : string
         * Distance : string
         * Photo : string
         * IsWorker : string
         * Age : string
         */

        private String UserId;
        private String Name;
        private String Grade;
        private String Distance;
        private String Photo;
        private String IsWorker;
        private String Age;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getGrade() {
            return Grade;
        }

        public void setGrade(String Grade) {
            this.Grade = Grade;
        }

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String Distance) {
            this.Distance = Distance;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }

        public String getIsWorker() {
            return IsWorker;
        }

        public void setIsWorker(String IsWorker) {
            this.IsWorker = IsWorker;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String Age) {
            this.Age = Age;
        }
    }
}
