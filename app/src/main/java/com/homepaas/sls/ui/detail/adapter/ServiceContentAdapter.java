package com.homepaas.sls.ui.detail.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceContent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 服务内容列表适配器
 *
 * @author zhudongjie .
 */
public class ServiceContentAdapter extends RecyclerView.Adapter<ServiceContentAdapter.ServiceContentViewHolder> {

    private List<ServiceContent> mList;

    private LayoutInflater mInflater;

    public ServiceContentAdapter() {
    }

    public ServiceContentAdapter(List<ServiceContent> list) {
        mList = list;
    }

    public void setList(List<ServiceContent> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ServiceContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.service_content_item, parent, false);
        return new ServiceContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceContentViewHolder holder, int position) {
        ServiceContent serviceContent = mList.get(position);
        holder.serviceName.setText(serviceContent.getServiceName());
        Glide.with(holder.itemView.getContext())
                .load(serviceContent.getPicturePath())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.picture);
        holder.serviceDetail.setText(serviceContent.getDetail());
        String priceCount = serviceContent.getPrice();
        SpannableString ss = new SpannableString(priceCount + "元/小时");
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(holder.itemView.getContext(), R.color.decorateOrange)), 0, priceCount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.servicePrice.setText(ss);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ServiceContentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.picture)
        ImageView picture;

        @Bind(R.id.service_name)
        TextView serviceName;

        @Bind(R.id.service_detail)
        TextView serviceDetail;

        @Bind(R.id.service_price)
        TextView servicePrice;

        public ServiceContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
