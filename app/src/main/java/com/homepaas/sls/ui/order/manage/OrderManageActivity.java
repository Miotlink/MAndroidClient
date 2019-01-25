package com.homepaas.sls.ui.order.manage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasBuilder;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.DaggerOrderComponent;
import com.homepaas.sls.di.component.DaggerOrderComponent.Builder;
import com.homepaas.sls.di.component.OrderComponent;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.common.fragment.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class OrderManageActivity extends CommonBaseActivity implements HasComponent<OrderComponent>, HasBuilder<Builder> {

    @Bind(R.id.indicator)
    TabLayout indicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    private OrderManageAdapter adapter;
    private OrderComponent mComponent;
    private DaggerOrderComponent.Builder builder;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        viewpager.setOffscreenPageLimit(4);
        init();
    }

    @Override
    protected void initData() {

    }


    private void init() {
        fragmentList.add(OrderAllFragment.newInstance());
        fragmentList.add(OrderToTakeFragment.newInstance());
        fragmentList.add(OrderToPayFragment.newInstance());
        fragmentList.add(OrderToConfirmFragment.newInstance());
        fragmentList.add(OrderToEvaluateFragment.newInstance());
        titleList.add("全部");
        titleList.add("待接单");
        titleList.add("待付款");
        titleList.add("待确认");
        titleList.add("待评价");
        adapter = new OrderManageAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(adapter);
        indicator.setupWithViewPager(viewpager);
    }

    @Override
    public OrderComponent getComponent() {

        return mComponent;
    }


    @Override
    public Builder getBuilder() {
        Builder builder = DaggerOrderComponent.builder()
                .applicationComponent(getApplicationComponent());
        return builder;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    @Override
    protected void notifyNetWorkChange(boolean isConnected) {
        if (isConnected) {

            Fragment fragment = fragmentList.get(viewpager.getCurrentItem());
            if (fragment instanceof BaseListFragment)
            {
                BaseListFragment baseListFragment = (BaseListFragment) fragment;
                baseListFragment.notifyNetWorkChange(isConnected);
            }
        }
    }
}
