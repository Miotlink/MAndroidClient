package com.homepaas.sls.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.homepaas.sls.R;
import com.homepaas.sls.event.EventChangeViewPagerStatus;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.manage.OrderManageAdapter;
import com.homepaas.sls.ui.order.select.NearbyServiceOrWorkerFragment;
import com.homepaas.sls.ui.order.select.RecentlyServiceOrWorkerFragment;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by mhy on 2017/8/26.
 * 选择服务商户/工人
 */

public class SelectServiceProviderOrWorkerActivity extends CommonBaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.btn_app_recommend)
    Button btnAppRecommend;
    @Bind(R.id.indicator)
    TabLayout indicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;


    private OrderManageAdapter adapter;
    private List<String> titleList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_service_provider_or_woker;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<Fragment> fragments = new ArrayList<>();
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(StaticData.ADDRESS);
        String stringExtra1 = intent.getStringExtra(StaticData.SERVICE_ID);

        titleList.add("最近");
        fragments.add(RecentlyServiceOrWorkerFragment.newInstance(stringExtra, stringExtra1));

        titleList.add("附近商户/工人");
        fragments.add(NearbyServiceOrWorkerFragment.newInstance(stringExtra, stringExtra1));

        adapter = new OrderManageAdapter(getSupportFragmentManager(), fragments, titleList);
        viewpager.setAdapter(adapter);
        indicator.setupWithViewPager(viewpager);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_app_recommend)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra(StaticData.USER_ID, "");
        setResult(Activity.RESULT_OK, new Intent());
        ActivityCompat.finishAfterTransition(this);
    }

    @Subscribe
    public void changeSelectFragment(EventChangeViewPagerStatus eventChangeViewPagerStatus) {
        //最近这个界面没有工人时并没有1s之后跳到附近商户下
        viewpager.setCurrentItem(viewpager.getChildCount() - 1);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
