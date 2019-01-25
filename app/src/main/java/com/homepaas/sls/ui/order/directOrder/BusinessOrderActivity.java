package com.homepaas.sls.ui.order.directOrder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.component.PlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.BusinessServiceType;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.MerchantServiceTypePrice;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.event.EmptyAddressEvent;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.presenter.ServicePresenter;
import com.homepaas.sls.mvp.presenter.addressmanage.AddressPresenter;
import com.homepaas.sls.mvp.presenter.order.BusinessInfoPresenter;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.mvp.presenter.order.PlaceOrderPresenter;
import com.homepaas.sls.mvp.presenter.order.ServiceTypePresenter;
import com.homepaas.sls.mvp.view.GetDescriptionView;
import com.homepaas.sls.mvp.view.QtyView;
import com.homepaas.sls.mvp.view.ServiceView;
import com.homepaas.sls.mvp.view.addressmanage.ManageAddressView;
import com.homepaas.sls.mvp.view.order.ActivityView;
import com.homepaas.sls.mvp.view.order.BusinessOrderPlaceView;
import com.homepaas.sls.mvp.view.order.OrderPlaceView;
import com.homepaas.sls.mvp.view.order.ServiceTypeListView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.adapter.AddPhotoAdapter;
import com.homepaas.sls.ui.order.directOrder.adapter.ActionListAdapter;
import com.homepaas.sls.ui.order.directOrder.adapter.SatisifiedActionAdapter;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.widget.ActionSheetNumberPicker;
import com.homepaas.sls.ui.widget.ActionSheetPhotoPicker;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.ui.widget.ServiceTimePicker;
import com.homepaas.sls.util.StaticData;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.homepaas.sls.common.Util.cutUnnecessaryPrecision;
import static com.homepaas.sls.navigation.Navigator.BUSINESS_SERVICE_TYPE_LIST_REQUEST_CODE;
import static com.homepaas.sls.ui.order.directOrder.AddressManageActivity.MODE_CHOOSE;
import static com.homepaas.sls.ui.order.orderplace.FlatServiceTypeActivity.KEY_TYPE_ENTITY;

/**
 * 商户下单(确认订单)
 */
public class BusinessOrderActivity extends CommonBaseActivity implements ServiceTimePicker.OnServiceTimeSelectListener, ServiceView, HasComponent<PlaceOrderComponent>, ActivityView, AddPhotoAdapter.AddPhotoListener, OrderPlaceView, QtyView, ActionSheetNumberPicker.OnItemClickListener, GetDescriptionView, ManageAddressView, ActionSheetPhotoPicker.OnPictureChoseListener, BusinessOrderPlaceView, ServiceTypeListView {

