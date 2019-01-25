package com.homepaas.sls.ui.newdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.GetAtStoreActivityEntity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CommonDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/2/13.
 */

public class ActionActRuleAdapter extends RecyclerView.Adapter<ActionActRuleAdapter.ActionActRuleView> {

    private static Context context;
    private LayoutInflater inflater;
    private List<GetAtStoreActivityEntity.ActivityNgDetail> list;

    public ActionActRuleAdapter(Context context) {
        this.context = context;
    }


    public void setList(List<GetAtStoreActivityEntity.ActivityNgDetail> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public ActionActRuleView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.pay_in_store_activity, parent, false);
        return new ActionActRuleView(view);
    }


    @Override
    public void onBindViewHolder(final ActionActRuleView holder, int position) {
        holder.bindDate(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ActionActRuleView extends RecyclerView.ViewHolder {
        @Bind(R.id.action_icon)
        ImageView actionIcon;
        @Bind(R.id.action_icon_frame)
        FrameLayout actionIconFrame;
        @Bind(R.id.action_description)
        TextView actionDescription;
        @Bind(R.id.help)
        ImageView help;
        @Bind(R.id.reduce_money)
        TextView reduceMoney;
        @Bind(R.id.action_indicator)
        ImageView actionIndicator;

        public ActionActRuleView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindDate(final GetAtStoreActivityEntity.ActivityNgDetail activityNgDetail) {
            actionDescription.setText(activityNgDetail.getTitle());
            reduceMoney.setVisibility(View.GONE);
            if (!activityNgDetail.getReturnType().isEmpty()) {
                actionIcon.setVisibility(View.VISIBLE);
                if (TextUtils.equals(activityNgDetail.getReturnType(), "0")) {
                    actionIcon.setImageResource(R.mipmap.mark_down1);
                    if(activityNgDetail.getBestRuleIndex()!=-1) {
                        if (!TextUtils.isEmpty(activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus())) {
                            reduceMoney.setVisibility(View.VISIBLE);
                            reduceMoney.setText("- ¥" + activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus());
                        }
                    }
                } else {
                    actionIcon.setImageResource(R.mipmap.cashback1);
                }
            } else {
                actionIcon.setVisibility(View.INVISIBLE);
            }
            boolean isStatfied = activityNgDetail.isSatified();
            actionIndicator.setImageResource(isStatfied ? R.mipmap.accord_with : R.mipmap.inconformity);
            help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CommonDialog.Builder()
                            .showAction(false)
                            .setContent(activityNgDetail.getHelp())
                            .setTitle(activityNgDetail.getTitle())
                            .create().show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
                }
            });
        }
    }
}
