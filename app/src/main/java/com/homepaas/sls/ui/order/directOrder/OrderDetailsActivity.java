package com.homepaas.sls.ui.order.directOrder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerOrderComponent;
import com.homepaas.sls.di.module.OrderInfoModule;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.ClaimsInfo;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.domain.entity.embedded_class.Service;
import com.homepaas.sls.event.BrowsePhotoEvent;
import com.homepaas.sls.event.ConfirmCompletedEvent;
import com.homepaas.sls.event.RefreshEvent;
import com.homepaas.sls.mvp.presenter.DetailPresenter;
import com.homepaas.sls.mvp.presenter.ServicePresenter;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.mvp.view.AvatarView;
import com.homepaas.sls.mvp.view.CallView;
import com.homepaas.sls.mvp.view.order.ActivityView;
import com.homepaas.sls.mvp.view.order.OrderActionView;
import com.homepaas.sls.mvp.view.order.OrderDetailView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.account.AccountDetailsActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.detail.WorkerDetailActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.directOrder.adapter.DetailAdapter;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.FilletCommonDialog;
import com.homepaas.sls.ui.widget.NewCommonDialog;
import com.homepaas.sls.ui.widget.ParallelLinesDecoration;
import com.homepaas.sls.ui.widget.PhotoPreviewDialog;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.CountDownTimerUtils;
import com.homepaas.sls.util.PermissionUtil;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.homepaas.sls.data.repository.Constant.ORDER_STATUS_CANCELED;
import static com.homepaas.sls.data.repository.Constant.ORDER_STATUS_CONFIRMED;
import static com.homepaas.sls.data.repository.Constant.ORDER_STATUS_TAKEN;
import static com.homepaas.sls.data.repository.Constant.ORDER_STATUS_TOASSIGN;
import static com.homepaas.sls.data.repository.Constant.ORDER_STATUS_TOTAKE;
import static com.homepaas.sls.data.repository.Constant.ORDER_STATUS_WORKER_COMPLETE;
import static com.umeng.socialize.utils.DeviceConfig.context;

public class OrderDetailsActivity extends CommonBaseActivity implements OrderDetailView, DetailAdapter.DetailOrderHostAction, OrderActionView, ActivityView, AvatarView, CallView, CountDownTimerUtils.OnFinishListner {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Inject
    OrderPresenter orderPresenter;
    @Inject
    ServicePresenter servicePresenter;
    @Inject
    DetailPresenter detailPresenter;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @Bind(R.id.count_down_time)
    TextView countDownTime;
    @Bind(R.id.help)
    ImageView help;
    @Bind(R.id.count_down)
    RelativeLayout countDown;
    @Bind((R.id.detail_order))
    Button detailOrder;
    @Bind(R.id.root)
    RelativeLayout root;
    private DetailAdapter adapter;
    private String orderId;
    private String serviceType;
    private String confirmGo;
    private CommonDialog confirmDialog;
    private CommonDialog dialog;
    private PhotoPreviewDialog previewDialog;
    private DetailOrderEntity orderInfo;
    private CountDownTimerUtils countDownTimerUtils;
    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 1;
    private static final int FLASH_DATA=499;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismissAllowingStateLoss();
        }
    };
    View.OnClickListener dialCallBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mNavigator.dial(OrderDetailsActivity.this, getString(R.string.service_phone_number));
        }
    };

    View.OnClickListener cancelCallBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            orderPresenter.cancelOrder(orderId, -1, "");
        }
    };
    private NewCommonDialog reminderDialog;

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

    @OnClick(R.id.help)
    public void showHelp() {
        showReminderDialog();
    }

    @OnClick(R.id.detail_order)
    void detailOrder() {
        startCancel(-1, orderId);
        detailOrder.setVisibility(View.GONE);
    }

    /**
     * @param context
     * @param orderId
     */


    public static void start(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra(Constant.OrderId, orderId);
        context.startActivity(intent);
    }


    public static void start(Context context, String orderId, String confirmGo) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra(Constant.OrderId, orderId);
        intent.putExtra(StaticData.ConfirmGO, confirmGo);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        setSupportActionBar(toolbar);
        initialize();
        servicePresenter.setAvatarView(this);
        orderPresenter.setOrderDetailView(this);
        orderPresenter.setOrderActionView(this);
        detailPresenter.setCallView(this);
