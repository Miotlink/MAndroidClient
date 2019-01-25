package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;

/**
 * Created by Administrator on 2015/12/21.
 * 服务端响应数据元信息
 */

public class Meta {

    public static final String CODE_SUCCESS = "0";

    public static final String CODE_INVALID_TOKEN = "2004";

    public static final String CODE_DEVICE = "2003";

    public static final String CODE_NO_SERVICE = "3001";

    public static final String CODE_OUT_OF_SERVICE = "3002";

    /**
     * 返回结果编号, 0 表示无错误, 2004 表示用户权限认证失败, 返回负数表示各种错误
     */
    @SerializedName("ErrorCode")
    private String errorCode;

    /**
     * 错误信息
     */
    @SerializedName("ErrorMsg")
    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public boolean isValid() throws ResponseMetaAuthException {

        if (errorCode.equals(CODE_SUCCESS)) {
            return true;
        } else if (errorCode.equals(CODE_INVALID_TOKEN)) {
            throw new ResponseMetaAuthException(errorMsg);
        } else {
            return false;
        }
    }

    /**
     * 判断请求数据是否成功
     */
    public boolean isSuccess() {
        return errorCode.equals(CODE_SUCCESS);
    }

    /**
     * 判断是否token验证失败
     */
    public boolean isAuthFailed() {
        return errorCode.equals(CODE_INVALID_TOKEN);
    }
}
