package com.homepaas.sls.domain.param;

/**
 * on 2016/2/2 0002
 *
 * @author zhudongjie .
 */
public class RegisterParam {

    public final String phone;

    public final String password;

    public final String captcha;

    public final int type;

    public final String invitationCode;

    public RegisterParam(String phone, String password, String captcha, int type,String invitationCode) {
        this.phone = phone;
        this.password = password;
        this.captcha = captcha;
        this.type = type;
        this.invitationCode = invitationCode;
    }
}