//        orderPresenter.getOrderDetail(orderId);
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenu() != -1)
            getMenuInflater().inflate(getMenu(), menu);

        return super.onCreateOptionsMenu(menu);
    }

    @MenuRes
    public int getMenu() {
        if (orderInfo == null)
            return R.menu.order_detail_menu;
        String status = orderInfo.getOrderStatus();
        switch (status) {
            case ORDER_STATUS_TOASSIGN:
            case ORDER_STATUS_TOTAKE:
            case ORDER_STATUS_TAKEN:
                return R.menu.order_detail_menu;
            case ORDER_STATUS_CANCELED:
                return R.menu.order_detail_menu_canceled;
            case ORDER_STATUS_WORKER_COMPLETE:
            case ORDER_STATUS_CONFIRMED:
            default:
                return -1;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //取消订单
//            case R.id.menu_item_cancel:
//                startCancel(-1, orderId);
//                return true;
            case R.id.more:
                detailOrder.setVisibility(View.VISIBLE);
                return true;
            //联系客服
            case R.id.menu_item_hotline:
                contactHotLine();
                return true;
            case android.R.id.home:
                String countDownTimeStr = countDownTime.getText().toString();
                int countDownVis=countDown.getVisibility();
                if (!TextUtils.isEmpty(countDownTimeStr) && !TextUtils.equals(countDownTimeStr, "00:00")&&countDownVis==0) {
                    showOutOfDialog();
                } else {
                    onBackPressed();
                }
//                if (!TextUtils.isEmpty(confirmGo) && TextUtils.equals(confirmGo, "1")) {
//                    onBackPressed();
//                } else {
//                    Intent intent = new Intent(this, MainActivity.class);
//                    intent.putExtra(StaticData.RETURN_MAP_FRAGMENT, "0");
//                    startActivity(intent);
//                    ActivityCompat.finishAfterTransition(this);
//                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private NewCommonDialog outOfNoticeDialog;
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



    //联系客服
    private void contactHotLine() {
        dialog = new CommonDialog.Builder()
                .showTitle(false)
                .setContent("是否电话联系客服" + getString(R.string.service_phone_number))
                .setConfirmButton("联系客服", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNavigator.dial(OrderDetailsActivity.this, getString(R.string.service_phone_number));
                    }
                }).setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderPresenter.getOrderDetail(orderId);
    }

    private void initialize() {
        orderId = getIntent().getStringExtra(Constant.OrderId);
        confirmGo = getIntent().getStringExtra(StaticData.ConfirmGO);
        serviceType = getIntent().getStringExtra(Constant.ServiceType);
        adapter = new DetailAdapter(context);
        adapter.setHostAction(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new ParallelLinesDecoration(this));
        recyclerView.setAdapter(adapter);
        refreshLayout.setCanLoadMore(false);
        refreshLayout.setOnRefreshListener(new HeaderViewLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderPresenter.getOrderDetail(orderId);
            }

            @Override
            public void onLoadMore() {

            }

            @Override
            public void onModeChanged(@HeaderViewLayout.Mode int mode) {

            }
        });
    }

    @Override
    protected void initializeInjector() {
        DaggerOrderComponent.builder()
                .orderInfoModule(new OrderInfoModule(this))
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public void render(DetailOrderEntity order, long requestTime) {
        if(countDownTimerUtils!=null){
            countDownTimerUtils.cancel();
//            countDownTimerUtils=null;
        }
        if(order!=null) {
            if (!TextUtils.isEmpty(order.getResidualTime()) && Long.valueOf(order.getResidualTime()) > 0) {
                long millisInFuture = Long.valueOf(order.getResidualTime()).longValue();
                long offset = (long) Math.ceil(System.currentTimeMillis() / 1000 - requestTime / 1000);
                long countDownmillisInFuture = (long) Math.floor(millisInFuture - offset);
                if (countDownmillisInFuture >= 1) {
                    countDown.setVisibility(View.VISIBLE);
                    countDownTimerUtils = new CountDownTimerUtils(countDownmillisInFuture * 1000, 1000, countDownTime);
                    countDownTimerUtils.setOnFinishListner(this);
                    countDownTimerUtils.start();
                } else {
                    countDown.setVisibility(View.GONE);
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            orderPresenter.getOrderDetail(orderId);
                        }
                    }, 1000);
                }
            } else {
                countDown.setVisibility(View.GONE);
            }
            orderInfo = order;
            invalidateOptionsMenu();
