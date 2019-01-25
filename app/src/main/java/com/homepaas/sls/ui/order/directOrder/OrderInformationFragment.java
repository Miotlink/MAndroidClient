package com.homepaas.sls.ui.order.directOrder;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.FlatUtils;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.data.repository.Constant;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.ClaimsInfo;
import com.homepaas.sls.domain.entity.DepositInfo;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.embedded_class.Picture;
import com.homepaas.sls.domain.entity.embedded_class.Refund;
import com.homepaas.sls.domain.entity.embedded_class.Service;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.order.directOrder.adapter.ClientOrderActivityAdapter;
import com.homepaas.sls.ui.order.directOrder.adapter.OrderPhotoAdapter;
import com.homepaas.sls.ui.order.directOrder.adapter.OrderRefundAdapter;
import com.homepaas.sls.ui.widget.KeywordUtil;
import com.homepaas.sls.ui.widget.photo.PhotoPreviewDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;

/**
 * Created by JWC on 2017/7/12.
 * 订单信息
 */

public class OrderInformationFragment extends CommonBaseFragment implements OrderPhotoAdapter.OnPictureOnClickListener {


    @Bind(R.id.service_type)
    TextView serviceType;
    @Bind(R.id.service_time)
    TextView serviceTime;
    @Bind(R.id.claims_title)
    TextView claimsTitle;
    @Bind(R.id.claims_money)
    TextView claimsMoney;
    @Bind(R.id.claims_rel)
    RelativeLayout claimsRel;
    @Bind(R.id.service_total_price)
    TextView serviceTotalPrice;
    @Bind(R.id.service_total_price_rel)
    RelativeLayout serviceTotalPriceRel;
    @Bind(R.id.service_price_title)
    TextView servicePriceTitle;
    @Bind(R.id.service_price)
    TextView servicePrice;
    @Bind(R.id.service_price_rel)
    RelativeLayout servicePriceRel;
    @Bind(R.id.activity_recycler_view)
    RecyclerView activityRecyclerView;
    @Bind(R.id.red_coupon_title)
    TextView redCouponTitle;
    @Bind(R.id.red_coupon_money)
    TextView redCouponMoney;
    @Bind(R.id.red_coupon_rel)
    RelativeLayout redCouponRel;
    @Bind(R.id.payment_type_title)
    TextView paymentTypeTitle;
    @Bind(R.id.payment_money)
    TextView paymentMoney;
    @Bind(R.id.payment_rel)
    RelativeLayout paymentRel;
    @Bind(R.id.remark)
    TextView remark;
    @Bind(R.id.photo_recycler_view)
    RecyclerView photoRecyclerView;
    @Bind(R.id.remark_lin)
    LinearLayout remarkLin;
    @Bind(R.id.refund_recycler_view)
    RecyclerView refundRecyclerView;
    @Bind(R.id.refund_lin)
    LinearLayout refundLin;
    @Bind(R.id.client_name)
    TextView clientName;
    @Bind(R.id.client_phone)
    TextView clientPhone;
    @Bind(R.id.client_address)
    TextView clientAddress;
    @Bind(R.id.order_code)
    TextView orderCode;

    private ClientOrderActivityAdapter clientOrderActivityAdapter;
    private List<ActivityNgInfoNew.ActivityNgDetail> activityNgDetailList;
    //订单图片
    private OrderPhotoAdapter orderPhotoAdapter;
    private List<Picture> pictureList;
    private List<String> photoList;
    //退款相关信息
    private OrderRefundAdapter orderRefundAdapter;
    private List<Refund> refundList;


    public static OrderInformationFragment newInstance() {
        OrderInformationFragment fragment = new OrderInformationFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_information;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (orderEntity != null && serviceType.getText().toString().trim().equals(""))
            setOrderInfomation(orderEntity);
    }

    public DetailOrderEntity orderEntity;