    private static final int REQUESTCODE_SERVICE = 0xff09;
    private static final String KEY_SERVICEINFO = "Serviceinfo";
    public static final int BUSIINESS_REQUEST_CODE_SERVICE_TYPE = 0xff02;
    public static final int REQUEST_CODE_ADDRESS = 0xff03;
    private static final int REQUEST_CODE_NOTE = 0xff04;
    public static final int REQUEST_CODE_PHOTO = 0xff05;
    public static final int SERVICE_TYPE_LIST_REQUEST_CODE = 0x00101;
    private static final String MINIMAL_HOURLY_WORKER_DURATION = "3";//默认小时工最低服务时长
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.address_icon)
    TextView addressIcon;
    @Bind(R.id.address_info)
    TextView address;
    @Bind(R.id.remark_text)
    TextView remarkText;
    @Bind(R.id.unit)
    TextView unit;
    @Bind(R.id.id_anchor)
    RelativeLayout idAnchor;
    @Bind(R.id.line)
    View line;
    @Bind(R.id.minus_action)
    ImageView minusAction;
    @Bind(R.id.service_number)
    TextView serviceNumber;
    @Bind(R.id.plus_action)
    ImageView plusAction;
    int serviceCountInt = 1;
    float serviceCountFloat = 0.0f;
    @Bind(R.id.service_time_option)
    RelativeLayout serviceTimeOption;
    @Bind(R.id.service_type)
    TextView serviceType;
    @Bind(R.id.album)
    RecyclerView album;
    @Bind(R.id.id_text)
    TextView sumTitle;
    @Bind(R.id.bill_sum)
    TextView billSum;
    @Bind(R.id.service_time_text)
    TextView serviceTimeText;
    LinearLayout activityBox;
    @Bind(R.id.special_favor_title)
    TextView specialFavorTitle;
    @Bind(R.id.special_favor_help)
    ImageView specialFavorHelp;
    @Bind(R.id.textView8)
    TextView textView8;
    @Bind(R.id.special_activity_satisfied_cell)
    LinearLayout specialActivitySatisfiedCell;
    @Bind(R.id.promotion_name)
    TextView promotionName;
    @Bind(R.id.promotion_hint_help)
    ImageView promotionHintHelp;
    @Bind(R.id.promotion_activity_layout)
    LinearLayout promotionActivityLayout;
    @Bind(R.id.promotion_action_text)
    TextView promotionTitle;
    @Bind(R.id.special_title)
    TextView specialTitle;
    @Bind(R.id.special_hint_help)
    ImageView specialHintHelp;
    @Bind(R.id.special_hint_layout)
    LinearLayout specialHintLayout;
    @Bind(R.id.textView7)
    TextView textView7;
    @Bind(R.id.order_total_price)
    TextView orderTotalPrice;
    @Bind(R.id.order_favor_price)
    TextView orderFavorPrice;
    @Bind(R.id.order_topay_price)
    TextView orderTopayPrice;
    @Bind(R.id.order_favor_layout)
    LinearLayout orderFavorLayout;
    @Bind(R.id.service_description)
    TextView serviceDescription;
    @Bind(R.id.range_layout)
    LinearLayout rangeLayout;
    @Bind(R.id.service_price_layout)
    RelativeLayout servicePriceLayout;
    @Bind(R.id.price_info)
    TextView priceInfo;
    @Bind(R.id.submit_order)
    TextView submitOrder;
    @Bind(R.id.suitable_service_price)
    TextView suitableServicePrice;
    @Bind(R.id.service_number_layout)
    RelativeLayout serviceNumberLayout;
    @Bind(R.id.sum_description)
    TextView sumDescription;
    @Bind(R.id.favor_result_description)
    TextView favorResultDescription;
    @Bind(R.id.action_collection)
    RecyclerView actionCollection;
    @Bind(R.id.activity_wrapper)
    LinearLayout activityWrapper;
    //    @Bind(R.id.available_action_description)
