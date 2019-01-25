package com.homepaas.sls.ui.newdetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.MerchantServicePriceListEntity;
import com.homepaas.sls.domain.entity.MerchantServiceTypePrice;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/2/13.
 */

public class MerchantServiceMunuAdapter extends RecyclerView.Adapter<MerchantServiceMunuAdapter.MerchantServiceMunuView> {

    private LayoutInflater inflater;
    private List<MerchantServiceTypePrice> list;
    private int selectPosition=-1;

    public void setList(List<MerchantServiceTypePrice> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setSelectPosition(int selectPosition){
        this.selectPosition=selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public MerchantServiceMunuView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.merchant_service_menu_adapter, parent, false);
        return new MerchantServiceMunuView(view);
    }

    @Override
    public void onBindViewHolder(final MerchantServiceMunuView holder, int position) {
        holder.menuName.setText(list.get(position).getName());
        if(selectPosition==position){
            holder.menuRel.setBackgroundResource(R.color.white);
            holder.colorLin.setVisibility(View.VISIBLE);
        }else{
            holder.menuRel.setBackgroundResource(R.color.merchant_menu_background);
            holder.colorLin.setVisibility(View.GONE);
        }
        if(onMenuItemOnClickListener!=null){
            holder.menuRel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMenuItemOnClickListener.menuItemOnClickListener(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class MerchantServiceMunuView extends RecyclerView.ViewHolder {
        @Bind(R.id.color_lin)
        LinearLayout colorLin;
        @Bind(R.id.menu_name)
        TextView menuName;
        @Bind(R.id.menu_rel)
        RelativeLayout menuRel;

        public MerchantServiceMunuView(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindDate(MerchantServiceTypePrice merchantServiceTypePrice){
        }
    }

    public interface OnMenuItemOnClickListener{
        void menuItemOnClickListener(int menuPosition);
    }
    private OnMenuItemOnClickListener onMenuItemOnClickListener;
    public void setOnMenuItemOnClickListener(OnMenuItemOnClickListener onMenuItemOnClickListener){
        this.onMenuItemOnClickListener=onMenuItemOnClickListener;
    }


}
