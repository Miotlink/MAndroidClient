package com.homepaas.sls.ui.order.directOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.homepaas.sls.data.entity.UpdateListEntity;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ClaimsInfo;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.OrderBtnInfo;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.domain.entity.embedded_class.Service;
import com.homepaas.sls.event.ConfirmCompletedEvent;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.mvp.presenter.pay.PayPresenter;
import com.homepaas.sls.mvp.view.order.BalancePayView;
import com.homepaas.sls.mvp.view.order.OrderActionView;
import com.homepaas.sls.mvp.view.order.OrderDetailView;
import com.homepaas.sls.ui.account.AccountDetailsActivity;
import com.homepaas.sls.ui.adapter.MorePPListAdapter;
import com.homepaas.sls.ui.bottomsheet.ConfirmCompletedBottomSheetDialogFragment;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.ui.widget.NewCommonDialog;
import com.homepaas.sls.ui.widget.OrderDetailTabAdapter;
import com.homepaas.sls.ui.widget.networkerrorview.SelectWorkerOrServiceNetWorkErrorViewReplacer;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.CountDownTimerUtils;
import com.homepaas.sls.util.PermissionUtils;
import com.homepaas.sls.util.StaticData;
import com.homepaas.sls.util.WindowUtil;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.runtimepermission.acp.AcpListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/7/12.
 * 订单详情
 */

