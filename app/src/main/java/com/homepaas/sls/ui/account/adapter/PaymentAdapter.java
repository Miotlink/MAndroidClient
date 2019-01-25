package com.homepaas.sls.ui.account.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.mvp.model.NormalPayItem;
import com.homepaas.sls.mvp.model.PayItem;
import com.homepaas.sls.mvp.model.TimePayItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * recyclerView Adapter
 */
public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private static final int DATE = 1;

    private static final int DATA_SHORT = 2;

    private static final int DATA_FULL = 3;


    private LayoutInflater mInflater;

    public void setPayItems(List<? extends PayItem> payItems) {
        mPayItems = payItems;
        notifyDataSetChanged();
    }

    private List<? extends PayItem> mPayItems;

    public PaymentAdapter(List<? extends PayItem> payItems) {
        mPayItems = payItems;
    }


    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        if (viewType == DATE) {
            view = mInflater.inflate(R.layout.payment_date_item, parent, false);
        } else if (viewType == DATA_SHORT) {
            view = mInflater.inflate(R.layout.balance_item, parent, false);
        } else {
            view = mInflater.inflate(R.layout.payment_item, parent, false);
        }
        return new PaymentViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        PayItem item = mPayItems.get(position);
        if (item instanceof TimePayItem) {
            return DATE;
        } else if (item instanceof NormalPayItem) {
            return ((NormalPayItem) item).isNoRefund() ? DATA_SHORT : DATA_FULL;
        } else {
            return DATE;
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBindViewHolder(PaymentViewHolder viewHolder, int position) {

        if (getItemViewType(position) == DATE) {
            TimePayItem item = (TimePayItem) mPayItems.get(position);
            viewHolder.date.setText(item.getTime());
        } else {
            NormalPayItem item = (NormalPayItem) mPayItems.get(position);
            viewHolder.name.setText(item.getItemLeftTop());
            viewHolder.money.setText(item.getMoney());
            viewHolder.money.setTextColor(item.getMoneyTextColor());
            viewHolder.type.setText(item.getPayType());
            viewHolder.date.setText(item.getDisplayTime());
            if (getItemViewType(position) == DATA_FULL) {
                viewHolder.state.setText(item.getState());
                viewHolder.destination.setText(item.getRefundFolw());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPayItems == null ? 0 : mPayItems.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.date)
        TextView date;

        @Bind(R.id.name)
        @Nullable
        TextView name;

        @Bind(R.id.money)
        @Nullable
        TextView money;

        @Bind(R.id.type)
        @Nullable
        TextView type;

        @Bind(R.id.state)
        @Nullable
        TextView state;

        @Bind(R.id.destination)
        @Nullable
        TextView destination;


        public PaymentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
