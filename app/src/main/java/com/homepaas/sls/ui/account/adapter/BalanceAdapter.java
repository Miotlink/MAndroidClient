package com.homepaas.sls.ui.account.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.AccountDetail;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * recyclerView Adapter
 */
public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder> {


    private LayoutInflater mInflater;

    private List<AccountDetail> details;

    public BalanceAdapter(List<AccountDetail> dataList) {
        details = dataList;
    }

    public void setDetails(List<AccountDetail> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @Override
    public BalanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.balance_item, parent, false);

        return new BalanceViewHolder(view);

    }

    @Override
    public void onBindViewHolder(BalanceViewHolder viewHolder, int position) {
        viewHolder.bindData(details.get(position));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }


    public static class BalanceViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.date)
        TextView date;

        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.money)
        TextView money;

        @Bind(R.id.type)
        TextView type;

        public BalanceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(AccountDetail item) {
            name.setText(item.getOperateType());
            date.setText(TimeUtils.formatDate(item.getTime()));
            float fMoney = Float.parseFloat(item.getMoney());
            if (fMoney > 0) {
                money.setTextColor(Color.argb(255, 255, 88, 102));
            } else {
                money.setTextColor(Color.argb(255, 42, 189, 135));
            }
            String strMoney = item.getMoney() + "元";
            money.setText(strMoney);
            type.setText(item.getPaymentMode());
        }
    }


}
