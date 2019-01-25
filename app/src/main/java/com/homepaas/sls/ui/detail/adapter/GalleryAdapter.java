package com.homepaas.sls.ui.detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.mvp.model.IServiceInfo.Photo;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import java.util.List;

/**
 * 查看图片适配器
 *
 * @author zhudongjie .
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<String> mPhotos;

    private LayoutInflater mInflater;

    public GalleryAdapter(List<String> urlList) {
        mPhotos = urlList;
    }

        public void setPhotos(List<String> photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        int i = position % mPhotos.size();
        holder.setPicture(mPhotos.get(i));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.picture);
        }

        public void setPicture(String photo) {
            Glide.with(picture.getContext())
                    .load(photo)
                    .placeholder(R.mipmap.worker_portrait_default)
                    .into(new ImageTarget(picture));
        }
    }
}
