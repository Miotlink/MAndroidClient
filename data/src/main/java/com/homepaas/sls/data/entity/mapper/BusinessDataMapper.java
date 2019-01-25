package com.homepaas.sls.data.entity.mapper;

import com.homepaas.sls.data.BuildConfig;
import com.homepaas.sls.data.entity.BusinessEntity;
import com.homepaas.sls.data.entity.BusinessEntity.Photo;
import com.homepaas.sls.data.entity.BusinessEntity.SystemCertification;
import com.homepaas.sls.data.entity.WorkerEntity;
import com.homepaas.sls.data.network.dataformat.BusinessTagsBody;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.ServiceType;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/13 0013
 *
 * @author zhudongjie .
 */
@Singleton
public class BusinessDataMapper {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Inject
    public BusinessDataMapper() {
    }


    public BusinessEntity transform(BusinessInfo businessInfo) {
        return transform(businessInfo, 0);
    }

    private List<Photo> transform1(List<BusinessInfo.Photo> photos){
        List<Photo> photoList = new ArrayList<>();
        if (photos != null && !photos.isEmpty()){
            for (BusinessInfo.Photo photo : photos){
                Photo photo1 = new Photo();
                photo1.setHqPic(photo.getHqPic());
                photo1.setSmallPic(photo.getSmallPic());
                photoList.add(photo1);
            }
        }
        return photoList;
    }
    private List<BusinessInfo.Photo> transform2(List<Photo> photos){
        List<BusinessInfo.Photo> photoList = new ArrayList<>();
        if (photos != null && !photos.isEmpty()){
            for (Photo photo : photos){
                BusinessInfo.Photo photo1 = new BusinessInfo.Photo();
                photo1.setHqPic(photo.getHqPic());
                photo1.setSmallPic(photo.getSmallPic());
                photoList.add(photo1);
            }
        }
        return photoList;
    }

    public BusinessEntity transform(BusinessInfo businessInfo, long persistedTime) {
        BusinessEntity businessEntity = null;
        if (businessInfo != null) {
            businessEntity = new BusinessEntity();
            businessEntity.setPhoto(businessInfo.getPhoto());
            businessEntity.setPhotoCount(businessInfo.getPhotoCount());
            businessEntity.setAddress(businessInfo.getAddress());
            businessEntity.setArea(businessInfo.getArea());
            businessEntity.setDistance(businessInfo.getDistance());
            ServiceType serviceType = new ServiceType();
            serviceType.setId(businessInfo.getDefaultServiceId());
            serviceType.setName(businessInfo.getDefaultService());
            businessEntity.setDefaultService(serviceType);
            businessEntity.setEstablishedTime(businessInfo.getEstablishedTime());
            businessEntity.setFavoriteCount(businessInfo.getFavoriteCount());
            businessEntity.setGradeRank(businessInfo.getGradeRank());
            businessEntity.setGrade(businessInfo.getGrade());
            businessEntity.setIsPraise(businessInfo.getIsPraise());
            businessEntity.setAllowAcceptOrder(businessInfo.getAllowAcceptOrder());
            businessEntity.setIsFavorite(businessInfo.getIsFavorite());
            businessEntity.setIntro(businessInfo.getIntro());
            businessEntity.setId(businessInfo.getId());
            businessEntity.setLicenseId(businessInfo.getLicenseId());
            businessEntity.setMainBusiness(businessInfo.getMainBusiness());
            businessEntity.setName(businessInfo.getName());
            businessEntity.setNumber(businessInfo.getNumber());
            businessEntity.setOrderRank(businessInfo.getOrderRank());
            businessEntity.setOrderCount(businessInfo.getOrderCount());
            businessEntity.setPraiseCount(businessInfo.getPraiseCount());
            businessEntity.setPhoneNumber(businessInfo.getPhoneNumber());
            businessEntity.setPhoneCount(businessInfo.getPhoneCount());
            businessEntity.setPhoneRank(businessInfo.getPhoneRank());
            businessEntity.setProperty(businessInfo.getProperty());
            businessEntity.setSignature(businessInfo.getSignature());
            businessEntity.setServiceTime(businessInfo.getServiceTime());
            businessEntity.setServiceScope(businessInfo.getServiceScope());
            businessEntity.setScale(businessInfo.getScale());
            businessEntity.setStaffNumber(businessInfo.getStaffNumber());
            businessEntity.setIsStorePay(businessInfo.getIsStorePay());
            businessEntity.setStorePayStr(businessInfo.getStorePayStr());
            businessEntity.setActionEntity(businessInfo.getActionEntity());
            List<SystemCertification> certifications = new ArrayList<>();
            if (businessInfo.getSystemCertifications() != null) {
                for (BusinessInfo.SystemCertification certification : businessInfo.getSystemCertifications()) {
                    SystemCertification certification1 = new SystemCertification();
                    certification1.setDescription(certification.getDescription());
                    certification1.setName(certification.getName());
                    certification1.setImage(certification.getImage());
                    certifications.add(certification1);
                }
            }
            businessEntity.setSystemCertifications(certifications);
            businessEntity.setTexId(businessInfo.getTexId());
            businessEntity.setLatitude(businessInfo.getLatitude());
            businessEntity.setLongitude(businessInfo.getLongitude());
            businessEntity.setPersistedTime(persistedTime);
            businessEntity.setIsCall(businessInfo.getIsCall());
            businessEntity.setBusinessTagsBody(transform(businessInfo.getGetBusinessTagsInfo()));
            businessEntity.setActivityNgInfos(businessInfo.getActivityNgInfos());
            businessEntity.setLicencePhotos(transform1(businessInfo.getLicencePhotos()));

        }

        return businessEntity;
    }

