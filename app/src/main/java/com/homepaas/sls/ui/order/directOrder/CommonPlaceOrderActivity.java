package com.homepaas.sls.ui.order.directOrder;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.util.StaticData;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/3/30.
 * 大佬要求写两个类，然后，接口获取两次，代码重写两次。。。。。
 */

public class CommonPlaceOrderActivity extends CommonBaseActivity {

    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    private String curLongtitude;
    private String curLatitude;
    private String serviceId;
    private String serviceName;
    private String isActivity;
    //二级商品id
    private String secondLevelServiceID;
    private CommonAppPreferences commonAppPreferences;

    public static void start(Context context, String serviceId, String serviceName, String isActivity, String merchantId, String merchantName, String secondLevel, String providerUserType, String secondLevelServiceID) {
        Intent intent = new Intent(context, CommonPlaceOrderActivity.class);
        intent.putExtra(StaticData.SERVICE_ID, serviceId);
        intent.putExtra(StaticData.SERVICE_NAME, serviceName);
        intent.putExtra(StaticData.IS_ACTIVITY, isActivity);
        intent.putExtra(StaticData.MERCHANT_ID, merchantId);
        intent.putExtra(StaticData.MERCHANT_NAME, merchantName);
        intent.putExtra(StaticData.SECOND_LEVEL, secondLevel);
        intent.putExtra(StaticData.PROVIDER_USER_TYPE, providerUserType);
        intent.putExtra(StaticData.SECOND_LEVEL_SERVICE_ID, secondLevelServiceID);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_place_order;
    }

    @Override
    protected void initView() {
        commonAppPreferences = new CommonAppPreferences(this);
        Intent intent = getIntent();
        serviceId = intent.getStringExtra(StaticData.SERVICE_ID);
        serviceName = intent.getStringExtra(StaticData.SERVICE_NAME);
        isActivity = intent.getStringExtra(StaticData.IS_ACTIVITY);
        String merchantId = intent.getStringExtra(StaticData.MERCHANT_ID);
        String merchantName = intent.getStringExtra(StaticData.MERCHANT_NAME);
        String secondLevel = intent.getStringExtra(StaticData.SECOND_LEVEL);
        String providerUserType = intent.getStringExtra(StaticData.PROVIDER_USER_TYPE);
        secondLevelServiceID = intent.getStringExtra(StaticData.SECOND_LEVEL_SERVICE_ID);
        title.setText(serviceName);
        curLongtitude = commonAppPreferences.getLongitude();
        curLatitude = commonAppPreferences.getLatitude();
        //即使列表只有单个选项，列表也要显示单个选项
        showFragment(R.id.fragment_container, SubsetOrderFragment.newInstance(serviceId, isActivity, curLongtitude, curLatitude, merchantId, merchantName, secondLevel, providerUserType));
    }


    public void setTitle(String string) {
        if (!TextUtils.isEmpty(string))
            title.setText(string);
    }

    /**
     * 返回二级商品的id
     */
    public String getServiceId() {
        return secondLevelServiceID;
    }

    @Override
    protected void initData() {

    }

//    @Override
//    protected void initializeInjector() {
//        DaggerPlaceOrderComponent.builder()
//                .applicationComponent(getApplicationComponent())
//                .cameraModule(new CameraModule(this))
//                .build()
//                .inject(this);
//    }

    @OnClick({R.id.back})
    void back() {
        finish();
    }
}
