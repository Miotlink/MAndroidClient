package com.homepaas.sls.common;

import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * on 2016/3/23 0023
 *
 * @author zhudongjie .
 */
public class PinyinUtils {

    private static final String TAG = "PinyinUtils";

    private PinyinUtils() {
    }

    /**
     * 将汉字字符转化为拼音，其他字符不变
     *
     * @param phrase 汉语语句
     * @return 转化后的拼音
     */
    public static String[] characterToPinyin(String phrase) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        char[] phraseArray = phrase.toCharArray();
        String[] pinyinArray = new String[phraseArray.length];
        for (int i = 0; i < phraseArray.length; i++) {
            try {
                String[] possiblePinyins = PinyinHelper.toHanyuPinyinStringArray(phraseArray[i], outputFormat);
                if (possiblePinyins != null) {
                    pinyinArray[i] = possiblePinyins[0];
                } else {
                    pinyinArray[i] = String.valueOf(phraseArray[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                Log.e(TAG, "characterToPinyin: ", e);
                pinyinArray[i] = String.valueOf(phraseArray[i]);
            }
        }

        return pinyinArray;
    }

    /**
     * 将汉字字符短语转化为拼音短语，其他字符不变
     *
     * @param phrase 汉语语句
     * @return 转化后的拼音
     */
    public static String toPinyinPhrase(String phrase) {
        String[] strings = characterToPinyin(phrase);
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
        }
        return sb.toString();
    }
}
