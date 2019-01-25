package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.Util;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.PlaceOrderParamModel;
import com.homepaas.sls.ui.order.directOrder.adapter.ActionListAdapter;
import com.homepaas.sls.ui.order.directOrder.adapter.SatisifiedActionAdapter;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.homepaas.sls.R.string.currentAction;
import static com.homepaas.sls.R.string.service_phone_number;
import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;
import static com.homepaas.sls.ui.order.adapter.Converter.BLOCK_ACTIVITY;
import static com.homepaas.sls.ui.order.adapter.Converter.BLOCK_DESCRIPTION;
import static com.homepaas.sls.ui.order.adapter.Converter.BLOCK_FAVOR;
import static com.homepaas.sls.ui.order.adapter.Converter.BLOCK_NOTE;
import static com.homepaas.sls.ui.order.adapter.Converter.BLOCK_NULL;
import static com.homepaas.sls.ui.order.adapter.Converter.BLOCK_PRICE;
import static com.homepaas.sls.ui.order.adapter.Converter.BLOCK_RANGE_INFO;
import static com.homepaas.sls.ui.order.adapter.Converter.BLOCK_SERVTYPE;
import static com.homepaas.sls.ui.order.adapter.Converter.BLOCK_WORKERINFO;
import static com.homepaas.sls.ui.order.adapter.Converter.TYPE_NEG;
import static com.homepaas.sls.ui.order.adapter.Converter.TYPE_STABLE;

