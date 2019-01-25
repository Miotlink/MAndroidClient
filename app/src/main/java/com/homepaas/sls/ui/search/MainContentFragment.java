package com.homepaas.sls.ui.search;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.homepaas.sls.Global;
import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.OrderListPopEntity;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.di.component.DaggerMainComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.entity.BusinessEntity;
import com.homepaas.sls.domain.entity.CheckIsReceivedRedCoupsEntry;
import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.entity.UpdateCheck;
import com.homepaas.sls.domain.entity.WholeCityBusines;
import com.homepaas.sls.domain.entity.WholeCityWorker;
import com.homepaas.sls.domain.entity.WorkerEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.jsinterface.entity.DirectOrderCommand;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.IServiceInfo.Type;
import com.homepaas.sls.mvp.model.ServiceItemModel;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.mvp.presenter.MainContentPresenter;
import com.homepaas.sls.mvp.presenter.MainContentPresenter.QueryMode;
import com.homepaas.sls.mvp.presenter.MainContentPresenter.ServiceType;
import com.homepaas.sls.mvp.presenter.MessagePresenter;
import com.homepaas.sls.mvp.presenter.SearchService.HomeHotServicePresenter;
import com.homepaas.sls.mvp.presenter.ShortDetailPresenter;
import com.homepaas.sls.mvp.presenter.homepage.ServiceSearchPresenter;
import com.homepaas.sls.mvp.view.MainContentView;
import com.homepaas.sls.mvp.view.SearchService.HomeHotSeviceView;
import com.homepaas.sls.mvp.view.ServiceSearchView;
import com.homepaas.sls.mvp.view.UnReadMessageCountView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.pushservice.PushUtil;
import com.homepaas.sls.ui.AddViewFrameLayout;
import com.homepaas.sls.ui.WebViewActivity;
import com.homepaas.sls.ui.adapter.HomeHotServiceAdapter;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.location.location.LocationHelper;
import com.homepaas.sls.ui.order.directOrder.ConfirmOrderActivity;
import com.homepaas.sls.ui.tag.Adapter.NewTagBaseAdapter;
import com.homepaas.sls.ui.tag.TagLayout;
import com.homepaas.sls.ui.widget.AppPreferences;
import com.homepaas.sls.ui.widget.AutoText;
import com.homepaas.sls.ui.widget.BriefCardView;
import com.homepaas.sls.ui.widget.CircleImageView;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.ui.widget.EditInvitationCodeDialog;
import com.homepaas.sls.ui.widget.ImageTextButton;
import com.homepaas.sls.ui.widget.LoadingView;
import com.homepaas.sls.ui.widget.utils.AnimationUtil;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.PermissionUtil;
import com.homepaas.sls.util.PermissionUtils;
import com.runtimepermission.acp.IBaseAcpListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import dagger.Lazy;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static android.view.View.VISIBLE;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_DEFAULT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_TEXT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_VOICE;
import static com.homepaas.sls.domain.param.Constant.SERVICE_TYPE_ALL;
import static com.homepaas.sls.domain.param.Constant.SERVICE_TYPE_BUSINESS;
import static com.homepaas.sls.domain.param.Constant.SERVICE_TYPE_WORKER;
import static com.homepaas.sls.navigation.Navigator.SEARCH_CONTENT;


/**
 * on 2016/1/5 0005
 *
 * @author zhudongjie .
 */
public class MainContentFragment extends CommonBaseFragment implements MainContentView, ServiceSearchView, HomeHotSeviceView, UnReadMessageCountView, AddViewFrameLayout.OnItemSelectListener, HomeHotServiceAdapter.OnItemClickListener, BriefCardView.onSelectedPositon, BriefCardView.HandleManage {


