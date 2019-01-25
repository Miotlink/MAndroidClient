package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessOrderServiceListEntity;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;

import java.util.List;

/**
 * Created by mhy on 2017/12/25.
 */

public class MerchantAdapter extends BaseRecyclerAdapter<BusinessOrderServiceListEntity.ListBean> {
    public MerchantAdapter(Context ctx) {
        super(ctx, null);
    }

    public MerchantAdapter(Context ctx, List<BusinessOrderServiceListEntity.ListBean> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.merchant_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final BusinessOrderServiceListEntity.ListBean item) {
        holder.setText(R.id.tv_server_address, item.getServiceName());
        ImageView imageView = holder.getImageView(R.id.img_service_icon);
        Glide.with(mContext).load(item.getIconUrl())
                .fitCenter()
                .placeholder(R.mipmap.default_no_circular_image)
                .error(R.mipmap.default_no_circular_image)
                .into(imageView);

        //PriceType (string, optional): 0：面议，1：固定价格 ,
        holder.setText(R.id.tv_price, "¥\t" + (item.getPriceType().equals("0") ? item.getPrice() + "元订金" : item.getPrice() + "元"));
        RatingBar ratingBar = (RatingBar) holder.getView(R.id.rb_service_grade);
        ratingBar.setRating(item.getGrade());
        holder.setText(R.id.tv_service_grad, item.getGrade() + "");
        holder.setText(R.id.tv_service_distance, item.getDistance());
        holder.setText(R.id.tv_service_name, item.getProviderName());

        holder.setClickListener(R.id.btn_subscribe, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (merchantClickListener != null)
                    merchantClickListener.subscribe(item.getId(), item.getProviderUserId(), item.getServiceName(), item.getProviderName(), item.getProviderUserType());
            }
        });
        holder.setClickListener(R.id.tv_service_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (merchantClickListener != null)
                    merchantClickListener.startMerchantDetail(item.getProviderUserType(), item.getProviderId());
            }
        });
        holder.setClickListener(R.id.img_phone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (merchantClickListener != null)
                    merchantClickListener.callPhone(item.getProviderPhone());
            }
        });
    }

    private onMerchantClickListener merchantClickListener;

    public void setOnMerchantClickListener(onMerchantClickListener merchantClickListener) {
        this.merchantClickListener = merchantClickListener;
    }

    public interface onMerchantClickListener {
        void callPhone(String phone);

        void subscribe(String serviceId, String orderProviderUserId, String serviceName, String providerName, String providerUserType);

        void startMerchantDetail(String userType, String merchantOrWorkerId);
    }
}
