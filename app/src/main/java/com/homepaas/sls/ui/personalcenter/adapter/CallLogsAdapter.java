package com.homepaas.sls.ui.personalcenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.mvp.model.CallLogModel;
import com.homepaas.sls.ui.widget.Refreshable;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * on 2015/12/30 0030
 *
 * @author zhudongjie .
 */
public class CallLogsAdapter extends RecyclerView.Adapter<CallLogsAdapter.LogsViewHolder> implements Refreshable<CallLogModel>{


    public interface OnItemOperateListener {

        void onItemCall(int position,CallLogModel item);

        void onDelete(int position,CallLogModel item);

        void onItemClick(int position,CallLogModel item);
    }


    private List<CallLogModel> mLogs;

    private LayoutInflater mInflater;

    private OnItemOperateListener mOnItemOperateListener;

    public CallLogsAdapter(List<CallLogModel> logs) {
        mLogs = logs;
    }

    public void setOnItemOperateListener(OnItemOperateListener onItemOperateListener) {
        mOnItemOperateListener = onItemOperateListener;
    }

    public void refresh(List<CallLogModel> logs) {
        this.mLogs = logs;
        notifyDataSetChanged();
    }

    @Override
    public LogsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View itemView = mInflater.inflate(R.layout.call_logs_item, parent, false);
        final LogsViewHolder viewHolder = new LogsViewHolder(itemView);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemOperateListener != null) {
                    mOnItemOperateListener.onDelete(viewHolder.getAdapterPosition(),mLogs.get(viewHolder.getAdapterPosition()));
                }
                throttle(v);
            }
        });
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemOperateListener != null) {
                    mOnItemOperateListener.onItemCall(viewHolder.getAdapterPosition(),mLogs.get(viewHolder.getAdapterPosition()));
                }
                throttle(v);
            }
        });

        viewHolder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemOperateListener != null) {
                    mOnItemOperateListener.onItemClick(viewHolder.getAdapterPosition(),mLogs.get(viewHolder.getAdapterPosition()));
                }
            }
        });
        return viewHolder;
    }

    private void throttle(final View view){
        if (view != null) {
            view.setClickable(false);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setClickable(true);
                }
            }, 600);
        }
    }

    @Override
    public void onBindViewHolder(LogsViewHolder holder, int position) {
        holder.bindData(mLogs.get(position));
    }

    

    @Override
    public int getItemCount() {
        return mLogs.size();
    }

    static class LogsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.photo)
        ImageView photo;

        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.address_and_time)
        TextView addressTime;

        @Bind(R.id.call_button)
        View call;

        @Bind(R.id.delete)
        View delete;

        @Bind(R.id.click_view)
        View click;

        public LogsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CallLogModel logModel) {
            Glide.with(itemView.getContext())
                    .load(logModel.getPhotoUrl())
                    .placeholder(R.mipmap.portrait_default)
                    .into(photo);
            String nameString = logModel.getCount() == 1 ? logModel.getName() : logModel.getName() + "(" + logModel.getCount() + ")";
            this.name.setText(nameString);
            String addTime = /*logModel.getAttribution()+" | "+ */TimeUtils.formatDate(logModel.getTime());
            addressTime.setText(addTime);
            call.setEnabled(logModel.isCallable());
        }
    }
}
