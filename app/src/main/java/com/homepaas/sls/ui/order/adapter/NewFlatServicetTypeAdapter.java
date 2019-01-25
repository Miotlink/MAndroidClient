package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.BusinessChooseEntity;
import com.homepaas.sls.domain.entity.CommonServiceType;
import com.homepaas.sls.domain.param.Constant;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/21.
 */
public class NewFlatServicetTypeAdapter extends RecyclerView.Adapter<NewFlatServicetTypeAdapter.Holder> {


    private final List<Boolean> checkList;
    private List<BusinessChooseEntity> datas;
    LayoutInflater inflater;
    Context context;
    private OnItemClickListener itemClickListener;
    private int selectPosition;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public void setSelectPosition(int selectPosition){
        this.selectPosition=selectPosition;
    }

    public NewFlatServicetTypeAdapter(List<BusinessChooseEntity> datas, List<Boolean> checkList, Context context) {
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

    public void setData(List<BusinessChooseEntity> dats) {
        this.datas=dats;
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
            BusinessChooseEntity service = datas.get(getAdapterPosition());

            if(selectPosition ==getAdapterPosition()){
                serviceName.setTextColor(Color.parseColor("#27b8f3"));
                price.setTextColor(Color.parseColor("#27b8f3"));
            }else{
                serviceName.setTextColor(Color.parseColor("#333333"));
                price.setTextColor(Color.parseColor("#333333"));
            }
            serviceName.setText(service.getServiveName());
            if (TextUtils.equals(Constant.SERVICE_TYPE_NEG, service.getNegotiable())) {
                if (service.getPriceOptions() != null) {
                    if (service.getPriceOptions().size() > 1) {
                        price.setText("¥" + service.getPriceOptions().get(0) + "-" + service.getPriceOptions().get(service.getPriceOptions().size() - 1) + "/" + service.getUnitName());
                    } else {
                        price.setText("¥" + service.getMin() + "/" + service.getUnitName());
                    }
                }
            }
            else {
                price.setText(service.getStartingPrice()+"元起");
            }
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
