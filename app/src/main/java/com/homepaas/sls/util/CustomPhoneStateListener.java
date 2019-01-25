package com.homepaas.sls.util;

import android.content.Context;
import android.database.Cursor;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.homepaas.sls.event.EventCallPhoneInfo;
import com.homepaas.sls.event.EventCallPhoneNoConnect;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.CALL_PHONE;
import static android.provider.CallLog.Calls.CACHED_NAME;
import static android.provider.CallLog.Calls.CONTENT_URI;
import static android.provider.CallLog.Calls.DATE;
import static android.provider.CallLog.Calls.DURATION;
import static android.provider.CallLog.Calls.NUMBER;
import static android.provider.CallLog.Calls.TYPE;

/**
 * Created by mhy on 2017/9/25.
 */

public class CustomPhoneStateListener extends PhoneStateListener {
    private EventCallPhoneInfo eventCallPhoneInfo;
    private Context mContext;

    public CustomPhoneStateListener(Context context) {
        mContext = context;
        EventBus.getDefault().register(this);
    }


    public void dispose() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void orderDetailsCallPhone(EventCallPhoneInfo eventCallPhoneInfo) {
        this.eventCallPhoneInfo = eventCallPhoneInfo;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        if (eventCallPhoneInfo == null)
            return;
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE://挂断
                //读取通话记录，查看刚刚拨出的号码是否接通成功
                readPhoneInfo(eventCallPhoneInfo.getPhoneNumber());
                eventCallPhoneInfo = null;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK://接听
                break;
            case TelephonyManager.CALL_STATE_RINGING://来电
                break;
        }
    }


    private void readPhoneInfo(String phoneNumber) {
        Cursor cursor = mContext.getContentResolver().query(CONTENT_URI,
                new String[]{NUMBER,// 电话号码
                        CACHED_NAME,// 联系人
                        TYPE,// 通话类型
                        DATE,// 通话时间
                        DURATION // 通话时长
                }, NUMBER + "=?",
                new String[]{phoneNumber},
                DATE + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            String conNumber = cursor.getString(cursor.getColumnIndex(NUMBER));
            int conType = cursor.getInt(cursor.getColumnIndex(TYPE));
            long timestamp = cursor.getLong(cursor.getColumnIndex(DATE));
            //通话记录时长 duration==0未拨通
            long duration = cursor.getLong(cursor.getColumnIndex(DURATION));
            cursor.close();
            //匹配好拨打电话时间 timestamp

//            来电：CallLog.Calls.INCOMING_TYPE （常量值：1）
//            已拨：CallLog.Calls.OUTGOING_TYPE（常量值：2）
//            未接：CallLog.Calls.MISSED_TYPE（常量值：3）
            if (conNumber.equals(phoneNumber)/*should be always true*/ && duration == 0) {
                EventBus.getDefault().post(new EventCallPhoneNoConnect());
            }
        }
    }

}
