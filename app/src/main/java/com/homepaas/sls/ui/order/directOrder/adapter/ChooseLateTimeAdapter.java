package com.homepaas.sls.ui.order.directOrder.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/7/4.
 */

public class ChooseLateTimeAdapter extends RecyclerView.Adapter<ChooseLateTimeAdapter.ChooseLateTimeView> implements View.OnClickListener{
    private LayoutInflater layoutInflater;
    private List<String> timeList;
    private int chooseLateTimePosition = -1;

    public void setChooseLateTimePosition(int chooseLateTimePosition){
        this.chooseLateTimePosition=chooseLateTimePosition;
        notifyDataSetChanged();
    }
    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
        notifyDataSetChanged();
    }

    @Override
    public ChooseLateTimeView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_choose_late_time, parent, false);
        ChooseLateTimeView chooseLateTimeView=new ChooseLateTimeView(view);
        view.setOnClickListener(this);
        return chooseLateTimeView;
    }

    @Override
    public void onBindViewHolder(final ChooseLateTimeView holder, int position) {
        holder.lateTime.setText(timeList.get(holder.getAdapterPosition()));
        if(chooseLateTimePosition==holder.getAdapterPosition()){
            holder.lateTime.setTextColor(Color.parseColor("#ff1b56"));
            holder.lateTime.setBackgroundResource(R.drawable.late_time_check);
        }else {
            holder.lateTime.setTextColor(Color.parseColor("#333639"));
            holder.lateTime.setBackgroundResource(R.drawable.late_time_not_check);
        }
        holder.itemView.setTag(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return timeList == null ? 0 : timeList.size();
    }

    @Override
    public void onClick(View view) {
        if(lateTimeSelect!=null){
            lateTimeSelect.timeSelect((int)view.getTag());
        }
    }

    public class ChooseLateTimeView extends RecyclerView.ViewHolder {
        @Bind(R.id.late_time)
        TextView lateTime;
        public ChooseLateTimeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface LateTimeSelect{
        void timeSelect(int position);
    }
    private LateTimeSelect lateTimeSelect;
    public void setLateTimeSelect(LateTimeSelect lateTimeSelect){
        this.lateTimeSelect=lateTimeSelect;
    }
}
