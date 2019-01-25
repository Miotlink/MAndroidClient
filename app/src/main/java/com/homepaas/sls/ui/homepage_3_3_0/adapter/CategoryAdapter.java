package com.homepaas.sls.ui.homepage_3_3_0.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.redpacket.newRedpacket.NewUsedRedPacketFragment;
import com.homepaas.sls.ui.widget.Refreshable;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/24.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements Refreshable<ServiceItem> {

    private Context context;
    private LayoutInflater inflater;
    private List<ServiceItem> serviceItems;

    public CategoryAdapter(List<ServiceItem> serviceItems) {
        this.serviceItems = serviceItems;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            context = parent.getContext();
            inflater = LayoutInflater.from(context);
        }
        View view = inflater.inflate(R.layout.category_item_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    public interface OnShowDetailListener {
        void showDetail(ServiceItem serviceItem);
    }
    private OnShowDetailListener onShowDetailListener;

    public void setOnShowDetailListener(OnShowDetailListener onShowDetailListener) {
        this.onShowDetailListener = onShowDetailListener;
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {
            holder.bind(serviceItems.get(holder.getAdapterPosition()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onShowDetailListener != null){
                        onShowDetailListener.showDetail(serviceItems.get(holder.getAdapterPosition()));
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return serviceItems == null ? 0 : serviceItems.size();
    }

    @Override
    public void refresh(List<ServiceItem> list) {
        serviceItems = list;
        notifyDataSetChanged();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        RoundedImageView icon;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.subtitle)
        TextView subtitle;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(ServiceItem serviceItem){
            if (serviceItem != null){
                Glide.with(icon.getContext())
                        .load(serviceItem.getIconUrl())
                        .placeholder(R.mipmap.client_v330_ic_homepage_more_white)
                        .error(R.mipmap.client_v330_ic_homepage_more_white)
                        .centerCrop()
                        .into(icon);
                title.setText(serviceItem.getServiceName());
                subtitle.setText(serviceItem.getSubtitle());
            }
        }
    }
}
