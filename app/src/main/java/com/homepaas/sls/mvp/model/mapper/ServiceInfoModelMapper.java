package com.homepaas.sls.mvp.model.mapper;

import android.support.annotation.NonNull;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.data.network.dataformat.BusinessTagsBody;
import com.homepaas.sls.domain.entity.AppViewPage;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.ServiceInfo;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.IServiceInfo.SystemCertification;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.homepaas.sls.domain.param.Constant.intStringToBoolean;

/**
 * on 2016/1/14 0014
 *
 * @author zhudongjie .
 */
@Singleton
public class ServiceInfoModelMapper {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Inject
    public ServiceInfoModelMapper() {
    }

    public List<IServiceInfo> transformServiceInfoList(ServiceInfo serviceInfo) {
        return transformServiceInfoList(serviceInfo.getWorkers(), serviceInfo.getBusinesses(), serviceInfo.getOtherWorkers(), serviceInfo.getOtherBusinesses());
    }

    public List<IServiceInfo> transformServiceInfoList(List<WorkerInfo> workerInfoList, List<BusinessInfo> businessInfoList,
                                                       List<WorkerInfo> otherWorkers, List<BusinessInfo> otherBusinesses) {
        List<IServiceInfo> infoList = new ArrayList<>();
        if (workerInfoList != null) {
            for (WorkerInfo workInfo : workerInfoList) {
                WorkerCollectionEntity worker = transformWorker(workInfo);
                worker.setShowPhoto(true);
                infoList.add(worker);
            }
        }
        if (businessInfoList != null) {
            for (BusinessInfo businessInfo : businessInfoList) {
                BusinessInfoModel business = transformBusiness(businessInfo);
                business.setShowPhoto(true);
                infoList.add(business);
            }
        }
        if (workerInfoList != null) {
            for (WorkerInfo workInfo : otherWorkers) {
                WorkerCollectionEntity worker = transformWorker(workInfo);
                worker.setShowPhoto(false);
                infoList.add(worker);
            }
        }
        if (businessInfoList != null) {
            for (BusinessInfo businessInfo : otherBusinesses) {
                BusinessInfoModel business = transformBusiness(businessInfo);
                business.setShowPhoto(false);
                infoList.add(business);
            }
        }
        return infoList;
    }
    private List<IServiceInfo.Photo> transform(List<BusinessInfo.Photo> photos){
        List<IServiceInfo.Photo> photoList = new ArrayList<>();
        if (photos != null && !photos.isEmpty()){
            for (BusinessInfo.Photo photo : photos){
                IServiceInfo.Photo photo1 = new IServiceInfo.Photo();
                photo1.setSmallPic(photo.getSmallPic());
                photo1.setHqPic(photo.getHqPic());
                photoList.add(photo1);
            }
        }
        return photoList;
    }

