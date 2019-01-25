package com.homepaas.sls.ui.order.directOrder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/5/27.
 */

public class OrderCancelReasonsAdapter extends RecyclerView.Adapter<OrderCancelReasonsAdapter.OrderCancelReasonsView> {
    private LayoutInflater layoutInflater;
    private int selectPosition = -1;
    private List<String> list;

    public void setSelectPositin(int lastSelectPosition, int selectPosition) {
        this.selectPosition = selectPosition;
        notifyItemChanged(lastSelectPosition);
        notifyItemChanged(selectPosition);
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public OrderCancelReasonsView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_order_cancel_reasons, parent, false);
        return new OrderCancelReasonsView(view);
    }

    @Override
    public void onBindViewHolder(final OrderCancelReasonsView holder, int position) {
        final String s = list.get(holder.getAdapterPosition());
        holder.cancelReasons.setText(s);
        if(selectPosition==position){
            holder.itemSelected.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
        }else{
            holder.itemSelected.setImageResource(R.mipmap.client_v330_ic_orders_choose);
        }
        if(onItemClickListener!=null){
            holder.cancelItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.itemClickListener(holder.getAdapterPosition(),s);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public static class OrderCancelReasonsView extends RecyclerView.ViewHolder {
        @Bind(R.id.cancel_reasons)
        TextView cancelReasons;
        @Bind(R.id.item_selected)
        ImageView itemSelected;
        @Bind(R.id.cancel_item)
        RelativeLayout cancelItem;
        public OrderCancelReasonsView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void itemClickListener(int selectPosition,String s);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
