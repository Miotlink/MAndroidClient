package com.homepaas.sls.ui.order.directOrder.adapter;

import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ActivityInfo;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.ui.widget.KeywordUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/3/29.
 */

public class OrderActivityAdapter extends RecyclerView.Adapter<OrderActivityAdapter.OrderActivityView> {

    private List<ActivityInfo> activityInfoList;
    private LayoutInflater layoutInflater;

    public void setActivityList(List<ActivityInfo> activityInfoList) {
        this.activityInfoList = activityInfoList;
        notifyDataSetChanged();
    }

    @Override
    public OrderActivityView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_order_activity, parent, false);
        return new OrderActivityView(view);
    }


    @Override
    public void onBindViewHolder(OrderActivityView holder, int position) {
        ActivityInfo activityInfo=activityInfoList.get(holder.getAdapterPosition());
        holder.activityTitle.setText(KeywordUtil.matcherActivity(Color.parseColor("#f56165"),activityInfo.getTitle()+"满"+ KeywordUtil.determine(activityInfo.getUpper())+"减"+ KeywordUtil.determine(activityInfo.getMinus())));

    }

    @Override
    public int getItemCount() {
        return activityInfoList == null ? 0 : activityInfoList.size();
    }

    public static class OrderActivityView extends RecyclerView.ViewHolder {
        @Bind(R.id.activity_title)
        TextView activityTitle;

        public OrderActivityView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
