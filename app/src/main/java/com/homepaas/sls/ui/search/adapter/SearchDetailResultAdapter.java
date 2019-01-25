package com.homepaas.sls.ui.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/3/23.
 */

public class SearchDetailResultAdapter extends RecyclerView.Adapter<SearchDetailResultAdapter.DetailResultView> {
    private LayoutInflater layoutInflater;
    private List<ServiceItem> serviceItemList;

    public void setDetailResultViewList(List<ServiceItem> serviceItemList) {
        this.serviceItemList = serviceItemList;
        notifyDataSetChanged();
    }

    @Override
    public DetailResultView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_detail_result, parent, false);
        return new DetailResultView(view);
    }

    @Override
    public void onBindViewHolder(DetailResultView holder, int position) {
        final ServiceItem serviceItem=serviceItemList.get(holder.getAdapterPosition());
        holder.detailServiceName.setText(serviceItem.getServiceName());
        holder.detailServiceContnet.setText(serviceItem.getSubtitle());
        if (onDetailResultClickListener != null) {
            holder.detailResultLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDetailResultClickListener.detailResultClickListener(serviceItem);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return serviceItemList == null ? 0 : serviceItemList.size();
    }

    public static class DetailResultView extends RecyclerView.ViewHolder {
        @Bind(R.id.detail_service_name)
        TextView detailServiceName;
        @Bind(R.id.detail_service_contnet)
        TextView detailServiceContnet;
        @Bind(R.id.go_next)
        ImageView goNext;
        @Bind(R.id.detail_result_lin)
        LinearLayout detailResultLin;
        @Bind(R.id.fenge_view)
        View fengeView;

        public DetailResultView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnDetailResultClickListener {
        void detailResultClickListener(ServiceItem serviceItem);
    }

    private OnDetailResultClickListener onDetailResultClickListener;

    public void setOnDetailResultClickListener(OnDetailResultClickListener onDetailResultClickListener) {
        this.onDetailResultClickListener = onDetailResultClickListener;
    }

}
