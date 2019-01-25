package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by JWC on 2017/7/10.
 */

public class QrCodeScanView extends ZXingView {
    public QrCodeScanView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void stopCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mPreview.stopCameraPreview();
            mPreview.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
    }
}
