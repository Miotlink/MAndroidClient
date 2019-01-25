package com.homepaas.sls.ui.homepage_3_3_0.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.CityDetail;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sherily on 2017/7/21.
 */

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {


    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<CityDetail> cityDetails;

    public void setData(List<CityDetail> cityDetails){
        this.cityDetails = cityDetails;
        notifyDataSetChanged();
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            mContext = parent.getContext();
            layoutInflater = LayoutInflater.from(mContext);
        }
        View view = layoutInflater.inflate(R.layout.city_item_layout, parent, false);
        return new CityViewHolder(view);
    }
    public interface OnChangeCity{
        void onChange(CityDetail cityDetail);
    }
    private OnChangeCity onChangeCity;

    public void setOnChangeCity(OnChangeCity onChangeCity) {
        this.onChangeCity = onChangeCity;
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, int position) {
        holder.bind();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChangeCity != null) {
                    onChangeCity.onChange(cityDetails.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityDetails == null ? 0 : cityDetails.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.city_name)
        TextView cityName;
        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bind(){
            CityDetail cityDetail = cityDetails.get(getAdapterPosition());
            cityName.setText(cityDetail.getCityName());
        }
    }
}
