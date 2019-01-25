package com.homepaas.sls.ui.order.directOrder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.UpdateListEntity;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.util.ArithUtil;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.SpanUtils;
import com.homepaas.sls.util.StringUitls;
import com.homepaas.sls.util.time.TimeTaskHelp;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/7/19.
 */

public class OrderTrackingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater layoutInflater;
    private List<TrackOrderInfo.OrderStatusInfo> orderStatusInfoList;
    private TimeTaskHelp timeTaskHelp;
    private Context context;

    public OrderTrackingAdapter(Context context, TimeTaskHelp timeTaskHelp) {
        this.context = context;
        this.timeTaskHelp = timeTaskHelp;
    }

    public void setOrderStatusInfoList(List<TrackOrderInfo.OrderStatusInfo> orderStatusInfoList) {
        this.orderStatusInfoList = orderStatusInfoList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        if (viewType == TrackOrderInfo.OrderStatusInfo.COUNT_TIME) { //倒计时
            view = layoutInflater.inflate(R.layout.adapter_order_count_time, parent, false);
            return new CountTimeViewHolder(view);
        } else if (viewType == TrackOrderInfo.OrderStatusInfo.TIME_HEAD) { //当前时间
            view = layoutInflater.inflate(R.layout.adapter_order_head, parent, false);
            return new HeadViewHolder(view);
        } else {
            //流程item
            view = layoutInflater.inflate(R.layout.adapter_order_tracking, parent, false);
            return new CenterViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (orderStatusInfoList == null || orderStatusInfoList.size() == 0) {
            return super.getItemViewType(position);
        } else if (orderStatusInfoList.get(position).getType() == TrackOrderInfo.OrderStatusInfo.COUNT_TIME) {
            return TrackOrderInfo.OrderStatusInfo.COUNT_TIME;
        } else if (orderStatusInfoList.get(position).getType() == TrackOrderInfo.OrderStatusInfo.TIME_HEAD) {
            return TrackOrderInfo.OrderStatusInfo.TIME_HEAD;
        } else {
            return TrackOrderInfo.OrderStatusInfo.DEFATULT_STATUS;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TrackOrderInfo.OrderStatusInfo orderStatusInfo = orderStatusInfoList.get(holder.getAdapterPosition());
        if (holder instanceof CenterViewHolder) { //流程item
            CenterViewHolder centerHolder = (CenterViewHolder) holder;
            if (!TextUtils.isEmpty(orderStatusInfo.getStatus()))
                centerHolder.orderStatus.setText(orderStatusInfo.getStatus());
            if (!TextUtils.isEmpty(orderStatusInfo.getTime()))
                centerHolder.minuteTime.setText(orderStatusInfo.getTime());

            final TrackOrderInfo.OrderStatusInfo.OrderProviderInfo orderProviderInfo = orderStatusInfo.getOrderProviderInfo();
            String description = orderStatusInfo.getDescription();
            if (orderProviderInfo != null) {
                centerHolder.tvServerInfo.setVisibility(View.VISIBLE);

                String phoneNumber = StringUitls.getTelnum(description);
                LogUtils.i("TAG", "phoneNumber" + phoneNumber);
                if (TextUtils.isEmpty(phoneNumber)) {
                    if (orderStatusInfo.getOrderProviderInfo() != null && !TextUtils.isEmpty(orderStatusInfo.getOrderProviderInfo().getPhoneNumber()))
                        phoneNumber = orderStatusInfo.getOrderProviderInfo().getPhoneNumber();
                }
                final String finalPhoneNumber = phoneNumber;
                SpannableString spannableString = SpanUtils.setSpanColorAndClick(context.getResources().getColor(R.color.recharge_bg_color), description, finalPhoneNumber, new SpanUtils.SpanClickListener() {
                    @Override
                    public void onSpanClick() {
                        hostAction.callWorker(orderStatusInfo,finalPhoneNumber);
                    }
                });
                centerHolder.tvServerInfo.setText(spannableString);
                centerHolder.tvServerInfo.setMovementMethod(LinkMovementMethod.getInstance());
            } else if (!TextUtils.isEmpty(orderStatusInfo.getDescription())) {
                centerHolder.tvServerInfo.setText(description);
            } else {
                centerHolder.tvServerInfo.setVisibility(View.GONE);
            }

            //如果流程item为最后一个item布局需要调整样式
            if (holder.getAdapterPosition() == orderStatusInfoList.size() - 1) {
                if (orderProviderInfo == null && TextUtils.isEmpty(description)) {
                    centerHolder.tvServerInfo.setVisibility(View.GONE);
                    centerHolder.imgDeLeft.setVisibility(View.GONE);
                } else {
                    centerHolder.tvServerInfo.setVisibility(View.VISIBLE);
                    centerHolder.imgDeLeft.setVisibility(View.VISIBLE);
                }
            }
        } else if (holder instanceof CountTimeViewHolder)//倒计时item
        {
            final CountTimeViewHolder countTimeViewHolder = (CountTimeViewHolder) holder;
            if (!TextUtils.isEmpty(orderStatusInfo.getStatus()))
                countTimeViewHolder.orderStatusTime.setText(orderStatusInfo.getStatus());
            if (!TextUtils.isEmpty(orderStatusInfo.getTime())) {
                timeTaskHelp.setOnTimeChangeListener(new TimeTaskHelp.OnTimeChangeListener() {
                    @Override
                    public void onTimeChange(String time) {
                        countTimeViewHolder.tvFooterTimeTime.setText(time);
                    }

                    @Override
                    public void onTimeCompletion() {
                        //计时结束刷新列表
                        EventBus.getDefault().post(new UpdateListEntity(true));
                    }
                });
                try {
                    timeTaskHelp.startCountDownTimeHMS(Long.parseLong(ArithUtil.round(orderStatusInfo.getTime(), 0)));
                } catch (Exception ex) {
                    LogUtils.i("TAG", ex.toString() + "倒计时格式化异常");
                }

            } else
                countTimeViewHolder.imgTime.setVisibility(View.INVISIBLE);

            final TrackOrderInfo.OrderStatusInfo.OrderProviderInfo orderProviderInfo = orderStatusInfo.getOrderProviderInfo();
            String description = orderStatusInfo.getDescription();
            if (orderProviderInfo != null) {
                countTimeViewHolder.tvServerInfoTime.setVisibility(View.VISIBLE);

                String phoneNumber = StringUitls.getTelnum(description);
                LogUtils.i("TAG", "phoneNumber" + phoneNumber);
                if (TextUtils.isEmpty(phoneNumber)) {
                    if (orderStatusInfo.getOrderProviderInfo() != null && !TextUtils.isEmpty(orderStatusInfo.getOrderProviderInfo().getPhoneNumber()))
                        phoneNumber = orderStatusInfo.getOrderProviderInfo().getPhoneNumber();
                }
                final String finalPhoneNumber = phoneNumber;
                SpannableString spannableString = SpanUtils.setSpanColorAndClick(context.getResources().getColor(R.color.recharge_bg_color), description, phoneNumber, new SpanUtils.SpanClickListener() {
                    @Override
                    public void onSpanClick() {
                        hostAction.callWorker(orderStatusInfo,finalPhoneNumber);
                    }
                });
                countTimeViewHolder.tvServerInfoTime.setText(spannableString);
                countTimeViewHolder.tvServerInfoTime.setMovementMethod(LinkMovementMethod.getInstance());
            } else if (!TextUtils.isEmpty(orderStatusInfo.getDescription())) {
                countTimeViewHolder.tvServerInfoTime.setText(description);
            } else {
                countTimeViewHolder.tvServerInfoTime.setVisibility(View.INVISIBLE);
            }


        } else if (holder instanceof HeadViewHolder) {//当前日期
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            String headItemData = orderStatusInfo.getHeadItemData();
//            int monthIndex = headItemData.indexOf("月");
//            int dayIndex = headItemData.indexOf("日");
//            headViewHolder.tvDay.setText(headItemData.substring(monthIndex + 1, dayIndex));
//            headViewHolder.tvMonth.setText(headItemData.substring(0, monthIndex + 1));
            headViewHolder.tvDay.setText(headItemData);
            //如果订单信息存在两天或以上，其他日期和样式和第一天的不一样
            if (holder.getAdapterPosition() != 0) {
                headViewHolder.tvDay.setTextColor(context.getResources().getColor(R.color.homepageServerText1));
                headViewHolder.tvMonth.setTextColor(context.getResources().getColor(R.color.homepageServerText1));
                headViewHolder.imgTop.setVisibility(View.VISIBLE);
                //
            } else {
                headViewHolder.tvDay.setTextColor(context.getResources().getColor(R.color.appText3));
                headViewHolder.tvMonth.setTextColor(context.getResources().getColor(R.color.appText3));
                headViewHolder.imgTop.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderStatusInfoList == null ? 0 : orderStatusInfoList.size();
    }


    public interface HostAction {
        void callWorker(TrackOrderInfo.OrderStatusInfo orderStatusInfoo,String phoneNumber);
    }

    private HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_day)
        TextView tvDay;
        @Bind(R.id.tv_month)
        TextView tvMonth;
        @Bind(R.id.img_top)
        ImageView imgTop;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class CenterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.minute_time)
        TextView minuteTime;
        @Bind(R.id.ly_top_left)
        LinearLayout lyTopLeft;
        @Bind(R.id.img_de_left)
        ImageView imgDeLeft;
        @Bind(R.id.order_status)
        TextView orderStatus;
        @Bind(R.id.tv_server_info)
        TextView tvServerInfo;


        public CenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class CountTimeViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_time)
        ImageView imgTime;
        @Bind(R.id.tv_footer_time_time)
        TextView tvFooterTimeTime;
        @Bind(R.id.img_black)
        ImageView imgBlack;
        @Bind(R.id.order_status_time)
        TextView orderStatusTime;
        @Bind(R.id.tv_server_info_time)
        TextView tvServerInfoTime;

        public CountTimeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
