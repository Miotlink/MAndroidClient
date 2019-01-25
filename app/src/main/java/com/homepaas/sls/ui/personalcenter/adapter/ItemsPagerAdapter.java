package com.homepaas.sls.ui.personalcenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 我的收藏列表适配器
 *
 * @author zhudongjie .
 */
public class ItemsPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments;

    private String[] mTitles ;

    public ItemsPagerAdapter(FragmentManager fm, Fragment[] fragments,String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
