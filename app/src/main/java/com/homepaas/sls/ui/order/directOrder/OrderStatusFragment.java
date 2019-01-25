package com.homepaas.sls.ui.order.directOrder;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.event.EventCallPhoneInfo;
import com.homepaas.sls.event.EventCallPhoneNoConnect;
import com.homepaas.sls.newmvp.contract.OrderStatusContract;
import com.homepaas.sls.newmvp.presenter.OrderStatusPresenter;
import com.homepaas.sls.ui.common.fragment.CommonMvpBaseFragment;
import com.homepaas.sls.ui.order.directOrder.adapter.OrderTrackingAdapter;
import com.homepaas.sls.ui.widget.CallPhoneBulyFragment;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.util.CustomPhoneStateListener;
import com.homepaas.sls.util.PermissionUtils;
import com.homepaas.sls.util.time.TimeTaskHelp;
import com.runtimepermission.acp.AcpListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * Created by JWC on 2017/7/12.
 * 订单状态
 */

public class OrderStatusFragment extends CommonMvpBaseFragment<OrderStatusPresenter> implements OrderTrackingAdapter.HostAction, OrderStatusContract.View {
    @Bind(R.id.track_recycler_view)
    RecyclerView trackRecyclerView;
    private OrderTrackingAdapter orderTrackingAdapter;
    private List<TrackOrderInfo.OrderStatusInfo> orderStatusInfoList;
    private TimeTaskHelp timeTaskHelp;
    private TelephonyManager telephonyManager;
    private CustomPhoneStateListener customPhoneStateListener;
    private String phone;
    private TrackOrderInfo.OrderStatusInfo orderStatusInfoo;

    public static OrderStatusFragment newInstance() {
        OrderStatusFragment fragment = new OrderStatusFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_status;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(customPhoneStateListener = new CustomPhoneStateListener(mContext), PhoneStateListener.LISTEN_CALL_STATE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        trackRecyclerView.setLayoutManager(linearLayoutManager);
        trackRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        if (timeTaskHelp != null)
            timeTaskHelp.stopTime();
        if (telephonyManager != null)
            telephonyManager = null;
        if (customPhoneStateListener != null) {
            customPhoneStateListener.dispose();
        }
        super.onDestroyView();
    }

    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(getActivity()))
                .build()
                .inject(this);
    }

    private String serviceId;

    public void serviceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setTrackOrderStatus(TrackOrderInfo trackOrderInfo) {
        if (trackOrderInfo != null) {
            //倒计时相关信息
            TrackOrderInfo.NextStatus nextStatus = trackOrderInfo.getNextStatus();
            if (nextStatus != null)
                nextStatus.getCountDown();

            //重置倒计时
            if (timeTaskHelp != null) {
                timeTaskHelp.stopTime();
            }
            timeTaskHelp = new TimeTaskHelp();
            if (orderTrackingAdapter == null) {
                orderTrackingAdapter = new OrderTrackingAdapter(getActivity(), timeTaskHelp);
                orderTrackingAdapter.setHostAction(this);
                trackRecyclerView.setAdapter(orderTrackingAdapter);
            }

            orderStatusInfoList = new ArrayList<>();

            for (int i = 0; i < trackOrderInfo.getDateStatus().size(); i++) {
                TrackOrderInfo.OrderStatusInfo orderStatusInfo = new TrackOrderInfo.OrderStatusInfo();
                TrackOrderInfo.DateStatus dateStatus = trackOrderInfo.getDateStatus().get(i);
                orderStatusInfo.setHeadItemData(dateStatus.getDate());
                orderStatusInfo.setType(TrackOrderInfo.OrderStatusInfo.TIME_HEAD);
                //添加头
                orderStatusInfoList.add(orderStatusInfo);
                //添加头对应数据
                orderStatusInfoList.addAll(dateStatus.getOrderStatusInfoList());
            }

            //将nextstatus 倒计时相关信息添加到最后一个item去
            TrackOrderInfo.NextStatus nextStatus1 = trackOrderInfo.getNextStatus();
            if (nextStatus1 != null) {
                if (!TextUtils.isEmpty(nextStatus.getStatus())) {
                    TrackOrderInfo.OrderStatusInfo orderStatusInfo = new TrackOrderInfo.OrderStatusInfo();
                    orderStatusInfo.setDate(nextStatus1.getCountDown());
                    orderStatusInfo.setTime(nextStatus1.getCountDown());
                    orderStatusInfo.setStatus(nextStatus1.getStatus());
                    orderStatusInfo.setDescription(nextStatus1.getDescription());
                    orderStatusInfo.setOrderProviderInfo(nextStatus1.getOrderProviderInfo());
                    orderStatusInfo.setType(TrackOrderInfo.OrderStatusInfo.COUNT_TIME);
                    orderStatusInfoList.add(orderStatusInfo);
                }
            }

            if (orderStatusInfoList != null && !orderStatusInfoList.isEmpty()) {
                orderTrackingAdapter.setOrderStatusInfoList(orderStatusInfoList);
            }
        }
    }


    // 拨打商户或工人电话
    private void dial(final String phone, final String title) {
        PermissionUtils.callPhonePermission(mContext, new AcpListener() {
            @Override
            public void onGranted() {
                NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title, new NewCallDialogFragment.OnCallPhoneListener() {
                    @Override
                    public void onCallPhone() {
                        //通知是app内启动拨打电话标识
                        EventBus.getDefault().post(new EventCallPhoneInfo(phone));
                    }
                });
                serviceFragment.show(getFragmentManager(), null);
            }

            @Override
            public void onDenied(List<String> permissions) {
                showMessage("拨打电话需要权限,请到设置中心");
            }
        });
    }

    /**
     * 电话未拨通提示弹框
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showHintDialog(EventCallPhoneNoConnect eventCallPhoneNoConnect) {
        CallPhoneBulyFragment callPhoneBulyFragment = CallPhoneBulyFragment.newInstance();
        callPhoneBulyFragment.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals("0", v.getTag().toString())) {
                    mPresenter.callPhoneBuly(serviceId, 0, phone, 0, "0", "号码错误");
                } else {
                    mPresenter.callPhoneBuly(serviceId, 0, phone, 0, "0", "无人接听");
                }
            }
        });
        callPhoneBulyFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "");
    }

    @Override
    protected OrderStatusPresenter getPresenter() {
        return new OrderStatusPresenter();
    }

    @Override
    public void submitSuccess() {
        showMessage("谢谢您的反馈，我们会尽快处理。");
    }

    @Override
    public void callWorker(TrackOrderInfo.OrderStatusInfo orderStatusInfoo, String phoneNumber) {
        phone = phoneNumber;
        this.orderStatusInfoo = orderStatusInfoo;
        dial(phoneNumber, orderStatusInfoo.getOrderProviderInfo().getName());
    }
}
