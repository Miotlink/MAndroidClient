package com.homepaas.sls.data.network.dataformat.request;

/**
 * Created by Administrator on 2016/12/7.
 */

public class GetBusinessCommentRequest {
    private String Token;

    private String UserType;
    private String MerchantOrWorkerId;
    private String PageIndex;
    private String PageSize;
    private String IsEnablePaging;


    public GetBusinessCommentRequest(String token, String userType, String merchantOrWorkerId, String pageIndex, String pageSize, String isEnablePaging) {
        Token = token;
        UserType = userType;
        MerchantOrWorkerId = merchantOrWorkerId;
        PageIndex = pageIndex;
        PageSize = pageSize;
        IsEnablePaging = isEnablePaging;
    }

    public String getIsEnablePaging() {
        return IsEnablePaging;
    }

    public void setIsEnablePaging(String isEnablePaging) {
        IsEnablePaging = isEnablePaging;
    }

    public String getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(String pageIndex) {
        PageIndex = pageIndex;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getMerchantOrWorkerId() {
        return MerchantOrWorkerId;
    }

    public void setMerchantOrWorkerId(String merchantOrWorkerId) {
        MerchantOrWorkerId = merchantOrWorkerId;
    }
}
