package com.homepaas.sls.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import com.homepaas.sls.R;
import com.runtimepermission.acp.Acp;
import com.runtimepermission.acp.AcpListener;
import com.runtimepermission.acp.AcpOptions;
import com.runtimepermission.acp.IBaseAcpListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YingJun.Xiong on 2016/10/28.
 * 权限公用类
 */
public class PermissionUtils {
    /**
     * 下载apk文件权限申请
     */
    public static void downLoadFilePermission(Context context, AcpListener acpListener) {
        try {
            Acp.getInstance(context).request(new AcpOptions.Builder()
                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .setDeniedMessage("检查更新需要文件存储权限")
                    .build(), acpListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestPhoneStrorageLocation(Context context, AcpListener acpListener) {
        try {
            Acp.getInstance(context).request(new AcpOptions.Builder()
                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE)
                    .setDeniedMessage("应用需要位置,文件存储,电话权限,请检查是否缺少")
                    .build(), acpListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void requestPhoneLocation(Context context, AcpListener acpListener) {
        try {
            Acp.getInstance(context).request(new AcpOptions.Builder()
                    .setPermissions(Manifest.permission.READ_PHONE_STATE)
                    .setDeniedMessage("应用需要电话权限")
                    .build(), acpListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 打电话权限 和读取手机通话记录申请
     */
    public static void callPhonePermission(final Context context, final AcpListener acpListener) {
        try {
            Acp.getInstance(context).request(new AcpOptions.Builder()
                    .setPermissions(Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG)
                    .setDeniedMessage("app需要电话权限")
                    .setRefusePermissionListener(new AcpOptions.OnRefusePermissionListener() {
                        @Override
                        public void onCallback(List<String> permissions) {
                            acpListener.onDenied(null);
                        }
                    })
                    .build(), acpListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照文件权限申请
     */
    public static void takeAPictureFilePermission(Context context, AcpListener acpListener) {
        try {
            Acp.getInstance(context).request(new AcpOptions.Builder()
                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .setDeniedMessage("拍照需要文件读写权限")
                    .build(), acpListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 二维码相机权限申请
     */
    public static void requestCameraPermission(Context context, IBaseAcpListener acpListener) {
        try {
            Acp.getInstance(context).request(new AcpOptions.Builder()
                    .setPermissions(Manifest.permission.CAMERA)
                    .setDeniedMessage(context.getResources().getString(R.string.app_name) + "需要相机权限,是否去设置")
                    .build(), acpListener);
        } catch (Exception e) {
            e.printStackTrace();
            acpListener.onRelease();
//            OtherDialogTools.showPermissionHint(context, "无法获取摄像头数据,请检查是否已经打开摄像头权限。", acpListener);
        }
    }


    /**
     * 定位权限申请
     */
    public static void requestLocationPermission(final Context context, AcpListener acpListener) {
        boolean isLocation = context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean isStorage = context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        List<String> permissions = new ArrayList<>();
        StringBuilder sb = new StringBuilder("需要");
        if (!isLocation) {
            sb.append("位置");
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!isStorage) {
            sb.append(isLocation ? "" : "、");
            sb.append("文件");
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
//        if (permissions.size() > 0 && CacheModel.getInstance().getShowLocationPermissionCount() == 0) {

        if (permissions.size() > 0) {
            sb.append("权限，是否去设置");
            try {
                Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(permissions.toArray(new String[]{}))
                        .setDeniedMessage(context.getResources().getString(R.string.app_name) + sb.toString())
                        .build(), acpListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            acpListener.onGranted();
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean gpsIsOpen(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            boolean network = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (gps || network) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
