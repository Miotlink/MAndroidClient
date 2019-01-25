package com.homepaas.sls.ui.widget;

import android.view.ViewGroup;

/**
 * Date: 2016/12/13.
 * Author: fmisser
 * Description: 循环的ViewPager, 和 {@link LoopViewPager} 配合使用
 */


public abstract class LoopPagerAdapter extends RecyclePagerAdapter {

    @Override
    public int getCount() {
        if (getItemCount() == 0) {
            return 0;
        } else {
//            return Integer.MAX_VALUE;
            return 1000;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //转化成数据item的position
        int itemPosition = position % getItemCount();
        return super.instantiateItem(container, itemPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int itemPosition = position % getItemCount();
        super.destroyItem(container, itemPosition, object);
    }

//    @Override
//    public void notifyDataSetChanged() {
//        super.notifyDataSetChanged();
//    }
}
