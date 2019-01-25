package com.homepaas.sls.ui.order.servicemerchant;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.imchating.ImLoginActivity;
import com.homepaas.sls.ui.order.manage.OrderManageAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by mhy on 2017/8/26.
 *  非标订单 服务商家界面
 */

public class ServiceMerchantActivity extends CommonBaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.indicator)
    TabLayout indicator;
    @Bind(R.id.call_secretary)
    ImageView callSecretary;
    @Bind(R.id.viewpager)
    ViewPager viewpager;


    private ServiceWebFragment serviceWebFragment;
    private OrderManageAdapter adapter;
    private List<String> titleList = new ArrayList<>();
    private ServiceItem serviceItem;

    public static void start(Context context, ServiceItem serviceItem) {
        Intent intent = new Intent(context, ServiceMerchantActivity.class);
        intent.putExtra("serviceItem", serviceItem);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_merchant;
    }

    @Override
    protected void initView() {
        serviceItem = (ServiceItem) getIntent().getSerializableExtra("serviceItem");
        List<Fragment> fragments = new ArrayList<>();
        serviceWebFragment = ServiceWebFragment.newInstance(serviceItem.getDetailUrl());
        titleList.add("服务");
        fragments.add(serviceWebFragment);
        titleList.add("商家");
        fragments.add(MerchantFragment.newInstance(serviceItem,false,serviceItem.getServiceId()));

        adapter = new OrderManageAdapter(getSupportFragmentManager(), fragments, titleList);
        viewpager.setAdapter(adapter);
        indicator.setupWithViewPager(viewpager);
//        viewpager.setOffscreenPageLimit(2);
        indicator.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(indicator, 30, 30);
            }
        });
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDipp) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDipp, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {

            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            if (i == 0) {
                params.leftMargin = left;
            } else {
                params.rightMargin = right;
            }
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.back, R.id.call_secretary})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.call_secretary:
                //进入客服页面
                ImLoginActivity.start(this, serviceItem);
                break;
        }
    }

    public void loadNewUrl(String url) {
        if (serviceWebFragment != null) {
            serviceWebFragment.loadNewUrl(url);
        }
    }

    public void showLoginDialog(String url) {
        if (serviceWebFragment != null) {
            serviceWebFragment.showLoginDialog(url);
        }
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    @Override
    protected void retrieveData() {
        super.retrieveData();
        if (serviceWebFragment != null)
            serviceWebFragment.onSuccessToken(getToken());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (serviceWebFragment != null)
            serviceWebFragment.onActivityResult(requestCode, resultCode, data);
    }
}
