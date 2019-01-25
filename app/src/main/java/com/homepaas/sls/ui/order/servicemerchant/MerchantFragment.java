package com.homepaas.sls.ui.order.servicemerchant;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.data.entity.BusinessOrderServiceListEntity;
import com.homepaas.sls.data.entity.BusinessSecServiceEntity;
import com.homepaas.sls.data.entity.BusinessServiceListEntity;
import com.homepaas.sls.domain.entity.BusinessCommentListOutput;
import com.homepaas.sls.domain.entity.LocationEntity;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.newmvp.contract.MerchantContract;
import com.homepaas.sls.newmvp.presenter.MerchantPresenter;
import com.homepaas.sls.ui.adapter.FilterPPListAdapter;
import com.homepaas.sls.ui.common.fragment.CommonMvpLazyLoadFragment;
import com.homepaas.sls.ui.location.RegisterAddressMapActivity;
import com.homepaas.sls.ui.location.location.AddressModel;
import com.homepaas.sls.ui.location.location.GeoCoderHelper;
import com.homepaas.sls.ui.location.location.LocationHelper;
import com.homepaas.sls.ui.location.location.SuggestionAddressModel;
import com.homepaas.sls.ui.login.FastLoginActivity;
import com.homepaas.sls.ui.order.adapter.MerchantAdapter;
import com.homepaas.sls.ui.order.directOrder.CommonPlaceOrderActivity;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.networkerrorview.CommonNetWorkErrorViewReplacer;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.PermissionUtils;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.homepaas.sls.util.SystemUtil;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.runtimepermission.acp.AcpListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mhy on 2017/12/25.
 */

public class MerchantFragment extends CommonMvpLazyLoadFragment<MerchantContract.Presenter> implements LocationHelper.OnLocatedListener, BaseRecyclerAdapter.OnItemClickListener, MerchantAdapter.onMerchantClickListener, MerchantContract.View, HeaderViewLayout.OnRefreshListener {

    @Bind(R.id.img_1)
    ImageView img1;
    @Bind(R.id.tv_location_address)
    TextView tvLocationAddress;
    @Bind(R.id.img_2)
    ImageView img2;
    @Bind(R.id.rl_user_location)
    RelativeLayout rlUserLocation;
    @Bind(R.id.ly_filtrate)
    LinearLayout lyFiltrate;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @Bind(R.id.btn_app_recommend)
    Button btnAppRecommend;
    @Bind(R.id.rl_content)
    RelativeLayout rlContent;
    @Bind(R.id.img_filtrate_flag)
    ImageView imgFiltrateFlag;
    RelativeLayout rlFilterLayout;

    private static final int REQUESTCODE_GETADDRESS = 0xf8;
    private static final int MERCHANT_DETAIL_ACTIVITY = 300;
    private boolean isOrderActivity;//判断进入该界面的入口， true表示下单进入，false表示非标订单详情页进入
    private ServiceItem serviceItem;
    private ObjectAnimator anim = null;
    private CommonDialog commonDialog;
    private int filtrateFlag = 0;
    private int currentIndex = -1;
    private int currentPage = 1;
    private int pageSize = 10;
    private String tagId;//：选填，用于标签筛选
    private String secondLevelServiceID;
    private boolean isLocationSuccess;//是否定位成功
    private double latitude;
    private double longitude;

    private CommonNetWorkErrorViewReplacer commonNetWorkErrorViewReplacer;
    private MerchantAdapter merchantAdapter;
    private FilterPPListAdapter filterPPListAdapter;
    private LocationHelper mLocationHelper;
    private LocationEntity locationEntity;
    private GeoCoderHelper geoCoderHelper;
    private CommonAppPreferences commonAppPreferences;

    public static MerchantFragment newInstance(ServiceItem serviceItem, boolean isOrderActivity, String secondLevelServiceID) {
        Bundle args = new Bundle();
        args.putSerializable("serviceItem", serviceItem);
        args.putBoolean(StaticData.IS_ORDER_ACTIVITY, isOrderActivity);
        args.putString("secondLevelServiceID", secondLevelServiceID);
        MerchantFragment fragment = new MerchantFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchant;
    }

