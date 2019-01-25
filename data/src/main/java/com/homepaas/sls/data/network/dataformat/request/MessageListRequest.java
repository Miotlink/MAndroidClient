package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
public class MessageListRequest {
    @SerializedName("Token")
    public String token;
    private String IsEnablePaging;
    private String PageIndex;
    private String PageSize;
//    IsEnablePaging：0：不启用分页，1：启用分页
//    PageIndex：第几页
//    PageSize：每一页多少条

    public MessageListRequest(String token, String isEnablePaging, String pageIndex, String pageSize) {
        this.token = token;
        IsEnablePaging = isEnablePaging;
        PageIndex = pageIndex;
        PageSize = pageSize;
    }
}
