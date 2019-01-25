package com.homepaas.sls.ui.homepage_3_3_0.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.util.SpannableStringUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/3/22.
 */

public class ServiceNavigationAdapter extends RecyclerView.Adapter<ServiceNavigationAdapter.ServiceNavigationViewHolder> {


    private List<ServiceItem> datas;
    private Context context;
    private LayoutInflater inflater;
    private int recyclerviewPadding;
    private int decoration;

    public ServiceNavigationAdapter(List<ServiceItem> datas) {
        this.datas = datas;
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
    public ServiceNavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
            inflater = LayoutInflater.from(context);
        }
        View view = inflater.inflate(R.layout.service_navigation_list_item, parent, false);
        return new ServiceNavigationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ServiceNavigationViewHolder holder, int position) {
        if (datas != null) {
            holder.bind(datas.get(holder.getAdapterPosition()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(datas.get(holder.getAdapterPosition()));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ServiceNavigationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        RoundedImageView icon;
        @Bind(R.id.icon_des)
        TextView iconDes;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.price)
        TextView price;

        public ServiceNavigationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //3.3.0需要在首页展示3张图片，直接按照屏幕宽度来手动设置宽高，但不够优雅，建议后期考虑重写LayoutManager
            FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            int width = dm.widthPixels;
            int imagWidth = width - getRecyclerviewPadding() - getDecoration() * (getItemCount() - 1);
            ll.width = imagWidth / 3;
            ll.height = imagWidth / 3;
            icon.setLayoutParams(ll);


        }

        public void bind(ServiceItem recommendItem) {
            iconDes.setVisibility(View.GONE);
            if (recommendItem != null) {
                if (recommendItem.getLogoUrl() != null && recommendItem.getLogoUrl().size() > 0 && !TextUtils.isEmpty(recommendItem.getLogoUrl().get(0))) {
                    Glide.with(icon.getContext())
                            .load(recommendItem.getLogoUrl().get(0))
                            .placeholder(R.mipmap.default_image)
                            .error(R.mipmap.default_image)
                            .centerCrop()
                            .into(icon);
                }
                name.setText(recommendItem.getServiceName());
                double priceNumber = Double.valueOf(recommendItem.getPrice());
                NumberFormat nf = NumberFormat.getInstance();
                String units = "";
                if (TextUtils.equals("0", recommendItem.getSellType())) {
                    units = recommendItem.getUnit() + "起";
                } else {
                    units = recommendItem.getUnit();
                }
                String ss = nf.format(priceNumber) + "元/" + units;
//                int color, float textSize, String text, String regx, TextView view

                price.setText(SpannableStringUtil.matcherSearchText(Color.parseColor("#f56165"), 18f, ss, "\\d+(\\.\\d+)?", price));
            }

        }
    }
}
