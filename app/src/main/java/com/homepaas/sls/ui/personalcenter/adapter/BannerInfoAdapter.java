package com.homepaas.sls.ui.personalcenter.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.ui.widget.Refreshable;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.homepaas.sls.ui.widget.PickerView.TAG;

/**
 * Created by Administrator on 2016/12/20.
 */

public class BannerInfoAdapter extends RecyclerView.Adapter<BannerInfoAdapter.ImageviewViewHolder> implements Refreshable<BannerInfo> {

    public static final String TAG = "BannerInfoAdapter";
    private List<BannerInfo> bannerInfos;
    private LayoutInflater layoutInflater;
    private Context context;

    public BannerInfoAdapter(List<BannerInfo> bannerInfos,Context context) {
        this.bannerInfos = bannerInfos;
        this.context = context;
//        record(bannerInfos);
    }
    private void record(List<BannerInfo> bannerInfos){
        String maxId = "0";
        for (BannerInfo bannerInfo : bannerInfos){
           if ((bannerInfo.getId().compareTo(maxId)) >= 0){
               maxId = bannerInfo.getId();
           }
        }
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("MAX_ACTIVITY_ID",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("MAX_ACTIVITY_ID",maxId).commit();
    }
    public void record(String version){
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("MAX_ACTIVITY_ID",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("MAX_ACTIVITY_ID",version).commit();
    }

    @Override
    public ImageviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (layoutInflater == null)
        layoutInflater = LayoutInflater.from(context);
        return new ImageviewViewHolder(layoutInflater.inflate(R.layout.image_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ImageviewViewHolder holder, int position) {
        Glide.with(context)
                .load(bannerInfos.get(holder.getAdapterPosition()).getImageUrl())
                .placeholder(R.mipmap.activity)
                .fitCenter()
                .into(new ImageTarget(holder.bannerImageview));
        Log.d(TAG, "onBindViewHolder: "+ bannerInfos.get(holder.getAdapterPosition()).getImageUrl());
        if (onBannerItemClickListener != null){
            holder.bannerImageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBannerItemClickListener.show(bannerInfos.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    public interface onBannerItemClickListener{
        void show(BannerInfo bannerInfo);
    }
    private onBannerItemClickListener onBannerItemClickListener;

    public void setOnBannerItemClickListener(BannerInfoAdapter.onBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    public int getItemCount() {
        return bannerInfos.size();
    }

    @Override
    public void refresh(List<BannerInfo> list) {
//        record(list);
        bannerInfos = list;
        notifyDataSetChanged();
    }

    public static class ImageviewViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.banner_imageview)
        ImageView bannerImageview;
        public ImageviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
