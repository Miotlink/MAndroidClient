package com.homepaas.sls.ui.footcard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.homepaas.sls.domain.entity.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */

public class FootCardAdapter extends FragmentStatePagerAdapter {

    private List<FootCardFragment> footCardFragments;
    private List<IService> serviceList;

    public void setData(List<IService> serviceList){
        this.serviceList = serviceList;
//        footCardFragments.clear();
//        for (int i = 0; i < serviceList.size(); i++){
//            footCardFragments.add(FootCardFragment.newInstance());
//        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE ;
    }

    public FootCardAdapter(FragmentManager fm) {
        super(fm);
        footCardFragments = new ArrayList<>();
        serviceList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        FootCardFragment footCardFragment = FootCardFragment.newInstance(serviceList.get(position));
//        footCardFragments.get(position).setData(serviceList.get(position));
        return /*footCardFragments.get(position)*/footCardFragment;
    }

    @Override
    public int getCount() {
        return serviceList.size();
    }
}
