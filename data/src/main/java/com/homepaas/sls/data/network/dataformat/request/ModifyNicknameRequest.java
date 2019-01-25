package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/22 0022
 *
 * @author zhudongjie .
 */
public class ModifyNicknameRequest {
    @SerializedName("Token")
    public final String token;
    @SerializedName("NickName")
    public final String nickname;

    public ModifyNicknameRequest(String token, String nickname) {
        this.token = token;
        this.nickname = nickname;
    }
}
