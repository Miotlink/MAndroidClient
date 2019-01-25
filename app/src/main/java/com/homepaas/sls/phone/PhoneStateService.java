package com.homepaas.sls.phone;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.di.component.ApplicationComponent;
import com.homepaas.sls.di.component.DaggerPhoneServiceComponent;
import com.homepaas.sls.domain.interactor.SaveCallLogInteractor;
import com.homepaas.sls.event.EventCallState;
import com.homepaas.sls.event.EventPhoneInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import rx.Subscriber;

import static android.provider.CallLog.Calls.CACHED_NAME;
import static android.provider.CallLog.Calls.CONTENT_URI;
import static android.provider.CallLog.Calls.DATE;
import static android.provider.CallLog.Calls.DURATION;
import static android.provider.CallLog.Calls.NUMBER;
import static android.provider.CallLog.Calls.TYPE;
import static com.homepaas.sls.BuildConfig.DEBUG;

/**
 * 监听来电通话状态，读取系统的通话记录【"content://call_log/calls"】，保存用户对应的工人或商户的通话记录到数据库中
 */
public class PhoneStateService extends Service {

    private static final String TAG = "PhoneStateService";

    private EventPhoneInfo eventPhoneInfo;


    private Handler mHandler = new Handler();

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            readCallLogAndSave();
        }
    };

    private PhoneStateListener mStateListener = new PhoneStateListener() {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://挂断
                    if (DEBUG)
                        Log.d(TAG, "onCallStateChanged: CALL_STATE_IDLE");
                    if (eventPhoneInfo != null) {
                        //延迟，查询，确保数据库已添加该记录
                        mHandler.postDelayed(r, 1000);
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://接听
                    if (DEBUG)
                        Log.d(TAG, "onCallStateChanged: CALL_STATE_OFFHOOK");
                    break;
                case TelephonyManager.CALL_STATE_RINGING://来电
                    if (DEBUG)
                        Log.d(TAG, "onCallStateChanged: CALL_STATE_RINGING");
                    break;

            }
        }
    };

    @Inject
    SaveCallLogInteractor mSaveCallLogInteractor;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerPhoneServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
        EventBus.getDefault().register(this);
    }

    private ApplicationComponent getApplicationComponent() {
        return SimpleTinkerInApplicationLike.getApplicationComponent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(mStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 打电话前将信息传过来，保存通话记录要用到相应的信息
     */
    @Subscribe
    public void onEvent(EventPhoneInfo info) {
        this.eventPhoneInfo = info;
    }


    private void readCallLogAndSave() {
        if (eventPhoneInfo == null)
            return;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //在拨打电话前请求该权限
            //todo:需要做一些小概率情况下的处理
            return;
        }
        Cursor cursor = getContentResolver().query(CONTENT_URI,
                new String[]{NUMBER,// 电话号码
                        CACHED_NAME,// 联系人
                        TYPE,// 通话类型
                        DATE,// 通话时间
                        DURATION // 通话时长
                }, NUMBER + "=?",
                new String[]{eventPhoneInfo.getPhoneNumber()},
                DATE + " DESC");
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String conNumber = cursor.getString(cursor.getColumnIndex(NUMBER));
//                String conName = cursor.getString(cursor.getColumnIndex(CACHED_NAME));
//                int conType = cursor.getInt(cursor.getColumnIndex(PriceType));
//                long timestamp = cursor.getLong(cursor.getColumnIndex(DATE));
//                long duration = cursor.getLong(cursor.getColumnIndex(DURATION));
//                Log.i(TAG, "readCallLogAndSave: conNumber = " + conNumber + " conName = " + conName + " conType = " + conType +
//                        " timestamp = " + timestamp + "format time =" + TimeFormatUtils.formatDate(String.valueOf(timestamp / 1000))
//                        + " duration = " + duration);
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
        if (cursor != null && cursor.moveToFirst()) {
            String conNumber = cursor.getString(cursor.getColumnIndex(NUMBER));
            String conName = cursor.getString(cursor.getColumnIndex(CACHED_NAME));
            int conType = cursor.getInt(cursor.getColumnIndex(TYPE));
            long timestamp = cursor.getLong(cursor.getColumnIndex(DATE));
            long duration = cursor.getLong(cursor.getColumnIndex(DURATION));
            cursor.close();
            if (DEBUG)
                Log.d(TAG, "readCallLogAndSave:");
            if (conNumber.equals(eventPhoneInfo.getPhoneNumber())/*should be always true*/ && conType == CallLog.Calls.OUTGOING_TYPE && duration > 0) {
                saveCallLog(timestamp, duration);
            }
        }
    }

    /**
     * 通话记录对于的工人或者商户进行保存数据到数据库
     * @param time
     * @param duration
     */
    public void saveCallLog(long time, long duration) {

        if (DEBUG)
            Log.d(TAG, "saveCallLog: " + duration);
        com.homepaas.sls.domain.entity.CallLog callLog = new com.homepaas.sls.domain.entity.CallLog();

        callLog.setDuration(duration);
        callLog.setTime(String.valueOf(time / 1000));
        callLog.setDialled(duration != 0);

        callLog.setPhoneNumber(eventPhoneInfo.getPhoneNumber());
        callLog.setPhotoUrl(eventPhoneInfo.getUrl());
        callLog.setType(eventPhoneInfo.getType());
        callLog.setId(eventPhoneInfo.getId());
        callLog.setName(eventPhoneInfo.getName());
        callLog.setAttribution(eventPhoneInfo.getAttribution());

        mSaveCallLogInteractor.setCallLog(callLog);
        mSaveCallLogInteractor.execute(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                eventPhoneInfo = null;
                EventBus.getDefault().post(new EventCallState(TelephonyManager.CALL_STATE_IDLE));
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
                eventPhoneInfo = null;
            }

            @Override
            public void onNext(Boolean aBoolean) {
                Log.i(TAG, "onNext: save call log " + (aBoolean ? "success" : "fail"));
            }
        });
    }


}
