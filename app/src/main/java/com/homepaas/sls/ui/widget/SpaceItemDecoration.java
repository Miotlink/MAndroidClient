package com.homepaas.sls.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by CJJ on 2016/7/29.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        outRect.right = space;
        if (parent.getChildLayoutPosition(view) == lastItem){
            outRect.right = 0;
        }


//        outRect.right = space;
//        outRect.bottom = space;
//        //for every child's left margin
//        outRect.top = space;

    }
}