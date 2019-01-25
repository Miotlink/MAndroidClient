package com.homepaas.sls.ui.newdetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/9.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    public List<String> datas;
    public LayoutInflater inflater;

    public PhotoAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.photo_list_item, parent, false);
        return new PhotoViewHolder(view);
    }

    public interface OnZoomPictureListener{
        void zoom(int position, List<String> photos);
    }
    private OnZoomPictureListener onZoomPictureListener;

    public void setOnZoomPictureListener(OnZoomPictureListener onZoomPictureListener) {
        this.onZoomPictureListener = onZoomPictureListener;
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, int position) {
        holder.bind(datas.get(holder.getAdapterPosition()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onZoomPictureListener != null){
                    onZoomPictureListener.zoom(holder.getAdapterPosition(),datas);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.photo)
        ImageView photo;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bind(String url){
            Glide.with(photo.getContext())
                    .load(url)
                    .placeholder(R.mipmap.portrait_default)
                    .into(photo);
        }
    }
}
