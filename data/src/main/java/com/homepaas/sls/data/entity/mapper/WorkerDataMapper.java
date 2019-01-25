package com.homepaas.sls.data.entity.mapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.homepaas.sls.data.BuildConfig;
import com.homepaas.sls.data.entity.WorkerEntity;
import com.homepaas.sls.data.entity.WorkerEntity.Photo;
import com.homepaas.sls.data.entity.WorkerEntity.Service.DefaultService;
import com.homepaas.sls.data.entity.WorkerEntity.SystemCertification;
import com.homepaas.sls.data.network.dataformat.WorkerTagsBody;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.ServiceType;
import com.homepaas.sls.domain.entity.WorkerInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/12 0012
 *
 * @author zhudongjie .
 */
@Singleton
public class WorkerDataMapper {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Inject
    public WorkerDataMapper() {
    }
    private GetWorkerTagsInfo transform(WorkerTagsBody workerTagsBody){
        GetWorkerTagsInfo getWorkerTagsInfo = new GetWorkerTagsInfo();
        getWorkerTagsInfo.setCount(workerTagsBody.getCount());
        getWorkerTagsInfo.setWorkerTagsInfos(workerTagsBody.getWorkerTagsInfos());
        return getWorkerTagsInfo;
    }
    private WorkerTagsBody transform(GetWorkerTagsInfo getWorkerTagsInfo){
        WorkerTagsBody workerTagsBody = new WorkerTagsBody();
        workerTagsBody.setCount(getWorkerTagsInfo.getCount());
        workerTagsBody.setWorkerTagsInfos(getWorkerTagsInfo.getWorkerTagsInfos());
        return workerTagsBody;
    }

    @Nullable
    public WorkerInfo transform(@Nullable WorkerEntity workerEntity) {
        WorkerInfo workerInfo = null;
        if (workerEntity != null) {
            workerInfo = new WorkerInfo();
            workerInfo.setId(workerEntity.getId());
            workerInfo.setPhoto(workerEntity.getPhoto());
            workerInfo.setPhotoCount(workerEntity.getPhotoCount());
            workerInfo.setPhoneNumber(workerEntity.getPhoneNumber());
            workerInfo.setAllowAcceptOrder(workerEntity.getAllowAcceptOrder());
            workerInfo.setName(workerEntity.getName());
            workerInfo.setGender(workerEntity.getGender());
            workerInfo.setAge(workerEntity.getAge());
            workerInfo.setJobNumber(workerEntity.getJobNumber());
            workerInfo.setWage(workerEntity.getWage());
            workerInfo.setDistance(workerEntity.getDistance());
            workerInfo.setNativePlace(workerEntity.getNativePlace());
            workerInfo.setWorkingYears(workerEntity.getWorkingYears());
            workerInfo.setEducation(workerEntity.getEducation());
            workerInfo.setHobby(workerEntity.getHobby());
            workerInfo.setStature(workerEntity.getStature());
            workerInfo.setWeight(workerEntity.getWeight());
            workerInfo.setBloodType(workerEntity.getBloodType());
            workerInfo.setConstellation(workerEntity.getConstellation());
            workerInfo.setSignature(workerEntity.getSignature());
            workerInfo.setAddress(workerEntity.getAddress());
            workerInfo.setServiceTime(workerEntity.getServiceTime());
            workerInfo.setServiceScope(workerEntity.getServiceScope());
            workerInfo.setIntro(workerEntity.getIntro());
            workerInfo.setPraiseCount(workerEntity.getPraiseCount());
            workerInfo.setIsPraise(workerEntity.getIsPraise());
            workerInfo.setFavoriteCount(workerEntity.getFavoriteCount());
            workerInfo.setIsFavorite(workerEntity.getIsFavorite());
            workerInfo.setOrderCount(workerEntity.getOrderCount());
            workerInfo.setOrderRank(workerEntity.getOrderRank());
            workerInfo.setPhoneCount(workerEntity.getPhoneCount());
            workerInfo.setPhoneRank(workerEntity.getPhoneRank());
            workerInfo.setGrade(workerEntity.getGrade());
            workerInfo.setGradeRank(workerEntity.getGradeRank());
            List<WorkerInfo.WorkerSystemCertification> systemCertifications = new ArrayList<>();
            if (workerEntity.getSystemCertification() != null) {
                for (SystemCertification certification : workerEntity.getSystemCertification()) {
                    WorkerInfo.WorkerSystemCertification wsc = new WorkerInfo.WorkerSystemCertification();
                    wsc.setImage(certification.getImage());
                    wsc.setName(certification.getName());
                    wsc.setDescription(certification.getDescription());
                    systemCertifications.add(wsc);
                }
                workerInfo.setSystemCertifications(systemCertifications);
            }

            workerInfo.setMandarinAbility(workerEntity.getMandarinAbility());
            workerInfo.setWorkExperience(workerEntity.getWorkExperience());
            workerInfo.setLatitude(workerEntity.getLatitude());
            workerInfo.setLongitude(workerEntity.getLongitude());
            if (workerEntity.getDefaultService() != null){
                workerInfo.setDefaultService(workerEntity.getDefaultService().getName());
                workerInfo.setDefaultServiceId(workerEntity.getDefaultService().getId());
            }
            List<String> services = new ArrayList<>();
            if (workerEntity.getServices() != null){
                for (ServiceType serviceType : workerEntity.getServices()){
                    services.add(serviceType.getName());
                }
                workerInfo.setServices(services);
            }

            if (workerEntity.getActionEntity() != null){
                workerInfo.setActionEntity(workerEntity.getActionEntity());
            }
            workerInfo.setIsCall(workerEntity.getIsCall());
            workerInfo.setActivityNgInfos(workerEntity.getActivityNgInfos());
            workerInfo.setGetWorkerTagsInfo(transform(workerEntity.getWorkerTagsBody()));
            workerInfo.setPhoneCount2(workerEntity.getPhoneCount2());
            workerInfo.setOrderCount2(workerEntity.getOrderCount2());
            workerInfo.setGrade2(workerEntity.getGrade2());
        }

        return workerInfo;
    }

