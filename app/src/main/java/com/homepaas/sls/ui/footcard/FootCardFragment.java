package com.homepaas.sls.ui.footcard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMainComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.entity.BusinessEntity;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.WorkerEntity;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.order.directOrder.PlaceOrderActivity;
import com.homepaas.sls.ui.widget.MyRatingBar;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/12/15.
 */

public class FootCardFragment extends CommonBaseFragment implements FootCardView {


    private static final String ARG_PARAM1 = "param1";
    @Bind(R.id.head_portrait)
    ImageView headPortrait;
    @Bind(R.id.m_name)
    TextView mName;
    @Bind(R.id.merchant_name)
    LinearLayout merchantName;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.gender_age)
    TextView genderAge;
    @Bind(R.id.worker_name)
    LinearLayout workerName;
    @Bind(R.id.rating_bar)
    MyRatingBar ratingBar;
    @Bind(R.id.scope)
    TextView scope;
    @Bind(R.id.activity_jian)
    ImageView activityJian;
    @Bind(R.id.activity_fan)
    ImageView activityFan;
    @Bind(R.id.distance)
    TextView distance;
    @Bind(R.id.merchant_address)
    TextView merchantAddress;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.worker_address)
    LinearLayout workerAddress;
    @Bind(R.id.service_type)
    TextView serviceType;
    @Bind(R.id.showDetial)
    RelativeLayout showDetial;
    @Bind(R.id.take_order_text)
    TextView takeOrderText;
    @Bind(R.id.take_order)
    FrameLayout takeOrder;
    @Bind(R.id.call_phone_text)
    TextView callPhoneText;
    @Bind(R.id.call_phone)
    FrameLayout callPhone;
    @Bind(R.id.w_worker)
    ImageView wWorker;


    private IService service;


    public static FootCardFragment newInstance(IService service) {
        FootCardFragment fragment = new FootCardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) service);
        fragment.setArguments(args);
        return fragment;
    }

    public static FootCardFragment newInstance() {

        FootCardFragment fragment = new FootCardFragment();
        return fragment;
    }

    @Inject
    FootCardPresenter footCardPresenter;
    @Inject
    Navigator mNavigator;

//    public void setData(IService service1) {
//        this.service = service1;
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            service = (IService) getArguments().getSerializable(ARG_PARAM1);
        }
        footCardPresenter.setFootCardView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_page_service_item_layout;
    }

    @Override
    protected void initView() {
        if (service != null) {
            if (service instanceof WorkerEntity) {
                setWorkerStyle();
            } else
                setMerchantStyle();

            callPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    footCardPresenter.call(service);
                }
            });

            showDetial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (service instanceof WorkerEntity) {
//                        mNavigator.showWorkerDetail(getContext(), ((WorkerEntity) service).getId());
                        mNavigator.showMerchantWorkerDetail(getContext(), Constant.SERVICE_TYPE_WORKER,((WorkerEntity) service).getId());
                    } else
//                        mNavigator.showBusinessDetail(getContext(), ((BusinessEntity) service).getId());
                        mNavigator.showMerchantWorkerDetail(getContext(), Constant.SERVICE_TYPE_BUSINESS,((BusinessEntity) service).getId());
                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    private WorkerEntity workerEntity;

    private void setWorkerStyle() {
        workerEntity = (WorkerEntity) service;
        merchantName.setVisibility(View.GONE);
        workerName.setVisibility(View.VISIBLE);
        merchantAddress.setVisibility(View.GONE);
        workerAddress.setVisibility(View.VISIBLE);
        if (TextUtils.equals("1", workerEntity.getIsWholeWorker())) {
            wWorker.setVisibility(View.VISIBLE);
        } else {
            wWorker.setVisibility(View.GONE);
        }
        Glide.with(this).load(workerEntity.photo).placeholder(R.mipmap.worker_portrait_default).into(new ImageTarget(headPortrait));
        name.setText(workerEntity.getName());
        genderAge.setText(workerEntity.getAge());
        if (TextUtils.equals("0", workerEntity.getGender())) {
            genderAge.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.man, 0, 0, 0);
            genderAge.setSelected(false);
        } else {
            genderAge.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.woman, 0, 0, 0);
            genderAge.setSelected(true);
        }
//        String[] grade = workerEntity.getGrade().split("评分");

