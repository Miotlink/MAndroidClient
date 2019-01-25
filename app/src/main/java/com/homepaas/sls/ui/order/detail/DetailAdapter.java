package com.homepaas.sls.ui.order.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.DetailOrder;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.adapter.OrderPhotoAdapter;
import com.homepaas.sls.ui.order.adapter.PhotoAdapter;
import com.homepaas.sls.ui.widget.ManuallyCheckBox;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CJJ on 2016/7/25.
 */
public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int SERVCIEINFO_TYPE = 0;
    private static final int SERVICESTATUS_TYPE = 1;
    private static final int SERVICECONTENT_TYPE = 2;
    private static final int REFUND_TYPE = 3;
    private static final int SERVICETIME_TYPE = 4;
    private static final int SERVCIEEVALUATION_TYPE = 5;
    private Context context;
    private LayoutInflater inflater;
    private int DEAULT_COUNT = 4;
    private DetailOrder orderInfo;
    private boolean hasEva;
    private boolean hasRefund;

    public DetailAdapter(Context context) {
        this.context = context;
    }

    public void setDetailInfo(DetailOrder detailInfo) {
        this.orderInfo = detailInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = LayoutInflater.from(context);
        switch (viewType) {
            case SERVCIEINFO_TYPE:
                return new ServiceInfoHolder(inflater.inflate(R.layout.detail_order_item_serviceinfo, parent, false));
            case SERVICESTATUS_TYPE:
                return new ServiceStatusHolder(inflater.inflate(R.layout.detail_order_item_servicestatus, parent, false));
            case SERVICECONTENT_TYPE:
                return new ServiceContentHolder(inflater.inflate(R.layout.detail_order_item_servicecontent, parent, false));
            case SERVICETIME_TYPE:
                return new ServiceTimeHolder(inflater.inflate(R.layout.detail_order_item_servicetime, parent, false));
            case REFUND_TYPE:
                return new RefundHolder(inflater.inflate(R.layout.detail_order_item_refund, parent, false));
            case SERVCIEEVALUATION_TYPE:
                return new EvaluationHolder(inflater.inflate(R.layout.detail_order_item_evaluation, parent, false));
            default:
                return new RefundHolder(inflater.inflate(R.layout.detail_order_item_refund, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ServiceInfoHolder) {
            ServiceInfoHolder serviceInfoHolder = (ServiceInfoHolder) holder;
            Glide.with(context).load(orderInfo.getServiceProvidePic()).into(serviceInfoHolder.avatar);
            serviceInfoHolder.name.setText(orderInfo.getServiceProviderName());
            serviceInfoHolder.collection.setChecked(Float.valueOf(orderInfo.getServiceProviderCollection()) > 0);
            serviceInfoHolder.collection.setText(orderInfo.getServiceProviderCollection());
            serviceInfoHolder.like.setChecked(Float.valueOf(orderInfo.getServiceProviderPraise()) > 0);
            serviceInfoHolder.like.setText(orderInfo.getServiceProviderPraise());
            serviceInfoHolder.score.setChecked(Float.valueOf(orderInfo.getServiceProviderScore()) > 0);
            serviceInfoHolder.score.setText(orderInfo.getServiceProviderScore());
        }

        if (holder instanceof ServiceStatusHolder)
        {
            ServiceStatusHolder serviceStatusHolder = (ServiceStatusHolder) holder;
            serviceStatusHolder.serviceStatus.setText(getStatusText(orderInfo.getOrderStatus()));
        }

        if (holder instanceof ServiceContentHolder)
        {
            ServiceContentHolder serviceContentHolder = (ServiceContentHolder) holder;
            serviceContentHolder.serviceRequirements.setText(orderInfo.getService().getContent());
            OrderPhotoAdapter adapter = new OrderPhotoAdapter(context, false);
//            adapter.setData(orderInfo.getService().getPictures());
            serviceContentHolder.pictureList.setAdapter(adapter);
            serviceContentHolder.price.setText(orderInfo.getPrice()+"元");
        }

        if (holder instanceof RefundHolder)
        {
            RefundHolder refundHolder = (RefundHolder) holder;
            DetailOrder.Refund refund = orderInfo.getRefunds().get(0);
            refundHolder.refundAmount.setText(refund.getRefundAmount());
            refundHolder.refundTime.setText(TimeUtils.formatOrderTime(refund.getRefundtime()));
            refundHolder.refundText.setText("("
                    +(refund.getRefundAmount().length()!=0?"应退"+refund.getRefundAmount()+"元":"")
                    +(refund.getLostIncome().length()!=0?"误工费"+refund.getLostIncome()+"元":""
                    +")"));
//            refundHolder.refundText.setText(orderInfo.getRefunds().get(0).get);
        }

        if (holder instanceof ServiceTimeHolder)
        {
            ServiceTimeHolder serviceTimeHolder = (ServiceTimeHolder) holder;
            serviceTimeHolder.orderTime.setText(orderInfo.getCreateTime());
            serviceTimeHolder.orderCode.setText(orderInfo.getOrderCode());
            if (TextUtils.equals(orderInfo.getOrderStatus(),String.valueOf(Constant.COMPLETE))||TextUtils.equals(orderInfo.getOrderStatus(),String.valueOf(Constant.CONFIRM))){
               serviceTimeHolder.orderTime.setVisibility(View.VISIBLE);
                serviceTimeHolder.orderTime.setText("完成时间:"+orderInfo.getFinishTime());
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = DEAULT_COUNT;
        DetailOrder.Evaluation evaluation = orderInfo.getEvaluation();
        List<DetailOrder.Refund> refunds = orderInfo.getRefunds();
        if (evaluation != null) {
            count++;
            hasEva = true;
        }
        if (!refunds.isEmpty()) {
            count++;
            hasRefund = true;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return SERVCIEINFO_TYPE;//基本信息
            case 1:
                return SERVICESTATUS_TYPE;
            case 2:
                return SERVICECONTENT_TYPE;
            case 3:
                if (hasRefund)
                    return REFUND_TYPE;
                else
                    return SERVICETIME_TYPE;
            case 4:
                if (hasRefund)
                    return SERVICETIME_TYPE;
                else if (hasEva)
                    return SERVCIEEVALUATION_TYPE;
                break;
            case 5:
                return SERVCIEEVALUATION_TYPE;
        }
        return super.getItemViewType(position);
    }

    static class DHolder extends RecyclerView.ViewHolder {

        public DHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * 基本信息
     */
    class ServiceInfoHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.like)
        ManuallyCheckBox like;
        @Bind(R.id.like_anim)
        TextView likeAnim;
        @Bind(R.id.collection)
        ManuallyCheckBox collection;
        @Bind(R.id.collection_anim)
        TextView collectionAnim;
        @Bind(R.id.score)
        ManuallyCheckBox score;
        @Bind(R.id.contact_provider)
        TextView contactProvider;
        @Bind(R.id.contact_service)
        TextView contactService;

        public ServiceInfoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 服务状态
     */
    class ServiceStatusHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.service_status)
        TextView serviceStatus;

        public ServiceStatusHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 服务内容信息
     */
    class ServiceContentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.service_requirements)
        TextView serviceRequirements;
        @Bind(R.id.picture_list)
        RecyclerView pictureList;
        @Bind(R.id.picture_container)
        RelativeLayout pictureContainer;
        @Bind(R.id.price)
        TextView price;

        public ServiceContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    /**
     * 服务时间信息
     */
    class ServiceTimeHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.order_time)
        TextView orderTime;
        @Bind(R.id.order_time_layout)
        RelativeLayout orderTimeLayout;
        @Bind(R.id.order_code)
        TextView orderCode;
        @Bind(R.id.order_code_alyout)
        RelativeLayout orderCodeAlyout;
        @Bind(R.id.complete_time)
        TextView completeTime;
        @Bind(R.id.order_completetime_layout)
        RelativeLayout orderCompletetimeLayout;

        public ServiceTimeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 退款信息
     */
    class RefundHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.refund_text)
        TextView refundText;
        @Bind(R.id.refund_time)
        TextView refundTime;
        @Bind(R.id.refund_amount)
        TextView refundAmount;
        @Bind(R.id.refund_status)
        TextView refundStatus;
        @Bind(R.id.refund_layout)
        RelativeLayout refundLayout;

        public RefundHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 评论信息
     */
    class EvaluationHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.evaluate_text)
        TextView evaluateText;
        @Bind(R.id.ratingbar)
        RatingBar ratingbar;
        @Bind(R.id.rating)
        TextView rating;
        @Bind(R.id.evaluate_time)
        TextView evaluateTime;
        @Bind(R.id.evaluate_content)
        TextView evaluateContent;
        @Bind(R.id.recycler_view_evaluation)
        RecyclerView recyclerViewEvaluation;
        @Bind(R.id.reply_content)
        TextView replyContent;
        @Bind(R.id.reply_content_layout)
        LinearLayout replyContentLayout;
        @Bind(R.id.evaluation_layout)
        RelativeLayout evaluationLayout;

        public EvaluationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    private String getStatusText(String orderStatus) {

        switch (Integer.valueOf(orderStatus)) {
            case Constant.PLACE:
                return "已下单";
            case Constant.TAKEN:
                return "已接单";
            case Constant.COMPLETE:
                return "服务完成";
            case Constant.CONFIRM:
                return "待评价";
            case Constant.CANCEL:
                return "已取消";
        }
        return "";
    }
}
