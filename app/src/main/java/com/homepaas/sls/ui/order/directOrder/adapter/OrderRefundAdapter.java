package com.homepaas.sls.ui.order.directOrder.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.embedded_class.Refund;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/5/10.
 */

public class OrderRefundAdapter extends RecyclerView.Adapter<OrderRefundAdapter.OrderRefundView> {
    private LayoutInflater layoutInflater;
    private List<Refund> refundList;

    public void setRefundList(List<Refund> refundList) {
        this.refundList = refundList;
        notifyDataSetChanged();
    }

    @Override
    public OrderRefundView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_order_refund, parent, false);
        return new OrderRefundView(view);
    }

    @Override
    public void onBindViewHolder(OrderRefundView holder, int position) {
        Refund refund = refundList.get(holder.getAdapterPosition());
        holder.refundTime.setText(TimeUtils.formatDateByLine(refund.getRefundTime()));
        if (TextUtils.equals("1", refund.getStatus())) {
            holder.refundStatus.setText("退款审核中");
        } else if (TextUtils.equals("2", refund.getStatus())) {
            holder.refundStatus.setText("退款完成");
        } else if (TextUtils.equals("4", refund.getStatus())) {
            holder.refundStatus.setText("审核不通过");
        }
        holder.refundPrice.setText(refund.getRefundAmount() + "元");
    }

    @Override
    public int getItemCount() {
        return refundList == null ? 0 : refundList.size();
    }

    public static class OrderRefundView extends RecyclerView.ViewHolder {
        @Bind(R.id.refund_time)
        TextView refundTime;
        @Bind(R.id.refund_status)
        TextView refundStatus;
        @Bind(R.id.refund_price)
        TextView refundPrice;

        public OrderRefundView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
