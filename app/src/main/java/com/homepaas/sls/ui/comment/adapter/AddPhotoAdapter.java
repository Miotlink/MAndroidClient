package com.homepaas.sls.ui.comment.adapter;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;

import java.util.List;

/**
 * on 2016/4/15 0015
 *
 * @author zhudongjie .
 */
public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.AddPhotoViewHolder> {

    private static final int TYPE_NORMAL = 1;

    private static final int TYPE_ADD = 2;

    public interface PhotoItem extends Parcelable {

        String getUrl();
    }

    public static class PhotoAdd implements PhotoItem {

        public PhotoAdd() {
        }

        protected PhotoAdd(Parcel in) {
        }

        public static final Creator<PhotoAdd> CREATOR = new Creator<PhotoAdd>() {
            @Override
            public PhotoAdd createFromParcel(Parcel in) {
                return new PhotoAdd(in);
            }

            @Override
            public PhotoAdd[] newArray(int size) {
                return new PhotoAdd[size];
            }
        };

        @Override
        public String getUrl() {
            //ignore
            return null;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

    public static class Photo implements PhotoItem {

        private String url;

        public Photo(String url) {
            this.url = url;
        }

        protected Photo(Parcel in) {
            url = in.readString();
        }

        public static final Creator<Photo> CREATOR = new Creator<Photo>() {
            @Override
            public Photo createFromParcel(Parcel in) {
                return new Photo(in);
            }

            @Override
            public Photo[] newArray(int size) {
                return new Photo[size];
            }
        };

        @Override
        public String getUrl() {
            return url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
        }
    }

    public interface OnItemClickListener {

        void onDeleteClick(int position, PhotoItem item);

        void onAddClick();
    }

    private List<? extends PhotoItem> urlList;

    private OnItemClickListener onItemClickListener;

    private LayoutInflater inflater;

    public AddPhotoAdapter(List<? extends PhotoItem> urlList) {
        this.urlList = urlList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AddPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.add_photo_item, parent, false);
        return new AddPhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddPhotoViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            Glide.with(holder.photo.getContext())
                    .load(urlList.get(position).getUrl())
                    .into(holder.photo);

            if (onItemClickListener != null) {
                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int finalPosition = holder.getAdapterPosition();
                        onItemClickListener.onDeleteClick(finalPosition, urlList.get(finalPosition));
//                        urlList.remove(finalPosition);
//                        notifyItemRemoved(finalPosition);
                    }
                });
            }
        } else {

            holder.photo.setImageResource(R.drawable.add_photo);
            holder.delete.setVisibility(View.GONE);
            if (onItemClickListener != null) {
                holder.photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onAddClick();
                    }
                });
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (urlList.get(position) instanceof PhotoAdd) {
            return TYPE_ADD;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return urlList == null ? 0 : urlList.size();
    }

    public static class AddPhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;

        View delete;

        public AddPhotoViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            delete = itemView.findViewById(R.id.delete);
        }
    }

}
