package com.homepaas.sls.common;

import android.text.TextUtils;

/**
 * Created by CJJ on 2016/11/18.
 */

public final class Util {
    //去除".0"精度(////todo 暂时没有考虑去除“20.01000”格式最后的三个零)
    public static String cutUnnecessaryPrecision(String decimal) {
        if (!TextUtils.isEmpty(decimal)&&validateDecimal(decimal)) {
            int index = decimal.indexOf(".");
            if (index > 0) {
                if (index != decimal.length() - 1) {
                    if (decimal.length() > index + 1) {
                        String substring = decimal.substring(index + 1);
                        int tailInt = Integer.parseInt(substring);
                        if (tailInt <= 0)
                            return decimal.substring(0, index);
                        else {//如果小数结尾有零全部去除
//                       if (substring.charAt(substring.length()-1) == '0')
//                       {
//                           return decimal.substring(0,index+1+substring.length()-1);
//                       }else return decimal.substring(0,index+substring.length());
                            int beginIndex = substring.indexOf("0");
                            if (beginIndex > 0) {
                                String ssub = substring.substring(beginIndex);
                                if (Integer.parseInt(ssub) <= 0) {//末尾是零
                                    return decimal.substring(0, index + 1 + beginIndex);
                                }
                            } else {
                                return decimal.substring(0, index + substring.length() + 1);
                            }

                        }
                    }
                } else
                    //去除多余小数点
                    return decimal.substring(0, index);
            } else return decimal;
        }
        return decimal;
    }

    //check for valid decimal number
    private static boolean validateDecimal(String decimal) {

        char[] chars = decimal.toCharArray();
        for (int i= 0;i<chars.length;i++){
            char c = chars[i];
            if ((c <48&&c != 46)|| c >57)
                return false;
        }
        return true;
    }
}
