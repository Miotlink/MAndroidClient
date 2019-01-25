package com.homepaas.sls.ui.order.directOrder.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.embedded_class.Picture;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/5/9.
 */

public class OrderPhotoAdapter extends RecyclerView.Adapter<OrderPhotoAdapter.OrderPhotoView> {
    private LayoutInflater layoutInflater;
    private List<Picture> pictureList;
    private Context context;

    public OrderPhotoAdapter(Context context) {
        this.context = context;
    }

    public void setPhotoList(List<Picture> pictureList) {
        this.pictureList = pictureList;
        notifyDataSetChanged();
    }

    @Override
    public OrderPhotoView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_order_photo, parent, false);
        return new OrderPhotoView(view);
    }

    @Override
    public void onBindViewHolder(final OrderPhotoView holder, int position) {
        Picture picture = pictureList.get(holder.getAdapterPosition());
        Glide.with(context).load(picture.getSmallPic()).placeholder(R.mipmap.client_v3_3_0_ic_tab_my_open).into(holder.picture);
        if (onPictureOnClickListener != null) {
            holder.picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPictureOnClickListener.zomm(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return pictureList == null ? 0 : pictureList.size();
    }

    public static class OrderPhotoView extends RecyclerView.ViewHolder {
        @Bind(R.id.picture)
        RoundedImageView picture;
        public OrderPhotoView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnPictureOnClickListener {
        void zomm(int position);
    }

    private OnPictureOnClickListener onPictureOnClickListener;

    public void setOnPictureOnClickListener(OnPictureOnClickListener onPictureOnClickListener) {
        this.onPictureOnClickListener = onPictureOnClickListener;
    }
}
