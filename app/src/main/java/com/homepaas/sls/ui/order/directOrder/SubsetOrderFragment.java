package com.homepaas.sls.ui.order.directOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ActivityInfo;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.CommonCouponEntity;
import com.homepaas.sls.domain.entity.CommonCouponInfo;
import com.homepaas.sls.domain.entity.PlaceOrderEntry;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.entity.QueryServicePriceEntry;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.ServiceTimeStartAtEntry;
import com.homepaas.sls.domain.entity.embedded_class.Time;
import com.homepaas.sls.domain.param.PlaceOrderParam;
import com.homepaas.sls.event.DemandRemarkEvent;
import com.homepaas.sls.mvp.presenter.addressmanage.AddressPresenter;
import com.homepaas.sls.mvp.presenter.order.QueryServicePricePresenter;
import com.homepaas.sls.mvp.view.addressmanage.ManageAddressView;
import com.homepaas.sls.mvp.view.order.QueryServicePriceView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.WebViewActivity;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.order.Constant;
import com.homepaas.sls.ui.order.SelectServiceProviderOrWorkerActivity;
import com.homepaas.sls.ui.order.directOrder.adapter.OrderActivityAdapter;
import com.homepaas.sls.ui.order.directOrder.adapter.SubsetOrderServicesAdapter;
import com.homepaas.sls.ui.order.pay.PayActivity;
import com.homepaas.sls.ui.order.servicemerchant.MerchantActivity;
import com.homepaas.sls.ui.redpacket.newRedpacket.UseNewRedPacketActivity;
import com.homepaas.sls.ui.widget.InsuranceExplainDialog;
import com.homepaas.sls.ui.widget.KeywordUtil;
import com.homepaas.sls.ui.widget.NoDefaultToggleButton;
import com.homepaas.sls.ui.widget.ReboundScrollView;
import com.homepaas.sls.util.ArithUtil;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by JWC on 2017/3/24.
 * 3.3.0有子集下单界面
 */

public class SubsetOrderFragment extends CommonBaseFragment implements View.OnTouchListener, ManageAddressView, QueryServicePriceView, SubsetOrderServicesAdapter.OnItemClickListener {


