package com.homepaas.sls.httputils;

import com.homepaas.sls.R;
import com.homepaas.sls.SimpleTinkerInApplicationLike;

/**
 * Created by mhy on 2017/7/25.
 */

public class Constants {

    public static String CLIENT_TIMEOUT_EXCEPTION = "小二繁忙，请稍等~~~";
    public static String SOCKET_TIMEOUT_EXCEPTION = "服务器响应超时,请稍后重试";
    public static String CONNECT_EXCEPTION = SimpleTinkerInApplicationLike.getMainApplication().getString(R.string.please_check_network);
    public static String UNKNOWN_HOST_EXCEPTION = "服务器异常,请稍后再试";
    public static String NULL_POINT_EREXCEPTION = "空指针异常";
    public static String HTTP_EXCEPTION = "";
    public static String INTERNAL_SERVER_ERROR = "服务器内部错误";
    public static String OTHER_EXCEPTION = "未知错误";
    public static String PARSE_EXCEPTION = "客户端解析异常";
    public static String SSL_HANDSHAKE_EXCEPTION = "证书错误";



}
