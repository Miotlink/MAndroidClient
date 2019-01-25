package com.homepaas.sls.ui.order.directOrder.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.LableEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/7/4.
 */

public class ChooseLableAdapter extends RecyclerView.Adapter<ChooseLableAdapter.ChooseLableView>{
    private LayoutInflater layoutInflater;
    private List<LableEntity> lableEntityList;

    public void setLableEntityList(List<LableEntity> lableEntityList) {
        this.lableEntityList = lableEntityList;
        notifyDataSetChanged();
    }

    @Override
    public ChooseLableView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_choose_lable, parent, false);
        return new ChooseLableView(view);
    }

    @Override
    public void onBindViewHolder(final ChooseLableView holder, int position) {
        final LableEntity lableEntity=lableEntityList.get(holder.getAdapterPosition());
        holder.lable.setText(lableEntity.getLable());
        if(TextUtils.equals("1",lableEntity.getIsCheck())){
            holder.lable.setTextColor(Color.parseColor("#ff1b56"));
            holder.lable.setBackgroundResource(R.drawable.late_time_check);
        }else {
            holder.lable.setTextColor(Color.parseColor("#333639"));
            holder.lable.setBackgroundResource(R.drawable.late_time_not_check);
        }
        if(lableSelect!=null){
            holder.lable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lableSelect.lableSelect(holder.getAdapterPosition()+"");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lableEntityList == null ? 0 : lableEntityList.size();
    }


    public class ChooseLableView extends RecyclerView.ViewHolder {
        @Bind(R.id.lable)
        TextView lable;
        public ChooseLableView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface LableSelect{
        void lableSelect(String position);
    }
    private LableSelect lableSelect;
    public void setLateTimeSelect(LableSelect lableSelect){
        this.lableSelect=lableSelect;
    }
}
