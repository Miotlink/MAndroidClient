package com.homepaas.sls.ui.order.directOrder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.BusinessServiceType;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.CommonServiceType;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.WorkerServiceType;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.domain.entity.WorkerServiceTypePrice;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.event.EmptyAddressEvent;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.PlaceOrderParamModel;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.presenter.DescriptionPresenter;
import com.homepaas.sls.mvp.presenter.DetailPresenter;
import com.homepaas.sls.mvp.presenter.ServicePresenter;
import com.homepaas.sls.mvp.presenter.addressmanage.AddressPresenter;
import com.homepaas.sls.mvp.presenter.order.BusinessInfoPresenter;
import com.homepaas.sls.mvp.presenter.order.BusinessServicePricePresenter;
import com.homepaas.sls.mvp.presenter.order.PlaceOrderPresenter;
import com.homepaas.sls.mvp.presenter.order.ServiceTypePresenter;
import com.homepaas.sls.mvp.presenter.order.WorkerInfoPresenter;
import com.homepaas.sls.mvp.view.BusinessPhoneView;
import com.homepaas.sls.mvp.view.CallView;
import com.homepaas.sls.mvp.view.GetDescriptionView;
import com.homepaas.sls.mvp.view.QtyView;
import com.homepaas.sls.mvp.view.ServiceView;
import com.homepaas.sls.mvp.view.WorkerPhoneView;
import com.homepaas.sls.mvp.view.addressmanage.AddAddressView;
import com.homepaas.sls.mvp.view.addressmanage.ManageAddressView;
import com.homepaas.sls.mvp.view.order.ActivityView;
import com.homepaas.sls.mvp.view.order.BusinessOrderPlaceView;
import com.homepaas.sls.mvp.view.order.OrderPlaceView;
import com.homepaas.sls.mvp.view.order.ServiceTypeListView;
import com.homepaas.sls.mvp.view.order.WorkerOrderPlaceView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.adapter.PlaceOrderAdapter;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.widget.ActionSheetNumberPicker;
import com.homepaas.sls.ui.widget.ActionSheetPhotoPicker;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.ServiceTimePicker;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;
import static com.homepaas.sls.navigation.Navigator.REQUEST_CODE_GET_MY_ADDRESS;
import static com.homepaas.sls.navigation.Navigator.SERVICE_TYPE_LIST_REQUEST_CODE;
import static com.homepaas.sls.ui.order.adapter.Converter.TYPE_INVALID;
import static com.homepaas.sls.ui.order.adapter.Converter.TYPE_NEG;
import static com.homepaas.sls.ui.order.adapter.Converter.TYPE_STABLE;
import static com.homepaas.sls.ui.order.directOrder.AddressManageActivity.MODE_CHOOSE;
import static com.homepaas.sls.ui.order.orderplace.FlatServiceTypeActivity.KEY_TYPE_ENTITY;

public class PlaceOrderActivity extends CommonBaseActivity implements PlaceOrderAdapter.HostAction, ServiceTypeListView, WorkerOrderPlaceView, BusinessOrderPlaceView, ServiceView, ServiceTimePicker.OnServiceTimeSelectListener, GetDescriptionView, ActivityView, AddAddressView, ManageAddressView, OrderPlaceView, QtyView, ActionSheetNumberPicker.OnItemClickListener, WorkerPhoneView, BusinessPhoneView, CallView {

