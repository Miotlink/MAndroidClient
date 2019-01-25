package com.homepaas.sls.ui.account.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.Recharge;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * recyclerView Adapter
 */
public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.RechargeViewHolder> {


    public interface OnItemClickListener {

        void onItemClick(int position,Recharge item);
    }


    private LayoutInflater mInflater;

    private List<Recharge> rechargeList;

    public RechargeAdapter(List<Recharge> dataList) {
        rechargeList = dataList;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;

    }

    public void setRechargeList(List<Recharge> rechargeList) {
        this.rechargeList = rechargeList;
        notifyDataSetChanged();
    }

    @Override
    public RechargeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.recharge_item, parent, false);
        final RechargeViewHolder viewHolder = new RechargeViewHolder(view);
        if (mOnItemClickListener != null) {
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(pos,rechargeList.get(pos));
                }
            });
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RechargeViewHolder viewHolder, int position) {
        viewHolder.bind(rechargeList.get(position));
    }

    @Override
    public int getItemCount() {
        return rechargeList.size();
    }

    public static class RechargeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView image;

        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.text)
        TextView text;

        @Bind(R.id.money)
        TextView money;

        @Bind(R.id.button)
        Button button;

        public RechargeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Recharge item) {

            Glide.with(image.getContext())
                    .load("http://www.itouxiang.net/uploads/allimg/20151215/063551505127506.jpg")
                    .into(image);
            name.setText(item.getTotalMoney());
//            text.setText(item.getDescription());
            money.setText(item.getNeedPay());
        }
    }
}
