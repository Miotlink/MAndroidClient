package com.homepaas.sls.ui.redpacket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.homepaas.sls.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/19.
 */
public class ActivityPacketListAdapter extends RecyclerView.Adapter<ActivityPacketListAdapter.Holder> {

    private List<? extends Object> datas;
    private LayoutInflater inflater;
    private Context context;

    public ActivityPacketListAdapter(List<? extends Object> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.item_packet_activity_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "领取成功", Toast.LENGTH_SHORT).show();
                v.setEnabled(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null? 0:datas.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @Bind(R.id.packet_img)
        ImageView packetImg;
        @Bind(R.id.packet_info)
        TextView packetInfo;
        @Bind(R.id.fetch_button)
        TextView fetchButton;

        public  Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