    public void setOrderInfomation(DetailOrderEntity orderInfo) {
        orderEntity = orderInfo;
        Service service = orderInfo.getService();
        String isNegotiable = orderInfo.getNegotiable();
        String totalPriceStr = orderInfo.getTotalPrice();
        ClaimsInfo claimsInfo = orderInfo.getClaimsInfo();
        DepositInfo depositInfo = orderInfo.getDepositInfo();
        if (service != null) {
            serviceType.setText(service.getServiceName());
            serviceTime.setText(TimeUtils.formatOrderTime(service.getServiceStartAt()));
            pictureList = service.getPictures();
            if (service.getAddressInfo() != null) {
                clientName.setText(service.getAddressInfo().getContact());
                clientPhone.setText(service.getAddressInfo().getPhoneNumber());
                clientAddress.setText(service.getAddressInfo().getAddress1()+service.getAddressInfo().getAddress2());
            }
        }
        //一元保险
        claimsRel.setVisibility(View.GONE);
        if (claimsInfo != null && !TextUtils.isEmpty(claimsInfo.getClaimsAmount())) {
            claimsRel.setVisibility(View.VISIBLE);
            claimsTitle.setText(KeywordUtil.determine(claimsInfo.getClaimsAmount()) + "元保险");
            claimsMoney.setText("¥" + claimsInfo.getClaimsAmount());
        }
        //总价
        serviceTotalPriceRel.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(totalPriceStr)) {
            serviceTotalPriceRel.setVisibility(View.VISIBLE);
            serviceTotalPrice.setText("¥" + totalPriceStr);
        }
        //服务价格或者订金
        servicePriceRel.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(orderInfo.getPayAmount()) && !TextUtils.isEmpty(orderInfo.getTotalPrice())) {
            servicePriceRel.setVisibility(View.VISIBLE);
            servicePriceTitle.setText(R.string.service_price_str);
            servicePrice.setText(orderInfo.getPrice() + "/" + orderInfo.getUnitName() + " x" + orderInfo.getServiceNumber());
        } else {
            if (depositInfo != null && !TextUtils.isEmpty(depositInfo.getDepositAmount())) {
                servicePriceRel.setVisibility(View.VISIBLE);
                servicePriceTitle.setText(R.string.deposit);
                servicePrice.setText("¥" + depositInfo.getDepositAmount());
            }
        }
        //活动显示
        activityRecyclerView.setVisibility(View.GONE);
        ActivityNgInfoNew activityNgInfos = orderInfo.getActivityNgInfos();
        if (!TextUtils.isEmpty(orderInfo.getTotalPrice()) && activityNgInfos != null && activityNgInfos.isSpecialSatisfied(Float.valueOf(orderInfo.getTotalPrice()))) {
            activityNgDetailList = activityNgInfos.getSatisfiedSpecialActivityList();
            if (activityNgDetailList != null && !activityNgDetailList.isEmpty()) {
                activityRecyclerView.setVisibility(View.VISIBLE);
                if (clientOrderActivityAdapter == null) {
                    clientOrderActivityAdapter = new ClientOrderActivityAdapter();
                    activityRecyclerView.setAdapter(clientOrderActivityAdapter);
                }
                clientOrderActivityAdapter.setActivityNgDetailList(activityNgDetailList);
            }
        }
        //红包
        redCouponRel.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(orderInfo.getCouponMoney())) {
            redCouponRel.setVisibility(View.VISIBLE);
            redCouponTitle.setText(orderInfo.getCouponTitle());
            redCouponMoney.setText("-¥" + cutUnnecessaryPrecision(orderInfo.getCouponMoney()));
        }
        //待付款还是实付
        if (!TextUtils.isEmpty(orderInfo.getPayAmount())) {
            paymentRel.setVisibility(View.VISIBLE);
            String payStatus = orderInfo.getPayStatus();
            paymentTypeTitle.setText(TextUtils.equals(Constant.PAYOFF, payStatus) ? "待支付" : "实付");
            paymentMoney.setText("¥" + orderInfo.getPayAmount());
        } else {
            paymentRel.setVisibility(View.GONE);
        }
        orderCode.setText(orderInfo.getOrderCode());
        //备注
        remarkLin.setVisibility(View.GONE);
        if (service != null && service.getContent() != null && !service.getContent().isEmpty()) {
            remarkLin.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            List<String> memos = new ArrayList<>();
            if (orderInfo.getService() != null) {
                memos = orderInfo.getService().getContent();
            }
            int size = memos.size();
            for (int i = 0; i < size; i++) {
                String content = memos.get(i);
                if (i < size - 1) {
                    if (!TextUtils.isEmpty(content)) {
                        stringBuilder.append(content);
                        stringBuilder.append(",");
                    }
                } else if (i == size - 1) {
                    if (!TextUtils.isEmpty(content)) {
                        stringBuilder.append(content);
                    }
                }
            }
            remark.setText(stringBuilder.toString());
        }
        //图片
        photoRecyclerView.setVisibility(View.GONE);
        if (pictureList != null && !pictureList.isEmpty()) {
            remarkLin.setVisibility(View.VISIBLE);
            photoRecyclerView.setVisibility(View.VISIBLE);
            if (orderPhotoAdapter == null) {
                orderPhotoAdapter = new OrderPhotoAdapter(getActivity());
                orderPhotoAdapter.setOnPictureOnClickListener(this);
                photoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                photoRecyclerView.setAdapter(orderPhotoAdapter);
            }
            orderPhotoAdapter.setPhotoList(pictureList);
            photoList = FlatUtils.extract(pictureList, new FlatUtils.ExtractFunc<String, Picture>() {
                @Override
                public String func(Picture picture) {
                    return picture.getSmallPic();
                }
            });
        }
        //退款
        refundLin.setVisibility(View.GONE);
        refundList = orderInfo.getRefunds();
        if (refundList != null && !refundList.isEmpty()) {
            refundLin.setVisibility(View.VISIBLE);
            if (orderRefundAdapter == null) {
                orderRefundAdapter = new OrderRefundAdapter();
                refundRecyclerView.setAdapter(orderRefundAdapter);
            }
            orderRefundAdapter.setRefundList(refundList);
        }

    }


    @Override
    public void zomm(int position) {
        PhotoPreviewDialog previewDialog = new PhotoPreviewDialog.Builder()
                .index(position)
                .path(photoList)
                .build();
        previewDialog.setIndex(position);
        previewDialog.show(getFragmentManager(), null);
    }
}