    @Bind(R.id.viewPager_brief_card)
    BriefCardView viewPagerBriefCard;
    @Bind(R.id.distance)
    TextView distance;
    @Bind(R.id.push_close)
    FrameLayout pushClose;
    @Bind(R.id.red_coups_text)
    AutoText redCoupsText;
    @Bind(R.id.red_coups_rel)
    RelativeLayout redCoupsRel;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.toolbar)
    LinearLayout toolbar;
    @Bind(R.id.scan)
    ImageView scan;
    @Bind(R.id.menu_service)
    ImageView menuService;
    @Bind(R.id.center_pointer)
    Space centerPointer;
    @Bind(R.id.location)
    ImageView location;
    @Bind(R.id.check_shop)
    CheckBox checkShop;
    @Bind(R.id.check_worker)
    CheckBox checkWorker;
    @Bind(R.id.close_search_prompt)
    ImageButton closeSearchPrompt;
    @Bind(R.id.speech_voice_tip)
    PercentFrameLayout speechVoiceTip;
    @Bind(R.id.instant_place_order)
    FrameLayout instantPlaceOrder;
    @Bind(R.id.yindao_3)
    ImageView yindao3;
    @Bind(R.id.yindao_2)
    ImageView yindao2;
    //引导页面
    @Bind(R.id.novive_btn)
    Button noviveBtn;
    @Bind(R.id.novice_bottom)
    ImageView noviceBottom;
    @Bind(R.id.novice)
    FrameLayout novice;
    ///////////旧版热区在左侧
    @Bind(R.id.worker_bussines_poi)
    AddViewFrameLayout workerBussinesPoi;
    @Bind(R.id.map_container)
    PercentRelativeLayout mapContainer;
    @Bind(R.id.location_pin)
    ImageView locationPin;
    @Bind(R.id.unreadIcon)
    View unreadIcon;
    @Bind(R.id.message_entry)
    FrameLayout messageEntry;
    @Bind(R.id.hot_search_lin)
    LinearLayout hotSearchLin;
    //地图筛选服务类型的水平滚动列表
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.hot_service_text)
    TextView hotServiceText;
    @Bind(R.id.home_more_image)
    ImageView homeMoreImage;
    //单击home_more_image_lin空间显示的蒙层中的tabLayout展示的服务类型
    @Bind(R.id.taglistview_lin)
    LinearLayout taglistviewLin;
    @Bind(R.id.home_taglistview)
    TagLayout homeTaglistView;
    @Bind(R.id.taglistview_second_lin)
    LinearLayout taglistviewSecondLin;
    @Bind(R.id.home_more_image_lin)
    LinearLayout homeMoreImageLin;
    @Bind(R.id.go_to_search)
    LinearLayout goToSearch;
    @Bind(R.id.choose_worker_business)
    ImageView mChooseWorkerBuiness;
    @Bind(R.id.home_speech_voice_bg)
    ImageButton homeSpeechVoiceBg;
    @Bind(R.id.loadingProgressBar)
    ContentLoadingProgressBar loadingProgressBar;
    //与使用MapView相比，TextureMapView可以有效解决移动地图时屏幕闪烁问题
    @Bind(R.id.mapView)
    TextureMapView mMapView;
    @Bind(R.id.search_voice_button)
    ImageTextButton mSearchVoice;
    @Bind(R.id.search_prompt_bg)
    View mSearchBackground;
    @Bind(R.id.search_edit_text)
    EditText mSearchEditText;
    @Bind(R.id.menu_photo)
    ImageView mPhoto;
    @Bind(R.id.search_prompt_content)
    TextView mSearchPromptContent;
    private TextView mExplainText;
    private TextView mExplianTextAll;
    private LinearLayout mMarkerLin;
    private LinearLayout mMarkerHouseLin;
    private LinearLayout mMarkerWorkerLin;
    private CircleImageView mMarkerImg;
    private LinearLayoutManager layoutManager;
    private LinearLayout popPositionView;//位置弹出框
    private View mMakerTemplate;
    private View mNewMarkerTemplate;
    private ImageView mMakerPhoto;
    private TextView mMarkerService;
    private LatLngBounds mMapBound;
    private static final int INVALID_INDEX = -1;
    private CommonDialog searchNoResultDialog;
    private View selfLocationView;

    //新增热门服务选择快
    @Inject
    HomeHotServicePresenter homeHotServicePresenter;
    @Inject
    protected PushUtil pushUtil;
    @Inject
    ShortDetailPresenter shortDetailPresenter;
    @Inject
    Navigator mNavigator;
    @Inject
    Global global;
    @Inject
    MainContentPresenter mainContentPresenter;
    private LocationHelper mLocationHelper;
    @Inject
    ServiceSearchPresenter serviceSearchPresenter;
    @Inject
    Lazy<ConfirmCompleteWindow> confirmCompleteWindow;
    @Inject
    MessagePresenter messagePresenter;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    //首页热门服务标签
    private NewTagBaseAdapter mTagBaseAdapter;
    private List<String> mHomeHotServiceInfoList;
    private List<HotServiceInfo> mHomeHotServiceInfos;
    private HomeHotServiceAdapter homeHotServiceAdapter;
    private List<IService> services;
    private WorkerBussinesModel selectedWorkerBussinesModel;
    private CheckIsReceivedRedCoupsEntry checkIsReceivedRedCoupsEntry;//红包属性
    //保存的百度地图的marker
    private Map<Integer, Marker> markersMap = new HashMap<>();
    //保存时哪一个全城显示标题信息
    private Map<String, Integer> selectedMap = new HashMap<>();
    private SoundPool mSoundPool;
    private SparseIntArray mSoundPoolMap;
    private LoadingView loadingView;
    private LatLng mLastPointer;
    private LatLng mSelfLocation;
    private BaiduMap mBaiduMap;

    private static final String SELECT_INDEX = "select_index";
    private static final String TAG = "MainContentFragment";
    private static final boolean DEBUG = false;
    private static final String PHOTO_ICON_KEY = "photoIcon";
    private static final String PHOTO_URL_KEY = "photoUrl";
    private static final String MARKER_ID_KEY = "markerId";
    private static final String MARKER_DEFAULT_SERVICE_KEY = "defaultService";
    private static final String MARKER_TYPE_KEY = "markerType";
    private static final int REQUEST_CODE_SEARCH = 0x10;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;
    private static final int REQUEST_CODE_CAMERA = 4;
    private static final int SOUND_NO_SERVICE = 1;
    private static final int SOUND_OUT_OF_SERVICE = 2;
    private static final int REQUEST_MESSAGE = 10;
    private static final String LAT_KEY = "LatLng_key";
    private static final String MARKER_INDEX_KEY = "MarkerIndex";
    private static final String PHOTO_URL = "PhotoUrl";
    private static final String IS_WORKER = "IsWorker";
    private static final String IS_WHOLECITYPROVIDER = "IsWholeCityProvider";
    private static final String IS_BUSINESS_WORKER = "IsBusinessWorker";
    private static final int MARKER_TYPE_PHOTO_WORKER = 0;
    private static final int MARKER_TYPE_PHOTO_BUSINESS = 4;
    private static final int MARKER_TYPE_BUSINESS = 1;
    private static final int MARKER_TYPE_WORKER = 2;
    private static final int MARKER_TYPE_LOCATION = 3;
    public static final int SEARCH_TYPE_NONE = 0;
    private static final int SEARCH_TYPE_WORKER = 1;
    private static final int SEARCH_TYPE_BUSINESS = 1 << 1;
    private static final int SEARCH_TYPE_ALL = SEARCH_TYPE_BUSINESS | SEARCH_TYPE_WORKER;
    // XXX: 2016/3/3
    private String mCallingPhone;
    @SearchType
    private int mSearchType = SEARCH_TYPE_NONE;

    /**
     * 地图图标类型
     */
    @IntDef({MARKER_TYPE_PHOTO_WORKER, MARKER_TYPE_PHOTO_BUSINESS, MARKER_TYPE_BUSINESS, MARKER_TYPE_WORKER, MARKER_TYPE_LOCATION})
    @interface MarkerType {

    }

    /**
     * 搜索种类
     */
    @IntDef(value = {SEARCH_TYPE_NONE, SEARCH_TYPE_WORKER, SEARCH_TYPE_BUSINESS, SEARCH_TYPE_ALL}, flag = true)
    @interface SearchType {

    }

    @QueryMode
    private String mSearchMode = QUERY_MODE_DEFAULT;
    private String mSearchContent;
    private String mServiceId;
    private int mPicWidth;
    private int mPicHeight;
    private int curSelectIndex;
    private int lastSelectIndex;
    private int wholeAllNumber = 0;
    private boolean isMapDataFirst = false;
    private boolean isShowMapView;
    private boolean zoomed;
    private boolean isFirstMarker = true;
    private boolean isFirstOnselect = true;//移动地图的时候，render重新加载数据后在onSelect方法中会赋值index，使得有一个marker状态改成选中状态,加这个为了加载新数据时候全部marker都为不选中状态

    /**
     * 设置地图元素数据
     *
     * @param services
     * @param hasWholeMerchant
     * @param wholeAllNumber
     */
    @Override
    public void render(List<IService> services, boolean hasWholeMerchant, int wholeAllNumber) {
        isMapDataFirst = true;
        markersMap.clear();
        selectedMap.put(SELECT_INDEX, -1);
        lastSelectIndex = -1;
        curSelectIndex = -1;
        this.services = services;
        this.wholeAllNumber = wholeAllNumber;
        isFirstMarker = true;
        isFirstOnselect = true;
        mBaiduMap.clear();//清楚所有标记
        for (int i = 0; i < services.size(); i++) {
            IService info = services.get(i);
            LatLng latLng = new LatLng(info.getLat(), info.getLng());
            showMarker(info.isCommonBusinessWorker(), info.isWholeCityProvider(), info.isWorker(), info.getPhotoUrl(), latLng, info.getId(), i, info.getDefService(), false);
        }
        viewPagerBriefCard.setData(services);
        viewPagerBriefCard.setVisibility(View.GONE);
        if (!zoomed) {
            zoomToSpan();
        }
    }

    @Override
    public void onSelect(int index, IService service) {
        if (isFirstOnselect) {
            isFirstOnselect = false;
        } else {
            lastSelectIndex = curSelectIndex;
            curSelectIndex = index;
            if (lastSelectIndex != INVALID_INDEX && lastSelectIndex != curSelectIndex) {
                if (index >= 0 && index < wholeAllNumber) {
                    int selectPosition = selectedMap.get(SELECT_INDEX);
                    if (selectPosition != -1) {
                        newResetMarker(selectPosition, false, index);
                    }
                }
                newResetMarker(lastSelectIndex, false, index);
                newResetMarker(curSelectIndex, true, index);
                setMarkerToTop(curSelectIndex);
            }
        }
    }

    @Override
    public void hadle(IService service, int actionCode) {
        switch (actionCode) {
            case 1:
                mainContentPresenter.call(service);
                break;
            case 2:
                if (service instanceof WorkerEntity)
                    mNavigator.showMerchantWorkerDetail(getContext(), Constant.SERVICE_TYPE_WORKER, ((WorkerEntity) service).getId());
                else if (service instanceof BusinessEntity)
                    mNavigator.showMerchantWorkerDetail(getContext(), Constant.SERVICE_TYPE_BUSINESS, ((BusinessEntity) service).getId());
                else if (service instanceof WholeCityBusines)
                    mNavigator.showMerchantWorkerDetail(getContext(), Constant.SERVICE_TYPE_WORKER, ((WholeCityBusines) service).getId());
                else if (service instanceof WholeCityWorker)
                    mNavigator.showMerchantWorkerDetail(getContext(), Constant.SERVICE_TYPE_WORKER, ((WholeCityWorker) service).getId());
                break;
        }
    }

    //一键下单的情况
    @Override
    public void onPreOrder(String serviceTypeId, String serviceName) {
        //搜索下单跳转逻辑按照h5页面跳下单的逻辑处理
        DirectOrderCommand command = new DirectOrderCommand();
        command.setTypeId(serviceTypeId);
        command.setTypeName(serviceName);
        Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
        if (serviceName != null && serviceTypeId != null)
            intent.putExtra("h5service", command);
        setServiceContent("");
        mSearchEditText.setText("");
        startActivity(intent);
    }

    /**
     * 自适应
     */
    public void zoomToSpan() {
        if (mBaiduMap == null) {
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (services.size() > 0) {
            for (IService service : services) {
                // polyline 中的点可能太多，只按marker 缩放
                if (!service.isWholeCityProvider())
                    builder.include(new LatLng(service.getLat(), service.getLng()));
            }
        }
        mBaiduMap.setMapStatus(MapStatusUpdateFactory
                .newLatLngBounds(builder.build()));
        zoomed = true;
    }

    @Override
    public void renderUnreadMsgCount(boolean hasUnread) {
        unreadIcon.setVisibility(hasUnread ? VISIBLE : View.INVISIBLE);
    }

    @Override
    public void initOrderListPop(OrderListPopEntity data) {

    }

    //地图标注点击事件监听
    private OnMarkerClickListener mOnMarkerClickListener = new OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            isFirstOnselect = false;
            Bundle extraInfo = marker.getExtraInfo();
            int index = extraInfo.getInt(MARKER_INDEX_KEY);
            if (index >= 0 && index < wholeAllNumber) {
                int selectPosition = selectedMap.get(SELECT_INDEX);
                if (selectPosition != -1) {
                    newResetMarker(selectPosition, false, index);
                }
            }
            lastSelectIndex = curSelectIndex;
            curSelectIndex = index;
            if (lastSelectIndex != INVALID_INDEX && lastSelectIndex != curSelectIndex) {
                newResetMarker(lastSelectIndex, false, index);
            }
            newResetMarker(curSelectIndex, true, index);
            //循环
            if (viewPagerBriefCard.getVisibility() == View.GONE) {
                showCard();
                viewPagerBriefCard.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPagerBriefCard.setPosition(curSelectIndex);
                    }
                }, 800);
            } else {
                viewPagerBriefCard.setPosition(index);
            }

            return true;
        }
    };


    //改变marker的状态
    private void newResetMarker(int changeIndex, boolean changeBoolean, int currIndex) {
        if (changeIndex < services.size() && currIndex < services.size()) {
            Marker marker = markersMap.get(changeIndex);
            if (marker != null) {
                marker.remove();
            }
            if (currIndex >= 0 && currIndex < wholeAllNumber) {
                isFirstMarker = false;
            } else {
                isFirstMarker = true;
            }
            IService info = services.get(changeIndex);
            LatLng latLng = new LatLng(info.getLat(), info.getLng());
            showMarker(info.isCommonBusinessWorker(), info.isWholeCityProvider(), info.isWorker(), info.getPhotoUrl(), latLng, info.getId(), changeIndex, info.getDefService(), changeBoolean);
        }
    }

    private void resetMarker(int currPosition) {
//        if (lastSelectIndex != INVALID_INDEX && lastSelectIndex != curSelectIndex) {
//            LatLngBounds bound = mBaiduMap.getMapStatus().bound;
//            List<Marker> markers = mBaiduMap.getMarkersInBounds(bound);//并不是所有的marker
//            for (Marker item :
//                    markers) {
//                Bundle extraInfo = item.getExtraInfo();
//                int index = extraInfo.getInt(MARKER_INDEX_KEY);
//                if (index == lastSelectIndex) {
//                    //选中和未选中的图片集
//                    com.homepaas.sls.domain.entity.ServiceType entity = (com.homepaas.sls.domain.entity.ServiceType) extraInfo.getSerializable(MARKER_DEFAULT_SERVICE_KEY);
//                    LatLng latLng = extraInfo.getParcelable(LAT_KEY);
//                    String photoUrl = extraInfo.getString(PHOTO_URL);
//                    Boolean isWorker = extraInfo.getBoolean(IS_WORKER);
//                    Boolean isWholeCityProvider = extraInfo.getBoolean(IS_WHOLECITYPROVIDER);
//                    Boolean isBusinessWorker=extraInfo.getBoolean(IS_BUSINESS_WORKER);
//                    showMarker(isBusinessWorker, isWholeCityProvider, isWorker, photoUrl, latLng, "", index, entity, false);
//                    item.remove();
//                    break;
//                }
//            }
//        }
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int index) {
            //改为直接从地图获取边界，不再从onMapStatusChangeFinish回调中获取bound,避免奇怪的NullPointerException
            List<Marker> markers = mBaiduMap.getMarkersInBounds(mBaiduMap.getMapStatus().bound);
            int size = markers == null ? 0 : markers.size();
            for (int i = 0; i < size; i++) {
                Marker marker = markers.get(i);
                Bundle extraInfo = marker.getExtraInfo();
                int p = extraInfo.getInt(MARKER_INDEX_KEY);
                if (p == index) {
                    lastSelectIndex = curSelectIndex;
                    curSelectIndex = index;
                    resetMarker(index);
                    LatLng latLng = extraInfo.getParcelable(LAT_KEY);
                    String photoUrl = extraInfo.getString(PHOTO_URL);
                    Boolean isWorker = extraInfo.getBoolean(IS_WORKER);
                    Boolean isWholeCityProvider = extraInfo.getBoolean(IS_WHOLECITYPROVIDER);
                    Boolean isBusinessWorker = extraInfo.getBoolean(IS_BUSINESS_WORKER);
                    com.homepaas.sls.domain.entity.ServiceType type = (com.homepaas.sls.domain.entity.ServiceType) extraInfo.getSerializable(MARKER_DEFAULT_SERVICE_KEY);
                    showMarker(isBusinessWorker, isWholeCityProvider, isWorker, photoUrl, latLng, null, index, type, true);
                    marker.remove();
                    setMarkerToTop(curSelectIndex);
                    break;

                }
            }
        }
    };

    @Override
    public void select(int index) {
        //改为直接从地图获取边界，不再从onMapStatusChangeFinish回调中获取bound,避免奇怪的NullPointerException
        List<Marker> markers = mBaiduMap.getMarkersInBounds(mBaiduMap.getMapStatus().bound);
        int size = markers == null ? 0 : markers.size();
        for (int i = 0; i < size; i++) {
            Marker marker = markers.get(i);
            Bundle extraInfo = marker.getExtraInfo();
            int p = extraInfo.getInt(MARKER_INDEX_KEY);
            if (p == index) {
                lastSelectIndex = curSelectIndex;
                curSelectIndex = index;
                resetMarker(index);
                LatLng latLng = extraInfo.getParcelable(LAT_KEY);
                String photoUrl = extraInfo.getString(PHOTO_URL);
                Boolean isWorker = extraInfo.getBoolean(IS_WORKER);
                Boolean isWholeCityProvider = extraInfo.getBoolean(IS_WHOLECITYPROVIDER);
                Boolean isBusinessWorker = extraInfo.getBoolean(IS_BUSINESS_WORKER);
                com.homepaas.sls.domain.entity.ServiceType type = (com.homepaas.sls.domain.entity.ServiceType) extraInfo.getSerializable(MARKER_DEFAULT_SERVICE_KEY);
                showMarker(isBusinessWorker, isWholeCityProvider, isWorker, photoUrl, latLng, null, index, type, true);
                marker.remove();
                setMarkerToTop(curSelectIndex);
                break;

            }
        }
    }

    //只能添加然后找出指定的marker,指定其层级
    public void setMarkerToTop(int index) {
        Marker marker = markersMap.get(index);
        if (marker != null) {
            marker.setToTop();
        }
    }

    public void showCard() {

        TranslateAnimation show = AnimationUtil.moveToViewLocation();
        show.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                viewPagerBriefCard.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewPagerBriefCard.setAnimation(show);
        viewPagerBriefCard.startAnimation(show);


    }

    public void hideen() {
        TranslateAnimation hidden = AnimationUtil.moveToViewBottom();
        hidden.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewPagerBriefCard.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewPagerBriefCard.setAnimation(hidden);
        viewPagerBriefCard.startAnimation(hidden);

    }

    private BaiduMap.OnMapClickListener mapClickListener = new BaiduMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            if (viewPagerBriefCard.getVisibility() == View.VISIBLE)
                hideen();
        }

        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            return false;
        }
    };
    private OnMapStatusChangeListener mOnMapStatusChangeListener = new OnMapStatusChangeListener() {
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {
            if (popPositionView != null) {
                mMapView.removeView(popPositionView);
                popPositionView = null;
            }
            if (viewPagerBriefCard.getVisibility() == View.VISIBLE)
                hideen();

        }

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {
            if (popPositionView != null)//有非常非常小的概率出现手指细微移动，但是没有调用onMapStatusChangeStart的错误，导致loadingView没有被移除，做一次
            {
                mMapView.removeView(popPositionView);
                popPositionView = null;
            }
            if (viewPagerBriefCard.getVisibility() == View.VISIBLE)
                hideen();
        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            if (mapStatus.bound != null)
                mMapBound = mapStatus.bound;//获取边界
            LatLng centerPointer = mapStatus.target;
            if (DEBUG)
                LogUtils.d(TAG, "onMapStatusChangeFinish: centerPointer = " + centerPointer.toString());
            if (!pointEquals(mLastPointer, centerPointer)) {
                mLastPointer = centerPointer;
                search();
            }
        }
    };

    /**
     * 添加当前位置的位置信息弹出窗口
     *
     * @param latLng
     */
    private void addLocationPopMarker(LatLng latLng) {
        if (popPositionView != null && popPositionView.getParent() != null)//有非常非常小的概率出现手指细微移动，导致loadingView没有被移除，做多次检查
        {
            mMapView.removeView(popPositionView);
            popPositionView = null;
        }
        popPositionView = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.loading_view, null);
        loadingView = (LoadingView) popPositionView.findViewById(R.id.loading);
        MapViewLayoutParams params = new MapViewLayoutParams.Builder()
                .position(latLng)
                //              .point(point)
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .yOffset(-locationPin.getHeight() - 10)
                .build();
        mMapView.addView(popPositionView, params);
