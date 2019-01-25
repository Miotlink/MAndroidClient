package com.homepaas.sls.ui.order.directOrder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/7/19.
 */

public class ClientOrderActivityAdapter extends RecyclerView.Adapter<ClientOrderActivityAdapter.ClientOrderActivityView> {
    private LayoutInflater layoutInflater;
    private List<ActivityNgInfoNew.ActivityNgDetail> activityNgDetailList;
    public void setActivityNgDetailList(List<ActivityNgInfoNew.ActivityNgDetail> activityNgDetailList){
        this.activityNgDetailList=activityNgDetailList;
        notifyDataSetChanged();
    }

    @Override
    public ClientOrderActivityView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_client_order_activity, parent, false);
        return new ClientOrderActivityView(view);
    }

    @Override
    public void onBindViewHolder(ClientOrderActivityView holder, int position) {
        holder.bind(activityNgDetailList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return activityNgDetailList==null?0:activityNgDetailList.size();
    }

    public static class ClientOrderActivityView extends RecyclerView.ViewHolder {
        @Bind(R.id.activity_title)
        TextView activityTitle;
        @Bind(R.id.activity_amount)
        TextView activityAmount;
        public ClientOrderActivityView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bind(ActivityNgInfoNew.ActivityNgDetail activityNgDetail){
            activityTitle.setText(activityNgDetail.getTitle());
            String minus = activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus();
            activityAmount.setText("- ¥" + minus);
        }
    }
}
