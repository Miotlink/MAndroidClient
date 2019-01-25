package com.homepaas.sls.ui.account.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.AccountMessage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AccountDetailsAdapter extends RecyclerView.Adapter<AccountDetailsAdapter.ReviewViewHolder> {

    List<AccountMessage> datas;
    LayoutInflater mInflater;
    Context context;
    private String orderId;


    public AccountDetailsAdapter(Context context) {
        this.context = context;
    }

    public AccountDetailsAdapter(List<AccountMessage> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public void setOrderId(String orderId){
        this.orderId=orderId;
    }

    public void setDatas(List<AccountMessage> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        View view = mInflater.inflate(R.layout.account_details_adapter, parent, false);
        return new ReviewViewHolder(view);
    }

    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder,  int position) {
        AccountMessage msg = datas.get(position);
        if(!TextUtils.isEmpty(orderId)){
            if(TextUtils.equals("订单号",msg.getKey())){
                holder.message_content.setTextColor(Color.parseColor("#27b8f3"));
            }
        }else{
            holder.message_content.setTextColor(Color.parseColor("#999999"));
        }
        holder.message_name.setText(msg.getKey());
        holder.message_content.setText(msg.getValue());
        if (onItemOnClickListener!=null) {
            holder.message_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  onItemOnClickListener.onItemClick();
                }
            });
        }
    }


    public interface OnItemOnClickListener{
        void onItemClick();
    }

    private OnItemOnClickListener onItemOnClickListener;

    public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.onItemOnClickListener = onItemOnClickListener;
    }


    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.message_name)
        TextView message_name;
        @Bind(R.id.message_content)
        TextView message_content;


        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

