package com.homepaas.sls.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.embedded_class.Time;
import com.homepaas.sls.ui.order.directOrder.adapter.ScheduleDateAdapter;
import com.homepaas.sls.ui.order.directOrder.adapter.ScheduleTimeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.*;

/**
 * Created by CJJ on 2016/9/10.
 *用于下单页的订单预订时间选择
 */
public class ServiceTimePicker extends ActionSheet {

    private static final String TAG = "ServiceTimePicker";
    private View sheetView;

    @Bind(R.id.date_picker)
    RecyclerView datePicker;
    @Bind(R.id.time_picker)
    RecyclerView timePicker;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.confirm)
    TextView confirm;
    @Bind(R.id.action_layout)
    RelativeLayout actionLayout;
    @Bind(R.id.bottom_cancel)
    TextView bottomCancel;
    private OnServiceTimeSelectListener listener;
    public static final String KEY_LEFT = "LEFT";
    public static final String KEY_RIGHT = "RIGHT";
    private ScheduleDateAdapter dateAdapter;
    private ScheduleTimeAdapter timeAdapter;
    private ArrayList<Time> timeSchedule;
    private ArrayList<ServiceScheduleEntity> dateSchedule;

    public static ServiceTimePicker newInstance() {

        Bundle args = new Bundle();

        ServiceTimePicker fragment = new ServiceTimePicker();
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(OnServiceTimeSelectListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.action_sheet_service_time_schedule;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actionLayout.setVisibility(GONE);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        sheetView = getSheetView();
        ButterKnife.bind(this, sheetView);
        Bundle bundle = getArguments();
        timeSchedule = (ArrayList<Time>) bundle.getSerializable(KEY_RIGHT);
        dateSchedule = (ArrayList<ServiceScheduleEntity>) bundle.getSerializable(KEY_LEFT);

        datePicker.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        SimpleItemDecoration decor = new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST);
        datePicker.addItemDecoration(decor);
        dateAdapter = new ScheduleDateAdapter(dateSchedule);
        dateAdapter.setSelectPos(0);
        datePicker.setAdapter(dateAdapter);
        dateAdapter.setOnItemClickListener(new ScheduleDateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                timeAdapter.setDatas(dateSchedule.get(index).getTimeList());
            }
        });

        timePicker.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
//        timePicker.addItemDecoration(decor);
        timeAdapter = new ScheduleTimeAdapter(timeSchedule);
        timeAdapter.setSelectPos(0);
        timeAdapter.setOnTimeClickListener(new ScheduleTimeAdapter.onTimeClickListener() {
            @Override
            public void onClick(int pos) {
                listener.onConfirmServiceTime(dateAdapter.getSelectPos(),timeAdapter.getSelectPos());
                dismiss();
            }
        });
        timePicker.setAdapter(timeAdapter);
        /*cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmServiceTime(dateAdapter.getSelectPos(),timeAdapter.getSelectPos());
                dismiss();
            }
        });*/
        bottomCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static class Builder{
        ArrayList<ServiceScheduleEntity> dateList;
        ArrayList<Time> timeList;
        OnServiceTimeSelectListener listener;

        public Builder left(List<ServiceScheduleEntity> datas)
        {
            this.dateList = new ArrayList<>();
            this.dateList.addAll(datas);
            return this;
        }

        public Builder right(List<Time> datas)
        {
            this.timeList = new ArrayList<>();
            this.timeList.addAll(datas);
            return this;
        }

        public ServiceTimePicker  build(){
            Bundle args = new Bundle();
            args.putSerializable(KEY_LEFT,dateList);
            args.putSerializable(KEY_RIGHT,timeList);
            ServiceTimePicker serviceTimePicker = ServiceTimePicker.newInstance();
            serviceTimePicker.setArguments(args);
            serviceTimePicker.setListener(listener);
            return serviceTimePicker;
        }

        public Builder cb(OnServiceTimeSelectListener listener) {
            this.listener = listener;
            return this;
        }
    }

    public interface OnServiceTimeSelectListener {
        /**
         *
         * @param dPos 日期下标
         * @param tPos 时间下标
         */
       void onConfirmServiceTime(int dPos,int tPos);
    }

    class Holder extends RecyclerView.ViewHolder{
        @Bind(R.id.text)
        TextView text;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
