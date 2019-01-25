package com.homepaas.sls.ui.order.directOrder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.data.entity.MorePPEntity;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ClaimsInfo;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.ExpressDetailOutputEntity;
import com.homepaas.sls.domain.entity.OrderBtnInfo;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.domain.entity.embedded_class.Service;
import com.homepaas.sls.event.ConfirmCompletedEvent;
import com.homepaas.sls.mvp.presenter.order.ErrandServiceDetailPresenter;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.mvp.view.order.ErrandServiceDetailView;
import com.homepaas.sls.mvp.view.order.OrderActionView;
import com.homepaas.sls.mvp.view.order.OrderDetailView;
import com.homepaas.sls.ui.account.AccountDetailsActivity;
import com.homepaas.sls.ui.adapter.MorePPListAdapter;
import com.homepaas.sls.ui.bottomsheet.ConfirmCompletedBottomSheetDialogFragment;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.directOrder.adapter.ExpressOrderTrackingAdapter;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.ui.widget.NewCommonDialog;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.StaticData;
import com.homepaas.sls.util.WindowUtil;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/7/6.
 * 跑腿服务详情
 */

public class ErrandServiceDetailActivity extends CommonBaseActivity implements ErrandServiceDetailView, OrderDetailView, OrderActionView, BaseRecyclerAdapter.OnItemClickListener {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.more)
    TextView more;
    @Bind(R.id.running_man)
    TextView runningMan;
    @Bind(R.id.call_running_man)
    TextView callRunningMan;
    @Bind(R.id.run_alone_rl)
    RelativeLayout runAloneRl;
    @Bind(R.id.order_tracking_recyclerView)
    RecyclerView orderTrackingRecyclerView;
    @Bind(R.id.cancel_reason)
    TextView cancelReason;
    @Bind(R.id.cancel_reason_lin)
    LinearLayout cancelReasonLin;
    @Bind(R.id.cooperative_enterprise)
    TextView cooperativeEnterprise;
    @Bind(R.id.third_order_number)
    TextView thirdOrderNumber;
    @Bind(R.id.send_address)
    TextView sendAddress;
    @Bind(R.id.send_people)
    TextView sendPeople;
    @Bind(R.id.receiver_address)
    TextView receiverAddress;
    @Bind(R.id.receiver_people)
    TextView receiverPeople;
    @Bind(R.id.delivery_time)
    TextView deliveryTime;
    @Bind(R.id.remark)
    TextView remark;
    @Bind(R.id.goods_name)
    TextView goodsName;
    @Bind(R.id.goods_weight)
    TextView goodsWeight;
    @Bind(R.id.estimated_cost)
    TextView estimatedCost;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.order_create_time)
    TextView orderCreateTime;
    @Bind(R.id.pay_order)
    TextView payOrder;
    @Bind(R.id.pay_rel)
    LinearLayout payRel;
    @Bind(R.id.empty_view)
    NestedScrollView emptyView;
    @Bind(R.id.swipe_refresh)
    HeaderViewLayout swipeRefresh;
    @Bind(R.id.title_rel)
    RelativeLayout titleRel;
    @Bind(R.id.claims_button)
    TextView claimsButton;
    @Bind(R.id.claims_status)
    TextView claimsStatus;

    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;

    private String mCallingPhone;
    private String mTitle;
    private String orderId;
    private String driverPhone;
    private String cancelMsg;
    private String isPayOff;
    private String orderCode;

    private NewCommonDialog displayBackClaimsReasonDialog;
    private DetailOrderEntity orderInfo;
    private NewCommonDialog remindDialog;
    private MorePPListAdapter morePPListAdapter;
    private ExpressOrderTrackingAdapter expressOrderTrackingAdapter;
    private OrderBtnInfo orderBtnInfo;
    private Service service;
    private View contentView;
    private PopupWindow popupWindow;
    @Inject
    ErrandServiceDetailPresenter errandServiceDetailPresenter;
    @Inject
    OrderPresenter orderPresenter;


    public static void start(Context context, String orderId) {
        Intent intent = new Intent(context, ErrandServiceDetailActivity.class);
        intent.putExtra(StaticData.ORDER_ID, orderId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_errand_service;
    }

    @Override
    protected void initView() {
        orderPresenter.setOrderDetailView(this);
        orderPresenter.setOrderActionView(this);
        orderId = getIntent().getStringExtra(StaticData.ORDER_ID);
        expressOrderTrackingAdapter = new ExpressOrderTrackingAdapter();
        orderTrackingRecyclerView.setAdapter(expressOrderTrackingAdapter);
        errandServiceDetailPresenter.setErrandServiceDetailView(this);
        swipeRefresh.setOnRefreshListener(mOnRefreshListener);

        initPPWindow();
    }

    private void initPPWindow() {
        morePPListAdapter = new MorePPListAdapter(this);
        morePPListAdapter.setOnItemClickListener(this);
        contentView = LayoutInflater.from(this).inflate(R.layout.more_pp_list, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (ErrandServiceDetailActivity.this != null) {
                    setBackgroundAlpha(1f);
                }
            }
        });
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ErrandServiceDetailActivity.this != null) {
                    popupWindow.dismiss();
                    setBackgroundAlpha(1f);
                }
            }
        });
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(morePPListAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        errandServiceDetailPresenter.getErrandServiceDetail(orderId);
        orderPresenter.getOrderDetail(orderId);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            errandServiceDetailPresenter.getErrandServiceDetail(orderId);
        }

        @Override
        public void onLoadMore() {
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }

    /**
     * 跑腿详情
     *
     * @param expressDetailOutputEntity
     */
    @Override
    public void renderDetail(ExpressDetailOutputEntity expressDetailOutputEntity) {
        swipeRefresh.stopRefresh();
        if (expressDetailOutputEntity != null) {
            cancelMsg = expressDetailOutputEntity.getCancelMsg();
            if (!TextUtils.isEmpty(expressDetailOutputEntity.getState()) && (TextUtils.equals("10", expressDetailOutputEntity.getState()) || TextUtils.equals("-1", expressDetailOutputEntity.getState()))) {
                more.setText("客服");
            } else {
                more.setText("更多");
            }
            if (!TextUtils.isEmpty(expressDetailOutputEntity.getDriverName())) {
                runAloneRl.setVisibility(View.VISIBLE);
            } else {
                runAloneRl.setVisibility(View.GONE);
            }
            runningMan.setText(expressDetailOutputEntity.getDriverName());
            driverPhone = expressDetailOutputEntity.getDriverPhone();
            if (!TextUtils.isEmpty(driverPhone)) {
                callRunningMan.setVisibility(View.VISIBLE);
            } else {
                callRunningMan.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(expressDetailOutputEntity.getCancelReason())) {
                cancelReasonLin.setVisibility(View.VISIBLE);
                cancelReason.setText(expressDetailOutputEntity.getCancelReason());
            } else {
                cancelReasonLin.setVisibility(View.GONE);
            }
            cooperativeEnterprise.setText(expressDetailOutputEntity.getShipperName());
            thirdOrderNumber.setText(expressDetailOutputEntity.getLogisticCode());
            ExpressDetailOutputEntity.ServiceAddressResponse sendAddressResponse = expressDetailOutputEntity.getSenderAddress();
            if (sendAddressResponse != null) {
                sendAddress.setText(sendAddressResponse.getAddress1() + sendAddressResponse.getAddress2());
                sendPeople.setText(sendAddressResponse.getContact() + "\t" + sendAddressResponse.getPhoneNumber());
            }
            ExpressDetailOutputEntity.ServiceAddressResponse receiverAddressResponse = expressDetailOutputEntity.getRevicerAddress();
            if (receiverAddressResponse != null) {
                receiverAddress.setText(receiverAddressResponse.getAddress1() + receiverAddressResponse.getAddress2());
                receiverPeople.setText(receiverAddressResponse.getContact() + "\t" + receiverAddressResponse.getPhoneNumber());
            }
            deliveryTime.setText(TimeUtils.formatOrderTime(expressDetailOutputEntity.getServiceTime()));
            remark.setText(expressDetailOutputEntity.getNote());
            goodsName.setText(expressDetailOutputEntity.getGoodsName());
            goodsWeight.setText(expressDetailOutputEntity.getGoodsWeight() + "KG");
            estimatedCost.setText(expressDetailOutputEntity.getPrice() + "元");
            orderNumber.setText(expressDetailOutputEntity.getOrderCode());
            if (!TextUtils.isEmpty(expressDetailOutputEntity.getCreateTime())) {
                orderCreateTime.setText(TimeUtils.formatOrderTime(expressDetailOutputEntity.getCreateTime()));
            }
            if (expressDetailOutputEntity.getTraces() != null && !expressDetailOutputEntity.getTraces().isEmpty()) {
                orderTrackingRecyclerView.setVisibility(View.VISIBLE);
                expressOrderTrackingAdapter.setList(expressDetailOutputEntity.getTraces());
            } else {
                orderTrackingRecyclerView.setVisibility(View.GONE);
            }
            isPayOff = expressDetailOutputEntity.getIsPayOff();
        }
    }

    @OnClick({R.id.call_running_man, R.id.back, R.id.pay_order, R.id.more, R.id.claims_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_running_man:
                if (!TextUtils.isEmpty(driverPhone)) {
                    dial(driverPhone, "联系跑男");
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.more:
                String moreStr = more.getText().toString();
                if (!TextUtils.isEmpty(moreStr) && orderBtnInfo != null) {
                    if (TextUtils.equals("更多", moreStr)) {
                        showMenu();
                    } else if (TextUtils.equals("客服", moreStr)) {
                        dial("4008-262-056", "联系客服");
                    }
                }
                break;
            case R.id.pay_order:
                //跑腿修改成跟小时工等其他服务一样，（注意：跑腿没有增加服务按钮和理赔按钮）：
                if (orderBtnInfo != null && TextUtils.equals("1", orderBtnInfo.getIsDisplayGotoPayBtn())) {
                    //去支付
                    Intent intent = new Intent(this, PayActivity.class);
                    intent.putExtra(Constant.OrderId, orderId);
                    startActivity(intent);
                } else if (orderBtnInfo != null && TextUtils.equals("1", orderBtnInfo.getIsDisplayClientConfirmBtn())) {
                    //确认完成
                    StringBuffer sb = new StringBuffer();
                    sb.append(TimeUtils.formatOrderTime(service.getServiceStartAt()));
                    sb.append("的");
                    sb.append(service.getServiceName());
                    sb.append("服务");
                    //确认完成弹框
                    ConfirmCompletedBottomSheetDialogFragment fragment = ConfirmCompletedBottomSheetDialogFragment.newInstance(ConfirmCompletedEvent.ORDER_DETAIL, sb.toString(), orderId);
                    fragment.setConfirmCompletedListener(new ConfirmCompletedBottomSheetDialogFragment.onConfirmCompletedListener() {
                        @Override
                        public void onConfirmCompleted(String flag, String orderID, String orderCode) {
                            orderPresenter.confirmOrder(new ConfirmCompletedEvent(flag, orderID, orderCode));
                        }
                    });
                    fragment.show(getSupportFragmentManager(), ConfirmCompletedBottomSheetDialogFragment.class.getSimpleName());
                } else if (orderBtnInfo != null && TextUtils.equals("1", orderBtnInfo.getIsDisplayGotoEvaluateBtn())) {
                    //评价
                    StringBuffer serviceDetails = new StringBuffer();
                    serviceDetails.append("(");
                    serviceDetails.append(TimeUtils.formatOrderTime(service.getServiceStartAt()));
                    serviceDetails.append("的");
                    serviceDetails.append(service.getServiceName());
                    serviceDetails.append("服务");
                    serviceDetails.append(")");
                    EvaluationOrderActivity.start(this, service.getServiceId(), orderCode, serviceDetails.toString());
                    overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);
                }
                break;
            case R.id.claims_button://保险理赔
                showClaims();
                break;
            default:
        }
    }

    /**
     * 保险赔付类按钮显示
     */
    private void showClaims() {
        String claimsText = claimsButton.getText().toString();
        if (TextUtils.equals(getResources().getString(R.string.client_insurance_claims), claimsText)) {
            displayApplyClaims();
        } else if (TextUtils.equals(getResources().getString(R.string.client_view_the_payment), claimsText)) {
            displayShowClaims();
        } else if (TextUtils.equals(getResources().getString(R.string.client_view_the_reason), claimsText)) {
            displayBackClaimsReason();
        }
    }

    private void showMenu() {
        if (Build.VERSION.SDK_INT < 24) {
            popupWindow.showAsDropDown(more, 0, -titleRel.getHeight());
        } else {
            int[] location = new int[2];
            titleRel.getLocationOnScreen(location);
            int y = location[1];
            popupWindow.showAtLocation(titleRel, Gravity.NO_GRAVITY, WindowUtil.getInstance().getScreenWidth(this), 0);
        }
        setBackgroundAlpha(0.9f);
    }

    @Override
    public void render(DetailOrderEntity order, long requestTime) {
        if (order != null) {
            orderInfo = order;
            orderCode = order.getOrderCode();
            service = order.getService();
            orderBtnInfo = order.getOrderBtnInfo();
            payRel.setVisibility(View.GONE);
            payOrder.setVisibility(View.GONE);
            claimsButton.setVisibility(View.GONE);
            claimsStatus.setVisibility(View.GONE);
            //跑腿修改成跟小时工等其他服务一样，（注意：跑腿没有增加服务按钮和理赔按钮）：
            if (orderBtnInfo != null && TextUtils.equals("1", orderBtnInfo.getIsDisplayGotoPayBtn())) {
                //去支付
                payRel.setVisibility(View.VISIBLE);
                payOrder.setVisibility(View.VISIBLE);
                payOrder.setText(R.string.client_to_pay_order);
            } else if (orderBtnInfo != null && TextUtils.equals("1", orderBtnInfo.getIsDisplayClientConfirmBtn())) {
                //确认完成
                payRel.setVisibility(View.VISIBLE);
                payOrder.setVisibility(View.VISIBLE);
                payOrder.setText(R.string.client_confirm_order);
            } else if (orderBtnInfo != null && TextUtils.equals("1", orderBtnInfo.getIsDisplayGotoEvaluateBtn())) {
                //评价
                payRel.setVisibility(View.VISIBLE);
                payOrder.setVisibility(View.VISIBLE);
                payOrder.setText(R.string.client_to_evaluate_order);
            }

            ClaimsInfo claimsInfo = order.getClaimsInfo();
            if (claimsInfo != null && claimsInfo.getClaimsManagementInfo() != null && !TextUtils.isEmpty(claimsInfo.getClaimsManagementInfo().getAuditStatus())) {
                payRel.setVisibility(View.VISIBLE);
                claimsStatus.setVisibility(View.VISIBLE);
                claimsStatus.setText(claimsInfo.getClaimsManagementInfo().getAuditStatus());
            } else {
                claimsStatus.setVisibility(View.GONE);
            }
            if (claimsInfo != null && TextUtils.equals("1", claimsInfo.getIsDisplayApplyClaimsBtn())) {
                payRel.setVisibility(View.VISIBLE);
                claimsButton.setVisibility(View.VISIBLE);
                claimsButton.setText(R.string.client_insurance_claims);
            }
            if (claimsInfo != null && TextUtils.equals("1", claimsInfo.getIsDisplayShowClaimsBtn())) {
                payRel.setVisibility(View.VISIBLE);
                claimsButton.setVisibility(View.VISIBLE);
                claimsButton.setText(R.string.client_view_the_payment);
            }
            if (claimsInfo != null && TextUtils.equals("1", claimsInfo.getIsDisplayBackClaimsReasonBtn())) {
                payRel.setVisibility(View.VISIBLE);
                claimsButton.setVisibility(View.VISIBLE);
                claimsButton.setText(R.string.client_view_the_reason);
            }
            //右上角操作菜单按钮
            initRightMenu(orderBtnInfo);
        }
    }

    @Override
    public void renderTrackOrderStatus(TrackOrderInfo orderInfo) {

    }

    @Override
    public void onOrderCancel(String msg, int position) {

    }

    @Override
    public void onOrderDelete(String msg, int position) {
        showMessage("删除订单成功");
        this.finish();
    }

    @Override
    public void onOrderConfirm(String orderId, int position) {
        //订单确认完成后，进行评价
        StringBuffer serviceDetails = new StringBuffer();
        serviceDetails.append("(");
        serviceDetails.append(TimeUtils.formatOrderTime(service.getServiceStartAt()));
        serviceDetails.append("的");
        serviceDetails.append(service.getServiceName());
        serviceDetails.append("服务");
        serviceDetails.append(")");
        EvaluationOrderActivity.start(this, service.getServiceId(), orderCode, serviceDetails.toString());
        overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);
    }

    private void initRightMenu(OrderBtnInfo orderBtnInfo) {
        //取消订单、删除订单、投诉[0.5] 该三种情况下显示更多 ，其他情况显示客服
        if (orderBtnInfo == null) {
            more.setText("客服");
            return;
        }
        //是否显示“取消订单”按钮
        String isDisplayCancelOrderBtn = orderBtnInfo.getIsDisplayCancelOrderBtn();
        //是否显示“删除订单”按钮
        String isDisplayDeleteOrderBtn1 = orderBtnInfo.getIsDisplayDeleteOrderBtn();
        //是否显示“投诉”按钮
        String isDisplayComplaintsBtn1 = orderBtnInfo.getIsDisplayComplaintsBtn();
        if ("1".equals(isDisplayCancelOrderBtn) || "1".equals(isDisplayDeleteOrderBtn1) || "1".equals(isDisplayComplaintsBtn1)) {
            more.setText("更多");
            List<MorePPEntity> list = new ArrayList<>();
            if ("1".equals(isDisplayCancelOrderBtn))
                list.add(new MorePPEntity("取消订单", MorePPEntity.CANCEL_ORDER));
            if ("1".equals(isDisplayDeleteOrderBtn1))
                list.add(new MorePPEntity("删除订单", MorePPEntity.DELETE_ORDER));
            if ("1".equals(isDisplayComplaintsBtn1))
                list.add(new MorePPEntity("投诉", MorePPEntity.COMPL_AINT));
            morePPListAdapter.setData(list);
        } else {
            more.setText("客服");
        }
    }


    @Override
    public void onItemClick(View itemView, int pos) {
        MorePPEntity morePPEntity = morePPListAdapter.getData().get(pos);
        //取消订单
        if (morePPEntity.getFlag().equals(MorePPEntity.CANCEL_ORDER)) {
            if (orderBtnInfo != null && TextUtils.equals("0", orderBtnInfo.getIsGotoCancelPage())) {
                remindOrder();
            } else {
                OrderCancelReasonsActivity.start(this, orderId);
            }
        } else if (morePPEntity.getFlag().equals(MorePPEntity.DELETE_ORDER)) {//删除订单
            orderPresenter.deleteOrder(orderId, 0);
        } else if (morePPEntity.getFlag().equals(MorePPEntity.COMPL_AINT))//投诉
        {
            mNavigator.complaint(ErrandServiceDetailActivity.this, orderId);
        }
        if (ErrandServiceDetailActivity.this != null) {
            popupWindow.dismiss();
            setBackgroundAlpha(1f);
        }
    }

    /**
     * 客户支付后点击取消订单提醒
     */

    private void remindOrder() {
        if (remindDialog == null) {
            remindDialog = new NewCommonDialog.Builder()
                    .setTitle("取消订单")
                    .setContentGravity(Gravity.CENTER)
                    .setContent("您已完成支付，是否联系客服取消订单？")
                    .setConfirmButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            remindDialog.dismiss();
                            dial("4008-262-056", "联系客服");
                        }
                    })
                    .setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            remindDialog.dismiss();
                        }
                    }).create();
        }
        remindDialog.show(getSupportFragmentManager(), null);
    }

    // 拨打电话
    private void dial(String phone, String title) {
        boolean ret = requestRuntimePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG}, REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        if (ret) {
            NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title);
            serviceFragment.show(getSupportFragmentManager(), null);
        } else {
            mCallingPhone = phone;
            mTitle = title;
            requestRuntimePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG}, REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        }
    }

    @Override
    protected void onDestroy() {
        if (swipeRefresh != null) {
            swipeRefresh.stopRefresh();
            swipeRefresh.destory();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CALL_AND_CALL_LOG:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                dial(mCallingPhone, mTitle);
                break;
        }
    }


    /**
     * 保险理赔相关逻辑
     */
    /**
     * 保险赔付
     */
    private void displayApplyClaims() {
        if (orderBtnInfo != null) {
            String serivceTime = "";
            Service service = orderInfo.getService();
            if (service != null) {
                serivceTime = service.getServiceStartAt();
            }
            List<ClaimsInfo.ClaimsManagementInfo.ClaimsManagementTypeInfo> claimsReasons = null;
            if (orderInfo.getClaimsInfo() != null && orderInfo.getClaimsInfo().getClaimsManagementInfo() != null && orderInfo.getClaimsInfo().getClaimsManagementInfo().getClaimsManagementTypeInfo() != null) {
                claimsReasons = orderInfo.getClaimsInfo().getClaimsManagementInfo().getClaimsManagementTypeInfo();
            }
            ApplyCompensationActivity.start(this, orderId, serivceTime, claimsReasons);
        }
    }


    /**
     * 查看赔付
     */
    private void displayShowClaims() {
        if (orderInfo != null && orderInfo.getClaimsInfo() != null && orderInfo.getClaimsInfo().getClaimsManagementInfo() != null && !TextUtils.isEmpty(orderInfo.getClaimsInfo().getClaimsManagementInfo().getSettlementId())) {
            String settlementId = orderInfo.getClaimsInfo().getClaimsManagementInfo().getSettlementId();
            if (!TextUtils.isEmpty(settlementId)) {
                AccountDetailsActivity.start(this, Integer.parseInt(settlementId));
            }
        }
    }

    /**
     * 查看原因
     */
    private void displayBackClaimsReason() {
        if (orderInfo != null && orderInfo.getClaimsInfo() != null && orderInfo.getClaimsInfo().getClaimsManagementInfo() != null && !TextUtils.isEmpty(orderInfo.getClaimsInfo().getClaimsManagementInfo().getBackReason())) {
            String backReason = orderInfo.getClaimsInfo().getClaimsManagementInfo().getBackReason();
            displayBackClaimsReasonDialog = new NewCommonDialog.Builder()
                    .setTitle("原因")
                    .setContent(backReason)
                    .showButton(false)
                    .setContentGravity(Gravity.CENTER)
                    .setConfirmButton("我知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            displayBackClaimsReasonDialog.dismiss();
                        }
                    }).create();
            displayBackClaimsReasonDialog.show(getSupportFragmentManager(), null);
        }
    }

}
