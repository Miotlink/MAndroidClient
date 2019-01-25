package com.homepaas.sls.ui.order.orderplace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.common.PhoneNumberUtils;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.presenter.DescriptionPresenter;
import com.homepaas.sls.mvp.presenter.order.BusinessInfoPresenter;
import com.homepaas.sls.mvp.presenter.order.PlaceOrderPresenter;
import com.homepaas.sls.mvp.presenter.order.ServiceTypePresenter;
import com.homepaas.sls.mvp.presenter.order.WorkerInfoPresenter;
import com.homepaas.sls.mvp.view.GetDescriptionView;
import com.homepaas.sls.mvp.view.order.BusinessOrderPlaceView;
import com.homepaas.sls.mvp.view.order.OrderPlaceView;
import com.homepaas.sls.mvp.view.order.ServiceTypeListView;
import com.homepaas.sls.mvp.view.order.WorkerOrderPlaceView;
import com.homepaas.sls.navigation.CameraTools;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.adapter.PhotoAdapter;
import com.homepaas.sls.ui.order.detail.DetailOrderActivity;
import com.homepaas.sls.ui.personalcenter.personalmessage.ChoosePictureFragment;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.ManuallyCheckBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PlaceOrderActivity extends CommonBaseActivity implements ChoosePictureFragment.OnPictureChoseListener, BusinessOrderPlaceView, WorkerOrderPlaceView, OrderPlaceView, ServiceTypeListView, GetDescriptionView {

    private static final String TAG = "PlaceOrderActivity";
    public static final String SERVICETYPE = "SERVICE_TYPE";
    private static final int MAX_PHOTO_LIMIT = 5;
    private static final String TYPE = "PriceType";
    private static final String ID = "ID";
    public static final String SERVICETYPEID = "SERVICETYPEID";
    @Bind(R.id.hourly_worker)
    TextView hourlyWorker;
    @Bind(R.id.picture_list)
    RecyclerView recyclerView;
    @Bind(R.id.camera)
    ImageView camera;
    @Bind(R.id.picture_container)
    RelativeLayout pictureContainer;
    @Bind(R.id.service_money)
    EditText serviceMoney;
    @Bind(R.id.service_money_quote)
    TextView serviceMoneyQuote;
    @Bind(R.id.service_illustrate)
    TextView serviceIllustrate;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.pay_button)
    Button payButton;
    @Bind(R.id.photo)
    ImageView photo;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.like)
    ManuallyCheckBox like;
    @Bind(R.id.collection)
    ManuallyCheckBox collection;
    @Bind(R.id.score)
    ManuallyCheckBox score;
    @Inject
    CameraTools cameraTools;

    @Inject
    WorkerInfoPresenter workerInfoPresenter;
    @Inject
    BusinessInfoPresenter businessInfoPresenter;
    @Inject
    PlaceOrderPresenter orderPresenter;
    @Inject
    ServiceTypePresenter serviceTypePresenter;
    @Inject
    DescriptionPresenter descriptionPrsenter;
    @Bind(R.id.content)
    EditText content;
    PhotoAdapter adapter;
    @Bind(R.id.content_method)
    LinearLayout contentMethod;
    //    @Bind(R.id.service_type_layout)
