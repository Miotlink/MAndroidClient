package com.homepaas.sls.ui.comment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.Evaluation;

import java.util.List;

/**
 * on 2016/4/14 0014
 *
 * @author zhudongjie .
 */
public class CommentPhotoAdapter extends RecyclerView.Adapter<CommentPhotoAdapter.CommentPhotoViewHolder> {

    private List<Evaluation.Picture> urlList;

    private LayoutInflater inflater;

    public CommentPhotoAdapter(List<Evaluation.Picture> urlList) {
        this.urlList = urlList;
    }

    public void setUrlList(List<Evaluation.Picture> urlList) {
        this.urlList = urlList;
        notifyDataSetChanged();
    }

    @Override
    public CommentPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.comment_photo_item, parent, false);
        return new CommentPhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentPhotoViewHolder holder, int position) {
        Glide.with(holder.photo.getContext())
                .load(urlList.get(position).getSmallPic())
                .placeholder(R.mipmap.portrait_default)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return urlList == null ? 0 : urlList.size();
    }

    public static class CommentPhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;

        public CommentPhotoViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.photo);
        }
    }
}
