package com.homepaas.sls.data.network.dataformat.request;

/**
 * Created by Administrator on 2016/12/7.
 */

public class BusinessOrderServiceListRequest {

    public BusinessOrderServiceListRequest(String serviceId, String latitude, String longitude, String pageIndex, String pageSize, String tagId) {
        ServiceId = serviceId;
        Latitude = latitude;
        Longitude = longitude;
        PageIndex = pageIndex;
        PageSize = pageSize;
        TagId = tagId;
    }

    /**
     * ServiceId：必填，二级服务的Id。
     * Latitude：必填，经度
     * Longitude：必填，纬度
     * PageIndex：必填，当前第几页。默认：1；第一页
     * PageSize：必填，每一页的条目数，默认：10；10条
     * TagId：选填，用于标签筛选
     */

    private String ServiceId;
    private String Latitude;
    private String Longitude;
    private String PageIndex;
    private String PageSize;
    private String TagId;

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String ServiceId) {
        this.ServiceId = ServiceId;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(String PageIndex) {
        this.PageIndex = PageIndex;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String PageSize) {
        this.PageSize = PageSize;
    }
}
