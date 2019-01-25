package com.homepaas.sls.ui.widget;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JWC on 2016/12/29.
 */

public class KeywordUtil {
    public static SpannableString matcherSearchTitle(int color, String text,
                                                     String keyword) {
        if(keyword!=null) {
            SpannableString s = new SpannableString(text);
            Pattern p = Pattern.compile(keyword);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return s;
        }
        return null;
    }

    public static SpannableString matcherActivity(int color, String text) {
            SpannableString s = new SpannableString(text);
            Pattern p = Pattern.compile("[0-9.]*");
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return s;
    }

    public static String matcherHtml(String text){
        String s="";
        String regex="(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s=text.substring(start,end);
        }
        return s;
    }

    /**
     * 判断小数点后面的数是不是0,这个是服务数量的
     *
     * @param numberStr
     * @return
     */
    public static String determine(String numberStr) {
        if(!TextUtils.isEmpty(numberStr)) {
            String[] numbers = numberStr.split("\\.");
            if (numbers.length > 1) {
                if (!TextUtils.isEmpty(numbers[1]) && (TextUtils.equals(numbers[1], "0")||TextUtils.equals(numbers[1], "00"))) {
                    return numbers[0];
                } else {
                    return numberStr;
                }
            } else {
                return numberStr;
            }
        }else {
            return "";
        }
    }

}