//    RelativeLayout serviceTypeLayout;
    private static final int PERMISSION_CAMERA = 1;
    private List<String> datas = new ArrayList<>();
    private String id;
    private int type;
    private boolean isTypeSelected;
    private String serviceTypeId;
    private String servicePrice;
    private static final String AndroidOrderFrom = "0";
    private boolean isPriceProvide;
    private String phonNumber;
    private CommonDialog callProviderDialog;
    private boolean isCallable;

    public static void start(Context context, int type, String id) {
        Intent intent = new Intent(context, PlaceOrderActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(ID, id);
        context.startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_place_order;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        businessInfoPresenter.setBusinessOrderPlaceView(this);
        workerInfoPresenter.setWorkerOrderPlaceView(this);
        orderPresenter.setOrderPlaceView(this);
        serviceTypePresenter.setServiceTypeListView(this);
        descriptionPrsenter.setGetDescriptionView(this);
        descriptionPrsenter.getServiceDescription();
        Intent intent = getIntent();
        type = intent.getIntExtra(TYPE, -1);
        id = intent.getStringExtra(ID);
        Log.i(TAG, "onCreate: " + id);
        setCommonData(type);
        setUpListener();
    }

    @Override
    protected void initData() {

    }

    private void setUpListener() {
        adapter.setItemCountChangeListener(new PhotoAdapter.onItemCountChangeListener() {
            @Override
            public void onCountChange(int count) {
                camera.setVisibility(count >= 5 ? View.GONE : View.VISIBLE);
            }
        });
    }

    private void setCommonData(int type) {
        adapter = new PhotoAdapter(datas, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setEnabled(false);
        if (type == Constant.SERVICE_TYPE_WORKER_INT) {
            workerInfoPresenter.getWorkerInfo(id);
            serviceTypePresenter.getWorkerServiceTypeList(id);

        } else {
            businessInfoPresenter.getBusinessInfo(id);
            serviceTypePresenter.getBusinessServiceTypeList(id);
        }
    }


    @Override
    protected void initializeInjector() {
        super.initializeInjector();
//        Dagge.builder()
//                .applicationComponent(getApplicationComponent())
//                .activityModule(new ActivityModule(this))
//                .build().inject(this);
//        cameraTools.setmContext(this);
        DaggerPlaceOrderComponent.builder().applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build().inject(this);
    }


    /**
     * 选择服务类型
     */
    @OnClick({R.id.hourly_worker, R.id.service_type_layout})
    public void chooseServiceType() {
        mNavigator.serviceType(this, type(), id);
    }

    @OnClick(R.id.content_method)
    public void OnDetailMessage() {
        if (type == Constant.SERVICE_TYPE_WORKER_INT) {
            workerInfoPresenter.getWorkerInfo(id);
        } else {
            businessInfoPresenter.getBusinessInfo(id);
        }
    }

    @OnClick(R.id.camera)
    public void takePhotos() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
//            return;
//        }
        if (datas.size() >= MAX_PHOTO_LIMIT) {
            showMessage("最多只能上传五张图片");
        } else {
            ChoosePictureFragment fragment = ChoosePictureFragment.newInstance("选择图片");
            fragment.show(getSupportFragmentManager(),"");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CAMERA) {
            for (int gr : grantResults) {
                if (gr != PackageManager.PERMISSION_GRANTED)
                    return;
            }
            takePhotos();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case Navigator.SERVICE_TYPE_LIST_REQUEST_CODE:

                    if (data != null) {
                        isTypeSelected = true;
                        hourlyWorker.setText(data.getStringExtra(SERVICETYPE));
                        serviceTypeId = data.getStringExtra(SERVICETYPEID);
                    }
                    break;
                case CameraTools.CODE_TAKE_PHOTO:
                    cameraTools.crop(cameraTools.getTempFile().getAbsolutePath(), 1, 1, 400, 400);
                    break;
                case CameraTools.CODE_CUT:
                    datas.add(cameraTools.getTempFile().getAbsolutePath());
                    adapter.getListener().onCountChange(datas.size());
                    adapter.notifyItemInserted(datas.size() - 1);
                    break;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick({R.id.btn_call})
    public void callServiceProvider() {

        if (callProviderDialog == null)
            callProviderDialog = new CommonDialog.Builder()
                    .setContent("是否电话联系" + getTypeText() + PhoneNumberUtils.encryptPhone(phonNumber))
                    .setConfirmButton("是", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mNavigator.dial(PlaceOrderActivity.this, phonNumber);
                        }
                    }).setCancelButton("否", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callProviderDialog.dismiss();
                        }
                    }).showTitle(false).create();
        callProviderDialog.show(getSupportFragmentManager(), null);
    }

    /**
     * 提交订单
     */
    @OnClick(R.id.pay_button)
    public void submitOrder(View view) {
        String money = serviceMoney.getText().toString().trim();
        String servcieContent = content.getText().toString();
        if (TextUtils.isEmpty(servcieContent)){showMessage("请输入服务内容");return;}
        if (money.startsWith(".")){showMessage("请输入有效的服务金额");return;}//防止输入点号
        if (servcieContent.length()<10){showMessage("请输入10到200字的服务内容");return;}//限制输入内容长度
        isPriceProvide = !TextUtils.isEmpty(money)&&Double.valueOf(money)>0;
        if (isTypeSelected) {
            servicePrice = money;
//            orderPresenter.placeOrder(datas, type(), id, serviceTypeId, content.getText().toString(), servicePrice, AndroidOrderFrom);
            throttle(view);
        } else {
            showMessage("请选择服务类别后下单");
        }
    }


    /**
     * 获取下单类型，2 工人，3 商户
     * @return
     */

    private String type() {
        return String.valueOf(type == Constant.SERVICE_TYPE_WORKER_INT ? 2 : 3);
    }

    public String getTypeText() {
        return type == Constant.SERVICE_TYPE_WORKER_INT ? "工人" : "商户";
    }

    /**
     * 渲染工人数据
     *
     * @param workerInfoModel
     */
    @Override
    public void render(WorkerCollectionEntity workerInfoModel) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, "render: " + workerInfoModel.getAge());
        Log.i(TAG, "render: " + workerInfoModel.getAddress());
        Glide.with(this).load(workerInfoModel.getPhoto())
                .placeholder(R.mipmap.worker_portrait_default)
                .bitmapTransform(new RoundedCornersTransformation(this, 9, 2)).into(photo);

        name.setText(workerInfoModel.getName());
        like.setChecked(workerInfoModel.getLikeCount() > 0 ? true : false);
        like.setText(String.valueOf(workerInfoModel.getLikeCount()));
        collection.setChecked(workerInfoModel.getCollectedCount() > 0 ? true : false);
        collection.setText(workerInfoModel.getCollectedCount() + "");
        score.setText(workerInfoModel.getGradeNumber());
