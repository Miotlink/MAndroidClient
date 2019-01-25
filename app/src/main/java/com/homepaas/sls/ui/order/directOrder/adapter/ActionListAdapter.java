package com.homepaas.sls.ui.order.directOrder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CommonDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CJJ on 2016/11/23.
 * 活动列表Adapter,但是目前活动并不是统一的列表，只是固定的Special和Promotion两种两条活动
 */

public class ActionListAdapter extends RecyclerView.Adapter<ActionListAdapter.Holder> {

    private Context context;
    private ActivityNgInfoNew action;
    private List<ActivityNgInfoNew.ActivityNgDetail> activityNgDetailList;
    private LayoutInflater inflater;
    private float sum = 0;

    public void setAction(ActivityNgInfoNew action) {
        this.action = action;
        if (action != null && action.isAvailableActivity() ) {
           this.activityNgDetailList = action.getNeedActivity("-1");
        }
        this.sum = 0;
        setSelection(sum);
        notifyDataSetChanged();
    }

    private void setSelection(float sum){
        if (action != null){
            action.isPromotionSatisfied(sum);
            action.isSpecialSatisfied(sum);
        }

    }
    public void setSum(float sum) {
        this.sum = sum;
        setSelection(sum);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null)
        {
            context = parent.getContext();
            inflater = LayoutInflater.from(context);
        }
        return new Holder(inflater.inflate(R.layout.list_item_string, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(activityNgDetailList.get(holder.getAdapterPosition()));
    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }

    @Override
    public int getItemCount() {
//        int count = 0;
//        if (action != null && action.isAvailableActivity() ) {
//                count = action.getNeedActivity("-1").size();
////            if (action.isPromotionAvailable())
////                count++;
//        }
        return activityNgDetailList == null ? 0 : activityNgDetailList.size();
    }

    class Holder extends RecyclerView.ViewHolder implements Action {

        @Bind(R.id.action_icon)
        ImageView actionIcon;
        @Bind(R.id.action_description)
        TextView actionDescription;
        @Bind(R.id.help)
        ImageView help;
        @Bind(R.id.action_indicator)
        ImageView actionIndicator;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(ActivityNgInfoNew.ActivityNgDetail activityNgDetail) {
            actionDescription.setText(activityNgDetail.getTitle());
            help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CommonDialog.Builder()
                            .showAction(false)
                            .setContent(activityNgDetailList.get(getAdapterPosition()).getHelp())
                            .setTitle(activityNgDetailList.get(getAdapterPosition()).getTitle())
                            .create().show(((CommonBaseActivity)context).getSupportFragmentManager(),null);
                }
            });
            if (TextUtils.equals("0",activityNgDetail.getReturnType())) {
//                boolean specialSatisfied = action.isSpecialSatisfied(sum);
                boolean isSatisfied = activityNgDetail.isSatified();
                boolean isFullcategory = activityNgDetail.isFullcategory();
                actionIcon.setImageResource(isFullcategory ? R.mipmap.mark_down2:R.mipmap.mark_down1);
                actionIndicator.setImageResource(isSatisfied ? R.mipmap.accord_with:R.mipmap.inconformity);

            } else if (TextUtils.equals("1",activityNgDetail.getReturnType())){
//                boolean specialSatisfied = action.isPromotionSatisfied(sum);
                boolean isSatisfied = activityNgDetail.isSatified();
                boolean isFullcategory = activityNgDetail.isFullcategory();
                actionIcon.setImageResource(isFullcategory ? R.mipmap.cashback2:R.mipmap.cashback1);
                actionIndicator.setImageResource(isSatisfied?R.mipmap.accord_with:R.mipmap.inconformity);

            }

        }
    }


    interface Action {
        void bind(ActivityNgInfoNew.ActivityNgDetail activityNgDetail);
    }
}
