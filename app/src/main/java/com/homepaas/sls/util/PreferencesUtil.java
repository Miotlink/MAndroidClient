package com.homepaas.sls.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import com.google.gson.Gson;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.util.gson.ParameterizedTypeImpl;

import java.lang.reflect.Type;


/**
 * 保存配置信息
 */
public class PreferencesUtil {

    public static final String SP_SETTING = BuildConfig.APPLICATION_ID + "_sp";
    /**
     * 读取对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getObject(String key, Class<T> clazz) {
        SharedPreferences sharedPreferences = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        String userValue = sharedPreferences.getString(key, "");
        if (userValue.equals(""))
            return null;
        try {
            byte[] data = Base64.decode(userValue.getBytes(), Base64.DEFAULT);
            Gson gson = new Gson();
            Type type = new ParameterizedTypeImpl(clazz, new Class[]{clazz});
            T obj = gson.fromJson(new String(data), type);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 保存对象
     *
     * @param key    要保存对象的key
     * @param object 要保存的对象
     */
    public static void saveObject(String key, Object object) {
        SharedPreferences sharedPreferences = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        try {
            Gson gson = new Gson();
            String json = gson.toJson(object);
            String objValue = new String(Base64.encode(json.getBytes(), Base64.DEFAULT));
            Editor editor = sharedPreferences.edit();
            editor.putString(key, objValue);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 保存字符串
     *
     * @param key    对应KEY
     * @param values 对应值
     */
    public static void saveString(String key, String values) {
        SharedPreferences sp = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, 0);
        sp.edit().putString(key, values).apply();
    }

    /**
     * 获取字符串的值
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        SharedPreferences sp = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, 0);
        return sp.getString(key, null);
    }

    /**
     * 保存int数据
     *
     * @param key
     * @param values
     */
    public static void saveInt(String key, int values) {
        SharedPreferences sp = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, 0);
        sp.edit().putInt(key, values).apply();
    }


    /**
     * 获取int类型数据
     *
     * @param key
     * @return
     */
    public static int getInt(String key) {
        SharedPreferences sp = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, 0);
        return sp.getInt(key, 0);
    }

    /**
     * 保存long数据
     *
     * @param key
     * @param values
     */
    public static void saveLong(String key, long values) {
        SharedPreferences sp = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, 0);
        sp.edit().putLong(key, values).apply();
    }

    /**
     * 获取long类型数据
     *
     * @param key
     * @return
     */
    public static long getLong(String key) {
        SharedPreferences sp = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, 0);
        return sp.getLong(key, 0);
    }

    /**
     * 保存boolean值
     *
     * @param key
     * @param values
     */
    public static void saveBoolean(String key, boolean values) {
        SharedPreferences sp = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, 0);
        sp.edit().putBoolean(key, values).apply();
    }

    /**
     * 获取boolean值
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        SharedPreferences sp = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(SP_SETTING, 0);
        return sp.getBoolean(key, false);
    }

    /**
     * 清空文件中的所有值
     *
     */
    public static void cleanValues() {
        SharedPreferences sp = SimpleTinkerInApplicationLike.getMainApplication().getApplicationContext().getSharedPreferences(
                SP_SETTING, 0);
        sp.edit().clear().apply();
    }
}
