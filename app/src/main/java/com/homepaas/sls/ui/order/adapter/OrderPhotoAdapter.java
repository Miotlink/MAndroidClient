package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.embedded_class.Picture;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/15.
 */
public class OrderPhotoAdapter extends RecyclerView.Adapter<OrderPhotoAdapter.OrderHolder> {

    public interface onItemCountChangeListener{

        void onCountChange(int count);
    }

    public interface OnItemClickListener{
        void onItemClick(int index);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    onItemCountChangeListener listener;
    private List<Picture> datas;
    private LayoutInflater inflater;
    private Context context;
    private boolean editable = true;

    public OrderPhotoAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 是否可以编辑图片，操作红叉
     * @param context
     * @param isEditable
     */
    public OrderPhotoAdapter(Context context,boolean isEditable){
        this.context = context;
        this.editable = isEditable;
        inflater = LayoutInflater.from(context);
    }

    public OrderPhotoAdapter(Context context, List<Picture> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Picture> photos){
        this.datas = photos;
        notifyDataSetChanged();
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
    public void onBindViewHolder(final OrderHolder holder, int position) {
        Glide.with(context).load(datas.get(position).getSmallPic()).into(holder.imageView);
        if (editable) {
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datas.remove(holder.getAdapterPosition());
                    if (listener != null) {
                        listener.onCountChange(datas.size());
                        notifyDataSetChanged();
                    }
                }
            });
        }else{
            holder.delete.setEnabled(false);
            holder.delete.setVisibility(View.GONE);
        }
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
