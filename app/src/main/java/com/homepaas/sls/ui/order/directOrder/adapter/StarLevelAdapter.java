package com.homepaas.sls.ui.order.directOrder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.StarLevelInfo;
import com.umeng.socialize.utils.Log;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/7/21.
 */

public class StarLevelAdapter extends RecyclerView.Adapter<StarLevelAdapter.StarLevelView> {

    private LayoutInflater layoutInflater;
    private List<StarLevelInfo.StarLevelLabelInfo> starLevelLabelInfoList;
    public int selectPosition = -1;

    public void setStarLevelLabelInfoList(List<StarLevelInfo.StarLevelLabelInfo> starLevelLabelInfoList) {
        this.starLevelLabelInfoList = starLevelLabelInfoList;
        if (starLevelLabelInfoList != null) {
            selectPosition = starLevelLabelInfoList.size() - 1;
        }
        notifyDataSetChanged();
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public StarLevelView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_star_level, parent, false);
        return new StarLevelView(view);
    }

    @Override
    public void onBindViewHolder(final StarLevelView holder, int position) {
        final StarLevelInfo.StarLevelLabelInfo starLevelLabelInfo = starLevelLabelInfoList.get(holder.getAdapterPosition());
        if (holder.getAdapterPosition() <= selectPosition) {
            holder.star.setBackgroundResource(R.mipmap.star_1);
        } else {
            holder.star.setBackgroundResource(R.mipmap.star_2);
        }
        if (hostAction != null) {
            holder.star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hostAction.selectWhat(holder.getAdapterPosition(), starLevelLabelInfo);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return starLevelLabelInfoList == null ? 0 : starLevelLabelInfoList.size();
    }

    public class StarLevelView extends RecyclerView.ViewHolder {
        @Bind(R.id.star)
        ImageView star;

        public StarLevelView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface HostAction {
        void selectWhat(int position, StarLevelInfo.StarLevelLabelInfo starLevelLabelInfo);
    }

    private HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }
}