    public static final String TYPE = "providerType";
    public static final String ID = "providerId";
    public static final String GENDER = "providerGender";
    public static final String WORKER_SERVICE="workerService";
    private  WorkerServiceTypePrice workerServiceTypePrice;
    private static final int REQUEST_CODE_MY_NOTES = 0xfea1;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    ActionSheetPhotoPicker photoPicker;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.id_text)
    TextView sumTitle;
    @Bind(R.id.bill_sum)
    TextView sumText;
    PlaceOrderAdapter adapter;
    @Inject
    PlaceOrderPresenter placeOrderPresenter;
    @Inject
    ServiceTypePresenter serviceTypePresenter;
    @Inject
    WorkerInfoPresenter workerInfoPresenter;
    @Inject
    BusinessInfoPresenter businessInfoPresenter;
    @Inject
    ServicePresenter servicePresenter;//获取服务时间表和对应服务的价格
    @Inject
    PlaceOrderParamModel paramModel;
    @Inject
    DescriptionPresenter descriptionPresenter;
    @Inject
    AddressPresenter addressPresenter;
    @Inject
    DetailPresenter detailPresenter;//仅仅是获取电话（并没有获取电话的接口）
    @Inject
    BusinessServicePricePresenter businessServicePricePresenter;
    String providerId;
    int providerType;
    String providerGender;
    @Bind(R.id.favor_result_description)
    TextView favorResultDescription;
    @Bind(R.id.favor_result_layout)
    LinearLayout favorResultLayout;
    private ServiceTimePicker schedulePicker;
    private ActionSheetNumberPicker numberPicker;
    private List<ServiceScheduleEntity> schedule;
    private boolean negotiable;
    private ActivityNgInfoNew currentAction;
    private String price;
    private int dateIndex;
    private int timeIndex;
    private String formatServiceTime;
    private CommonDialog submitDialog;
    private String workerServiceName="";

    private boolean isNegotiable=false;//是否是面议 1：面议 0：定价
    //地址是地址列表中的哪一个
    private int addressPosition = -1;
    public static final String ADDRESS_POSITION = "address_position";
    private boolean hasAddress=false;//是否有地址


    public static void start(Context context, int type, String id) {
        Intent intent = new Intent(context, PlaceOrderActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }

    public static void start(Context context, int type, String id, String gender) {
        Intent intent = new Intent(context, PlaceOrderActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(ID, id);
        intent.putExtra(GENDER, gender);
        context.startActivity(intent);
    }

    public static void start(Context context, int type, String id, String gender,WorkerServiceTypePrice workerServiceTypePrice) {
        Intent intent = new Intent(context, PlaceOrderActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(ID, id);
        intent.putExtra(GENDER, gender);
        intent.putExtra(WORKER_SERVICE,workerServiceTypePrice);
        context.startActivity(intent);
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerPlaceOrderComponent.builder().applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build().inject(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_place_order2;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        setSupportActionBar(toolbar);
        placeOrderPresenter.setOrderPlaceView(this);
        providerId = getIntent().getStringExtra(ID);
        workerServiceTypePrice= (WorkerServiceTypePrice) getIntent().getSerializableExtra(WORKER_SERVICE);
        servicePresenter.setActivityView(this);
        servicePresenter.setDescriptionView(this);
        addressPresenter.setAddressView(this);
        detailPresenter.setWorkerPhoneView(this);
        detailPresenter.setBusinessPhoneView(this);
        detailPresenter.setCallView(this);
        providerType = getIntent().getIntExtra(TYPE, 1);//默认是工人
        providerGender = getIntent().getStringExtra(GENDER);
        init();
    }

    @Override
    protected void initData() {

    }

    public void init() {
        paramModel.setProviderId(providerId);
        paramModel.setProviderType(String.valueOf(providerType));
        paramModel.setProviderGender(providerGender);
        serviceTypePresenter.setServiceTypeListView(this);
        workerInfoPresenter.setWorkerOrderPlaceView(this);
        businessInfoPresenter.setBusinessOrderPlaceView(this);
        addressPresenter.getMyServiceAddressList();
        servicePresenter.setServiceView(this);
        servicePresenter.setQtyView(this);
        adapter = new PlaceOrderAdapter();
        adapter.setHostAction(this);
        adapter.setData(paramModel);
        recyclerView.addItemDecoration(new SimpleItemDecoration(this, SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        if (providerType == Constant.SERVICE_TYPE_WORKER_INT||providerType==Constant.WHOLE_SERVICE_TYPE_WORKER_INT) {
            workerInfoPresenter.getWorkerInfo(providerId);
            serviceTypePresenter.getWorkerServiceTypeList(providerId);
            detailPresenter.getWorkerInfo(providerId);
        } else {
            businessInfoPresenter.getBusinessInfo(providerId);
            serviceTypePresenter.getBusinessServiceList(providerId);
            detailPresenter.getBusinessInfo(providerId);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SERVICE_TYPE_LIST_REQUEST_CODE://获取服务类型列表回调
                    CommonServiceType service = (CommonServiceType) data.getSerializableExtra(KEY_TYPE_ENTITY);
                    workerServiceName=service.getServiceTypeName();
                    String name = service.getServiceTypeName();
                    String serviceTypeId = service.getServiceTypeId();
                    String unitName = service.getUnitName();
                    paramModel.setUnit(unitName);
                    if (TextUtils.equals(service.PriceType, Constant.SERVICE_TYPE_NEG) || TextUtils.isEmpty(service.PriceType)) {
                        paramModel.setMode(TYPE_NEG);
                    } else {
                        paramModel.setMode(TYPE_STABLE);
                        price = service.getPrice();
                        paramModel.setPrice(price);
                    }
                    if (Constant.HOURLY_WORKER.equals(serviceTypeId) || Constant.HOURLY_WORKER_WINDOW.equals(serviceTypeId)) {
                        paramModel.serviceCount = 3f;
                        paramModel.setServiceNumberString("3");
                        servicePresenter.getQty(serviceTypeId);
                    } else paramModel.setServiceNumber(1);
                    schedulePicker = null;
                    paramModel.setServiceName(name);
                    paramModel.setServiceTypeId(serviceTypeId);
                    servicePresenter.getSchedule(serviceTypeId);
                    paramModel.setCurrentAction(null);
                    servicePresenter.getAvailableActivity(serviceTypeId);
                    servicePresenter.getServiceDescription(serviceTypeId);
                    adapter.notifyDataSetChanged();
                    calculateSum();
                    adapter.notifyItemChanged(2);
                    break;
                case REQUEST_CODE_GET_MY_ADDRESS:
                    String keyType = data.getStringExtra(AddressManageActivity.KEY_TYPE);
                    addressPosition = data.getIntExtra(AddressManageActivity.KEY_POSITION, -1);
                    if (TextUtils.equals(keyType, "0")) {
                        addressPresenter.getMyServiceAddressList();
                    } else {
                        AddressEntity entity = (AddressEntity) data.getSerializableExtra(AddressManageActivity.KEY_ADDRESS);
                        if (entity != null) {
                            paramModel.setAddress(entity);
                            adapter.notifyItemChanged(0);
                            hasAddress=true;
                        }
                    }
                    break;
                case REQUEST_CODE_MY_NOTES:
                    String notes = data.getStringExtra("Notes");
                    ArrayList<String> paths = data.getStringArrayListExtra("Photos");
                    paramModel.setPhotoPath(paths);
                    paramModel.setNote(notes);
                    adapter.notifyItemChanged(5);
                    break;
                default:
            }
        }
    }
    private CommonDialog addAddressDialog;


    /**
     * 获取服务类别
     */
    @Override
    public void startFetchServiceType() {
        if (!hasAddress) {
            if (addAddressDialog == null) {
                addAddressDialog = new CommonDialog.Builder()
                        .setContent("请先设置服务地址？")
                        .setCancelButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addAddressDialog.dismiss();
                            }
                        }).setConfirmButton("去设置", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(PlaceOrderActivity.this, AddressManageActivity.class);
                                intent.putExtra("Mode", MODE_CHOOSE);
                                intent.putExtra(ADDRESS_POSITION, -1);
                                startActivityForResult(intent, REQUEST_CODE_GET_MY_ADDRESS);
                                addAddressDialog.dismiss();
                            }
                        }).showTitle(false).create();
            }
            addAddressDialog.show(getSupportFragmentManager(), null);
        } else {
            paramModel.setMode(TYPE_INVALID);
            mNavigator.serviceType(this, type(), providerId, workerServiceName);
        }
    }

    private String type() {
        if(providerType == Constant.SERVICE_TYPE_WORKER_INT||providerType==Constant.WHOLE_SERVICE_TYPE_WORKER_INT){
            return String.valueOf(2);
        }else {
            return String.valueOf(3);
        }
    }

    @Override
    public void startHostAddPhoto() {
        if (photoPicker == null) {
            photoPicker = ActionSheetPhotoPicker.newInstance();
            photoPicker.setOnPictureChoseListener(new ActionSheetPhotoPicker.OnPictureChoseListener() {
                @Override
                public void onPictureChose(File filePath) {
                    paramModel.getPhotoPath().add(filePath.getAbsolutePath());
                    adapter.notifyItemChanged(5);
                }
            });
        }
        if (paramModel.getPhotoPath().size() >= 8) {
            showMessage("至多选择8张照片");
            return;
        }
        photoPicker.show(this);
    }

    @Override
    public void startPickServiceTime() {
        if (schedulePicker != null)
            schedulePicker.show(this);
    }

    @Override
    public void startShiftAddress() {
        Intent intent = new Intent(this, AddressManageActivity.class);
        intent.putExtra("Mode", MODE_CHOOSE);
        intent.putExtra(ADDRESS_POSITION, addressPosition);
        startActivityForResult(intent, REQUEST_CODE_GET_MY_ADDRESS);
    }

    @Override
    public void onServiceNumberChange() {
        calculateSum();
    }

    @Override
    public void startSelectServiceNumber() {
//        adapter.notifyItemChanged(BLOCK_SERVNUMBER);
        if (Constant.HOURLY_WORKER.equals(paramModel.getServiceTypeId()) || Constant.HOURLY_WORKER_WINDOW.equals(paramModel.getServiceTypeId()))
            numberPicker.show(this);
    }

    @Override
    public void startAddNote() {
        Intent intent = new Intent(this, OrderNoteActivity.class);
        intent.putStringArrayListExtra("Photos", (ArrayList<String>) paramModel.getPhotoPath());
        intent.putExtra("Notes", paramModel.getNote());
        startActivityForResult(intent, REQUEST_CODE_MY_NOTES);
    }

    @Override
    public void startDial(String phoneNumber) {
        detailPresenter.attemptCall(providerId, phoneNumber);
    }

    /**
     * 计算总价
     */
    private void calculateSum() {
        if (paramModel.getMode() == TYPE_NEG) {
            if (TextUtils.isEmpty(paramModel.getStartingPrice())) {
                sumTitle.setText("总计");
                sumText.setText("面议");
            } else {
                sumTitle.setText("总计");
                sumText.setText("¥" + cutUnnecessaryPrecision(paramModel.getStartingPrice()) + "起");
            }
            paramModel.setSum(0);
        }
        if (paramModel.getMode() == TYPE_INVALID) {
            sumTitle.setText("总计");
            sumText.setText("---");
            paramModel.setSum(0);
        }
        boolean isHourlyWorker = Constant.HOURLY_WORKER.equals(paramModel.getServiceTypeId()) || Constant.HOURLY_WORKER_WINDOW.equals(paramModel.getServiceTypeId());
        if (paramModel.getMode() == TYPE_STABLE) {
            BigDecimal value = new BigDecimal(price);
            BigDecimal number = new BigDecimal(paramModel.getServiceNumber());
            BigDecimal hourlyNumber = new BigDecimal(paramModel.serviceCount);
            paramModel.setSum(value.multiply(isHourlyWorker ? hourlyNumber : number).doubleValue());
            String resultStr = "¥ " + cutUnnecessaryPrecision(String.valueOf(paramModel.sum));
            sumTitle.setText("总计");
            sumText.setText(resultStr);
            if (currentAction != null && currentAction.isSpecialSatisfied((float) paramModel.sum)) {
                favorResultLayout.setVisibility(View.VISIBLE);
                List<ActivityNgInfoNew.ActivityNgDetail> satisifiedActivity = currentAction.getSatisfiedSpecialActivityList();
                String minus = "0";
                BigDecimal sumDecimal = new BigDecimal(paramModel.sum).setScale(2, RoundingMode.HALF_UP);
                BigDecimal minusDecimal = new BigDecimal(minus).setScale(2, RoundingMode.HALF_UP);
                for (ActivityNgInfoNew.ActivityNgDetail activityNgDetail : satisifiedActivity){
                    String minuss = activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus();
                    BigDecimal newminusDecimal = new BigDecimal(minuss).setScale(2, RoundingMode.HALF_UP);
                    minusDecimal = minusDecimal.add(newminusDecimal);
                }

                favorResultDescription.setText("已优惠¥" + String.valueOf(minusDecimal.floatValue()));
                sumText.setText("¥" + cutUnnecessaryPrecision(String.valueOf(sumDecimal.subtract(minusDecimal).floatValue())));
            } else
                favorResultLayout.setVisibility(View.GONE);

        }
        adapter.notifyItemChanged(3);
    }


    //默认选中该工人的第一项服务
    @Override
    public void renderWorker(WorkerServiceTypeInfo serviceTypeListInfo) {
        if (serviceTypeListInfo == null)
            return;
        WorkerServiceType workerServiceType=serviceTypeListInfo.getList().get(0);
        if(workerServiceTypePrice!=null){
            for(WorkerServiceType workerServiceTypeChild:serviceTypeListInfo.getList()){
                if(TextUtils.equals(workerServiceTypeChild.getServiceTypeId(),workerServiceTypePrice.getId())){
                    workerServiceType=workerServiceTypeChild;
                    break;
                }
            }
        }else{
            workerServiceType = serviceTypeListInfo.getList().get(0);
        }
        workerServiceName=workerServiceType.getServiceTypeName();
        paramModel.setUnit(workerServiceType.getUnitName());
        if (TextUtils.equals(Constant.SERVICE_TYPE_NEG, workerServiceType.getPriceType())) {
            paramModel.setMode(TYPE_NEG);
            isNegotiable=true;
        } else {
            paramModel.setMode(TYPE_STABLE);
            price = workerServiceType.getPrice();
            isNegotiable=false;
        }
        paramModel.setPrice(price);
        paramModel.setServiceName(workerServiceType.getServiceTypeName());
        paramModel.setServiceTypeId(workerServiceType.getServiceTypeId());
        paramModel.setStartingPrice(workerServiceType.getStartingPrice());
        boolean isHourlyWorker = Constant.HOURLY_WORKER.equals(paramModel.getServiceTypeId()) || Constant.HOURLY_WORKER_WINDOW.equals(paramModel.getServiceTypeId());
        if (isHourlyWorker) {
            paramModel.setServiceNumberString("3");
        } else {
            paramModel.setServiceNumberString("1");
            paramModel.setServiceNumber(1);
        }
        adapter.notifyDataSetChanged();
        if (Constant.HOURLY_WORKER.equals(workerServiceType.getServiceTypeId()) || Constant.HOURLY_WORKER_WINDOW.equals(workerServiceType.getServiceTypeId())) {
            paramModel.serviceCount = 3f;
            servicePresenter.getQty(workerServiceType.getServiceTypeId());
        }
        servicePresenter.getServiceDescription(workerServiceType.getServiceTypeId());
        servicePresenter.getSchedule(workerServiceType.getServiceTypeId());
        servicePresenter.getAvailableActivity(serviceTypeListInfo.getList().get(0).getServiceTypeId());
    }

    @Override
    public void renderBusiness(BusinessServiceTypeInfo serviceTypeListInfo) {
        if (serviceTypeListInfo == null)
            return;
        BusinessServiceType businessServiceType = serviceTypeListInfo.getList().get(0);
        paramModel.setServiceName(businessServiceType.getServiceTypeName());
        paramModel.setServiceTypeId(businessServiceType.getServiceTypeId());
        servicePresenter.getSchedule(businessServiceType.getServiceTypeId());
        servicePresenter.getAvailableActivity(serviceTypeListInfo.getList().get(0).getServiceTypeId());
        adapter.notifyItemChanged(2);
    }


    /**
     * 返回头像，姓名，等信息
     *
     * @param workerInfoModel
     */
    @Override
    public void render(WorkerCollectionEntity workerInfoModel) {
        paramModel.setProviderName(workerInfoModel.getName());
        paramModel.setGetProviderPic(workerInfoModel.getPhoto());
        paramModel.setProviderGender(workerInfoModel.getGender());
        adapter.notifyItemChanged(0);
    }

    @Override
    public void render(BusinessInfoModel businessInfoModel) {
        paramModel.setProviderName(businessInfoModel.getName());
        paramModel.setGetProviderPic(businessInfoModel.getPhoto());
        adapter.notifyItemChanged(0);
    }


    @Override
    public void onPriceResult(PriceEntity price) {

    }

    @Override
    public void onScheduleResult(List<ServiceScheduleEntity> sch) {
            schedule = sch;
            if (sch != null && !sch.isEmpty())
                schedulePicker = new ServiceTimePicker.Builder()
                        .left(sch)
                        .right(sch.get(0).getTimeList())
                        .build();
            schedulePicker.setListener(this);
    }


    @Override
    public void onConfirmServiceTime(int dPos, int tPos) {
        if (dPos != -1 && tPos != -1) {
            {
                paramModel.setServiceTimeStart(TimeUtils.formatSmartTime(schedule.get(dPos).getDate()) + schedule.get(dPos).getTimeSchedule().get(tPos));
                adapter.notifyItemChanged(2);
                dateIndex = dPos;
                timeIndex = tPos;
            }
        } else paramModel.setServiceTimeStart(null);
        adapter.notifyItemChanged(2);
    }

    @OnClick(R.id.submit)
    public void submitOrder() {
        if (validateParam(paramModel)) {
            submitDialog = new CommonDialog.Builder()
                    .setContent("请您确认服务信息填写正确再提交订单")
                    .showTitle(false)
                    .setConfirmButton("我已确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean equals = Constant.HOURLY_WORKER.equals(paramModel.getServiceTypeId()) || Constant.HOURLY_WORKER_WINDOW.equals(paramModel.getServiceTypeId());
                            placeOrderPresenter.placeOrder(paramModel.getPhotoPath(), type(), paramModel.getProviderId(), paramModel.getServiceTypeId(), paramModel.getNote(), String.valueOf(equals ? paramModel.serviceCount : paramModel.getServiceNumber()), Constant.ANDROID_TYPE, getFormatServiceTime(), paramModel.getAddress().getId());
                        }
                    }).setCancelButton("我再想想", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).create();
            submitDialog.show(getSupportFragmentManager(), null);
        }
    }

    private boolean validateParam(PlaceOrderParamModel paramModel) {
        if (TextUtils.isEmpty(paramModel.getServiceTypeId())) {
            showMessage("请选择服务类别");
            return false;
        }
        if (paramModel.getAddress() == null || paramModel.getAddress().getId() == null) {
            showMessage("请选择服务地址");
            return false;
        }
        if (paramModel.getServiceTimeStart() == null) {
            showMessage("请选择服务时间");
            return false;
        }
        return true;
    }

    @Override
    public void renderDescription(String description) {
        adapter.setDescriptionStr(description);
    }

    /**
     * 可参与的活动回调
     *
     * @param actionEntity
     */
    @Override
    public void renderActivity(ActivityNgInfoNew actionEntity) {
        currentAction = actionEntity;
        paramModel.setCurrentAction(actionEntity);
        calculateSum();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddressSaveSuccess() {

    }

    /**
     * 获取到服务地址
     *
     * @param addressCollection
     */
    @Override
    public void renderAddress(List<AddressEntity> addressCollection) {
        if (addressCollection != null && !addressCollection.isEmpty()) {
            paramModel.setAddress(addressCollection.get(0));
            adapter.notifyItemChanged(0);
            addressPosition = 0;
            hasAddress=true;
        }else{
            paramModel.setAddress(null);
            adapter.notifyItemChanged(0);
            addressPosition = -1;
            hasAddress=false;
        }
    }

    @Override
    public void deleteSuccess(int delIndex) {

    }

    /**
     * 订单创建成功
     *
     * @param orderId
     */
    @Override
    public void onOrderCreate(String orderId,String serviceTypeId) {
        if (orderId != null) {
            if(isNegotiable){
                ClientOrderDetailActivity.start(this, orderId);
            }else{
                Intent intent = new Intent(this, PayActivity.class);
                intent.putExtra(com.homepaas.sls.ui.order.Constant.OrderId, orderId);//传递
                intent.putExtra(StaticData.ConfirmGO,"0");
                startActivity(intent);
            }
            ActivityCompat.finishAfterTransition(this);
        } else {
            showMessage(getString(R.string.network_error));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onQtyResult(List<String> datas) {
        if (numberPicker == null) {
            numberPicker = ActionSheetNumberPicker.newInstance();
            numberPicker.setData(datas);
            numberPicker.setOnItemClickListener(this);
        }
//        int position = convertViewTypeToPosition(Converter.BLOCK_SERVNUMBER);
//        if (position != -1)
        adapter.notifyItemChanged(2);
    }

    @Override
    public void onItemClick(String str) {
        paramModel.serviceCount = Float.parseFloat(str);
        paramModel.setServiceNumberString(str);
//        int i = convertViewTypeToPosition(Converter.BLOCK_SERVNUMBER);
//        if (i != -1)
        adapter.notifyDataSetChanged();
        calculateSum();
    }

    public String getFormatServiceTime() {
        ServiceScheduleEntity scheduleEntity = schedule.get(dateIndex);
        String date = scheduleEntity.getDate();
        int end = date.indexOf("今") + date.indexOf("明") + date.indexOf("周") + 2;
        String subDate = date.substring(0, end);
        return subDate + " " + scheduleEntity.getTimeSchedule().get(timeIndex);
    }

    @Override
    public void renderBusinessPhoneView(String phoneNumber, boolean callable) {
        paramModel.setPhoneNumber(phoneNumber);
        adapter.notifyItemChanged(1);
    }

    /**
     * 获取到工人电话回调
     *
     * @param phoneNumber
     * @param callable
     */
    @Override
    public void renderWorkerPhoneNumber(String phoneNumber, boolean callable) {
        paramModel.setPhoneNumber(phoneNumber);
        adapter.notifyItemChanged(1);
    }

    @Override
    public void callIfEnable(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }


    /**
     * 针对从下单页面到服务地址选择页面把服务地址删空的情况，此时下单页面已经选择的服务地址将会失效，刷新
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressClear(EmptyAddressEvent event) {
        paramModel.setAddress(null);
        adapter.notifyItemChanged(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        placeOrderPresenter.destroy();
        serviceTypePresenter.destroy();
        workerInfoPresenter.destroy();
        businessInfoPresenter.destroy();
        servicePresenter.destroy();//获取服务时间表和对应服务的价格
        descriptionPresenter.destroy();
        addressPresenter.destroy();
        detailPresenter.destroy();
    }



    /**
     * 登录后重新获取数据
     */
    @Override
    protected void retrieveData() {
        super.retrieveData();
        addressPresenter.getMyServiceAddressList();
        if (providerType == Constant.SERVICE_TYPE_WORKER_INT||providerType==Constant.WHOLE_SERVICE_TYPE_WORKER_INT) {
            workerInfoPresenter.getWorkerInfo(providerId);
            serviceTypePresenter.getWorkerServiceTypeList(providerId);
            detailPresenter.getWorkerInfo(providerId);
        } else {
            businessInfoPresenter.getBusinessInfo(providerId);
            serviceTypePresenter.getBusinessServiceList(providerId);
            detailPresenter.getBusinessInfo(providerId);
        }
    }
}
