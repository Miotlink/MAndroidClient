package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/18 0018
 *
 * @author zhudongjie .
 */
public class ModifyPasswordRequest {
    @SerializedName("Token")
    public final String token;
    @SerializedName("Password")
    public final String oldPassword;
    @SerializedName("NewPassword")
    public final String newPassword;

    public ModifyPasswordRequest(String token, String oldPassword, String newPassword) {
        this.token = token;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
