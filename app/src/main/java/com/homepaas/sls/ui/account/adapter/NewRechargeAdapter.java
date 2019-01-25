package com.homepaas.sls.ui.account.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.Recharge;
import com.homepaas.sls.domain.entity.RechargeListExEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.http.Body;

/**
 * Created by JWC on 2016/11/30.
 */

public class NewRechargeAdapter extends BaseAdapter{
    int lastIndex;
    List<RechargeListExEntity.RechargeItem> datas;
    Context context;
    LayoutInflater inflater;

    public NewRechargeAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void select(int selectPos){
    }

    public void setDatas(List<RechargeListExEntity.RechargeItem> datas){
        this.datas=datas;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return datas == null?0:datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null?null:datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder =null;
        if (convertView  == null)
        {
            convertView = inflater.inflate(R.layout.item_recharge,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        RechargeListExEntity.RechargeItem recharge=datas.get(position);
        holder.rechargeMoney.setText(recharge.getRechargeMoney()+"元");
        if(!TextUtils.isEmpty(recharge.getReturnType())) {
            if (recharge.getReturnType().equals("3")) {
                holder.returnMoney.setVisibility(View.VISIBLE);
                holder.returnMoney.setText("折扣:" +((int)Double.parseDouble(recharge.getReturnMoney()) )/ 10);
                holder.giftImage.setVisibility(View.INVISIBLE);
            } else if (recharge.getReturnType().equals("4")) {
                holder.returnMoney.setVisibility(View.VISIBLE);
                holder.returnMoney.setText("赠送:" + recharge.getReturnMoney()+"元");
                holder.giftImage.setVisibility(View.VISIBLE);
            } else if (recharge.getReturnType().equals("5")) {
                holder.returnMoney.setVisibility(View.INVISIBLE);
                holder.giftImage.setVisibility(View.INVISIBLE);
            }
        }else{
            holder.returnMoney.setVisibility(View.INVISIBLE);
            holder.giftImage.setVisibility(View.INVISIBLE);
        }

        return  convertView;
    }

    class ViewHolder{
        @Bind(R.id.item_frame)
        RelativeLayout itemFrame;
        @Bind(R.id.recharge_money)
        TextView rechargeMoney;
        @Bind(R.id.return_money)
        TextView returnMoney;
        @Bind(R.id.gift_image)
        ImageView giftImage;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }

    }
}