//        serviceIllustrate.setText(workerInfoModel.getD);
        phonNumber = workerInfoModel.getPhoneNumber();
        isCallable = workerInfoModel.isCallable();
    }

    /**
     * 渲染商户数据
     *
     * @param businessInfoModel
     */
    @Override
    public void render(BusinessInfoModel businessInfoModel) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, "render: " + businessInfoModel.getServiceTime());
        Glide.with(this).load(businessInfoModel.getPhoto())
                .placeholder(R.mipmap.worker_portrait_default)
                .bitmapTransform(new RoundedCornersTransformation(this, 9, 2)).into(photo);

        name.setText(businessInfoModel.getName());
        like.setChecked(businessInfoModel.isLike());
        like.setText(String.valueOf(businessInfoModel.getLikeCount()));
        collection.setChecked(businessInfoModel.isCollected());
        collection.setText(businessInfoModel.getCollectedCount() + "");
        score.setText(businessInfoModel.getGradeNumber());
        phonNumber = businessInfoModel.getPhoneNumber();
        isCallable = businessInfoModel.isCallable();
    }

    @Override
    public void onOrderCreate(String orderId,String serviceTypeId) {
        String totalMoney = serviceMoney.getText().toString();
        if (isPriceProvide) {
            //跳转至支付页面---下单成功的页面--判断服务金额是否为零
            if (BuildConfig.DEBUG)
                Toast.makeText(getApplicationContext(), "下单成功！" , Toast.LENGTH_SHORT).show();
            OrderPayActivity.start(this, orderId, totalMoney, name.getText().toString());
        } else {
            //订单详情
            if (BuildConfig.DEBUG)
                Toast.makeText(getApplicationContext(), "" + orderId, Toast.LENGTH_SHORT).show();
            DetailOrderActivity.start(this, orderId);
        }
        finish();
    }


    @Override
    public void renderWorker(WorkerServiceTypeInfo serviceTypeListInfo) {
        if (serviceTypeListInfo != null && serviceTypeListInfo.getList().size() != 0) {
            serviceTypeId = serviceTypeListInfo.getList().get(0).getServiceTypeId();
            hourlyWorker.setText(serviceTypeListInfo.getList().get(0).getServiceTypeName());
            isTypeSelected = true;
        }
    }

    @Override
    public void renderBusiness(BusinessServiceTypeInfo serviceTypeListInfo) {
        if (serviceTypeListInfo != null && serviceTypeListInfo.getList().size() != 0) {
            serviceTypeId = serviceTypeListInfo.getList().get(0).getServiceTypeId();
            hourlyWorker.setText(serviceTypeListInfo.getList().get(0).getServiceTypeName());
            isTypeSelected = true;
        }
    }


    @Override
    public void renderDescription(String descripiton) {
        serviceIllustrate.setText(descripiton);
    }

    @Override
    protected void retrieveData() {
        submitOrder(payButton);
    }

    @Override
    public void onPictureChose(File filePath) {
        datas.add(filePath.getAbsolutePath());
        adapter.getListener().onCountChange(datas.size());
        adapter.notifyItemInserted(datas.size() - 1);
    }
}
