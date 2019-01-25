package com.homepaas.sls.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerTestComponent;
import com.homepaas.sls.newmvp.contract.TestContract;
import com.homepaas.sls.newmvp.presenter.HomePresenter;
import com.homepaas.sls.ui.common.activity.CommonMvpBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by mhy on 2017/7/24.
 */

public class TestActivity extends CommonMvpBaseActivity<TestContract.Presenter> implements TestContract.View {
    @Bind(R.id.tv_data)
    TextView tvData;
    /**
     * 作为页面容器的ViewPager
     */
    ViewPager mViewPager;
    /**
     * 页面集合
     */
    List<Fragment> fragmentList;
    @Bind(R.id.fl_content)
    FrameLayout flContent;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected TestContract.Presenter getPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity;
    }

    @Override
    protected void initView() {
//        replaceFragment(R.id.fl_content, TestAFragment.newInstance());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(TestAFragment.newInstance());
        fragmentList.add(TestBFragment.newInstance());
        fragmentList.add(TestCFragment.newInstance());
        fragmentList.add(TestDFragment.newInstance());
        mViewPager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
    }

    /**
     * 定义自己的ViewPager适配器。
     * 也可以使用FragmentPagerAdapter。关于这两者之间的区别，可以自己去搜一下。
     */
    class MyFrageStatePagerAdapter extends FragmentPagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerTestComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        tvData.setText("默认值");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    public void initDataSuccess(String json) {
        showMessage(json);
    }

    @Override
    public void showLoading() {

    }
}
