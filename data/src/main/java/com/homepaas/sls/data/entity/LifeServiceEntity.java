package com.homepaas.sls.data.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.validator.ttl.TtlObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2015/12/21.
 * 系统服务实体数据
 */

@DatabaseTable(tableName = "tb_lifeService")
public class LifeServiceEntity implements TtlObject{

    //类型ID
    @DatabaseField
    @SerializedName("TypeId")
    private String typeId;
    //类型图标
    @DatabaseField
    @SerializedName("TypeLogo")
    private String typeLogo;
    //类型名称
    @DatabaseField
    @SerializedName("TypeName")
    private String typeName;
    //服务ID
    @DatabaseField
    @SerializedName("ServiceId")
    private String serviceId;
    //服务名称
    @DatabaseField
    @SerializedName("ServiceName")
    private String serviceName;
    //持久化时间
    @DatabaseField
    private long persistedTime;

    public LifeServiceEntity() {}

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeLogo() {
        return typeLogo;
    }

    public void setTypeLogo(String typeLogo) {
        this.typeLogo = typeLogo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public long getPersistedTime() {
        return persistedTime;
    }

    public void setPersistedTime(long persistedTime) {
        this.persistedTime = persistedTime;
    }
}
