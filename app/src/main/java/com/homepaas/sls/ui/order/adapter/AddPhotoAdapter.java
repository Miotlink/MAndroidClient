package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CJJ on 2016/9/16.
 */

public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.Holder> {


    public static final int TYPE_ADD = 1;
    public static final int TYPE_PHOTO = 2;

    List<String> paths;
    LayoutInflater inflater;
    Context context;

    public void addPhoto(String path) {
        if (path == null || paths == null) return;
        paths.add(path);
        notifyItemInserted(paths.size());
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
            context = parent.getContext();
        }
        switch (viewType) {
            case TYPE_ADD:
                return new Holder(inflater.inflate(R.layout.item_add_photo_action, parent, false));
            case TYPE_PHOTO:
                return new Holder(inflater.inflate(R.layout.add_photo_item, parent, false));
        }
        return new Holder(new View(context));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return paths == null || paths.isEmpty() ? 1 : paths.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (paths == null || paths.isEmpty()) {
            return TYPE_ADD;
        } else {
            if (position < paths.size())
                return TYPE_PHOTO;
            else return TYPE_ADD;
        }
    }

    class Holder extends RecyclerView.ViewHolder implements Action {
        @Nullable
        @Bind(R.id.photo)
        ImageView photo;
        @Nullable
        @Bind(R.id.delete)
        ImageView delete;
        @Nullable
        @Bind(R.id.add_photo_action)
        ImageView addPhoto;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind() {
            switch (getItemViewType()) {
                case TYPE_ADD://添加照片
                    addPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            photoListener.startAddPhoto();
                        }
                    });
                    break;
                case TYPE_PHOTO://查看照片
                    photo.setImageBitmap(BitmapFactory.decodeFile(paths.get(getAdapterPosition())));
                    Glide.with(context).load(new File(paths.get(getAdapterPosition()))).into(photo);
                    photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            paths.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                        }
                    });
                    break;
            }
        }
    }

    public interface Action {
        void bind();
    }

    private AddPhotoListener photoListener;

    public void setPhotoListener(AddPhotoListener photoListener) {
        this.photoListener = photoListener;
    }

    public interface AddPhotoListener {

        void startAddPhoto();
    }
}
