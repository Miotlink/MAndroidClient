package com.homepaas.sls.ui.newdetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sherily on 2017/2/9.
 */

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ActionViewHolder> {


    private LayoutInflater inflater;
    private List<ActivityNgInfoNew.ActivityNgDetail> datas;

    public void setDatas(List<ActivityNgInfoNew.ActivityNgDetail> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.detail_action_item_layout, parent, false);
        return new ActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActionViewHolder holder, int position) {
        holder.bind(datas.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class ActionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.name)
        TextView name;

        public ActionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ActivityNgInfoNew.ActivityNgDetail activityNgInfo) {
            /**
             * 目前只接收0，1，2三种活动
             * 0：减免
             * 1：返充
             * 2：红包；
             * 3：充值折扣
             * 4：充值送余额
             * 5：充值送红包
             * 6：App引流红包
             */

            if (TextUtils.equals("0", activityNgInfo.getReturnType()) || TextUtils.equals("1", activityNgInfo.getReturnType()) || TextUtils.equals("2", activityNgInfo.getReturnType())) {
                name.setText(activityNgInfo.getTitle() + "：" + activityNgInfo.getHelp());
                if (TextUtils.equals("0", activityNgInfo.getReturnType())) {
                    icon.setImageResource(activityNgInfo.isFullcategory() ? R.mipmap.mark_down2:R.mipmap.mark_down1);
//                    name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.jian, 0, 0, 0);
                } else if (TextUtils.equals("1", activityNgInfo.getReturnType())) {
                    icon.setImageResource(activityNgInfo.isFullcategory() ? R.mipmap.cashback2:R.mipmap.cashback1);
//                    name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.fan, 0, 0, 0);
                } else if (TextUtils.equals("2", activityNgInfo.getReturnType())) {
                    icon.setImageResource(activityNgInfo.isFullcategory() ? R.mipmap.cashback2:R.mipmap.cashback1);
                }
            }


        }
    }
}
