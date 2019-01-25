package com.homepaas.sls.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * on 2015/12/25 0025
 *
 * @author zhudongjie .
 */
public class ItemsPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments;

    private String[] mTitles;

    public ItemsPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        mFragments = fragments;
    }

    public void setTitles(String[] titles) {
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
        if (mTitles != null) {
            return mTitles[position];
        } else {
            return super.getPageTitle(position);
        }
    }
}