    @Bind(R.id.image_address)
    ImageView imageAddress;
    @Bind(R.id.prompt_add_address)
    TextView promptAddAddress;
    @Bind(R.id.go_address)
    ImageView goAddress;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_photo_number_and_name)
    TextView tvPhotoNumberAndName;
    @Bind(R.id.address_information)
    LinearLayout addressInformation;
    @Bind(R.id.add_address_rel)
    RelativeLayout addAddressRel;
    @Bind(R.id.three_service_name)
    TextView threeServiceName;
    @Bind(R.id.services_recyclerView)
    RecyclerView servicesRecyclerView;
    @Bind(R.id.add_service_number)
    ImageView addServiceNumber;
    @Bind(R.id.service_number_edit)
    EditText serviceNumberEdit;
    @Bind(R.id.reduce_service_number)
    ImageView reduceServiceNumber;
    @Bind(R.id.choose_time)
    TextView chooseTime;
    @Bind(R.id.img_right_arrow)
    ImageView imgRightArrow;
    @Bind(R.id.choose_time_rel)
    RelativeLayout chooseTimeRel;
    @Bind(R.id.tv_worker)
    TextView tvWorker;
    @Bind(R.id.img_right_arrow_worker)
    ImageView imgRightArrowWorker;
    @Bind(R.id.rl_designate_worker)
    RelativeLayout rlDesignateWorker;
    @Bind(R.id.activity_recyclerView)
    RecyclerView activityRecyclerView;
    @Bind(R.id.activity_relief)
    TextView activityRelief;
    @Bind(R.id.activity_rel)
    RelativeLayout activityRel;
    @Bind(R.id.img_right_hint)
    ImageView imgRightHint;
    @Bind(R.id.coupon_relief)
    TextView couponRelief;
    @Bind(R.id.coupon_rel)
    RelativeLayout couponRel;
    @Bind(R.id.total_relief)
    TextView totalRelief;
    @Bind(R.id.insurance_text)
    TextView insuranceText;
    @Bind(R.id.insurance_explain)
    ImageView insuranceExplain;
    @Bind(R.id.insurance_select)
    NoDefaultToggleButton insuranceSelect;
    @Bind(R.id.insurance_rel)
    RelativeLayout insuranceRel;
    @Bind(R.id.remark_text)
    EditText remarkText;
    @Bind(R.id.remark_rel)
    RelativeLayout remarkRel;
    @Bind(R.id.recharge_privilege)
    TextView rechargePrivilege;
    @Bind(R.id.recharge_privilege_rel)
    RelativeLayout rechargePrivilegeRel;
    @Bind(R.id.scroll_view)
    ReboundScrollView scrollView;
    @Bind(R.id.tv_coupon_info)
    TextView tvCouponInfo;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.place_order_button)
    Button placeOrderButton;
    @Bind(R.id.tv_select_service_or_worker_hint)
    TextView tvSelectServiceOrWorkerHint;
    @Bind(R.id.tv_subtotal_price)
    TextView tvSubtotalPrice;
    @Bind(R.id.ly_xj)
    LinearLayout lyXj;
    @Bind(R.id.rl_service_num)
    RelativeLayout rlServiceNum;
    @Bind(R.id.tv_main_demand)
    TextView tvMainDemand;
    @Bind(R.id.img_main_demand_right_arrow)
    ImageView imgMainDemandRightArrow;
    @Bind(R.id.rl_main_demand)
    RelativeLayout rlMainDemand;
    @Bind(R.id.tv_merchant)
    TextView tvMerchant;
    @Bind(R.id.img_right_arrow_merchant)
    ImageView imgRightArrowMerchant;
    @Bind(R.id.rl_no_standard_order)
    RelativeLayout rlNoStandardOrder;
    @Bind(R.id.tv_select_service_hint)
    TextView tvSelectServiceHint;
    @Bind(R.id.tv_rema)
    TextView tvRema;

    private SubsetOrderServicesAdapter subsetOrderServicesAdapter;
    private OrderActivityAdapter orderActivityAdapter;
    //活动集合
    private List<ActivityInfo> activityInfoList;
    //服务集合
    private List<ServiceItem> serviceItemList;
    //对应服务的时间集合
    private List<ServiceScheduleEntity> scheduleList;
    //返回的时间
    private Time serviceTime;
    private ServiceScheduleEntity serviceScheduleEntity;
    private ServiceItem serviceItem;
    private AddressEntity addressEntity;
    private BigDecimal currentDecimal;
    private CommonCouponInfo commonCouponInfo;//红包
    private ActivityNgInfoNew activityNgInfoNew; //活动
    private BigDecimal discountCouponDecimal = null;//红包优惠
    private BigDecimal discountActivityDecimal = null; //活动
    private BigDecimal totalPriceDecimal = null; //总价
    private List<AddressEntity> addressCollection;


    private String serviceId;
    //二级商品id
    private String secondLevelServiceId;
    private String isActivity;  //是否是活动跳转  活动跳转获取服务价格的接口不同
    private String longitude;
    private String latitude;
    private String addressId;  //服务地址id
    private int addressPosition = -1; //地址选择
    private int selectPosition = -1;  //服务选择
    private static final int UPDATE_EDITTEXT = 99;
    public static final int REQUEST_CODE_ADDRESS = 0xff03;
    public static final int SELECT_WORKER_SERVICE = 0xff07;
    public static final int SELECT_SERVICE = 0xff08;
    public static final int REQUEST_CODE_TIME = 0xff04;
    private static final int REQUEST_CODE_COUPON = 0xff06;
    public static final int MODE_CHOOSE = 1;//选择地址模式
    private boolean insuranceCheck = false;  //是否买保险
    private int insuranceAdd = 0;
    private String claimsNoteTitle;  //保险须知标题
    private String claimsNoteContent;//保险须知内容
    private String claimsNoteUrl;    //保险协议url
    // //是否是特殊类型（保姆，月嫂，看护类等等），0 不是，1是;default:0
    private String specialType1;
    private String totalPrice;
    private String demandRemark;//主要需求备注信息
    private boolean isMerchantService;//用户服务地址是否在商家服务范围内
    private boolean isNotUseCoupon = false;
    private boolean mainDemand = false;//特殊服务，主要需求是否填写好
    private String couponId = null;  //这里没用，支付的时候才有用
    private String workerOrMerchantUserId = "";//选择商户或工人的标识
    private String specialTypeUrl;//特殊服务主要需求url地址
    private String mServerNumberPrice;//服务数量的单价，如果有服务数量区间的话，那么由区间决定，没有的话使用默认
    private String merchantOrWorkerId;//商家或者商户id
    private String merchantName;//商家名称
    private String secondLevel;//二级服务id
    //    private String providerUserId;//工人或者商户的Id
    private String providerUserType;//类型，2：工人，3：商户
    private String firstProviderUserType;

    @Inject
    QueryServicePricePresenter queryServicePricePresenter;
    @Inject
    AddressPresenter addressPresenter;
    @Inject
    Navigator mNavigator;

    public static SubsetOrderFragment newInstance(String serviceId, String isActivity, String longitude, String latitude, String merchantId, String merchantName, String secondLevel, String providerUserType) {
        SubsetOrderFragment fragment = new SubsetOrderFragment();
        Bundle args = new Bundle();
        args.putString(StaticData.SERVICE_ID, serviceId);
        args.putString(StaticData.IS_ACTIVITY, isActivity);
        args.putString(StaticData.LONGITUDE, longitude);
        args.putString(StaticData.LATITUDE, latitude);
        args.putString(StaticData.MERCHANT_ID, merchantId);
        args.putString(StaticData.MERCHANT_NAME, merchantName);
        args.putString(StaticData.SECOND_LEVEL, secondLevel);
        args.putString(StaticData.PROVIDER_USER_TYPE, providerUserType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        if (mHandler != null) {
            mHandler.removeMessages(UPDATE_EDITTEXT);
        }
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void demandRemark(DemandRemarkEvent event) {
        if (event != null) {
            demandRemark = event.getDemandRemarkContent();
            if (!TextUtils.isEmpty(demandRemark)) {
                mainDemand = true;
                tvMainDemand.setText("需求已确认");
                tvMainDemand.setTextColor(getResources().getColor(R.color.homepageServerText1));
            } else {
                mainDemand = false;
                tvMainDemand.setText("请选择您的需求");
                tvMainDemand.setTextColor(getResources().getColor(R.color.appText3));
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_EDITTEXT:
                    if (!TextUtils.isEmpty(serviceNumberEdit.getText()) && !TextUtils.isEmpty(serviceNumberEdit.getText().toString())) {
                        if (serviceItem != null) {
                            BigDecimal maxCount = new BigDecimal(serviceItem.getMaxCount()).setScale(1, RoundingMode.HALF_UP);
                            BigDecimal minCount = new BigDecimal(serviceItem.getMinCount()).setScale(1, RoundingMode.HALF_UP);
                            currentDecimal = new BigDecimal(serviceNumberEdit.getText().toString()).setScale(1, RoundingMode.HALF_UP);
                            submitTotalPrice();
                            if (maxCount.compareTo(minCount) == 0) {
                                addServiceNumber.setEnabled(false);
                                reduceServiceNumber.setEnabled(false);
                            } else {
                                if (currentDecimal.compareTo(maxCount) == 0) {
                                    reduceServiceNumber.setEnabled(true);
                                    addServiceNumber.setEnabled(false);
                                } else if (currentDecimal.compareTo(minCount) == 0) {
                                    reduceServiceNumber.setEnabled(false);
                                    addServiceNumber.setEnabled(true);
                                } else if (currentDecimal.compareTo(maxCount) > 0) {
                                    fillNumber(serviceItem.getMaxCount(), 1);
                                } else if (currentDecimal.compareTo(minCount) < 0) {
                                    fillNumber(serviceItem.getMinCount(), 0);
                                } else if (currentDecimal.compareTo(maxCount) < 0 && currentDecimal.compareTo(minCount) > 0) {
                                    reduceServiceNumber.setEnabled(true);
                                    addServiceNumber.setEnabled(true);
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 监听edittext并填写符合要求的数量
     *
     * @param count
     * @param what
     */
    private void fillNumber(String count, int what) {
        currentDecimal = new BigDecimal(count).setScale(1, RoundingMode.HALF_UP);
        serviceNumberEdit.setText(determine(String.valueOf(currentDecimal)));
        if (what == 0) {
            reduceServiceNumber.setEnabled(false);
            addServiceNumber.setEnabled(true);
            showMessage("服务数量最小只能填写" + count);
        } else {
            reduceServiceNumber.setEnabled(true);
            addServiceNumber.setEnabled(false);
            showMessage("服务数量最大只能填写" + count);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_subset_order;
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            serviceId = getArguments().getString(StaticData.SERVICE_ID);
            secondLevelServiceId = ((CommonPlaceOrderActivity) mContext).getServiceId();
            isActivity = getArguments().getString(StaticData.IS_ACTIVITY);
            longitude = getArguments().getString(StaticData.LONGITUDE);
            latitude = getArguments().getString(StaticData.LATITUDE);
            merchantOrWorkerId = getArguments().getString(StaticData.MERCHANT_ID);
            merchantName = getArguments().getString(StaticData.MERCHANT_NAME);
            secondLevel = getArguments().getString(StaticData.SECOND_LEVEL);
            providerUserType = getArguments().getString(StaticData.PROVIDER_USER_TYPE);
            firstProviderUserType = providerUserType;
            //指派工人或商户控件和指派服务商控件显示隐藏逻辑
            setMerchantInfo();
        }

        remarkText.setOnTouchListener(this);
        activityInfoList = new ArrayList<>();
        setAdapter();
        resetData();
        queryServicePricePresenter.setQueryServicePriceView(this);
        addressPresenter.setAddressView(this);
        //获取服务地址
        addressPresenter.getMyServiceAddressList();
        editListener();
        EventBus.getDefault().register(this);
    }

    public void setMerchantInfo() {
        //是否是标准订单
        if (!TextUtils.isEmpty(merchantOrWorkerId)) {
            //标准订单的指派工人或商户布局
            rlDesignateWorker.setVisibility(View.GONE);
            rlNoStandardOrder.setVisibility(View.VISIBLE);
            //主要需求布局
            rlMainDemand.setVisibility(View.GONE);
            //-1 系统推荐商家
            if (merchantOrWorkerId.equals("-1")) {
                tvMerchant.setText(mContext.getString(R.string.system_merchant));
                tvMerchant.setTextColor(mContext.getResources().getColor(R.color.appText3));
                tvSelectServiceHint.setVisibility(View.GONE);
            } else {
                if (!TextUtils.isEmpty(merchantName))
                    tvMerchant.setText(merchantName);
                tvMerchant.setTextColor(mContext.getResources().getColor(R.color.home_type_txt));
                tvSelectServiceHint.setVisibility(View.VISIBLE);
            }
        } else {
            rlMainDemand.setVisibility(View.VISIBLE);
            rlDesignateWorker.setVisibility(View.VISIBLE);
            rlNoStandardOrder.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {

    }

    /**
     * edittext监听
     */
    private void editListener() {
        //只是为了显示光标
        serviceNumberEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceNumberEdit.setCursorVisible(true);
            }
        });
        //监听edittext变化
        serviceNumberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(serviceNumberEdit.getText().toString())) {
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(UPDATE_EDITTEXT, 500);
                    } else {
                        mHandler.sendEmptyMessageDelayed(UPDATE_EDITTEXT, 500);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //隐藏光标并且隐藏软键盘
        serviceNumberEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    serviceNumberEdit.setCursorVisible(false);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(serviceNumberEdit.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 添加adapter
     */
    private void setAdapter() {
        serviceItemList = new ArrayList<>();
        subsetOrderServicesAdapter = new SubsetOrderServicesAdapter(getActivity());
        subsetOrderServicesAdapter.setOnItemClickListener(this);
        servicesRecyclerView.setAdapter(subsetOrderServicesAdapter);
        orderActivityAdapter = new OrderActivityAdapter();
        activityRecyclerView.setAdapter(orderActivityAdapter);
    }

    @OnClick({R.id.add_address_rel, R.id.rl_designate_worker, R.id.choose_time_rel, R.id.add_service_number, R.id.reduce_service_number, R.id.place_order_button, R.id.insurance_explain, R.id.insurance_select, R.id.coupon_rel, R.id.rl_no_standard_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address_rel:  //选择地址
                Intent intent = new Intent(getActivity(), AddressManageActivity.class);
                intent.putExtra(StaticData.MODE, MODE_CHOOSE);
                intent.putExtra(StaticData.ADDRESS_POSITION, addressPosition);
                startActivityForResult(intent, REQUEST_CODE_ADDRESS);
                break;
            case R.id.choose_time_rel:  //选择时间
                if (scheduleList != null && !scheduleList.isEmpty()) {
                    Intent chooseTimeIntent = new Intent(getActivity(), OrderChooseTimeActivity.class);
                    chooseTimeIntent.putExtra(StaticData.SCHEDULE_List, (Serializable) scheduleList);
                    startActivityForResult(chooseTimeIntent, REQUEST_CODE_TIME);
                }
                break;
            case R.id.add_service_number:  //服务数量增加
                if (serviceItem != null) {
                    addServiceNumber(serviceItem);
                }
                break;
            case R.id.reduce_service_number:  //服务数量减少
                if (serviceItem != null) {
                    reduceServiceNumber(serviceItem);
                }
                break;
            case R.id.place_order_button:   //提交订单
                placeOrder();
                break;
            case R.id.insurance_explain:  //查看保险赔付说明
                seeInsuranceAgreement();
                break;
            case R.id.insurance_select:   //是否买保险
                insuranceAdd += 1;
                if (insuranceAdd % 2 == 0) {
                    insuranceSelect.setChecked(true);
                    insuranceCheck = true;
                } else {
                    insuranceSelect.setChecked(false);
                    insuranceCheck = false;
                }
                submitTotalPrice();
                break;
            case R.id.coupon_rel:
                String couponText = couponRelief.getText().toString();
                if (!TextUtils.isEmpty(couponText) && !TextUtils.equals(getResources().getString(R.string.deposit_coupon), couponText) && !TextUtils.equals(getResources().getString(R.string.no_coupon), couponText)) {
                    goChooseCoupon();
                }
                break;
            case R.id.rl_designate_worker://指派工人
                if (TextUtils.isEmpty(addressId)) {
                    showMessage("请选择服务地址");
                    return;
                }
                if (serviceItem == null)
                    return;
                Intent intents = new Intent(mContext, SelectServiceProviderOrWorkerActivity.class);
                intents.putExtra(StaticData.ADDRESS, addressId);
                intents.putExtra(StaticData.SERVICE_ID, serviceItem.getServiceId());
                startActivityForResult(intents, SELECT_WORKER_SERVICE);
                break;
            case R.id.rl_no_standard_order://非标准订单选择服务商显示二级服务品类下的三级服务列表
                ServiceItem newServiceItem = new ServiceItem();
                newServiceItem.setOrderType(isActivity);
                newServiceItem.setServiceId(secondLevel);
//                newServiceItem.setServiceName(merchantName);
                Intent merchantIntent = new Intent(mContext, MerchantActivity.class);
                merchantIntent.putExtra("serviceItem", newServiceItem);
                startActivityForResult(merchantIntent, SELECT_SERVICE);
                break;
            default:
        }
    }

    /**
     * 加服务数量
     *
     * @param serviceItem
     */
    private void addServiceNumber(ServiceItem serviceItem) {
        reduceServiceNumber.setEnabled(true);
        BigDecimal addDecimal = new BigDecimal(serviceItem.getCountStep()).setScale(1, RoundingMode.HALF_UP);
        currentDecimal = currentDecimal.add(addDecimal);
        serviceNumberEdit.setText(determine(String.valueOf(currentDecimal)));
        //重新绘制adapter
        subsetOrderServicesAdapter.setServiceNumberAddOrSubtract(serviceNumberEdit.getText().toString());
        if (serviceItem.getMaxCount() != null) {
            BigDecimal maxCount = new BigDecimal(serviceItem.getMaxCount()).setScale(1, RoundingMode.HALF_UP);
            if ((currentDecimal.compareTo(maxCount)) >= 0) {
                addServiceNumber.setEnabled(false);
            } else {
                addServiceNumber.setEnabled(true);
            }
        }
    }


    /**
     * 减服务数量
     *
     * @param serviceItem
     */
    private void reduceServiceNumber(ServiceItem serviceItem) {
        addServiceNumber.setEnabled(true);
        BigDecimal addDecimal = new BigDecimal(serviceItem.getCountStep()).setScale(1, RoundingMode.HALF_UP);
        currentDecimal = currentDecimal.subtract(addDecimal);
        serviceNumberEdit.setText(determine(String.valueOf(currentDecimal)));
        //重新绘制adapter
        subsetOrderServicesAdapter.setServiceNumberAddOrSubtract(serviceNumberEdit.getText().toString());
        if (serviceItem.getMinCount() != null) {
            BigDecimal minCount = new BigDecimal(serviceItem.getMinCount()).setScale(1, RoundingMode.HALF_UP);
            if ((currentDecimal.compareTo(minCount)) <= 0) {
                reduceServiceNumber.setEnabled(false);
            } else {
                reduceServiceNumber.setEnabled(true);
            }
        }
    }

    /**
     * 所有触发价格改变的都会调用这个类。如切换服务类型item或者服务数量加减等触发
     * 计算价格
     *
     * @param serviceItem
     */
    private String calculationPrice(ServiceItem serviceItem) {
        //根据区间计算价格
        List<ServiceItem.RangePrice> rangePrices = serviceItem.getRangePrices();
        mServerNumberPrice = serviceItem.getPrice();
        //如果集合数量为0或者集合为空，则表示没有区间单价，直接使用默认的服务单价
        double number = ArithUtil.getType(serviceNumberEdit.getText().toString()).doubleValue();
        if (rangePrices != null && rangePrices.size() > 0) {
            //多个区间需要遍历
            for (int i = 0; i < rangePrices.size(); i++) {
                ServiceItem.RangePrice rangePrice = rangePrices.get(i);
                if (number <= rangePrice.getMaxCount() && number >= rangePrice.getMinCount()) //满足某个区间直接退出
                {
                    mServerNumberPrice = rangePrice.getPrice();
                    break;
                }
            }
        }
        if (serviceItem != null && !TextUtils.isEmpty(mServerNumberPrice)) {
            BigDecimal priceDecimal = new BigDecimal(mServerNumberPrice).setScale(2, RoundingMode.HALF_UP);
            BigDecimal insuranceDecimal = new BigDecimal("1.00").setScale(2, RoundingMode.HALF_UP);
            if (!TextUtils.isEmpty(serviceItem.getSellType()) && TextUtils.equals(serviceItem.getSellType(), "1")) {
                if (insuranceCheck) {
                    return String.valueOf(priceDecimal.multiply(currentDecimal).add(insuranceDecimal));
                } else {
                    return String.valueOf(priceDecimal.multiply(currentDecimal));
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 计算价格不包含保险费用
     *
     * @param serviceItem
     */
    private String calculationPriceNoInsurance(ServiceItem serviceItem) {
        if (serviceItem != null && !TextUtils.isEmpty(serviceItem.getPrice())) {
            BigDecimal priceDecimal = new BigDecimal(serviceItem.getPrice()).setScale(2, RoundingMode.HALF_UP);
            if (!TextUtils.isEmpty(serviceItem.getSellType()) && TextUtils.equals(serviceItem.getSellType(), "1")) {
                return String.valueOf(priceDecimal.multiply(currentDecimal));
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 确认完成按钮显示的价格
     */
    public void submitTotalPrice() {
        discountCouponDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
        discountActivityDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
        totalPrice = calculationPrice(serviceItem);
        if (!TextUtils.isEmpty(totalPrice)) {
            totalPriceDecimal = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);
        }
        if (commonCouponInfo != null && !TextUtils.isEmpty(totalPrice)) {
            String discountCoupon = displayCoupon(commonCouponInfo, totalPrice);
            if (!TextUtils.isEmpty(discountCoupon)) {
                discountCouponDecimal = new BigDecimal(discountCoupon).setScale(2, RoundingMode.HALF_UP);
            }
        }
        if (activityNgInfoNew != null && !TextUtils.isEmpty(totalPrice)) {
            String discountActivity = discountActivity(activityNgInfoNew, totalPrice);
            if (!TextUtils.isEmpty(discountActivity)) {
                discountActivityDecimal = new BigDecimal(discountActivity).setScale(2, RoundingMode.HALF_UP);
            }
        }
        if (TextUtils.isEmpty(totalPrice)) {//面议类订单
            couponRelief.setText(R.string.deposit_coupon);
            String s = String.valueOf(discountActivityDecimal.add(discountCouponDecimal));
            if (!TextUtils.isEmpty(s) && s.equals("0.00")) {
                totalRelief.setText("");
                tvCouponInfo.setText("");
            } else {
                totalRelief.setText("已优惠¥" + s);
                tvCouponInfo.setText("已优惠¥" + s);
            }


            String price = determinePrice(serviceItem.getDepositAmount());
            tvPrice.setText("¥" + price);
            if (insuranceCheck) {
                price = ArithUtil.sub(price, "1").toString();
            }
            tvSubtotalPrice.setText("¥" + price);
//            placeOrderButton.setText("提交订单 ¥" + determinePrice(serviceItem.getDepositAmount()) + "元");
        } else {//定价类订单
            activityRelief.setText("-¥" + String.valueOf(discountActivityDecimal));
            if (!TextUtils.equals("0.00", String.valueOf(discountCouponDecimal))) {
                couponRelief.setText("-¥" + String.valueOf(discountCouponDecimal));
            } else {
                couponRelief.setText(R.string.no_coupon);
            }
            String s = String.valueOf(discountActivityDecimal.add(discountCouponDecimal));
            if (!TextUtils.isEmpty(s) && s.equals("0.00")) {
                totalRelief.setText("");
                tvCouponInfo.setText("");
            } else {
                totalRelief.setText("已优惠¥" + s);
                tvCouponInfo.setText("已优惠¥" + s);
            }
            String price = determinePrice(String.valueOf(totalPriceDecimal.subtract(discountCouponDecimal).subtract(discountActivityDecimal)));
            tvPrice.setText("¥" + price);
            if (insuranceCheck) {
                price = ArithUtil.sub(price, "1").toString();
            }
            tvSubtotalPrice.setText("¥" + price);
//            placeOrderButton.setText(" 提交订单 ¥" + determinePrice(String.valueOf(totalPriceDecimal.subtract(discountCouponDecimal).subtract(discountActivityDecimal))) + "元");
        }
    }

    /**
     * 判断小数点后面的数是不是0,这个是服务数量的
     *
     * @param numberStr
     * @return
     */
    private String determine(String numberStr) {
        if (!TextUtils.isEmpty(numberStr)) {
            String[] numbers = numberStr.split("\\.");
            if (numbers.length > 1) {
                if (!TextUtils.isEmpty(numbers[1]) && TextUtils.equals(numbers[1], "0")) {
                    return numbers[0];
                } else {
                    return numberStr;
                }
            } else {
                return numberStr;
            }
        } else {
            return "0";
        }
    }


    /**
     * 判断小数点后面的数是不是00,这个是价格的
     *
     * @param numberStr
     * @return
     */
    private String determinePrice(String numberStr) {
        String[] numbers = numberStr.split("\\.");
        if (numbers.length > 1) {
            if (!TextUtils.isEmpty(numbers[1]) && TextUtils.equals(numbers[1], "00")) {
                return numbers[0];
            } else {
                return numberStr;
            }
        } else {
            return numberStr;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SELECT_SERVICE://选择服务商或者选择服务商详情中的服务
                    merchantOrWorkerId = data.getStringExtra(StaticData.MERCHANT_ID);
                    merchantName = data.getStringExtra(StaticData.MERCHANT_NAME);
                    if (TextUtils.equals(merchantOrWorkerId, "-1")) { //助家推荐服务商
                        serviceId = secondLevelServiceId;
                        providerUserType = firstProviderUserType;
                        addressGetPrice();
                        setMerchantInfo();
                        return;
                    }
                    serviceId = data.getStringExtra(StaticData.SERVICE_ID);
                    providerUserType = data.getStringExtra(StaticData.PROVIDER_USER_TYPE);
                    //更新下单信息
//                    CommonAppPreferences commonAppPreferences = new CommonAppPreferences(mContext);
//                    longitude = commonAppPreferences.getLongitude();
//                    latitude = commonAppPreferences.getLatitude();
                    addressGetPrice();
                    setMerchantInfo();
                    break;
                //指派工人或商户预约后返回
                case SELECT_WORKER_SERVICE:
                    workerOrMerchantUserId = data.getStringExtra(StaticData.USER_ID);
                    if (TextUtils.isEmpty(workerOrMerchantUserId)) {
                        //情况下单指派工人或商户信息
                        tvWorker.setText(mContext.getString(R.string.system_worker));
                        tvWorker.setTextColor(mContext.getResources().getColor(R.color.appText3));
                        tvSelectServiceOrWorkerHint.setVisibility(View.GONE);
                    } else {
                        String stringExtra = data.getStringExtra(StaticData.USER_SELECT_SERVICE_OR_WORKER_NAME);
                        if (!TextUtils.isEmpty(stringExtra)) {
                            tvWorker.setText(stringExtra);
                            tvWorker.setTextColor(mContext.getResources().getColor(R.color.home_type_txt));
                            tvSelectServiceOrWorkerHint.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case REQUEST_CODE_ADDRESS:
                    resetData();
                    String keyType = data.getStringExtra(AddressManageActivity.KEY_TYPE);
                    addressPosition = data.getIntExtra(AddressManageActivity.KEY_POSITION, -1);
                    if (TextUtils.isEmpty(keyType) || TextUtils.equals(keyType, "0")) {
                        addressPresenter.getMyServiceAddressList();
                    } else {
                        AddressEntity entity = (AddressEntity) data.getSerializableExtra(AddressManageActivity.KEY_ADDRESS);
                        if (entity != null) {
                            setAddress(entity);
                            //选择地址后保存用户选择地址，下次默认选择用户上次选择地址
                            PreferencesUtil.saveString(StaticData.ADDRESS_ID, entity.getId());
                        }
                    }
                    break;
                case REQUEST_CODE_TIME:
                    serviceScheduleEntity = (ServiceScheduleEntity) data.getSerializableExtra(StaticData.SERVICE_SCHEDULE);
                    serviceTime = (Time) data.getSerializableExtra(StaticData.SERVICE_TIME);
                    if (serviceTime != null && serviceScheduleEntity != null) {
                        chooseTime.setText(serviceScheduleEntity.getDate() + serviceTime.getTime());
                        chooseTime.setTextColor(getResources().getColor(R.color.homepageServerText1));
                    }
                    break;
                case REQUEST_CODE_COUPON:
                    if (data != null) {
                        isNotUseCoupon = data.getBooleanExtra("IsNotUseCoupon", false);
                        if (isNotUseCoupon) {
                            couponId = null;
                            couponRelief.setText("不使用红包");
                            discountCouponDecimal = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
                            if (discountActivityDecimal == null) {
                                discountActivityDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
                            }
                            String s = String.valueOf(discountActivityDecimal.add(discountCouponDecimal));
                            if (!TextUtils.isEmpty(s) && s.equals("0.00")) {
                                totalRelief.setText("");
                                tvCouponInfo.setText("");
                            } else {
                                totalRelief.setText("已优惠¥" + s);
                                tvCouponInfo.setText("已优惠¥" + s);
                            }
                            tvPrice.setText("¥" + determinePrice(String.valueOf(totalPriceDecimal.subtract(discountCouponDecimal).subtract(discountActivityDecimal))));
                            tvSubtotalPrice.setText("¥" + determinePrice(String.valueOf(totalPriceDecimal.subtract(discountCouponDecimal).subtract(discountActivityDecimal))));
                        } else {
                            String discountCoupon = data.getStringExtra("DiscountMoney");
                            couponId = data.getStringExtra("CouponId");
                            discountCouponDecimal = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
                            if (!TextUtils.isEmpty(discountCoupon)) {
                                discountCouponDecimal = new BigDecimal(discountCoupon).setScale(2, RoundingMode.HALF_UP);
                            }
                            if (discountActivityDecimal == null) {
                                discountActivityDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
                            }
                            couponRelief.setText("-¥" + String.valueOf(discountCouponDecimal));
                            String s = String.valueOf(discountActivityDecimal.add(discountCouponDecimal));
                            if (!TextUtils.isEmpty(s) && s.equals("0.00")) {
                                totalRelief.setText("");
                                tvCouponInfo.setText("");
                            } else {
                                totalRelief.setText("已优惠¥" + s);
                                tvCouponInfo.setText("已优惠¥" + s);
                            }
                            tvPrice.setText("¥" + determinePrice(String.valueOf(totalPriceDecimal.subtract(discountCouponDecimal).subtract(discountActivityDecimal))));
                            tvSubtotalPrice.setText("¥" + determinePrice(String.valueOf(totalPriceDecimal.subtract(discountCouponDecimal).subtract(discountActivityDecimal))));
                        }
                    }
                    break;
            }
        }
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
    public void itemClickListener(int selectPosition) {
        resetData();
        this.selectPosition = selectPosition;
        serviceItem = serviceItemList.get(selectPosition);
        serviceNumberEdit.setText(determine(serviceItem.getDefaultCount()));
        subsetOrderServicesAdapter.setItemClickChange(selectPosition, serviceNumberEdit.getText().toString());

        if (!TextUtils.isEmpty(serviceItem.getDefaultCount())) {
            currentDecimal = new BigDecimal(serviceItem.getDefaultCount()).setScale(1, RoundingMode.HALF_UP);
        }
        queryServicePricePresenter.getServiceTime(serviceItem.getServiceId(), longitude, latitude);
        queryServicePricePresenter.getAvailableActivity(serviceItem.getServiceId());
        queryServicePricePresenter.getPlaceOrderCoupon(serviceItem.getServiceId(), longitude, latitude, "0");
        if (!TextUtils.isEmpty(serviceItem.getMaunlInput()) && TextUtils.equals(serviceItem.getMaunlInput(), "0")) {
            serviceNumberEdit.setEnabled(false);
        } else {
            serviceNumberEdit.setEnabled(true);
        }
    }


    @Override
    public void renderAddress(List<AddressEntity> addressCollection) {
        this.addressCollection = addressCollection;
        addressGetPrice();
    }


    public void addressGetPrice() {
        //有服务地址后使用服务地址列表的第一个服务地址填写地址姓名电话和获取对应地址下服务的价格
        if (addressCollection != null && !addressCollection.isEmpty()) {
            //如果用户有选择地址记录就默认选择和上次一样的地址
            String addressID = PreferencesUtil.getString(StaticData.ADDRESS_ID);
            if (TextUtils.isEmpty(addressID)) {
                setAddress(addressCollection.get(0));
                addressPosition = 0;
            } else {
                for (int i = 0; i < addressCollection.size(); i++) {
                    AddressEntity addressEntity = addressCollection.get(i);
                    //后台返回地址有和上次记录一样的就使用和上次记录一样的地址信息
                    if (addressEntity.getId().equals(addressID)) {
                        setAddress(addressEntity);
                        addressPosition = i;
                        return;
                    }
                }
                setAddress(addressCollection.get(0));
                addressPosition = 0;
            }
        } else {
            //提示用户选择一个服务地址
            promptAddAddress.setVisibility(View.VISIBLE);
            addressInformation.setVisibility(View.INVISIBLE);
            addressPosition = -1;
            if (!TextUtils.isEmpty(isActivity) && TextUtils.equals(isActivity, "0")) {
                if (merchantOrWorkerId.equals("-1") || TextUtils.isEmpty(merchantOrWorkerId)) {
                    //系统推荐商家
                    queryServicePricePresenter.getQueryServicePrice(serviceId, longitude, latitude, "", "", "");
                } else {
                    queryServicePricePresenter.getQueryServicePrice(serviceId, longitude, latitude, TextUtils.isEmpty(secondLevel) ? "2" : "3", merchantOrWorkerId, providerUserType);
                }
            } else {
                queryServicePricePresenter.getQueryActivityServicePrice(serviceId, longitude, latitude);
            }
        }
    }

    /**
     * 有服务地址后填写地址姓名电话和获取对应地址下服务的价格
     *
     * @param addressEntity
     */
    private void setAddress(AddressEntity addressEntity) {
        if (addressEntity != null) {
            this.addressEntity = addressEntity;
            promptAddAddress.setVisibility(View.GONE);
            addressInformation.setVisibility(View.VISIBLE);
            addressId = addressEntity.getId();
            longitude = addressEntity.getLng();
            latitude = addressEntity.getLat();


            tvAddress.setText(addressEntity.getAddress() + addressEntity.getDetailAddress());
            tvPhotoNumberAndName.setText(addressEntity.getContact() + "\t\t" + addressEntity.getPhoneNumber());
            if (!TextUtils.isEmpty(serviceId)) {
                if (!TextUtils.isEmpty(isActivity) && TextUtils.equals(isActivity, "0")) {
//                    queryServicePricePresenter.getQueryServicePrice(serviceId, longitude, latitude, TextUtils.isEmpty(secondLevel) ? "2" : "3", merchantOrWorkerId, providerUserType);
                    if (merchantOrWorkerId.equals("-1") || TextUtils.isEmpty(merchantOrWorkerId)) {
                        //系统推荐商家
                        queryServicePricePresenter.getQueryServicePrice(serviceId, longitude, latitude, "", "", "");
                    } else {
                        queryServicePricePresenter.getQueryServicePrice(serviceId, longitude, latitude, TextUtils.isEmpty(secondLevel) ? "2" : "3", merchantOrWorkerId, providerUserType);
                    }
                } else {
                    queryServicePricePresenter.getQueryActivityServicePrice(serviceId, longitude, latitude);
                }
            }
        } else {
            promptAddAddress.setVisibility(View.VISIBLE);
            addressInformation.setVisibility(View.INVISIBLE);
            if (!TextUtils.isEmpty(isActivity) && TextUtils.equals(isActivity, "0")) {
//                queryServicePricePresenter.getQueryServicePrice(serviceId, longitude, latitude, TextUtils.isEmpty(secondLevel) ? "2" : "3", merchantOrWorkerId, providerUserType);
                if (merchantOrWorkerId.equals("-1") || TextUtils.isEmpty(merchantOrWorkerId)) {
                    //系统推荐商家
                    queryServicePricePresenter.getQueryServicePrice(serviceId, longitude, latitude, "", "", "");
                } else {
                    queryServicePricePresenter.getQueryServicePrice(serviceId, longitude, latitude, TextUtils.isEmpty(secondLevel) ? "2" : "3", merchantOrWorkerId, providerUserType);
                }
            } else {
                queryServicePricePresenter.getQueryActivityServicePrice(serviceId, longitude, latitude);
            }
        }

    }

    @Override
    public void deleteSuccess(int delIndex) {

    }

    /**
     * 重置数据
     */
    private void resetData() {
        selectPosition = -1;
        serviceNumberEdit.setText("");
        currentDecimal = new BigDecimal("0").setScale(1, RoundingMode.HALF_UP);
        if (scheduleList != null) {
            scheduleList.clear();
        }
        chooseTime.setText(R.string.service_choose_time);
        chooseTime.setTextColor(getResources().getColor(R.color.appText3));
        serviceScheduleEntity = null;
        serviceTime = null;
//        addServiceNumber.setEnabled(true);
//        reduceServiceNumber.setEnabled(true);
        serviceNumberEdit.setEnabled(true);
        commonCouponInfo = null;
        activityNgInfoNew = null;
        activityRelief.setText("");
        couponRelief.setText("");
        totalRelief.setText("");
        tvCouponInfo.setText("");
    }

    @Override
    public void addressError() {
        isMerchantService = false;
        resetData();
        subsetOrderServicesAdapter.setServiceItemList(new ArrayList<ServiceItem>());
        subsetOrderServicesAdapter.notifyDataSetChanged();
    }

    /**
     * 获取四级服务类型集合
     *
     * @param queryServicePriceEntry
     */
    @Override
    public void renderServices(QueryServicePriceEntry queryServicePriceEntry) {
        isMerchantService = true;
        claimsNoteTitle = "";
        claimsNoteContent = "";
        claimsNoteUrl = "";
        if (queryServicePriceEntry != null && queryServicePriceEntry.getServiceItem() != null) {
            serviceItemList = queryServicePriceEntry.getServiceItem().getSubItems();
            if (serviceItemList != null && serviceItemList.size() == 1) {
                threeServiceName.setText(queryServicePriceEntry.getServiceItem().getServiceName());
            } else {
                threeServiceName.setText("请选择" + queryServicePriceEntry.getServiceItem().getServiceName() + "类型");
            }
            if (mContext instanceof CommonPlaceOrderActivity)
                ((CommonPlaceOrderActivity) (mContext)).setTitle(queryServicePriceEntry.getServiceItem().getServiceName());
            subsetOrderServicesAdapter.setServiceItemList(serviceItemList);
//            subsetOrderServicesAdapter.setItemClickChange(-1,serviceNumberEdit.getText().toString());
            String isEnableClaims = queryServicePriceEntry.getServiceItem().getIsEnableClaims();
            if (!TextUtils.isEmpty(isEnableClaims) && TextUtils.equals("1", isEnableClaims)) {
                insuranceRel.setVisibility(View.VISIBLE);
                insuranceSelect.setChecked(true);
                insuranceCheck = true;
                insuranceAdd = 0;
            } else {
                insuranceCheck = false;
                insuranceRel.setVisibility(View.GONE);
            }
            ServiceItem.ClaimsNote claimsNote = queryServicePriceEntry.getServiceItem().getClaimsNote();
            if (claimsNote != null) {
                claimsNoteTitle = claimsNote.getTitle();
                claimsNoteContent = claimsNote.getContent();
                claimsNoteUrl = claimsNote.getUrl();
            }
//            maxRechargeAmount = queryServicePriceEntry.getServiceItem().getMaxRechargeAmount();
//            maxRechargeOffAmount = queryServicePriceEntry.getServiceItem().getMaxRechargeOffAmount();


            //默认选取第一个服务
            resetData();
            this.selectPosition = 0;
            serviceItem = serviceItemList.get(selectPosition);
            serviceNumberEdit.setText(determine(serviceItem.getDefaultCount()));
            subsetOrderServicesAdapter.setItemClickChange(selectPosition, serviceNumberEdit.getText().toString());
            if (!TextUtils.isEmpty(serviceItem.getDefaultCount())) {
                currentDecimal = new BigDecimal(serviceItem.getDefaultCount()).setScale(1, RoundingMode.HALF_UP);
            }
            queryServicePricePresenter.getServiceTime(serviceItem.getServiceId(), longitude, latitude);
            queryServicePricePresenter.getAvailableActivity(serviceItem.getServiceId());
            queryServicePricePresenter.getPlaceOrderCoupon(serviceItem.getServiceId(), longitude, latitude, "0");
            if (!TextUtils.isEmpty(serviceItem.getMaunlInput()) && TextUtils.equals(serviceItem.getMaunlInput(), "0")) {
                serviceNumberEdit.setEnabled(false);
            } else {
                serviceNumberEdit.setEnabled(true);
            }
        }
        specialType1 = queryServicePriceEntry.getServiceItem().getSpecialType();
        if (!TextUtils.isEmpty("specialType1") && specialType1.equals("1")) {
            //特殊类型不需要备注和服务数量
            remarkRel.setVisibility(View.GONE);
            rlServiceNum.setVisibility(View.GONE);
            rlMainDemand.setVisibility(View.VISIBLE);
        } else {
            remarkRel.setVisibility(View.VISIBLE);
            rlServiceNum.setVisibility(View.VISIBLE);
            rlMainDemand.setVisibility(View.GONE);
        }

        specialTypeUrl = queryServicePriceEntry.getServiceItem().getSpecialTypeUrl();

    }

    /**
     * 对应服务的时间
     *
     * @param serviceTimeStartAtEntry
     */
    @Override
    public void renderServiceTime(ServiceTimeStartAtEntry serviceTimeStartAtEntry) {
        if (serviceTimeStartAtEntry != null) {
            scheduleList = serviceTimeStartAtEntry.getServiceScheduleEntities();
        }
    }

    /**
     * 对应的活动
     *
     * @param actionEntity
     */
    @Override
    public void renderActivity(ActivityNgInfoNew actionEntity) {
        this.activityNgInfoNew = actionEntity;
        if (actionEntity != null && actionEntity.isAvailableActivity()) {
            List<ActivityNgInfoNew.ActivityNgDetail> activityNgDetailList = actionEntity.getNeedActivity("0");
            if (activityInfoList == null) {
                activityInfoList = new ArrayList<>();
            } else {
                activityInfoList.clear();
            }
            for (ActivityNgInfoNew.ActivityNgDetail activityNgDetail : activityNgDetailList) {
                String title = activityNgDetail.getTitle();
                List<ActivityNgInfoNew.ActivityNgOfRule> activityNgOfRuleList = activityNgDetail.getActivityNgOfRuleList();
                if (activityNgOfRuleList != null && !activityNgOfRuleList.isEmpty()) {
                    for (ActivityNgInfoNew.ActivityNgOfRule activityNgOfRule : activityNgOfRuleList) {
                        if (activityNgOfRule != null) {
                            ActivityInfo activityInfo = new ActivityInfo();
                            activityInfo.setTitle(title);
                            activityInfo.setUpper(activityNgOfRule.getUpper());
                            activityInfo.setMinus(activityNgOfRule.getMinus());
                            activityInfoList.add(activityInfo);
                        }
                    }
                }
            }
            if (!activityInfoList.isEmpty()) {
                activityRel.setVisibility(View.VISIBLE);
                orderActivityAdapter.setActivityList(activityInfoList);
            } else {
                activityRel.setVisibility(View.GONE);
            }
        } else {
            activityRel.setVisibility(View.GONE);
        }
        submitTotalPrice();
    }

    /**
     * 提交订单后
     *
     * @param placeOrderEntry
     */
    @Override
    public void renderPlaceOrder(PlaceOrderEntry placeOrderEntry) {
        if (placeOrderEntry != null && !TextUtils.isEmpty(placeOrderEntry.getOrderId())) {
//            if (!TextUtils.isEmpty(serviceItem.getSellType()) && TextUtils.equals(serviceItem.getSellType(), "0")) {
//            OrderDetailsActivity.start(getActivity(), placeOrderEntry.getOrderId());
//            }
            //面议类原本是去详情页，现在有了定金，就全部去支付页
//            else {
            Intent intent = new Intent(getActivity(), PayActivity.class);
            intent.putExtra(Constant.OrderId, placeOrderEntry.getOrderId());
            intent.putExtra(StaticData.ConfirmGO, "0");
            startActivity(intent);
            getActivity().finish();
//            }
        }
    }

    /**
     * 跳转选择红包页
     */
    private void goChooseCoupon() {
        Intent intent = new Intent(getActivity(), UseNewRedPacketActivity.class);
        intent.putExtra(StaticData.TOTAL_MONEY, totalPrice);
        intent.putExtra(StaticData.IS_NOT_USE_COUPON, isNotUseCoupon);
        intent.putExtra(StaticData.COUPON_ID, couponId);
        intent.putExtra(StaticData.SERVICE_ID, serviceItem.getServiceId());
        intent.putExtra(StaticData.IS_PAY_COUPON, false);
        intent.putExtra(StaticData.LATITUDE, latitude);
        intent.putExtra(StaticData.LONGITUDE, longitude);
        startActivityForResult(intent, REQUEST_CODE_COUPON);
    }

    /**
     * 红包显示
     *
     * @param commonCouponEntity
     */
    @Override
    public void renderCoupon(CommonCouponEntity commonCouponEntity) {
        if (commonCouponEntity != null && commonCouponEntity.getCouponDetailses() != null && commonCouponEntity.getCouponDetailses().get(0) != null) {
            CommonCouponEntity.CouponDetails couponDetails = commonCouponEntity.getCouponDetailses().get(0);
            if (!TextUtils.isEmpty(couponDetails.getAmount()) && !TextUtils.isEmpty(couponDetails.getDiscount())) {
                couponRel.setVisibility(View.VISIBLE);
                if (TextUtils.equals(couponDetails.getAmount(), "0")) {
                    couponRelief.setText(KeywordUtil.matcherActivity(Color.parseColor("#f56165"), "红包优惠：无门槛 减" + couponDetails.getDiscountAmount() + "元"));
                } else {
                    couponRelief.setText(KeywordUtil.matcherActivity(Color.parseColor("#f56165"), "红包优惠：满" + couponDetails.getAmount() + "元 减" + couponDetails.getDiscountAmount() + "元"));
                }
            }
        }
    }

    /**
     * 显示红包
     *
     * @param commonCouponInfo
     */
    @Override
    public void renderCouponInfo(CommonCouponInfo commonCouponInfo) {
        this.commonCouponInfo = commonCouponInfo;
        if (commonCouponInfo == null) {
            couponRelief.setText(R.string.no_coupon);
        } else {
            submitTotalPrice();
        }
    }

    @Override
    public void netWrokError() {

    }


    /**
     * 提交订单
     */
    private void placeOrder() {
        if (TextUtils.isEmpty(addressId)) {
            showMessage("请选择服务地址");
            return;
        }
        if (serviceItem == null && !TextUtils.isEmpty(merchantOrWorkerId) || !isMerchantService) //非标订单有地址，没有服务列表提示
        {
            showMessage("该商家无法服务此地址，请选择其他服务商");
            return;
        }
        if (serviceItem == null) {
            showMessage("请选择服务类型");
            return;
        }
        if (TextUtils.isEmpty(serviceNumberEdit.getText().toString()) || Double.valueOf(serviceNumberEdit.getText().toString()) == 0) {
            showMessage("请填写服务数量");
            return;
        }

        if (!mainDemand && specialType1.equals("1")) {
            showMessage("请选择主要需求");
            return;
        }
        if (serviceScheduleEntity == null || serviceTime == null) {
            showMessage("请选择服务时间");
            return;
        }
        PlaceOrderParam placeOrderParam = new PlaceOrderParam();
        placeOrderParam.setServiceId(serviceItem.getServiceId());
        placeOrderParam.setSpecialType(serviceItem.getSpecialType());
        placeOrderParam.setOrderFrom("0");
        placeOrderParam.setContact(addressEntity.getContact());
        placeOrderParam.setGender(addressEntity.getGender());
        placeOrderParam.setPhoneNumber(addressEntity.getPhoneNumber());
        placeOrderParam.setServiceAddressId(addressEntity.getId());
        placeOrderParam.setTotal(String.valueOf(currentDecimal));

        //指派工人
        if (TextUtils.isEmpty(merchantOrWorkerId) || merchantOrWorkerId.equals("-1")) {
            placeOrderParam.setWorkerOrMerchantUserId(workerOrMerchantUserId);
        } else {
            //指定服务商
            placeOrderParam.setWorkerOrMerchantUserId(merchantOrWorkerId);
        }
        placeOrderParam.setServiceStartAt(getFormatServiceTime(serviceScheduleEntity, serviceTime));
        if (!TextUtils.isEmpty(serviceItem.getSellType()) && TextUtils.equals(serviceItem.getSellType(), "1")) {
//            placeOrderParam.setPrice(serviceItem.getPrice());
            placeOrderParam.setPrice(mServerNumberPrice);
        } else {
            placeOrderParam.setPrice(serviceItem.getDepositAmount());
        }
        placeOrderParam.setSellType(serviceItem.getSellType());
        String remarkStr;
        if (specialType1.equals("1")) {  //特殊服务备注信息 如育儿嫂等
            remarkStr = demandRemark;
        } else {//普通服务备注
            remarkStr = remarkText.getText().toString();
        }

        if (insuranceCheck) {
            placeOrderParam.setIsClaims("1");
        } else {
            placeOrderParam.setIsClaims("0");
        }
        if (!TextUtils.isEmpty(remarkStr) && !TextUtils.equals(remarkStr, "备注留言")) {
            placeOrderParam.setServiceContent(remarkStr);
        } else {
            placeOrderParam.setServiceContent("");
        }
        queryServicePricePresenter.getPlaceOrder(placeOrderParam);
    }

    /**
     * 服务时间
     *
     * @param scheduleEntity
     * @param serviceTime
     * @return
     */

    public String getFormatServiceTime(ServiceScheduleEntity scheduleEntity, Time serviceTime) {
        String date = scheduleEntity.getDate();
        int end = date.indexOf("今") > 0 ? date.indexOf("今") : (date.indexOf("明") > 0 ? date.indexOf("明") : date.indexOf("周"));
        String subDate = date.substring(0, end);
        return subDate + " " + serviceTime.getTime();
    }

    /**
     * 查看保险协议
     */
    private InsuranceExplainDialog insuranceExplainDialog;

    private void seeInsuranceAgreement() {
        if (!TextUtils.isEmpty(claimsNoteTitle) && !TextUtils.isEmpty(claimsNoteContent)) {
            if (insuranceExplainDialog == null) {
                insuranceExplainDialog = new InsuranceExplainDialog.Builder()
                        .setTitle(claimsNoteTitle)
                        .setContent(claimsNoteContent)
                        .showButton(false)
                        .setConfirmButton("好的", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                insuranceExplainDialog.dismissDialog();
                            }
                        })
                        .setInsuranceAgreementText(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!TextUtils.isEmpty(claimsNoteUrl)) {
                                    mNavigator.loadWebView(getActivity(), claimsNoteUrl, "保险协议");
                                }
                            }
                        }).create();
            }
            insuranceExplainDialog.show(getFragmentManager(), null);
        }
    }


    /**
     * 最优活动
     *
     * @param activityNgInfoNew
     * @param totalPrice
     * @return
     */
    private String discountActivity(ActivityNgInfoNew activityNgInfoNew, String totalPrice) {
        if (activityNgInfoNew != null && activityNgInfoNew.isSpecialSatisfied(Float.valueOf(totalPrice))) {
            BigDecimal minusDecimal = new BigDecimal("0").setScale(2, RoundingMode.HALF_UP);
            for (ActivityNgInfoNew.ActivityNgDetail activityNgDetail : activityNgInfoNew.getSatisfiedSpecialActivityList()) {
                String addMinus = activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus();
                BigDecimal addMinusDecimal = new BigDecimal(addMinus).setScale(2, RoundingMode.HALF_UP);
                minusDecimal = minusDecimal.add(addMinusDecimal);
            }
            return String.valueOf(minusDecimal);
        } else {
            return "";
        }
    }

    /**
     * 返回红包值
     *
     * @param commonCouponInfo
     * @param totalPrice
     * @return
     */
    private String displayCoupon(CommonCouponInfo commonCouponInfo, String totalPrice) {
        if (commonCouponInfo != null && commonCouponInfo.getCommonCouponEntityList() != null && !commonCouponInfo.getCommonCouponEntityList().isEmpty()) {
            CommonCouponEntity commonCouponEntity = getServiceBigCoupon(commonCouponInfo.getCommonCouponEntityList(), totalPrice);
            if (commonCouponEntity != null && commonCouponEntity.getCouponDetailses() != null && !commonCouponEntity.getCouponDetailses().isEmpty() && commonCouponEntity.getCouponDetailses().get(0) != null) {
                CommonCouponEntity.CouponDetails couponDetails = commonCouponEntity.getCouponDetailses().get(0);
                if (!TextUtils.isEmpty(couponDetails.getDiscountAmount())) {
                    return couponDetails.getDiscountAmount();
                }
            } else {
                return "";
            }
        }
        return "";
    }

    /**
     * 符合指定服务的最大红包
     */
    public CommonCouponEntity getServiceBigCoupon(List<CommonCouponEntity> commonCouponEntityList, String totalPrice) {
        CommonCouponEntity resultCoupon = null;
        if (commonCouponEntityList != null) {
            for (CommonCouponEntity commonCouponEntity : commonCouponEntityList) {
                if (commonCouponEntity != null) {
                    if (commonCouponEntity.getCouponDetailses() != null && commonCouponEntity.getCouponDetailses().get(0) != null) {
                        CommonCouponEntity.CouponDetails couponDetails = commonCouponEntity.getCouponDetailses().get(0);
                        if (couponDetails != null && TextUtils.equals(Constant.DiscountTypeFullCut, couponDetails.getDiscountType()) && !TextUtils.isEmpty(couponDetails.getAmount()) && Double.parseDouble(totalPrice) >= Double.parseDouble(couponDetails.getAmount())) {
                            resultCoupon = bigCoupon(resultCoupon, commonCouponEntity);
                            couponId = resultCoupon.getId();//获取筛选出的优惠卷ID
                        }
                    }
                }
            }
        }
        return resultCoupon;
    }

    /**
     * 比较优惠大小 选出最大的
     *
     * @param lastCoupon
     * @param currCoupon
     * @return
     */
    private CommonCouponEntity bigCoupon(CommonCouponEntity lastCoupon, CommonCouponEntity currCoupon) {
        if (lastCoupon == null) {
            return currCoupon;
        } else {
            if (lastCoupon.getCouponDetailses() != null && currCoupon.getCouponDetailses() != null) {
                CommonCouponEntity.CouponDetails lastDetail = lastCoupon.getCouponDetailses().get(0);
                CommonCouponEntity.CouponDetails currDetail = currCoupon.getCouponDetailses().get(0);
                if (lastDetail != null && currDetail != null) {
                    if (!TextUtils.isEmpty(lastDetail.getDiscountAmount()) && !TextUtils.isEmpty(currDetail.getDiscountAmount())) {
                        if (Double.parseDouble(lastDetail.getDiscountAmount()) - Double.parseDouble(currDetail.getDiscountAmount()) <= 0) {
                            return currCoupon;
                        } else {
                            return lastCoupon;
                        }
                    } else if (TextUtils.isEmpty(lastDetail.getDiscountAmount()) && !TextUtils.isEmpty(currDetail.getDiscountAmount())) {
                        return currCoupon;
                    } else if (!TextUtils.isEmpty(lastDetail.getDiscountAmount()) && TextUtils.isEmpty(currDetail.getDiscountAmount())) {
                        return lastCoupon;
                    } else {
                        return currCoupon;
                    }
                } else if (lastDetail == null && currDetail != null) {
                    return currCoupon;
                } else if (lastDetail != null && currDetail == null) {
                    return lastCoupon;
                } else {
                    return currCoupon;
                }
            } else if (lastCoupon.getCouponDetailses() == null && currCoupon.getCouponDetailses() != null) {
                return currCoupon;
            } else if (lastCoupon.getCouponDetailses() != null && currCoupon.getCouponDetailses() == null) {
                return lastCoupon;
            } else {
                return currCoupon;
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.remark_text && canVerticalScroll(remarkText))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }
        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

    /**
     * 主要需求
     */
    @OnClick(R.id.rl_main_demand)
    public void onViewClicked() {
        if (TextUtils.isEmpty(specialTypeUrl))
            return;
        PushInfo pushInfo = new PushInfo();
        pushInfo.setIsShare("");
        pushInfo.setUrl(specialTypeUrl);
        WebViewActivity.start(mContext, pushInfo);
    }

}
