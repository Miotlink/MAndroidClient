package com.homepaas.sls.ui.newdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.MerchantServiceTypePrice;
import com.homepaas.sls.domain.entity.WorkerServiceTypePrice;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/2/13.
 */

public class WorkerServiceItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int WORKER_THREE_SERVICE = 0; //没有子集的布局
    private static final int WORKER_FOUR_SERVICE = 1; //有子集的布局
    private LayoutInflater inflater;
    private List<WorkerServiceTypePrice> list;
    private static Context context;

    public WorkerServiceItemAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<WorkerServiceTypePrice> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        if (viewType == WORKER_THREE_SERVICE) {
            view = inflater.inflate(R.layout.worker_service_item_three_adapter, parent, false);
            WorkerServiceThreeView workerServiceThreeView = new WorkerServiceThreeView(view);
            workerServiceThreeView.setIsRecyclable(false);
            return workerServiceThreeView;
        } else {
            view = inflater.inflate(R.layout.worker_service_item_four_adapter, parent, false);
            WorkerServiceFourView workerServiceFourView = new WorkerServiceFourView(view);
            workerServiceFourView.setIsRecyclable(false);
            return workerServiceFourView;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final WorkerServiceTypePrice workerServiceTypePrice = list.get(position);
        if (getItemViewType(position) == WORKER_THREE_SERVICE) {
            ((WorkerServiceThreeView) holder).setThreeDate(workerServiceTypePrice);
            if (onWorkerThreeBuyClickListener != null) {
                ((WorkerServiceThreeView) holder).workerThreeBuyService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onWorkerThreeBuyClickListener.onThreeBuyClick(workerServiceTypePrice);
                    }
                });
            }
        } else {
            ((WorkerServiceFourView) holder).setFourDate(workerServiceTypePrice);
            if (onWorkerFourBuyClickListener != null) {
                ((WorkerServiceFourView) holder).workerFourBuyService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onWorkerFourBuyClickListener.onFourBuyClick(workerServiceTypePrice);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getChildList() != null && !list.get(position).getChildList().isEmpty()) {
            return WORKER_FOUR_SERVICE;
        } else {
            return WORKER_THREE_SERVICE;
        }
    }

    public static class WorkerServiceThreeView extends RecyclerView.ViewHolder {
        @Bind(R.id.worker_three_service_name)
        TextView workerThreeServiceName;
        @Bind(R.id.worker_three_servicee_price)
        TextView workerThreeServiceePrice;
        @Bind(R.id.worker_three_buy_service)
        TextView workerThreeBuyService;

        public WorkerServiceThreeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setThreeDate(WorkerServiceTypePrice workerServiceTypePrice) {
            workerThreeServiceName.setText(workerServiceTypePrice.getName());
            workerThreeServiceePrice.setText(workerServiceTypePrice.getPrice());
        }
    }

    public static class WorkerServiceFourView extends RecyclerView.ViewHolder {
        @Bind(R.id.worker_four_service_name)
        TextView workerFourServiceName;
        @Bind(R.id.worker_four_buy_service)
        TextView workerFourBuyService;
        @Bind(R.id.item_item_recyclerView)
        RecyclerView itemItemRecyclerView;

        WorkerServiceItemItemAdapter workerServiceItemItemAdapter;

        public WorkerServiceFourView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setFourDate(WorkerServiceTypePrice workerServiceTypePrice) {
            workerFourServiceName.setText(workerServiceTypePrice.getName());
            workerServiceItemItemAdapter = new WorkerServiceItemItemAdapter(workerServiceTypePrice.getChildList());
//            itemItemRecyclerView.addItemDecoration(new SimpleItemDecoration(context, SimpleItemDecoration.VERTICAL_LIST));
            itemItemRecyclerView.setAdapter(workerServiceItemItemAdapter);
        }
    }

    public interface OnWorkerThreeBuyClickListener {
        void onThreeBuyClick(WorkerServiceTypePrice workerServiceTypePrice);
    }

    private OnWorkerThreeBuyClickListener onWorkerThreeBuyClickListener;

    public void setOnWorkerThreeBuyClickListener(OnWorkerThreeBuyClickListener onWorkerThreeBuyClickListener) {
        this.onWorkerThreeBuyClickListener = onWorkerThreeBuyClickListener;
    }

    public interface OnWorkerFourBuyClickListener {
        void onFourBuyClick(WorkerServiceTypePrice workerServiceTypePrice);
    }

    private OnWorkerFourBuyClickListener onWorkerFourBuyClickListener;

    public void setOnWorkerFourBuyClickListener(OnWorkerFourBuyClickListener onWorkerFourBuyClickListener) {
        this.onWorkerFourBuyClickListener = onWorkerFourBuyClickListener;
    }


}
