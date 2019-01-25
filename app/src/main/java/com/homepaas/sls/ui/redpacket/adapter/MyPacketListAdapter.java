package com.homepaas.sls.ui.redpacket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.homepaas.sls.R;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class MyPacketListAdapter extends RecyclerView.Adapter<MyPacketListAdapter.Holder> {

    private List<? extends Object> datas;
    private LayoutInflater inflater;
    private Context context;

    public MyPacketListAdapter(List<? extends Object> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.item_my_redpacket_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas == null? 0:datas.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
