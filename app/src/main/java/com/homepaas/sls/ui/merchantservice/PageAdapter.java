package com.homepaas.sls.ui.merchantservice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PageAdapter extends FragmentPagerAdapter{

    List<Fragment> fragments;
    List<String> titles;
    public PageAdapter(List<Fragment> fragments, List<String> titles, FragmentManager manager) {
        super(manager);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