    public BusinessInfoModel transformBusiness(@NonNull BusinessInfo businessInfo) {
        BusinessInfoModel infoModel = new BusinessInfoModel(businessInfo.getId());
        infoModel.setPhoto(businessInfo.getPhoto());
        infoModel.setPhotoCount(businessInfo.getPhotoCount());
        infoModel.setPhoneNumber(businessInfo.getPhoneNumber());
        infoModel.setCallable(intStringToBoolean(businessInfo.getIsCall()));
        infoModel.setPlaceOrderEnable(intStringToBoolean(businessInfo.getAllowAcceptOrder()));
        infoModel.setName(businessInfo.getName());
        infoModel.setDistance(businessInfo.getDistance());
        infoModel.setProperty(businessInfo.getProperty());
        infoModel.setEstablishedTime(businessInfo.getEstablishedTime());
        infoModel.setMainBusiness(businessInfo.getMainBusiness());
        infoModel.setArea(businessInfo.getArea());
        infoModel.setScale(businessInfo.getScale());
        infoModel.setStaffNumber(businessInfo.getStaffNumber());
        infoModel.setTexId(businessInfo.getTexId());
        infoModel.setLicenseId(businessInfo.getLicenseId());
        infoModel.setAddress(businessInfo.getAddress());
        infoModel.setServiceTime(businessInfo.getServiceTime());
        infoModel.setServiceScope(businessInfo.getServiceScope());
        infoModel.setIntro(businessInfo.getIntro());
        infoModel.setCollected(intStringToBoolean(businessInfo.getIsFavorite()));
        infoModel.setCollectedCount(Integer.parseInt(businessInfo.getFavoriteCount()));
        infoModel.setLike(intStringToBoolean(businessInfo.getIsPraise()));
        infoModel.setLikeCount(Integer.parseInt(businessInfo.getPraiseCount()));
        infoModel.setGrade(businessInfo.getGrade());
        infoModel.setGradeRank(businessInfo.getGradeRank());
        infoModel.setGradeNumber(businessInfo.getGrade());
        infoModel.setOrderRank(businessInfo.getOrderRank());
        infoModel.setOrderCount(businessInfo.getOrderCount());
        infoModel.setPhoneCount(businessInfo.getPhoneCount());
        infoModel.setPhoneRank(businessInfo.getPhoneRank());
        infoModel.setActionEntity(businessInfo.getActionEntity());
        infoModel.setIsStorePay(businessInfo.getIsStorePay());
        infoModel.setStorePayStr(businessInfo.getStorePayStr());
        if (businessInfo.getSystemCertifications() != null) {
            List<SystemCertification> systemCertifications = new ArrayList<>(businessInfo.getSystemCertifications().size());
            for (BusinessInfo.SystemCertification certification : businessInfo.getSystemCertifications()) {
                SystemCertification certification1 = new SystemCertification();
                certification1.setImage(certification.getImage());
                certification1.setDescription(certification.getDescription());
                certification1.setName(certification.getName());
                systemCertifications.add(certification1);
            }
            infoModel.setSystemCertifications(systemCertifications);
        }
        infoModel.setLatitude(Double.parseDouble(businessInfo.getLatitude()));
        infoModel.setLongitude(Double.parseDouble(businessInfo.getLongitude()));
        infoModel.setSignature(businessInfo.getSignature());
        infoModel.setNumber(businessInfo.getNumber());
        infoModel.setDefaultServiceId(businessInfo.getDefaultServiceId());
        infoModel.setDefaultService(businessInfo.getDefaultService());
        infoModel.setServices(businessInfo.getServices());
        infoModel.setActivityNgInfos(businessInfo.getActivityNgInfos());
        infoModel.setGetBusinessTagsInfo(businessInfo.getGetBusinessTagsInfo());
        infoModel.setLicencePhotos(transform(businessInfo.getLicencePhotos()));
        infoModel.setPhoneCount2(businessInfo.getPhoneCount2());
        infoModel.setOrderCount2(businessInfo.getOrderCount2());
        infoModel.setGrade2(businessInfo.getGrade2());

        return infoModel;
    }


