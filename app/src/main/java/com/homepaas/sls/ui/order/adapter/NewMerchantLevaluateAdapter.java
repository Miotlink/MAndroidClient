package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.BusinessCommentListOutput;
import com.homepaas.sls.util.listview.ViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by mhy on 2017/12/27.
 */

public class NewMerchantLevaluateAdapter extends CommonAdapter<BusinessCommentListOutput.ListBean> {

    public NewMerchantLevaluateAdapter(Context context, List<BusinessCommentListOutput.ListBean> mDatas) {
        super(context, mDatas,  R.layout.merchant_levaluate_item);
    }

    @Override
    public void convert(ViewHolder holder, BusinessCommentListOutput.ListBean item, int position) {
        View view = holder.getView(R.id.view_interval);
        view.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        RoundedImageView imageView = (RoundedImageView) holder.getView(R.id.rl_user_icon);
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
            holder.setText(R.id.tv_city, item.getCity());
        if (!TextUtils.isEmpty(item.getServiceName()))
            holder.setText(R.id.tv_server_name, item.getServiceName());
        if (!TextUtils.isEmpty(item.getDate()))
            holder.setText(R.id.tv_time, item.getDate());
    }
}