//        mLocationHelper.reverse(latLng, new LocationHelper.OnGeoResultListener() {
//            @Override
//            public void onReverseCodeResult(ReverseGeoCodeResult result) {
//                loadingView.setText(getReverseAddress(result));
//            }
//        });
    }

    /**
     * 获取的poi有些不准确
     *
     * @param result
     * @return
     */
    public String getReverseAddress(ReverseGeoCodeResult result) {
        if (result != null) {
            if (result.getPoiList() != null && !result.getPoiList().isEmpty())
                return result.getPoiList().get(0).name;
            else
                return result.getAddress();
        } else return "";

    }

    private void search() {
        if (mLastPointer == null)
            return;
        //SEARCH_TYPE_ALL SEARCH_TYPE_NONE =>SERVICE_TYPE_ALL
        String serviceType = getStringServiceType();
        if (mLastPointer != null) {
            homeHotServicePresenter.getHotServiceInfo(String.valueOf(mLastPointer.latitude),
                    String.valueOf(mLastPointer.longitude));
            serviceSearchPresenter.searchService(String.valueOf(mLastPointer.latitude),
                    String.valueOf(mLastPointer.longitude),
                    mSearchContent, mServiceId, serviceType, mSearchMode);
        }
    }

    @ServiceType
    private String getStringServiceType() {
        String serviceType = SERVICE_TYPE_ALL;
        if (mSearchType == SEARCH_TYPE_ALL) {
            serviceType = SERVICE_TYPE_ALL;
        } else if (mSearchType == SEARCH_TYPE_WORKER) {
            serviceType = SERVICE_TYPE_WORKER;
        } else if (mSearchType == SEARCH_TYPE_BUSINESS) {
            serviceType = SERVICE_TYPE_BUSINESS;
        }

        return serviceType;
    }

    private static boolean pointEquals(LatLng latLng1, LatLng latLng2) {
        if (latLng1 == null || latLng2 == null) {
            return false;
        }
        return latLng1.latitude == latLng2.latitude && latLng1.longitude == latLng2.longitude;
    }


    /**
     * 一键下单
     */
    @OnClick(R.id.instant_place_order)
    public void directPlaceOrder() {
        startActivity(new Intent(getActivity(), ConfirmOrderActivity.class));
    }

    @OnClick(R.id.go_to_search)
    public void gotoSearch() {
        if (mLastPointer != null)
            mNavigator.NewSearchText(this, mLastPointer.latitude, mLastPointer.longitude, getStringServiceType(), REQUEST_CODE_SEARCH, mSearchEditText.getText().toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("TAG", "MainContentFragment:onCreate()");
    }

    private int imageCount = 0;

    /**
     * 切换热门搜索形式
     */
    @OnClick({R.id.home_more_image_lin, R.id.taglistview_lin, R.id.push_close, R.id.red_coups_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_more_image_lin:
                imageCount = imageCount + 1;
                setVisition(imageCount % 2);
                break;
            case R.id.taglistview_lin:
                setVisition(0);
                imageCount = 0;
                break;
            case R.id.push_close:
                redCoupsRel.setVisibility(View.GONE);
                break;
            case R.id.red_coups_text:
                if (checkIsReceivedRedCoupsEntry != null && !TextUtils.isEmpty(checkIsReceivedRedCoupsEntry.getUrl())) {
                    PushInfo pushInfo = new PushInfo();
                    pushInfo.setUrl(checkIsReceivedRedCoupsEntry.getUrl());
                    pushInfo.setTitle(checkIsReceivedRedCoupsEntry.getTitle());
                    pushInfo.setIsShare(null);
                    WebViewActivity.start(getActivity(), pushInfo);
                }
                break;
            default:
        }

    }

    private void setVisition(int remainder) {
        recyclerView.setVisibility(remainder == 0 ? VISIBLE : View.GONE);
        hotServiceText.setVisibility(remainder == 0 ? View.GONE : VISIBLE);
        taglistviewLin.setVisibility(remainder == 0 ? View.GONE : VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (remainder == 0) {
                hotSearchLin.setElevation(2.0f);
                hotSearchLin.setTranslationZ(2.0f);
            } else {
                hotSearchLin.setElevation(0.0f);
                hotSearchLin.setTranslationZ(0.0f);
            }

        }

    }

    /**
     * 搜索
     */
    @OnClick(R.id.search_edit_text)
    void searchClick() {
        if (mLastPointer != null)
//            mNavigator.searchText(this, mLastPointer.latitude, mLastPointer.longitude, getStringServiceType(), REQUEST_CODE_SEARCH);
            mNavigator.NewSearchText(this, mLastPointer.latitude, mLastPointer.longitude, getStringServiceType(), REQUEST_CODE_SEARCH, mSearchEditText.getText().toString());
    }

    public void searchText() {
        if (mLastPointer != null)
//            mNavigator.searchText(this, mLastPointer.latitude, mLastPointer.longitude, getStringServiceType(), REQUEST_CODE_SEARCH);
            mNavigator.NewSearchText(this, mLastPointer.latitude, mLastPointer.longitude, getStringServiceType(), REQUEST_CODE_SEARCH, mSearchEditText.getText().toString());
    }

    @OnClick(R.id.scan)
    void scan() {
        PermissionUtils.requestCameraPermission(mContext, new IBaseAcpListener() {
            @Override
            public void onMarshmallowLaterClick() {

            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onGranted() {
                mNavigator.scanQrCode(mContext);
            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });
    }

    @OnFocusChange(R.id.search_edit_text)
    void searchFocusChange(boolean focused) {
        if (focused && mLastPointer != null) {
//            mNavigator.searchText(this, mLastPointer.latitude, mLastPointer.longitude, getStringServiceType(), REQUEST_CODE_SEARCH);
            mNavigator.NewSearchText(this, mLastPointer.latitude, mLastPointer.longitude, getStringServiceType(), REQUEST_CODE_SEARCH, mSearchEditText.getText().toString());
        }
    }

    public void setMenuPhoto(String url) {
        Glide.with(this)
                .load(url)
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 4, 0))
                .placeholder(R.mipmap.user_default_portrait_small)
                .into(mPhoto);
    }

    @Override
    public void showError(Throwable e) {
        mBaiduMap.clear();
        addLocationMarker();
        if (mSearchMode.equals(QUERY_MODE_VOICE)) {
            mSearchEditText.setText("");
            if (e instanceof NoServiceException) {
                play(SOUND_NO_SERVICE);
            } else if (e instanceof OutOfServiceException) {
                play(SOUND_OUT_OF_SERVICE);
            }
            mSearchPromptContent.setText(e.getMessage());
        } else if (e instanceof AuthException) {
            unreadIcon.setVisibility(View.INVISIBLE);

        } else {
            super.showError(e);
        }
    }

    @Override
    public void homeShowLoading() {
        if (loadingProgressBar != null) {
            loadingProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void homeHighLoging() {
        if (loadingProgressBar != null) {
            loadingProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * 地图元素读取上次缓存数据
     */
    @Override
    public void mapDataGetError() {
        if (!isMapDataFirst) { //第一次获取数据失败，获取上次缓存的数据
//            String serviceSearchStr = PreferencesUtil.getString(StaticData.NEARBY);
//            ServiceSearchInfo serviceSearchInfo = new Gson().fromJson(serviceSearchStr, ServiceSearchInfo.class);
//            boolean hasWholeMerchant = PreferencesUtil.getBoolean(StaticData.HAS_WHOLEMER_CHANT);
//            render(serviceSearchInfo.getWrapperList(), hasWholeMerchant, serviceSearchInfo.getWholeServiceNumber());
        }
    }

    //加载图片显示图标
    private void showMaker(final LatLng latLng, final String id, final String defaultService, @Type final int serviceType, final String url, @MarkerType final int type) {
        if (DEBUG)
            Log.d(TAG, "showMaker: latLng = " + latLng + " id = " + id + " url = " + url + " type = " + type);
        BitmapDescriptor bitmapDescriptor;
        switch (type) {
            case MARKER_TYPE_PHOTO_BUSINESS:
            case MARKER_TYPE_PHOTO_WORKER:
                Glide.with(this).load(url)
                        .override(mPicWidth, mPicHeight)
                        .placeholder(R.mipmap.portrait_default)
                        //    .bitmapTransform(new RoundedCornersTransformation(getActivity(), mRadius, 0))
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                if (DEBUG)
                                    Log.d(TAG, "onResourceReady: resource  = null ? " + (resource == null));
                                if (resource == null) {
                                    mMakerPhoto.setImageResource(type == MARKER_TYPE_PHOTO_WORKER ? R.mipmap.worker_portrait_default : R.mipmap.business_portrait_default);
                                } else {

                                    mMakerPhoto.setImageDrawable(resource);
                                }
                                mMarkerService.setText(defaultService);
                                mMakerTemplate.setBackgroundResource(R.mipmap.map_marker_bg_unchecked);
                                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(mMakerTemplate);
                                Bundle bundle = new Bundle(5);
                                bundle.putInt(PHOTO_ICON_KEY, type);
                                bundle.putString(PHOTO_URL_KEY, url);
                                bundle.putString(MARKER_ID_KEY, id);
                                bundle.putInt(MARKER_TYPE_KEY, serviceType);
                                bundle.putString(MARKER_DEFAULT_SERVICE_KEY, defaultService);
                                addMakerToMap(latLng, bitmapDescriptor, bundle, 0);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                mMakerPhoto.setImageResource(type == MARKER_TYPE_PHOTO_WORKER ? R.mipmap.worker_portrait_default : R.mipmap.business_portrait_default);
                                mMarkerService.setText(defaultService);
                                mMakerTemplate.setBackgroundResource(R.mipmap.map_marker_bg_unchecked);
                                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(mMakerTemplate);
                                Bundle bundle = new Bundle(5);
                                bundle.putInt(PHOTO_ICON_KEY, type);
                                bundle.putString(PHOTO_URL_KEY, url);
                                bundle.putString(MARKER_ID_KEY, id);
                                bundle.putInt(MARKER_TYPE_KEY, serviceType);
                                bundle.putString(MARKER_DEFAULT_SERVICE_KEY, defaultService);
                                addMakerToMap(latLng, bitmapDescriptor, bundle, 0);
                            }
                        });

                return;
            case MARKER_TYPE_BUSINESS:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_shop_marker);
                break;
            case MARKER_TYPE_WORKER:
            default:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_worker_marker);
                break;

        }
        Bundle bundle = new Bundle(5);
        bundle.putInt(PHOTO_ICON_KEY, type);
        bundle.putString(PHOTO_URL_KEY, url);
        bundle.putString(MARKER_ID_KEY, id);
        bundle.putInt(MARKER_TYPE_KEY, serviceType);
        bundle.putString(MARKER_DEFAULT_SERVICE_KEY, defaultService);
        addMakerToMap(latLng, bitmapDescriptor, bundle, 0);
    }


    //小图标取消选中后恢复
    private void resetThumbnails(Marker marker) {
        int type = marker.getExtraInfo().getInt(PHOTO_ICON_KEY);
        BitmapDescriptor bitmapDescriptor;
        switch (type) {
            case MARKER_TYPE_BUSINESS:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_shop_marker);
                break;
            case MARKER_TYPE_WORKER:
            default:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_worker_marker);
                break;
        }
        marker.setIcon(bitmapDescriptor);
    }


    //选中或取消maker
    private void checkMarker(final Marker marker, String url, final boolean checked) {
        int type = marker.getExtraInfo().getInt(PHOTO_ICON_KEY);
        final boolean isWorker = (type == MARKER_TYPE_PHOTO_WORKER || type == MARKER_TYPE_WORKER);
        Glide.with(this).load(url)
                .override(mPicWidth, mPicHeight)
                //     .bitmapTransform(new RoundedCornersTransformation(getActivity(), mRadius, 0))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if (resource == null) {
                            mMakerPhoto.setImageResource(isWorker ? R.mipmap.worker_portrait_default : R.mipmap.business_portrait_default);
                        } else {
                            mMakerPhoto.setImageDrawable(resource);
                        }
                        String service = marker.getExtraInfo().getString(MARKER_DEFAULT_SERVICE_KEY);
                        mMarkerService.setText(service);
                        mMakerTemplate.setBackgroundResource(checked ? R.mipmap.map_marker_bg_checked
                                : R.mipmap.map_marker_bg_unchecked);

                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(mMakerTemplate);
                        marker.setIcon(bitmapDescriptor);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        mMakerPhoto.setImageResource(isWorker ? R.mipmap.worker_portrait_default : R.mipmap.business_portrait_default);
                        String service = marker.getExtraInfo().getString(MARKER_DEFAULT_SERVICE_KEY);
                        mMarkerService.setText(service);
                        mMakerTemplate.setBackgroundResource(checked ? R.mipmap.map_marker_bg_checked
                                : R.mipmap.map_marker_bg_unchecked);

                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(mMakerTemplate);
                        marker.setIcon(bitmapDescriptor);
                    }
                });
    }

    //在地图上添加图标
    private void addMakerToMap(LatLng latLng, BitmapDescriptor bitmapDescriptor, Bundle extra, int index) {

        if (DEBUG)
            Log.d(TAG, "addMakerToMap: " + latLng);
        //构建MarkerOption，用于在地图上添加Marker
        MarkerOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptor)
                .extraInfo(extra);
        //在地图上添加Marker，并显示
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
        markersMap.put(index, marker);
    }

    @Override
    public void showService(List<IServiceInfo> serviceInfoList) {
        if (DEBUG)
            LogUtils.d(TAG, "showService: serviceInfoList.size = " + serviceInfoList.size());
        mBaiduMap.clear();//清楚所有标记
    }

    @Override
    public void call(WorkerBussinesModel workerBussinesModel, boolean callAble) {
        selectedWorkerBussinesModel = workerBussinesModel;
        if (callAble) {
            dial(workerBussinesModel.getPhoneNumber());
        }


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

    /**
     * 显示所有的图标
     *
     * @param latLng
     * @param id
     * @param entity
     */
    private void showMarker(final boolean isBusinessWorker, final Boolean isWholeCityProvider, final Boolean isWorker, String photoUrl, final LatLng latLng, String id, final int index, final com.homepaas.sls.domain.entity.ServiceType entity, final boolean isChecked) {
        final Bundle extraInfo = new Bundle();
        extraInfo.putInt(MARKER_INDEX_KEY, index);
        Glide.with(getContext())
                .load(photoUrl)
                .crossFade()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mMarkerImg.setImageDrawable(resource);
                        if (isChecked) {
                            mExplainText.setVisibility(View.GONE);
                            mExplianTextAll.setVisibility(View.GONE);
                            mMarkerLin.setVisibility(View.GONE);
                            if (isWholeCityProvider) {
                                mMarkerHouseLin.setVisibility(View.VISIBLE);
                                mMarkerWorkerLin.setVisibility(View.GONE);
                                mMarkerHouseLin.setBackgroundResource(R.mipmap.green_house);
                            } else {
                                if (isWorker) {
                                    if (isBusinessWorker) {
                                        mMarkerHouseLin.setVisibility(View.VISIBLE);
                                        mMarkerWorkerLin.setVisibility(View.GONE);
                                        mMarkerHouseLin.setBackgroundResource(R.mipmap.blue_house);
                                    } else {
                                        mMarkerHouseLin.setVisibility(View.GONE);
                                        mMarkerWorkerLin.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    mMarkerHouseLin.setVisibility(View.VISIBLE);
                                    mMarkerWorkerLin.setVisibility(View.GONE);
                                    mMarkerHouseLin.setBackgroundResource(R.mipmap.blue_house);
                                }
                            }
                        } else {
                            mMarkerLin.setVisibility(View.VISIBLE);
                            mMarkerWorkerLin.setVisibility(View.GONE);
                            mMarkerHouseLin.setVisibility(View.GONE);
                            if (isWholeCityProvider) {
                                if (isFirstMarker) {
                                    selectedMap.put(SELECT_INDEX, index);
                                    mExplainText.setVisibility(View.GONE);
                                    mExplianTextAll.setVisibility(View.VISIBLE);
                                    isFirstMarker = false;
                                } else {
                                    mExplainText.setVisibility(View.GONE);
                                    mExplianTextAll.setVisibility(View.GONE);
                                    isFirstMarker = false;
                                }
                                mMarkerLin.setBackgroundResource(R.mipmap.green);
                            } else {
                                mExplainText.setVisibility(View.VISIBLE);
                                mExplianTextAll.setVisibility(View.GONE);
                                if (isWorker) {
                                    if (isBusinessWorker) {
                                        mMarkerLin.setBackgroundResource(R.mipmap.blue);
                                        if (entity != null) {
                                            mExplainText.setText(entity.getName() == null ? "" : entity.getName());
                                        } else {
                                            mExplainText.setText("");
                                        }
                                    } else {
                                        mMarkerLin.setBackgroundResource(R.mipmap.orange);
                                        if (entity != null) {
                                            mExplainText.setText(entity.getName() == null ? "" : entity.getName());
                                        } else {
                                            mExplainText.setText("");
                                        }
                                    }
                                } else {
                                    mMarkerLin.setBackgroundResource(R.mipmap.blue);
                                    if (entity != null) {
                                        mExplainText.setText(entity.getName() == null ? "" : entity.getName());
                                    } else {
                                        mExplainText.setText("");
                                    }
                                }
                            }

                        }
                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(mNewMarkerTemplate);
                        addMakerToMap(latLng, bitmapDescriptor, extraInfo, index);
                    }
                });
    }

    public void showConfirmCompleleteWindow(PushInfo pushInfo) {
        confirmCompleteWindow.get().bindData(pushInfo);
        confirmCompleteWindow.get().showAtLocation(mSearchVoice, Gravity.BOTTOM, 0, 0);
    }

    private EditInvitationCodeDialog editInvitationCodeDialog;

    public void showEditDialog(String code) {
        editInvitationCodeDialog = EditInvitationCodeDialog.newInstance(code);
        editInvitationCodeDialog.setOnCancelListener(new EditInvitationCodeDialog.OnCancelListener() {
            @Override
            public void cancel() {
                editInvitationCodeDialog.dismiss();
            }
        });
        editInvitationCodeDialog.setOnConfirmListener(new EditInvitationCodeDialog.OnConfirmListener() {
            @Override
            public void comfirm(String it_code) {
                mainContentPresenter.addRecommendInfo(it_code);
                editInvitationCodeDialog.dismiss();
            }
        });
        editInvitationCodeDialog.show(getFragmentManager(), null);
    }

    @Override
    public void commit(String msg) {
        if (!TextUtils.isEmpty(msg))
            showMessage(msg);

    }

    /**
     * 是否领取了红包
     *
     * @param checkIsReceivedRedCoupsEntry
     */
    @Override
    public void renderReceivedRedCoups(CheckIsReceivedRedCoupsEntry checkIsReceivedRedCoupsEntry) {
        this.checkIsReceivedRedCoupsEntry = checkIsReceivedRedCoupsEntry;
        if (checkIsReceivedRedCoupsEntry != null && !TextUtils.isEmpty(checkIsReceivedRedCoupsEntry.getIsReceived())
                && TextUtils.equals(checkIsReceivedRedCoupsEntry.getIsReceived(), "0")
                && !TextUtils.isEmpty(checkIsReceivedRedCoupsEntry.getTitle())
                && !TextUtils.isEmpty(checkIsReceivedRedCoupsEntry.getUrl())) {
            redCoupsRel.setVisibility(VISIBLE);
            redCoupsText.setText(checkIsReceivedRedCoupsEntry.getTitle());
        } else {
            redCoupsRel.setVisibility(View.GONE);
        }

    }

    public void setService(ServiceItemModel itemModel) {
        mSearchEditText.setText(itemModel.getName());
        mSearchEditText.setSelection(itemModel.getName().length());
        mServiceId = itemModel.getId();
        mSearchContent = itemModel.getName();
//        mSearchMode = QUERY_MODE_TYPE;
        mSearchMode = QUERY_MODE_TEXT;
        search();
    }

    void setServiceContent(String content) {
        mSearchContent = content;
        if (mSearchContent.isEmpty()) {
            mSearchMode = QUERY_MODE_DEFAULT;
        } else {
            mSearchMode = QUERY_MODE_TEXT;
        }
        //search();
    }

    private int searchTypeCount = 0;

    /*
    地图切换工人商户信息
       默认同时显示工人和商户
       第一次单击显示工人信息，第二次显示商户，第三次，同时选中，取模进行
     */
    @OnClick(R.id.choose_worker_business)
    void chooseWorkerBuinsee() {
        searchTypeCount += 1;
        if (searchTypeCount % 3 == 0) {
            mSearchType = SEARCH_TYPE_NONE;
            mChooseWorkerBuiness.setImageResource(R.mipmap.home_filtrate1);
        } else if (searchTypeCount % 3 == 1) {
            mSearchType = SEARCH_TYPE_WORKER;
            mChooseWorkerBuiness.setImageResource(R.mipmap.home_filtrate3);
        } else {
            mSearchType = SEARCH_TYPE_BUSINESS;
            mChooseWorkerBuiness.setImageResource(R.mipmap.home_filtrate2);
        }
        search();
    }


    @OnCheckedChanged(R.id.check_shop)
    void checkBusiness(CompoundButton checkbox, boolean checked) {
        if (checked) {
            mSearchType |= SEARCH_TYPE_BUSINESS;
        } else {
            mSearchType &= ~SEARCH_TYPE_BUSINESS;
        }
        search();
    }

    @OnCheckedChanged(R.id.check_worker)
    void checkWorker(CompoundButton checkbox, boolean checked) {
        if (checked) {
            mSearchType |= SEARCH_TYPE_WORKER;
        } else {
            mSearchType &= ~SEARCH_TYPE_WORKER;
        }
        search();
    }

    /*
     * 添加当前位置图标
     */
    private void addLocationMarker() {
        if (mSelfLocation == null || selfLocationView != null)
            return;
        selfLocationView = getActivity().getLayoutInflater().inflate(R.layout.breathing_view, null);
        MapViewLayoutParams selfLP = new MapViewLayoutParams.Builder()
                .position(mSelfLocation)
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .build();
        mMapView.addView(selfLocationView, selfLP);
    }

    /*
     * 重新定位
     */
    @OnClick(R.id.location)
    void location() {
        mLocationHelper.start();
    }


    @OnClick({R.id.menu_photo, R.id.message_entry})
    void onMenuClick(View view) {
        if (view.getId() == R.id.menu_photo) {
//            mOnInteractListener.onMenuClick(GravityCompat.START);
        } else if (view.getId() == R.id.message_entry) {
//            mOnInteractListener.onMenuClick(GravityCompat.END);
            if (View.VISIBLE == unreadIcon.getVisibility()) {
                unreadIcon.setVisibility(View.GONE);
            }
            try {
                if (!TextUtils.isEmpty(personalInfoPDataSource.getToken()))
                    mNavigator.viewMessage(getActivity());
                else
                    mNavigator.login(MainContentFragment.this, REQUEST_MESSAGE);
            } catch (GetPersistenceDataException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void initializeInjector() {
        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_content;
    }

    @Override
    protected void initView() {
        LogUtils.i("MainContentFragment:initView");
        services = new ArrayList<>();
        //循环缩略信息
        viewPagerBriefCard.setOnSelectedPositon(this);
        viewPagerBriefCard.setHandleManage(this);

        mainContentPresenter.setMainView(this);
        serviceSearchPresenter.setServiceSearchView(this);
        messagePresenter.setUnReadMessageCountView(this);
        homeHotServicePresenter.setHomeHotSeviceView(this);
        homeHotServiceAdapter = new HomeHotServiceAdapter();
        homeHotServiceAdapter.setOnItemClickListener(this);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(homeHotServiceAdapter);
        EventBus.getDefault().register(this);


        mHomeHotServiceInfoList = new ArrayList<String>();
        mTagBaseAdapter = new NewTagBaseAdapter(getActivity(), mHomeHotServiceInfoList);
        homeTaglistView.setLimit(true);
        homeTaglistView.setLimitCount(3);
        homeTaglistView.setAdapter(mTagBaseAdapter);
        mHomeHotServiceInfos = new ArrayList<>();
        List<HotServiceInfo> cacheList = new ArrayList<>();
        cacheList = AppPreferences.getHotServiceList(getActivity());
        if (cacheList != null) {
            setHotServive(cacheList);
        }


        //蒙层显示的服务类型tablayout单击 搜索显示地图对应服务的marker数据
        homeTaglistView.setItemClickListener(new TagLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (mHomeHotServiceInfos != null && position < mHomeHotServiceInfos.size() && mHomeHotServiceInfos.get(position) != null) {
                    searchServiceList(mHomeHotServiceInfos.get(position).getName(), mHomeHotServiceInfos.get(position).getServiceId(), position, true);
                    setVisition(0);
                    imageCount = 0;
                }
            }
        });

        mMakerTemplate = getActivity().getLayoutInflater().inflate(R.layout.map_marker, null);
        mNewMarkerTemplate = getActivity().getLayoutInflater().inflate(R.layout.new_marker_template, null);
        mExplainText = (TextView) mNewMarkerTemplate.findViewById(R.id.explain_text);
        mExplianTextAll = (TextView) mNewMarkerTemplate.findViewById(R.id.explain_text_all);
        mMarkerLin = (LinearLayout) mNewMarkerTemplate.findViewById(R.id.marker_lin);
        mMarkerHouseLin = (LinearLayout) mNewMarkerTemplate.findViewById(R.id.marker_house_lin);
        mMarkerWorkerLin = (LinearLayout) mNewMarkerTemplate.findViewById(R.id.marker_worker_lin);
        mMarkerImg = (CircleImageView) mNewMarkerTemplate.findViewById(R.id.marker_img);

        mMakerPhoto = (ImageView) mMakerTemplate.findViewById(R.id.photo);
        mMarkerService = (TextView) mMakerTemplate.findViewById(R.id.marker_service);
        taglistviewSecondLin.setAlpha(0.4f);

        mPicHeight = getResources().getDimensionPixelSize(R.dimen.marker_photo_height);
        mPicWidth = getResources().getDimensionPixelSize(R.dimen.marker_photo_width);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap = new SparseIntArray(3);
        mSoundPoolMap.put(SOUND_NO_SERVICE, mSoundPool.load(getActivity(), R.raw.no_service, 1));
        mSoundPoolMap.put(SOUND_OUT_OF_SERVICE, mSoundPool.load(getActivity(), R.raw.out_of_service, 1));

        //地图相关初始化
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
        mBaiduMap.setOnMapStatusChangeListener(mOnMapStatusChangeListener);
        mBaiduMap.setOnMapClickListener(mapClickListener);
        mLocationHelper = LocationHelper.sharedInstance(getActivity());
        mLocationHelper.addOnLocatedListener(new LocationHelper.OnLocatedListener() {
            @Override
            public void onLocated(BDLocation bdLocation) {
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatus mapStatus = new MapStatus.Builder()
                        .target(ll)
                        .zoom(15)
                        .build();
                MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
                mBaiduMap.animateMapStatus(u);
                mLastPointer = ll;
                mSelfLocation = ll;
                addLocationMarker();
                //定位成功搜索地图数据
                search();
            }
        });
        //隐藏baiduMap logo ---这尼玛是侵权
        View logo = mMapView.getChildAt(1);
        if (logo != null)
            logo.setVisibility(View.INVISIBLE);
        if (requestRuntimePermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_LOCATION)) {
            mLocationHelper.start();
        }
        //获取消息是否未读
        messagePresenter.getUnreadMessageCount();
        //检查更新
        mainContentPresenter.checkUpdate();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isShowMapView)
            mMapView.onResume();
        isShowMapView = true;
        imageCount = 0;
        setVisition(imageCount % 2);
        mainContentPresenter.getCheckIsReceivedRedCoups();
        messagePresenter.getUnreadMessageCount();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
    }


    @Override
    public void onDestroyView() {
        mMapView.onDestroy();
        super.onDestroyView();
        mainContentPresenter.destroy();
        mLocationHelper.clearOnLocatedListener();
//        mSpeechRecognizer.destroy();
        EventBus.getDefault().unregister(this);
    }

    /*
     *播放语音提示
     * @param soundType SOUND_NO_SERVICE ， SOUND_OUT_OF_SERVICE
     */
    private void play(int soundType) {
        mSoundPool.play(mSoundPoolMap.get(soundType), 1, 0.5f, 1, 0, 1);
    }

    // 拨打商户或工人电话
    private void dial(String phone) {
        List<String> group = new ArrayList<>();
        group.add(Manifest.permission_group.PHONE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), REQUEST_PERMISSION_CALL_AND_CALL_LOG)) {
            mainContentPresenter.startDial();
            mNavigator.call(getActivity(), phone);
        } else {
            mCallingPhone = phone;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                mMapView.showZoomControls(false);
                mBaiduMap.setMyLocationEnabled(true);
                mLocationHelper.start();
                break;

            case REQUEST_PERMISSION_CALL_AND_CALL_LOG:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                dial(mCallingPhone);
                break;

            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0) {
                    for (int gra : grantResults) {
                        if (gra != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                }
                scan();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SEARCH:
                    String content = data.getStringExtra(SEARCH_CONTENT);
                    String gobacktype = data.getStringExtra(NewSearchActivity.GO_BACK_TYPE);

                    if (TextUtils.equals(gobacktype, "1")) {
                        if (TextUtils.isEmpty(content)) {
                            searchServiceList("全部", "", 0, true);
                        }
                    } else {
                        mSearchEditText.setText(content);
                        mSearchEditText.setSelection(content.length());
                        mSearchEditText.requestFocus();
                        ServiceSearchInfo searchInfo = SearchServiceHolder.take();
                        int position = returnBackService(content);
                        ScrollService(-1);
                        setModel(content, position);
                        if (TextUtils.isEmpty(content)) ;//取消搜索的回调，不处理，方便理解
                        else if (searchInfo != null && searchInfo.isOrdering()) //一键下单逻辑
                            onPreOrder(searchInfo.getServiceTypeId(), searchInfo.getServiceName());
                        else {
                            //一般搜索逻辑
                            if (searchInfo == null || searchInfo.getWrapperList().isEmpty()) {
                                //提示没有结果
                                alertNoSearchResult(content);
                                return;
                            } else {
                                show(searchInfo.getWrapperList(), searchInfo.getWholeServiceNumber());
                            }
                        }
                    }
                    break;
                case REQUEST_MESSAGE:
                    if (data != null && data.getBooleanExtra("Status", false))
                        mNavigator.viewMessage(getActivity());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 处理从搜索页返回过来的值后类型的变化
     */

    private void setModel(String returnStr, int position) {
        if (position != -1 && mHomeHotServiceInfos != null && position < mHomeHotServiceInfos.size()) {
            HotServiceInfo returnServiceInfo = mHomeHotServiceInfos.get(position);
            mServiceId = returnServiceInfo.getServiceId();
            mSearchContent = returnStr;
//            mSearchMode = QUERY_MODE_TYPE;
            mSearchMode = QUERY_MODE_TEXT;
        } else {
            mServiceId = null;
            mSearchContent = returnStr;
            mSearchMode = QUERY_MODE_TEXT;
        }
    }

    /**
     * 处理从搜索页返回过来的值和选择条字相对应的position
     */
    private int returnBackService(String returnStr) {
        if (mHomeHotServiceInfos != null) {
            for (int i = 0; i < mHomeHotServiceInfos.size(); i++) {
                if (mHomeHotServiceInfos.get(i).getName().equals(returnStr)) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * 处理从搜索页返回过来的值和选择条字相对应
     */

    private void ScrollService(int position) {
        homeHotServiceAdapter.setOnClick(position, lastPosition);
        recyclerView.smoothScrollToPosition(position == -1 ? 0 : position);
        lastPosition = position;
        mTagBaseAdapter.setPosition(-1);
    }

    private void alertNoSearchResult(String searchContent) {
        searchNoResultDialog = new CommonDialog.Builder()
                .showTitle(false)
                .setContent("附近没有工人提供" + searchContent + "服务，试试一键下单， 我们将免费帮您安排工人上门服务")
                .setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchNoResultDialog.dismiss();
                    }
                })
                .setConfirmButton("一键下单", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ConfirmOrderActivity.class));
                    }
                })
                .create();
        searchNoResultDialog.show(getFragmentManager(), null);
    }

    //展示搜索结果并且打开左侧列表
    private void show(List<IService> wrapperList, int wholeAllNumber) {
        this.wholeAllNumber = wholeAllNumber;
        render(wrapperList, false, wholeAllNumber);
        zoomToSpan();
//        workerBussinesPoi.openList(true);
        //如果只有一个人，考虑直接跳下单，不过可能并不是满足用户需要的搜索结果？？
    }


    private CommonDialog dialogUpdate;
    private CommonDialog dialogForceUpdate;

    private UpdateCheck updateCheck;

    @Override
    public void update(final UpdateCheck updateCheck) {
    }


    @Subscribe
    public void onEvent(EventPersonalInfo event) {
        if (event.changeType == EventPersonalInfo.LOGIN_STATE) {
            search();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    /**
     * 服务列表数据
     *
     * @param hotServiceInfos
     */
    @Override
    public void renderHotService(List<HotServiceInfo> hotServiceInfos) {
//        mHomeHotServiceInfos.clear();
        mHomeHotServiceInfos = hotServiceInfos;
        if (mHomeHotServiceInfos != null) {
            hotServiceText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            HotServiceInfo hotServiceInfo = new HotServiceInfo();
            hotServiceInfo.setName("全部");
            hotServiceInfo.setServiceId("-1");
            hotServiceInfos.add(0, hotServiceInfo);
            mHomeHotServiceInfos = hotServiceInfos;
            mHomeHotServiceInfoList.clear();
            for (int i = 0; i < mHomeHotServiceInfos.size(); i++) {
                mHomeHotServiceInfoList.add(hotServiceInfos.get(i).getName());
            }
            mTagBaseAdapter.setPosition(0);
            mTagBaseAdapter.setList(mHomeHotServiceInfoList);
            homeHotServiceAdapter.setDate(mHomeHotServiceInfos);
            AppPreferences.serHotService(getActivity(), mHomeHotServiceInfos);
        }
    }

    private void setHotServive(List<HotServiceInfo> hotServiceInfos) {
        if (hotServiceInfos != null) {
            hotServiceText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mHomeHotServiceInfoList.clear();
            for (int i = 0; i < hotServiceInfos.size(); i++) {
                mHomeHotServiceInfoList.add(hotServiceInfos.get(i).getName());
            }
            mTagBaseAdapter.setPosition(0);
            mTagBaseAdapter.setList(mHomeHotServiceInfoList);
            homeHotServiceAdapter.setDate(hotServiceInfos);
        }
    }


    @Override
    public void ItemClick(String name, String serviceId, int position) {
        imageCount = 0;
        searchServiceList(name, serviceId, position, false);

    }

    private int lastPosition = 0;

    private void searchServiceList(String searchContent, String serviceId, int position, boolean hasScroll) {
//        mSearchEditText.setText(searchContent);
        if (TextUtils.equals("全部", searchContent)) {
            mServiceId = null;
            mSearchContent = "";
            mSearchEditText.setText("");
            mSearchMode = QUERY_MODE_DEFAULT;
        } else {
            mServiceId = serviceId;
            mSearchContent = searchContent;
//            mSearchMode = QUERY_MODE_TYPE;
            mSearchMode = QUERY_MODE_TEXT;
            mSearchEditText.setText(searchContent);
        }
        String serviceType = getStringServiceType();
        if (mLastPointer != null) {
            serviceSearchPresenter.searchService(String.valueOf(mLastPointer.latitude),
                    String.valueOf(mLastPointer.longitude),
                    mSearchContent, mServiceId, serviceType, mSearchMode);
        }
        homeHotServiceAdapter.setOnClick(position, lastPosition);
        if (hasScroll) {
            recyclerView.smoothScrollToPosition(position);
        }
        lastPosition = position;
        mTagBaseAdapter.setPosition(position);
    }


}
