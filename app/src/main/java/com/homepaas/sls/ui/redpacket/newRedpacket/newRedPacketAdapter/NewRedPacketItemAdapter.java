package com.homepaas.sls.ui.redpacket.newRedpacket.newRedPacketAdapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.widget.Refreshable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sherily on 2016/11/29.
 */

public class NewRedPacketItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Refreshable<CouponContents> {

    public int redPackeStatus = -1;//0：未使用；1：已使用；2：已过期;3:使用列表红包；



    private Context context;
    private LayoutInflater inflater;
    private List<CouponContents> couponContentsList;
    private boolean isNotUsed = false;  //是否没有使用优惠卷


    public NewRedPacketItemAdapter(List<CouponContents> couponContentsList, int redPackeStatus) {
        this.couponContentsList = couponContentsList;
        this.redPackeStatus = redPackeStatus;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null)
            context = parent.getContext();
        if (inflater == null)
            inflater = LayoutInflater.from(context);
        switch (viewType) {
            case ITEM_VIEW_TYPE_ONE:
                return new ItemViholder(inflater.inflate(R.layout.new_redpacket_item, parent, false));
            case ITEM_VIEW_TYPE_TWO:
                return new ItemViholder2(inflater.inflate(R.layout.new_redpacket_item2, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViholder) {
            ((ItemViholder) holder).bind(holder.getAdapterPosition());
        }

        if (holder instanceof ItemViholder2) {
            ((ItemViholder2) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return couponContentsList.size();
    }

    public static final int ITEM_VIEW_TYPE_ONE = 0;
    public static final int ITEM_VIEW_TYPE_TWO = 1;

    @Override
    public int getItemViewType(int position) {
        if (true) {
            return ITEM_VIEW_TYPE_TWO;
        } else {
            return ITEM_VIEW_TYPE_ONE;
        }
    }

    @Override
    public void refresh(List<CouponContents> list) {
        if (couponContentsList == null)
            couponContentsList = new ArrayList<>();
        couponContentsList = list;
        notifyDataSetChanged();
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    private int timeOffset(String timeStamp1, String timeStamp2) {
        long t1 = Long.parseLong(timeStamp1) * 1000;
        long t2 = Long.parseLong(timeStamp2) * 1000;
        Date date1 = new Date(t1);
        Date date2 = new Date(t2);
        int days = (int) (date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24);
        return days;
    }

    public interface canUseListener {
        void select(CouponContents couponContents);
    }

    private canUseListener canUseListener;

    public void setCanUseListener(NewRedPacketItemAdapter.canUseListener canUseListener) {
        this.canUseListener = canUseListener;
    }

    public interface ShowServiceDetailListener {
        void showDetail(ServiceItem serviceItem);
    }

    private ShowServiceDetailListener showServiceDetailListener;

    public void setShowServiceDetailListener(ShowServiceDetailListener showServiceDetailListener) {
        this.showServiceDetailListener = showServiceDetailListener;
    }

    private int mSelectedPosition = -1;
    private int lastPosition = -1;
    private String lastSelectedCouponId;//首次默认选中红包的ID

    public void setLastSelectedCouponId(String lastSelectedCouponId, boolean notUsed) {
        this.isNotUsed = notUsed;
        this.lastSelectedCouponId = lastSelectedCouponId;
        if (lastSelectedCouponId == null) {
            if (isNotUsed) {
                mSelectedPosition = -1;
            } else
                mSelectedPosition = 0;

        } else {
            for (CouponContents couponContents : couponContentsList) {
                if (TextUtils.equals(lastSelectedCouponId, couponContents.getId())) {
                    mSelectedPosition = couponContentsList.indexOf(couponContents);
                    return;
                } else {
                    if (couponContentsList.indexOf(couponContents) == (couponContentsList.size() - 1)) {
                        mSelectedPosition = -1;
                    }
                }
            }
        }
    }

    public boolean isSelected(int position) {
        return mSelectedPosition == position;
    }

    public void selectItem(int position) {
        lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    public class ItemViholder2 extends RecyclerView.ViewHolder {
        @Bind(R.id.price_unit)
        TextView priceUnit;
        @Bind(R.id.redpacket_price)
        TextView redpacketPrice;
        @Bind(R.id.price)
        LinearLayout price;
        @Bind(R.id.amount)
        TextView amount;
        @Bind(R.id.fengexian)
        ImageView fengexian;
        @Bind(R.id.redpacket_name)
        TextView redpacketName;
        @Bind(R.id.redpacket_kind)
        TextView redpacketKind;
        @Bind(R.id.redpacket_time)
        TextView redpacketTime;
        @Bind(R.id.status)
        ImageView status;
        @Bind(R.id.use_this)
        TextView useThis;
        @Bind(R.id.gou)
        ImageView gou;
        @Bind(R.id.couponbd)
        RelativeLayout couponbd;
        @Bind(R.id.redPacket)
        LinearLayout redPacket;

        public ItemViholder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind() {
            setStatusStyle();
            setData(couponContentsList.get(getAdapterPosition()));

        }

        private void setStyle1() {//彩色
            useThis.setVisibility(View.VISIBLE);
            gou.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
            couponbd.setBackgroundResource(R.mipmap.hongbao);
            priceUnit.setTextColor(ContextCompat.getColor(context, R.color.appText7));
            redpacketPrice.setTextColor(ContextCompat.getColor(context, R.color.appText7));
            amount.setTextColor(ContextCompat.getColor(context, R.color.appText7));
            redpacketName.setTextColor(ContextCompat.getColor(context, R.color.appText));
            redpacketKind.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.yuan, 0, 0, 0);
            redpacketTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.yuan, 0, 0, 0);
            redpacketKind.setTextColor(ContextCompat.getColor(context, R.color.appText10));
            redpacketTime.setTextColor(ContextCompat.getColor(context, R.color.appText10));
        }

        private void setStyle2() {//黑白底
            useThis.setVisibility(View.GONE);
            gou.setVisibility(View.GONE);
            couponbd.setBackgroundResource(R.mipmap.anhongbao);
            priceUnit.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            redpacketPrice.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            amount.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            redpacketName.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            redpacketKind.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.tuoyuan, 0, 0, 0);
            redpacketTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.tuoyuan, 0, 0, 0);
            redpacketKind.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            redpacketTime.setTextColor(ContextCompat.getColor(context, R.color.appText9));
        }

        private void setStatusStyle() {
            int position = getAdapterPosition();
            CouponContents couponContents = couponContentsList.get(getAdapterPosition());
            if (redPackeStatus == 0) {
                setStyle1();
                useThis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (showServiceDetailListener != null){
                            showServiceDetailListener.showDetail(couponContentsList.get(getAdapterPosition()).getServiceItem());
                        }
                    }
                });
            } else if (redPackeStatus == 3) {
                status.setVisibility(View.GONE);
                if (TextUtils.equals("1", couponContents.getTag())) {
                    setStyle1();
                    useThis.setVisibility(View.GONE);
                    if (isSelected(position)) {
                        gou.setVisibility(View.VISIBLE);
                    } else {
                        gou.setVisibility(View.GONE);
                    }
                    couponbd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            mSelectedPosition = curPosition;
                            selectItem(getAdapterPosition());
                            canUseListener.select(couponContentsList.get(getAdapterPosition()));
                        }
                    });
                } else {
                    setStyle2();
                }

            } else {
                status.setVisibility(View.VISIBLE);
                if (redPackeStatus == 1) {
                    status.setBackgroundResource(R.mipmap.yishiyong);
                } else {
                    status.setBackgroundResource(R.mipmap.yigouqi);
                }
                setStyle2();
            }
        }

        private void setData(CouponContents couponContents) {
            String endTimeStamp = couponContents.getEndTime();
            String startTimeStamp = couponContents.getStartTime();
            if (!TextUtils.isEmpty(endTimeStamp) && !TextUtils.isEmpty(startTimeStamp)) {
                redpacketTime.setVisibility(View.VISIBLE);
                String end = timeStampTransform(endTimeStamp);
                String start = timeStampTransform(startTimeStamp);
                if (TextUtils.equals(start, end)) {
                    redpacketTime.setText("仅限" + start + "当天使用");
                } else {
                    redpacketTime.setText(start + "至" + end);
                }
            } else {
                redpacketTime.setVisibility(View.GONE);
            }
            redpacketName.setText(couponContents.getTitle());

            List<CouponContents.CouponDetails> couponDetailses = couponContents.getCouponDetailses();
            List<CouponContents.MyServiceType> myServiceTypes = couponContents.getMyServiceTypes();
            if (couponDetailses != null) {
//                StringBuilder sb = new StringBuilder();
                for (CouponContents.CouponDetails couponDetails1 : couponDetailses) {
                    int discountType = Integer.parseInt(couponDetails1.getDiscountType());
                    switch (discountType) {
                        case 0:
                            if (TextUtils.equals("0",couponDetails1.getAmount())){
                                amount.setText("无门槛");
                            } else {
                                amount.setText("满" + couponDetails1.getAmount() + "可用");
                            }
                            redpacketPrice.setText(couponDetails1.getDiscountAmount());
                            break;
                        case 1:
                            redpacketPrice.setText(couponDetails1.getDiscount());
                            amount.setText(couponDetails1.getDiscount());
                            break;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            if (myServiceTypes != null && !myServiceTypes.isEmpty()) {
                for (CouponContents.MyServiceType myServiceType : myServiceTypes) {
                    sb.append(myServiceType.getServiceName());
                    if (myServiceTypes.indexOf(myServiceType) < (myServiceTypes.size() - 1)) {
                        sb.append("、");
                    }
                }
            } else {
                sb.append("全品类可用");
            }
            redpacketKind.setText(sb.toString());


        }


    }

    public class ItemViholder extends RecyclerView.ViewHolder {
        @Bind(R.id.price_unit)
        TextView priceUnit;
        @Bind(R.id.redpacket_price)
        TextView redpacketPrice;
        @Bind(R.id.price)
        LinearLayout price;
        @Bind(R.id.amount)
        TextView amount;
        @Bind(R.id.fengjiexian)
        ImageView fengjiexian;
        @Bind(R.id.fengexian)
        ImageView fengexian;
        @Bind(R.id.redpacket_name)
        TextView redpacketName;
        @Bind(R.id.redpacket_kind)
        TextView redpacketKind;
        @Bind(R.id.redpacket_time)
        TextView redpacketTime;
        @Bind(R.id.status)
        ImageView status;
        @Bind(R.id.gou)
        ImageView gou;
        @Bind(R.id.area)
        TextView area;
        @Bind(R.id.deadline)
        TextView deadline;
        @Bind(R.id.couponbd)
        RelativeLayout couponbd;
        @Bind(R.id.redPacket)
        LinearLayout redPacket;

        public ItemViholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(int position) {
            setStatusStyle();
            setData(couponContentsList.get(position));

        }

        private void setStyle1() {
            gou.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
            couponbd.setBackgroundResource(R.mipmap.hongbao);
            priceUnit.setTextColor(ContextCompat.getColor(context, R.color.appText7));
            redpacketPrice.setTextColor(ContextCompat.getColor(context, R.color.appText7));
            amount.setTextColor(ContextCompat.getColor(context, R.color.appText7));
            redpacketName.setTextColor(ContextCompat.getColor(context, R.color.appText));
            redpacketKind.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.yuan, 0, 0, 0);
            redpacketTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.yuan, 0, 0, 0);
            redpacketKind.setTextColor(ContextCompat.getColor(context, R.color.appText10));
            redpacketTime.setTextColor(ContextCompat.getColor(context, R.color.appText10));
            area.setTextColor(ContextCompat.getColor(context, R.color.appText10));
            deadline.setVisibility(View.VISIBLE);
            deadline.setTextColor(ContextCompat.getColor(context, R.color.appText8));
        }

        private void setStyle2() {
            gou.setVisibility(View.GONE);
            couponbd.setBackgroundResource(R.mipmap.anhongbao);
            priceUnit.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            redpacketPrice.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            amount.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            redpacketName.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            redpacketKind.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.tuoyuan, 0, 0, 0);
            redpacketTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.tuoyuan, 0, 0, 0);
            redpacketKind.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            redpacketTime.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            area.setTextColor(ContextCompat.getColor(context, R.color.appText9));
            deadline.setVisibility(View.GONE);
        }

        private void setStatusStyle() {
            int position = getAdapterPosition();
            CouponContents couponContents = couponContentsList.get(getAdapterPosition());
            if (redPackeStatus == 0) {
                setStyle1();
            } else if (redPackeStatus == 3) {
                status.setVisibility(View.GONE);
                if (TextUtils.equals("1", couponContents.getTag())) {
                    setStyle1();
                    if (isSelected(position)) {
                        gou.setVisibility(View.VISIBLE);
                    } else {
                        gou.setVisibility(View.GONE);
                    }
//                    if (canUseListener != null){
//                        redPacket.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mSelectedPosition = curPosition;
//                                selectItem(curPosition);
//                                canUseListener.select(couponContents);
//                            }
//                        });
//                    }
                    couponbd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            mSelectedPosition = curPosition;
                            selectItem(getAdapterPosition());
                            canUseListener.select(couponContentsList.get(getAdapterPosition()));
                        }
                    });
                } else {
                    setStyle2();
                }

            } else {
                status.setVisibility(View.VISIBLE);
                if (redPackeStatus == 1) {
                    status.setBackgroundResource(R.mipmap.yishiyong);
                } else {
                    status.setBackgroundResource(R.mipmap.yigouqi);
                }
                setStyle2();
            }
        }

        private void setData(CouponContents couponContents) {
            String endTimeStamp = couponContents.getEndTime();
            String startTimeStamp = couponContents.getStartTime();
            if (!TextUtils.isEmpty(endTimeStamp) && !TextUtils.isEmpty(startTimeStamp)) {
                redpacketTime.setVisibility(View.VISIBLE);
                String end = timeStampTransform(endTimeStamp);
                String start = timeStampTransform(startTimeStamp);
                if (TextUtils.equals(start, end)) {
                    redpacketTime.setText("仅限" + start + "当天使用");
                } else {
                    redpacketTime.setText("有效期：" + start + "至" + end);
                }
            } else {
                redpacketTime.setVisibility(View.GONE);
            }
            redpacketName.setText(couponContents.getTitle());
            int days = timeOffset(endTimeStamp, startTimeStamp);
            if (days <= 7 && days > 0) {
                deadline.setVisibility(View.VISIBLE);
                deadline.setText("还有" + days + "天过期");
            } else {
                deadline.setVisibility(View.GONE);
            }

            List<CouponContents.CouponDetails> couponDetailses = couponContents.getCouponDetailses();
            List<CouponContents.MyServiceType> myServiceTypes = couponContents.getMyServiceTypes();
            if (couponDetailses != null) {
//                StringBuilder sb = new StringBuilder();
                for (CouponContents.CouponDetails couponDetails1 : couponDetailses) {
                    int discountType = Integer.parseInt(couponDetails1.getDiscountType());
                    switch (discountType) {
                        case 0:
                            redpacketPrice.setText(couponDetails1.getDiscountAmount());
                            amount.setText("满" + couponDetails1.getAmount() + "可用");
                            break;
                        case 1:
                            redpacketPrice.setText(couponDetails1.getDiscount());
                            amount.setText(couponDetails1.getDiscount());
                            break;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            if (myServiceTypes != null && !myServiceTypes.isEmpty()) {
                for (CouponContents.MyServiceType myServiceType : myServiceTypes) {
                    sb.append(myServiceType.getServiceName());
                    if (myServiceTypes.indexOf(myServiceType) < (myServiceTypes.size() - 1)) {
                        sb.append("、");
                    }
                }
            } else {
                sb.append("全品类");
            }
            redpacketKind.setText(sb.toString());


        }

    }
}
