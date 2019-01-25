package com.homepaas.sls.ui.widget;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class CallPhoneUtils {
    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;
    private static  String mCallingPhone;
    private static  String mTitle;
    // 拨打电话

    public static void dial(Context context, Fragment fragment, String phone, String title) {
        List<String> permissions = new ArrayList<>(2);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //Service 中无法回调onRequestPermissionsResult，提前请求
            permissions.add(Manifest.permission.READ_CALL_LOG);
        }
        if (permissions.isEmpty()) {
            NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title);
            serviceFragment.show(fragment.getFragmentManager(), null);
        } else {
            mCallingPhone = phone;
            mTitle = title;
            fragment.requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        }
    }
}
