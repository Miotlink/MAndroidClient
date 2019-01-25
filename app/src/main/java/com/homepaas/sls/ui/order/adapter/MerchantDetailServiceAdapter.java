package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessServiceListEntity;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;

import java.util.List;

/**
 * Created by mhy on 2017/12/25.
 */

public class MerchantDetailServiceAdapter extends BaseRecyclerAdapter<BusinessServiceListEntity.ListBean> {
    public MerchantDetailServiceAdapter(Context ctx) {
        super(ctx, null);
    }

    public MerchantDetailServiceAdapter(Context ctx, List<BusinessServiceListEntity.ListBean> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.merchant_detail_service_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final BusinessServiceListEntity.ListBean item) {

        ImageView imageView = holder.getImageView(R.id.img_service_icon);
        Glide.with(mContext).load(item.getIconUrl())
                .fitCenter()
                .placeholder(R.mipmap.default_no_circular_image)
                .error(R.mipmap.default_no_circular_image)
                .into(imageView);

        holder.setText(R.id.tv_price,  "¥\t" + item.getPrice());
        holder.setText(R.id.tv_server_name, item.getName());
        holder.setClickListener(R.id.btn_subscribe, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (merchantClickListener != null)
                    merchantClickListener.subscribe(item.getId(),item.getName());
            }
        });
    }

    private onMerchantClickListener merchantClickListener;

    public void setOnMerchantClickListener(onMerchantClickListener merchantClickListener) {
        this.merchantClickListener = merchantClickListener;
    }

    public interface onMerchantClickListener {
        void subscribe(String serviceId,String serviceName);
    }
}
