package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.CommonServiceType;
import com.homepaas.sls.domain.entity.WorkerServiceType;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.domain.param.Constant;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/21.
 */
public class FlatServicetTypeAdapter extends RecyclerView.Adapter<FlatServicetTypeAdapter.Holder> {


    private final List<Boolean> checkList;
    private List<CommonServiceType> datas;
    LayoutInflater inflater;
    Context context;
    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FlatServicetTypeAdapter(List<CommonServiceType> datas, List<Boolean> checkList, Context context) {
        this.datas = datas;
        this.checkList = checkList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.item_flat_service_tye, parent, false));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<CommonServiceType> dats) {
        datas.addAll(dats);
        for (int i = 0; i < dats.size(); i++) {
            if (i == 0) checkList.add(true);
            checkList.add(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.bind();
    }

    int selctPos = -1;
    int prevPos = -2;

    class Holder extends RecyclerView.ViewHolder implements Action {
        @Bind(R.id.service_name)
        TextView serviceName;
        @Bind(R.id.price)
        TextView price;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind() {
            CommonServiceType service = datas.get(getAdapterPosition());
            serviceName.setText(service.getServiceTypeName());
            if (TextUtils.equals(Constant.SERVICE_TYPE_STABLE, service.getPriceType())||TextUtils.isEmpty(service.getPriceType()))
                price.setText("¥" + service.getPrice() + "/" + service.getUnitName());
            else
                price.setText("面议");
            if (getAdapterPosition() == selctPos) {
                serviceName.setTextColor(context.getResources().getColor(R.color.newAppPrimary));
                price.setTextColor(context.getResources().getColor(R.color.newAppPrimary));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prevPos = selctPos;
                    selctPos = getAdapterPosition();
                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface Action {
        void bind();
    }
}
