package com.homepaas.sls.domain.param;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
public class LoginParam {

    private final String account;

    private final String password;

    private String captcha;

    public LoginParam(String account, String password) {
        this.account = account;
        this.password = password;
    }


    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginParam{");
        sb.append("account='").append(account).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", captcha='").append(captcha).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