/**
 * Created by CJJ on 2016/9/17.
 *
 */

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderAdapter.Holder> {

/*    public static final int DEFAULT_ITEM_COUNT = 9;
    //地址
    public static final int BLOCK_ADDRESS = 0;
    //工人信息
    public static final int BLOCK_WORKERINFO = 1;
    //服务类型
    public static final int BLOCK_SERVTYPE = 2;
    public static final int BLOCK_SERVNUMBER = 3;
    public static final int BLOCK_SERVTIME = 4;
    public static final int BLOCK_ACTIVITY = 5;
    public static final int BLOCK_NOTE = 6;
    public static final int BLOCK_PHOTO = 7;
    public static final int BLOCK_DESCRIPTION = 8;
    public static final int BLOCK_NULL = -1;*/

    LayoutInflater inflater;
    Context context;

    public PlaceOrderParamModel data;
    private AddPhotoAdapter photoAdapter;
    private List<ActivityNgInfoNew.ActivityNgDetail> satisfiedSpecialActivityList;
    private SatisifiedActionAdapter satisifiedActionAdapter;
    private String descriptionStr;
    private CommonDialog promotionHelpDialog;
    private CommonDialog specialHelpDialog;
    private ActionListAdapter actionAdapter;

    public void setDescriptionStr(String descriptionStr) {
        this.descriptionStr = descriptionStr;
        notifyItemChanged(Converter.convertViewTypeToPosition(BLOCK_DESCRIPTION));
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
            context = parent.getContext();
        }
        switch (viewType) {
            case BLOCK_WORKERINFO:
                return new Holder(inflater.inflate(R.layout.item_placeorder_provider_info, parent, false));
            case BLOCK_SERVTYPE:
                return new Holder(inflater.inflate(R.layout.item_placeorder_servicetype, parent, false));
            case BLOCK_PRICE:
                return new Holder(inflater.inflate(R.layout.item_placeorder_etc, parent, false));
            case BLOCK_RANGE_INFO:
                return new Holder(inflater.inflate(R.layout.item_price_range_info, parent, false));
            case BLOCK_ACTIVITY:
                return new Holder(inflater.inflate(R.layout.item_placeorder_activity, parent, false));
            case BLOCK_NOTE:
                return new Holder(inflater.inflate(R.layout.item_placeorder_note, parent, false));
            case BLOCK_DESCRIPTION:
                return new Holder(inflater.inflate(R.layout.item_placeorder_description, parent, false));
            default:
                return new Holder(new View(context));
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind();
    }

    public void setData(PlaceOrderParamModel data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void notifyPhotoAdd(int i) {
        photoAdapter.notifyItemInserted(i);
    }

    //ViewHolder
    class Holder extends RecyclerView.ViewHolder implements Action {


        @Override
        public void bind() {
            switch (getItemViewType()) {

                case BLOCK_WORKERINFO:
                    //服务地址
                    address.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startShiftAddress();
                        }
                    });
                    if (data.getAddress() != null)
                    {
                        AddressEntity entity = data.getAddress();
                        String contact = entity.getContact();
                        String ads = entity.getAddress();
                        String detialAddress = entity.getDetailAddress();
                        String gender = TextUtils.equals(entity.getGender(), Constant.GENDER_MALE) ? "先生" : "女士";
                        String phone = entity.getPhoneNumber();
                        address.setText(contact + "  " + gender + "  " + phone + "\n" + ads + detialAddress);
                    }
                    if ("1".equals(data.getProviderType())) {
                        // 工人加上师傅,阿姨
                        String genderShow;
                        if ("0".equals(data.getProviderGender()) || "♂".equals(data.getProviderGender())) {
                            genderShow = "  师傅";
                        } else {
                            genderShow = "  阿姨";
                        }
                        providerName.setText(data.getProviderName() == null ? "" : data.getProviderName() + genderShow);
                    } else {
                        // 商户不加
                        providerName.setText(data.getProviderName() == null ? "" : data.getProviderName());
                    }
                    phoneNumber.setVisibility(View.GONE);
                    iconPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startDial(data.getPhoneNumber());
                        }
                    });
                    Glide.with(context)
                            .load(data.getProviderPic)
                            .placeholder(R.mipmap.head_portrait_default)
                            .into(providerAvatar);
                    break;
                case BLOCK_SERVTYPE:
                    serviceTypeName.setText(data.getServiceName());

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startFetchServiceType();
                        }
                    });
                    break;
                case BLOCK_PRICE:
                    serviceNumberLayout.setVisibility(VISIBLE);
                    servicePriceLayout.setVisibility(VISIBLE);

                    if (data.getMode() == TYPE_NEG)
                    {
                        serviceNumberLayout.setVisibility(GONE);
                        servicePriceLayout.setVisibility(GONE);
                        price.setText("面议");
                    }
                    else if (data.getMode() == TYPE_STABLE)
                        price.setText("¥" + data.getPrice()+"/"+data.getUnit());
                    else
                        price.setText("");

                    //服务数量
                    unit.setText(data.getUnit());
                    if (Constant.HOURLY_WORKER.equals(data.getServiceTypeId()) || Constant.HOURLY_WORKER_WINDOW.equals(data.getServiceTypeId())) {
                        minusAction.setVisibility(GONE);
                        plusAction.setVisibility(GONE);
                        serviceNumber.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hostAction.startSelectServiceNumber();
                            }
                        });
                        serviceNumber.setText(String.valueOf(data.serviceCount));
                    } else {
                        serviceNumber.setText(data.getServiceNumber());//非小时工显示整数数量
                        minusAction.setVisibility(View.VISIBLE);
                        plusAction.setVisibility(View.VISIBLE);
                    }

                    minusAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (data.getServiceNumber().equals("1"))
                            {
                                minusAction.setImageResource(R.mipmap.reduce2);
                                return;
                            }
                            minusAction.setImageResource(R.mipmap.reduce1);
                            Holder.this.serviceNumber.setText(String.valueOf(data.minusServiceNumber()));
                            hostAction.onServiceNumberChange();
                        }

                    });
                    plusAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Holder.this.serviceNumber.setText(String.valueOf(data.plusServiceNumber()));
                            hostAction.onServiceNumberChange();
                        }
                    });

                    price.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    //服务时间
                    String servTime = data.getServiceTimeStart();
                    serviceTime.setText(TextUtils.isEmpty(servTime) ? "" : servTime);
                    serviceTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startPickServiceTime();
                        }
                    });

                    break;
                case BLOCK_NOTE:
                    remarkText.setText(data.getNote());
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startAddNote();
                        }
                    });
                    if (photoAdapter == null) {
                        photoAdapter = new AddPhotoAdapter();
                        photoAdapter.setPaths(data.getPhotoPath());
                        album.setLayoutManager(new GridLayoutManager(context, 4));
                        photoAdapter.setPhotoListener(new AddPhotoAdapter.AddPhotoListener() {
                            @Override
                            public void startAddPhoto() {
                                hostAction.startHostAddPhoto();
                                album.setVisibility(View.VISIBLE);
                            }
                        });
                        album.setAdapter(photoAdapter);
                    }
                    if (data.getPhotoPath()!=null&&!data.getPhotoPath().isEmpty())
                    {
                        album.setVisibility(VISIBLE);
                        album.setAdapter(photoAdapter);
                    }
                    photoAdapter.setPaths(data.getPhotoPath());
                    break;
                case BLOCK_DESCRIPTION:
                    if (description != null) {
                        itemView.setVisibility(View.VISIBLE);
                        if(descriptionStr!=null){
                        description.setText("服务说明\n"+descriptionStr);
                        }else{
                            description.setText("服务说明");
                        }
                    } else {
                        itemView.setVisibility(GONE);
                    }
                    break;
                case BLOCK_ACTIVITY:
                    if (data.getMode() == TYPE_NEG) {
                        discountSumLayout.setVisibility(GONE);
                        sumDescription.setText(data.getStartingPrice());
                    }else {
                        discountSumLayout.setVisibility(VISIBLE);
                        sumDescription.setText("¥"+data.getPrice()+"×"+data.getServiceNumberString()+"            ¥"+ cutUnnecessaryPrecision(String.valueOf(data.sum)));
                        transformActivity();
                    }

                    if (actionAdapter == null)
                        actionAdapter = new ActionListAdapter();
                    actionCollection.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                    actionCollection.setAdapter(actionAdapter);
                    if (data.currentAction != null&&(data.currentAction.isSpecialAvailable()||data.currentAction.isPromotionAvailable()))
                    {
                        activityWrapper.setVisibility(VISIBLE);
                        actionAdapter.setAction(data.currentAction);
                        actionAdapter.setSum((float) data.sum);
                    } else {
                        activityWrapper.setVisibility(GONE);
                    }
                    break;
                default:
                    //do nothing but waiting to die;

            }
        }

        private void transformActivity() {

            if (data.currentAction != null && data.currentAction.isSpecialSatisfied((float) data.sum)) {
                actionSpecial.setVisibility(VISIBLE);
                satisfiedSpecialActivityList = data.currentAction.getSatisfiedSpecialActivityList();
//                actionTitle.setVisibility(VISIBLE);
//                actionDiscount.setVisibility(VISIBLE);
                if (satisifiedActionAdapter == null){
                    satisifiedActionAdapter = new SatisifiedActionAdapter();
                    actionSpecial.setAdapter(satisifiedActionAdapter);
                } else {
                    actionSpecial.getAdapter();
                }
                satisifiedActionAdapter.setData(satisfiedSpecialActivityList);
                String minus = "0";
                BigDecimal sumDecimal = new BigDecimal(data.sum).setScale(2, RoundingMode.HALF_UP);
                BigDecimal minusDecimal = new BigDecimal(minus).setScale(2, RoundingMode.HALF_UP);
                for (ActivityNgInfoNew.ActivityNgDetail activityNgDetail : satisfiedSpecialActivityList){
                    String minuss = activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus();
                    BigDecimal newminusDecimal = new BigDecimal(minuss).setScale(2, RoundingMode.HALF_UP);
                    minusDecimal = minusDecimal.add(newminusDecimal);
                }
                orderSum.setText("¥" + cutUnnecessaryPrecision(String.valueOf(sumDecimal.subtract(minusDecimal).floatValue())));
            } else {
                actionSpecial.setVisibility(GONE);
//                actionTitle.setVisibility(GONE);
//                actionDiscount.setVisibility(GONE);
                orderSum.setText("¥" + cutUnnecessaryPrecision(String.valueOf(data.sum)));
            }
            /*
            //处理满减
            if (data.currentAction == null) {
                itemView.setVisibility(GONE);
                return;
            }
            itemView.setVisibility(View.VISIBLE);
            if (data.currentAction.isSpecialAvailable())//可以参与满减活动
            {
                if (data.currentAction.isSpecialSatisfied((float) data.sum)) {//可以参与满减活动，并且满足活动条件
                    specialActivitySatisfiedCell.setVisibility(View.VISIBLE);
                    //显示特价活动
                    specialFavorTitle.setText(data.currentAction.getSpecialTitle());
                    specialHintLayout.setVisibility(GONE);
                    orderFavorLayout.setVisibility(View.VISIBLE);

                    String minus = data.currentAction.getSpecialRule().get(0).getMinus();
                    textView8.setText("-¥ " + cutUnnecessaryPrecision(minus));

                    //显示优惠
                    orderTotalPrice.setText(String.valueOf(data.sum));
                    orderFavorPrice.setText(minus);
                    String text = String.valueOf(data.sum - Double.parseDouble(minus));
                    orderTopayPrice.setText(text);
                    specialFavorHelp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (specialHelpDialog == null) {
                                specialHelpDialog = new CommonDialog.Builder()
                                        .showAction(false)
                                        .setContent(data.currentAction.getSpecialHelp())
                                        .setTitle(data.currentAction.getSpecialTitle())
                                        .create();
                            }
                            specialHelpDialog.show(((PlaceOrderActivity) context).getSupportFragmentManager(), null);
                        }
                    });
                } else {//不满足条件,只给出满减活动提示
                    specialActivitySatisfiedCell.setVisibility(GONE);
                    orderFavorLayout.setVisibility(GONE);
                    specialTitle.setText(data.currentAction.getSpecialAvailableDesc());
                    specialHintLayout.setVisibility(View.VISIBLE);//显示特价活动的提示
                    specialHintHelp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (specialHelpDialog == null) {
                                specialHelpDialog = new CommonDialog.Builder()
                                        .showAction(false)
                                        .setContent(data.currentAction.getSpecialHelp())
                                        .setTitle(data.currentAction.getSpecialTitle())
                                        .create();
                            }
                            specialHelpDialog.show(((PlaceOrderActivity) context).getSupportFragmentManager(), null);
                        }
                    });
                }

            } else {//没有参与的满减活动，均不显示
                specialActivitySatisfiedCell.setVisibility(GONE);
                specialHintLayout.setVisibility(GONE);
                orderFavorLayout.setVisibility(GONE);
            }

            //处理满返
            if (data.currentAction.isPromotionAvailable()) {//可以参加满返活动
                promotionActivityLayout.setVisibility(View.VISIBLE);
                promotionNameText.setText(data.currentAction.getPromotionTitle());
                promotionHintHelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (promotionHelpDialog == null)
                            promotionHelpDialog = new CommonDialog.Builder()
                                    .showAction(false)
                                    .setContent(data.currentAction.getPromotionHelp())
                                    .setTitle(data.currentAction.getPromotionTitle())
                                    .create();
                        promotionHelpDialog.show(((PlaceOrderActivity) context).getSupportFragmentManager(), null);
                    }
                });

                //满足满返活动条件
                if (data.currentAction.isPromotionSatisfied((float) data.sum)) {
                    promotionName.setVisibility(View.VISIBLE);
                    promotionName.setText(data.currentAction.getPromotionName());
                } else {
                    promotionName.setVisibility(GONE);
                }
            } else {
                promotionActivityLayout.setVisibility(GONE);
            }*/
        }

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //BLOCK_WORKERINFO
        @Nullable
        @Bind(R.id.provider_name)
        TextView providerName;
        @Nullable
        @Bind(R.id.provider_avatar)
        RoundedImageView providerAvatar;
        @Nullable
        @Bind(R.id.icon_phone)
        ImageView iconPhone;
        @Nullable
        @Bind(R.id.phone_number)
        TextView phoneNumber;

        //BLOCK_SERVETYPE
        @Nullable
        @Bind(R.id.service_type_name)
        TextView serviceTypeName;

        //BLOCK_PRICE
        @Nullable
        @Bind(R.id.price)
        TextView price;

        @Nullable
        @Bind(R.id.minus_action)//服务数量减
                ImageView minusAction;

        @Nullable
        @Bind(R.id.plus_action)//服务数量加
                ImageView plusAction;

        @Nullable
        @Bind(R.id.service_number)//服务数量
                TextView serviceNumber;
        @Nullable
        @Bind(R.id.service_price_layout)
        RelativeLayout servicePriceLayout;

        @Nullable
        @Bind(R.id.service_number_layout)
        RelativeLayout serviceNumberLayout;

        @Nullable
        @Bind(R.id.service_time_text)//服务时间
                TextView serviceTime;

        @Nullable
        @Bind(R.id.address_info)//服务地址
                TextView address;


        @Nullable
        @Bind(R.id.unit)//服务单位
                TextView unit;


        //活动--订单金额
        @Nullable
        @Bind(R.id.action_collection)
        RecyclerView actionCollection;
        @Nullable
        @Bind(R.id.activity_wrapper)
        LinearLayout activityWrapper;
        @Nullable
        @Bind(R.id.sum_description)
        TextView sumDescription;
        @Nullable
        @Bind(R.id.action_special)
        RecyclerView actionSpecial;
