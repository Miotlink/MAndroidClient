package com.homepaas.sls.ui.comment.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * on 2016/4/14 0014
 *
 * @author zhudongjie .
 */
//never used
public class CommentAddedAdapter extends RecyclerView.Adapter<CommentAddedAdapter.CommentViewHolder> {


    private List<Pair<String, String>> list;

    private LayoutInflater inflater;

    public CommentAddedAdapter(List<Pair<String, String>> list) {
        this.list = list;
    }

    public void setList(List<Pair<String, String>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.added_comment_item, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Pair<String, String> item = list.get(position);
        holder.commentType.setText(item.first);
        holder.commentContent.setText(item.second);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.comment_type)
        TextView commentType;

        @Bind(R.id.comment_content)
        TextView commentContent;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
