package com.homepaas.sls.ui.account.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.AccountDetail;
import com.homepaas.sls.domain.entity.AccountListEntity;
import com.homepaas.sls.domain.entity.SettlementDetailResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/29.
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.DetailsItemView>{

    private LayoutInflater mInflater;
    private List<AccountListEntity.ClientSettlementDetailResponse> details;

    public AccountListAdapter() {
    }

    public void setDetails(List<AccountListEntity.ClientSettlementDetailResponse> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    public void addMore(List<AccountListEntity.ClientSettlementDetailResponse> moreList) {
        int pos = details.size();
        details.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public DetailsItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mInflater==null){
            mInflater=LayoutInflater.from(parent.getContext());
        }
        View view=mInflater.inflate(R.layout.account_list_adapter,parent,false);
        return new DetailsItemView(view);
    }

    @Override
    public void onBindViewHolder(final DetailsItemView holder, final int position) {
            holder.bindData(details.get(position));
            if (onItemOnClickListener != null) {
                holder.detailsItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemOnClickListener.onItemClick(details.get(holder.getAdapterPosition()));
                    }
                });
            }

    }

    @Override
    public int getItemCount() {
        return details==null?0:details.size();
    }

    public static class DetailsItemView extends RecyclerView.ViewHolder{

        @Bind(R.id.details_item)
        LinearLayout detailsItem;
        @Bind(R.id.account_type)
        TextView accountType;
        @Bind(R.id.account_price)
        TextView accountPrice;
        @Bind(R.id.account_time)
        TextView accountTime;
        @Bind(R.id.account_mode)
        TextView accountMode;

        public DetailsItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bindData(AccountListEntity.ClientSettlementDetailResponse item) {
            accountType.setText(item.getTradeType());
            accountTime.setText(TimeUtils.formatNewDate(item.getSettlementTime(), "MM/dd"));
            if(item.getSettlementType().equals("线下支付")){
                accountMode.setText("(线下交易)");
            }else{
                accountMode.setText("");
            }

            if(!TextUtils.isEmpty(item.getSettlementAmount())) {
                if (item.getSettlementAmount().equals("0.00")) {
                    accountPrice.setText(item.getSettlementAmount());
                } else {
                    accountPrice.setText(TextUtils.equals("True", item.getIsMinus()) ? "-" + item.getSettlementAmount() + "" : "+" + item.getSettlementAmount() + "");
                }
            }else{
                accountPrice.setText("");
            }
        }
    }

    public interface OnItemOnClickListener {
        void onItemClick(AccountListEntity.ClientSettlementDetailResponse settlementDetail);
    }
    private OnItemOnClickListener onItemOnClickListener;
    public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener){
        this.onItemOnClickListener=onItemOnClickListener;
    }


}