public class ClientOrderDetailActivity extends CommonBaseActivity implements OrderDetailView, OrderActionView, CountDownTimerUtils.OnFinishListner, BalancePayView, BaseRecyclerAdapter.OnItemClickListener {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.more)
    TextView more;
    @Bind(R.id.title_rel)
    RelativeLayout titleRel;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.swipe_refresh)
    HeaderViewLayout swipeRefresh;
    @Bind(R.id.count_down_time)
    TextView countDownTime;
    @Bind(R.id.help)
    ImageView help;
    @Bind(R.id.count_down_rel)
    RelativeLayout countDownRel;
    @Bind(R.id.claims_status)
    TextView claimsStatus;
    @Bind(R.id.claims_button)
    TextView claimsButton;
    @Bind(R.id.delete_button)
    TextView deleteButton;
    @Bind(R.id.no_button_lin)
    LinearLayout noButtonLin;
    @Bind(R.id.ok_button)
    TextView okButton;
    @Bind(R.id.show_button_lin)
    LinearLayout showButtonLin;
    private PopupWindow popupWindow;
    private View contentView;
    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;

    private String orderId;
    private String serviceId;
    private String orderCode;
    private String mCallingPhone;
    private String mTitle;

    private List<String> titleList;
    private List<Fragment> fragmentList;
    private OrderDetailTabAdapter orderDetailTabAdapter;
    private OrderStatusFragment orderStatusFragment;
    private OrderInformationFragment orderInformationFragment;
    private CountDownTimerUtils countDownTimerUtils;
    private DetailOrderEntity orderInfo;
    private NewCommonDialog reminderDialog;
    private NewCommonDialog outOfNoticeDialog;
    private NewCommonDialog displayBackClaimsReasonDialog;
    //    private NewCommonDialog confirmDialog;
    private NewCommonDialog deleteDialog;
    private NewCommonDialog remindDialog;
    private MorePPListAdapter morePPListAdapter;
    @Inject
    OrderPresenter orderPresenter;
    @Inject
    PayPresenter payPresenter;
    private SelectWorkerOrServiceNetWorkErrorViewReplacer commonNetWorkErrorViewReplacer;


    public static void start(Context context, String orderId) {
        Intent intent = new Intent(context, ClientOrderDetailActivity.class);
        intent.putExtra(StaticData.ORDER_ID, orderId);
        context.startActivity(intent);
    }

    public static void startOrderDetailForResult(Activity context, String orderId) {
        Intent intent = new Intent(context, ClientOrderDetailActivity.class);
        intent.putExtra(StaticData.ORDER_ID, orderId);
        context.startActivityForResult(intent, StaticData.ORDER_DETAILS_FOR_RESULT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_client_order_detail;
    }

    @Override
    protected void initView() {
        orderId = getIntent().getStringExtra(StaticData.ORDER_ID);
        EventBus.getDefault().register(this);

        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titleList.add("状态");
        titleList.add("订单信息");
        orderStatusFragment = OrderStatusFragment.newInstance();
        orderInformationFragment = OrderInformationFragment.newInstance();
        fragmentList.add(orderStatusFragment);
        fragmentList.add(orderInformationFragment);
        orderDetailTabAdapter = new OrderDetailTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewpager.setOffscreenPageLimit(1);
        viewpager.setAdapter(orderDetailTabAdapter);
        tablayout.removeAllTabs();
        tablayout.setupWithViewPager(viewpager);
        swipeRefresh.setOnRefreshListener(mOnRefreshListener);

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
                if (ClientOrderDetailActivity.this != null) {
                    setBackgroundAlpha(1f);
                }
            }
        });

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClientOrderDetailActivity.this != null) {
                    popupWindow.dismiss();
                    setBackgroundAlpha(1f);
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(morePPListAdapter);


        orderPresenter.setOrderDetailView(this);
        orderPresenter.setOrderActionView(this);
        payPresenter.setBalancePayView(this);

        commonNetWorkErrorViewReplacer = new SelectWorkerOrServiceNetWorkErrorViewReplacer(mContext, viewpager, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                getData();
            }
        });

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            onUpdateList();
        }

        @Override
        public void onLoadMore() {
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };


    @Subscribe
    public void eventUpdateList(UpdateListEntity updateListEntity) {
        if (updateListEntity.isUpdateList())
            onUpdateList();
    }


    public void onUpdateList() {
        orderPresenter.getOrderDetail(orderId);
        orderPresenter.getTrackOrderStatus(orderId);
    }

    @OnClick({R.id.back, R.id.more, R.id.help, R.id.claims_button, R.id.delete_button, R.id.ok_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                String countDownTimeStr = countDownTime.getText().toString();
                int countDownVis = countDownRel.getVisibility();
                if (!TextUtils.isEmpty(countDownTimeStr) && !TextUtils.equals(countDownTimeStr, "00:00") && countDownVis == 0) {
                    showOutOfDialog();
                } else {
                    finish();
                }
                break;
            case R.id.more:
                String moreStr = more.getText().toString();
                if (!TextUtils.isEmpty(moreStr)) {
                    if (TextUtils.equals("更多", moreStr)) {
                        showMenu();
                    } else if (TextUtils.equals("客服", moreStr)) {
                        dial("4008-262-056", "联系客服");
                    }
                }
                break;
            case R.id.help:  //倒计时说明
                showReminderDialog();
                break;
            case R.id.claims_button:
                showClaims();
                break;
            case R.id.delete_button:
                showDeleteOrder();
                break;
            case R.id.ok_button:
                showConfirmOrder();
                break;
            default:
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

    // 拨打电话
    private void dial(final String phone, final String title) {
        PermissionUtils.callPhonePermission(this, new AcpListener() {
            @Override
            public void onGranted() {
                NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title);
                serviceFragment.show(getSupportFragmentManager(), null);
            }

            @Override
            public void onDenied(List<String> permissions) {
                showMessage("拨打电话需要权限,请到设置中心");
            }
        });
    }


    /**
     * 倒计时说明
     */
    private void showReminderDialog() {
        if (reminderDialog == null)
            reminderDialog = new NewCommonDialog.Builder()
                    .setTitle("温馨提示")
                    .setContent("由于资源紧张，本单需要立即付款以保证预约成功。请在下单20分钟内完成支付，否则订单将被取消")
                    .setContentGravity(Gravity.CENTER)
                    .showButton(false)
                    .setConfirmButton("我知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reminderDialog.dismissDialog();
                        }
                    })
                    .setConfirmTextColor(R.color.count_down_time)
                    .create();
        reminderDialog.show(getSupportFragmentManager(), null);
    }

    private void showOutOfDialog() {
        String contet = "还差一步，确定要退出吗？15:00内还可从订单列表继续支付，否则订单将自动取消哦~";
        long curSystemTime = System.currentTimeMillis();
        long countTime = 0;
        if (countDownTimerUtils != null)
            countTime = countDownTimerUtils.getCurMillisUntilFinished();
        if (countTime > 1000) {
            outOfNoticeDialog = new NewCommonDialog.Builder()
                    .showTitle(false)
                    .setContent(contet)
                    .setmCountDown(true)
                    .setContentGravity(Gravity.CENTER)
                    .setCountDown(countTime, 1000, curSystemTime)
                    .setKeyColor(Color.parseColor("#f56165"))
                    .setCancelTextColor(R.color.count_down_time)
                    .setCancelButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳到订单列表页面
                            onBackPressed();
                            outOfNoticeDialog.dismissDialog();

                        }
                    })
                    .setConfirmTextColor(R.color.count_down_time)
                    .setConfirmButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            outOfNoticeDialog.dismissDialog();
                        }
                    })
                    .create();
            outOfNoticeDialog.show(getSupportFragmentManager(), null);
        } else {
            //跳到订单列表页面
            onBackPressed();
        }
    }


    @Override
    public void showError(Throwable t) {
        super.showError(t);
        if (swipeRefresh != null) {
            swipeRefresh.stopRefresh();
        }
        if (orderInfo == null) {
            showButtonLin.setVisibility(View.GONE);
            commonNetWorkErrorViewReplacer.showErrorLayout();
        }
    }


    /**
     * 订单信息
     *
     * @param orderInfo
     * @param requestTime
     */
    @Override
    public void render(DetailOrderEntity orderInfo, long requestTime) {
        if (orderStatusFragment != null) {
            orderStatusFragment.serviceId(orderInfo.getService().getServiceId());
        }
        commonNetWorkErrorViewReplacer.showOriginalLayout();
        this.orderInfo = orderInfo;
        swipeRefresh.stopRefresh();
        if (countDownTimerUtils != null) {
            countDownTimerUtils.cancel();
        }
        if (orderInfo != null) {
            //倒计时显示
            if (!TextUtils.isEmpty(orderInfo.getResidualTime()) && Long.valueOf(orderInfo.getResidualTime()) > 0) {
                long millisInFuture = Long.valueOf(orderInfo.getResidualTime()).longValue();
                long offset = (long) Math.ceil(System.currentTimeMillis() / 1000 - requestTime / 1000);
                long countDownmillisInFuture = (long) Math.floor(millisInFuture - offset);
                if (countDownmillisInFuture >= 1) {
                    countDownRel.setVisibility(View.VISIBLE);
                    countDownTimerUtils = new CountDownTimerUtils(countDownmillisInFuture * 1000, 1000, countDownTime);
                    countDownTimerUtils.setOnFinishListner(this);
                    countDownTimerUtils.start();
                } else {
                    countDownRel.setVisibility(View.GONE);
                    swipeRefresh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            orderPresenter.getOrderDetail(orderId);
                        }
                    }, 1000);
                }
            } else {
                countDownRel.setVisibility(View.GONE);
            }
            orderCode = orderInfo.getOrderCode();
            if (orderInfo.getService() != null) {
                serviceId = orderInfo.getService().getServiceId();
            }
            //显示右侧菜单按钮
            initRightMenu(orderInfo.getOrderBtnInfo());
            //显示底部按钮
            showButton(orderInfo);
            //传递数据给orderInformationFragment
            if (orderInformationFragment != null) {
                orderInformationFragment.setOrderInfomation(orderInfo);
            }
        }
    }

    private void initRightMenu(OrderBtnInfo orderBtnInfo) {
        //取消订单、删除订单、投诉[0.5] 该三种情况下显示更多 ，其他情况显示客服
        if (orderInfo.getOrderBtnInfo() == null) {
            more.setText("客服");
            return;
        }
        //是否显示“取消订单”按钮
        String isDisplayCancelOrderBtn = orderInfo.getOrderBtnInfo().getIsDisplayCancelOrderBtn();
        //是否显示“删除订单”按钮
        String isDisplayDeleteOrderBtn1 = orderInfo.getOrderBtnInfo().getIsDisplayDeleteOrderBtn();
        //是否显示“投诉”按钮
        String isDisplayComplaintsBtn1 = orderInfo.getOrderBtnInfo().getIsDisplayComplaintsBtn();
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

    /**
     * 订单状态信息
     *
     * @param orderInfo
     */
    @Override
    public void renderTrackOrderStatus(TrackOrderInfo orderInfo) {
        if (isFinishing())
            return;
        commonNetWorkErrorViewReplacer.showOriginalLayout();
        swipeRefresh.stopRefresh();
        if (orderStatusFragment != null) {
            orderStatusFragment.setTrackOrderStatus(orderInfo);
        }
    }

    /**
     * 显示底部按钮
     */
    private void showButton(DetailOrderEntity order) {
        ClaimsInfo claimsInfo = order.getClaimsInfo();
        if (claimsInfo != null && claimsInfo.getClaimsManagementInfo() != null && !TextUtils.isEmpty(claimsInfo.getClaimsManagementInfo().getAuditStatus())) {
            claimsStatus.setVisibility(View.VISIBLE);
            claimsStatus.setText(claimsInfo.getClaimsManagementInfo().getAuditStatus());
        } else {
            claimsStatus.setVisibility(View.GONE);
        }
        claimsButton.setVisibility(View.GONE);
        if (claimsInfo != null && TextUtils.equals("1", claimsInfo.getIsDisplayApplyClaimsBtn())) {
            claimsButton.setVisibility(View.VISIBLE);
            claimsButton.setText(R.string.client_insurance_claims);
        }
        if (claimsInfo != null && TextUtils.equals("1", claimsInfo.getIsDisplayShowClaimsBtn())) {
            claimsButton.setVisibility(View.VISIBLE);
            claimsButton.setText(R.string.client_view_the_payment);
        }
        if (claimsInfo != null && TextUtils.equals("1", claimsInfo.getIsDisplayBackClaimsReasonBtn())) {
            claimsButton.setVisibility(View.VISIBLE);
            claimsButton.setText(R.string.client_view_the_reason);
        }
        deleteButton.setVisibility(View.GONE);
        if (order.getOrderBtnInfo() != null && TextUtils.equals("1", order.getOrderBtnInfo().getIsDisplayDeleteOrderBtn())) {//删除订单按钮，不显示底部
//            deleteButton.setText(R.string.client_delete_order);
//            deleteButton.setVisibility(View.VISIBLE);
            showButtonLin.setVisibility(View.GONE);
        }
        if (claimsButton.getVisibility() == View.GONE && deleteButton.getVisibility() == View.GONE) {
            noButtonLin.setVisibility(View.GONE);
        } else {
            noButtonLin.setVisibility(View.VISIBLE);
        }
        okButton.setVisibility(View.GONE);
        if (order.getOrderBtnInfo() != null && TextUtils.equals("1", order.getOrderBtnInfo().getIsDisplayGotoPayBtn())) {
            okButton.setVisibility(View.VISIBLE);
            okButton.setText(R.string.client_to_pay_order);
        }
        //增加服务
        if (order.getOrderBtnInfo() != null && TextUtils.equals("1", order.getOrderBtnInfo().getIsDisplayAddServiceBtn())) {
            okButton.setVisibility(View.VISIBLE);
            okButton.setText(R.string.client_add_service);
        }
//        投诉
//        if (order.getOrderBtnInfo() != null && TextUtils.equals("1", order.getOrderBtnInfo().getIsDisplayComplaintsBtn())) {
//            noButtonLin.setVisibility(View.VISIBLE);
//            deleteButton.setVisibility(View.VISIBLE);
//            deleteButton.setText(R.string.complanint1);
//        }
        if (order.getOrderBtnInfo() != null && TextUtils.equals("1", order.getOrderBtnInfo().getIsDisplayClientConfirmBtn())) {
            okButton.setVisibility(View.VISIBLE);
            okButton.setText(R.string.client_confirm_order);
        }
        if (order.getOrderBtnInfo() != null && TextUtils.equals("1", order.getOrderBtnInfo().getIsDisplayGotoEvaluateBtn())) {
            okButton.setVisibility(View.VISIBLE);
            okButton.setText(R.string.client_to_evaluate_order);
        }
        if (claimsStatus.getVisibility() == View.GONE && noButtonLin.getVisibility() == View.GONE && okButton.getVisibility() == View.GONE) {
            showButtonLin.setVisibility(View.GONE);
        } else {
            showButtonLin.setVisibility(View.VISIBLE);
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

    /**
     * 保险赔付
     */
    private void displayApplyClaims() {
        if (orderInfo != null) {
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

    /**
     * 删除订单 不知道以后会不会加其他状态的按钮，所以这样写
     */

    private void showDeleteOrder() {
//        if (orderInfo.getOrderBtnInfo() != null && TextUtils.equals("1", orderInfo.getOrderBtnInfo().getIsDisplayComplaintsBtn())) {
        //投诉
//            mNavigator.complaint(this, orderInfo.getOrderId());
//            return;
//        }
        String deleteText = deleteButton.getText().toString();
        if (TextUtils.equals(getResources().getString(R.string.client_delete_order), deleteText)) {
            if (deleteDialog == null) {
                deleteDialog = new NewCommonDialog.Builder().setTitle("删除订单")
                        .setContentGravity(Gravity.CENTER)
                        .setContent("确定删除订单吗？删除后不可恢复哦？")
                        .setCancelButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteDialog.dismiss();
                            }
                        })
                        .setConfirmButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                orderPresenter.deleteOrder(orderId, -1);
                            }
                        }).create();
            }
            deleteDialog.show(getSupportFragmentManager(), null);
        }
    }

    /**
     * 余额支付回调
     *
     * @param errcode ..
     */
    @Override
    public void onBalancePayResult(String errcode) {
        boolean success = TextUtils.equals("0", errcode);
        showMessage(success ? "支付成功" : "支付异常");
        if (success) {
            //延时刷新数据,让用户自己刷新数据

        }
    }

    public void getData() {
        orderPresenter.getOrderDetail(orderId);
        orderPresenter.getTrackOrderStatus(orderId);
    }

    @Subscribe
    private void showConfirmOrder() {
        if (orderInfo == null || orderInfo.getOrderBtnInfo() == null)
            return;
        if (TextUtils.equals("1", orderInfo.getOrderBtnInfo().getIsDisplayGotoPayBtn())) { //去支付
            //去支付
            if (Double.parseDouble(orderInfo.getPayAmount()) <= 0) { //直接调用支付接口
                //订单总价
                String totalPrice = orderInfo.getTotalPrice();
                //调用支付接口  totalPrice
                payPresenter.payByBalance(orderId, "", totalPrice);
            } else {
                //进入支付界面
                Intent intent = new Intent(this, PayActivity.class);
                intent.putExtra(Constant.OrderId, orderId);//传递
                startActivity(intent);
            }
        } else if (TextUtils.equals("1", orderInfo.getOrderBtnInfo().getIsDisplayAddServiceBtn())) {//增加服务
            mNavigator.addServiceNum(this, orderInfo.getOrderId(), serviceId);
        } else if (TextUtils.equals("1", orderInfo.getOrderBtnInfo().getIsDisplayClientConfirmBtn())) {//确认完成

            StringBuffer sb = new StringBuffer();
            sb.append(TimeUtils.formatOrderTime(orderInfo.getService().getServiceStartAt()));
            sb.append("的");
            sb.append(orderInfo.getService().getServiceName());
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
        } else if (TextUtils.equals("1", orderInfo.getOrderBtnInfo().getIsDisplayGotoEvaluateBtn())) {//去评价

            StringBuffer serviceDetails = new StringBuffer();
            serviceDetails.append("(");
            serviceDetails.append(TimeUtils.formatOrderTime(orderInfo.getService().getServiceStartAt()));
            serviceDetails.append("的");
            serviceDetails.append(orderInfo.getService().getServiceName());
            serviceDetails.append("服务");
            serviceDetails.append(")");
            EvaluationOrderActivity.start(this, serviceId, orderCode, serviceDetails.toString());
            overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);
        }
    }

