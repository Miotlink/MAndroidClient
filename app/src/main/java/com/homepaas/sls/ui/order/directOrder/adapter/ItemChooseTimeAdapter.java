package com.homepaas.sls.ui.order.directOrder.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.embedded_class.Time;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/3/29.
 */

public class ItemChooseTimeAdapter extends RecyclerView.Adapter<ItemChooseTimeAdapter.ItemChooseTimeView> {

    private LayoutInflater layoutInflater;
    private List<Time> timeList;
    private int selectPosition=-1;

    public void setTimeList(List<Time> timeList) {
        this.timeList = timeList;
        notifyDataSetChanged();
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public ItemChooseTimeView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_item_choose_time, parent, false);
        return new ItemChooseTimeView(view);
    }

    @Override
    public void onBindViewHolder(final ItemChooseTimeView holder, int position) {
        Time time=timeList.get(holder.getAdapterPosition());
        holder.itemTime.setText(time.getTime());
        if(!TextUtils.isEmpty(time.getAvailable())&&TextUtils.equals(time.getAvailable(),"0")){
            holder.itemTimeFull.setVisibility(View.VISIBLE);
            holder.itemChooseTimeRel.setBackgroundResource(R.drawable.item_choose_time_view1);
        }else {
            holder.itemTimeFull.setVisibility(View.GONE);
            if(position==selectPosition){
                holder.itemChooseTimeRel.setBackgroundResource(R.drawable.item_choose_time_view3);
            }else{
                holder.itemChooseTimeRel.setBackgroundResource(R.drawable.item_choose_time_view2);
            }
        }
        if(onItemTimeClickListener!=null&&!TextUtils.isEmpty(time.getAvailable())&&TextUtils.equals(time.getAvailable(),"1")){
            holder.itemChooseTimeRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemTimeClickListener.itemTimeClickListener(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return timeList == null ? 0 : timeList.size();
    }

    public static class ItemChooseTimeView extends RecyclerView.ViewHolder {
        @Bind(R.id.item_time)
        TextView itemTime;
        @Bind(R.id.item_time_full)
        TextView itemTimeFull;
        @Bind(R.id.item_choose_time_rel)
        LinearLayout itemChooseTimeRel;

        public ItemChooseTimeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemTimeClickListener {
        void itemTimeClickListener(int itemSelectPosition);
    }

    private OnItemTimeClickListener onItemTimeClickListener;

    public void setOnItemTimeClickListener(OnItemTimeClickListener onItemTimeClickListener) {
        this.onItemTimeClickListener = onItemTimeClickListener;
    }
}
