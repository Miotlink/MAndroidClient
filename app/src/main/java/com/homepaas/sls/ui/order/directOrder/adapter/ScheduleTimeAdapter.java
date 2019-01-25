package com.homepaas.sls.ui.order.directOrder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.embedded_class.Time;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CJJ on 2016/9/14.
 */

public class ScheduleTimeAdapter extends RecyclerView.Adapter<ScheduleTimeAdapter.Holder> {

    List<Time> datas;
    LayoutInflater inflater;
    int checkColor = Color.parseColor("#3FBEF9");
    int notCheckColor = Color.parseColor("#CCCCCC");
    int selectPos = -1;
    int lastPos = -1;
    private Context context;

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public ScheduleTimeAdapter(List<Time> datas) {
        this.datas = datas;
    }

    public void setDatas(List<Time> datas) {
        this.datas = datas;
        selectPos = -1;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null)
        {
            context = parent.getContext();
            inflater = LayoutInflater.from(parent.getContext());
        }
        return new Holder(inflater.inflate(R.layout.item_time_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class Holder extends RecyclerView.ViewHolder implements Action {
        @Bind(R.id.time)
        TextView schedule;
        @Bind(R.id.is_vacant)
        TextView isVacant;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind() {
            schedule.setText(datas.get(getAdapterPosition()).getTime());
            if (datas.get(getAdapterPosition()).isAvailable())
            {
                schedule.setTextColor(Color.parseColor("#333333"));
                isVacant.setVisibility(View.GONE);
            }
            else {
                schedule.setTextColor(Color.parseColor("#cbcfd7"));
                isVacant.setVisibility(View.VISIBLE);
            }
//            if (getAdapterPosition() != selectPos)
//                schedule.setTextColor(notCheckColor);
//            else
//                schedule.setTextColor(checkColor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (datas.get(getAdapterPosition()).isAvailable()) {
                        lastPos = selectPos;
                        selectPos = getAdapterPosition();
                        if (onTimeClickListener != null)
                            onTimeClickListener.onClick(selectPos);
                        notifyItemChanged(lastPos);
                    }else {
                        Toast.makeText(context.getApplicationContext(),"该时间段工人无法接受预约，请选择其他时间时间段",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private onTimeClickListener onTimeClickListener;

    public void setOnTimeClickListener(ScheduleTimeAdapter.onTimeClickListener onTimeClickListener) {
        this.onTimeClickListener = onTimeClickListener;
    }

    public interface onTimeClickListener{
        void onClick(int pos);
    }

    public interface Action {
        void bind();
    }
}
