package com.homepaas.sls.ui.order.directOrder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.widget.glide.ImageTarget;
import com.homepaas.sls.util.ArithUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/3/23.
 */

public class SubsetOrderServicesAdapter extends RecyclerView.Adapter<SubsetOrderServicesAdapter.SubsetOrderServicesView> {
    private LayoutInflater layoutInflater;
    private List<ServiceItem> serviceItemList;
    private int selectPosition = -1;
    private String serverNumber;
    private Context context;

    public SubsetOrderServicesAdapter(Context context) {
        this.context = context;
    }

    public void setServiceItemList(List<ServiceItem> serviceItemLis) {
        this.serviceItemList = serviceItemLis;
//        notifyDataSetChanged();
    }

    public void setItemClickChange(int selectPosition,String serverNumber) {
        this.selectPosition = selectPosition;
        this.serverNumber = serverNumber;
        notifyDataSetChanged();
    }

    public void setServiceNumberAddOrSubtract(String serverNumber) {
        this.serverNumber = serverNumber;
        notifyDataSetChanged();
    }

//
//    public void setItemClickChange(int selectPosition, double serverNumber) {
//        this.selectPosition = selectPosition;
//        this.serverNumber = serverNumber;
//        notifyDataSetChanged();
//    }

    @Override
    public SubsetOrderServicesView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_subset_order_services, parent, false);
        return new SubsetOrderServicesView(view);
    }

    @Override
    public void onBindViewHolder(final SubsetOrderServicesView holder, final int position) {
        ServiceItem serviceItem = serviceItemList.get(position);
        Glide.with(context).load(serviceItem.getIconUrl()).placeholder(R.mipmap.default_image)
                .into(new ImageTarget(holder.serviceIcon));
        holder.serviceName.setText(serviceItem.getServiceName());
        if (!TextUtils.isEmpty(serviceItem.getSellType()) && TextUtils.equals(serviceItem.getSellType(), "0")) {
            holder.servicePrice.setText(serviceItem.getDepositAmount() + "元/" + serviceItem.getDepositUnit());
        } else {
            String mServerNumberPrice = serviceItem.getPrice();
            if (!TextUtils.isEmpty(serverNumber)) {
                List<ServiceItem.RangePrice> rangePrices = serviceItem.getRangePrices();
                double number = ArithUtil.getType(serverNumber).doubleValue();
                //如果集合数量为0或者集合为空，则表示没有区间单价，直接使用默认的服务单价
                if (rangePrices != null && rangePrices.size() > 0) {
                    //多个区间需要遍历
                    for (int i = 0; i < rangePrices.size(); i++) {
                        ServiceItem.RangePrice rangePrice = rangePrices.get(i);
                        if (number <= rangePrice.getMaxCount() && number >= rangePrice.getMinCount()) //满足某个区间直接退出
                        {
                            mServerNumberPrice = rangePrice.getPrice();
                            break;
                        }
                    }
                }
            }
            holder.servicePrice.setText(mServerNumberPrice + "元/" + serviceItem.getUnit());
        }
        if (selectPosition == position) {
            holder.itemSelected.setImageResource(R.mipmap.client_v330_ic_orders_pitch_on);
        } else {
            holder.itemSelected.setImageResource(R.mipmap.subscribe_order_list_no_select);
        }
        if (onItemClickListener != null) {
            holder.selectedRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.itemClickListener(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return serviceItemList == null ? 0 : serviceItemList.size();
    }

    public static class SubsetOrderServicesView extends RecyclerView.ViewHolder {
        @Bind(R.id.service_icon)
        ImageView serviceIcon;
        @Bind(R.id.service_name)
        TextView serviceName;
        @Bind(R.id.service_price)
        TextView servicePrice;
        @Bind(R.id.item_selected)
        ImageView itemSelected;
        @Bind(R.id.selected_rel)
        RelativeLayout selectedRel;

        public SubsetOrderServicesView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void itemClickListener(int selectPosition);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
