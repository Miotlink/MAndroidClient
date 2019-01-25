package com.homepaas.sls.data.network.dataformat.request;

/**
 * Created by Administrator on 2016/12/7.
 */

public class GetBusinessInfoRequest {
    private String Token;

    private String UserType;
    private String MerchantOrWorkerId;

    public GetBusinessInfoRequest(String token, String userType, String merchantOrWorkerId) {
        Token = token;
        UserType = userType;
        MerchantOrWorkerId = merchantOrWorkerId;
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
