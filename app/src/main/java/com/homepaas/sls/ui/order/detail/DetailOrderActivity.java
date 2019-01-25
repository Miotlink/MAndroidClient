package com.homepaas.sls.ui.order.detail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.PhoneNumberUtils;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.di.component.DaggerOrderComponent;
import com.homepaas.sls.di.module.OrderInfoModule;
import com.homepaas.sls.domain.entity.DetailOrder;
import com.homepaas.sls.event.ConfirmCompletedEvent;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.mvp.view.order.OrderActionView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.coupon.ShareCouponActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.adapter.OrderPhotoAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.ManuallyCheckBox;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by CMJ on 2016/6/22.
 */

public class DetailOrderActivity extends CommonBaseActivity implements View.OnClickListener, OrderActionView {
    @Nullable
    @Bind(R.id.avatar)
    ImageView avatar;
    @Nullable
    @Bind(R.id.name)
    TextView name;
    @Nullable
    @Bind(R.id.like)
    ManuallyCheckBox like;
    @Nullable
    @Bind(R.id.like_anim)
    TextView likeAnim;
    @Nullable
    @Bind(R.id.collection)
    ManuallyCheckBox collection;
    @Nullable
    @Bind(R.id.collection_anim)
    TextView collectionAnim;
    @Nullable
    @Bind(R.id.score)
    ManuallyCheckBox score;
    @Nullable
    @Bind(R.id.picture_list)
    RecyclerView pictureList;
    @Nullable
    @Bind(R.id.picture_container)
    RelativeLayout pictureContainer;
    @Nullable
    @Bind(R.id.order_time)
    TextView orderTime;
    @Nullable
    @Bind(R.id.order_code)
    TextView orderCode;
    @Nullable
    @Bind(R.id.complete_time)
    TextView completeTime;
    @Nullable
    @Bind(R.id.main_layout)
    LinearLayout mainLayout;
    @Nullable
    @Bind(R.id.service_status)
    TextView serviceStatus;
    @Nullable
    @Bind(R.id.bottom_action_container)
    LinearLayout bottomActionContainer;
    Button cancelOrder;
    Button payOrder;
    Button confirmOrder;
    Button evaluateOrder;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.service_requirements)
    TextView serviceRequirements;
    @Bind(R.id.refund_time)
    TextView refundTime;
    @Bind(R.id.refund_amount)
    TextView refundAmount;
    @Bind(R.id.refund_status)
    TextView refundStatus;
    @Bind(R.id.ratingbar)
    RatingBar ratingbar;
    @Bind(R.id.rating)
    TextView rating;
    @Bind(R.id.evaluate_time)
    TextView evaluateTime;
    @Bind(R.id.evaluate_content)
    TextView evaluateContent;
    @Bind(R.id.recycler_view_evaluation)
    RecyclerView recyclerViewEvaluation;
    @Bind(R.id.reply_content)
    TextView replyContent;
    @Bind(R.id.reply_content_layout)
    LinearLayout replyContentLayout;
    @Bind(R.id.evaluation_layout)
    RelativeLayout evaluationLayout;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.contact_provider)
    TextView contactProvider;
    @Bind(R.id.contact_service)
    TextView contactService;
    @Nullable
    @Bind(R.id.price)
    TextView servicePrice;
    @Bind(R.id.refund_layout)
    RelativeLayout refundLayout;
    private String orderId;
    private String serviceProderName;
    @Inject
    OrderPresenter presenter;
    private boolean isWithEnvelops;
    private String price;
    private OrderPhotoAdapter adapter;
    private CommonDialog confirmDialog;
    private CommonDialog cancelDialog;
    private String serviceProviderPhone;
    private CommonDialog callProviderDialog;
    private CommonDialog callCustomerServiceDialog;
    private String customerServicePhone;
    private String typText;
    private String serviceType;
    private static final String DEAULT_SERVICETYPE = "2";

    public static void start(Context context, String orderId) {
        start(context, orderId, DEAULT_SERVICETYPE);
    }

    /**
     * @param context
     * @param orderCode
     * @param serviceType 是否有优惠券
     */
    public static void start(Context context, String orderCode, String serviceType) {
        Intent intent = new Intent(context, DetailOrderActivity.class);
        intent.putExtra(Constant.OrderId, orderCode);
        intent.putExtra(Constant.ServiceType, serviceType);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_order;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        orderId = getIntent().getStringExtra(Constant.OrderId);
        serviceType = getIntent().getStringExtra(Constant.ServiceType);
//        presenter.setOrderDetailView(this);
        presenter.setOrderActionView(this);
    }

    @Override
    protected void initData() {
        presenter.getOrderDetail(orderId);
    }

