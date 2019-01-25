package com.homepaas.sls.ui.order.directOrder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.KdEOrderInfo;
import com.homepaas.sls.domain.entity.KdTrackInfoResponse;
import com.homepaas.sls.domain.entity.embedded_class.Address;
import com.homepaas.sls.mvp.presenter.order.ExpressOrderDetailPresenter;
import com.homepaas.sls.mvp.view.order.ExpressOrderDetailView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.directOrder.adapter.ExpressOrderTrackingAdapter;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/6/3.
 * 快递订单跟踪页面
 */

public class ExpressOrderTrackingActivity extends CommonBaseActivity implements ExpressOrderDetailView {


    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.status_picture)
    ImageView statusPicture;
    @Bind(R.id.status_type)
    TextView statusType;
    @Bind(R.id.status_content)
    TextView statusContent;
    @Bind(R.id.wait_courier_rel)
    RelativeLayout waitCourierRel;
    @Bind(R.id.express_company)
    TextView expressCompany;
    @Bind(R.id.express_odd_Numbers)
    TextView expressOddNumbers;
    @Bind(R.id.division_image)
    ImageView divisionImage;
    @Bind(R.id.express_order_tarcking_recyclerView)
    RecyclerView expressOrderTarckingRecyclerView;
    @Bind(R.id.express_order_trackint_lin)
    LinearLayout expressOrderTrackintLin;
    @Bind(R.id.company_icon)
    ImageView companyIcon;
    @Bind(R.id.express_company_text)
    TextView expressCompanyText;
    @Bind(R.id.send_address_text)
    TextView sendAddressText;
    @Bind(R.id.send_address)
    TextView sendAddress;
    @Bind(R.id.send_people)
    TextView sendPeople;
    @Bind(R.id.receiver_address_text)
    TextView receiverAddressText;
    @Bind(R.id.receiver_address)
    TextView receiverAddress;
    @Bind(R.id.receiver_people)
    TextView receiverPeople;
    @Bind(R.id.goods_name)
    TextView goodsName;
    @Bind(R.id.goods_weight)
    TextView goodsWeight;
    @Bind(R.id.payment_method)
    TextView paymentMethod;
    @Bind(R.id.estimated_cost)
    TextView estimatedCost;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.order_create_time)
    TextView orderCreateTime;
    @Bind(R.id.empty_view)
    NestedScrollView emptyView;
    @Bind(R.id.swipe_refresh)
    HeaderViewLayout swipeRefresh;
    private String orderId;
    private DetailOrderEntity detailOrderEntity;
    private ExpressOrderTrackingAdapter expressOrderTrackingAdapter;

    @Inject
    ExpressOrderDetailPresenter expressOrderDetailPresenter;

    public static void start(Context context, String orderId) {
        Intent intent = new Intent(context, ExpressOrderTrackingActivity.class);
        intent.putExtra(Constant.OrderId, orderId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_express_order_tracking;
    }

    @Override
    protected void initView() {
        orderId = getIntent().getStringExtra(Constant.OrderId);
        expressOrderDetailPresenter.setExpressOrderDetailView(this);
        expressOrderDetailPresenter.getExpressOrderDetail(orderId);


        swipeRefresh.setOnRefreshListener(mOnRefreshListener);
        expressOrderTrackingAdapter = new ExpressOrderTrackingAdapter();
        expressOrderTarckingRecyclerView.setAdapter(expressOrderTrackingAdapter);
    }

    @Override
    protected void initData() {

    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            expressOrderDetailPresenter.getExpressOrderDetail(orderId);
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
     * 订单详情
     *
     * @param detailOrderEntity
     */
    @Override
    public void renderOrderDetail(DetailOrderEntity detailOrderEntity) {
        swipeRefresh.stopRefresh();
        this.detailOrderEntity = detailOrderEntity;
        if (detailOrderEntity != null) {
            if (detailOrderEntity.getService() != null && detailOrderEntity.getService().getAddressInfo() != null) {
                Address address = detailOrderEntity.getService().getAddressInfo();
                sendAddress.setText(address.getAddress1() + address.getAddress2());
                sendPeople.setText(address.getContact() + " " + address.getPhoneNumber());
            }
            KdEOrderInfo kdEOrderInfo = detailOrderEntity.getKdEOrderInfo();
            if (kdEOrderInfo != null) {
                expressCompany.setText(detailOrderEntity.getKdEOrderInfo().getShipperName());
                expressOddNumbers.setText(divisionLogisticCode(detailOrderEntity.getKdEOrderInfo().getLogisticCode()));
                Glide.with(this).load(kdEOrderInfo.getShipperIcon()).placeholder(R.mipmap.client_v3_3_0_ic_expressage_head).into(companyIcon);
                expressCompanyText.setText(kdEOrderInfo.getShipperName());
                KdEOrderInfo.ServiceAddressResponse serviceAddressResponse = kdEOrderInfo.getTargetAddress();
                if (serviceAddressResponse != null) {
                    receiverAddress.setText(serviceAddressResponse.getAddress1() + " " + serviceAddressResponse.getAddress2());
                    receiverPeople.setText(serviceAddressResponse.getContact() + " " + serviceAddressResponse.getPhoneNumber());
                }
                goodsName.setText(kdEOrderInfo.getGoodsName());
                goodsWeight.setText(kdEOrderInfo.getGoodsWight() + "kg");
                if (!TextUtils.isEmpty(kdEOrderInfo.getPayType()) && TextUtils.equals(kdEOrderInfo.getPayType(), "1")) {
                    paymentMethod.setText("寄件人付");
                } else {
                    paymentMethod.setText("收件人付");
                }
                estimatedCost.setText("¥" + kdEOrderInfo.getPerPayAmount());
                expressOrderDetailPresenter.getKdTrackInfo(kdEOrderInfo.getLogisticCode(), kdEOrderInfo.getShipperCode());
            }
            orderNumber.setText(detailOrderEntity.getOrderCode());
            orderCreateTime.setText(TimeUtils.formatOrderTime(detailOrderEntity.getCreateTime()));
        }
    }


    /**
     * 物流信息
     *
     * @param kdTrackInfoResponse
     */
    @Override
    public void renderKdTrackInfo(KdTrackInfoResponse kdTrackInfoResponse) {
        if (kdTrackInfoResponse != null && kdTrackInfoResponse.getTraces() != null && kdTrackInfoResponse.getTraces().size() > 0) {
            waitCourierRel.setVisibility(View.GONE);
            divisionImage.setVisibility(View.VISIBLE);
            expressOrderTarckingRecyclerView.setVisibility(View.VISIBLE);
            expressOrderTrackingAdapter.setList(kdTrackInfoResponse.getTraces());
        } else {
            divisionImage.setVisibility(View.GONE);
            waitCourierRel.setVisibility(View.VISIBLE);
            expressOrderTarckingRecyclerView.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }

    /**
     * string加空格
     *
     * @param numberStr
     * @return
     */
    private String divisionLogisticCode(String numberStr) {
        if (!TextUtils.isEmpty(numberStr)) {
            return numberStr.replaceAll(".{3}(?!$)", "$0 ");
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        if (swipeRefresh != null) {
            swipeRefresh.stopRefresh();
            swipeRefresh.destory();
        }
        super.onDestroy();
    }
}