    public List<BusinessInfo> transform(List<BusinessEntity> entities) {
        List<BusinessInfo> infoList = new ArrayList<>();
        if (entities != null) {
            for (int i = 0; i < entities.size(); i++) {
                infoList.add(transform(entities.get(i)));
            }
        }
        return infoList;
    }
    private GetBusinessTagsInfo transform(BusinessTagsBody businessTagsBody){
        GetBusinessTagsInfo getBusinessTagsInfo = new GetBusinessTagsInfo();
        getBusinessTagsInfo.setCount(businessTagsBody.getCount());
        getBusinessTagsInfo.setBusinessTagsInfos(businessTagsBody.getBusinessTagsInfos());
        return getBusinessTagsInfo;
    }
    private BusinessTagsBody transform(GetBusinessTagsInfo getBusinessTagsInfo){
        BusinessTagsBody businessTagsBody = new BusinessTagsBody();
        businessTagsBody.setCount(getBusinessTagsInfo.getCount());
        businessTagsBody.setBusinessTagsInfos(getBusinessTagsInfo.getBusinessTagsInfos());
        return businessTagsBody;
    }
    public BusinessInfo transform(BusinessEntity entity) {
        BusinessInfo info = null;
        if (entity != null) {
            info = new BusinessInfo();
            info.setId(entity.getId());
            info.setPhoto(entity.getPhoto());
            info.setPhotoCount(entity.getPhotoCount());
            info.setPhoneNumber(entity.getPhoneNumber());
            info.setIsCall(entity.getIsCall());
            info.setAllowAcceptOrder(entity.getAllowAcceptOrder());
            info.setName(entity.getName());
            info.setDistance(entity.getDistance());
            info.setProperty(entity.getProperty());
            info.setEstablishedTime(entity.getEstablishedTime());
            info.setMainBusiness(entity.getMainBusiness());
            info.setArea(entity.getArea());
            info.setScale(entity.getScale());
            info.setStaffNumber(entity.getStaffNumber());
            info.setTexId(entity.getTexId());
            info.setLicenseId(entity.getLicenseId());
            info.setAddress(entity.getAddress());
            info.setServiceTime(entity.getServiceTime());
            info.setServiceScope(entity.getServiceScope());
            info.setIntro(entity.getIntro());
            info.setIsPraise(entity.getIsPraise());
            info.setIsFavorite(entity.getIsFavorite());
            info.setFavoriteCount(entity.getFavoriteCount());
            info.setPraiseCount(entity.getPraiseCount());
            info.setGradeRank(entity.getGradeRank());
            info.setGrade(entity.getGrade());
            info.setOrderRank(entity.getOrderRank());
            info.setOrderCount(entity.getOrderCount());
            info.setPhoneCount(entity.getPhoneCount());
            info.setPhoneRank(entity.getPhoneRank());
            info.setActionEntity(entity.getActionEntity());
            info.setIsStorePay(entity.getIsStorePay());
            info.setStorePayStr(entity.getStorePayStr());
            List<BusinessInfo.SystemCertification> certifications = new ArrayList<>();
            if (entity.getSystemCertifications() != null) {
                for (SystemCertification certification : entity.getSystemCertifications()) {
                    BusinessInfo.SystemCertification certification1 = new BusinessInfo.SystemCertification();
                    certification1.setDescription(certification.getDescription());
                    certification1.setName(certification.getName());
                    certification1.setImage(certification.getImage());
                    certifications.add(certification1);
                }
            }
            info.setSystemCertifications(certifications);
            info.setLatitude(entity.getLatitude());
            info.setLongitude(entity.getLongitude());
            info.setNumber(entity.getNumber());
            info.setSignature(entity.getSignature());
            if (entity.getDefaultService() != null) {
                info.setDefaultService(entity.getDefaultService().getName());
                info.setDefaultServiceId(entity.getDefaultService().getId());
            }
            List<String> services = new ArrayList<>();
           if (entity.getServices() != null){
               for (ServiceType serviceType : entity.getServices()){
                   services.add(serviceType.getName());
               }
           }
            info.setServices(services);
            info.setActivityNgInfos(entity.getActivityNgInfos());
            info.setGetBusinessTagsInfo(transform(entity.getBusinessTagsBody()));
            info.setLicencePhotos(transform2(entity.getLicencePhotos()));
            info.setPhoneCount2(entity.getPhoneCount2());
            info.setGrade2(entity.getGrade2());
            info.setOrderCount2(entity.getOrderCount2());

        }


        return info;
    }
}
