package com.homepaas.sls.ui.newdetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.WorkerServiceTypePrice;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/2/13.
 */

public class WorkerServiceItemItemAdapter extends RecyclerView.Adapter<WorkerServiceItemItemAdapter.ItemItemView> {

    private LayoutInflater inflater;
    private List<WorkerServiceTypePrice> list;

    public WorkerServiceItemItemAdapter(List<WorkerServiceTypePrice> list) {
        this.list=list;
    }

    public void setList(List<WorkerServiceTypePrice> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.worker_servic_item_item_adapter, parent, false);
        return new ItemItemView(view);
    }

    @Override
    public void onBindViewHolder(ItemItemView holder, int position) {
        holder.setDate(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ItemItemView extends RecyclerView.ViewHolder {
        @Bind(R.id.worker_four_item_service_name)
        TextView workerFourItemServiceName;
        @Bind(R.id.worker_four_item_service_price)
        TextView workerFourItemServicePrice;

        public ItemItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDate(WorkerServiceTypePrice workerServiceTypePrice) {
            workerFourItemServiceName.setText(workerServiceTypePrice.getName());
            workerFourItemServicePrice.setText(workerServiceTypePrice.getPrice());
        }
    }
}
