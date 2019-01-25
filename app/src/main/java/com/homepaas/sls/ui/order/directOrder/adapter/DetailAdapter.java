package com.homepaas.sls.ui.order.directOrder.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.FlatUtils;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.data.repository.Constant;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.ClaimsInfo;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.embedded_class.Picture;
import com.homepaas.sls.domain.entity.embedded_class.Refund;
import com.homepaas.sls.domain.entity.embedded_class.Service;
import com.homepaas.sls.event.BrowsePhotoEvent;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.adapter.OrderPhotoAdapter;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;

/**
 * Created by CJJ on 2016/9/10.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.Holder> {

    /*共九种状态*/
    static final int STATUS_PLACE = 0xff01;//已下单
    static final int STATUS_TAKE = 0xff02;//带接单
    static final int STATUS_TAKEN = 0xff03;//已接单
    static final int STATUS_PAY = 0xff04;//未付款（待付款）
    static final int STATUS_PAID = 0xff05;//已付款
    static final int STATUS_SERVE = 0xff06;//待服务
    static final int STATUS_SERVED = 0xff07;//工人服务完成
    static final int STATUS_CONFIR = 0xff08;//确认完成

    /**
     * 根据Ui 图将订单详情分为四到五个板块(Ui的图没啥层次性，Not Code Friendly）
     */
    //服务状态板块，就是一张对应状态的切图
    private static final int Block_ServiceStatus = 0;
    //服务内容板块(服务提供者信息、服务类型、服务时间、备注、服务价格等)
    private static final int Block_ServiceContent = 1;
    //退款信息
    private static final int Block_Refund = 2;
    //订单信息(联系人、服务地址、下单时间等)
    private static final int Block_OrderInfo = 3;
    //底部按钮板块
    private static final int Block_ActionButton = 4;

    private DetailOrderEntity orderInfo;
    private Context context;
    private String avatarUrl;
    private CommonDialog cancelDialog;
    private CommonDialog deleteDialog;
    private BigDecimal realPayPrice;
    private CommonDialog notTakenDialog;

    public DetailAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(DetailOrderEntity data) {
        this.orderInfo = data;
        notifyDataSetChanged();
    }

    public void notifyAvatar(String s) {
        avatarUrl = s;
        notifyItemChanged(Block_ServiceContent);
    }

    @IntDef({STATUS_PLACE, STATUS_TAKE, STATUS_TAKEN, STATUS_PAY, STATUS_PAID, STATUS_SERVE, STATUS_SERVED, STATUS_CONFIR})
    public @interface OrderStatus {
    }

    LayoutInflater inflater;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {

            inflater = LayoutInflater.from(parent.getContext());
            context = parent.getContext();
        }
        switch (viewType) {
            case Block_ServiceStatus:
                return new Holder(inflater.inflate(R.layout.item_orderdetail_status, parent, false));
            case Block_ServiceContent:
                return new Holder(inflater.inflate(R.layout.item_orderdetail_content, parent, false));
            case Block_Refund:
                return new Holder(inflater.inflate(R.layout.item_orderdetail_refund, parent, false));
            case Block_OrderInfo:
                return new Holder(inflater.inflate(R.layout.item_orderdetail_info, parent, false));
            case Block_ActionButton:
                return new Holder(inflater.inflate(R.layout.item_orderdetail_action, parent, false));
            default://返回一个空View
                return new Holder(new View(parent.getContext()));
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind();
    }


    @DrawableRes
    private int getStatusPic() {
        Refund refund = null;
        List<Refund> refunds = orderInfo.getRefunds();
        if (refunds != null && !refunds.isEmpty()) {
            refund = refunds.get(refunds.size() - 1);
        }
        boolean isEvaluated = TextUtils.equals(orderInfo.getIsEvaluated(), "1");
        boolean payOff = TextUtils.equals(orderInfo.getIsPayOff(), Constant.PAYOFF);
        String orderStatus = orderInfo.getOrderStatus();
        //是否是一键下单
        boolean quickOrder = orderInfo.getServiceProviderId() == null;
        boolean everTake = !TextUtils.isEmpty(orderInfo.getAcceptTime());//根据接单时间是否为空判断是否曾经接过单
        String acceptTimeStr = orderInfo.getAcceptTime();
        String paidTimeStr = orderInfo.getPayTime();
        boolean hasPrice = !TextUtils.isEmpty(orderInfo.getTotalPrice());//单价判断是否是定价的服务

        switch (orderStatus) {
            case Constant.ORDER_STATUS_TOASSIGN:
            case Constant.ORDER_STATUS_TOTAKE://待接单---此时如果是定价服务必须支付了工人才能接单或者该订单才能被抢
                if (hasPrice) {//定价
                    if (payOff) return R.mipmap.specify_the_order_pending_orders;
                    else return R.mipmap.one_click_orders_pricing_submit;
                } else {//非定价服务，不论指定还是一键，工人都可以接单（如果是一键，则可以被抢单）
                    return R.mipmap.one_click_orders_pending_orders;
                }
            case Constant.ORDER_STATUS_TAKEN://工人已接单
                if (hasPrice) {
                    if (!payOff)
                        return R.mipmap.one_click_orders_added;
                    else {
                        //先接单，后付款
                        if (paidTimeStr == null || acceptTimeStr.compareTo(paidTimeStr) > 0)
                            return R.mipmap.one_click_orders_service;
                            //先付款，后接单
                        else return R.mipmap.one_click_orders_pricing_service;

                    }
                } else {
                    if (!payOff)
                        return R.mipmap.one_click_orders_received;
                    else
                        return R.mipmap.one_click_orders_service;
                }
            case Constant.ORDER_STATUS_CANCELED://已取消
                //优先判断退款
                if (refund != null && !TextUtils.isEmpty(refund.getStatus())) {
                    switch (refund.getStatus()) {
                        case "1"://退款中
                            if (everTake)
                                //提交订单---->接单-->退款
                                return R.mipmap.one_click_orders_pricing_cancel3;
                            else
                                //提交订单--->退款
                                return R.mipmap.one_click_orders_pricing_cancel2;
                        case "2"://接单--->退款中--->退款成功---->取消成功
                            if (everTake)//提交订单---->接单---->退款中--->退款成功---->取消成功
                                return R.mipmap.one_click_orders_pricing_cancel4;
                            else//提交订单---->退款中--->退款成功---->取消成功
                                return R.mipmap.one_click_orders_cancel6;
                    }
                }
                //没有退款则走正常流程
                if (everTake)//若没有工人id,则认为是一键下单后直接取消
                    return R.mipmap.one_click_orders_cancel5;

                else {//提交订单--->取消订单
                    return R.mipmap.one_click_orders_pricing_cancel;

                }
            case Constant.ORDER_STATUS_WORKER_COMPLETE://工人服务完成，待确认
                return R.mipmap.one_click_orders_pricing_confirmed;
           /* case Constant.ORDER_STATUS_TOASSIGN:
                if (payOff)
                    return R.mipmap.one_click_orders_pricing_pending_orders;
                else
                    return R.mipmap.one_click_orders_pricing_submit;*/
            case Constant.ORDER_STATUS_CONFIRMED:
                if (isEvaluated)
                    return R.mipmap.one_click_orders_pricing_evaluation2;
                else
                    return R.mipmap.one_click_orders_pricing_evaluation;
        }
        return R.mipmap.specify_the_order_negotiable_for_orders;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return Block_ServiceStatus;
            case 1:
                return Block_ServiceContent;
            default:
                //需要判断是否是退款，退款状态，pos = 2位置为退款信息
                if (getItemCount() != 5)//有退款
                {
                    if (position == 2)
                        return Block_OrderInfo;
                    if (position == 3)
                        return Block_ActionButton;
                } else {
                    if (position == 2)
                        return Block_Refund;
                    if (position == 3)
                        return Block_OrderInfo;
                    return Block_ActionButton;
                }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {//是否有退款，有退款状态则多出一个Item
        int count = 4;
        if (orderInfo == null)
            return 0;
        if (orderInfo.refunds != null && !orderInfo.refunds.isEmpty()
                ) {//如果退款信息，则认为有退款信息Item
            count++;
        }
        return count;
    }


    class Holder extends RecyclerView.ViewHolder implements Action {

        private SatisifiedActionOrderDetailAdapter satisifiedActionOrderDetailAdapter;
        private List<ActivityNgInfoNew.ActivityNgDetail> satisfiedSpecialActivityList;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind() {
            Refund refund = null;
            List<Refund> refunds = orderInfo.getRefunds();
            if (refunds != null && !refunds.isEmpty()) {
                refund = refunds.get(refunds.size() - 1);
            }
            switch (getItemViewType()) {
                case Block_ServiceStatus://状态
//                    statusPic.setImageResource(getStatusPic());
                    getState();
                    break;
                case Block_ServiceContent://服务内容
                    if (orderInfo.getMerchantResponse() == null) {
                        providerMerchantLayout.setVisibility(View.GONE);
                    } else {
                        providerMerchantLayout.setVisibility(View.VISIBLE);
                        providerMerchantName.setText(orderInfo.getMerchantResponse().getName());
                        providerMerchantLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hostAction.startViewProviderInfo(orderInfo.getMerchantResponse().getId());
                            }
                        });
                        if (!TextUtils.isEmpty(orderInfo.getMerchantResponse().getIcon())) {
                            Glide.with(context).load(orderInfo.getMerchantResponse().getIcon()).into(providerMerchantAvatar);
                        }
                        merchantIconPhone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hostAction.startDial(orderInfo.getMerchantResponse().getId(), orderInfo.getMerchantResponse().getPhoneNumber());
                            }
                        });
                    }

                    if (orderInfo.getWorkerResponse() == null) {
                        providerLayout.setVisibility(View.GONE);
                    } else {
                        providerLayout.setVisibility(View.VISIBLE);
                        providerName.setText(orderInfo.getWorkerResponse().getName());
                        providerLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hostAction.startViewProviderInfo(orderInfo.getWorkerResponse().getId());
                            }
                        });
                        if (TextUtils.isEmpty(orderInfo.getWorkerResponse().getIcon())) {
                            Glide.with(context).load(orderInfo.getWorkerResponse().getIcon()).into(providerAvatar);
                        }
                        iconPhone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hostAction.startDial(orderInfo.getWorkerResponse().getId(), orderInfo.getWorkerResponse().getPhoneNumber());
                            }
                        });

                    }
                    //有定单总价则显示订单总价，在工人接单的情况下如果没有价格则显示与工人协商并改价格
                    if (orderInfo.getTotalPrice() == null) {
                        price.setText("¥" + cutUnnecessaryPrecision(orderInfo.getStartingPrice()) + "起");
                        paidLayout.setVisibility(View.GONE);
                        discountLayout.setVisibility(View.GONE);
                        couponLayout.setVisibility(View.GONE);
                        if (TextUtils.equals(orderInfo.getOrderStatus(), Constant.ORDER_STATUS_TAKEN)) {
                            totalPriceLayout.setVisibility(View.VISIBLE);
                            sum.setText("请与工人协商,并确定价格");
                        } else {
                            totalPriceLayout.setVisibility(View.GONE);
                        }
                    } else {
                        //定价
                        String totalPrice = orderInfo.getTotalPrice();
                        realPayPrice = new BigDecimal(totalPrice);
                        //单价
                        if (TextUtils.equals(orderInfo.getNegotiable(), "1")) {//面议服务
//                            if (TextUtils.isEmpty(orderInfo.getStartingPrice()))
//                                price.setText("面议");
//                            else
//                                price.setText("¥" + cutUnnecessaryPrecision(orderInfo.getStartingPrice()));
                            priceRel.setVisibility(View.GONE);
                        } else
                            priceRel.setVisibility(View.VISIBLE);
                        price.setText("¥" + cutUnnecessaryPrecision(orderInfo.getPrice()) + "/" + orderInfo.getUnitName() + "  ×" + orderInfo.getServiceNumber());
                        String totalPriceStr=orderInfo.getTotalPrice();
                        if(orderInfo.getClaimsInfo()!=null&&!TextUtils.isEmpty(orderInfo.getClaimsInfo().getClaimsAmount())){
                            insuranceRel.setVisibility(View.VISIBLE);
                            insuranceTitle.setText(orderInfo.getClaimsInfo().getClaimsAmount()+"元保险");
                            insuranceMoney.setText("¥"+orderInfo.getClaimsInfo().getClaimsAmount());
//                            BigDecimal claimsAmountDecimal=new BigDecimal(orderInfo.getClaimsInfo().getClaimsAmount()).setScale(2, RoundingMode.HALF_UP);
//                            BigDecimal totalPriceDecimal = new BigDecimal(orderInfo.getTotalPrice()).setScale(2, RoundingMode.HALF_UP);
//                            totalPriceStr=String.valueOf(claimsAmountDecimal.add(totalPriceDecimal));
                        }else {
                            insuranceRel.setVisibility(View.GONE);
                        }
                        //订单总价

                        sum.setText("¥" + cutUnnecessaryPrecision(totalPriceStr));


                        if (orderInfo.getActivityNgInfos() != null && orderInfo.getActivityNgInfos().isSpecialSatisfied(Float.valueOf(orderInfo.getTotalPrice()))) {
                            actionSpecial.setVisibility(View.VISIBLE);
                            satisfiedSpecialActivityList = orderInfo.getActivityNgInfos().getSatisfiedSpecialActivityList();
                            if (satisifiedActionOrderDetailAdapter == null) {
                                satisifiedActionOrderDetailAdapter = new SatisifiedActionOrderDetailAdapter();
                                actionSpecial.setAdapter(satisifiedActionOrderDetailAdapter);
                            } else {
                                actionSpecial.getAdapter();
                            }
                            satisifiedActionOrderDetailAdapter.setData(satisfiedSpecialActivityList);
                        } else {
                            actionSpecial.setVisibility(View.GONE);
                        }
//                        if (orderInfo.getActionMoney() != null) {
//                            discountLayout.setVisibility(View.VISIBLE);
//                            discount.setText("-¥" + cutUnnecessaryPrecision(orderInfo.getActionMoney()));
//                            activityTitle.setText(orderInfo.getActionTitle());
//                        } else discountLayout.setVisibility(View.GONE);

                        if (!TextUtils.isEmpty(orderInfo.getCouponMoney())) {
                            couponLayout.setVisibility(View.VISIBLE);
                            couponTitle.setText(orderInfo.getCouponTitle());
                            coupon.setText("-¥" + cutUnnecessaryPrecision(orderInfo.getCouponMoney()));
                        } else couponLayout.setVisibility(View.GONE);

                        //付款,总价减去活动减免以及优惠券减免
                        paidLayout.setVisibility(View.VISIBLE);
                        String isPayOff = orderInfo.getIsPayOff();
                        paytypeText.setText(TextUtils.equals(Constant.PAYOFF, isPayOff) ? "实付" : "待支付");

                        String payMoney=orderInfo.getPayMoney();
//                        if(orderInfo.getClaimsInfo()!=null&&!TextUtils.isEmpty(orderInfo.getClaimsInfo().getClaimsAmount())){
//                            BigDecimal claimsAmountDecimal=new BigDecimal(orderInfo.getClaimsInfo().getClaimsAmount()).setScale(2, RoundingMode.HALF_UP);
//                            BigDecimal payDecimal = new BigDecimal(orderInfo.getPayMoney()).setScale(2, RoundingMode.HALF_UP);
//                            payMoney=String.valueOf(claimsAmountDecimal.add(payDecimal));
//                        }
                        paidSum.setText("¥" + cutUnnecessaryPrecision(payMoney));

                        //如果已经取消，则不显示支付信息
                        if (TextUtils.equals(orderInfo.getOrderStatus(), Constant.ORDER_STATUS_CANCELED))
                            paidLayout.setVisibility(View.GONE);
                           /* //非定价(区间价格)情况隐藏活动和优惠券
                            discountLayout.setVisibility(View.GONE);
                            paidLayout.setVisibility(View.GONE);
                            price.setText("¥" + orderInfo.getMinPrice() + "-" + orderInfo.getMaxPrice() + "/" + orderInfo.getUnitName() + "  ×" + orderInfo.getServiceNumber());
                            Float minSum = Float.parseFloat(orderInfo.getMinPrice()) * Float.parseFloat(orderInfo.getServiceNumber());
                            Float maxSum = Float.parseFloat(orderInfo.getMaxPrice()) * Float.parseFloat(orderInfo.getServiceNumber());
                            sum.setText("¥" + minSum + " - " + maxSum);*/
                    }
                    if(orderInfo.getService()!=null) {
                        serviceType.setText(orderInfo.getService().getServiceName());
                        serviceTime.setText(TimeUtils.formatOrderTime(orderInfo.getService().getServiceStartAt()));
                    }
                    //是否有备注
                    boolean hasMemo = orderInfo.getService() != null && orderInfo.getService().getContent() != null && orderInfo.getService().getContent().size() > 0;
                    if (hasMemo) {
                        StringBuilder stringBuilder = new StringBuilder();
                        List<String> memos=new ArrayList<>();
                        if(orderInfo.getService()!=null) {
                            memos = orderInfo.getService().getContent();
                        }
                        int size = memos.size();
                        for (int i = 0; i < size; i++) {

                            String content = memos.get(i);
                            if (TextUtils.isEmpty(content)) ;
                            else {
                                stringBuilder.append(content);
                                stringBuilder.append("\n");
                            }

                        }
                        if (TextUtils.isEmpty(stringBuilder.toString())) {
                            noteLayout.setVisibility(View.GONE);
                            noteInput.setVisibility(View.GONE);
                        } else {
                            noteLayout.setVisibility(View.VISIBLE);
                            noteInput.setVisibility(View.VISIBLE);
                            noteInput.setText(stringBuilder.toString());
                        }
                    } else {
                        noteLayout.setVisibility(View.GONE);
                        noteInput.setVisibility(View.GONE);
                    }
                    noteInput.setEnabled(false);
                    if (orderInfo.getService()!=null&&orderInfo.getService().getPictures() != null && !orderInfo.getService().getPictures().isEmpty()) {
                        notePics.setVisibility(View.VISIBLE);
                        OrderPhotoAdapter adapter = new OrderPhotoAdapter(context, false);
                        adapter.setOnItemClickListener(new OrderPhotoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int index) {
                                EventBus.getDefault().post(new BrowsePhotoEvent(FlatUtils.extract(orderInfo.getService().getPictures(), new FlatUtils.ExtractFunc<String, Picture>() {
                                    @Override
                                    public String func(Picture picture) {
                                        return picture.getSmallPic();
                                    }
                                }), index));
                            }
                        });
                        adapter.setData(orderInfo.service.getPictures());
                        notePics.setLayoutManager(new GridLayoutManager(context, 4));
                        notePics.setAdapter(adapter);
                    } else {
                        notePics.setVisibility(View.GONE);
                    }
                    break;
                case Block_Refund://退款
                    if (orderInfo.refunds != null && orderInfo.refunds.size() > 0) {
                        refundTime.setText(TimeUtils.formatOrderTime(refund.refundTime));
                        refundStatus.setText(TextUtils.equals(refund.getStatus(), "1") ? "退款中" : "退款完成");
                        refundMoney.setText("¥" + cutUnnecessaryPrecision(refund.getRefundAmount()));
                        refundStr.setText("应退¥" + cutUnnecessaryPrecision(refund.refundAmount) + "误工费¥" + cutUnnecessaryPrecision(refund.lostIncome)  /*+"优惠券扣除¥"缺少优惠券扣除字段*/);
                    }
                    break;
                case Block_OrderInfo:
                    orderCode.setText(orderInfo.getOrderCode());
                    placeTime.setText(TimeUtils.formatOrderTime(orderInfo.createTime));
                    if(orderInfo.getService()!=null) {
                        clientName.setText(orderInfo.getService().getAddressInfo().getContact());
                        clientPhone.setText(orderInfo.getService().getAddressInfo().getPhoneNumber());
                        serviceAddress.setText(orderInfo.getService().getAddressInfo().getAddress1());
                    }

                    String acceptTimeStr = orderInfo.getAcceptTime();
                    String payTimeStr = orderInfo.getPayTime();
                    if (!TextUtils.isEmpty(acceptTimeStr)) {
                        acceptTimeLayout.setVisibility(View.VISIBLE);
                        this.acceptTime.setText(TimeUtils.formatOrderTime(acceptTimeStr));
                    } else acceptTimeLayout.setVisibility(View.GONE);

                    if (orderInfo.getCancelTime() != null) {
                        cancelTimeLayout.setVisibility(View.VISIBLE);
                        cancelTime.setText(TimeUtils.formatOrderTime(orderInfo.getCancelTime()));
                    } else cancelTimeLayout.setVisibility(View.GONE);

                    if (!TextUtils.isEmpty(payTimeStr)) {
                        payTimeLayout.setVisibility(View.VISIBLE);
                        paidTime.setText(TimeUtils.formatOrderTime(payTimeStr));
                    } else payTimeLayout.setVisibility(View.GONE);

                    if (!TextUtils.isEmpty(orderInfo.getConfirmTime())) {
                        confirmTimeLayout.setVisibility(View.VISIBLE);
                        confirmTime.setText(TimeUtils.formatOrderTime(orderInfo.getConfirmTime()));
                    } else confirmTimeLayout.setVisibility(View.GONE);

                    //完成时间即客户自己确认完成的时间
                    if (!TextUtils.isEmpty(orderInfo.getConfirmTime())) {
                        finishTimeLayout.setVisibility(View.VISIBLE);
                        finishTime.setText(TimeUtils.formatOrderTime(orderInfo.getConfirmTime()));
                    } else finishTimeLayout.setVisibility(View.GONE);

                    //接单时间和付款时间都存在，如果接单时间在后，则接单时间显示在下
                    if (!(TextUtils.isEmpty(acceptTimeStr) || TextUtils.isEmpty(payTimeStr))) {
                        if (acceptTimeStr.compareTo(payTimeStr) > 0) {
                            acceptTimeTitle.setText("付款时间：");
                            acceptTime.setText(TimeUtils.formatOrderTime(payTimeStr));

                            paidTimeTitle.setText("接单时间：");
                            paidTime.setText(TimeUtils.formatOrderTime(acceptTimeStr));
                        }
                    }

                    break;
                case Block_ActionButton://底部操作
                    bindAction();
                    break;
            }
        }

        private void setStats(int statusPictureInt, int statusTypeInt, int statusContentInt, int statusContentVis,
                              int statusLinV1Int, int statusLinV2Int, int statusLinV3Int, int statusLinV4Int,
                              int statusV1Int, int statusV2Int, int statusV3Int, int statusV4Int,
                              int statusImageInt) {

            statusPicture.setBackgroundResource(statusPictureInt);
            statusType.setText(statusTypeInt);
            statusContent.setText(statusContentInt);
            statusContent.setVisibility(statusContentVis == 1 ? View.VISIBLE : View.GONE);
            statusLinV1.setVisibility(statusLinV1Int == 1 ? View.VISIBLE : View.GONE);
            statusLinV2.setVisibility(statusLinV2Int == 1 ? View.VISIBLE : View.GONE);
            statusLinV3.setVisibility(statusLinV3Int == 1 ? View.VISIBLE : View.GONE);
            statusLinV4.setVisibility(statusLinV4Int == 1 ? View.VISIBLE : View.GONE);
            statusV1.setText(statusV1Int);
            statusV2.setText(statusV2Int);
            statusV3.setText(statusV3Int);
            statusV4.setText(statusV4Int);
            statusImage.setBackgroundResource(statusImageInt);

        }

        private void getState() {
            Refund refund = null;
            List<Refund> refunds = orderInfo.getRefunds();
            if (refunds != null && !refunds.isEmpty()) {
                refund = refunds.get(refunds.size() - 1);
            }
            boolean isEvaluated = TextUtils.equals(orderInfo.getIsEvaluated(), "1");
            boolean payOff = TextUtils.equals(orderInfo.getIsPayOff(), Constant.PAYOFF);
            String orderStatus = orderInfo.getOrderStatus();
            //是否是一键下单
            boolean quickOrder = orderInfo.getServiceProviderId() == null;
            boolean everTake = !TextUtils.isEmpty(orderInfo.getAcceptTime());//根据接单时间是否为空判断是否曾经接过单
            String acceptTimeStr = orderInfo.getAcceptTime();
            String paidTimeStr = orderInfo.getPayTime();
            boolean hasPrice = !TextUtils.isEmpty(orderInfo.getTotalPrice());//单价判断是否是定价的服务
            String providerType = orderInfo.getServiceProvideType();
            statusLinV1.setVisibility(View.VISIBLE);
            statusLinV2.setVisibility(View.VISIBLE);
            statusLinV3.setVisibility(View.VISIBLE);
            statusLinV4.setVisibility(View.VISIBLE);
            switch (orderStatus) {
                case Constant.ORDER_STATUS_TOASSIGN:
                    if (hasPrice) {
                        if (!payOff) {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.order_submitted, R.string.first_pay_then_wait, 1,
                                    1, 1, 1, 1,
                                    R.string.order_submitted, R.string.wait_pay, R.string.wait_order, R.string.wait_service,
                                    R.mipmap.indent_flow_1);
                        } else {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.pay_sussess, R.string.wait_patience_mer_worker, 1,
                                    1, 1, 1, 1,
                                    R.string.order_submitted, R.string.already_pay, R.string.wait_order, R.string.wait_service,
                                    R.mipmap.indent_flow_2);
                        }
                    } else {
                        setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.order_submitted, R.string.wait_patience_mer_worker, 1,
                                1, 1, 1, 1,
                                R.string.order_submitted, R.string.wait_order, R.string.wait_pay, R.string.wait_service,
                                R.mipmap.indent_flow_1);
                    }
                    break;
                case Constant.ORDER_STATUS_TOTAKE:
                    if (hasPrice) {
                        if (!payOff) {
                            if (TextUtils.equals(providerType, "2")) {
                                setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.order_submitted, R.string.first_pay_then_wait_worker, 1,
                                        1, 1, 1, 1,
                                        R.string.order_submitted, R.string.wait_pay, R.string.wait_woker_order, R.string.wait_service,
                                        R.mipmap.indent_flow_1);
                            } else if (TextUtils.equals(providerType, "3")) {
                                setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.order_submitted, R.string.first_pay_then_wait_merchant, 1,
                                        1, 1, 1, 1,
                                        R.string.order_submitted, R.string.wait_pay, R.string.wait_mer_order, R.string.wait_service,
                                        R.mipmap.indent_flow_1);
                            }
                        } else {
                            if (TextUtils.equals(providerType, "2")) {
                                setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.pay_sussess, R.string.wait_patience_worker_order, 1,
                                        1, 1, 1, 1,
                                        R.string.order_submitted, R.string.already_pay, R.string.wait_woker_order, R.string.wait_service,
                                        R.mipmap.indent_flow_2);

                            } else if (TextUtils.equals(providerType, "3")) {
                                setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.pay_sussess, R.string.wait_patience_merchant_assigned_worker, 1,
                                        1, 1, 1, 1,
                                        R.string.order_submitted, R.string.already_pay, R.string.wait_mer_order, R.string.wait_service,
                                        R.mipmap.indent_flow_2);
                            }
                        }
                    } else {
                        if (TextUtils.equals(providerType, "2")) {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.order_submitted, R.string.wait_patience_worker_order, 1,
                                    1, 1, 1, 1,
                                    R.string.order_submitted, R.string.wait_woker_order, R.string.wait_pay, R.string.wait_service,
                                    R.mipmap.indent_flow_1);

                        } else if (TextUtils.equals(providerType, "3")) {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.order_submitted, R.string.wait_patience_merchant_assigned_worker, 1,
                                    1, 1, 1, 1,
                                    R.string.order_submitted, R.string.wait_mer_order, R.string.wait_pay, R.string.wait_service,
                                    R.mipmap.indent_flow_1);
                        }
                    }
                    break;
                case Constant.ORDER_STATUS_MERCHANT_ASSIGNMENT:
                    if (hasPrice) {
                        if (!payOff) {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.wait_patience_merchant_then_assigned_worker, R.string.first_pay_or_client_cancel_order, 1,
                                    1, 1, 1, 1,
                                    R.string.order_submitted, R.string.order_mer_submitted, R.string.wait_pay, R.string.wait_service,
                                    R.mipmap.indent_flow_2);
                        } else {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.pay_sussess, R.string.wait_mer_assigned_worker_for_service, 1,
                                    1, 1, 1, 1,
                                    R.string.order_submitted, R.string.already_pay, R.string.wait_assigend_worker, R.string.wait_service,
                                    R.mipmap.indent_flow_2);
                        }
                    } else {
                        setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.order_mer_submitted, R.string.negotiate_price_with_customer_content, 1,
                                1, 1, 1, 1,
                                R.string.order_submitted, R.string.order_mer_submitted, R.string.pending_pricing, R.string.wait_pay,
                                R.mipmap.indent_flow_2);
                    }
                    break;
                case Constant.ORDER_STATUS_WORKER_ORDER:
                    if (hasPrice) {
                        if (!payOff) {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.mer_aggigend_worker_type, R.string.first_pay_or_client_cancel_order, 1,
                                    1, 1, 1, 1,
                                    R.string.order_submitted, R.string.already_assigend_worker, R.string.wait_pay, R.string.wait_service,
                                    R.mipmap.indent_flow_2);
                        } else {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.pay_sussess, R.string.wait_mer_assigned_worker_for_service, 1,
                                    1, 1, 1, 1,
                                    R.string.order_submitted, R.string.already_pay, R.string.wait_assigend_worker, R.string.wait_service,
                                    R.mipmap.indent_flow_2);
                        }
                    }
                    break;
                case Constant.ORDER_STATUS_TAKEN:
                    if (hasPrice) {
                        if (!payOff) {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.order_worker_submitted, R.string.first_pay_or_worker_cancel_order, 1,
                                    1, 1, 1, 1,
                                    R.string.order_submitted, R.string.already_assigend_worker, R.string.wait_pay, R.string.wait_service,
                                    R.mipmap.indent_flow_2);
                        } else {
                            //先接单，后付款
                            if (paidTimeStr == null || acceptTimeStr.compareTo(paidTimeStr) > 0) {
                                if (TextUtils.equals(providerType, "2")) {
                                    setStats(R.mipmap.client_v3_1_0_ic_indent_await, R.string.door_to_door_service, R.string.wait_worker_door_to_door_service, 1,
                                            1, 1, 1, 1,
                                            R.string.order_submitted, R.string.order_worker_submitted, R.string.already_pay, R.string.wait_service,
                                            R.mipmap.indent_flow_3);
                                } else {
                                    setStats(R.mipmap.client_v3_1_0_ic_indent_await, R.string.door_to_door_service, R.string.wait_worker_door_to_door_service, 1,
                                            1, 1, 1, 1,
                                            R.string.order_submitted, R.string.already_assigend_worker, R.string.already_pay, R.string.wait_service,
                                            R.mipmap.indent_flow_3);
                                }
                            } else {
                                if (TextUtils.equals(providerType, "2")) {
                                    setStats(R.mipmap.client_v3_1_0_ic_indent_await, R.string.door_to_door_service, R.string.wait_worker_door_to_door_service, 1,
                                            1, 1, 1, 1,
                                            R.string.order_submitted, R.string.already_pay, R.string.order_worker_submitted, R.string.wait_service,
                                            R.mipmap.indent_flow_3);
                                } else {
                                    setStats(R.mipmap.client_v3_1_0_ic_indent_await, R.string.door_to_door_service, R.string.wait_worker_door_to_door_service, 1,
                                            1, 1, 1, 1,
                                            R.string.order_submitted, R.string.already_pay, R.string.already_assigend_worker, R.string.wait_service,
                                            R.mipmap.indent_flow_3);
                                }
                            }
                        }
                    } else {
                        setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.order_worker_submitted, R.string.negotiate_price_with_worker_content, 1,
                                1, 1, 1, 1,
                                R.string.order_submitted, R.string.order_worker_submitted, R.string.pending_pricing, R.string.wait_pay,
                                R.mipmap.indent_flow_2);
                    }
                    break;
                case Constant.ORDER_STATUS_WORKER_COMPLETE:
                    if (TextUtils.equals(providerType, "2")) {
                        setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.worker_already_service, R.string.confirm_service_completion_content, 1,
                                1, 1, 1, 1,
                                R.string.order_submitted, R.string.order_worker_submitted, R.string.already_service, R.string.pending_confirmation,
                                R.mipmap.indent_flow_3);
                    } else {
                        setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.worker_already_service, R.string.confirm_service_completion_content, 1,
                                1, 1, 1, 1,
                                R.string.order_submitted, R.string.already_assigend_worker, R.string.already_service, R.string.pending_confirmation,
                                R.mipmap.indent_flow_3);
                    }
                    break;
                case Constant.ORDER_STATUS_CONFIRMED:
                    if (!isEvaluated) {
                        if (TextUtils.equals(providerType, "2")) {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.already_service_type, R.string.evaluation_service, 1,
                                    1, 1, 1, 0,
                                    R.string.order_submitted, R.string.order_worker_submitted, R.string.service_completion, R.string.pending_confirmation,
                                    R.mipmap.indent_flow_three_4);
                        } else {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.already_service_type, R.string.evaluation_service, 1,
                                    1, 1, 1, 0,
                                    R.string.order_submitted, R.string.already_assigend_worker, R.string.service_completion, R.string.pending_confirmation,
                                    R.mipmap.indent_flow_three_4);
                        }
                    } else {
                        if (TextUtils.equals(providerType, "2")) {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.already_service_type, R.string.no_date, 0,
                                    1, 1, 1, 0,
                                    R.string.order_submitted, R.string.order_worker_submitted, R.string.service_completion, R.string.pending_confirmation,
                                    R.mipmap.indent_flow_three_4);
                        } else {
                            setStats(R.mipmap.client_v3_1_0_ic_indent_aaffirm, R.string.already_service_type, R.string.no_date, 0,
                                    1, 1, 1, 0,
                                    R.string.order_submitted, R.string.already_assigend_worker, R.string.service_completion, R.string.pending_confirmation,
                                    R.mipmap.indent_flow_three_4);
                        }
                    }
                    break;
                case Constant.ORDER_STATUS_CANCELED:
                    if (refund != null && !TextUtils.isEmpty(refund.getStatus())) {
                        switch (refund.getStatus()) {
                            case "1"://退款中
                                if (everTake) {
                                    //提交订单---->接单-->退款
                                    if (TextUtils.equals(providerType, "2")) {
                                        setStats(R.mipmap.client_v3_1_0_ic_indent_caution, R.string.refund_in, R.string.refund_in_72_hours, 1,
                                                1, 1, 1, 1,
                                                R.string.order_submitted, R.string.order_worker_submitted, R.string.refund_in, R.string.refund_success,
                                                R.mipmap.indent_flow_2);
                                    } else {
                                        setStats(R.mipmap.client_v3_1_0_ic_indent_caution, R.string.refund_in, R.string.refund_in_72_hours, 1,
                                                1, 1, 1, 1,
                                                R.string.order_submitted, R.string.already_assigend_worker, R.string.refund_in, R.string.refund_success,
                                                R.mipmap.indent_flow_2);
                                    }
                                } else {
                                    //提交订单--->退款
                                    setStats(R.mipmap.client_v3_1_0_ic_indent_caution, R.string.refund_in, R.string.no_date, 0,
                                            1, 1, 1, 0,
                                            R.string.order_submitted, R.string.refund_in, R.string.refund_success, R.string.pending_confirmation,
                                            R.mipmap.indent_flow_three_3);
                                }
                            case "2"://接单--->退款中--->退款成功---->取消成功
                                if (everTake) {//提交订单---->接单---->退款中--->退款成功---->取消成功
                                    if (TextUtils.equals(providerType, "2")) {
                                        setStats(R.mipmap.client_v3_1_0_ic_indent_caution, R.string.cancel_order, R.string.no_date, 0,
                                                1, 1, 1, 1,
                                                R.string.order_submitted, R.string.order_worker_submitted, R.string.refund_success, R.string.cancel_order,
                                                R.mipmap.indent_flow_4);
                                    } else {
                                        setStats(R.mipmap.client_v3_1_0_ic_indent_caution, R.string.cancel_order, R.string.no_date, 0,
                                                1, 1, 1, 1,
                                                R.string.order_submitted, R.string.already_assigend_worker, R.string.refund_success, R.string.cancel_order,
                                                R.mipmap.indent_flow_4);
                                    }
                                } else {//提交订单---->退款中--->退款成功---->取消成功
                                    setStats(R.mipmap.client_v3_1_0_ic_indent_caution, R.string.cancel_order, R.string.no_date, 0,
                                            1, 1, 1, 0,
                                            R.string.order_submitted, R.string.refund_success, R.string.cancel_order, R.string.cancel_order,
                                            R.mipmap.indent_flow_three_4);
                                }
                        }
                    } else {
                        //没有退款则走正常流程
                        if (everTake) {//若没有工人id,则认为是一键下单后直接取消
                            if (TextUtils.equals(providerType, "2")) {
                                setStats(R.mipmap.client_v3_1_0_ic_indent_caution, R.string.cancel_order, R.string.no_date, 0,
                                        1, 1, 1, 0,
                                        R.string.order_submitted, R.string.order_worker_submitted, R.string.cancel_order, R.string.cancel_order,
                                        R.mipmap.indent_flow_three_4);
                            } else {
                                setStats(R.mipmap.client_v3_1_0_ic_indent_caution, R.string.cancel_order, R.string.no_date, 0,
                                        1, 1, 0, 0,
                                        R.string.order_submitted, R.string.already_assigend_worker, R.string.cancel_order, R.string.cancel_order,
                                        R.mipmap.indent_flow_three_4);
                            }
                        } else {//提交订单--->取消订单
                            setStats(R.mipmap.client_v3_1_0_ic_indent_caution, R.string.cancel_order, R.string.no_date, 0,
                                    1, 1, 0, 0,
                                    R.string.order_submitted, R.string.cancel_order, R.string.cancel_order, R.string.cancel_order,
                                    R.mipmap.indent_flow_two);
                        }
                    }
                    break;
            }
        }


        private void bindAction() {
            String status = orderInfo.getOrderStatus();
            boolean payOff = TextUtils.equals(orderInfo.getIsPayOff(), Constant.PAYOFF);
            boolean canceled = TextUtils.equals(Constant.ORDER_STATUS_CANCELED, orderInfo.getOrderStatus());
            boolean hasPrice = orderInfo.getTotalPrice() != null && !TextUtils.isEmpty(orderInfo.getTotalPrice());
            List<Refund> refunds = orderInfo.getRefunds();
            boolean isRefunding = refunds != null && !refunds.isEmpty() && "1".equals(refunds.get(refunds.size() - 1).getStatus());
            negativeAction.setVisibility(View.VISIBLE);
            positiveAction.setVisibility(View.VISIBLE);
            negativeAction.setTextColor(context.getResources().getColor(R.color.appTextNormal));
            positiveAction.setTextColor(context.getResources().getColor(R.color.appTextNormal));
            negativeAction.setBackgroundResource(R.drawable.order_start_bg);
            positiveAction.setBackgroundResource(R.drawable.order_start_bg);
            ClaimsInfo claimsInfo = orderInfo.getClaimsInfo();
            final Service service = orderInfo.getService();
            showClaims.setVisibility(View.GONE);
            //显示赔付的信息
            if (claimsInfo != null) {
                final ClaimsInfo.ClaimsManagementInfo claimsManagementInfo = claimsInfo.getClaimsManagementInfo();
                if (!TextUtils.isEmpty(claimsInfo.getIsDisplayApplyClaimsBtn()) && TextUtils.equals("1", claimsInfo.getIsDisplayApplyClaimsBtn())) {
                    showClaims.setVisibility(View.VISIBLE);
                    showClaims.setText("保险赔付");
                    if (hostAction != null) {
                        showClaims.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (service != null) {
                                    hostAction.startInsuranceCompensation(orderInfo);
                                }
                            }
                        });
                    }
                }
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
                }else {
                    claimsStatus.setVisibility(View.GONE);
                }
            }


            switch (status) {
//                case Constant.ORDER_STATUS_TOASSIGN://待指派
//                    negativeAction.setVisibility(View.VISIBLE);
//                    negativeAction.setText("取消订单");
//                    negativeAction.setTextColor(context.getResources().getColor(R.color.appTextNormal));
//                    negativeAction.setBackgroundResource(R.drawable.order_start_bg);
//                    negativeAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            cancelDialog = new CommonDialog.Builder()
//                                    .setContent("确定取消订单吗？")
//                                    .setCancelButton("先不取消", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            cancelDialog.dismiss();
//                                        }
//                                    })
//                                    .setConfirmButton("确定", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            hostAction.startCancel(-1, orderInfo.getOrderId());
//                                        }
//                                    }).setTitle("取消订单").create();
//                            cancelDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
//                        }
//                    });
//
////                    positiveAction.setVisibility(View.GONE);
//
//                    positiveAction.setTextColor(context.getResources().getColor(R.color.decorateOrange));
//                    positiveAction.setText("支付");
//                    positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
//                    positiveAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            notTakenDialog = new CommonDialog.Builder()
//                                    .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
//                                    .setTitle("温馨提示")
//                                    .showAction(false)
//                                    .create();
//                            notTakenDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
//                        }
//                    });
//                    break;
//                case Constant.ORDER_STATUS_TOASSIGN://待指派
//                case Constant.ORDER_STATUS_TOTAKE://已经下单，待接单
//                    negativeAction.setVisibility(View.VISIBLE);
//                    negativeAction.setText("取消订单");
//                    negativeAction.setTextColor(context.getResources().getColor(R.color.appTextNormal));
//                    negativeAction.setBackgroundResource(R.drawable.order_start_bg);
//                    negativeAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            cancelDialog = new CommonDialog.Builder()
//                                    .setContent("确定取消订单吗？")
//                                    .setCancelButton("先不取消", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            cancelDialog.dismiss();
//                                        }
//                                    })
//                                    .setConfirmButton("确定", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            hostAction.startCancel(-1, orderInfo.getOrderId());
//                                        }
//                                    }).setTitle("取消订单").create();
//                            cancelDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
//                        }
//                    });
//
//                    positiveAction.setTextColor(context.getResources().getColor(R.color.decorateOrange));
//                    positiveAction.setText("支付");
//                    positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
//                    positiveAction.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            notTakenDialog = new CommonDialog.Builder()
//                                    .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
//                                    .setTitle("温馨提示")
//                                    .showAction(false)
//                                    .create();
//                            notTakenDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
//                        }
//                    });
//
////                    if (hasPrice) {
////                        if (!payOff) {//未支付
////                            positiveAction.setTextColor(context.getResources().getColor(R.color.decorateOrange));
////                            positiveAction.setText("支付");
////                            positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
////                            positiveAction.setOnClickListener(new View.OnClickListener() {
////                                @Override
////                                public void onClick(View v) {
////                                    notTakenDialog = new CommonDialog.Builder()
////                                            .setContent("您已成功下单，请等待工人接单后完成支付，预计时间0.5 -1 小时。详情请查看【首页-个人中心-我的订单】")
////                                            .setTitle("温馨提示")
////                                            .showAction(false)
////                                            .create();
////                                    notTakenDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
////                                }
////                            });
////                        } else {//已支付
////                            positiveAction.setVisibility(View.GONE);
////                        }
////                    }
//                    break;
                case Constant.ORDER_STATUS_CANCELED://
                    negativeAction.setText("删除订单");
                    if (isRefunding)
                        negativeAction.setVisibility(View.GONE);
                    negativeAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteDialog = new CommonDialog.Builder()
                                    .setTitle("删除订单")
                                    .setContent("确定删除订单吗？")
                                    .setCancelButton("取消", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            deleteDialog.dismiss();
                                        }
                                    })
                                    .setConfirmButton("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            deleteDialog.dismiss();
                                            hostAction.startDelete(getAdapterPosition(), orderInfo.getOrderId());
                                        }
                                    })
                                    .showTitle(false).create();
                            deleteDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
                        }
                    });
                    positiveAction.setVisibility(View.GONE);
                    break;
                case Constant.ORDER_STATUS_CONFIRMED://客户已经确认完成
                    if ("1".equals(orderInfo.isEvaluated)) {
                        //已评价过
                        positiveAction.setVisibility(View.GONE);
                        negativeAction.setText("删除订单");
                        negativeAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteDialog = new CommonDialog.Builder()
                                        .setTitle("删除订单")
                                        .setContent("确定删除订单吗？删除后不可恢复哦")
                                        .setCancelButton("取消", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                deleteDialog.dismiss();
                                            }
                                        })
                                        .setConfirmButton("删除", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                hostAction.startDelete(getAdapterPosition(), orderInfo.getOrderId());
                                            }
                                        })
                                        .showTitle(true).create();
                                deleteDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
                            }
                        });
                        if (isRefunding)
                            negativeAction.setVisibility(View.GONE);
                    } else {
                        negativeAction.setVisibility(View.GONE);
                        positiveAction.setText("评价");
                        positiveAction.setTextColor(context.getResources().getColor(R.color.newAppPrimary));
                        positiveAction.setBackgroundResource(R.drawable.order_positive_button_bg);
                        positiveAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hostAction.startComment(getAdapterPosition(), orderInfo.getOrderId());
                            }
                        });
                    }
                    break;
                case Constant.ORDER_STATUS_WORKER_COMPLETE://工人完成,待客户确认
                    negativeAction.setVisibility(View.GONE);
                    positiveAction.setTextColor(context.getResources().getColor(R.color.newAppPrimary));
                    positiveAction.setText("确认服务完成");
                    positiveAction.setBackgroundResource(R.drawable.order_positive_button_bg);
                    positiveAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hostAction.startConfirm(getAdapterPosition(), orderInfo.getOrderId());
                        }
                    });
                    break;
                case Constant.ORDER_STATUS_TOASSIGN://待指派
                case Constant.ORDER_STATUS_TOTAKE://已经下单，待接单
                case Constant.ORDER_STATUS_MERCHANT_ASSIGNMENT:  //待商户指派工人
                case Constant.ORDER_STATUS_WORKER_ORDER:  //待商户指派的工人接单
                case Constant.ORDER_STATUS_TAKEN:
                    negativeAction.setText("取消订单");
                    negativeAction.setVisibility(View.GONE);//取消订单按钮均不要，入口改为Menu中的取消订单
                    negativeAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            cancelDialog = new CommonDialog.Builder()
                                    .setContent("确定取消订单吗？")
                                    .setCancelButton("先不取消", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            cancelDialog.dismiss();
                                        }
                                    })
                                    .setConfirmButton("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            hostAction.startCancel(getAdapterPosition(), orderInfo.getOrderId());
                                        }
                                    }).setTitle("").create();
                            cancelDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
                        }
                    });
                    //现在的逻辑接单后不一定有价格,所以需要考虑是否有价格以及是否支付三种情况
                    if (hasPrice) {
                        if (!payOff) {
                            positiveAction.setText("支付");
                            positiveAction.setTextColor(context.getResources().getColor(R.color.decorateOrange));
                            positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
                            positiveAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    hostAction.startPay(getAdapterPosition(), orderInfo.getOrderId());
                                }
                            });
                        } else
                            positiveAction.setVisibility(View.GONE);
                    } else {

                        if (TextUtils.equals(status, "1") || TextUtils.equals(status, "10")) {
                            positiveAction.setVisibility(View.GONE);
                        } else {
                            positiveAction.setVisibility(View.VISIBLE);
                            positiveAction.setText(R.string.pay_str);
                            positiveAction.setTextColor(context.getResources().getColor(R.color.decorateOrange));
                            positiveAction.setBackgroundResource(R.drawable.order_middle_bg);
                            positiveAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    notTakenDialog = new CommonDialog.Builder()
                                            .setContent(TextUtils.equals(orderInfo.getServiceProvideType(), "2") ? "请与工人协商服务价格" : "请与商户协商服务价格")
                                            .setTitle("温馨提示")
                                            .showAction(false)
                                            .create();
                                    notTakenDialog.show(((CommonBaseActivity) context).getSupportFragmentManager(), null);
                                }
                            });
                        }
                    }
                    break;
            }
        }

        @Nullable
        @Bind(R.id.status_pic)
        ImageView statusPic;
        @Nullable
        @Bind(R.id.status_picture)
        ImageView statusPicture;
        @Nullable
        @Bind(R.id.status_type)
        TextView statusType;
        @Nullable
        @Bind(R.id.status_content)
        TextView statusContent;
        @Nullable
        @Bind(R.id.status_v1)
        TextView statusV1;
        @Nullable
        @Bind(R.id.status_lin_v1)
        LinearLayout statusLinV1;
        @Nullable
        @Bind(R.id.status_v2)
        TextView statusV2;
        @Nullable
        @Bind(R.id.status_lin_v2)
        LinearLayout statusLinV2;
        @Nullable
        @Bind(R.id.status_v3)
        TextView statusV3;
        @Nullable
        @Bind(R.id.status_lin_v3)
        LinearLayout statusLinV3;
        @Nullable
        @Bind(R.id.status_v4)
        TextView statusV4;
        @Nullable
        @Bind(R.id.status_lin_v4)
        LinearLayout statusLinV4;
        @Nullable
        @Bind(R.id.status_image)
        ImageView statusImage;
        @Nullable
        @Bind(R.id.provider_avatar)
        RoundedImageView providerAvatar;
        @Nullable
        @Bind(R.id.provider_merchant_avatar)
        RoundedImageView providerMerchantAvatar;
        @Nullable
        @Bind(R.id.provider_merchant_name)
        TextView providerMerchantName;
        @Nullable
        @Bind(R.id.merchant_icon_phone)
        TextView merchantIconPhone;
        @Nullable
        @Bind(R.id.provider_merchant_layout)
        LinearLayout providerMerchantLayout;
        @Nullable
        @Bind(R.id.provider_layout)
        LinearLayout providerLayout;
        @Nullable
        @Bind(R.id.icon_phone)
        TextView iconPhone;
        @Nullable
        @Bind(R.id.provider_name)
        TextView providerName;
        @Nullable
        @Bind(R.id.service_time)
        TextView serviceTime;
        @Nullable
        @Bind(R.id.note_layout)
        RelativeLayout noteLayout;
        @Nullable
        @Bind(R.id.note_input)
        EditText noteInput;
        @Nullable
        @Bind(R.id.note_pics)
        RecyclerView notePics;
        @Nullable
        @Bind(R.id.price)
        TextView price;
        @Nullable
        @Bind(R.id.activity_title)
        TextView activityTitle;
        @Nullable
        @Bind(R.id.sum)
        TextView sum;
        @Nullable
        @Bind(R.id.discount_layout)
        RelativeLayout discountLayout;

        //优惠券
        @Nullable
        @Bind(R.id.coupon_layout)
        RelativeLayout couponLayout;
        @Nullable
        @Bind(R.id.coupon_title)
        TextView couponTitle;
        @Nullable
        @Bind(R.id.coupon)
        TextView coupon;

        @Nullable
        @Bind(R.id.total_price_layout)
        RelativeLayout totalPriceLayout;
        @Nullable
        @Bind(R.id.pay_type_text)
        TextView paytypeText;
        @Nullable
        @Bind(R.id.discount)
        TextView discount;
        @Nullable
        @Bind(R.id.paid_sum)
        TextView paidSum;
        @Nullable
        @Bind(R.id.refund_status)
        TextView refundStatus;
        @Nullable
        @Bind(R.id.refund_money)
        TextView refundMoney;
        @Nullable
        @Bind(R.id.refund_str)
        TextView refundStr;
        @Nullable
        @Bind(R.id.client_name)
        TextView clientName;
        @Nullable
        @Bind(R.id.client_phone)
        TextView clientPhone;
        @Nullable
        @Bind(R.id.textView)
        TextView textView;
        @Nullable
        @Bind(R.id.service_address)
        TextView serviceAddress;
        @Nullable
        @Bind(R.id.order_code)
        TextView orderCode;
        @Nullable
        @Bind(R.id.place_time)
        TextView placeTime;
        @Nullable
        @Bind(R.id.accept_time_title)
        TextView acceptTimeTitle;
        @Nullable
        @Bind(R.id.accept_time)
        TextView acceptTime;
        @Nullable
        @Bind(R.id.paid_time_title)
        TextView paidTimeTitle;
        @Nullable
        @Bind(R.id.paid_time)
        TextView paidTime;
        @Nullable
        @Bind(R.id.cancel_time)
        TextView cancelTime;
        @Nullable
        @Bind(R.id.service_type)
        TextView serviceType;
        @Nullable
        @Bind(R.id.refund_time)
        TextView refundTime;
        @Nullable
        @Bind(R.id.negative_action)
        TextView negativeAction;
        @Nullable
        @Bind(R.id.positive_action)
        TextView positiveAction;
        @Nullable
        @Bind(R.id.accept_time_layout)
        LinearLayout acceptTimeLayout;
        @Nullable
        @Bind(R.id.cancel_time_layout)
        LinearLayout cancelTimeLayout;
        @Nullable
        @Bind(R.id.paid_layout)
        RelativeLayout paidLayout;
        @Nullable
        @Bind(R.id.pay_time_layout)
        LinearLayout payTimeLayout;
        @Nullable
        @Bind(R.id.confirm_time_layout)
        LinearLayout confirmTimeLayout;
        @Nullable
        @Bind(R.id.confirm_time)
        TextView confirmTime;
        @Nullable
        @Bind(R.id.finish_time_layout)
        LinearLayout finishTimeLayout;
        @Nullable
        @Bind(R.id.finish_time)
        TextView finishTime;
        //活动
        @Nullable
        @Bind(R.id.action_special)
        RecyclerView actionSpecial;

        @Nullable
        @Bind(R.id.price_rel)
        RelativeLayout priceRel;
        @Nullable
        @Bind(R.id.claims_status)
        TextView claimsStatus;
        @Nullable
        @Bind(R.id.show_claims)
        TextView showClaims;
        @Nullable
        @Bind(R.id.insurance_title)
        TextView insuranceTitle;
        @Nullable
        @Bind(R.id.insurance_money)
        TextView insuranceMoney;
        @Nullable
        @Bind(R.id.insurance_rel)
        RelativeLayout insuranceRel;
    }

    public interface Action {
        void bind();
    }

    public interface DetailOrderHostAction {
        void startDelete(int pos, String orderId);

        void startComment(int pos, String orderId);

        void startPay(int pos, String orderId);

        void startCancel(int pos, String orderId);

        void startConfirm(int pos, String orderId);

        void startViewProviderInfo(String providerId);

        void startDial(String providerId, String serviceProviderPhone);

        //保险赔付
        void startInsuranceCompensation(DetailOrderEntity orderEntity);

        //查看原因
        void startSeeReason(String reason);

        //查看赔付
        void startSeeCompensation(String id);
    }

    private DetailOrderHostAction hostAction;

    public void setHostAction(DetailOrderHostAction hostAction) {
        this.hostAction = hostAction;
    }
}
