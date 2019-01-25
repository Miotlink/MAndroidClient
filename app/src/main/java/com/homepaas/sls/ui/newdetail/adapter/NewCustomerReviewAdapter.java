package com.homepaas.sls.ui.newdetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.PhoneNumberUtils;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.ui.comment.adapter.CommentPhotoAdapter;
import com.homepaas.sls.ui.widget.MoreLoadable;
import com.homepaas.sls.ui.widget.MyRatingBar;
import com.homepaas.sls.ui.widget.Refreshable;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sherily on 2017/2/8.
 */

public class NewCustomerReviewAdapter extends RecyclerView.Adapter<NewCustomerReviewAdapter.CustomerItemView> implements Refreshable<Evaluation>, MoreLoadable<Evaluation> {



    private List<Evaluation> mList;

    private LayoutInflater mInflater;

    public void setList(List<Evaluation> list) {
        mList = list;
//        notifyDataSetChanged();
    }

    @Override
    public void refresh(List<Evaluation> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public void addMore(List<Evaluation> moreList) {
        int pos = mList.size();
        mList.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public CustomerItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.new_customer_review_item_layout, parent, false);
        return new CustomerItemView(view);
    }

    @Override
    public void onBindViewHolder(CustomerItemView holder, int position) {
        holder.bind(mList.get(holder.getAdapterPosition()));

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public static class CustomerItemView extends RecyclerView.ViewHolder {
        @Bind(R.id.photo)
        RoundedImageView photo;
        @Bind(R.id.telephone)
        TextView telephone;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.rating_bar)
        MyRatingBar ratingBar;
        @Bind(R.id.comment_content)
        TextView commentContent;
        @Bind(R.id.phones)
        RecyclerView photos;

        public CustomerItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Evaluation comment) {
            Glide.with(photo.getContext())
                    .load(comment.getClientPhoto())
                    .placeholder(R.mipmap.client_v3_1_0_ic_homepage_default_avatar)
                    .into(photo);
            telephone.setText(PhoneNumberUtils.encryptPhone(comment.getClientPhone()));
            date.setText(TimeUtils.formatOrderTime(comment.getDate()));
            ratingBar.setmScope(Float.parseFloat(comment.getScore()));
            if (!TextUtils.isEmpty(comment.getContent())) {
                commentContent.setVisibility(View.VISIBLE);
                commentContent.setText(comment.getContent());
            } else {
                commentContent.setVisibility(View.GONE);
            }
            CommentPhotoAdapter photoAdapter = (CommentPhotoAdapter) photos.getAdapter();
            if (photoAdapter == null) {
                photoAdapter = new CommentPhotoAdapter(comment.getPictures());
                photos.setAdapter(photoAdapter);
            } else {
                photoAdapter.setUrlList(comment.getPictures());
            }

        }
    }
}
