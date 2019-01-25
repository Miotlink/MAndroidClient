package com.homepaas.sls.ui.detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.ui.comment.adapter.CommentPhotoAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 客户评价列表适配器
 *
 * @author zhudongjie .
 */
public class CustomerReviewAdapter extends RecyclerView.Adapter<CustomerReviewAdapter.ReviewViewHolder> {


    private List<Evaluation> mList;

    private LayoutInflater mInflater;


    public CustomerReviewAdapter() {
    }

    public CustomerReviewAdapter(List<Evaluation> list) {
        mList = list;
    }

    public void setList(List<Evaluation> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addMore(List<Evaluation> moreList){
        int pos = mList.size();
        mList.addAll(moreList);
        notifyItemRangeInserted(pos,moreList.size());
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.customer_review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.photo)
        ImageView headPortrait;

        @Bind(R.id.customer_name)
        TextView name;

        @Bind(R.id.date)
        TextView date;

        @Bind(R.id.ratingbar)
        RatingBar ratingBar;

        @Bind(R.id.rating)
        TextView rating;

        @Bind(R.id.customer_review)
        TextView review;

        @Bind(R.id.phones)
        RecyclerView photos;

        @Bind(R.id.comment_item)
        View commentItem;

        @Bind(R.id.comment_content)
        TextView commentContent;

        @Bind(R.id.reply_item)
        View replyItem;

        @Bind(R.id.reply_content)
        TextView replyContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Evaluation comment) {
            Glide.with(headPortrait.getContext())
                    .load(comment.getClientPhoto())
                    .placeholder(R.mipmap.head_portrait_default)
                    .into(headPortrait);
            name.setText(comment.getClientName());
            date.setText(TimeUtils.formatDate(comment.getDate()));
            review.setText(comment.getContent());
            ratingBar.setRating(Float.parseFloat(comment.getScore()));
            rating.setText(comment.getScore());
            CommentPhotoAdapter photoAdapter = (CommentPhotoAdapter) photos.getAdapter();
            if (photoAdapter == null) {
                photoAdapter = new CommentPhotoAdapter(comment.getPictures());
                photos.setAdapter(photoAdapter);
            } else {
                photoAdapter.setUrlList(comment.getPictures());
            }

            if (comment.getAdditionalEva()!=null){
                commentItem.setVisibility(View.VISIBLE);
                commentContent.setText(comment.getAdditionalEva().getContent());
            }else {
                commentItem.setVisibility(View.GONE);
            }

            if (comment.getReply()!=null){
                replyItem.setVisibility(View.VISIBLE);
                replyContent.setText(comment.getReply().getContent());
            }else {
                replyItem.setVisibility(View.GONE);
            }

        }
    }


}
