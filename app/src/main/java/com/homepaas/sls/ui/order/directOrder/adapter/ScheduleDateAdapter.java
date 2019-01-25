package com.homepaas.sls.ui.order.directOrder.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.ui.AddViewFrameLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CJJ on 2016/9/14.
 */

public class ScheduleDateAdapter extends RecyclerView.Adapter<ScheduleDateAdapter.Holder>{


    List<ServiceScheduleEntity> datas;
    LayoutInflater inflater ;
    int checkColor = Color.parseColor("#3FBEF9");
    int  notCheckColor = Color.parseColor("#CCCCCC");
    int selectPos = -1;
    int lastPos = -1;

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public ScheduleDateAdapter(List<ServiceScheduleEntity> datas) {
        this.datas = datas;
    }

    public void setDatas(List<ServiceScheduleEntity> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ScheduleDateAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());
        return new Holder(inflater.inflate(R.layout.item_schedule,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.schedule.setText(TimeUtils.formatSmartTime(datas.get(position).getDate()));
        if (position != selectPos)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#eeeeee"));
//            holder.schedule.setTextColor(notCheckColor);
        }
        else
            holder.itemView.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return datas == null?0:datas.size();
    }

    class Holder extends RecyclerView.ViewHolder implements Action{
        @Bind(R.id.schedule)
        TextView schedule;

        public Holder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastPos = selectPos;
                    selectPos = getAdapterPosition();
//                    schedule.setTextColor(checkColor);
                    itemView.setBackgroundColor(Color.WHITE);
                    notifyItemChanged(lastPos);
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void bind() {
            schedule.setText(datas.get(getAdapterPosition()).getDate());
            if (getAdapterPosition() != selectPos)
            {
//                schedule.setTextColor(notCheckColor);
                itemView.setBackgroundColor(Color.parseColor("#fbfbfb"));
            }

            schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lastPos = selectPos;
                    selectPos = getAdapterPosition();
                    schedule.setTextColor(checkColor);
                    notifyItemChanged(lastPos);
                }
            });
        }
    }

    public interface Action{
        void bind();
    }
    public interface OnItemClickListener{
        void onItemClick(int index);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
