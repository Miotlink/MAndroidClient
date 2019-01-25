package com.homepaas.sls.mvp.model.mapper;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.domain.entity.WorkerEntity;
import com.homepaas.sls.mvp.model.BusinessInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherily on 2016/9/29.
 */

public class SystemCertificationMapper {
    public static class SystemCertification {

        private String name;
        private String description;
        private String image;

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }
    }

    public SystemCertificationMapper() {
    }

    public List<SystemCertification> transform(BusinessCollectionEntity businessCollectionEntity){
        List<BusinessCollectionEntity.SystemCertification> systemCertificationList = businessCollectionEntity.getSystemCertifications();
        List<SystemCertification> systemCertifications = new ArrayList<>();
        if (systemCertificationList != null ){
           for (BusinessCollectionEntity.SystemCertification systemCertification : systemCertificationList){
               SystemCertification systemCertification1 = new SystemCertification();
               systemCertification1.setDescription(systemCertification.getDescription());
               systemCertification1.setImage(systemCertification.getImage());
               systemCertification1.setName(systemCertification.getName());
               systemCertifications.add(systemCertification1);
           }
        }
        return systemCertifications;
    }

    public List<SystemCertification> transform(WorkerCollectionEntity workerCollectionEntity){
        List<WorkerCollectionEntity.SystemCertification> systemCertificationList = workerCollectionEntity.getSystemCertification();
        List<SystemCertification> systemCertifications = new ArrayList<>();
        if (systemCertificationList != null ){
            for (WorkerCollectionEntity.SystemCertification systemCertification : systemCertificationList){
                SystemCertification systemCertification1 = new SystemCertification();
                systemCertification1.setDescription(systemCertification.getDescription());
                systemCertification1.setImage(systemCertification.getImage());
                systemCertification1.setName(systemCertification.getName());
                systemCertifications.add(systemCertification1);
            }
        }
        return systemCertifications;
    }

    public List<SystemCertification> transform(com.homepaas.sls.mvp.model.WorkerCollectionEntity workerCollectionEntity){
        List<com.homepaas.sls.mvp.model.WorkerCollectionEntity.SystemCertification> systemCertificationList = workerCollectionEntity.getSystemCertifications();
        List<SystemCertification> systemCertifications = new ArrayList<>();
        if (systemCertificationList != null ){
            for (com.homepaas.sls.mvp.model.WorkerCollectionEntity.SystemCertification systemCertification : systemCertificationList){
                SystemCertification systemCertification1 = new SystemCertification();
                systemCertification1.setDescription(systemCertification.getDescription());
                systemCertification1.setImage(systemCertification.getImage());
                systemCertification1.setName(systemCertification.getName());
                systemCertifications.add(systemCertification1);
            }
        }
        return systemCertifications;
    }


    public List<SystemCertification> transform(BusinessInfoModel businessInfoModel){
        List<BusinessInfoModel.SystemCertification> systemCertificationList = businessInfoModel.getSystemCertifications();
        List<SystemCertification> systemCertifications = new ArrayList<>();
        if (systemCertificationList != null ){
            for (BusinessInfoModel.SystemCertification systemCertification : systemCertificationList){
                SystemCertification systemCertification1 = new SystemCertification();
                systemCertification1.setDescription(systemCertification.getDescription());
                systemCertification1.setImage(systemCertification.getImage());
                systemCertification1.setName(systemCertification.getName());
                systemCertifications.add(systemCertification1);
            }
        }
        return systemCertifications;
    }

}
