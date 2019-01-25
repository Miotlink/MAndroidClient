package com.homepaas.sls.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceType;
import com.homepaas.sls.domain.entity.ServiceTypeEx;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2016/9/16.
 * Author: fmisser
 * Description:
 */

public class ServiceItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ServiceTypeEx> serviceTypeExList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;


    public ServiceItemAdapter() {

    }

    public void setServiceTypeExList(List<ServiceTypeEx> list) {
        this.serviceTypeExList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View view = mLayoutInflater.inflate(R.layout.services_type_select_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).setModel(serviceTypeExList.get(position));
    }

    @Override
    public int getItemCount() {
        return serviceTypeExList == null ? 0 : serviceTypeExList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_textView)
        TextView textView;

        private ServiceTypeEx serviceTypeEx;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setModel(ServiceTypeEx serviceTypeEx) {
            this.serviceTypeEx = serviceTypeEx;
            textView.setText(serviceTypeEx.getTypeName());
        }

        @OnClick(R.id.item_textView)
        public void OnClick() {
            EventBus.getDefault().post(serviceTypeEx);
        }
    }
}
