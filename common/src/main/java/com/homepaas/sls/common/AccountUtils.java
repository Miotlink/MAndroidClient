package com.homepaas.sls.common;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
public final class AccountUtils {

    private static Pattern sPattern = Pattern.compile("\\w{6,12}");

    private static Pattern sNicknamePattern = Pattern.compile("[a-zA-Z_\\u4e00-\\u9fa5]{1,10}");

    public static boolean isAuthCodeValid(String authCode) {
        return TextUtils.isDigitsOnly(authCode)&& authCode.length() == 4;
    }

    public static boolean isAccountValid(String account) {
        return TextUtils.isDigitsOnly(account) && account.startsWith("1") && account.length() == 11;
    }


    public static boolean isNicknameValid(String nickname) {
        return sNicknamePattern.matcher(nickname).matches();
    }

    public static boolean isPasswordValid(String password) {
        return sPattern.matcher(password).matches();
    }

}
