package com.homepaas.sls.ui.order.directOrder.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.KdApiSearchResponseTrace;
import com.homepaas.sls.domain.entity.KdTrackInfoResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/6/5.
 */

public class ExpressOrderTrackingAdapter extends RecyclerView.Adapter<ExpressOrderTrackingAdapter.ExpressOrderTrackingView> {
    public LayoutInflater layoutInflater;
    private List<KdApiSearchResponseTrace> list;

    public void setList(List<KdApiSearchResponseTrace> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public ExpressOrderTrackingView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_express_order_tracking, parent, false);
        return new ExpressOrderTrackingView(view);
    }

    @Override
    public void onBindViewHolder(ExpressOrderTrackingView holder, int position) {
        KdApiSearchResponseTrace kdApiSearchResponseTrace = list.get(holder.getAdapterPosition());
        if(kdApiSearchResponseTrace!=null) {
            String[] time=null;
            if(!TextUtils.isEmpty(kdApiSearchResponseTrace.getAcceptTime())) {
                time = kdApiSearchResponseTrace.getAcceptTime().split(" ");
            }
            if(time!=null&&time.length==2) {
                holder.hourTime.setText(time[1]);
                holder.yuarTime.setText(time[0]);
            }
            holder.acceptStation.setText(kdApiSearchResponseTrace.getAcceptStation());
            if(list.size()==1){
                holder.upperIcon.setVisibility(View.INVISIBLE);
                holder.inIcon.setVisibility(View.VISIBLE);
                holder.lowerIcon.setVisibility(View.INVISIBLE);
                holder.inIcon.setBackgroundResource(R.mipmap.client_v3_1_0_ic_blue_dot);
            }else{
                if(holder.getAdapterPosition()==0){
                    holder.upperIcon.setVisibility(View.INVISIBLE);
                    holder.inIcon.setVisibility(View.VISIBLE);
                    holder.lowerIcon.setVisibility(View.VISIBLE);
                    holder.inIcon.setBackgroundResource(R.mipmap.client_v3_1_0_ic_blue_dot);
                }else if(holder.getAdapterPosition()>0&&holder.getAdapterPosition()<list.size()-1){
                    holder.upperIcon.setVisibility(View.VISIBLE);
                    holder.inIcon.setVisibility(View.VISIBLE);
                    holder.lowerIcon.setVisibility(View.VISIBLE);
                    holder.inIcon.setBackgroundResource(R.mipmap.client_v3_1_0_ic_gray_dot);
                }else if(holder.getAdapterPosition()==list.size()-1){
                    holder.upperIcon.setVisibility(View.VISIBLE);
                    holder.inIcon.setVisibility(View.VISIBLE);
                    holder.lowerIcon.setVisibility(View.INVISIBLE);
                    holder.inIcon.setBackgroundResource(R.mipmap.client_v3_1_0_ic_gray_dot);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ExpressOrderTrackingView extends RecyclerView.ViewHolder {
        @Bind(R.id.hour_time)
        TextView hourTime;
        @Bind(R.id.yuar_time)
        TextView yuarTime;
        @Bind(R.id.upper_icon)
        ImageView upperIcon;
        @Bind(R.id.in_icon)
        ImageView inIcon;
        @Bind(R.id.lower_icon)
        ImageView lowerIcon;
        @Bind(R.id.accept_station)
        TextView acceptStation;
        public ExpressOrderTrackingView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