/*    @Override
    public void render(DetailOrder order) {
        bindCommonData(order);
        injectActionLayout(order);//根据订单状态动态加入底部操作按钮布局
    }*/


    @OnClick({R.id.share_coupon})
    public void shareCoupon() {
        startActivity(new Intent(this, ShareCouponActivity.class));
    }

    /**
     * 动态加入底部按钮操作布局
     *
     * @param order
     */
    private void injectActionLayout(DetailOrder order) {
        LayoutInflater layoutInflater = getLayoutInflater();
        boolean isPayOff = TextUtils.equals(order.getIsPayOff(), "1");
        boolean canCancel = TextUtils.equals(order.getCanCancel(), "1");
        //是否待议价
        boolean isMargin = Double.valueOf(order.getPrice()) <= 0.00;
        double money = Double.valueOf(order.getPrice());
        int status = Integer.valueOf(order.getOrderStatus());
        if (isPayOff) {
            if (status == Constant.PLACE) { //下单已经支付，只显示信息，没有操作按钮
                //do nothing just present those information
                serviceStatus.setText("已下单");
            }

            if (status == Constant.TAKEN)//支付并且已经接单，只显示信息
            {
                serviceStatus.setText("已接单");
                //do nothing
            }
            if (status == Constant.COMPLETE)//已支付，服务完成，显示确认完成
            {
                serviceStatus.setText("服务完成");
                completeTime.setText(order.getFinishTime() == null ? "" : order.getFinishTime());
                View rootView = layoutInflater.inflate(R.layout.orderdetail_action_complete_confirm_layout, bottomActionContainer, true);
                confirmOrder = (Button) rootView.findViewById(R.id.confirmOrder);
            }
            if (status == Constant.CONFIRM)//支付，服务确认完成
            {
                serviceStatus.setText("服务完成");
                View rootView = layoutInflater.inflate(R.layout.orderdetail_action_complete_evaluate_layout, bottomActionContainer, true);
                evaluateOrder = (Button) rootView.findViewById(R.id.evaluateOrder);
            }

        } else {
            if (status == Constant.PLACE)//下单未支付，显示取消和支付按钮,如果待议价，只显示取消按钮
            {
                if (money > 0) {
                    serviceStatus.setText("已下单");
                    View rootView = layoutInflater.inflate(R.layout.orderdetail_action_placed_notpaid_layout, bottomActionContainer, true);
                    payOrder = (Button) rootView.findViewById(R.id.payOrder);
                    cancelOrder = (Button) rootView.findViewById(R.id.cancelOrder);
                    payOrder.setOnClickListener(this);
                    cancelOrder.setOnClickListener(this);
                }else{
                    View rootView = layoutInflater.inflate(R.layout.orderdetail_action_placed_nomoney_layout,bottomActionContainer,true);
                    cancelOrder = (Button) rootView.findViewById(R.id.cancelOrder);
                    cancelOrder.setOnClickListener(this);
                }

            }
            if (status == Constant.COMPLETE) {//服务已完成，显示确认按钮
                if (serviceStatus != null) {
                    serviceStatus.setText("服务完成");
                }
                View rootView = layoutInflater.inflate(R.layout.orderdetail_action_complete_confirm_layout, bottomActionContainer, true);
                confirmOrder = (Button) rootView.findViewById(R.id.confirmOrder);
                confirmOrder.setOnClickListener(this);
            }
            if (status == Constant.CONFIRM) {//服务已经确认，显示评价按钮
                serviceStatus.setText("服务完成");
                View rootView = layoutInflater.inflate(R.layout.orderdetail_action_complete_evaluate_layout, bottomActionContainer, true);
                evaluateOrder = (Button) rootView.findViewById(R.id.evaluateOrder);
                evaluateOrder.setOnClickListener(this);
            }
        }
        setUpListener();
    }

    private void setUpListener() {
        if (payOrder != null) {
            //支付
            payOrder.setOnClickListener(this);
        }
        if (cancelOrder != null) {
            //取消--------取消之前要请求服务器，确定订单是否已经接单
            cancelOrder.setOnClickListener(this);
        }
        if (evaluateOrder != null) {
            //评价
//            mNavigator.addComment(this,orderId);
            evaluateOrder.setOnClickListener(this);
        }
        if (confirmOrder != null) {
            //确认
            confirmOrder.setOnClickListener(this);
        }
    }

    private void bindCommonData(DetailOrder order) {
        if (order != null) {
            if (order.getServiceProvidePic() != null) {
                Glide.with(this).load(order.getServiceProvidePic()).into(avatar);
            }
            serviceProviderPhone = order.getServiceProviderPhoneNumber();
            customerServicePhone = order.getCustomerServicePhoneNumber();
            name.setText(order.getServiceProviderName());
            collection.setChecked(Float.valueOf(order.getServiceProviderCollection()) > 0);
            collection.setText(order.getServiceProviderCollection());
            like.setChecked(Float.valueOf(order.getServiceProviderPraise()) > 0);
            like.setText(order.getServiceProviderPraise());
            score.setChecked(Float.valueOf(order.getServiceProviderScore()) > 0);
            score.setText(order.getServiceProviderScore());
            orderId = order.getOrderId();
            contactProvider.setText("联系" + getTypText());
            //点击拨打客服电话
            contactService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callCustomerServiceDialog == null)
                        callCustomerServiceDialog = new CommonDialog.Builder()
                                .setContent("是否电话联系客服"+getString(R.string.service_phone_number))
                                .setConfirmButton("是", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mNavigator.dial(DetailOrderActivity.this, customerServicePhone);
                                    }
                                }).setCancelButton("否", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        callCustomerServiceDialog.dismiss();
                                    }
                                }).showTitle(false).create();
                    callCustomerServiceDialog.show(getSupportFragmentManager(), null);
                }
            });
            //点击拨打服务商电话
            if (TextUtils.equals(Constant.CANCALL, order.getServiceProviderCanCall()))
                contactProvider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callProviderDialog == null)
                            callProviderDialog = new CommonDialog.Builder()
                                    .setContent("是否电话联系" + getTypText() + PhoneNumberUtils.encryptPhone(serviceProviderPhone))
                                    .setConfirmButton("是", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mNavigator.dial(DetailOrderActivity.this, serviceProviderPhone);
                                        }
                                    }).setCancelButton("否", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            callProviderDialog.dismiss();
                                        }
                                    }).showTitle(false).create();
                        callProviderDialog.show(getSupportFragmentManager(), null);
                    }
                });
            else {
                showMessage("暂时不能拨打该服务商电话");
            }
            /*服务内容*/
            serviceRequirements.setText(order.getService().getContent());
            adapter = new OrderPhotoAdapter(this, false);
            if (order.getService().getPictures().isEmpty()) {
                pictureContainer.setVisibility(View.GONE);
            } else {
//                adapter.setData(order.getService().getPictures());
                pictureList.setAdapter(adapter);
            }

            /*服务金额*/
            price = order.getPrice();
            servicePrice.setText(Double.valueOf(price) <= 0 ? "待议价" : price+"元");
            /*退款记录*/
            DetailOrder.Refund refund = order.getUpdateRefund();
            if (refund != null) {
                refundLayout.setVisibility(View.VISIBLE);
                refundLayout.setVisibility(View.VISIBLE);
                if (refund.refundtime!=null)
                refundTime.setText(TimeUtils.formatOrderTime(refund.refundtime));
                refundAmount.setText(refund.refundAmount);
                refundStatus.setText(refund.status);
            }

            /*服务信息*/
            orderCode.setText("订单编号:" + order.getOrderCode());
            orderTime.setText("下单时间:" + TimeUtils.formatOrderTime(order.getCreateTime()));

            /*评价内容*/
            if (order.getEvaluation().getScore() != null) {
                evaluationLayout.setVisibility(View.VISIBLE);
                if (order.getEvaluation().getScore() != null)
                    ratingbar.setRating(Float.valueOf(order.getEvaluation().getScore()));
                if (order.getEvaluation().getScore() != null)
                    rating.setText(order.getEvaluation().getScore());
                if (order.getEvaluation().getPictures() != null) {
                    OrderPhotoAdapter evaluationAdapter = new OrderPhotoAdapter(this, false);
//                    evaluationAdapter.setData(order.getEvaluation().getPictures());
                    recyclerViewEvaluation.setAdapter(evaluationAdapter);
                }
            } else {
                evaluationLayout.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerOrderComponent.builder()
                .orderInfoModule(new OrderInfoModule())
                .applicationComponent(getApplicationComponent())
                .build().inject(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideLoading();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payOrder:
//                mNavigator.payOrder(this, orderId, price, name.getText().toString().trim());
                mNavigator.pay(this, orderId, price, serviceProderName);
                this.finish();
                break;
            case R.id.confirmOrder:
                presenter.confirmOrder(new ConfirmCompletedEvent("",orderId,orderId));
                break;
            case R.id.cancelOrder:
                if (cancelDialog == null) {
                    cancelDialog = new CommonDialog.Builder()
                            .setContent("是否确认取消订单？")
                            .setCancelButton("否", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cancelDialog.dismiss();
                                }
                            }).setConfirmButton("是", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    presenter.cancelOrder(orderId, -1,"");
                                    cancelDialog.dismiss();
                                }
                            }).showTitle(false).create();
                }
                cancelDialog.show(getSupportFragmentManager(), null);
                break;
            case R.id.evaluateOrder:
                mNavigator.addComment(this, orderId, null, null);
                break;
        }
    }

    @OnClick(R.id.contact_provider)
    public void contanctProvider() {
        //拨打电话
    }

    @Override
    public void onOrderCancel(String msg, int position) {
        showMessage(msg);
        mNavigator.myOrderList(this);
    }

    @Override
    public void onOrderDelete(String msg, int position) {

    }

    @Override
    public void onOrderConfirm(final String orId, int position) {
        if (confirmDialog == null) {
            confirmDialog = new CommonDialog.Builder()
                    .setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmDialog.dismiss();
                        }
                    }).showTitle(false).setContent("确认订单完成？")
                    .setConfirmButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转到评价页面
                            mNavigator.addComment(getContext(), orderId, null, null);
                        }
                    }).create();
        }
        confirmDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    protected void retrieveData() {
        presenter.getOrderDetail(orderId);
    }

    public String getTypText() {
        if (TextUtils.equals(serviceType, "2"))
            return "工人";
        else return "商户";
    }
}
