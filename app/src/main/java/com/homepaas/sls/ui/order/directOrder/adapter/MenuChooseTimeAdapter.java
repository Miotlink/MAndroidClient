package com.homepaas.sls.ui.order.directOrder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/3/24.
 * 下单页头部选择时间：周一，3月22号之类
 */

public class MenuChooseTimeAdapter extends RecyclerView.Adapter<MenuChooseTimeAdapter.MenuChooseTiemView> {

    private LayoutInflater layoutInflater;
    private List<ServiceScheduleEntity> dateList;
    private int selectPosition = -1;
    private Context context;

    public MenuChooseTimeAdapter(Context context) {
        this.context = context;
    }

    public void setDateList(List<ServiceScheduleEntity> dateList) {
        this.dateList = dateList;
        notifyDataSetChanged();
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public MenuChooseTiemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_menu_choose_time, parent, false);
        return new MenuChooseTiemView(view);
    }

    @Override
    public void onBindViewHolder(final MenuChooseTiemView holder, int position) {
        String[] timeArray = dateList.get(holder.getAdapterPosition()).getDate().split(" ");
        holder.date.setTextColor(selectPosition == position ? Color.parseColor("#FFFFFF") : Color.parseColor("#333639"));
        holder.week.setTextColor(selectPosition == position ? Color.parseColor("#FFFFFF") : Color.parseColor("#333639"));
        holder.menuChooseTimeLin.setBackgroundResource(selectPosition == position ? R.mipmap.client_v330_ic_time_choose : R.mipmap.client_v330_ic_time_choose2);
        if (timeArray != null && timeArray.length > 0) {
            holder.week.setText(timeArray[1]);
            holder.date.setText(timeArray[0]);
        }
        if (onMenuDateClickListener != null) {
            holder.menuChooseTimeLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMenuDateClickListener.menuDateClickListener(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dateList == null ? 0 : dateList.size();
    }

    public static class MenuChooseTiemView extends RecyclerView.ViewHolder {
        @Bind(R.id.week)
        TextView week;
        @Bind(R.id.menu_full_time)
        TextView menuFullTime;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.menu_choose_time_lin)
        LinearLayout menuChooseTimeLin;

        public MenuChooseTiemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnMenuDateClickListener {
        void menuDateClickListener(int menuSelectPosition);
    }

    private OnMenuDateClickListener onMenuDateClickListener;

    public void setOnMenuDateClickListener(OnMenuDateClickListener onMenuDateClickListener) {
        this.onMenuDateClickListener = onMenuDateClickListener;
    }

}
