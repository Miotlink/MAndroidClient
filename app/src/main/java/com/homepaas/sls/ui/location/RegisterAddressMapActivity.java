package com.homepaas.sls.ui.location;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.homepaas.sls.R;
import com.homepaas.sls.ui.adapter.DividerItemDecoration;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.location.location.AddressModel;
import com.homepaas.sls.ui.location.location.GeoCoderHelper;
import com.homepaas.sls.ui.location.location.LocationHelper;
import com.homepaas.sls.ui.location.location.SuggestionAddressModel;
import com.homepaas.sls.ui.location.location.SuggestionSearchHelper;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.PermissionUtils;
import com.runtimepermission.acp.AcpListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class RegisterAddressMapActivity extends CommonBaseActivity implements ServiceAddressSearchAdapter.OnSuggestionLaltgListener {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.search_edit_text)
    EditText searchEditText;
    @Bind(R.id.detele)
    ImageView detele;
    @Bind(R.id.search_edit_ll)
    LinearLayout searchEditLl;
    @Bind(R.id.cancel_search)
    TextView cancelSearch;
    @Bind(R.id.topPanel)
    LinearLayout topPanel;
    @Bind(R.id.mapView)
    TextureMapView mapView;
    @Bind(R.id.center_pointer)
    Space centerPointer;
    @Bind(R.id.location)
    ImageView location;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch;
    @Bind(R.id.no_search_result)
    LinearLayout noSearchResult;
    @Bind(R.id.search_result)
    FrameLayout searchResult;
    @Bind(R.id.anchor)
    View anchor;

    private static final int REQUEST_PERMISSION_LOCATION = 0;
    private boolean isLocationSuccess;//是否定位成功

    private BaiduMap mBaiduMap;
    private CommonDialog locationDialog;
    private RegisterAddressRecyclerviewAdapter adapter;
    private GeoCoderHelper geoCoderHelper;
    private LocationHelper mLocationHelper;
    private ServiceAddressSearchAdapter serviceAddressSearchAdapter;
    private List<AddressModel> mapAddressDataList;
    private List<SuggestionAddressModel> suggestionAddressModelList;
    private SuggestionSearchHelper suggestionSearchHelper;
    private ReverseGeoCodeResult.AddressComponent currentAddressComponent;
    private TextWatcher mAddressTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(searchEditText.getText())) {
                back.setVisibility(View.GONE);
                detele.setVisibility(View.VISIBLE);
                suggestionSearchHelper.setKeyword(searchEditText.getText().toString());
                suggestionSearchHelper.requestSuggestion();
            } else {
                suggestionSearchHelper.setKeyword("");
//                clearData();
                detele.setVisibility(View.INVISIBLE);
                serviceAddressSearchAdapter.clearData();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @OnClick(R.id.search_edit_text)
    public void searchOnClick() {
        show();
    }

    @OnFocusChange(R.id.search_edit_text)
    public void searchFocuse(boolean focused) {
        if (focused)
            show();
    }

    private void hidden() {
        detele.setVisibility(View.INVISIBLE);
        cancelSearch.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
    }

    private void show() {
//        detele.setVisibility(View.VISIBLE);
        back.setVisibility(View.GONE);
        cancelSearch.setVisibility(View.VISIBLE);
        searchResult.setVisibility(View.VISIBLE);
    }


    private BaiduMap.OnMapClickListener mOnMapClickListener = new BaiduMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            hidden();

        }

        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            return false;
        }
    };
    private BaiduMap.OnMapStatusChangeListener mOnMapStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            LatLng latLng = mapStatus.target;//地图中心点
            geoCoderHelper.setLatLng(latLng);
            geoCoderHelper.reverseGeoCode();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_address_map;
    }

    @Override
    protected void initView() {
        initBaiduMap();
        geoCoderHelper = new GeoCoderHelper();
        suggestionSearchHelper = new SuggestionSearchHelper();
        initLocationHelper();
        searchResult.setVisibility(View.GONE);
        mapAddressDataList = new ArrayList<>();
        suggestionAddressModelList = new ArrayList<>();
        initRecyclerView();
        geoCoderHelper.setOnReverseGeoCodeResultListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
            @Override
            public void result(Object obj, LatLng latLng) {
                adapter.setData((ReverseGeoCodeResult) obj, latLng);
                currentAddressComponent = ((ReverseGeoCodeResult) obj).getAddressDetail();

            }
        });
        suggestionSearchHelper.setListener(new SuggestionSearchHelper.OnResultListener() {
            @Override
            public void result(Object object) {
                serviceAddressSearchAdapter.setData((SuggestionResult) object);
            }
        });
        searchEditText.addTextChangedListener(mAddressTextWatcher);


