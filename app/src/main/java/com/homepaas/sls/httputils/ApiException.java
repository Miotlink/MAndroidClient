package com.homepaas.sls.httputils;

/**
 *  2017/3/2.
 */

public class ApiException extends Exception{
    private String code;
    private String enumCode;
    public ApiException(String error, String code, String enumCode){
        super(error);
        this.code = code;
        this.enumCode = enumCode;
    }

    public ApiException(String error, String code){
        super(error);
        this.code = code;
    }

    public ApiException(String error){
        super(error);
        this.code ="-1";
        this.enumCode = "";
    }
    public String getCode() {
        return code;
    }

    public String getEnumCode() {
        return enumCode;
    }
}
