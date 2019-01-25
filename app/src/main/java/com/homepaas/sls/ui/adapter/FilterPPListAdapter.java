package com.homepaas.sls.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessOrderServiceListEntity;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;

/**
 * Created by mhy on 2017/8/26.
 */

public class FilterPPListAdapter extends BaseRecyclerAdapter<BusinessOrderServiceListEntity.ServiceTag> {

    public FilterPPListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.filter_pplist_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, BusinessOrderServiceListEntity.ServiceTag item) {
        TextView textView = holder.getTextView(R.id.tv_type);
        textView.setText(item.getName());
        if (item.isSelect()) {
            textView.setTextColor(mContext.getResources().getColor(R.color.filter_item_select));
            if (!TextUtils.isEmpty(item.getId()))
                holder.setVisibility(R.id.img_select, View.VISIBLE);
            else
                holder.setVisibility(R.id.img_select, View.GONE);
        } else {
            holder.setVisibility(R.id.img_select, View.GONE);
            textView.setTextColor(mContext.getResources().getColor(R.color.filter_item));
        }
    }

    public void setCurrentSelectPos(int pos) {
        for (int i = 0; i < mData.size(); i++) {
            BusinessOrderServiceListEntity.ServiceTag filterTypeEntity = mData.get(i);
            if (pos == i)
                filterTypeEntity.setSelect(true);
            else
                filterTypeEntity.setSelect(false);
        }
        notifyDataSetChanged();
    }
}