//        //请求必要的权限：位置信息，读取电话状态（获取deviceId必须）,存储空间（下载更新文件，保存图片必须）
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_LOCATION);
//            }
//            return;
//        }
        requestLocation();
    }

    /**
     * 请求定位
     */
    protected void requestLocation() {
        //是否开启定位权限
        PermissionUtils.requestLocationPermission(mContext, new AcpListener() {
            @Override
            public void onGranted() {
                if (!PermissionUtils.gpsIsOpen(mContext)) {    // 定位未开启
                    locationDialog = new CommonDialog.Builder()
                            .setContent("未开启定位开关")
                            .setCancelButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    locationDialog.dismiss();
                                }
                            })
                            .setConfirmButton("开启", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    mContext.startActivity(intent);
                                }
                            }).showTitle(false).create();
                    locationDialog.show(((CommonBaseActivity) mContext).getSupportFragmentManager(), null);
                    isLocationSuccess = false;
                } else {
                    mapView.showZoomControls(false);
                    mBaiduMap.setMyLocationEnabled(true);
                    mLocationHelper.start();
                    isLocationSuccess = true;
                }
            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });
    }

    @Override
    protected void initData() {
    }
//
//    /**
//     * 调起权限请求窗口
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_PERMISSION_LOCATION) {
//            for (int gra : grantResults) {
//                if (gra != PackageManager.PERMISSION_GRANTED) {
////                    //用户不同意，向用户展示该权限作用
////                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
////                        AlertDialog.Builder dia = new AlertDialog.Builder(this);
////                        dia.setTitle("如果不开启定位，页面将无法正常显示");
////                               dia .setPositiveButton("确定", new DialogInterface.OnClickListener() {
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int which) {
////                                        finish();
////                                    }
////                                });
////                               dia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
////                                    @TargetApi(Build.VERSION_CODES.M)
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int which) {
////                                       requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
////                                                Manifest.permission.READ_PHONE_STATE,
////                                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_LOCATION);
////                                    }
////                                });
////                        dia.create().show();
////                        return;
////                    }
////                    finish();
//                    return;
//                }
//            }
//            mapView.showZoomControls(false);
//            mBaiduMap.setMyLocationEnabled(true);
//            mLocationHelper.start();
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!isLocationSuccess) {
            requestLocation();
        }
    }

    public void showNoResult() {
        searchResult.setVisibility(View.VISIBLE);
        noSearchResult.setVisibility(View.VISIBLE);
        recyclerViewSearch.setVisibility(View.INVISIBLE);
    }

    public void showSearchResult() {
        searchResult.setVisibility(View.VISIBLE);
        noSearchResult.setVisibility(View.INVISIBLE);
        recyclerViewSearch.setVisibility(View.VISIBLE);
    }

    public void hiddenKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @OnClick(R.id.cancel_search)
    public void hiddenSearch() {
        hiddenKeyBoard();
//        if ( serviceAddressSearchAdapter.getItemCount() == 0 ){
        back.setVisibility(View.VISIBLE);
        cancelSearch.setVisibility(View.GONE);
        searchResult.setVisibility(View.GONE);
        deleteText();
//        }
//        serviceAddressSearchAdapter.clearData();


    }


    @OnClick(R.id.detele)
    public void deleteText() {
        searchEditText.setText("");
        suggestionSearchHelper.setKeyword("");
//       clearData();
        detele.setVisibility(View.INVISIBLE);
        serviceAddressSearchAdapter.clearData();
    }

    public void clearData() {
        searchResult.setVisibility(View.VISIBLE);
        noSearchResult.setVisibility(View.INVISIBLE);
        recyclerViewSearch.setVisibility(View.INVISIBLE);
    }

    /**
     * 百度地图初始化配置
     */
    private void initBaiduMap() {
        //地图相关初始化
        mapView.showZoomControls(false);
        mBaiduMap = mapView.getMap();
        //隐藏baiduMap logo
        View logo = mapView.getChildAt(1);
        logo.setVisibility(View.INVISIBLE);
        //设置地图缩放级别16 类型普通地图
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setOnMapStatusChangeListener(mOnMapStatusChangeListener);
        mBaiduMap.setOnMapClickListener(mOnMapClickListener);
    }

    /**
     * 定位初始化
     */
    private void initLocationHelper() {
        mLocationHelper = LocationHelper.sharedInstance(this);
        mLocationHelper.addOnLocatedListener(new LocationHelper.OnLocatedListener() {
            @Override
            public void onLocated(BDLocation bdLocation) {
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                moveTolocation(ll);
                addLocationMarker(ll);
                SuggestionSearchHelper.setCity(bdLocation.getCity());

                geoCoderHelper.setLatLng(ll);
                geoCoderHelper.reverseGeoCode();
            }
        });
    }


    /**
     * 配置RecyclerView
     */
    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        adapter = new RegisterAddressRecyclerviewAdapter(this, mapAddressDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter.setOnGetAddressListener(new RegisterAddressRecyclerviewAdapter.OnGetAddressListener() {
            @Override
            public void getAddress(AddressModel addressModel, ReverseGeoCodeResult.AddressComponent component) {
                LogUtils.i("TAG", addressModel.toString());
                Intent intent = new Intent();
                if (!TextUtils.isEmpty(addressModel.resultAdress)) {//当前定位地址
                    intent.putExtra("type", 1);
                    intent.putExtra("addressModel", addressModel);
//                    intent.putExtra("address",addressModel.resultAdress);
                } else {//pio搜索地址
                    intent.putExtra("type", 2);
//                    intent.putExtra("address",addressModel.poiAddress);
//                    intent.putExtra("address_poiName",addressModel.poiName);
                    intent.putExtra("addressModel", addressModel);
                }
//                intent.putExtra("LatLng",addressModel.latLng);
//                intent.putExtra("AddressComponent",component);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //recyclerView.addItemDecoration(new RecyclerviewDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        recyclerViewSearch.setHasFixedSize(true);
        serviceAddressSearchAdapter = new ServiceAddressSearchAdapter(suggestionAddressModelList, this);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearch.setLayoutManager(layoutManager2);
        recyclerViewSearch.setAdapter(serviceAddressSearchAdapter);
        recyclerViewSearch.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        serviceAddressSearchAdapter.setOnSuggestionLaltgListener(this);
    }


    /**
     * 添加当前位置图标
     *
     * @param latLng
     */
    private void addLocationMarker(LatLng latLng) {
//        if (latLng == null)
//            return;
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.map_location_self);
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(descriptor);
        mBaiduMap.addOverlay(option);
    }

    /**
     * 移动到定位点
     *
     * @param latLng
     */
    private void moveTolocation(LatLng latLng) {
        MapStatus mapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(15)
                .build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.animateMapStatus(u);
    }


    @OnClick(R.id.location)
    public void location() {
        requestLocation();
    }


    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
    }


    @Override
    protected void onDestroy() {
        mapView.onDestroy();

        super.onDestroy();
        mLocationHelper.clearOnLocatedListener();
        geoCoderHelper.clear();
        suggestionSearchHelper.clear();
        hiddenKeyBoard();
    }

    @Override
    public void getLaltg(SuggestionAddressModel suggestionAddressModel) {
        searchResult.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        Intent intent = new Intent();
        intent.putExtra("type", 3);
        intent.putExtra("addressModel", suggestionAddressModel);
        setResult(RESULT_OK, intent);
        finish();
    }
}