    @NonNull
    public List<WorkerEntity> transform(@Nullable List<WorkerInfo> infoList, long persistedTime) {
        if (infoList != null) {
            List<WorkerEntity> entities = new ArrayList<>(infoList.size());
            for (WorkerInfo info : infoList) {
                entities.add(transform(info, persistedTime));
            }
            return entities;
        }
        return new ArrayList<>();
    }

    @NonNull
    public List<WorkerInfo> transform(@Nullable List<WorkerEntity> entities) {
        List<WorkerInfo> infoList = new ArrayList<>();
        if (entities != null) {
            for (WorkerEntity entity : entities) {
                infoList.add(transform(entity));
            }
        }
        return infoList;
    }

    @Nullable
    public WorkerEntity transform(@Nullable WorkerInfo info) {
        return transform(info, 0);
    }

    @Nullable
    public WorkerEntity transform(@Nullable WorkerInfo info, long persistedTime) {
        WorkerEntity entity = null;
        if (info != null) {
            entity = new WorkerEntity();
            entity.setAddress(info.getAddress());
            entity.setAge(info.getAge());
            entity.setBloodType(info.getBloodType());
            entity.setConstellation(info.getConstellation());
            WorkerEntity.Service service = new WorkerEntity.Service();
            service.setServiceTypeList(info.getServices());
            service.setDefaultService(new DefaultService(info.getDefaultServiceId(), info.getDefaultService()));
            entity.setDistance(info.getDistance());
            entity.setEducation(info.getEducation());
            entity.setFavoriteCount(info.getFavoriteCount());
            entity.setGender(info.getGender());
            entity.setGrade(info.getGrade());
            entity.setGradeRank(info.getGradeRank());
            entity.setHobby(info.getHobby());
            entity.setId(info.getId());
            entity.setIntro(info.getIntro());
            entity.setIsFavorite(info.getIsFavorite());
            entity.setIsPraise(info.getIsPraise());
            entity.setJobNumber(info.getJobNumber());
            entity.setName(info.getName());
            entity.setNativePlace(info.getNativePlace());
            entity.setOrderCount(info.getOrderCount());
            entity.setOrderRank(info.getOrderRank());
            entity.setPhoneCount(info.getPhoneCount());
            entity.setPhoneRank(info.getPhoneRank());
            entity.setPhoneNumber(info.getPhoneNumber());
            entity.setPraiseCount(info.getPraiseCount());
            entity.setServiceScope(info.getServiceScope());
            entity.setServiceTime(info.getServiceTime());
            entity.setSignature(info.getSignature());
            entity.setStature(info.getStature());
            List<SystemCertification> systemCertifications = new ArrayList<>();
            if (info.getSystemCertifications() != null) {
                for (WorkerInfo.WorkerSystemCertification certification : info.getSystemCertifications()) {
                    SystemCertification wsc = new SystemCertification();
                    wsc.setImage(certification.getImage());
                    wsc.setName(certification.getName());
                    wsc.setDescription(certification.getDescription());
                    wsc.setWorker(entity);
                    systemCertifications.add(wsc);
                }
            }
            entity.setSystemCertification(systemCertifications);
            entity.setWage(info.getWage());
            entity.setWeight(info.getWeight());
            entity.setWorkingYears(info.getWorkingYears());
            entity.setWorkExperience(info.getWorkExperience());
            entity.setMandarinAbility(info.getMandarinAbility());
            entity.setLongitude(info.getLongitude());
            entity.setLatitude(info.getLatitude());
            entity.setPersistedTime(persistedTime);
            entity.setIsCall(info.getIsCall());
            entity.setAllowAcceptOrder(info.getAllowAcceptOrder());
            entity.setActivityNgInfos(info.getActivityNgInfos());
            entity.setWorkerTagsBody(transform(info.getGetWorkerTagsInfo()));
            entity.setGrade2(info.getGrade2());
            entity.setOrderCount2(info.getOrderCount2());
            entity.setPhoneCount2(info.getPhoneCount2());
        }

        return entity;
    }
}
