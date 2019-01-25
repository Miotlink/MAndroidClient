package com.homepaas.sls.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.AccountComponent;
import com.homepaas.sls.di.component.DaggerAccountComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.ui.adapter.ItemsPagerAdapter;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;

import butterknife.Bind;


public class AccountActivity extends CommonBaseActivity implements PageFragment.OnRefreshEndLister, HasComponent<AccountComponent> {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.tab)
    TabLayout mTab;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Bind(R.id.refreshLayout)
    HeaderViewLayout mHeaderViewLayout;

    private PageFragment[] mFragments = new PageFragment[2];

    private AccountComponent mComponent;

    private HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.SimpleOnRefreshListener() {
        @Override
        public void onRefresh() {
            int i = mViewPager.getCurrentItem();
            mFragments[i].refresh();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        mFragments[0] = new AccountBalanceFragment();
        mFragments[1] = new PayDetailFragment();
        ItemsPagerAdapter pagerAdapter = new ItemsPagerAdapter(getSupportFragmentManager(), mFragments);
        pagerAdapter.setTitles(new String[]{"账户余额", "支付明细"});
        mViewPager.setAdapter(pagerAdapter);
        mTab.setupWithViewPager(mViewPager);
        mHeaderViewLayout.setOnRefreshListener(mOnRefreshListener);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        mComponent = DaggerAccountComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build();

    }

/*
  暂时不做充值功能
  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account,menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.recharge:
                startActivity(new Intent(this, RechargeActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public AccountComponent getComponent() {
        return mComponent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHeaderViewLayout != null) {
            mHeaderViewLayout.stopRefresh();
            mHeaderViewLayout.destory();
        }
    }

    @Override
    public void onRefreshEnd() {
        mHeaderViewLayout.stopRefresh();
    }
}
