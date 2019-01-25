package com.homepaas.sls.ui.order.directOrder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.data.repository.Constant;
import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.ClaimsInfo;
import com.homepaas.sls.domain.entity.KdEOrderInfo;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.Refreshable;
import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;

/**
 * Created by CJJ on 2016/9/15.
 * 订单列表
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.Holder> implements Refreshable<OrderEntity> {

    List<OrderEntity> datas;
    LayoutInflater inflater;
    Context context;
    //分别应用三种阶段的基础颜色
    private static int colorBegin = Color.parseColor("#CCCCCC");//灰色
    private static int colorMiddle = Color.parseColor("#FD5524");//橘黄色
    private static int colorEnd = Color.parseColor("#3FBEF9");//蓝色

    private int colorNormal;
    private int colorPay;
    private int colorComment;
    private CommonDialog cancelDialog;
    private CommonDialog cancelDialog1;
    private CommonDialog notTakenDialog;

    public OrderListAdapter() {
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
            context = parent.getContext();
            initColor();
        }
        return new Holder(inflater.inflate(R.layout.item_order_list, parent, false));
    }

    private void initColor() {
        colorNormal = context.getResources().getColor(R.color.appTextNormal);
        colorPay = context.getResources().getColor(R.color.decorateOrange);
        colorComment = context.getResources().getColor(R.color.newAppPrimary);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public void refresh(List<OrderEntity> list) {
        if (datas == null)
            datas = new ArrayList<>();
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }

    public void setDatas(List<OrderEntity> datas) {
        this.datas = datas;
    }

    public List<OrderEntity> getDatas() {
        return datas;
    }

    class Holder extends RecyclerView.ViewHolder implements Action {
        @Bind(R.id.service_name)
        TextView serviceName;
        @Bind(R.id.status_description)
        TextView statusDescription;
        @Bind(R.id.providerinfo_box)
        LinearLayout providerInfoBox;
        @Bind(R.id.avatar)
        RoundedImageView avatar;
        @Bind(R.id.provider_name)
        TextView providerName;
        @Bind(R.id.waiting_hint)
        TextView waitingHint;
        @Bind(R.id.appoint_time)
        TextView appointTime;
        @Bind(R.id.textView5)
        TextView textView5;
        @Bind(R.id.service_address)
        TextView serviceAddress;
        @Bind(R.id.service_price)
        TextView price;
        @Bind(R.id.negative_action)
        TextView negativeAction;
        @Bind(R.id.positive_action)
        TextView positiveAction;
        @Bind(R.id.service_sum)
        TextView serviceSum;
        @Bind(R.id.service_sum_text)
        TextView serviceSumText;
        @Bind(R.id.content)
        LinearLayout mainContent;
        @Bind(R.id.icon_type)
        View iconType;
        @Bind(R.id.total_price_layout)
        LinearLayout totalPriceLayout;
        @Bind(R.id.not_express_info_lin)
        LinearLayout notExpressInfoLin;
        @Bind(R.id.express_odd_Numbers)
        TextView expressOddNumbers;
        @Bind(R.id.send_address)
        TextView sendAddress;
        @Bind(R.id.receiver_address)
        TextView receiverAddress;
        @Bind(R.id.send_time)
        TextView sendTime;
        @Bind(R.id.express_info_lin)
        LinearLayout expressInfoLin;
        @Bind(R.id.button_rel)
        RelativeLayout buttonRel;
        @Bind(R.id.claims_status)
        TextView claimsStatus;
        @Bind(R.id.show_claims)
        TextView showClaims;
        @Bind(R.id.insurance_title)
        TextView insuranceTitle;
        @Bind(R.id.insurance_money)
        TextView insuranceMoney;
        @Bind(R.id.insurance_rel)
        RelativeLayout insuranceRel;
        @Bind(R.id.address_str)
        TextView addressStr;
        @Bind(R.id.running_receiver_address_str)
        TextView runningReceiverAddressStr;
        @Bind(R.id.running_receiver_address)
        TextView runningReceiverAddress;
        @Bind(R.id.running_receiver_address_rel)
        RelativeLayout runningReceiverAddressRel;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void startCancel() {
            int pos = getAdapterPosition();
            String id = datas.get(pos).getOrderId();
            hostAction.startCancel(pos, id);
        }

        @Override
        public void startComment() {
            int pos = getAdapterPosition();
            String id = datas.get(pos).getOrderId();
            hostAction.startComment(pos, id);
        }

        @Override
        public void startDelete() {
            int pos = getAdapterPosition();
            String id = datas.get(pos).getOrderId();
            hostAction.startDelete(pos, id);
        }

        /**
         * 实现Action接口执行所有的Holder需要的操作，避免嵌套过多的Listener 导致低效率的 final
         */
        @Override
        public void bind() {
            int position = getAdapterPosition();
            OrderEntity entity = datas.get(position);
            String status = entity.getOrderStatus();
            boolean payOff = TextUtils.equals("1", entity.getIsPayOff());
            boolean isEvaluated = entity.getIsEvaluated().equals(Constant.EVALUATED) ? true : false;
//            boolean hasPrice = TextUtils.isEmpty(entity.getTotalPrice()) || TextUtils.equals("面议", entity.getTotalPrice());
            boolean hasPrice = !TextUtils.isEmpty(entity.getTotalPrice());
            boolean isRefunding = TextUtils.equals(entity.getRefundStatus(), "1");
            boolean isNegotiable = TextUtils.equals(entity.getNegotiable(), "1");
            String providerType = entity.getProviderType();
            if (entity.getClaimsInfo() != null && !TextUtils.isEmpty(entity.getClaimsInfo().getClaimsAmount())) {
                insuranceRel.setVisibility(View.VISIBLE);
                insuranceTitle.setText(entity.getClaimsInfo().getClaimsAmount() + "元保险");
                insuranceMoney.setText("¥" + entity.getClaimsInfo().getClaimsAmount());
            } else {
                insuranceRel.setVisibility(View.GONE);
            }
            //判断是不是快递
            String isKdEOrder = entity.getIsKdEOrder();
            if (!TextUtils.isEmpty(isKdEOrder) && TextUtils.equals(isKdEOrder, "1")) {
                notExpressInfoLin.setVisibility(GONE);
                expressInfoLin.setVisibility(View.VISIBLE);
                KdEOrderInfo kdEOrderInfo = entity.getKdEOrderInfo();
                if (entity.getService() != null) {
                    sendAddress.setText(entity.getService().getAddress());
                }
                if (kdEOrderInfo != null) {
                    expressOddNumbers.setText(kdEOrderInfo.getLogisticCode());
                    KdEOrderInfo.ServiceAddressResponse serviceAddressResponse = kdEOrderInfo.getTargetAddress();
                    if (serviceAddressResponse != null) {
                        receiverAddress.setText(serviceAddressResponse.getAddress1() + " " + serviceAddressResponse.getAddress2());
                    }
                    sendTime.setText(TimeUtils.formatOrderTime(entity.getCreateTime()));
                }
                //状态
                statusDescription.setText("");
                //计算订单总价
                totalPriceLayout.setVisibility(GONE);
                //底部按钮
                buttonRel.setVisibility(GONE);
            } else {
                notExpressInfoLin.setVisibility(View.VISIBLE);
                expressInfoLin.setVisibility(View.GONE);
                if (entity.getService() != null) {
                    appointTime.setText(TimeUtils.formatOrderTime(entity.getService().getServiceStartAt()));
                    if(!TextUtils.isEmpty(isKdEOrder) &&TextUtils.equals(isKdEOrder, "2")){
                        addressStr.setText("寄件地址");
                        runningReceiverAddressRel.setVisibility(View.VISIBLE);
                        if(entity.getExpressOrderInfo()!=null&&entity.getExpressOrderInfo().getTargetAddress()!=null){
                            runningReceiverAddress.setText(entity.getExpressOrderInfo().getTargetAddress().getAddress1()+entity.getExpressOrderInfo().getTargetAddress().getAddress2());
                        }
                    }else {
                        addressStr.setText("服务地址");
                        runningReceiverAddressRel.setVisibility(View.GONE);
                    }
                    serviceAddress.setText(entity.getService().getAddress());
                }
                price.setText(TextUtils.isEmpty(entity.getPrice()) ? "¥" + cutUnnecessaryPrecision(entity.getStartingPrice()) + "起" : "¥" + cutUnnecessaryPrecision(entity.getPrice()) + "/" + entity.getUnitName() + "     x" + entity.getTotal());
                //状态
                statusDescription.setText(getStatusText(hasPrice, status, payOff, isEvaluated, providerType));
                //计算订单总价
                totalPriceLayout.setVisibility(View.VISIBLE);
                setStatusSumText(status, payOff);
                //底部按钮
                buttonRel.setVisibility(View.VISIBLE);
                ClaimsInfo claimsInfo = entity.getClaimsInfo();
                initStyleAndVisibility(status, TextUtils.equals(entity.getIsPayOff(), Constant.PAYOFF), isRefunding, hasPrice, isNegotiable, providerType, claimsInfo);
            }
            //查看服务者信息
            initServiceInfo(entity);
            if (entity.service != null) {
                serviceName.setText(entity.service.getServiceName());
                setIconType(entity.service.getRootId());
            }
            mainContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hostAction.startViewDetail(datas.get(getAdapterPosition()).getOrderId(), datas.get(getAdapterPosition()).getIsKdEOrder());
                }
            });
        }

        private void setIconType(String serviceType) {
            switch (serviceType) {
                case "168":
                    iconType.setBackgroundResource(R.color.service_install_color);
                    break;
                case "169":
                    iconType.setBackgroundResource(R.color.service_convenience_color);
                    break;
                case "170":
                    iconType.setBackgroundResource(R.color.service_housekeep_color);
                    break;
                case "171":
                    iconType.setBackgroundResource(R.color.service_repair_color);
                    break;
                case "172":
                    iconType.setBackgroundResource(R.color.service_fix_color);
                    break;
                default:
                    break;
            }
        }

        private void setStatusSumText(String status, boolean payOff) {
            OrderEntity entity = datas.get(getAdapterPosition());
            String totalPrice = entity.getTotalPrice();
            String orderStatus = entity.getOrderStatus();
            boolean hasPrice = !(TextUtils.isEmpty(totalPrice) || TextUtils.equals("面议", totalPrice));
            String refundStatus = entity.getRefundStatus();
            totalPriceLayout.setVisibility(View.VISIBLE);
            serviceSumText.setVisibility(View.VISIBLE);
            serviceSum.setVisibility(View.VISIBLE);
            switch (status) {
                case Constant.ORDER_STATUS_CANCELED://取消的情况
                    //如果有退款
                    if (TextUtils.isEmpty(refundStatus))//没有退款记录
                    {
                        if (hasPrice)//有价格
                        {
                            serviceSumText.setText("订单总价¥");
                            serviceSum.setText(totalPrice);
                        } else {
//                            serviceSumText.setText("面议");
                            totalPriceLayout.setVisibility(GONE);
                        }
                    } else {//有退款记录的情况下应该是已经有总价了和单价（单价=总价/数量）
                        //没有价格，面议
//                        serviceSumText.setText("");
//                        serviceSum.setText("面议");
//                        if (hasPrice)//有价格
//                                {
                        serviceSumText.setText("订单总价¥");
                        serviceSum.setText(totalPrice);
//                        } else {
                        totalPriceLayout.setVisibility(GONE);
//                        }
                    }
                    break;
                default:
                    if (hasPrice) {
                        //FIXME!!!这里暂时改成订单总价,因为实际支付和待支付的逻辑没有考虑优惠
                        serviceSumText.setText("订单总价¥");
                        serviceSum.setText(totalPrice);
                        float money = Float.parseFloat(totalPrice);
                        ActionEntity activity = entity.getActivity();
                        float realMoney = money;
                        //减去活动减免
                        if (activity != null) {
                            float totalMoney = Float.parseFloat(totalPrice);
//                            if (activity.isSpecialSatisfied(totalMoney)) {
//                                String specialMinusCount = activity.getSpecialRule().get(0).getMinus();
//                                float minusMoney = TextUtils.isEmpty(specialMinusCount) ? 0.0f : Float.parseFloat(specialMinusCount);
//                                realMoney -= minusMoney;
//                            }
                            BigDecimal paySum = new BigDecimal(totalPrice);
                            String minus = "0";
                            if (entity.getActivityNgInfos() != null && entity.getActivityNgInfos().isSpecialSatisfied(Float.valueOf(totalPrice))) {
                                BigDecimal minusDecimal = new BigDecimal(minus).setScale(2, RoundingMode.HALF_UP);
                                for (ActivityNgInfoNew.ActivityNgDetail activityNgDetail : entity.getActivityNgInfos().getSatisfiedSpecialActivityList()) {
                                    String minuss = activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus();
                                    BigDecimal newminusDecimal = new BigDecimal(minuss).setScale(2, RoundingMode.HALF_UP);
                                    minusDecimal = minusDecimal.add(newminusDecimal);
                                }
                                realMoney = Float.parseFloat(paySum.subtract(minusDecimal).doubleValue() + "");
                            }
                        }
                        if (payOff)//已经支付
                        {
                            //减去优惠券减免金额
                            String discountAmount = entity.getDiscountAmount();//优惠金额
                            realMoney -= TextUtils.isEmpty(discountAmount) ? 0.0f : Float.parseFloat(discountAmount);
                            serviceSumText.setText("实付¥");
                            serviceSum.setText(cutUnnecessaryPrecision(String.valueOf(realMoney)));
                        } else {//待支付，不读取优惠券（优惠券在支付后读取，不排除未支付时优惠券字段存在，支付前忽略该字段即可）
                            serviceSumText.setText("待支付¥");
                            serviceSum.setText(cutUnnecessaryPrecision(String.valueOf(realMoney)));
                        }

                    } else {//面议
//                        totalPriceLayout.setVisibility(View.GONE);
//                        serviceSumText.setVisibility(View.GONE);
//                        serviceSum.setVisibility(View.GONE);
                        //接单前隐藏
                        if (TextUtils.equals(orderStatus, Constant.ORDER_STATUS_TAKEN)) {
                            serviceSumText.setVisibility(View.VISIBLE);
                            serviceSumText.setText("请与工人协商,并确定价格");
                            serviceSum.setVisibility(GONE);
                        } else {
                            serviceSumText.setVisibility(GONE);
                            serviceSum.setVisibility(GONE);
                        }

                    }
            }
        }

        /**
         * 添加物流公司或者服务者信息
         *
         * @param entity
         */
        private void initServiceInfo(OrderEntity entity) {
            String isKdEOrder = entity.getIsKdEOrder();
            if (!TextUtils.isEmpty(isKdEOrder) && TextUtils.equals(isKdEOrder, "1")) {
                KdEOrderInfo kdEOrderInfo = entity.getKdEOrderInfo();
                if (kdEOrderInfo != null) {
                    if (TextUtils.isEmpty(kdEOrderInfo.getShipperName()) && TextUtils.isEmpty(kdEOrderInfo.getShipperIcon())) {
                        providerInfoBox.setVisibility(GONE);
                    } else {
                        providerInfoBox.setVisibility(View.VISIBLE);
                        Glide.with(context).load(kdEOrderInfo.getShipperIcon()).placeholder(R.mipmap.client_v3_3_0_ic_expressage_head).into(avatar);
                        providerName.setText(kdEOrderInfo.getShipperName());
                    }
                } else {
                    providerInfoBox.setVisibility(GONE);
                }
            }else if(!TextUtils.isEmpty(isKdEOrder) && TextUtils.equals(isKdEOrder, "2")){
                providerInfoBox.setVisibility(GONE);
            } else {
                if (entity.getMerchantResponse() == null && entity.getWorkerResponse() == null) {
                    providerInfoBox.setVisibility(GONE);
                } else {
                    providerInfoBox.setVisibility(View.VISIBLE);
                    if (TextUtils.equals(entity.getProviderType(), "2")) {
                        if (entity.getWorkerResponse() != null) {
                            Glide.with(context).load(entity.getWorkerResponse().getIcon()).placeholder(R.mipmap.head_portrait_default).into(avatar);
                            providerName.setText(entity.getWorkerResponse().getName());
                        }
                    } else {
                        if (entity.getMerchantResponse() != null) {
                            Glide.with(context).load(entity.getMerchantResponse().getIcon()).placeholder(R.mipmap.head_portrait_default).into(avatar);
                            providerName.setText(entity.getMerchantResponse().getName());
                        }
                    }
                }
            }

        }

        /**
         * 根据状态初始化item 中的textView的颜色相关的样式,以及按钮的显示与隐藏
         *
         * @param status      订单状态
         * @param payOff      是否支付完成
         * @param isRefunding 是否处于退款中
         */
        private void initStyleAndVisibility(String status, final boolean payOff, final boolean isRefunding, final boolean hasPrice, final boolean isNegotiable, final String providerType, final ClaimsInfo claimsInfo) {
            negativeAction.setTextColor(colorNormal);
            positiveAction.setTextColor(colorNormal);
            negativeAction.setVisibility(View.VISIBLE);
            positiveAction.setVisibility(View.VISIBLE);
            claimsStatus.setVisibility(GONE);
            showClaims.setVisibility(GONE);
            //显示赔付的信息
            if (claimsInfo != null) {
                final ClaimsInfo.ClaimsManagementInfo claimsManagementInfo = claimsInfo.getClaimsManagementInfo();
                if (!TextUtils.isEmpty(claimsInfo.getIsDisplayShowClaimsBtn()) && TextUtils.equals("1", claimsInfo.getIsDisplayShowClaimsBtn())) {
                    showClaims.setVisibility(View.VISIBLE);
                    showClaims.setText("查看赔付");
                    if (hostAction != null) {
                        showClaims.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hostAction.startSeeCompensation(claimsManagementInfo.getSettlementId());
                            }
                        });
                    }
                }
                if (!TextUtils.isEmpty(claimsInfo.getIsDisplayBackClaimsReasonBtn()) && TextUtils.equals("1", claimsInfo.getIsDisplayBackClaimsReasonBtn())) {
                    showClaims.setVisibility(View.VISIBLE);
                    showClaims.setText("查看原因");
                    if (hostAction != null) {
                        showClaims.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hostAction.startSeeReason(claimsManagementInfo.getBackReason());
                            }
                        });
                    }
                }
                if (claimsManagementInfo != null && !TextUtils.isEmpty(claimsManagementInfo.getAuditStatus())) {
                    claimsStatus.setVisibility(View.VISIBLE);
                    claimsStatus.setText(claimsManagementInfo.getAuditStatus());
                } else {
                    claimsStatus.setVisibility(View.GONE);
                }
            }
            switch (status) {

//                case Constant.ORDER_STATUS_TOASSIGN://待指派
//                    negativeAction.setText(R.string.cancle_order_str);
//                    negativeAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            cancelDialog = new CommonDialog.Builder()
//                                    .setContent("取消订单  确定取消订单吗？")
//                                    .setCancelButton("先不取消", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            cancelDialog.dismiss();
//                                        }
//                                    })
//                                    .setConfirmButton("确定", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            hostAction.startCancel(getAdapterPosition(),datas.get(getAdapterPosition()).getOrderId());
//                                        }
//                                    }).showTitle(false).create();
//                            cancelDialog.show(((BaseActivity)context).getSupportFragmentManager(),null);
//                        }
//                    });
////                    positiveAction.setVisibility(View.GONE);
//
//                    //待付款
//                    positiveAction.setTextColor(colorPay);
//                    positiveAction.setText("支付");
//                    positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
//                    positiveAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {//工人未接单，不能支付，弹框提示
//                            notTakenDialog = new CommonDialog.Builder()
//                                    .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
//                                    .setTitle("温馨提示")
//                                    .showAction(false)
//                                    .create();
//                            notTakenDialog.show(((BaseActivity) context).getSupportFragmentManager(), null);
//                        }
//                    });
//                    break;

//                case Constant.ORDER_STATUS_TOASSIGN://待指派
//                case Constant.ORDER_STATUS_TOTAKE://待接单：取消,没支付可以支付
//                    negativeAction.setText(R.string.cancle_order_str);
//                    negativeAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            cancelDialog = new CommonDialog.Builder()
//                                    .setContent("取消订单  确定取消订单吗？")
//                                    .setCancelButton("先不取消", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            cancelDialog.dismiss();
//                                        }
//                                    })
//                                    .setConfirmButton("确定", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            hostAction.startCancel(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
//                                        }
//                                    }).showTitle(false).create();
//                            cancelDialog.show(((BaseActivity) context).getSupportFragmentManager(), null);
//
//                        }
//                    });
//
//                    //待付款
//                    positiveAction.setTextColor(colorPay);
//                    positiveAction.setText("支付");
//                    positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
//                    positiveAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {//工人未接单，不能支付，弹框提示
//                            notTakenDialog = new CommonDialog.Builder()
//                                    .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
//                                    .setTitle("温馨提示")
//                                    .showAction(false)
//                                    .create();
//                            notTakenDialog.show(((BaseActivity) context).getSupportFragmentManager(), null);
//                        }
//                    });
//
////                    //面议
////                    if (negotiable || payOff) {
////                        positiveAction.setVisibility(View.GONE);
////                    }else {
////                        //待付款
////                        positiveAction.setTextColor(colorPay);
////                        positiveAction.setText("支付");
////                        positiveAction.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View v) {//工人未接单，不能支付，弹框提示
////                               notTakenDialog = new CommonDialog.Builder()
////                                       .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
////                                       .setTitle("温馨提示")
////                                       .showAction(false)
////                                       .create();
////                                notTakenDialog.show(((BaseActivity)context).getSupportFragmentManager(),null);
////                            }
////                        });
////                        positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
////                    }
//                    break;
                case Constant.ORDER_STATUS_CANCELED://已取消：删除
                    positiveAction.setVisibility(GONE);
                    negativeAction.setText(R.string.delete_order_str);
                    negativeAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startDelete(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                        }
                    });
                    break;
                case Constant.ORDER_STATUS_TOASSIGN://待指派
                case Constant.ORDER_STATUS_TOTAKE: //待接单：取消,没支付可以支付
                case Constant.ORDER_STATUS_MERCHANT_ASSIGNMENT:  //待商户指派工人
                case Constant.ORDER_STATUS_WORKER_ORDER:  //待商户指派的工人接单
                case Constant.ORDER_STATUS_TAKEN://已接单：服务中
                    if (isNegotiable) {
                        negativeAction.setVisibility(GONE);
                    } else {
                        if (payOff) {
                            negativeAction.setVisibility(View.VISIBLE);
                            negativeAction.setText("取消订单");
                            negativeAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    hostAction.startRemind();
                                }
                            });
                        } else {
                            negativeAction.setVisibility(View.VISIBLE);
                            negativeAction.setText("取消订单");
                            negativeAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    hostAction.startCancel(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                                }
                            });
                        }
                    }
                    //现在的逻辑接单后不一定有价格,所以需要考虑是否有价格以及是否支付三种情况
                    if (hasPrice) {
                        if (!payOff) {
                            positiveAction.setText(R.string.pay_str);
                            positiveAction.setTextColor(colorPay);
                            positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
                            positiveAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    hostAction.startPay(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                                }
                            });
                        } else {
                            positiveAction.setVisibility(GONE);
                        }
                    } else {
                        if (TextUtils.equals(status, "1") || TextUtils.equals(status, "10")) {
                            positiveAction.setVisibility(View.GONE);
                        } else {
                            positiveAction.setVisibility(View.VISIBLE);
                            positiveAction.setText(R.string.pay_str);
                            positiveAction.setTextColor(colorPay);
                            positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
                            positiveAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    notTakenDialog = new CommonDialog.Builder()
                                            .setContent(TextUtils.equals(providerType, "2") ? "请与工人协商服务价格" : "请与商户协商服务价格")
                                            .setTitle("温馨提示")
                                            .showAction(false)
                                            .create();
                                    notTakenDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
                                }
                            });
                        }

                    }
                    break;
                case Constant.ORDER_STATUS_WORKER_COMPLETE://工人已经确认服务完成：待客户确认
                    negativeAction.setVisibility(GONE);
                    positiveAction.setText(R.string.confirm_order_finish);
                    positiveAction.setTextColor(context.getResources().getColor(R.color.newAppPrimary));
                    positiveAction.setBackgroundResource(R.drawable.order_positive_button_bg);
                    positiveAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startConfirm(datas.get(getAdapterPosition()).getOrderId());
                        }
                    });
                    break;
                case Constant.ORDER_STATUS_CONFIRMED://客户已经确认完成，待评价
                    if (TextUtils.equals(datas.get(getAdapterPosition()).getIsEvaluated(), Constant.EVALUATED)) {
                        //已经评价
                        positiveAction.setVisibility(GONE);
                        negativeAction.setText("删除订单");
                        if (isRefunding)
                            negativeAction.setVisibility(GONE);
                        negativeAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hostAction.startDelete(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                            }
                        });
                    } else {
                        negativeAction.setVisibility(GONE);
                        positiveAction.setText("评价");
                        positiveAction.setTextColor(context.getResources().getColor(R.color.newAppPrimary));
                        positiveAction.setBackgroundResource(R.drawable.order_positive_button_bg);
                        positiveAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hostAction.startComment(getAdapterPosition(), datas.get(getAdapterPosition()).getOrderId());
                            }
                        });
                    }
                    break;
            }
        }
    }


    private String getStatusText(boolean hasPrice, String status, boolean payOff, boolean isEvaluated, String providerType) {
        switch (status) {
            case Constant.ORDER_STATUS_TOASSIGN:  //状态1
                if (hasPrice) {
                    if (!payOff) {
                        return "待付款";
                    } else {
                        return "等待商户/工人接单";
                    }
                } else {
                    return "等待商户/工人接单";
                }
            case Constant.ORDER_STATUS_TOTAKE:   //状态10
                if (hasPrice) {
                    if (!payOff) {
                        return "待付款";
                    } else {
                        if (TextUtils.equals(providerType, "2")) {
                            return "等待工人接单";
                        } else if (TextUtils.equals(providerType, "3")) {
                            return "等待商户接单";
                        } else {
                            return "等待商户/工人接单";
                        }
                    }
                } else {
                    if (TextUtils.equals(providerType, "2")) {
                        return "等待工人接单";
                    } else if (TextUtils.equals(providerType, "3")) {
                        return "等待商户接单";
                    } else {
                        return "等待商户/工人接单";
                    }
                }
            case Constant.ORDER_STATUS_MERCHANT_ASSIGNMENT:  //状态14
                if (hasPrice) {
                    if (!payOff) {
                        return "待付款";
                    } else {
                        return "待商户指派工人";
                    }
                } else {
                    return "待定价";
                }
            case Constant.ORDER_STATUS_WORKER_ORDER: //状态17
                if (hasPrice) {
                    if (!payOff) {
                        return "待付款";
                    } else {
                        return "待商户指派工人";
                    }
                } else {
                    return "待商户指派工人";
                }
            case Constant.ORDER_STATUS_TAKEN:   //状态20
                if (hasPrice) {
                    if (!payOff) {
                        return "待付款";
                    } else {
                        return "待工人服务";
                    }
                } else {
                    return "待定价";
                }
            case Constant.ORDER_STATUS_WORKER_COMPLETE:  //状态30
                return "待确认";
            case Constant.ORDER_STATUS_CONFIRMED:   //状态40
                if (isEvaluated) {
                    return "已完成";
                } else {
                    return "待评价";
                }
            case Constant.ORDER_STATUS_CANCELED:  //状态50
                return "已取消";
        }
        return "";
    }

    public interface Action {
        void startCancel();

        void startComment();

        void startDelete();

        void bind();
    }

    /**
     * Activity or Fragment needs to implements this interface
     * to be notified to execute specific action and need to callback to notify
     * this adapter to refresh layout
     */
    public interface HostAction {
        //支付过的订单去取消时用
        void startRemind();
        //没有支付过的订单取消时用
        void startCancel(int pos, String id);
        //去评价的时候用
        void startComment(int pos, String id);
        //删除订单的时候用
        void startDelete(int pos, String id);
        //去支付的时候用
        void startPay(int pos, String id);
        // 查看订单详情
        void startViewDetail(String orderId, String isKdEOrder);
        //确认服务完成
        void startConfirm(String orderId);

        //保险赔付
        void startInsuranceCompensation(String id);

        //查看原因
        void startSeeReason(String reason);

        //查看赔付
        void startSeeCompensation(String id);
    }

    private HostAction hostAction;

    public void setHostAction(HostAction hostAction) {
        this.hostAction = hostAction;
    }
}
