package com.homepaas.sls.ui.redpacket.newRedpacket;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.data.network.Url;
import com.homepaas.sls.di.component.DaggerCouponContentsComponent;
import com.homepaas.sls.di.module.CouponContentsModule;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.personalcenter.more.GeneralWebViewActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.newRedPacketAdapter.NewRedPacketAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class NewRedPacketActivity extends CommonBaseActivity {
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.indicator)
    TabLayout indicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    private NewRedPacketAdapter newRedPacketAdapter;

    @Override
    protected void onDestroy() {
        if (refreshLayout != null) {
            refreshLayout.stopRefresh();
            refreshLayout.destory();
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_red_packet;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        viewpager.setOffscreenPageLimit(1);
        init();
    }

    @Override
    protected void initData() {

    }

    public void init(){
        fragmentList.add(NewNoUsedRedPacketFragment.newInstance());
        fragmentList.add(NewUsedRedPacketFragment.newInstance());
        fragmentList.add(NewOutofdateRedPacketFragment.newInstance());

        titleList.add("未使用");
        titleList.add("已使用");
        titleList.add("已过期");

        newRedPacketAdapter = new NewRedPacketAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewpager.setAdapter(newRedPacketAdapter);
        indicator.setupWithViewPager(viewpager);
        setIndicator(this,indicator,15,15);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coupon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.coupon_about){
            Intent intent = new Intent(this,GeneralWebViewActivity.class);
            intent.putExtra(Navigator.WEB_VIEW_TITLE,"红包说明");
            intent.putExtra(Navigator.WEB_VIEW_URL, Url.HTM_BASE_URL_DEFAULT+"yhqsm.html");
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerCouponContentsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .couponContentsModule(new CouponContentsModule())
                .build()
                .inject(this);
    }

    public void setIndicator(Context context, TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) ( context.getResources().getDisplayMetrics().density * leftDip);
        int right = (int) ( context.getResources().getDisplayMetrics().density * rightDip);

        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