//            if (order.getServiceProvideType().equals("2")) {
//                servicePresenter.getWorkerAvatar(order.getServiceProviderId());
//            } else {
//                servicePresenter.getBusinessAvatar(order.getServiceProviderId());
//            }
            refreshLayout.stopRefresh();
            adapter.setDatas(order);
        }
    }

    @Override
    public void renderTrackOrderStatus(TrackOrderInfo orderInfo) {

    }

    @Override
    protected void retrieveData() {
        orderPresenter.getOrderDetail(orderId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void startDelete(final int pos, final String orderId) {
        orderPresenter.deleteOrder(orderId, pos);
    }

    @Override
    public void startComment(int pos, String orderId) {
        mNavigator.addComment(this, orderId, null, null);
    }

    @Override
    public void startPay(int pos, String orderId) {
        Intent intent = new Intent(this, PayActivity.class);
        intent.putExtra(Constant.OrderId, orderId);//传递
        startActivity(intent);
    }

    @Override
    public void startCancel(int pos, String orderId) {
        Intent intent = new Intent(this, OrderCancelReasonsActivity.class);
        intent.putExtra(StaticData.SERVICE_ID, orderId);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);
//        String[] arr = getCancelContent();
//        CommonDialog.Builder builder = new CommonDialog.Builder()
//                .showTitle(false)
//                .setContent(arr[0])
//                .setCancelButton(arr[1], listener);
//        if (TextUtils.equals("确定", arr[2]))
//            builder.setConfirmButton(arr[2], cancelCallBack);
//        else
//            builder.setConfirmButton(arr[2], dialCallBack);
//        dialog = builder.create();
//        dialog.show(getSupportFragmentManager(), null);
    }

    @NonNull
    private String[] getCancelContent() {
        String[] desArr = new String[3];
        desArr[0] = "确定取消订单吗？";
        desArr[1] = "先不取消";
        desArr[2] = "确定";
        if (TextUtils.equals(orderInfo.getIsPayOff(), "1")) {
            desArr[0] = "您已付款，联系客服取消订单/客服电话：4008-262-056";
            desArr[2] = "联系客服";
        } else if (TextUtils.equals(orderInfo.getOrderStatus(), ORDER_STATUS_TAKEN)) {
            desArr[0] = "工人已接单，联系客服取消订单/客服电话：4008-262-056";
            desArr[2] = "联系客服";
        }
        return desArr;
    }

    @Override
    public void startConfirm(int pos, final String orderId) {
        if (confirmDialog == null) {
            confirmDialog = new CommonDialog.Builder()
                    .setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmDialog.dismiss();
                        }
                    }).showTitle(false).setContent("确认服务完成？")
                    .setConfirmButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderPresenter.confirmOrder(new ConfirmCompletedEvent("",orderId,orderId));
                        }
                    }).create();
        }
        confirmDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void startViewProviderInfo(String providerId) {
        Intent intent = new Intent(this, WorkerDetailActivity.class);
        intent.putExtra(Navigator.WORKER_ID, providerId);
        startActivity(intent);
    }

    @Override
    public void startDial(String providerId, String serviceProviderPhone) {
        detailPresenter.attemptCall(providerId, serviceProviderPhone);
    }

    @Override
    public void startInsuranceCompensation(DetailOrderEntity orderEntity) {
        if(orderEntity!=null){
            String serivceTime="";
            Service service=orderEntity.getService();
            if(service!=null){
                serivceTime=service.getServiceStartAt();
            }
            List<ClaimsInfo.ClaimsManagementInfo.ClaimsManagementTypeInfo> claimsReasons=null;
            if(orderEntity.getClaimsInfo()!=null&&orderEntity.getClaimsInfo().getClaimsManagementInfo()!=null&&orderEntity.getClaimsInfo().getClaimsManagementInfo().getClaimsManagementTypeInfo()!=null){
                claimsReasons=orderEntity.getClaimsInfo().getClaimsManagementInfo().getClaimsManagementTypeInfo();
            }
            ApplyCompensationActivity.start(this,orderId,serivceTime,claimsReasons);
        }

    }

    private FilletCommonDialog filletCommonDialog;
    @Override
    public void startSeeReason(String reason) {
        filletCommonDialog=new FilletCommonDialog.Builder()
                .setTitle("原因")
                .setContent(reason)
                .showButton(false)
                .setConfirmButton("我知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filletCommonDialog.dismiss();
                    }
                }).create();
        filletCommonDialog.show(getSupportFragmentManager(),null);
    }

    @Override
    public void startSeeCompensation(String id) {
        if(!TextUtils.isEmpty(id)) {
            AccountDetailsActivity.start(this, Integer.parseInt(id));
        }
    }

    @Override
    public void onOrderCancel(String msg, int position) {
        showMessage(msg);
//        mNavigator.myOrderList(this);
        orderPresenter.getOrderDetail(orderId);//取消成功刷新此页面
        EventBus.getDefault().post(new RefreshEvent());
    }

    @Override
    public void onOrderDelete(String msg, int position) {
//        mNavigator.myOrderList(this);
        finish();
    }

    @Override
    public void onOrderConfirm(final String orId, int position) {
//        if (confirmDialog == null) {
//            confirmDialog = new CommonDialog.Builder()
//                    .setCancelButton("取消", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            confirmDialog.dismiss();
//                        }
//                    }).showTitle(false).setContent("确认订单完成？")
//                    .setConfirmButton("确认", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //跳转到评价页面
//                            mNavigator.addComment(getContext(), orderId, null, null);
//                        }
//                    }).create();
//        }
//        confirmDialog.show(getSupportFragmentManager(), null);
        showMessage("订单已完成");
        orderPresenter.getOrderDetail(orderId);
        EventBus.getDefault().post(new RefreshEvent());
    }

    @Override
    public void renderAvatar(AvatarsEntity avatarInfo) {
//        if (avatarInfo != null && !avatarInfo.getAvatars().isEmpty() && !TextUtils.isEmpty(avatarInfo.getAvatars().get(0)))
//            adapter.notifyAvatar(avatarInfo.getAvatars().get(0));
    }

    @Override
    public void renderActivity(ActivityNgInfoNew actionEntity) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startViewPhoto(BrowsePhotoEvent event) {
        if (previewDialog == null)
            previewDialog = new PhotoPreviewDialog.Builder()
                    .index(event.index)
                    .path(event.filePaths)
                    .build();
        previewDialog.setIndex(event.index);
        previewDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    protected void onDestroy() {
        if (refreshLayout != null) {
            refreshLayout.stopRefresh();
            refreshLayout.destory();
        }
        EventBus.getDefault().unregister(this);
        if (countDownTimerUtils != null) {
            countDownTimerUtils.cancel();
            countDownTimerUtils = null;
        }
        super.onDestroy();
    }



    @Override
    public View getSnackBarHolderView() {
        return root;
    }

    private String tel;
    @Override
    public void callIfEnable(String phoneNumber) {
        List<String> group = new ArrayList<>();
        group.add(Manifest.permission_group.PHONE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), REQUEST_PERMISSION_CALL_AND_CALL_LOG)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        } else {
            tel = phoneNumber;
        }

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CALL_AND_CALL_LOG:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_DENIED) {
                        return;
                    }
                }
                callIfEnable(tel);
        }
    }

    @Override
    public void onFinish(boolean isFinish) {
        if (isFinish) {//计时结束，刷新页面，订单状态应该变为取消状态
            countDown.setVisibility(View.GONE);
//            orderPresenter.getOrderDetail(orderId);
            //延迟一秒去请求数据，防止后台还没修改完状态，获取还是之前的状态
            refreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    orderPresenter.getOrderDetail(orderId);
                }
            },1000);
            if (countDownTimerUtils != null){
                countDownTimerUtils.cancel();
//                countDownTimerUtils = null;
            }
        }
    }
}