//        @Nullable
//        @Bind(R.id.action_title)
//        TextView actionTitle;
//        @Nullable
//        @Bind(R.id.action_discount)
//        TextView actionDiscount;
        @Nullable
        @Bind(R.id.order_sum)
        TextView orderSum;
        @Nullable
        @Bind(R.id.discount_sum_layout)
        LinearLayout discountSumLayout;

        @Nullable
        @Bind(R.id.remark_text) //备注
                TextView remarkText;

        @Nullable
        @Bind(R.id.album)
        RecyclerView album;


        //BLOCK_DESCRIPTION
        @Nullable
        @Bind(R.id.description)
        TextView description;

    }


    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return BLOCK_WORKERINFO;
            case 1:
                return BLOCK_SERVTYPE;
            case 2:
                return BLOCK_PRICE;//包含价格、服务地址、服务数量、服务时间等信息
            case 3:
                return BLOCK_ACTIVITY;
            case 4:
                return BLOCK_FAVOR;
            case 5:
                return BLOCK_NOTE;
            case 6:
                return BLOCK_DESCRIPTION;
            default:
                return BLOCK_NULL;
        }

//       return convertPositionToViewType(true, data.getMode(),position, data.currentAction != null&&(data.currentAction.isSpecialAvailable()||data.currentAction.isPromotionAvailable()));
    }

    public interface Action {
        void bind();
    }

    public HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }

    public interface HostAction {
        void startFetchServiceType();//获取服务类别

        void startHostAddPhoto();//添加图片

        void startPickServiceTime();//选择服务时间

        void startShiftAddress();//选择服务地址

        void onServiceNumberChange();

        void startSelectServiceNumber();

        void startAddNote();

        void startDial(String phoneNumber);
    }
}
