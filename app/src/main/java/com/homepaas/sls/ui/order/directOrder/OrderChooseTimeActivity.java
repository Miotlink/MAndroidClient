package com.homepaas.sls.ui.order.directOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.embedded_class.Time;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.adapter.ItemChooseTimeAdapter;
import com.homepaas.sls.ui.order.directOrder.adapter.MenuChooseTimeAdapter;
import com.homepaas.sls.util.StaticData;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/3/24.
 * 下单的时候选择时间界面
 */

public class OrderChooseTimeActivity extends CommonBaseActivity implements MenuChooseTimeAdapter.OnMenuDateClickListener,ItemChooseTimeAdapter.OnItemTimeClickListener {
    @Bind(R.id.menu_time_recyclerview)
    RecyclerView menuTimeRecyclerview;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.item_time_recycleView)
    RecyclerView itemTimeRecycleView;
    @Bind(R.id.confirm_selection_button)
    Button confirmSelectionButton;

    private MenuChooseTimeAdapter menuChooseTimeAdapter;
    private ItemChooseTimeAdapter itemChooseTimeAdapter;

    private List<ServiceScheduleEntity> scheduleList;
    private Time serviceTime;
    private ServiceScheduleEntity serviceScheduleEntity;
    private int menuSelectPosition;
    private int itemSelectPosition;

    public static void start(Context context, List<ServiceScheduleEntity> schedule) {
        Intent intent = new Intent(context, OrderChooseTimeActivity.class);
        intent.putExtra(StaticData.SCHEDULE_List, (Serializable) schedule);
        context.startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_choosetime;
    }

    @Override
    protected void initView() {
            scheduleList = (List<ServiceScheduleEntity>) getIntent().getSerializableExtra(StaticData.SCHEDULE_List);
            setChooseTimeAdapter();
        }

    @Override
    protected void initData() {

    }

    private void setChooseTimeAdapter() {
        menuChooseTimeAdapter = new MenuChooseTimeAdapter(this);
        menuChooseTimeAdapter.setOnMenuDateClickListener(this);
        menuTimeRecyclerview.setAdapter(menuChooseTimeAdapter);
        menuChooseTimeAdapter.setDateList(scheduleList);
        itemTimeRecycleView.setLayoutManager(new GridLayoutManager(this, 4));
        itemChooseTimeAdapter = new ItemChooseTimeAdapter();
        itemChooseTimeAdapter.setOnItemTimeClickListener(this);
        itemTimeRecycleView.setAdapter(itemChooseTimeAdapter);
        if (scheduleList != null && scheduleList.size() > 0) {
            menuChooseTimeAdapter.setSelectPosition(0);
            itemChooseTimeAdapter.setTimeList(scheduleList.get(0).getTimeList());
            serviceScheduleEntity=scheduleList.get(0);
        }
    }

    /**
     * 几月几号头部的点击
     *
     * @param menuSelectPosition
     */
    @Override
    public void menuDateClickListener(int menuSelectPosition) {
        this.menuSelectPosition=menuSelectPosition;
        if (scheduleList != null) {
            menuChooseTimeAdapter.setSelectPosition(menuSelectPosition);
            itemChooseTimeAdapter.setTimeList(scheduleList.get(menuSelectPosition).getTimeList());
            itemChooseTimeAdapter.setSelectPosition(-1);
            serviceScheduleEntity=scheduleList.get(menuSelectPosition);
        }
    }

    @Override
    public void itemTimeClickListener(int itemSelectPosition) {
        this.itemSelectPosition=itemSelectPosition;
        itemChooseTimeAdapter.setSelectPosition(itemSelectPosition);
        serviceTime=scheduleList.get(menuSelectPosition).getTimeList().get(itemSelectPosition);
    }

    @OnClick({R.id.confirm_selection_button,R.id.back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.confirm_selection_button:
                Intent resultIntent = new Intent();
                resultIntent.putExtra(StaticData.SERVICE_SCHEDULE,serviceScheduleEntity);
                resultIntent.putExtra(StaticData.SERVICE_TIME, serviceTime);
                setResult(Activity.RESULT_OK, resultIntent);
                ActivityCompat.finishAfterTransition(this);
                break;
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
