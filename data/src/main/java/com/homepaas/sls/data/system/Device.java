package com.homepaas.sls.data.system;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.homepaas.sls.data.exception.GetSystemInfoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/2/2 0002
 *
 * @author zhudongjie .
 */
@Singleton
public class Device {

    private static final String TAG = "Device";

    private Context context;

    @Inject
    public Device(Context context) {
        this.context = context;
    }

    public String getDeviceId() throws GetSystemInfoException {
        String serialNumber = android.os.Build.SERIAL;
        // 恢复出厂设置和root的时候会被重置
        // String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        /**
         * tm.getDeviceId() 获取IMEI值，手机设备有效，平板电脑获取为null
         * SerialNumber 所有GSM(全球移动通讯系统)设备都能获取唯一标识符，但CDMA（电信手机使用）设备返回null
         */
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String longID = null;
        MessageDigest m = null;
        try {
            longID = tm.getDeviceId() + getAppendID() + serialNumber;
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "getMD5uuid: ", e);
            throw new GetSystemInfoException(e);
        } catch (SecurityException e) {
            //when current process doesn't have android.permission.READ_PHONE_STATE
            Log.e(TAG, "getDeviceId: ", e);
            throw new GetSystemInfoException(e);
        }
        m.update(longID.getBytes(), 0, longID.length());
        byte pMd5Data[] = m.digest();
        String uniqueID = "";
        for (int i = 0; i < pMd5Data.length; i++) {
            int b = (0xFF & pMd5Data[i]);
            if (b <= 0xF)
                uniqueID += "0";
            uniqueID += Integer.toHexString(b);
        }
        return uniqueID.toUpperCase();
    }

    /**
     * 平板电脑的设置没有通话功能或者你不愿加入READ_PHONE_STATE许可
     * 取出ROM版本、制造商、CPU型号、以及其他硬件信息
     * Android系统2.3版本以上可以通过下面的方法得到Serial Number
     * 模拟形成IMEI值 由15个数字构成
     */
    private String getAppendID() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("35");
        stringBuilder.append(Build.BOARD.length() % 10);
        stringBuilder.append(Build.BRAND.length() % 10);
        stringBuilder.append(Build.CPU_ABI.length() % 10);
        stringBuilder.append(Build.DEVICE.length() % 10);
        stringBuilder.append(Build.DISPLAY.length() % 10);
        stringBuilder.append(Build.HOST.length() % 10);
        stringBuilder.append(Build.ID.length() % 10);
        stringBuilder.append(Build.MANUFACTURER.length() % 10);
        stringBuilder.append(Build.MODEL.length() % 10);
        stringBuilder.append(Build.PRODUCT.length() % 10);
        stringBuilder.append(Build.TAGS.length() % 10);
        stringBuilder.append(Build.TYPE.length() % 10);
        stringBuilder.append(Build.USER.length() % 10);
        return stringBuilder.toString();
    }
}
