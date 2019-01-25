package com.homepaas.sls.ui.homepage_3_3_0;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.data.entity.HomeOrderStatusEntity;
import com.homepaas.sls.data.entity.OrderListPopEntity;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.di.component.DaggerHomePageComponent;
import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.CityDetail;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.LocationEntity;
import com.homepaas.sls.domain.entity.RecommendServiceEntity;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.domain.entity.ShortCutEntity;
import com.homepaas.sls.domain.entity.SuperDiscountEntity;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.mvp.model.HomeListData;
import com.homepaas.sls.mvp.presenter.HomePagePresenter;
import com.homepaas.sls.mvp.presenter.MessagePresenter;
import com.homepaas.sls.mvp.view.HomePageView;
import com.homepaas.sls.mvp.view.UnReadMessageCountView;
import com.homepaas.sls.pushservice.PushUtil;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.homepage_3_3_0.adapter.HotServiceAdapter;
import com.homepaas.sls.ui.homepage_3_3_0.adapter.NavigationAdapter;
import com.homepaas.sls.ui.homepage_3_3_0.adapter.SuperDiscountAdapter;
import com.homepaas.sls.ui.location.location.LocationHelper;
import com.homepaas.sls.ui.order.chooseservice.NewChooseServiceActivity;
import com.homepaas.sls.ui.order.directOrder.ClientOrderDetailActivity;
import com.homepaas.sls.ui.order.directOrder.CommonPlaceOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ConfirmOrderActivity;
import com.homepaas.sls.ui.order.servicemerchant.ServiceMerchantActivity;
import com.homepaas.sls.ui.search.AllCategoriesActivity;
import com.homepaas.sls.ui.search.CommonSearchServiceActivity;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.ui.widget.ReboundScrollView;
import com.homepaas.sls.ui.widget.networkerrorview.CommonNetWorkErrorViewReplacer;
import com.homepaas.sls.ui.widget.newbanner.GlideImageLoader;
import com.homepaas.sls.util.DensityUtil;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.PermissionUtils;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.homepaas.sls.util.WindowUtil;
import com.hyphenate.helpdesk.easeui.ui.BaseFragment;
import com.runtimepermission.acp.AcpListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.SettingDialog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import me.henrytao.smoothappbarlayout.SmoothAppBarLayout;

