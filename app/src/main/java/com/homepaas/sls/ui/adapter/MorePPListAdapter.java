package com.homepaas.sls.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.MorePPEntity;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;

/**
 * Created by mhy on 2017/8/26.
 */

public class MorePPListAdapter extends BaseRecyclerAdapter<MorePPEntity> {
    public MorePPListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.morepplist_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, MorePPEntity item) {
        TextView textView = holder.getTextView(R.id.tv_more_item);
        textView.setText(item.getName());
        if (position == getData().size() - 1) {
            holder.setVisibility(R.id.lin_bg, View.GONE);
        } else {
            holder.setVisibility(R.id.lin_bg, View.VISIBLE);
        }
    }
}
