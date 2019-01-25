package com.homepaas.sls.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.ui.search.adapter.SearchServiceResultAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2016/12/23.
 */

public class HomeHotServiceAdapter extends RecyclerView.Adapter<HomeHotServiceAdapter.ReviewViewHolder> {

    private LayoutInflater mInflater;
    private List<HotServiceInfo> list;
    private int clickPosition = 0;
    private int lastPosition=0;

    public HomeHotServiceAdapter() {
    }

    public void setDate(List<HotServiceInfo> list){
        this.list=list;
        notifyDataSetChanged();
    }



    public void setOnClick(int position,int lastPosition) {
          clickPosition = position;
          this.lastPosition=lastPosition;
        notifyItemChanged(lastPosition);
         notifyItemChanged(position);
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.home_hot_service_adapter, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewViewHolder holder, final int position) {
        if (clickPosition==position){
            holder.hotServiceText.setTextColor(Color.parseColor("#27b8f3"));
        }else{
            holder.hotServiceText.setTextColor(Color.parseColor("#999999"));
        }
        holder.hotServiceText.setText(list.get(position).getName());
        if(onItemClickListener!=null){
            holder.clickLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.ItemClick(list.get(position).getName(),list.get(position).getServiceId(),holder.getAdapterPosition());

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.hot_service_text)
        TextView hotServiceText;
        @Bind(R.id.click_lin)
        LinearLayout clickLin;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void ItemClick(String name,String serviceID,int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