//        String grade = workerEntity.getGrade().split("评价")[1].split("分")[0];
        ratingBar.setmScope(Float.parseFloat(workerEntity.getGrade()));
        scope.setText(workerEntity.getGrade());
        if (TextUtils.equals("1", workerEntity.getIsReduction())) {
            activityJian.setVisibility(View.VISIBLE);
        } else {
            activityJian.setVisibility(View.INVISIBLE);
        }
        if (TextUtils.equals("1", workerEntity.getIsReturn())) {
            activityFan.setVisibility(View.VISIBLE);
        } else {
            activityFan.setVisibility(View.INVISIBLE);
        }
        distance.setText(workerEntity.getDistance());
        address.setText(workerEntity.getNativePlace());
        time.setText(workerEntity.getDisplayAttribute() + "年");
        StringBuilder stringBuilder = new StringBuilder();
        for (String ss : workerEntity.getServices()) {
            stringBuilder.append(ss);
            if (workerEntity.getServices().indexOf(ss) < (workerEntity.getServices().size() - 1))
                stringBuilder.append("、");
        }
        serviceType.setText(stringBuilder.toString());
        if (TextUtils.equals("1", workerEntity.getAcceptOrder())) {
            takeOrderText.setSelected(false);
            takeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlaceOrderActivity.start(getContext(), Constant.SERVICE_TYPE_WORKER_INT, workerEntity.getId(), workerEntity.getGender());
                }
            });
        } else {
            takeOrderText.setSelected(true);
        }
        if (TextUtils.equals("1", workerEntity.getIscall())) {
            callPhoneText.setSelected(false);
        } else {
            callPhoneText.setSelected(true);
        }


    }

    private BusinessEntity businessEntity;

    private void setMerchantStyle() {
        businessEntity = (BusinessEntity) service;
        merchantName.setVisibility(View.VISIBLE);
        workerName.setVisibility(View.GONE);
        merchantAddress.setVisibility(View.VISIBLE);
        workerAddress.setVisibility(View.GONE);
        Glide.with(this).load(businessEntity.photo).placeholder(R.mipmap.business_portrait_default).into(new ImageTarget(headPortrait));
        mName.setText(businessEntity.getName());
        if (TextUtils.equals("1", businessEntity.getIsWholeMerchant())/*businessEntity.isWholeCityProvider()*/) {
            name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.service, 0, 0, 0);
        } else {
            name.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.merchants, 0, 0, 0);
        }
//        String[] grade = businessEntity.getGrade().split("评分");
//        String grade = businessEntity.getGrade().split("评价")[1].split("分")[0];
        ratingBar.setmScope(Float.parseFloat(businessEntity.getGrade()));
        scope.setText(businessEntity.getGrade());
        if (TextUtils.equals("1", businessEntity.getIsReduction())) {
            activityJian.setVisibility(View.VISIBLE);
        } else {
            activityJian.setVisibility(View.INVISIBLE);
        }
        if (TextUtils.equals("1", businessEntity.getIsReturn())) {
            activityFan.setVisibility(View.VISIBLE);
        } else {
            activityFan.setVisibility(View.INVISIBLE);
        }
        distance.setText(businessEntity.getDistance());
        merchantAddress.setText(businessEntity.getAddress());
        StringBuilder stringBuilder = new StringBuilder();
        for (String ss : businessEntity.getServices()) {
            stringBuilder.append(ss);
            if (businessEntity.getServices().indexOf(ss) < (businessEntity.getServices().size() - 1))
                stringBuilder.append("、");
        }
        serviceType.setText(stringBuilder.toString());
        if (TextUtils.equals("1", businessEntity.getAcceptOrder())) {
            takeOrderText.setSelected(false);
            takeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlaceOrderActivity.start(getContext(), Constant.SERVICE_TYPE_BUSINESS_INT, businessEntity.getId());
                }
            });
        } else {
            takeOrderText.setSelected(true);
        }


    }


    private String mCallingPhone;
    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 1;

    // 拨打商户或工人电话
    private void dial(String phone) {
        List<String> permissions = new ArrayList<>(2);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //Service 中无法回调onRequestPermissionsResult，提前请求
            permissions.add(Manifest.permission.READ_CALL_LOG);
        }
        if (permissions.isEmpty()) {
            footCardPresenter.startDial();
           /* Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);*/
            mNavigator.call(getActivity(), phone);
        } else {
            mCallingPhone = phone;
            requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION_CALL_AND_CALL_LOG);
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
                dial(mCallingPhone);
                break;

        }
    }

    @Override
    protected void initializeInjector() {
//        getComponent(MainComponent.class)
//                .inject(this);
        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        footCardPresenter.destroy();
    }

    @Override
    public void call(IService service, boolean enable) {
        if (enable) {
            if (service instanceof WorkerEntity) {
                if (TextUtils.equals("1", ((WorkerEntity) service).getIscall()))
                    dial(((WorkerEntity) service).getPhoneNumber());
            } else {
                if (TextUtils.equals("1", ((BusinessEntity) service).getIsCall()))
                    dial(((BusinessEntity) service).getPhoneNumber());
            }
        }
    }
}
