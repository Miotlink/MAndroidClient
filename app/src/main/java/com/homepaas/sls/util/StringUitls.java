package com.homepaas.sls.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mhy on 2017/9/5.
 */

public class StringUitls {
    /**
     * 从字符串中获取有效的电话号码返回
     * @param sParam
     * @return
     */
    public static String getTelnum(String sParam) {

        if (sParam.length() <= 0)
            return "";
        Pattern pattern = Pattern.compile("(1|861)(3|5|8)\\d{9}$*|\\d{12}|\\d{10}|\\d{3}\\-\\d{8}");
        Matcher matcher = pattern.matcher(sParam);
        StringBuffer bf = new StringBuffer();
        while (matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        return bf.toString();
    }
}