/**
 * A simple {@link BaseFragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomePageFragment extends CommonBaseFragment implements HomePageView, ReboundScrollView.OnScrollTouchEvent, HotServiceAdapter.OnHandleServiceItem, OnBannerListener, NavigationAdapter.OnCheckMoreService, UnReadMessageCountView, HotServiceAdapter.OnDrawPopuListner, SuperDiscountAdapter.OnTakeTheDiscount {

    @Bind(R.id.hot_search_link)
    RecyclerView hotSearchLink;
    @Bind(R.id.nagavition_recyclerview)
    RecyclerView nagavitionRecyclerview;
    @Bind(R.id.scroll_content)
    ReboundScrollView scrollContent;
    @Bind(R.id.search_edit_text)
    TextView searchEditText;
    @Bind(R.id.go_to_search)
    LinearLayout goToSearch;
    @Bind(R.id.menu_service)
    ImageView menuService;
    @Bind(R.id.unreadIcon)
    View unreadIcon;
    @Bind(R.id.message_entry)
    FrameLayout messageEntry;
    @Bind(R.id.toolbar)
    LinearLayout toolbar;
    @Bind(R.id.tool_bar)
    Toolbar toolBar;
    @Bind(R.id.collapsing_bar)
    CollapsingToolbarLayout collapsingBar;
    @Bind(R.id.appbar)
    SmoothAppBarLayout appbar;
    @Bind(R.id.content)
    CoordinatorLayout content;
    @Bind(R.id.hot_fl)
    FrameLayout hotFl;
    //超级优惠 【只有在用户登录的情况下才会有】
    @Bind(R.id.super_discount_link)
    RecyclerView superDiscountLink;
    @Bind(R.id.super_discount)
    LinearLayout superDiscount;
    @Bind(R.id.super_discount_title)
    TextView superDiscountTitle;
    @Bind(R.id.location)
    TextView location;
    @Bind(R.id.banner_layout)
    Banner bannerLayout;
    @Bind(R.id.rl_anim_target)
    RelativeLayout rlAnimTarget;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.ly_home_order_status)
    LinearLayout lyHomeOrderStatus;
    @Bind(R.id.tv_select_all_service)
    TextView tvSelectAllService;

    private BDLocation curtBDLocation;
    private List<TextView> addPopuView;
    private List<Integer> colors;
    private LocationHelper mLocationHelper;
    private HotServiceAdapter hotServiceAdapter;
    private NavigationAdapter navigationAdapter;
    private SuperDiscountAdapter superDiscountAdapter;
    private List<BannerInfo> bannerInfos;
    private List<String> bannerImages;
    private CommonAppPreferences commonAppPreferences;
    private LayoutInflater layoutInflater;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    HomePagePresenter homePagePresenter;
    @Inject
    MessagePresenter messagePresenter;
    @Inject
    protected PushUtil pushUtil;

    static Gson gson = new Gson();
    private CommonNetWorkErrorViewReplacer commonNetWorkErrorViewReplacer;
    private HomeOrderStatusEntity homeOrderStatusEntity;

    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private static final int REQUEST_CODE_CAMERA = 4;
    private static final int REFRESS_LOCATION_CODE = 3;
    private static final int REQUEST_MESSAGE = 2;
    public static final int ANIM_TIME = 500;
    private boolean isSelectedLocation = false;
    //该页面的与location相关的接口都引用curxxxx
    private String curLongtitude;
    private String curLatitude;
    private String curCity = "";
    private CityDetail locationCityDetail;
    private String lastLongtitude;
    private String lastLatitude;
    private String lastCity;
    private boolean isHomeData = false;//判断首页列表是否绑定过数据
    private boolean bannerIsStart; //广告栏是否开始播放
    private float startY = 0;
    //浮层是否展开
    private boolean supernatantIsShow = false;

    public HomePageFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomePageFragment.
     */
    public static HomePageFragment newInstance() {
        LogUtils.i("HomePageFragment newInstance");
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        LogUtils.i("TAG", "HomePageFragment:onCreate()");
    }


    @Override
    protected void initializeInjector() {
        DaggerHomePageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page;
    }


    @Override
    protected void initView() {

        LogUtils.i("HomePageFragment:initView");
        scrollContent.setOnScrollTouchEvent(this);
        commonAppPreferences = new CommonAppPreferences(getActivity());
        homePagePresenter.setHomePageView(this);
        messagePresenter.setUnReadMessageCountView(this);

        //获取上一次定位信息
        lastLatitude = commonAppPreferences.getLatitude();
        lastLongtitude = commonAppPreferences.getLongitude();
        lastCity = commonAppPreferences.getCity();

        layoutInflater = LayoutInflater.from(getContext());
        mLocationHelper = LocationHelper.sharedInstance(getContext());
        mLocationHelper.addOnLocatedListener(new LocationHelper.OnLocatedListener() {
            @Override
            public void onLocated(BDLocation bdLocation) {
                LogUtils.i("定位回调");
                curtBDLocation = bdLocation;
                int status = bdLocation.getLocType();
                if (status == BDLocation.TypeServerError || status == BDLocation.TypeNetWorkException || status == BDLocation.TypeCriteriaException) {
                    curLongtitude = null;
                    curLatitude = null;
                    curCity = getResources().getString(R.string.location_error);
                    location.setText(curCity);
                    commonAppPreferences.setLocalAddress(curLongtitude, curLatitude, curCity);
                } else {
                    if (isSelectedLocation) {
                        return;
                    }
                    curLongtitude = String.valueOf(curtBDLocation.getLongitude());
                    curLatitude = String.valueOf(curtBDLocation.getLatitude());
                    curCity = curtBDLocation.getCity();
                    locationCityDetail = new CityDetail();
                    locationCityDetail.setCityName(curCity);
                    locationCityDetail.setCityLat(curLatitude);
                    locationCityDetail.setCityLng(curLongtitude);
                    location.setText(TextUtils.isEmpty(curCity) ? getResources().getString(R.string.location_error) : curCity);
                    commonAppPreferences.setLocalAddress(curLongtitude, curLatitude, curCity);
                    isSelectedLocation = true;

                    //压合获取轮播图信息,获取轮播图下方分类,获取最底部服务列表 三个接口 返回首页信息列表数据
                    homePagePresenter.getHomeListData(curLongtitude, curLatitude);
                }
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至城市列表
                Intent intent = new Intent(getActivity(), CityActivity.class);
                intent.putExtra(StaticData.LOCATION_ADDRESS, locationCityDetail);
                startActivityForResult(intent, REFRESS_LOCATION_CODE);
            }
        });


        hotSearchLink.setLayoutManager(new GridLayoutManager(getContext(), 5));
        hotServiceAdapter = new HotServiceAdapter();
        hotServiceAdapter.setOnHandleServiceItem(this);
        hotServiceAdapter.setOnDrawPopuListner(this);
        hotSearchLink.setAdapter(hotServiceAdapter);
        navigationAdapter = new NavigationAdapter();
        navigationAdapter.setOnCheckMoreService(this);
        nagavitionRecyclerview.setAdapter(navigationAdapter);

        superDiscountLink.setLayoutManager(new GridLayoutManager(getContext(), 3));
        superDiscountAdapter = new SuperDiscountAdapter();
        superDiscountAdapter.setOnTakeTheDiscount(this);
        superDiscountLink.setAdapter(superDiscountAdapter);
        superDiscount.setVisibility(View.GONE);

        String versionName = "";
        try {
            versionName = getActivity().getPackageManager().getPackageInfo("com.homepaas.sls", 0).versionName + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        commonAppPreferences.setVersionName(versionName);
        commonNetWorkErrorViewReplacer = new CommonNetWorkErrorViewReplacer(mContext, scrollContent, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                //刷新首页数据
                homePagePresenter.getHomeListData(curLongtitude, curLatitude);
            }
        });
        //首页缓存
        String homePageCacheTime = commonAppPreferences.getHomePageCacheTime();
        String homeListStr = commonAppPreferences.getHomeListData();
        if (TextUtils.isEmpty(homePageCacheTime) || !TextUtils.equals(homePageCacheTime, TimeUtils.getYearMonth()) || TextUtils.isEmpty(homeListStr)) {
//            homePagePresenter.getHomeListData(curLongtitude, curLatitude);//目前业务还没有分区域不需要经纬度
        } else {
            //首先加载缓存数据
            HomeListData homeListData = gson.fromJson(homeListStr, HomeListData.class);
            if (homeListData != null && homeListData.getShortCutEntity() != null) {
                setHomeListData(homeListData);
            }
//            else {
//                homePagePresenter.getHomeListData(curLongtitude, curLatitude);//目前业务还没有分区域不需要经纬度
//            }
        }
        commonAppPreferences.setHomePageCacheTime(TimeUtils.getYearMonth());

        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(token)) {
            homePagePresenter.loadPersonalInfo();//在登录的情况下请求一次个人信息，保存手机号。
        }
        lyHomeOrderStatus.post(new Runnable() {
            @Override
            public void run() {
                if (lyHomeOrderStatus != null) {
                    int screenHeight = WindowUtil.getInstance().getScreenHeight(getActivity());
                    lyHomeOrderStatus.setMinimumHeight((int) (screenHeight * 0.083));
                    lyHomeOrderStatus.setMinimumWidth(DensityUtil.dip2px(getActivity(), 160));
                }
            }
        });
        //请求权限
        requestPermission();
    }

    @Override
    protected void initData() {

    }

    /**
     * 非标订单定位位置改变，首页位置进行同步
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLocation(LocationEntity locationEntity) {
        if (locationEntity != null && !TextUtils.isEmpty(locationEntity.getCity()) && !curCity.equals(locationEntity.getCity())) {
            curCity = locationEntity.getCity();
            location.setText(curCity);
            curLongtitude = locationEntity.getLongitude() + "";
            curLatitude = locationEntity.getLatitude() + "";
            locationCityDetail = new CityDetail();
            locationCityDetail.setCityName(curCity);
            locationCityDetail.setCityLat(curLatitude);
            locationCityDetail.setCityLng(curLongtitude);
            commonAppPreferences.setLocalAddress(curLongtitude, curLatitude, curCity);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("TAG", "HomePageFragment:onResume");
        getData();
    }

    public void getData() {
        curLatitude = commonAppPreferences.getLatitude();
        curLongtitude = commonAppPreferences.getLongitude();
        if (PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE)) {
            //超级优惠接口【只有登陆的情况下才显示】
            homePagePresenter.getSuperDiscount(curLongtitude, curLatitude);
            //首页浮层订单状态
            homePagePresenter.getOrderStatus();
            //获取未读消息数量
            messagePresenter.getUnreadMessageCount();
        } else {
            superDiscount.setVisibility(View.GONE);
            lyHomeOrderStatus.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(curLongtitude) && !TextUtils.isEmpty(curLongtitude))
            homePagePresenter.getHomeListData(curLongtitude, curLatitude);

    }

    public void requestPermission() {
        LogUtils.i("requestPermission");

//        //申请apk文件的读写权限


//        AndPermission.with(mContext)
//                .requestCode(200)
//                .permission(
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//                .callback(listener)
//                .start();

        PermissionUtils.requestPhoneStrorageLocation(mContext, new AcpListener() {
            @Override
            public void onGranted() {
                mLocationHelper.start();//开始定位，并通过定位到的信息进行获取定位相关的首页列表数据
            }

            @Override
            public void onDenied(List<String> permissions) {
                boolean isLocationError = false;
                for (int i = 0; i < permissions.size(); i++) {
                    String s = permissions.get(i);
                    if (s.equals("android.permission.ACCESS_FINE_LOCATION")) {
                        location.setText(getResources().getString(R.string.location_error));
                        isLocationError = true;
                    }
                }
                if (!isLocationError) {
                    mLocationHelper.start();//开始定位，并通过定位到的信息进行获取定位相关的首页列表数据
                }
            }
        });
    }


    public void checkPermission() {
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
        StringBuffer stringBuffer = new StringBuffer("您拒绝了必要的");

        if (AndPermission.hasPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE)) {
            mLocationHelper.start();//开始定位，并通过定位到的信息进行获取定位相关的首页列表数据
            return;
        } else if (!AndPermission.hasPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            stringBuffer.append("文件存储,");
        }
        if (!AndPermission.hasPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            stringBuffer.append("位置信息,");
            location.setText(getResources().getString(R.string.location_error));
//            LocationEntity locationEntity = new LocationEntity(getResources().getString(R.string.location_error), "", 0, 0);
//            PreferencesUtil.saveObject(StaticData.ADDRESS_LOCATION, locationEntity);
//            EventBus.getDefault().post(locationEntity);//通知首页同步定位信息
        }
        if (!AndPermission.hasPermission(mContext, Manifest.permission.READ_PHONE_STATE)) {
            stringBuffer.append("电话,");
        }
        stringBuffer.append("权限，请在设置中授权！");
        // 使用AndPermission提供的默认设置dialog，用户点击确定后会打开App的设置页面让用户授权。
        // 第二种：用自定义的提示语。
        SettingDialog settingDialog = AndPermission.defaultSettingDialog((Activity) mContext, 400);
        settingDialog.setTitle("权限申请失败")
                .setMessage(stringBuffer.toString())
                .setPositiveButton("好，去设置")
                .show();
        // 建议：自定义这个Dialog，提示具体需要开启什么权限，自定义Dialog具体实现上面有示例代码。
    }


    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                //部分中国厂商生产手机（例如vivo、pppo某型号）在用户允许权限，并且回调了权限授权成功的方法，但是实际执行代码时并没有这个权限，
                // 建议在成功的回调房中调用AppOpsManager做权限判断： if(AndPermission.hasPermission()) {// 有权限。}
                checkPermission();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                //部分中国厂商生产手机（例如小米、华为某型号）在申请权限时，用户点击确定授权后，还是回调我们申请失败，这个时候其实我们是拥有权限的，
                // 建议在失败的回调房中调用AppOpsManager做权限判断： if(AndPermission.hasPermission()) {// 执行操作。}
                checkPermission();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocationHelper.clearOnLocatedListener();
        EventBus.getDefault().unregister(this);
        homePagePresenter.destroy();
        messagePresenter.destroy();
    }

    @Override
    public void renderShortCut(ShortCutEntity shortCutEntity) {
        hotServiceAdapter.setDatas(shortCutEntity.getShortCutContents());
    }

    @Override
    public void renderRecommend(RecommendServiceEntity recommendServiceEntity) {
        navigationAdapter.setDatas(recommendServiceEntity.getRecommendBlocks());
    }


    @Subscribe
    public void onEventMainThread(ServiceItem serviceItem) {
        handle(serviceItem);
    }

    @Override
    public void handle(ServiceItem serviceItem) {
        if (serviceItem == null) {
            //跳转到全部类目页面
            AllCategoriesActivity.start(getActivity(), curLatitude, curLongtitude);
        } else if (Constant.SERVICE_GROUP.equals(serviceItem.getStructureType())) {
            //跳转类目页面
            mNavigator.showCategory(getContext(), serviceItem.getServiceId(), curLongtitude, curLatitude);
        } else if (Constant.SERVICE_PRODUCT.equals(serviceItem.getStructureType())) {
            //isFlagMerchantService; 标记详情页是否出现商家的tab页面 0：不出现 1:出现
            if (TextUtils.isEmpty(serviceItem.getIsFlagMerchantService()) || serviceItem.getIsFlagMerchantService().equals("0")) {
                //跳转详情页面
                DetailWebActivity.start(mContext, serviceItem);
            } else {
                //跳转到非标订单详情页面
                ServiceMerchantActivity.start(mContext, serviceItem);
            }
        }
    }

    @Override
    public void check(String serviceId) {
        //跳到类目页面
        mNavigator.showCategory(getContext(), serviceId, curLongtitude, curLatitude);
    }


    /**
     * 搜索
     */
    @OnClick(R.id.go_to_search)
    void searchClick() {
        CommonSearchServiceActivity.start(getActivity(), curLatitude, curLongtitude);
    }

    /**
     * 搜索
     */
    @OnClick(R.id.tv_select_all_service)
    void allServiceClick() {
        //跳转到全部类目页面
        AllCategoriesActivity.start(mContext, null, null);
    }


    @OnClick(R.id.message_entry)

    public void checkMessage() {
        if (View.VISIBLE == unreadIcon.getVisibility()) {
            unreadIcon.setVisibility(View.GONE);
        }
        try {
            if (!TextUtils.isEmpty(personalInfoPDataSource.getToken())) {
                mNavigator.viewMessage(getActivity());
            } else {
                mNavigator.login(HomePageFragment.this, REQUEST_MESSAGE);
            }

        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        bannerLayout.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        bannerLayout.stopAutoPlay();
    }

    public void renderBannerData(AdsInfo adsInfo) {
        if (adsInfo == null)
            return;

        this.bannerInfos = adsInfo.getBannerInfos();
        bannerImages = new ArrayList<>();
        for (BannerInfo bannerInfo : bannerInfos) {
            LogUtils.e(bannerInfo.getImageUrl()+"-------------------");
            bannerImages.add(bannerInfo.getImageUrl());
        }
//        //防止重复初始化banner出现问题
//        if (bannerIsStart) {
////            bannerLayout.update(bannerImages);
//            bannerLayout.stopAutoPlay();
//        } else {
        bannerLayout.setImages(bannerImages)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
//        }
    }

    public void version(String version) {
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("MAX_ACTIVITY_ID", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("MAX_ACTIVITY_ID", version).commit();
    }

    @Override
    public void renderUnreadMsgCount(boolean hasUnread) {
        unreadIcon.setVisibility(hasUnread ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void initOrderListPop(OrderListPopEntity data) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 400: { // 这个400就是上面defineSettingDialog()的第二个参数。
                // 你可以在这里检查你需要的权限是否被允许，并做相应的操作。
                checkPermission();
                break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CommonBaseActivity.REQUEST_LOGIN_BASE_ACTIVITY:  //登陆界面返回
                    if (data != null && data.getBooleanExtra("Status", false)) {
                        //一键下单入口暂时不需要
                        //homePagePresenter.getFistOrderInfo();
                    }
                    break;
                case REQUEST_MESSAGE:
                    if (data != null && data.getBooleanExtra("Status", false))
                        mNavigator.viewMessage(getActivity());
                    break;
                case REFRESS_LOCATION_CODE:
                    if (data != null) {
                        Bundle bundle = data.getBundleExtra(StaticData.SELECTED_CITY);
                        CityDetail cityDetail = (CityDetail) bundle.getSerializable(StaticData.SELECTED_CITY);
                        if (cityDetail != null) {
                            isSelectedLocation = true;
                            location.setText(cityDetail.getCityName());
                            curCity = cityDetail.getCityName();
                            curLongtitude = cityDetail.getCityLng();
                            curLatitude = cityDetail.getCityLat();
                            locationCityDetail = new CityDetail();
                            locationCityDetail.setCityName(curCity);
                            locationCityDetail.setCityLat(curLatitude);
                            locationCityDetail.setCityLng(curLongtitude);
                            commonAppPreferences.setLocalAddress(curLongtitude, curLatitude, curCity);
                        }
                    }
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void renderFirstOrder(boolean isFirst, IsFirstOrderInfo.Service lastOrderServiceInfo) {
        if (isFirst) {
            Intent intent = new Intent(getActivity(), NewChooseServiceActivity.class);
            intent.putExtra("isFirstOrder", isFirst);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
            if (lastOrderServiceInfo != null) {
                intent.putExtra("LastServiceTypeId", lastOrderServiceInfo.getId());
                intent.putExtra("LastServiceTypeName", lastOrderServiceInfo.getName());
            }
            intent.putExtra(StaticData.SEARCH_OR_BUTTON, "1");
            startActivity(intent);
        }
    }

    @Override
    public void renderSuperDiscount(SuperDiscountEntity superDiscountEntity) {
        if (superDiscountEntity != null && superDiscountEntity.getServiceItemList() != null && !superDiscountEntity.getServiceItemList().isEmpty()) {
            superDiscount.setVisibility(View.VISIBLE);
            superDiscountTitle.setText(superDiscountEntity.getTitle());
            superDiscountAdapter.setData(superDiscountEntity.getServiceItemList());
        } else {
            superDiscount.setVisibility(View.GONE);
        }
    }

    //绑定首页列表数据
    @Override
    public void setHomeListData(HomeListData homeListData) {
//        if (!isHomeData)
        commonAppPreferences.setHomeListData(gson.toJson(homeListData));
        if (homeListData == null)
            return;
        commonNetWorkErrorViewReplacer.showOriginalLayout();
        isHomeData = true;
        AdsInfo adsInfo = homeListData.getAdsInfo();
//        LogUtils.i("TAG", "bannerIsStart" + bannerIsStart);
//         && !bannerIsStart
        if (adsInfo != null && !bannerIsStart) {
            renderBannerData(adsInfo);
            version(adsInfo.getVersion());
            bannerIsStart = true;
        }
        renderShortCut(homeListData.getShortCutEntity());
        renderRecommend(homeListData.getRecommendServiceEntity());
    }

    //显示网络错误布局
    @Override
    public void getHomeListDataError() {
        if (!isHomeData)
            commonNetWorkErrorViewReplacer.showErrorLayout();
    }

    @Override
    public void renderSuperDiscountError() {
        SuperDiscountEntity object = PreferencesUtil.getObject(StaticData.DISCOUNTS, SuperDiscountEntity.class);
        renderSuperDiscount(object);
    }

    @Override
    public void drawPopu(List<Integer> position, List<View> itemViews, List<String> title) {//添加角标
        addPopuView = new ArrayList<>();
        colors = new ArrayList<>();
        colors.add(Color.parseColor("#FF3D72"));
        colors.add(Color.parseColor("#FE7E36"));
        colors.add(Color.parseColor("#29A6F4"));
        colors.add(Color.parseColor("#FFB92F"));
        colors.add(Color.parseColor("#8D49FE"));
        colors.add(Color.parseColor("#22D1D1"));
        colors.add(Color.parseColor("#FF4550"));
        colors.add(Color.parseColor("#8552CA"));
        colors.add(Color.parseColor("#87DA32"));
        colors.add(Color.parseColor("#87DA32"));
        colors.add(Color.parseColor("#40C3D5"));

        for (View itemView : itemViews) {
            TextView textView = (TextView) layoutInflater.inflate(R.layout.popu_textview, null);
            textView.setText(title.get(itemViews.indexOf(itemView)));
            textView.setTextColor(colors.get(position.get(itemViews.indexOf(itemView))));
            GradientDrawable mygbg = (GradientDrawable) textView.getBackground();
            mygbg.setStroke(1, colors.get(position.get(itemViews.indexOf(itemView))));
            int left = itemView.getLeft();
            int top = itemView.getTop();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = left + itemView.getWidth() / 2;
            layoutParams.topMargin = top + 3;
            hotFl.addView(textView, layoutParams);
            addPopuView.add(textView);
        }

    }

    @Override
    public void disCount(ServiceItem serviceItem) {
        //跳转到新下单页面
        //Context context, String serviceId, String serviceName, String isActivity
        CommonPlaceOrderActivity.start(getActivity(), serviceItem.getServiceId(), serviceItem.getServiceName(), serviceItem.getOrderType(), "", "", "", "", serviceItem.getServiceId());
    }

    /**
     * 轮播图单击
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {
        BannerInfo bannerInfo = bannerInfos.get(position);
        String type = bannerInfo.getApns().getJumpType();
        if (!TextUtils.isEmpty(type)) {
            switch (type) {
                case "1":
                    pushUtil.startInternalView(getActivity().getApplicationContext(), bannerInfo.getApns());
                    break;
                case "2":
                    pushUtil.startWebView(getActivity().getApplicationContext(), bannerInfo.getApns());
                    break;
                default:
                    break;
            }
        }
    }

//

    //------------------------------订单状态浮层相关
    @Override
    public void initOrderStatus(HomeOrderStatusEntity homeOrderStatusEntity) {
        this.homeOrderStatusEntity = homeOrderStatusEntity;
        if (homeOrderStatusEntity == null || TextUtils.isEmpty(homeOrderStatusEntity.getOrderId())
                || TextUtils.isEmpty(homeOrderStatusEntity.getStatus())
                || TextUtils.isEmpty(homeOrderStatusEntity.getDescription())) {
            lyHomeOrderStatus.setVisibility(View.GONE);
            supernatantIsShow = false;
        } else {
            tvStatus.setText(homeOrderStatusEntity.getStatus());
            tvDescription.setText(homeOrderStatusEntity.getDescription());
            lyHomeOrderStatus.setVisibility(View.VISIBLE);
            supernatantIsShow = true;
        }
    }


    @OnClick(R.id.ly_home_order_status)
    public void onViewClicked() {
        toOrderDetails();
    }

    /**
     * 悬浮层状态布局切换显示隐藏
     */
    @OnClick(R.id.rl_anim_target)
    void animLayoutClick() {
        if (!supernatantIsShow) {//折叠单击展开
            animOpen();
        } else {
            toOrderDetails();//展开点击进入订单详情
        }
    }

    public void toOrderDetails() {
        //进入订单详情
        ClientOrderDetailActivity.startOrderDetailForResult(getActivity(), homeOrderStatusEntity.getOrderId());
    }


    /**
     * 浮层是否折叠
     * b true:折叠 false 还原初始状态
     *
     * @param
     */
    @Override
    public void onEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
//                if (!isUnFold) {
//                    //展开浮层
//                  animOpen();
//                    isUnFold = true;
//                }
//                startY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (supernatantIsShow)//如果是关闭状态不进行处理
                {
                    //浮层折叠 event.getY() - startY > DensityUtil.dip2px(mContext, 20) 防止移动过小,过小就不响应
                    if (event.getY() - startY > DensityUtil.dip2px(mContext, 20) || startY - event.getY() > DensityUtil.dip2px(mContext, 20)) {
                        animFold();
                    }
                }

                break;
        }
    }

    /**
     * 浮层展开
     */
    public void animOpen() {
        final float x = lyHomeOrderStatus.getTranslationX();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(lyHomeOrderStatus, "translationX", x, 0);
        objectAnimator.setDuration(ANIM_TIME);
        objectAnimator.start();
        supernatantIsShow = true;
    }

    /**
     * 浮层折叠
     */
    public void animFold() {
        final float x = lyHomeOrderStatus.getTranslationX();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(lyHomeOrderStatus, "translationX", x, lyHomeOrderStatus.getWidth() - rlAnimTarget.getWidth());
        objectAnimator.setDuration(ANIM_TIME);
        objectAnimator.start();
        supernatantIsShow = false;
    }
    //------------------------------订单状态浮层相关
}
