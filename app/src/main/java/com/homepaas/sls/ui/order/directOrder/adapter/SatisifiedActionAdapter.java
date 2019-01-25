package com.homepaas.sls.ui.order.directOrder.adapter;

import android.content.Context;
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
 * Created by Sherily on 2017/2/22.
 */

public class SatisifiedActionAdapter extends RecyclerView.Adapter<SatisifiedActionAdapter.ActionViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ActivityNgInfoNew.ActivityNgDetail> activityNgDetailList;

    public void setData(List<ActivityNgInfoNew.ActivityNgDetail> activityNgDetailList){
        this.activityNgDetailList = activityNgDetailList;
        notifyDataSetChanged();
    }
    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            context = parent.getContext();
            layoutInflater = LayoutInflater.from(context);
        }
        View view = layoutInflater.inflate(R.layout.list_item_action, parent, false);
        return new ActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActionViewHolder holder, int position) {
        holder.bind(activityNgDetailList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return activityNgDetailList == null ? 0 : activityNgDetailList.size();
    }

    public static class ActionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.available_action_description)
        TextView availableActionDescription;
        @Bind(R.id.discount_description)
        TextView discountDescription;
        public ActionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(ActivityNgInfoNew.ActivityNgDetail activityNgDetail){
            availableActionDescription.setText(activityNgDetail.getTitle());
            String minus = activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus();
            discountDescription.setText("- ¥" + minus);
        }
    }
}
