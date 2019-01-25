package com.homepaas.sls.ui.order.manage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMainComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.entity.ClaimsInfo;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.entity.embedded_class.Service;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.event.RefreshEvent;
import com.homepaas.sls.event.SuccessPayEvent;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.mvp.presenter.pay.PayPresenter;
import com.homepaas.sls.mvp.view.order.BalancePayView;
import com.homepaas.sls.mvp.view.order.GetOrderView;
import com.homepaas.sls.mvp.view.order.OrderActionView;
import com.homepaas.sls.ui.comment.AddCommentActivity;
import com.homepaas.sls.ui.common.fragment.BaseListFragment;
import com.homepaas.sls.ui.order.directOrder.ApplyCompensationActivity;
import com.homepaas.sls.ui.order.directOrder.ClientOrderDetailActivity;
import com.homepaas.sls.ui.order.directOrder.ErrandServiceDetailActivity;
import com.homepaas.sls.ui.order.directOrder.EvaluationOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ExpressOrderTrackingActivity;
import com.homepaas.sls.ui.order.directOrder.OrderCancelReasonsActivity;
import com.homepaas.sls.ui.order.directOrder.adapter.ClientOrderListAdapter;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.ui.widget.NewCommonDialog;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

public class OrderToTakeFragment extends BaseListFragment<OrderEntity> implements GetOrderView, OrderActionView, HeaderViewLayout.OnRefreshListener, ClientOrderListAdapter.HostAction, BalancePayView {


    private String mCallingPhone;
    private String mTitle;
    private boolean isCreated = false;
    private boolean isShow = false;
    private boolean isLoaded = false;
    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;
    private static final int REQUEST_LOGIN = 1;

    @Inject
    PayPresenter payPresenter;
    @Inject
    OrderPresenter presenter;
    private NewCommonDialog remindDialog;
    private ClientOrderListAdapter adapter;

    private void load() {
        if (isCreated && getUserVisibleHint() && !isLoaded) {
            getData(true);
            isLoaded = true;
            isCreated = false;
        }
    }

    public static OrderToTakeFragment newInstance() {
        OrderToTakeFragment fragment = new OrderToTakeFragment();
        return fragment;
    }

    public OrderToTakeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        refreshLayout.setOnRefreshListener(this);
//        recyclerView.addItemDecoration(getItemDecoration());

