package com.homepaas.sls.data.network.dataformat.request;

/**
 * Created by Administrator on 2016/12/7.
 */

public class SelectServiceOrWorkerRequest {

    /**
     * Token；必填
     * TabType：1：最近Tab，2：附近Tab；必填
     * ServiceId：新服务体系的四级服务品类的Id；必填
     * AddressId：地址Id；必填
     * IsEnablePaging：是否启用分页；0：不启用，1：启用，默认启用
     * PageIndex：第几页；，默认第1页
     * PageSize：每页显示多少条；默认10条
     * Token : xxx
     * TabType : xxx
     * ServiceId : xxx
     * AddressId : xxx
     * IsEnablePaging : 1
     * PageIndex : 1
     * PageSize : 10
     */

    private String Token;
    private String TabType;
    private String ServiceId;
    private String AddressId;
    private String IsEnablePaging;
    private String PageIndex;
    private String PageSize;

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getTabType() {
        return TabType;
    }

    public void setTabType(String TabType) {
        this.TabType = TabType;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String ServiceId) {
        this.ServiceId = ServiceId;
    }

    public String getAddressId() {
        return AddressId;
    }

    public void setAddressId(String AddressId) {
        this.AddressId = AddressId;
    }

    public String getIsEnablePaging() {
        return IsEnablePaging;
    }

    public void setIsEnablePaging(String IsEnablePaging) {
        this.IsEnablePaging = IsEnablePaging;
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

    public SelectServiceOrWorkerRequest(String token, String tabType, String serviceId, String addressId, String isEnablePaging, String pageIndex, String pageSize) {
        Token = token;
        TabType = tabType;
        ServiceId = serviceId;
        AddressId = addressId;
        IsEnablePaging = isEnablePaging;
        PageIndex = pageIndex;
        PageSize = pageSize;
    }
}
