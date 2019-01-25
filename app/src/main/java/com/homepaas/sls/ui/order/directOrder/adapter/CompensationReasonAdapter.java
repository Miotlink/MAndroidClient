package com.homepaas.sls.ui.order.directOrder.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ReasonsEntity;
import com.homepaas.sls.util.StaticData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/6/29.
 */

public class CompensationReasonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SELECT_REASON = 0; //选择原因的布局，后面有勾选的额
    private static final int SELECT_TIME = 1; //选择工人迟到的时间布局

    private ChooseLateTimeAdapter chooseLateTimeAdapter;
    private LayoutInflater layoutInflater;
    private List<ReasonsEntity> list;
    private int reasonSelectPosition = -1;
    private Map<String, Integer> map;

    public void setList(List<ReasonsEntity> list) {
        this.list = list;
        map = new HashMap<>();
        notifyDataSetChanged();
    }

    public void setReasonSelectPosition(int reasonSelectPosition) {
        this.reasonSelectPosition = reasonSelectPosition;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        if (viewType == SELECT_REASON) {
            view = layoutInflater.inflate(R.layout.adapter_compensation_reason, parent, false);
            SelectReasonView selectReasonView = new SelectReasonView(view);
            selectReasonView.setIsRecyclable(false);
            return selectReasonView;
        } else {
            view = layoutInflater.inflate(R.layout.adapter_compensation_reason_time, parent, false);
            SelectTimeView selectTimeView = new SelectTimeView(view);
            selectTimeView.setIsRecyclable(false);
            return selectTimeView;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (map == null || map.isEmpty()) {
            ReasonsEntity reasonsEntity = list.get(position);
            ((SelectReasonView) holder).setReason(reasonsEntity, position);
        } else {
            if (map.get(StaticData.REASON_SELECT_POSITION) != null) {
                if (position < map.get(StaticData.REASON_SELECT_POSITION) + 1) {
                    ReasonsEntity reasonsEntity = list.get(position);
                    ((SelectReasonView) holder).setReason(reasonsEntity, position);
                } else if (position == map.get(StaticData.REASON_SELECT_POSITION) + 1) {
                    ReasonsEntity reasonsEntity = list.get(position - 1);
                    ((SelectTimeView) holder).setLateTime(reasonsEntity);
                } else {
                    ReasonsEntity reasonsEntity = list.get(position - 1);
                    ((SelectReasonView) holder).setReason(reasonsEntity, position);
                }
            } else {
                ReasonsEntity reasonsEntity = list.get(position);
                ((SelectReasonView) holder).setReason(reasonsEntity, position);
            }
        }
    }



    public class SelectReasonView extends RecyclerView.ViewHolder {
        @Bind(R.id.reason_rel)
        RelativeLayout reasonRel;
        @Bind(R.id.reason)
        TextView reason;
        @Bind(R.id.item_selected)
        ImageView itemSelected;

        public SelectReasonView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setReason(final ReasonsEntity reasonsEntity, final int position) {
            if (position == reasonSelectPosition) {
                itemSelected.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
            } else {
                itemSelected.setImageResource(R.mipmap.client_v330_ic_orders_choose);
            }
            reason.setText(reasonsEntity.getReason());
            if(hostAction!=null){
                reasonRel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){
                        if(TextUtils.equals("工人迟到",reasonsEntity.getReason())){
                            map.put(StaticData.REASON_SELECT_POSITION, position);
                            map.put(StaticData.REASON_SELECT_ID,Integer.parseInt(reasonsEntity.getId()));
                            map.put(StaticData.WORKER_LATE_ITEM,1);
                        }else {
                            map.put(StaticData.WORKER_LATE_ITEM,0);
                        }
                        hostAction.onReasonSelect(position,reasonsEntity.getId());
                    }
                });
            }
        }
    }

    public class SelectTimeView extends RecyclerView.ViewHolder {
        @Bind(R.id.reason_time)
        TextView reasonTime;
        @Bind(R.id.time_recyclerView)
        RecyclerView timeRecyclerView;

        public SelectTimeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setLateTime(ReasonsEntity reasonsEntity) {
            reasonTime.setText(reasonsEntity.getReasonTime());
            chooseLateTimeAdapter = new ChooseLateTimeAdapter();
            timeRecyclerView.setAdapter(chooseLateTimeAdapter);
            chooseLateTimeAdapter.setTimeList(reasonsEntity.getLateTime());
            if(map!=null&&map.get(StaticData.WORKER_LATE_ITEM)!=null&&map.get(StaticData.WORKER_LATE_ITEM)==1&&map.get(StaticData.CHOOSE_LATE_TIME_POSITION)!=null){
                chooseLateTimeAdapter.setChooseLateTimePosition(map.get(StaticData.CHOOSE_LATE_TIME_POSITION));
            }
            chooseLateTimeAdapter.setLateTimeSelect(new ChooseLateTimeAdapter.LateTimeSelect() {
                @Override
                public void timeSelect(int position) {
                    chooseLateTimeAdapter.setChooseLateTimePosition(position);
                    if(hostAction!=null){
                        hostAction.onLateTimeSelect(position);
                    }
                    if(map!=null){
                        map.put(StaticData.CHOOSE_LATE_TIME_POSITION,position);
                        if(hostAction!=null&&map.get(StaticData.REASON_SELECT_POSITION)!=null) {
                            map.put(StaticData.WORKER_LATE_ITEM,1);
                            hostAction.onReasonSelect(map.get(StaticData.REASON_SELECT_POSITION),map.get(StaticData.REASON_SELECT_ID)+"");
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (map == null || map.isEmpty()) {
            return list == null ? 0 : list.size();
        } else {
            if (map.get(StaticData.REASON_SELECT_POSITION) != null) {
                return list == null ? 0 : list.size() + 1;
            } else {
                return list == null ? 0 : list.size();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (map == null || map.isEmpty()) {
            return SELECT_REASON;
        } else {
            if (map.get(StaticData.REASON_SELECT_POSITION) != null && position == map.get(StaticData.REASON_SELECT_POSITION) + 1) {
                return SELECT_TIME;
            } else {
                return SELECT_REASON;
            }
        }
    }

    public interface HostAction {
        void onReasonSelect(int position,String ReasonType); //选择哪个原因
        void onLateTimeSelect(int position); //工人迟到，选择的时间
    }

    private HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }
}
