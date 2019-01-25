package com.homepaas.sls.ui.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.mvp.model.PopuModle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sherily on 2017/2/13.
 */

public class MorePopuWindowAdapter extends RecyclerView.Adapter<MorePopuWindowAdapter.ItemViewHolder> {


    private List<PopuModle> datas;
    private LayoutInflater inflater;

    public void setDatas(List datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public interface OnAction {
        void handler(int position,boolean status);
    }

    private OnAction onAction;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.more_popu_window_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        PopuModle popuModle = datas.get(holder.getAdapterPosition());
        holder.bind(datas.get(holder.getAdapterPosition()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAction != null) {
                    datas.get(holder.getAdapterPosition()).setStatus(!datas.get(holder.getAdapterPosition()).isStatus());

                    onAction.handler(holder.getAdapterPosition(),datas.get(holder.getAdapterPosition()).isStatus());
                    notifyItemChanged(holder.getAdapterPosition());


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.name)
        TextView name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(PopuModle popuModle) {
            name.setText(popuModle.getName());
            if (getAdapterPosition() == 0) {
                if (popuModle.isStatus())
                    icon.setImageResource(R.mipmap.client_v3_1_0_ic_homepage_collect_finish);
                else
                    icon.setImageResource(R.mipmap.client_v3_1_0_ic_homepage_collect);
            } /*else if (getAdapterPosition() == 1) {
                icon.setImageResource(R.mipmap.client_v3_1_0_ic_homepage_share);
            }*/ else {
                if (popuModle.isStatus())
                    icon.setImageResource(R.mipmap.client_v3_1_0_ic_homepage_like_finish);
                else
                    icon.setImageResource(R.mipmap.client_v3_1_0_ic_homepage_like);
            }

        }
    }
}