    @Override
    protected void initView() {
        LogUtils.i("MerchantFragment:initView");
        geoCoderHelper = new GeoCoderHelper();
        commonAppPreferences = new CommonAppPreferences(mContext);
        Bundle arguments = getArguments();
        serviceItem = (ServiceItem) arguments.getSerializable("serviceItem");
        secondLevelServiceID = arguments.getString("secondLevelServiceID");
        isOrderActivity = arguments.getBoolean(StaticData.IS_ORDER_ACTIVITY);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        merchantAdapter = new MerchantAdapter(mContext);
        merchantAdapter.setOnMerchantClickListener(this);
        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST, 10, R.color.common_background_color));
        recyclerView.setAdapter(merchantAdapter);

        commonNetWorkErrorViewReplacer = new CommonNetWorkErrorViewReplacer(mContext, refreshLayout, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (isLocationSuccess)
                    updateList();
                else
                    requestLocation();
            }
        });
        initPPWindow();

        if (isOrderActivity) {
            initData();
        }
    }

    @Override
    protected void initData() {
        LogUtils.i("MerchantFragment:initData");
        locationEntity = PreferencesUtil.getObject(StaticData.ADDRESS_LOCATION, LocationEntity.class);
        final String city = commonAppPreferences.getCity();
        //此处定位和首页定位保持一致，互相覆盖。如果用户在首页选了城市，没用当前定位，默认用他选择城市的人民政府的位置，
        // 此时商家左上角显示为：XX市人民政府。当没有也获取不了用户地址时，就用北京市人民政府的地址。

        //有上次定位信息，并且上次的城市和首页的城市是一样的使用上次
        if (locationEntity != null && city.equals(locationEntity.getCity())) {
            latitude = locationEntity.getLatitude();
            longitude = locationEntity.getLongitude();
            setLocationAddress(locationEntity.getAddress());
            updateList();
            LocationEntity entity = new LocationEntity(locationEntity.getAddress(), city, longitude, latitude);
            PreferencesUtil.saveObject(StaticData.ADDRESS_LOCATION, entity);
            EventBus.getDefault().post(locationEntity);//通知首页同步定位信息
            isLocationSuccess = true;
        } else {
            //没有历史定位信息，使用定位获取
            requestLocation();
        }
    }


    private void requestLocation() {
        PermissionUtils.requestLocationPermission(mContext, new AcpListener() {
            @Override
            public void onGranted() {
                //开始定位
                location();
            }

            @Override
            public void onDenied(List<String> permissions) {
                showLocationError();
            }
        });
    }


    private void location() {
        mLocationHelper = LocationHelper.sharedInstance(getContext());
        mLocationHelper.addOnLocatedListener(this);
        mLocationHelper.start();
    }

    private void setLocationAddress(String address) {
        if (!TextUtils.isEmpty(address)) {
            if (address.length() > 8) {
                address = address.substring(0, 7) + "...";
            }
            tvLocationAddress.setText(address);
        } else {
            tvLocationAddress.setText("定位失败");
        }
    }

    @Override
    protected MerchantContract.Presenter getPresenter() {
        return new MerchantPresenter();
    }

    @OnClick({R.id.rl_user_location, R.id.ly_filtrate, R.id.btn_app_recommend, R.id.rl_filter_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_user_location:
                //地图界面
                startActivityForResult(new Intent(mContext, RegisterAddressMapActivity.class), REQUESTCODE_GETADDRESS);
                break;
            case R.id.ly_filtrate:
                //筛选菜单
                if (filtrateFlag % 2 == 0) {//打开筛选菜单
                    showPPWindow();
                } else {
                    closePPWindow();
                }
                break;
            case R.id.btn_app_recommend:
                //助家推荐商户
                if (PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE)) {
                    if (isOrderActivity) {
                        Activity activity = (Activity) mContext;
                        activity.setResult(Activity.RESULT_OK, new Intent().putExtra(StaticData.MERCHANT_ID, "-1").putExtra(StaticData.MERCHANT_NAME, ""));
                        activity.finish();
                    } else {
                        CommonPlaceOrderActivity.start(getActivity(), serviceItem.getServiceId(), serviceItem.getServiceName(), serviceItem.getOrderType(), "-1", "", serviceItem.getServiceId(), "",secondLevelServiceID);
                    }
                } else {
                    startActivity(new Intent(mContext, FastLoginActivity.class));
                }
                break;
            case R.id.rl_filter_layout:
                closePPWindow();
                break;
        }
    }

    private void showPPWindow() {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        anim = ObjectAnimator.ofFloat(imgFiltrateFlag, "rotation", 0f, 180f);
        anim.setDuration(100).start();
        filtrateFlag++;
        rlFilterLayout.setVisibility(View.VISIBLE);
    }

    private void closePPWindow() {
        anim = ObjectAnimator.ofFloat(imgFiltrateFlag, "rotation", 180f, 0f);
        anim.setDuration(100).start();
        filtrateFlag++;
        rlFilterLayout.setVisibility(View.GONE);
    }

    private void initPPWindow() {
        filterPPListAdapter = new FilterPPListAdapter(mContext);
        filterPPListAdapter.setOnItemClickListener(this);
        rlFilterLayout = (RelativeLayout) mView.findViewById(R.id.rl_filter_layout);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.rv_service_filter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(filterPPListAdapter);
    }

    public void updateList() {
        currentPage = 1;
        LogUtils.i("MerchantFragment:mPresenter\t" + mPresenter);
        mPresenter.getBusinessOrderServiceList(serviceItem.getServiceId(), latitude + "", longitude + "", currentPage + "",
                pageSize + "", tagId);
    }

    public void loadList() {
        currentPage++;
        mPresenter.getBusinessOrderServiceList(serviceItem.getServiceId(), latitude + "", longitude + "", currentPage + "", pageSize + "", "");
    }

    @Override
    public void onRefresh() {
        updateList();
    }

    @Override
    public void onLoadMore() {
        loadList();
    }


    @Override
    public void onDestroyView() {
        LogUtils.i("MerchantFragment:onDestroyView");
        if (refreshLayout != null) {
            refreshLayout.stopRefresh();
            refreshLayout.destory();
        }
        if (mLocationHelper != null) {
            mLocationHelper.removeOnLocatedListener(this);
            mLocationHelper.cancel();
        }
        if (geoCoderHelper != null) {
            geoCoderHelper.clear();
        }
        if (anim != null) {
            anim.cancel();
        }
        super.onDestroyView();
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        refreshLayout.stopRefresh();
        if (merchantAdapter.getData() == null || merchantAdapter.getData().size() == 0) {
            commonNetWorkErrorViewReplacer.showErrorLayout();
        }
    }


    /**
     * 接受地址回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUESTCODE_GETADDRESS) {
                int type = data.getIntExtra("type", 0);
                AddressModel addressModel;
                switch (type) {
                    case 1://地图移动坐标地址[用户当前地址]
                        addressModel = data.getParcelableExtra("addressModel");
                        if (addressModel == null)
                            return;
                        String replace = addressModel.resultAdress.replace(addressModel.province + addressModel.city + addressModel.district, "");
                        setLocationAddress(replace);
                        latitude = addressModel.latLng.latitude;
                        longitude = addressModel.latLng.longitude;
                        saveLocationInfo(replace, addressModel.city);
                        break;
                    case 2://poi地址[搜索框搜索地址]
                        addressModel = data.getParcelableExtra("addressModel");
                        if (addressModel == null)
                            return;
                        String poiName = addressModel.poiName;
                        setLocationAddress(poiName);
                        latitude = addressModel.latLng.latitude;
                        longitude = addressModel.latLng.longitude;
                        saveLocationInfo(poiName, addressModel.city);
                        break;
                    case 3://搜索地址[当前地址附近地址列表]
                        final SuggestionAddressModel suggestionAddressModell = data.getParcelableExtra("addressModel");
                        latitude = suggestionAddressModell.getLatLng().latitude;
                        longitude = suggestionAddressModell.getLatLng().longitude;
                        geoCoderHelper.setLatLng(suggestionAddressModell.getLatLng());
                        geoCoderHelper.setOnReverseGeoCodeResultListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
                            @Override
                            public void result(Object obj, LatLng latLng) {
                                ReverseGeoCodeResult reverseGeoCodeResult = (ReverseGeoCodeResult) obj;
                                ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
                                String str = reverseGeoCodeResult.getAddress().replace(addressDetail.province + addressDetail.city + addressDetail.district, "") + suggestionAddressModell.getKey();
                                saveLocationInfo(str, addressDetail.city);
                            }
                        });
                        geoCoderHelper.reverseGeoCode();
                        break;
                }
                updateList();
            } else if (requestCode == MERCHANT_DETAIL_ACTIVITY) { //下单页进入服务商界面在进入服务商详情界面，服务商详情界面的服务tab中预约单击返回到下单页进行刷新数据
                Activity activity = (Activity) mContext;
                activity.setResult(Activity.RESULT_OK, data);
                activity.finish();
            }
        }
    }


    /**
     * 保存最新定位信息并通知首页定位信息同步
     * <p>
     * 暂时不和首页同步
     *
     * @param str
     * @param city
     */
    public void saveLocationInfo(String str, String city) {
        isLocationSuccess = true;
        setLocationAddress(str);
//        LocationEntity locationEntity = new LocationEntity(str, city, longitude, latitude);
//        PreferencesUtil.saveObject(StaticData.ADDRESS_LOCATION, locationEntity);
//        EventBus.getDefault().post(locationEntity);//通知首页同步定位信息
    }


    @Override
    public void onItemClick(View itemView, int pos) {
        if (currentIndex != -1 && currentIndex == pos)
            return;
        currentIndex = pos;
        filterPPListAdapter.setCurrentSelectPos(pos);
        closePPWindow();
        tagId = filterPPListAdapter.getData().get(pos).getId();
        updateList();
    }

    @Override
    public void callPhone(final String phone) {
        // 拨打电话
        PermissionUtils.callPhonePermission(mContext, new AcpListener() {
            @Override
            public void onGranted() {
                NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, "拨打商家电话");
                serviceFragment.show(getChildFragmentManager(), null);
            }

            @Override
            public void onDenied(List<String> permissions) {
                showMessage("拨打电话需要权限,请到设置中心");
            }
        });
    }

    /**
     * 三级商品预约下单
     *
     * @param serviceId           三级服务id
     * @param orderProviderUserId 指定服务商或工人的id
     * @param serviceName
     * @param providerName
     */
    @Override
    public void subscribe(String serviceId, String orderProviderUserId, String serviceName, String providerName, String providerUserType) {
        if (PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE)) {
            //指定服务商下单
            if (isOrderActivity) {
                Activity activity = (Activity) mContext;
                activity.setResult(Activity.RESULT_OK, new Intent().putExtra(StaticData.MERCHANT_ID, orderProviderUserId)
                        .putExtra(StaticData.MERCHANT_NAME, providerName).putExtra(StaticData.SERVICE_ID, serviceId)
                        .putExtra(StaticData.SECOND_LEVEL, serviceItem.getServiceId())
                        .putExtra(StaticData.PROVIDER_USER_TYPE, providerUserType));
                activity.finish();
            } else {
                CommonPlaceOrderActivity.start(getActivity(), serviceId, serviceName, serviceItem.getOrderType(), orderProviderUserId, providerName, serviceItem.getServiceId(), providerUserType,secondLevelServiceID);
            }
        } else {
            startActivity(new Intent(mContext, FastLoginActivity.class));
        }
    }

    @Override
    public void startMerchantDetail(String userType, String merchantOrWorkerId) {
        if (isOrderActivity) {
            Intent starter = new Intent(mContext, MerchantDetailActivity.class);
            starter.putExtra("userType", userType);
            starter.putExtra("merchantOrWorkerId", merchantOrWorkerId);
            starter.putExtra(StaticData.IS_ORDER_ACTIVITY, isOrderActivity);
            starter.putExtra(StaticData.ORDER_TYPE, serviceItem.getOrderType());
            starter.putExtra(StaticData.SECOND_LEVEL, serviceItem.getServiceId());
            starter.putExtra(StaticData.LATITUDE, latitude);
            starter.putExtra(StaticData.LONGITUDE, longitude);
            startActivityForResult(starter, MERCHANT_DETAIL_ACTIVITY);
        } else {
            //进入商户或工人详情页
            MerchantDetailActivity.start(mContext, userType, merchantOrWorkerId, isOrderActivity, serviceItem.getOrderType(), serviceItem.getServiceId(),latitude,longitude);
        }
    }

    @Override
    public void initBusinessOrderServiceList(BusinessOrderServiceListEntity businessOrderServiceListEntity) {
        refreshLayout.stopRefresh();
        //绑定筛选标签列表数据
        if (filterPPListAdapter.getData() == null || filterPPListAdapter.getData().size() == 0) {
            businessOrderServiceListEntity.getTags().add(0, new BusinessOrderServiceListEntity.ServiceTag("", "所有服务类型", true));
            filterPPListAdapter.setData(businessOrderServiceListEntity.getTags());
        }

        if ((businessOrderServiceListEntity == null || businessOrderServiceListEntity.getList() == null
                || businessOrderServiceListEntity.getList().size() == 0)
                && currentPage == 1) {
            commonNetWorkErrorViewReplacer.showEmptyView(R.string.empty_hint4);
            refreshLayout.setCanLoadMore(false);
            return;
        }
        List<BusinessOrderServiceListEntity.ListBean> chooseWorkerOrMerchantInfos = businessOrderServiceListEntity.getList();
        if (currentPage == 1)//刷新
        {
            merchantAdapter.updateList(chooseWorkerOrMerchantInfos);
        } else {//加载
            merchantAdapter.append(chooseWorkerOrMerchantInfos);
        }
        commonNetWorkErrorViewReplacer.showOriginalLayout();
        //是否可以加载
        if (merchantAdapter.getData().size() >= businessOrderServiceListEntity.getCount()) {
            refreshLayout.setCanLoadMore(false);
        } else {
            refreshLayout.setCanLoadMore(true);
        }
    }

    @Override
    public void onLocated(BDLocation bdLocation) {
        LogUtils.i("MerchantFragment:onLocated");
        int status = bdLocation.getLocType();
        if (status == BDLocation.TypeServerError || status == BDLocation.TypeNetWorkException || status == BDLocation.TypeCriteriaException) {
            showLocationError();
        } else {
            String address = bdLocation.getCountry() + bdLocation.getProvince() + bdLocation.getCity() + bdLocation.getDistrict();
            String addrStr = bdLocation.getAddrStr();
            //显示定位地址
            String replace = addrStr.replace(address, "");
            SystemUtil.threadStatus();
            final String city = commonAppPreferences.getCity();
            //有历史信息且首页和定位的城市不是同一座时使用首页的城市信息
            if (locationEntity != null && !commonAppPreferences.getCity().equals(bdLocation.getCity()) && !commonAppPreferences.getCity().equals(getResources().getString(R.string.location_error))) {
                //历史定位城市和首页城市不一致，使用首页的城市地址
                final String cityGovernment;//城市政府名称
                cityGovernment = city + "人民政府";
                //通过地理编码获取对应城市市政府的经纬度
                geoCoderHelper.setAddress(cityGovernment, city);
                geoCoderHelper.setOnGeoCodeResultListener(new GeoCoderHelper.onGeoCodeResultListener() {
                    @Override
                    public void result(Object obj, LatLng latLng) {
                        LogUtils.i("MerchantFragment:result");
                        GeoCodeResult geoCodeResult = (GeoCodeResult) obj;
                        latitude = geoCodeResult.getLocation().latitude;
                        longitude = geoCodeResult.getLocation().longitude;
                        updateList();
                        saveLocationInfo(cityGovernment, city);
                    }
                });
                geoCoderHelper.geoCode();
            } else {
                latitude = bdLocation.getLatitude();
                longitude = bdLocation.getLongitude();
                List<Poi> poiList = bdLocation.getPoiList();
                if (poiList != null && poiList.size() > 0) {
                    replace = poiList.get(0).getName();
                }
                saveLocationInfo(replace, bdLocation.getCity());
                //刷新定位相关服务数据
                updateList();
            }
        }
    }


    /**
     * 定位失败显示北京政府地址
     * 失败的原因有 没有开启定位权限和开启定位权限后没有开启gps或者百度SDK定位失败
     */
    private void showLocationError() {
        //定位失败
//        if (mContext.getResources().getString(R.string.location_error)) {
        String defaultAddress = "北京市人民政府";
        tvLocationAddress.setText(defaultAddress);
        longitude = 116.413787;
        latitude = 39.910524;
        saveLocationInfo(defaultAddress, "北京市");
        updateList();
//        }

        commonDialog = new CommonDialog.Builder()
                .setContent("未找到您的地理位置,请手动选择")
                .setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commonDialog.dismiss();
                    }
                })
                .setConfirmButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //地图界面
                        startActivityForResult(new Intent(mContext, RegisterAddressMapActivity.class), REQUESTCODE_GETADDRESS);
                    }
                }).setTitle("")
                .create();
        commonDialog.showAllowingStateLoss(getChildFragmentManager(), null);
    }


    @Override
    public void onModeChanged(int mode) {

    }

    @Override
    public void initMerchantOrWorkerInfo(BusinessExInfoOutput businessExInfoOutput) {

    }

    @Override
    public void initBusinessSecService(BusinessSecServiceEntity businessSecServiceEntity) {

    }

    @Override
    public void initBusinessServiceList(BusinessServiceListEntity businessServiceListEntity) {

    }

    @Override
    public void initBusinessCommentList(BusinessCommentListOutput businessCommentListOutput) {
    }

}