//    TextView availableActionDescription;
//    @Bind(R.id.discount_description)
//    TextView discountDescription;
    @Bind(R.id.sum)
    TextView discountedSum;
    @Bind(R.id.discount_sum_layout)
    LinearLayout discountSumLayout;
    @Bind(R.id.favor_result_layout)
    LinearLayout favorResultLayout;
    @Bind(R.id.special_space)
    View specialSpace;

    @Bind(R.id.provider_avatar)
    RoundedImageView roundedImageView;
    @Bind(R.id.provider_name)
    TextView providerName;
    @Bind(R.id.icon_phone)
    ImageView iconPhone;
    @Bind(R.id.action_special)
    RecyclerView actionSpecial;

    private List<ServiceScheduleEntity> dateDatas;
    private List<String> timeDatas;
    @Inject
    ServicePresenter servicePresenter;
    @Inject
    PlaceOrderPresenter placeOrderPresenter;
    @Inject
    OrderPresenter orderPresenter;
    private ServiceTimePicker serviceTimePicker;
    @Inject
    AddressPresenter addressPresenter;
    PlaceOrderComponent component;
    private String serviceTypeId;
    private List<ServiceScheduleEntity> schedule;
    private ServiceTimePicker schedulePicker;
    private String serviceTypeName;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ActionSheetPhotoPicker photoPicker;
    private int dateIndex = -1;
    private int timeIndex = -1;
    private String addressId;

    public static final int MODE_RANGE = 0;//区间价格
    public static final int MODE_NEG = 1;//面议
    public static final int MODE_STABLE = 2;//固定价格
    public static final int MODE_INVALID = -1;//无效议价方式
    private PriceEntity priceModel;
    private AddPhotoAdapter photoAdapter;
    private ActivityNgInfoNew currentAction;
    private CommonDialog specialHelpDialog;
    private CommonDialog promotionHelpDialog;
    private double minSum;
    private double maxSum;
    private String selectedPrice;//区间价格情况下，客户选择的价格
    private boolean priceReady;//价格是否获取成功
    private boolean scheduleReady;//服务时间表是否获取成功，针对h5页面跳过来需要登录的情况，登录成功后根据该字段再次获取对应的信息
    private ActionSheetNumberPicker numberPicker;
    private boolean hasEverLogin = false;
    private ActionSheetNumberPicker pricePicker;
    //地址是地址列表中的哪一个
    private int addressPosition = -1;
    public static final String ADDRESS_POSITION = "address_position";
    //选择服务获取返回的position，字体变蓝色
    public static final String KEY_SELECT_POSITION = "key_select_position";

    private int selectPosition = 0;


    private boolean isNegotiable=false;//是否是面议 1：面议 0：定价
    //选择服务价格
    ActionSheetNumberPicker.OnItemClickListener onPriceItemClickListener = new ActionSheetNumberPicker.OnItemClickListener() {
        @Override
        public void onItemClick(String str) {
            selectedPrice = str;
            suitableServicePrice.setText("¥" + cutUnnecessaryPrecision(selectedPrice) + "/" + priceModel.getUnitName());
            serviceNumberLayout.setVisibility(VISIBLE);
            calculateSum();
        }
    };
    private CommonDialog submitDialog;
    private ActionListAdapter actionAdapter;


    public static void start(Context context, int type, String id) {
        Intent intent = new Intent(context, BusinessOrderActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }

    public static void start(Context context, int type, String id, MerchantServiceTypePrice merchantServiceTypePrice) {
        Intent intent = new Intent(context, BusinessOrderActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(ID, id);
        intent.putExtra(MERCHANT_SERVICE, merchantServiceTypePrice);
        context.startActivity(intent);
    }

    @Override
    public void renderDescription(String description) {
        if (description != null)
            serviceDescription.setText("服务说明\n" + description);
    }

    private List<AddressEntity> addressCollection;

    @Override
    public void renderAddress(List<AddressEntity> addressCollection) {
        this.addressCollection = addressCollection;
        if (addressCollection != null && !addressCollection.isEmpty()) {
            setAddress(addressCollection.get(0));
            addressPosition = 0;
            serviceTypePresenter.getBusinessServiceList(providerId);
        } else {
            address.setText("");
        }
    }

    @Override
    public void deleteSuccess(int delIndex) {

    }

    @Override
    public void onPictureChose(File filePath) {
        album.setVisibility(VISIBLE);
        photoPaths.add(filePath.getAbsolutePath());
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void renderWorker(WorkerServiceTypeInfo serviceTypeListInfo) {

    }

    private BusinessServiceTypeInfo serviceTypeListInfo;

    @Override
    public void renderBusiness(BusinessServiceTypeInfo serviceTypeListInfo) {
        this.serviceTypeListInfo = serviceTypeListInfo;
        if (serviceTypeListInfo != null) {
            if (serviceTypeListInfo.getList().size() > 0) {
                BusinessServiceType serviceTypeEx = serviceTypeListInfo.getList().get(0);
                if (merchantServiceTypePrice != null) {
                    for (BusinessServiceType serviceTypeExChile : serviceTypeListInfo.getList()) {
                        if (TextUtils.equals(serviceTypeExChile.getServiceTypeId(), merchantServiceTypePrice.getId())) {
                            serviceTypeEx = serviceTypeExChile;
                            break;
                        }
                    }
                } else {
                    serviceTypeEx = serviceTypeListInfo.getList().get(0);
                }
                serviceTypeId = serviceTypeEx.getServiceTypeId();
                serviceTypeName = serviceTypeEx.getServiceTypeName();
                reset();
                if (Constant.HOURLY_WORKER.equals(serviceTypeId) || Constant.HOURLY_WORKER_WINDOW.equals(serviceTypeId)) {
                    serviceNumber.setText(MINIMAL_HOURLY_WORKER_DURATION);
                    servicePresenter.getQty(serviceTypeId);
                } else {
                    minusAction.setVisibility(VISIBLE);
                    plusAction.setVisibility(VISIBLE);
                    serviceNumber.setText(String.valueOf(serviceCountInt));
                }
                favorResultLayout.setVisibility(GONE);
                if (!TextUtils.isEmpty(serviceTypeId)&&!TextUtils.isEmpty(addressId)) {
                    servicePresenter.getServicePrice(serviceTypeId, "3", addressId);
                }
                servicePresenter.getSchedule(serviceTypeId);
                servicePresenter.getServiceDescription(serviceTypeId);
            }
        }
    }


    @IntDef({MODE_RANGE, MODE_NEG, MODE_STABLE, MODE_INVALID})
    public @interface MODE {
    }

    private int mode = MODE_INVALID;
    private double sum = -1;

    String providerId;
    int providerType;
    private MerchantServiceTypePrice merchantServiceTypePrice;
    public static final String TYPE = "providerType";
    public static final String ID = "providerId";
    public static final String MERCHANT_SERVICE = "merchantService";

    @Inject
    BusinessInfoPresenter businessInfoPresenter;

    @Inject
    ServiceTypePresenter serviceTypePresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_order;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        providerId = getIntent().getStringExtra(ID);
        providerType = getIntent().getIntExtra(TYPE, 1);//默认是工人
        merchantServiceTypePrice = (MerchantServiceTypePrice) getIntent().getSerializableExtra(MERCHANT_SERVICE);
        servicePresenter.setServiceView(this);
        servicePresenter.setQtyView(this);
        servicePresenter.setActivityView(this);
        servicePresenter.setDescriptionView(this);
        placeOrderPresenter.setOrderPlaceView(this);
        addressPresenter.setAddressView(this);
        businessInfoPresenter.setBusinessOrderPlaceView(this);
        serviceTypePresenter.setServiceTypeListView(this);
        addressPresenter.getMyServiceAddressList();
        businessInfoPresenter.getBusinessInfo(providerId);
        init();
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        component = DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this)).build();
        component.inject(this);
    }

    public void init() {
        photoAdapter = new AddPhotoAdapter();
        album.setLayoutManager(new GridLayoutManager(this, 4));
        photoAdapter.setPaths(photoPaths);
        photoAdapter.setPhotoListener(this);
        album.setAdapter(photoAdapter);
        minusAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceCountInt = serviceCountInt > 1 ? --serviceCountInt : serviceCountInt;
                serviceNumber.setText(String.valueOf(serviceCountInt));
                if (serviceCountInt == 1) minusAction.setImageResource(R.mipmap.reduce2);
                else minusAction.setImageResource(R.mipmap.reduce1);
                calculateSum();
            }
        });
        plusAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceCountInt++;
                serviceNumber.setText(String.valueOf(serviceCountInt));
                if (serviceCountInt == 1) minusAction.setImageResource(R.mipmap.reduce2);
                else minusAction.setImageResource(R.mipmap.reduce1);
                calculateSum();
            }
        });
        rangeLayout.setVisibility(GONE);
        actionAdapter = new ActionListAdapter();
        actionCollection.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        actionCollection.setAdapter(actionAdapter);
    }

    private CommonDialog addAddressDialog;

    @OnClick({R.id.service_type, R.id.service_price_layout, R.id.minus_action, R.id.plus_action, R.id.service_time_option, R.id.address_info, R.id.icon_phone})
    public void clickOptionItem(View view) {
        switch (view.getId()) {
            case R.id.service_type://选择服务类型
                if (address.getText().toString().isEmpty()) {
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
                                        Intent intent = new Intent(BusinessOrderActivity.this, AddressManageActivity.class);
                                        intent.putExtra("Mode", MODE_CHOOSE);
                                        intent.putExtra(ADDRESS_POSITION, -1);
                                        startActivityForResult(intent, REQUEST_CODE_ADDRESS);
                                        addAddressDialog.dismiss();
                                    }
                                }).showTitle(false).create();
                    }
                    addAddressDialog.show(getSupportFragmentManager(), null);
                } else {
                    mNavigator.serviceBusinessType(this, serviceTypeListInfo, providerId, entity.getId(), selectPosition, businessInfoModel.getName());
                }
                break;
            case R.id.service_price_layout:
                if (pricePicker != null)
                    pricePicker.show(this);
                break;
            case R.id.minus_action://减少服务数量
                if (mode == MODE_INVALID) {
                    showMessage("请先选择服务类别");
                    return;
                }
                serviceNumber.setText(String.valueOf(serviceCountInt));
                calculateSum();
                break;
            case R.id.plus_action://增加服务数量
                if (mode == MODE_INVALID) {
                    showMessage("请先选择服务类别");
                    return;
                }
                serviceCountInt++;
                if (serviceCountInt == 1) minusAction.setImageResource(R.mipmap.reduce2);
                else minusAction.setImageResource(R.mipmap.reduce1);
                serviceNumber.setText(String.valueOf(serviceCountInt));
                calculateSum();
                break;
            case R.id.service_time_option://选择服务时间
                if (schedule == null || schedule.isEmpty()) {
                    Toast.makeText(this, "请选择服务类型", Toast.LENGTH_SHORT);
                }
                if (schedule != null && !schedule.isEmpty()) {
                    schedulePicker = new ServiceTimePicker.Builder()
                            .left(schedule)
                            .right(schedule.get(0).getTimeList())
                            .build();
                    schedulePicker.setListener(this);
                    schedulePicker.show(this);
                }
                break;
            case R.id.address_info:
                Intent intent = new Intent(this, AddressManageActivity.class);
                intent.putExtra("Mode", MODE_CHOOSE);
                intent.putExtra(ADDRESS_POSITION, addressPosition);
                startActivityForResult(intent, REQUEST_CODE_ADDRESS);
                break;
            case R.id.icon_phone:
                dial(businessInfoModel.getPhoneNumber(), "联系客户");
                break;
        }
    }
    private List<ActivityNgInfoNew.ActivityNgDetail> satisfiedSpecialActivityList;
    private SatisifiedActionAdapter satisifiedActionAdapter;
    private void transformActivityLayout(float sum) {
        discountSumLayout.setVisibility(VISIBLE);
        if (currentAction != null && currentAction.isSpecialSatisfied(sum)) {
            satisfiedSpecialActivityList = currentAction.getSatisfiedSpecialActivityList();
            actionSpecial.setVisibility(VISIBLE);
            if (satisifiedActionAdapter == null){
                satisifiedActionAdapter = new SatisifiedActionAdapter();
                actionSpecial.setAdapter(satisifiedActionAdapter);
            } else {
                actionSpecial.getAdapter();
            }
            satisifiedActionAdapter.setData(satisfiedSpecialActivityList);
            String minus = "0";
            BigDecimal sumDecimal = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
            BigDecimal minusDecimal = new BigDecimal(minus).setScale(2, RoundingMode.HALF_UP);
            for (ActivityNgInfoNew.ActivityNgDetail activityNgDetail : satisfiedSpecialActivityList){
                String minuss = activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus();
                BigDecimal newminusDecimal = new BigDecimal(minuss).setScale(2, RoundingMode.HALF_UP);
                minusDecimal = minusDecimal.add(newminusDecimal);
            }
            String favorSumNumber = cutUnnecessaryPrecision(String.valueOf(sumDecimal.subtract(minusDecimal).floatValue()));
            discountedSum.setText("¥" + favorSumNumber);
            billSum.setText("¥" + favorSumNumber);
            favorResultLayout.setVisibility(VISIBLE);
            favorResultDescription.setText("已优惠" + "¥" + String.valueOf(minusDecimal));
        } else {
            favorResultLayout.setVisibility(GONE);
            actionSpecial.setVisibility(GONE);
//            availableActionDescription.setVisibility(GONE);
//            discountDescription.setVisibility(GONE);
            discountedSum.setText("¥" + String.valueOf(sum));
            favorResultDescription.setText("");
        }
    }

    /**
     * 计算订单的最新价格，服务有面议、固定价格和区间价之分
     */
    private void calculateSum() {
        serviceNumberLayout.setVisibility(VISIBLE);
        if (mode == MODE_NEG) {
            if (TextUtils.isEmpty(priceModel.getStartingPrice())) {
                //没有有起始价格
                sumTitle.setText("订单总价");
                billSum.setText("面议");
            } else {
                sumTitle.setText("订单总价");
                billSum.setText("¥" + cutUnnecessaryPrecision(priceModel.getStartingPrice()) + "起");
            }
            actionAdapter.setSum(0);
//            transformActivityLayout(0);
            serviceNumberLayout.setVisibility(GONE);
            discountSumLayout.setVisibility(GONE);
        } else if (mode == MODE_RANGE) {
            float count = Float.valueOf(serviceNumber.getText().toString().trim());
            if (selectedPrice == null) {//没有选择服务价格
                double minPrice = Double.parseDouble(priceModel.getMin());
                double maxPrice = Double.parseDouble(priceModel.getMax());
                minSum = minPrice * count;
                maxSum = maxPrice * count;
                sumTitle.setText("总计");
                billSum.setText("¥" + cutUnnecessaryPrecision(String.valueOf(minPrice)) + " - " + cutUnnecessaryPrecision(String.valueOf(maxPrice)));
                discountSumLayout.setVisibility(GONE);
            } else {
                double sp = Double.parseDouble(selectedPrice);
                float sum = (float) (sp * count);
                billSum.setText("¥" + cutUnnecessaryPrecision(String.valueOf(sum)));
                transformActivityLayout(sum);
                actionAdapter.setSum(sum);
                sumTitle.setText("总计");
                sumDescription.setText("¥" + selectedPrice + "×" + count + "            ¥" + sum);
            }
        } else if (mode == MODE_STABLE) {
            double minPrice = Double.parseDouble(priceModel.getMin());
            double maxPrice = Double.parseDouble(priceModel.getMax());
            double count = Double.valueOf(serviceNumber.getText().toString().trim());
            if (Constant.HOURLY_WORKER.equals(serviceTypeId) || Constant.HOURLY_WORKER_WINDOW.equals(serviceTypeId))//小时工
            {
                sum = serviceCountFloat * Double.parseDouble(priceModel.getMin());
            } else {
                sum = minPrice * count;
            }
            billSum.setText("¥" + cutUnnecessaryPrecision(String.valueOf(sum)));
            sumTitle.setText("总计");
            sumDescription.setText("¥" + priceModel.getMin() + "×" + cutUnnecessaryPrecision(String.valueOf(count)) + "            ¥" + cutUnnecessaryPrecision(String.valueOf(sum)));
            actionAdapter.setSum((float) sum);
            transformActivityLayout((float) sum);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case BUSINESS_SERVICE_TYPE_LIST_REQUEST_CODE:
                    BusinessServiceType serviceTypeEx = (BusinessServiceType) data.getSerializableExtra(KEY_TYPE_ENTITY);
                    selectPosition = data.getIntExtra(KEY_SELECT_POSITION, 0);
                    serviceTypeId = serviceTypeEx.getServiceTypeId();
                    serviceTypeName = serviceTypeEx.getServiceTypeName();
                    reset();
                    if (Constant.HOURLY_WORKER.equals(serviceTypeId) || Constant.HOURLY_WORKER_WINDOW.equals(serviceTypeId)) {
                        serviceNumber.setText(MINIMAL_HOURLY_WORKER_DURATION);
                        servicePresenter.getQty(serviceTypeId);
                    } else {
                        minusAction.setVisibility(VISIBLE);
                        plusAction.setVisibility(VISIBLE);
                        serviceNumber.setText(String.valueOf(serviceCountInt));
                    }
                    favorResultLayout.setVisibility(GONE);
                    if (!TextUtils.isEmpty(serviceTypeId)&&!TextUtils.isEmpty(addressId)) {
                        servicePresenter.getServicePrice(serviceTypeId, "3", addressId);
                    }
                    servicePresenter.getSchedule(serviceTypeId);
                    servicePresenter.getServiceDescription(serviceTypeId);
                    break;
                case REQUEST_CODE_ADDRESS:
                    String keyType = data.getStringExtra(AddressManageActivity.KEY_TYPE);
                    addressPosition = data.getIntExtra(AddressManageActivity.KEY_POSITION, -1);
                    if (TextUtils.equals(keyType, "0")) {
                        addressPresenter.getMyServiceAddressList();
                    } else {
                        AddressEntity entity = (AddressEntity) data.getSerializableExtra(AddressManageActivity.KEY_ADDRESS);
                        if (entity != null) {
                            setAddress(entity);
                        }
                    }
                    break;
                case REQUEST_CODE_NOTE:
                    String notes = data.getStringExtra("Notes");
                    ArrayList<String> paths = data.getStringArrayListExtra("Photos");
                    if (paths != null && !paths.isEmpty()) {
                        photoPaths.clear();
                        photoPaths.addAll(paths);
                        photoAdapter.notifyDataSetChanged();
                        album.setVisibility(VISIBLE);
                    } else album.setVisibility(GONE);
                    if (notes != null)
                        remarkText.setText(notes);
                    break;
            }
        }
    }

    public void reset() {
        mode = MODE_INVALID;
        serviceCountInt = 1;
        serviceCountFloat = 3f;
        numberPicker = null;
        dateIndex = -1;
        timeIndex = -1;
        serviceTimeText.setText("");
        sum = -1;
        pricePicker = null;
        selectedPrice = null;
        suitableServicePrice.setText(null);
    }

    private AddressEntity entity;

    private void setAddress(AddressEntity entity) {
        this.entity = entity;
        if (entity != null) {
            String contact = entity.getContact();
            String ads = entity.getAddress();
            String detialAddress = entity.getDetailAddress();
            String gender = TextUtils.equals(entity.getGender(), Constant.GENDER_MALE) ? "先生" : "女士";
            String phone = entity.getPhoneNumber();
            address.setText(contact + "  " + gender + "  " + phone + "\n" + ads + detialAddress);
            addressId = entity.getId();
            if (!TextUtils.isEmpty(serviceTypeId)&&!TextUtils.isEmpty(addressId)) {
                servicePresenter.getServicePrice(serviceTypeId, "3", addressId);
            }
        }
    }

    @Override
    public void onOrderCreate(String orderId, String serviceTypeId) {
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

    //服务数量
    @Override
    public void onQtyResult(List<String> datas) {
        if (numberPicker == null) {
            numberPicker = ActionSheetNumberPicker.newInstance();
            numberPicker.setData(datas);
            numberPicker.setOnItemClickListener(this);
        } else {
            numberPicker.setData(datas);
        }
        serviceNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberPicker != null)
                    numberPicker.show(BusinessOrderActivity.this);
            }
        });
        unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberPicker != null)
                    numberPicker.show(BusinessOrderActivity.this);
            }
        });
        minusAction.setVisibility(GONE);
        plusAction.setVisibility(GONE);
        if (datas != null && !datas.isEmpty())
            serviceNumber.setText(datas.get(0));
        else serviceNumber.setText(MINIMAL_HOURLY_WORKER_DURATION);
        calculateSum();
    }

    @Override
    public void onItemClick(String str) {
        serviceNumber.setText(str);
        serviceCountFloat = Float.parseFloat(str);
        calculateSum();
    }

    @Override
    public boolean onSupportNavigateUp() {
        ActivityCompat.finishAfterTransition(this);
        return true;
    }

    /**
     * 获取到服务价格
     *
     * @param price
     */
    @Override
    public void onPriceResult(PriceEntity price) {
        if (price != null){
            isNegotiable=TextUtils.equals(price.getNegotiable(),"1");
            priceModel = price;
        } else {
            priceModel = new PriceEntity();
        }


        String priceStr;
        servicePriceLayout.setVisibility(View.GONE);
        specialSpace.setVisibility(VISIBLE);
        rangeLayout.setVisibility(GONE);
        if (price == null) {
            mode = MODE_NEG;
        } else if ("1".equals(price.getNegotiable()) ||  price.getMin() == null || price.getMax() == null || price.getUnitName() == null) {
            mode = MODE_NEG;
        } else if (price.getMin() != null && price.getMax() != null && price.getMin().equals(price.getMax())) {
            servicePriceLayout.setVisibility(VISIBLE);
            priceStr = "¥" + cutUnnecessaryPrecision(price.getMin()) + (!TextUtils.isEmpty(price.getUnitName()) ? "/" + price.getUnitName() : "");
            mode = MODE_STABLE;
            selectedPrice = price.getMin();
            suitableServicePrice.setText(priceStr);
        } else {//区间价格
            specialSpace.setVisibility(GONE);
            rangeLayout.setVisibility(VISIBLE);
            servicePriceLayout.setVisibility(VISIBLE);
            mode = MODE_RANGE;
            priceStr = serviceTypeName + "   ¥" + cutUnnecessaryPrecision(price.getMin()) + " - " + cutUnnecessaryPrecision(price.getMax()) + (TextUtils.isEmpty(price.getUnitName()) ? "" : "/" + price.getUnitName());
            priceInfo.setText(priceStr);
            List<String> priceOptions = price.getPriceOptions();
            if (priceOptions != null)
                pricePicker = ActionSheetNumberPicker.newInstance()
                        .setPrefix("¥")
                        .setData(priceOptions)
                        .setTitle("价格越高，服务质量越好哦")
                        .setOnItemClickListener(onPriceItemClickListener);
        }
        serviceType.setText(serviceTypeName);
        if (price != null){
            unit.setText(price.getUnitName());
        } else {
            unit.setText("");
        }
        servicePresenter.getAvailableActivity(serviceTypeId);
//        calculateSum();
    }

    /**
     * 获取到服务时间表
     *
     * @param sch
     */
    @Override
    public void onScheduleResult(List<ServiceScheduleEntity> sch) {
        if (sch != null && !sch.isEmpty()){
            schedule = sch;
            schedulePicker = new ServiceTimePicker.Builder()
                    .left(sch)
                    .right(sch.get(0).getTimeList())
                    .build();
            schedulePicker.setListener(this);
        }

    }

    @Override
    public PlaceOrderComponent getComponent() {
        return component;
    }

    @Override
    public void onConfirmServiceTime(int dPos, int tPos) {
        dateIndex = dPos;
        timeIndex = tPos;
        if (dateIndex != -1 && timeIndex != -1) {
            serviceTimeText.setText(schedule.get(dPos).getDate() + schedule.get(dPos).getTimeSchedule().get(tPos));
        }
    }

    @Override
    public void renderActivity(ActivityNgInfoNew actionEntity) {
        currentAction = actionEntity;
        if (actionEntity != null && actionEntity.isAvailableActivity()) {
            activityWrapper.setVisibility(VISIBLE);
            actionAdapter.setAction(actionEntity);
        } else {
            favorResultLayout.setVisibility(GONE);
            activityWrapper.setVisibility(GONE);
        }
        showLoading();
        calculateSum();
        hideLoading();
    }

    @Override
    public void startAddPhoto() {
        initPhotoPicker();
    }

    private void initPhotoPicker() {
        if (photoPicker == null) {
            photoPicker = ActionSheetPhotoPicker.newInstance();
            photoPicker.setOnPictureChoseListener(this);
        }
        if (photoPaths.size() > 7) {
            showMessage("至多选择8张照片");
            return;
        }
        photoPicker.show(this);
    }

    /**
     * 提交订单
     */
    @OnClick(R.id.submit_order)
    public void submit() {
        if (addressId == null) {
            showMessage("请选择服务地址");
            return;
        }
        if (serviceTypeId == null) {
            showMessage("请选择服务类型");
            return;
        }
        //有服务时间的情况下才需要选择服务时间（后台返回的服务时间可能为空）
        if (dateIndex == -1 || timeIndex == -1 && schedule != null && !schedule.isEmpty()) {
            showMessage("请选择服务时间");
            return;
        }
        if (mode == MODE_RANGE && TextUtils.isEmpty(selectedPrice))//区间价格
        {
            showMessage("请选择服务价格");
            return;
        }
        submitDialog = new CommonDialog.Builder()
                .setContent("请您确认服务信息填写正确再提交订单")
                .showTitle(false)
                .setConfirmButton("我已确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        place();
                    }
                }).setCancelButton("我再想想", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).create();
        submitDialog.show(getSupportFragmentManager(), null);
    }

    //下单
    public void place() {
        if (Constant.HOURLY_WORKER.equals(serviceTypeId) || Constant.HOURLY_WORKER_WINDOW.equals(serviceTypeId)) {
            placeOrderPresenter.placeDirectOrder(serviceTypeId,
                    TextUtils.isEmpty(remarkText.getText().toString().trim()) ? null : remarkText.getText().toString().trim(),
                    photoPaths,
                    addressId, getFormatServiceTime(),
                    serviceCountFloat + "",
                    selectedPrice
            );
        } else {
            placeOrderPresenter.placeDirectOrder(serviceTypeId,
                    TextUtils.isEmpty(remarkText.getText().toString().trim()) ? null : remarkText.getText().toString().trim(),
                    photoPaths,
                    addressId, getFormatServiceTime(),
                    serviceCountInt + "",
                    selectedPrice
            );
        }
    }

    //    @OnClick(R.id.add_photo)
    public void addPhoto() {
        initPhotoPicker();
    }

    @OnClick(R.id.remark_text)
    public void addRemarkText() {
        Intent intent = new Intent(this, OrderNoteActivity.class);
        intent.putExtra("Notes", remarkText.getText().toString().trim());
        intent.putStringArrayListExtra("Photos", photoPaths);
        startActivityForResult(intent, REQUEST_CODE_NOTE);
    }

    /**
     * 登录成功后重新获取
     */
    @Override
    protected void retrieveData() {
//        servicePresenter.getServicePrice(serviceTypeId, "3", addressId);
//        servicePresenter.getSchedule(serviceTypeId);
        addressPresenter.getMyServiceAddressList();
        businessInfoPresenter.getBusinessInfo(providerId);
    }

    public String getFormatServiceTime() {
        ServiceScheduleEntity scheduleEntity = schedule.get(dateIndex);
        String date = scheduleEntity.getDate();
        int end = date.indexOf("今") > 0 ? date.indexOf("今") : (date.indexOf("明") > 0 ? date.indexOf("明") : date.indexOf("周"));
        String subDate = date.substring(0, end);

        return subDate + " " + scheduleEntity.getTimeSchedule().get(timeIndex);
    }


