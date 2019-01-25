package com.homepaas.sls.ui.newdetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.WorkerServiceTypePrice;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/2/13.
 */

public class WorkerAcionChooseAdapter extends RecyclerView.Adapter<WorkerAcionChooseAdapter.ItemItemView> {


    private LayoutInflater inflater;
    private List<WorkerServiceTypePrice> list;

    public WorkerAcionChooseAdapter(List<WorkerServiceTypePrice> list) {
        this.list = list;
    }

    @Override
    public ItemItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.action_choose_service_adapter, parent, false);
        return new ItemItemView(view);
    }

    @Override
    public void onBindViewHolder(ItemItemView holder, int position) {
        final WorkerServiceTypePrice workerServiceTypePrice=list.get(position);
        holder.setDate(workerServiceTypePrice);
        if(onItemClickListener!=null){
            holder.backRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(workerServiceTypePrice);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ItemItemView extends RecyclerView.ViewHolder {
        @Bind(R.id.action_service_name)
        TextView actionServiceName;
        @Bind(R.id.action_service_price)
        TextView actionServicePrice;
        @Bind(R.id.back_rel)
        RelativeLayout backRel;

        public ItemItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDate(WorkerServiceTypePrice workerServiceTypePrice) {
            actionServiceName.setText(workerServiceTypePrice.getName());
            actionServicePrice.setText(workerServiceTypePrice.getPrice());
        }
    }

    public interface onItemClickListener{
        void onItemClick(WorkerServiceTypePrice workerServiceTypePrice);
    }

    public void setonItemClickListener(onItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;

    }

    private onItemClickListener onItemClickListener;
}
