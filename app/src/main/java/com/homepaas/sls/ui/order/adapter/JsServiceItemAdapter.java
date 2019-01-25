package com.homepaas.sls.ui.order.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.jsinterface.entity.DirectOrderCommand;
import com.homepaas.sls.ui.adapter.ServiceItemAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/3/10.
 */

public class JsServiceItemAdapter  extends RecyclerView.Adapter<JsServiceItemAdapter.ItemViewHolder>{
    private List<DirectOrderCommand> directOrderCommandList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;


    public JsServiceItemAdapter() {

    }

    public void setDirectOrderCommandList(List<DirectOrderCommand> list) {
        this.directOrderCommandList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View view = mLayoutInflater.inflate(R.layout.services_type_select_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.setModel(directOrderCommandList.get(position));
        if(onItemOnClickListener!=null){
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemOnClickListener.onItemClick(directOrderCommandList.get(holder.getAdapterPosition()));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return directOrderCommandList == null ? 0 : directOrderCommandList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_textView)
        TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setModel(DirectOrderCommand command) {
            textView.setText(command.getTypeName());
        }
    }

    public interface OnItemOnClickListener {
        void onItemClick(DirectOrderCommand command);
    }
    private OnItemOnClickListener onItemOnClickListener;
    public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener){
        this.onItemOnClickListener=onItemOnClickListener;
    }
}
