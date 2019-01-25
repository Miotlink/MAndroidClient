package com.homepaas.sls.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.SystemCertificationMapper;

import java.util.List;

/**
 * on 2015/12/25 0025
 *
 * @author zhudongjie .
 */
public class AuthenticateIconAdapter extends RecyclerView.Adapter<AuthenticateIconAdapter.ViewHolder> {

    private List<SystemCertificationMapper.SystemCertification> mIconList;

    private LayoutInflater mInflater;

    public AuthenticateIconAdapter(List<SystemCertificationMapper.SystemCertification> iconList) {
        mIconList = iconList;
    }


    public void setSystemCertificationList(List<SystemCertificationMapper.SystemCertification> iconList) {
        mIconList = iconList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.detail_authenticate_icon_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.icon.getContext())
                .load(mIconList.get(position).getImage())
                .placeholder(R.mipmap.authenticate_default_small)
                .into(holder.icon);
       // holder.icon.setImageResource(R.mipmap.authenticate_organization);
    }

    @Override
    public int getItemCount() {
        return mIconList == null ? 0 : mIconList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}