//    //确认完成接口调用
//    @Subscribe
//    public void confrimCompleted(ConfirmCompletedEvent completedEvent) {
//        if (completedEvent.getFlag().equals(ConfirmCompletedEvent.ORDER_DETAIL)) {
//            orderPresenter.confirmOrder(completedEvent);
//        }
//    }

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


    @Override
    public void onFinish(boolean isFinish) {
        if (isFinish) {//计时结束，刷新页面，订单状态应该变为取消状态
            countDownRel.setVisibility(View.GONE);
            //延迟一秒去请求数据，防止后台还没修改完状态，获取还是之前的状态
            swipeRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    orderPresenter.getOrderDetail(orderId);
                }
            }, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        if (swipeRefresh != null) {
            swipeRefresh.stopRefresh();
            swipeRefresh.destory();
        }
        super.onDestroy();
        if (countDownTimerUtils != null) {
            countDownTimerUtils.cancel();
            countDownTimerUtils = null;
        }
        if (orderPresenter != null)
            orderPresenter.destroy();
        if (payPresenter != null)
            payPresenter.destroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onOrderCancel(String msg, int position) {

    }

    @Override
    public void onOrderDelete(String msg, int position) {
        this.finish();
    }

    @Override
    public void onOrderConfirm(String orderId, int position) {
        StringBuffer serviceDetails = new StringBuffer();
        serviceDetails.append("(");
        serviceDetails.append(TimeUtils.formatOrderTime(orderInfo.getService().getServiceStartAt()));
        serviceDetails.append("的");
        serviceDetails.append(orderInfo.getService().getServiceName());
        serviceDetails.append("服务");
        serviceDetails.append(")");
        EvaluationOrderActivity.start(this, serviceId, orderCode, serviceDetails.toString());
        overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);
    }


    @Override
    public void onItemClick(View itemView, int pos) {
        MorePPEntity morePPEntity = morePPListAdapter.getData().get(pos);
        //取消订单
        if (morePPEntity.getFlag().equals(MorePPEntity.CANCEL_ORDER)) {
            if (orderInfo != null && orderInfo.getOrderBtnInfo() != null && TextUtils.equals("0", orderInfo.getOrderBtnInfo().getIsGotoCancelPage())) {
                remindOrder();//客户支付后点击取消提示
            } else {
                //客户未支付点击取消
                OrderCancelReasonsActivity.start(this, orderId);
            }
        } else if (morePPEntity.getFlag().equals(MorePPEntity.DELETE_ORDER)) {//删除订单
            orderPresenter.deleteOrder(orderId, 0);
        } else if (morePPEntity.getFlag().equals(MorePPEntity.COMPL_AINT))//投诉
        {
            mNavigator.complaint(ClientOrderDetailActivity.this, orderId);
        }
        if (ClientOrderDetailActivity.this != null) {
            popupWindow.dismiss();
            setBackgroundAlpha(1f);
        }
    }
}
