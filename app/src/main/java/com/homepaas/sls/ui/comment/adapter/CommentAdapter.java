package com.homepaas.sls.ui.comment.adapter;

import android.support.v7.widget.RecyclerView;
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
import com.homepaas.sls.ui.widget.MoreLoadable;
import com.homepaas.sls.ui.widget.Refreshable;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * recyclerView Adapter
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> implements Refreshable<Evaluation>
        , MoreLoadable<Evaluation> {



    public interface AddCommentClickListener {

        void onAdd(int position);
    }

    private AddCommentClickListener mListener;

    private LayoutInflater mInflater;

    private List<Evaluation> comments;

    public CommentAdapter(List<Evaluation> comments) {
        this.comments = comments;
    }

    public void setAddCommentClickListener(AddCommentClickListener addCommentClickListener) {
        mListener = addCommentClickListener;
    }

    @Override
    public void refresh(List<Evaluation> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @Override
    public void addMore(List<Evaluation> moreData) {
        int pos = comments.size();
        comments.addAll(moreData);
        notifyItemRangeInserted(pos,moreData.size());
    }


    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.my_comment_item, parent, false);

        final CommentViewHolder viewHolder = new CommentViewHolder(view);
        viewHolder.addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAdd(viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CommentViewHolder viewHolder, int position) {
        viewHolder.bindData(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();

    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

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

//        @Bind(R.id.comments)
//        RecyclerView comments;

        @Bind(R.id.comment_item)
        View commentItem;

        @Bind(R.id.comment_content)
        TextView commentContent;

        @Bind(R.id.reply_item)
        View replyItem;

        @Bind(R.id.reply_content)
        TextView replyContent;

        @Bind(R.id.add_comment)
        View addCommentButton;

        @Bind(R.id.button_container)
        ViewGroup buttonContainer;


        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Evaluation comment) {
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

            if (comment.getReply() != null) {
                replyItem.setVisibility(View.VISIBLE);
                replyContent.setText(comment.getReply().getContent());
            } else {
                replyItem.setVisibility(View.GONE);
            }

            if (comment.getAdditionalEva() != null) {
                commentItem.setVisibility(View.VISIBLE);
                commentContent.setText(comment.getAdditionalEva().getContent());
            } else {
                commentItem.setVisibility(View.GONE);
            }

            buttonContainer.setVisibility(comment.canBeAdded() ? View.VISIBLE : View.GONE);

        }
    }
}
