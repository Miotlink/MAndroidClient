package com.homepaas.sls.ui.order.pay;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.di.component.DaggerOrderComponent;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.domain.entity.embedded_class.Service;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.mvp.view.order.OrderDetailView;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.directOrder.ClientOrderDetailActivity;
import com.homepaas.sls.ui.order.directOrder.ErrandServiceDetailActivity;
import com.homepaas.sls.ui.order.directOrder.ExpressOrderTrackingActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.util.StaticData;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class PaySuccessInfoActivity extends CommonBaseActivity implements OrderDetailView {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.client_name)
    TextView clientName;
    @Bind(R.id.service_address)
    TextView serviceAddress;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.service_time)
    TextView serviceTime;
    @Bind(R.id.activity_pay_success_info)
    LinearLayout activityPaySuccessInfo;
    @Bind(R.id.pay_sum)
    TextView paySum;

    @Inject
    public OrderPresenter payPresenter;

    private String orderId;
    private String payMoney;
    private String serviceAddressStr;
    private String clientNamaStr;
    private String serviceTimeStr;
    private String isKdOrder;

    public static void start(Context context, String orderId, String payMoney, String serviceAddress, String clientNama, String serviceTime, String isKdOrder) {
        Intent intent = new Intent(context, PaySuccessInfoActivity.class);
        intent.putExtra(Constant.OrderId, orderId);
        intent.putExtra(StaticData.PAY_MONEY, payMoney);
        intent.putExtra(StaticData.SERVICE_ADDRESS, serviceAddress);
        intent.putExtra(StaticData.CLIENT_NAME, clientNama);
        intent.putExtra(StaticData.SERVICE_TIME, serviceTime);
        intent.putExtra(StaticData.IS_KD_ORDER, isKdOrder);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success_info;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        orderId = getIntent().getStringExtra(Constant.OrderId);
        payMoney = getIntent().getStringExtra(StaticData.PAY_MONEY);

        serviceAddressStr = getIntent().getStringExtra(StaticData.SERVICE_ADDRESS);
        clientNamaStr = getIntent().getStringExtra(StaticData.CLIENT_NAME);
        serviceTimeStr = getIntent().getStringExtra(StaticData.SERVICE_TIME);
        isKdOrder = getIntent().getStringExtra(StaticData.IS_KD_ORDER);
        //不是订单详情进入，服务信息没有传递，需要通过接口进行获取
        if (TextUtils.isEmpty(serviceAddressStr) && TextUtils.isEmpty(clientNamaStr) && TextUtils.isEmpty(serviceTimeStr) && TextUtils.isEmpty(isKdOrder)) {
            payPresenter.setOrderDetailView(this);
            payPresenter.getOrderDetail(orderId);
        } else {
            initData();
        }
    }

    public void initData() {
        clientName.setText(clientNamaStr);
        serviceAddress.setText(serviceAddressStr);
        serviceTime.setText(TimeUtils.formatOrderTime(serviceTimeStr));
        paySum.setText("已支付￥" + payMoney);
    }

    @OnClick(R.id.check_detail)
    public void checkDetail() {
        if (TextUtils.equals("1", isKdOrder)) {
            ExpressOrderTrackingActivity.start(this, orderId);
        } else if (TextUtils.equals("2", isKdOrder)) {
            ErrandServiceDetailActivity.start(this, orderId);
        } else {
            ClientOrderDetailActivity.start(this, orderId);
        }
        ActivityCompat.finishAfterTransition(this);
    }

    @OnClick(R.id.main_page)
    public void shootMainPage() {
        startActivity(new Intent(this, MainActivity.class));
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void render(DetailOrderEntity order, long requestTime) {
        //传递到支付成功页面的数据
        if (order.getService() != null) {
            Service service = order.getService();
            serviceTimeStr = service.getServiceStartAt();
            if (service.getAddressInfo() != null) {
                serviceAddressStr = service.getAddressInfo().getAddress1();
                clientNamaStr = service.getAddressInfo().getContact();
            }
            isKdOrder = order.getIsKdEOrder();
            initData();
        }
    }

    @Override
    public void renderTrackOrderStatus(TrackOrderInfo orderInfo) {

    }


    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerOrderComponent.builder().applicationComponent(getApplicationComponent())
                .build().inject(this);
    }


    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int ResId) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showLoading() {

    }
}