        presenter.setGetOrderView(this);
        presenter.setOrderActionView(this);
        payPresenter.setBalancePayView(this);
        EventBus.getDefault().register(this);
//        isCreated = true;
//        presenter.getToTakeOrder();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        load();
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<OrderEntity> list) {
        adapter = new ClientOrderListAdapter();
        adapter.setDatas(list);
        adapter.setHostAction(this);
        return adapter;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setMoreLoadable(true);
        setEmptyView(R.mipmap.order, "当前没有订单，赶快去看看需要什么服务吧");
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        load();
        if (getUserVisibleHint()) {
            onRefresh();
//            presenter.getToTakeOrder();
            isLoaded = true;
            isShow = true;
        } else {
            isShow = false;
        }
    }

    @Subscribe
    public void onEvent(EventPersonalInfo event) {
//        if (event.changeType == EventPersonalInfo.LOGIN_STATE) {
//            search();
//        }
        if (event.isLogin())
            getData(true);
    }

    public void getData(boolean isShowDialog) {
        if (PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE)) {//用户未登录显示空布局提示
            presenter.getOrderList(StaticData.ORDER_LIST_TOTAKE, isShowDialog);
        } else {
            render(null);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_LOGIN:
                    if (data != null && data.getBooleanExtra("Status", false))
                        getData(true);
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void addService(String serviceId, String orderCode) {
        //增加服务数量
        mNavigator.addServiceNum(mContext, orderCode, serviceId);
    }

    @Override
    public void startInsuranceCompensation(OrderEntity orderInfo, String id) {
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
            ApplyCompensationActivity.start(getActivity(), orderInfo.getOrderId(), serivceTime, claimsReasons);
        }
    }

    @Override
    public void showError(Throwable e) {
        if (adapter == null || adapter.getDatas() == null || adapter.getDatas().size() == 0)//读取缓存数据
        {
            OrderInfo orderInfo = PreferencesUtil.getObject(StaticData.WAITING_LIST, OrderInfo.class);
            if (orderInfo != null)
                render(orderInfo.getOrders());
        }
        super.showError(e);
    }

    @Override
    public void render(@Nullable List<OrderEntity> list) {
        super.render(list);
    }

    @Override
    protected void initializeInjector() {
        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build()
                .inject(this);
    }

    /**
     * 订单取消后的回调
     *
     * @param msg
     */
    @Override
    public void onOrderCancel(String msg, int position) {
        showMessage(msg);
        adapter.getDatas().get(position).setOrderStatus(com.homepaas.sls.data.repository.Constant.ORDER_STATUS_CANCELED);
        adapter.notifyItemChanged(position);
    }

    /**
     * 订单删除后的回调
     *
     * @param msg
     * @param position
     */
    @Override
    public void onOrderDelete(String msg, int position) {
        showMessage("订单删除成功");
        adapter.getDatas().remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void networkErrorRefresh() {
        super.networkErrorRefresh();
        getData(true);
    }

    /**
     * 订单确认完成后的回调
     *
     * @param orderId
     * @param position
     */
    @Override
    public void onOrderConfirm(final String orderId, int position) {
        showMessage("订单确认");
        //跳转到评价页面
        new CommonDialog.Builder()
                .setContent("确认订单完成")
                .setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideLoading();
                    }
                }).showTitle(false).setConfirmButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommentActivity.start(getActivity(), orderId, null, null);
                getActivity().finish();
            }
        }).create().show(getChildFragmentManager(), null);

        getData(true);
    }

    /**
     * 刷新认为是重新获取数据
     */
    @Override
    public void onRefresh() {
        getData(false);
    }

    @Override
    public void onLoadMore() {
        presenter.getMoreOrderList(StaticData.ORDER_LIST_TOTAKE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPage(RefreshEvent e) {
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessPayRefresh(SuccessPayEvent event) {
        getData(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void startRemind() {
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
        remindDialog.show(getFragmentManager(), null);
    }

    @Override
    public void startCancel(int pos, String id) {
        Intent intent = new Intent(getActivity(), OrderCancelReasonsActivity.class);
        intent.putExtra(StaticData.SERVICE_ID, id);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);
//        new CommonDialog.Builder()
//                .setTitle("取消订单")
//                .setContent("确定取消订单吗？")
//                .setCancelButton("先不取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        hideLoading();
//                    }
//                }).showTitle(true).setConfirmButton("确定", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.cancelOrder(id,pos);
//            }
//        }).create().show(getChildFragmentManager(), null);
    }

    @Override
    public void startComment(String serviceId, String orderCode, String serviceDetails) {
        EvaluationOrderActivity.start(getActivity(), serviceId, orderCode, serviceDetails);
        getActivity().overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);
    }


    @Override
    public void startDelete(int pos, String id) {
        presenter.deleteOrder(id, pos);
    }

    private String payAmount;
    private String currentOrderID;
    private String totalPrice;

    @Override
    public void startPay(int pos, String id) {
        currentOrderID = id;
        OrderEntity orderEntity = adapter.getDatas().get(pos);
        //支付金额
        payAmount = orderEntity.getPayAmount();

        //去支付
        //工人报价金额和定金金额一致时 实际支付时为0元，那就直接调用支付接口，成功后支付成功界面
        if (orderEntity.getOrderBtnInfo() != null && TextUtils.equals("1", orderEntity.getOrderBtnInfo().getIsDisplayGotoPayBtn()) && Double.parseDouble(payAmount) <= 0) {
            //订单总价
            totalPrice = orderEntity.getTotalPrice();
            //调用支付接口  totalPrice
            payPresenter.payByBalance(currentOrderID, "", totalPrice);
        } else {
            Intent intent = new Intent(getActivity(), PayActivity.class);
            intent.putExtra("TotalMoney", payAmount);
            intent.putExtra("ServiceProviderName", orderEntity.getProviderName());
            intent.putExtra(com.homepaas.sls.ui.order.Constant.OrderId, currentOrderID);
            startActivity(intent);
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
//            PaySuccessInfoActivity.start(getActivity(), currentOrderID, payAmount, "", "", "", "");
            //进入订单详情界面
            ClientOrderDetailActivity.start(getActivity(), currentOrderID);
        }
    }

    /**
     * 查看订单详情
     *
     * @param orderId
     */
    @Override
    public void startViewDetail(String orderId, String isKdEOrder) {
        if (!TextUtils.isEmpty(isKdEOrder) && TextUtils.equals(isKdEOrder, "1")) {
            ExpressOrderTrackingActivity.start(getActivity(), orderId);
        } else if (!TextUtils.isEmpty(isKdEOrder) && TextUtils.equals(isKdEOrder, "2")) {
            ErrandServiceDetailActivity.start(getActivity(), orderId);
        } else {
            ClientOrderDetailActivity.start(getActivity(), orderId);
        }
    }

    @Override
    public void startConfirm(Service service, final String orderId, String orderCode) {
//        StringBuffer sb = new StringBuffer();
//        sb.append(TimeUtils.formatOrderTime(service.getServiceStartAt()));
//        sb.append("的");
//        sb.append(service.getServiceName());
//        sb.append("服务");
//
//        //确认完成弹框
//        ConfirmCompletedBottomSheetDialogFragment fragment = ConfirmCompletedBottomSheetDialogFragment.newInstance(sb.toString(), orderId);
//        fragment.show(getActivity().getSupportFragmentManager(), ConfirmCompletedBottomSheetDialogFragment.class.getSimpleName());

    }

    @Override
    public void startSeeReason(String reason) {

    }

    @Override
    public void startSeeCompensation(String id) {

    }


    // 拨打电话
    private void dial(String phone, String title) {
        boolean ret = requestRuntimePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG}, REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        if (ret) {
            NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title);
            serviceFragment.show(getFragmentManager(), null);
        } else {
            mCallingPhone = phone;
            mTitle = title;
            requestRuntimePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG}, REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        }
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


}
