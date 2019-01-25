package com.homepaas.sls.ui.merchantservice;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class GalleryAdapter extends PagerAdapter {
    List<View> views;

    public GalleryAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        if (views.size()>2)
        return Integer.MAX_VALUE;
        else return views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get((position+views.size())%views.size());
        if (view.getParent() != null)container.removeView(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(views.get((position+views.size())%views.size()));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
