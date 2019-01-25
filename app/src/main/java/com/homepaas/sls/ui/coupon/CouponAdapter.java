package com.homepaas.sls.ui.coupon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.ui.widget.Refreshable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/11.
 */
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.Holder> implements Refreshable<CouponContents> {

    private Context context;
    private LayoutInflater inflater;
    //    private List<Object> datas;
    private List<CouponContents> datas;

    public CouponAdapter(Context context, List<CouponContents> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<CouponContents> couponContentses) {
        datas = couponContentses;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.item_coupon, parent, false));
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    private String getTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/M/dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    private String getCurrentTimeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * 时间戳格式化
     *
     * @param timeStamp
     * @return
     */
    private String timeStampTransform(String timeStamp) {
        long time = Long.parseLong(timeStamp) * 1000;
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return simpleDateFormat.format(date);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final CouponContents couponContents = datas.get(holder.getAdapterPosition());
        int isUsed = Integer.parseInt(couponContents.getIsUsed());
        String currentTimeStamp = getCurrentTimeStamp();
        String endTimeStamp = couponContents.getEndTime();
        String end = timeStampTransform(endTimeStamp);
        String startTimeStamp = couponContents.getStartTime();
        String start = timeStampTransform(startTimeStamp);
        String text = "有效期：" + start + "-" + end;
        boolean isInvaild = currentTimeStamp.compareTo(endTimeStamp) > 0;
        Log.d("couponTime", "onBindViewHolder: " + couponContents.getEndTime());
        Log.d("couponTime", "onBindViewHolder: " + isInvaild);
        if (isInvaild) {
            holder.couponbd.setBackgroundResource(R.mipmap.quan_bg_dis);
            holder.isUsed.setVisibility(View.VISIBLE);
            switch (isUsed) {
                case 0:
                    holder.isUsed.setText("已过期");
                    break;
                case 1:
                    holder.isUsed.setText("已使用");
                    holder.couponbd.setClickable(false);
                    break;
            }
            holder.couponEndtime.setText(text);

        } else {
            holder.couponbd.setClickable(false);
            switch (isUsed) {
                case 0:
                    holder.couponbd.setBackgroundResource(R.mipmap.coupon_bg);
                    holder.isUsed.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    holder.couponbd.setBackgroundResource(R.mipmap.quan_bg_dis);
                    holder.isUsed.setVisibility(View.VISIBLE);
                    holder.isUsed.setText("已使用");
                    break;
            }
            holder.couponEndtime.setText(text);
        }

        String tag = datas.get(position).getTag();
        if (tag != null) {
            holder.couponbd.setBackgroundResource(TextUtils.equals("1",tag) ? R.mipmap.coupon_bg : R.mipmap.quan_bg_dis);
        }
        List<CouponContents.CouponDetails> couponDetailses = couponContents.getCouponDetailses();
        List<CouponContents.MyServiceType> myServiceTypes = couponContents.getMyServiceTypes();
        if (couponDetailses != null) {
            StringBuilder sb = new StringBuilder();
            for (CouponContents.CouponDetails couponDetails1 : couponDetailses) {
                int discountType = Integer.parseInt(couponDetails1.getDiscountType());
                switch (discountType) {
                    case 0:
                        sb.append("满");
                        sb.append(couponDetails1.getAmount());
                        sb.append("减");
                        sb.append(couponDetails1.getDiscountAmount());
                        sb.append("；");
                        break;
                    case 1:
                        sb.append(couponDetails1.getDiscount());
                        break;
                }
            }
            holder.couponDescription.setText(sb.toString());

        }
        StringBuilder sb = new StringBuilder();
        if (myServiceTypes != null && !myServiceTypes.isEmpty()) {

            for (CouponContents.MyServiceType myServiceType : myServiceTypes) {
                sb.append(myServiceType.getServiceName());
                sb.append("；");
            }
        } else {
            sb.append("全品类");
        }
        holder.couponDescription1.setText(sb.toString());
        holder.couponbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coupClickListener != null)
                    coupClickListener.onItemClick(couponContents);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void refresh(List<CouponContents> list) {
        setDatas(list);
    }

    public static class Holder extends RecyclerView.ViewHolder {

        @Bind(R.id.isUsed)
        TextView isUsed;
        @Bind(R.id.couponbd)
        RelativeLayout couponbd;
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.coupon_title)
        TextView couponTitle;
        @Bind(R.id.coupon_description)
        TextView couponDescription;
        @Bind(R.id.coupon_endtime)
        TextView couponEndtime;
        @Bind(R.id.coupon_description1)
        TextView couponDescription1;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    OnCoupClickListener coupClickListener;

    public void setOnCoupClickListener(OnCoupClickListener onCoupClickListener) {
        this.coupClickListener = onCoupClickListener;
    }

    public interface OnCoupClickListener {
        void onItemClick(CouponContents item);
    }
}
