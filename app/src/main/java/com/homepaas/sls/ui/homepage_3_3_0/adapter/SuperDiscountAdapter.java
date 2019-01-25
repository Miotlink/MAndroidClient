package com.homepaas.sls.ui.homepage_3_3_0.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Sherily on 2017/7/14.
 */

public class SuperDiscountAdapter extends RecyclerView.Adapter<SuperDiscountAdapter.SuperDiscountViewHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<ServiceItem> serviceItems;
    private int recyclerviewPadding;
    private int decoration;

    public void setData(List<ServiceItem> serviceItems) {
        this.serviceItems = serviceItems;
        notifyDataSetChanged();
    }

    @Override
    public SuperDiscountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == layoutInflater) {
            mContext = parent.getContext();
            layoutInflater = LayoutInflater.from(mContext);
        }
        View view = layoutInflater.inflate(R.layout.service_discount_list_item, parent, false);
        return new SuperDiscountViewHolder(view);
    }

    public interface OnTakeTheDiscount {
        void disCount(ServiceItem serviceItem);
    }

    private OnTakeTheDiscount onTakeTheDiscount;

    public void setOnTakeTheDiscount(OnTakeTheDiscount onTakeTheDiscount) {
        this.onTakeTheDiscount = onTakeTheDiscount;
    }

    public void setRecyclerviewPadding(int recyclerviewPadding) {
        this.recyclerviewPadding = recyclerviewPadding;
    }

    public void setDecoration(int decoration) {
        this.decoration = decoration;
    }

    public int getRecyclerviewPadding() {
        return recyclerviewPadding;
    }

    public int getDecoration() {
        return decoration;
    }

    @Override
    public void onBindViewHolder(final SuperDiscountViewHolder holder, final int position) {
        holder.bind();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onTakeTheDiscount) {
                    onTakeTheDiscount.disCount(serviceItems.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceItems == null ? 0 : serviceItems.size();
    }

    public class SuperDiscountViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_discounts)
        ImageView imgDiscounts;
        //后台返回的就是圆角，所以不需要处理
//        RoundedImageView icon;
        //优惠后价格信息
        @Bind(R.id.icon_des)
        TextView iconDes;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.price)
        TextView price;
//        @Bind(R.id.cardView)
//        CardView cardView;

        public SuperDiscountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.gougou, 0, 0, 0);
            name.setCompoundDrawablePadding(5);
            price.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.gougou, 0, 0, 0);
            price.setCompoundDrawablePadding(5);

//            Resources resources = mContext.getResources();
//            DisplayMetrics dm = resources.getDisplayMetrics();
//            int width = dm.widthPixels;
//            int imagWidth = width - getRecyclerviewPadding() - getDecoration() * (getItemCount() - 1);
//            int oneImageWidth = imagWidth / 3;
//            icon.setMinimumWidth(oneImageWidth);
//            cardView.setMinimumWidth(oneImageWidth);
        }

        public void bind() {
            ServiceItem serviceItem = serviceItems.get(getAdapterPosition());
            if (serviceItem.getSuperDiscountItem() != null) {
                ServiceItem.SuperDiscountItem superDiscountItem = serviceItem.getSuperDiscountItem();
                String key = "￥" + superDiscountItem.getAmount();
                iconDes.setText(key);
                if (!superDiscountItem.getDiscountContent().isEmpty()) {
                    name.setVisibility(View.VISIBLE);
                    name.setText(superDiscountItem.getDiscountContent().get(0));
                    name.setTextSize(12f);
                    name.setTextColor(ContextCompat.getColor(name.getContext(), R.color.appText10));
                    if (superDiscountItem.getDiscountContent().size() >= 2) {
                        price.setVisibility(View.VISIBLE);
                        price.setText(superDiscountItem.getDiscountContent().get(1));
                        price.setTextSize(12f);
                        price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.appText10));
                    } else {
                        price.setVisibility(View.GONE);
                    }
                } else {
                    name.setVisibility(View.GONE);
                    price.setVisibility(View.GONE);
                }

                Glide.with(imgDiscounts.getContext())
                        .load(serviceItem.getIconUrl())
                        .placeholder(R.mipmap.default_image)
                        .crossFade()
                        .error(R.mipmap.default_image)
                        .into(imgDiscounts);
            }
        }
    }
}