    public WorkerCollectionEntity transformWorker(@NonNull WorkerInfo workerInfo) {
        WorkerCollectionEntity infoModel = new WorkerCollectionEntity(workerInfo.getId());
        infoModel.setPhoto(workerInfo.getPhoto());
        infoModel.setPhotoCount(workerInfo.getPhotoCount());
        infoModel.setPhoneNumber(workerInfo.getPhoneNumber());
        infoModel.setPlaceOrderEnable(intStringToBoolean(workerInfo.getAllowAcceptOrder()));
        infoModel.setName(workerInfo.getName());
        infoModel.setGender("0".equals(workerInfo.getGender()) ? "♂" : "♀");
        infoModel.setAge(workerInfo.getAge());
        infoModel.setJobNumber(workerInfo.getJobNumber());
        infoModel.setWage(workerInfo.getWage());
        infoModel.setDistance(workerInfo.getDistance());
        infoModel.setNativePlace(workerInfo.getNativePlace());
        infoModel.setWorkingYears(workerInfo.getWorkingYears());
        infoModel.setEducation(workerInfo.getEducation());
        infoModel.setHobby(workerInfo.getHobby());
        infoModel.setStature(workerInfo.getStature());
        infoModel.setWeight(workerInfo.getWeight());
        infoModel.setBloodType(workerInfo.getBloodType());
        infoModel.setConstellation(workerInfo.getConstellation());
        infoModel.setSignature(workerInfo.getSignature());
        infoModel.setAddress(workerInfo.getAddress());
        infoModel.setServiceScope(workerInfo.getServiceScope());
        infoModel.setServiceTime(workerInfo.getServiceTime());
        infoModel.setIntro(workerInfo.getIntro());
        infoModel.setLike(intStringToBoolean(workerInfo.getIsPraise()));
        infoModel.setLikeCount(Integer.parseInt(workerInfo.getPraiseCount()));
        infoModel.setCollected(intStringToBoolean(workerInfo.getIsFavorite()));
        infoModel.setCollectedCount(Integer.parseInt(workerInfo.getFavoriteCount()));
        infoModel.setOrderRank(workerInfo.getOrderRank());
        infoModel.setOrderCount(workerInfo.getOrderCount());
        infoModel.setPhoneCount(workerInfo.getPhoneCount());
        infoModel.setPhoneRank(workerInfo.getPhoneRank());
        infoModel.setGrade(workerInfo.getGrade());
        infoModel.setGradeNumber(workerInfo.getGrade());
        infoModel.setGradeRank(workerInfo.getGradeRank());
        if (workerInfo.getSystemCertifications() != null) {
            List<SystemCertification> systemCertifications = new ArrayList<>(workerInfo.getSystemCertifications().size());
            for (WorkerInfo.WorkerSystemCertification certification : workerInfo.getSystemCertifications()) {
                SystemCertification certification1 = new SystemCertification();
                certification1.setImage(certification.getImage());
                certification1.setDescription(certification.getDescription());
                certification1.setName(certification.getName());
                systemCertifications.add(certification1);
            }
            infoModel.setSystemCertifications(systemCertifications);
        }
        infoModel.setMandarinAbility(workerInfo.getMandarinAbility());
        infoModel.setWorkExperience(workerInfo.getWorkExperience());
        infoModel.setLongitude(Double.parseDouble(workerInfo.getLongitude()));
        infoModel.setLatitude(Double.parseDouble(workerInfo.getLatitude()));
        infoModel.setDefaultServiceId(workerInfo.getDefaultServiceId());
        infoModel.setDefaultService(workerInfo.getDefaultService());
        infoModel.setServices(workerInfo.getServices());
        infoModel.setActionEntity(workerInfo.getActionEntity());
        infoModel.setCallable(intStringToBoolean(workerInfo.getIsCall()));
        infoModel.setActivityNgInfos(workerInfo.getActivityNgInfos());
        infoModel.setGetWorkerTagsInfo(workerInfo.getGetWorkerTagsInfo());
        infoModel.setPhoneCount2(workerInfo.getPhoneCount2());
        infoModel.setOrderCount2(workerInfo.getOrderCount2());
        infoModel.setGrade2(workerInfo.getGrade2());
        return infoModel;
    }


    public List<BusinessInfoModel> transformBusinessList(List<BusinessInfo> businessInfoList) {
        List<BusinessInfoModel> modelList = new ArrayList<>();
        if (businessInfoList != null) {
            for (BusinessInfo info : businessInfoList) {
                BusinessInfoModel model = transformBusiness(info);
                modelList.add(model);
            }
        }
        return modelList;
    }

    public List<WorkerCollectionEntity> transformWorkerList(List<WorkerInfo> workerInfoList) {
        List<WorkerCollectionEntity> modelList = new ArrayList<>();
        if (workerInfoList != null) {
            for (WorkerInfo info : workerInfoList) {
                WorkerCollectionEntity model = transformWorker(info);
                modelList.add(model);
            }

        }
        return modelList;
    }


}
