package com.homepaas.sls.ui.order.adapter;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.homepaas.sls.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/15.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.OrderHolder> {

    public interface onItemCountChangeListener{

        void onCountChange(int count);
    }

    onItemCountChangeListener listener;
    private List<String> datas;
    private LayoutInflater inflater;
    private Context context;



    public PhotoAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setItemCountChangeListener(onItemCountChangeListener listener) {
        this.listener = listener;
    }

    public onItemCountChangeListener getListener() {
        return listener;
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderHolder(inflater.inflate(R.layout.item_order_picture,parent,false));
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, final int position) {
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(datas.get(position)));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(position);
                listener.onCountChange(datas.size());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.img)
        ImageView imageView;
        @Bind(R.id.delete)
        ImageView delete;

        public OrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
