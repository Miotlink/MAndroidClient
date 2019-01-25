package com.homepaas.sls.event;

import com.homepaas.sls.domain.entity.BusinessEntity;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.WorkerEntity;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.CallLogModel;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;

/**
 * 拨打电话消息事件信息
 *
 * @author zhudongjie .
 */
public class EventPhoneInfo {

    private String name;

    private String phoneNumber;

    private String id;

    private int type;

    private String url;

    private String attribution;

    public EventPhoneInfo(IServiceInfo info) {
        name = info.getName();
        phoneNumber = info.getPhoneNumber();
        id = info.getId();
        type = info.type();
//        url = info.getPhotos().get(0).getSmallPic();
        if (info instanceof WorkerCollectionEntity) {
            attribution = ((WorkerCollectionEntity) info).getNativePlace();
        } else if (info instanceof BusinessInfoModel) {
            attribution = ((BusinessInfoModel) info).getAddress();
        }
    }

    public EventPhoneInfo(IService info) {
        if (info instanceof WorkerEntity){
            WorkerEntity entity = (WorkerEntity)info;
            name = entity.getName();
            phoneNumber = entity.getPhoneNumber();
            id = entity.getId();
            type = 1;
            attribution = entity.getNativePlace();
        } else {
            BusinessEntity entity = (BusinessEntity) info;
            name = entity.getName();
            phoneNumber = entity.getPhoneNumber();
            id = entity.getId();
            type = 2;
            attribution = entity.getAddress();
        }
    }

    public EventPhoneInfo(WorkerBussinesModel workerBussinesModel) {
        name = workerBussinesModel.getName();
        phoneNumber = workerBussinesModel.getPhoneNumber();
        id = workerBussinesModel.getId();
        type = workerBussinesModel.getType();
        url = workerBussinesModel.getPhoto();
        attribution = workerBussinesModel.getNativePlace();

    }
    public EventPhoneInfo(CallLogModel callLog){
        name = callLog.getName();
        phoneNumber = callLog.getPhoneNumber();
        id = callLog.getId();
        type = callLog.getType();
        url = callLog.getPhotoUrl();
        attribution = callLog.getAttribution();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getAttribution() {
        return attribution;
    }
}
