package com.homepaas.sls.ui.personalcenter.collection;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.event.ColleActionTabEvent;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.manage.OrderManageAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by mhy on 2017/8/26.
 * 我的收藏
 */

public class NewCollectionActivity extends CommonBaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.indicator)
    TabLayout indicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private OrderManageAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_collection;
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

        List<String> titleList = new ArrayList<>();
        titleList.add("工人收藏");
        titleList.add("商户收藏");

        fragments.add(NewWorkerColleactionFragment.newInstance());
        fragments.add(NewMerchantColleactionFragment.newInstance());

        adapter = new OrderManageAdapter(getSupportFragmentManager(), fragments, titleList);
        viewpager.setAdapter(adapter);
        indicator.setupWithViewPager(viewpager);
    }

    @Override
    protected void initData() {

    }


    /**
     * 修改tab文案
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTabTxt(ColleActionTabEvent event) {
        StringBuffer stringBuffer = new StringBuffer();
        if (event.getTabIndex() == 0) {
            stringBuffer.append("工人收藏");
            stringBuffer.append("(");
            stringBuffer.append(event.getTabNum());
            stringBuffer.append(")");
            TabLayout.Tab tab = indicator.getTabAt(0);
            if (tab != null) {
                tab.setText(stringBuffer.toString());
            }
        } else {
            stringBuffer.append("商户收藏");
            stringBuffer.append("(");
            stringBuffer.append(event.getTabNum());
            stringBuffer.append(")");
            TabLayout.Tab tab = indicator.getTabAt(1);
            if (tab != null) {
                tab.setText(stringBuffer.toString());
            }
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
