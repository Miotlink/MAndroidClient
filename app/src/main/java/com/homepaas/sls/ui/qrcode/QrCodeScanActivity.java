package com.homepaas.sls.ui.qrcode;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.homepaas.sls.R;
import com.homepaas.sls.data.network.UrlUtils;
import com.homepaas.sls.di.component.DaggerQrCodeScanComponent;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.presenter.QrScanPresenter;
import com.homepaas.sls.mvp.view.QrScanView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.QrCodeScanView;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import cn.bingoogolapple.qrcode.core.QRCodeView;

public class QrCodeScanActivity extends CommonBaseActivity implements QRCodeView.Delegate, QrScanView {

    private static final String TAG = "QrCodeScanActivity";

    private static final boolean DEBUG = false;

    private QrCodeScanView scannerView;


    @Inject
    QrScanPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code_scan;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scannerView = (QrCodeScanView) findViewById(R.id.qr_code_scanner);
        //noinspection ConstantConditions
        scannerView.setDelegate(this);
        presenter.setView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerQrCodeScanComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //scannerView.startCamera();
        scannerView.startSpot();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        if (DEBUG)
            Log.d(TAG, "onScanQRCodeSuccess: " + result);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
//        startActivity(intent);
//        finish();

        //扫描工人/商户二维码
        boolean isUpload = true;
        if (UrlUtils.isUrl(result)) {
            String[] results = result.split("//");

            if (results[1].startsWith(UrlUtils.baseUrl())){
                String[] strings = results[1].split("\\?");

                Map<String,String> codesMap = new TreeMap<>();
                String[] codes = strings[1].split("&");
                for (String string : codes){
                    String[] code = string.split("=");
                    codesMap.put(code[0],code[1]);
                }

                if (codesMap.get("fn").equals("1")){
                    //type=1表示工人或者type=2表示商户,id=xxx表示工人或者商户id
                    switch (codesMap.get("type")){
                        case "1":
                            mNavigator.showMerchantWorkerDetail(this, Constant.SERVICE_TYPE_WORKER, codesMap.get("id"));
//                            mNavigator.showWorkerDetail(this, codesMap.get("id"));
                            break;
                        case "2":
//                            mNavigator.showBusinessDetail(this, codesMap.get("id"));
                            mNavigator.showMerchantWorkerDetail(this, Constant.SERVICE_TYPE_BUSINESS, codesMap.get("id"));
                            break;
                    }
                    isUpload = false;
                    finish();
                } else {
                    isUpload = true;
                }
            }
        }

        if (isUpload) {
            presenter.uploadUrl(result);
        }
    }



    @Override
    public void showError(Throwable e) {
        super.showError(e);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "onScanQRCodeOpenCameraError: ");
    }

    @Override
    public void onResult(PushInfo pushInfo) {
        if (DEBUG)
            Log.d(TAG, "onResult: ");
        pushUtil.startInternalView(this, pushInfo);
        finish();
    }
}
