package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.BusinessCommentListOutput;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by mhy on 2017/12/27.
 */

public class MerchantLevaluateAdapter extends BaseRecyclerAdapter<BusinessCommentListOutput.ListBean> {
    public MerchantLevaluateAdapter(Context ctx) {
        super(ctx, null);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.merchant_levaluate_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, BusinessCommentListOutput.ListBean item) {

        RoundedImageView imageView = (RoundedImageView) holder.getImageView(R.id.rl_user_icon);
        Glide.with(mContext).load(item.getAvatarUrl())
                .fitCenter()
                .placeholder(R.mipmap.user_grade_icon)
                .error(R.mipmap.user_grade_icon)
                .into(imageView);
        if (!TextUtils.isEmpty(item.getPhone()))
            holder.setText(R.id.tv_user_phone, item.getPhone());
        else {
            holder.setText(R.id.tv_user_phone, "");
        }
        RatingBar ratingBar = (RatingBar) holder.getView(R.id.rb_user_grade);
        ratingBar.setRating(item.getStar());
        if (!TextUtils.isEmpty(item.getContent()))
            holder.setText(R.id.tv_levaluate_info, item.getContent());
        else
            holder.setText(R.id.tv_levaluate_info, "");
        if (!TextUtils.isEmpty(item.getCity()))
            holder.setText(R.id.tv_city, item.getCity() );
        if (!TextUtils.isEmpty(item.getServiceName()))
            holder.setText(R.id.tv_server_name, item.getServiceName() );
        if (!TextUtils.isEmpty(item.getDate()))
            holder.setText(R.id.tv_time, item.getDate());
    }
}
