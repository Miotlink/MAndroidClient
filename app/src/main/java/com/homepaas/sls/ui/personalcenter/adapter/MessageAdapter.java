package com.homepaas.sls.ui.personalcenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.ui.widget.MoreLoadable;
import com.homepaas.sls.ui.widget.Refreshable;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * on 2015/12/31 0031
 *
 * @author zhudongjie .
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> implements Refreshable<Message>,
        MoreLoadable<Message> {

    private static final String TAG = "MessageAdapter";


    public interface OnItemOperateListener {

        void onDeleteClick(int position);

        void onItemClick(int position);
    }

    private LayoutInflater mInflater;

    List<Message> mMessages;

    private OnItemOperateListener mOnItemOperateListener;

    public MessageAdapter(List<Message> messages) {
        mMessages = messages;
    }

    public void setOnItemOperateListener(OnItemOperateListener onItemOperateListener) {
        mOnItemOperateListener = onItemOperateListener;
    }

    @Override
    public void addMore(List<Message> moreData) {
        if (moreData == null || moreData.size() == 0)
            return;
        mMessages.addAll(moreData);
        notifyDataSetChanged();
    }

    @Override
    public void refresh(List<Message> list) {
        if (list == null || list.size() == 0)
            mMessages = list;
        notifyDataSetChanged();
    }


    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.message_item, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        if (mOnItemOperateListener != null) {
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete
                    mOnItemOperateListener.onDeleteClick(holder.getAdapterPosition());
                }
            });

            holder.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    mOnItemOperateListener.onItemClick(position);
                }
            });
        }
        Message message = mMessages.get(position);
        holder.content.setText(message.getContent());
        holder.time.setText(TimeUtils.formatDate(message.getDate()));
        holder.title.setText(message.getTitle());
        holder.icon.setImageResource(getIcon(message.getType()));
        holder.notice.setVisibility(message.isRead() ? View.INVISIBLE : View.VISIBLE);
    }

    private int getIcon(String type) {
        switch (type) {
            case "1"://活动消息
                return R.mipmap.message_icon_activity;
            case "2"://订单消息
                return R.mipmap.message_icon_evalution;
            case "3"://优惠消息
                return R.mipmap.message_icon_red_package;
            case "4"://账户消息
                return R.mipmap.message_icon_balance;
            default:
                return R.mipmap.message_icon_activity;
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.timeList)
        TextView time;
        @Bind(R.id.delete)
        TextView delete;
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.notice_icon)
        View notice;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.click)
        RelativeLayout click;


        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
