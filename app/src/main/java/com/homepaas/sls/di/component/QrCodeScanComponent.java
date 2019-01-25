package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.QrScanModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.qrcode.QrCodeScanActivity;

import dagger.Component;

/**
 * on 2016/7/12 0012
 *
 * @author zhudongjie
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = QrScanModule.class)
public interface QrCodeScanComponent {

    void inject(QrCodeScanActivity activity);
}