//    @Override
//    public void showError(Throwable e) {
////        if (!hasEverLogin)
//            super.showError(e);
////        hasEverLogin = true;
//    }

    /**
     * 针对从下单页面到服务地址选择页面把服务地址删空的情况，此时下单页面已经选择的服务地址将会失效，刷新
     *
     * @param event
     */
    @Subscribe
    public void onAddressClear(EmptyAddressEvent event) {
//        addressMask.setVisibility(VISIBLE);
        addressId = null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        servicePresenter.destroy();
        placeOrderPresenter.destroy();
        orderPresenter.destroy();
        addressPresenter.destroy();
    }



    private BusinessInfoModel businessInfoModel;

    @Override
    public void render(BusinessInfoModel businessInfoModel) {
        this.businessInfoModel = businessInfoModel;
        providerName.setText(businessInfoModel.getName());
        Glide.with(this)
                .load(businessInfoModel.getPhoto())
                .placeholder(R.mipmap.head_portrait_default)
                .into(roundedImageView);
    }


    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;
    private String mCallingPhone;
    private String mTitle;

    // 拨打电话
    private void dial(String phone, String title) {
        List<String> permissions = new ArrayList<>(2);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //Service 中无法回调onRequestPermissionsResult，提前请求
            permissions.add(Manifest.permission.READ_CALL_LOG);
        }
        if (permissions.isEmpty()) {
            NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title);
            serviceFragment.show(getSupportFragmentManager(), null);
        } else {
            mCallingPhone = phone;
            mTitle = title;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION_CALL_AND_CALL_LOG);
            }
        }
    }
}
