package com.homepaas.sls.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.ComplaintEntity;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;

/**
 * Created by mhy on 2017/8/26.
 */

public class HistoryAdapter extends BaseRecyclerAdapter<ComplaintEntity> {

    public HistoryAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.complaint_list_item;
    }

    @Override
    public void bindData(final RecyclerViewHolder holder, final int position, final ComplaintEntity item) {
        holder.setText(R.id.tv_name, item.getName());
        ImageView imageView = holder.getImageView(R.id.img_check);
        imageView.setImageResource(item.isCheck() ? R.mipmap.select_pic_gallery_normal : R.mipmap.select_pic_gallery_pressed);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectClickListener != null)
                    onSelectClickListener.onSelectClick(holder.getLayoutPosition(), item);
            }
        });
    }

    private OnSelectClickListener onSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        this.onSelectClickListener = onSelectClickListener;
    }

    public interface OnSelectClickListener {

        void onSelectClick(int position, ComplaintEntity item);
    }

}