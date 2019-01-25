package com.homepaas.sls.ui.order.manage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.Order;

import java.util.List;

/**
 * Created by CJJ on 2016/9/9.
 *
 */
public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.Holder> {

    List<Order> datas;
    LayoutInflater inflater;

    public void setDatas(List<Order> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());
        return new Holder(inflater.inflate(R.layout.item_order_manage,parent,false));
    }

    @Override
    public int getItemCount() {
        return datas == null?0:datas.size();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
