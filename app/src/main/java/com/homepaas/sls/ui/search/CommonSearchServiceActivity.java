package com.homepaas.sls.ui.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerNewSearchServiceComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.mvp.presenter.SearchService.CommonSearchServicePresenter;
import com.homepaas.sls.mvp.view.SearchService.CommonSearchServiceView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.homepage_3_3_0.DetailWebActivity;
import com.homepaas.sls.ui.order.servicemerchant.ServiceMerchantActivity;
import com.homepaas.sls.ui.search.adapter.SearchDetailResultAdapter;
import com.homepaas.sls.ui.tag.Adapter.TagBaseAdapter;
import com.homepaas.sls.ui.tag.TagLayout;
import com.homepaas.sls.ui.widget.LimitEditText;
import com.homepaas.sls.util.StaticData;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by JWC on 2017/3/23.
 * 3.3.0的搜索页面
 */

public class CommonSearchServiceActivity extends CommonBaseActivity implements SearchDetailResultAdapter.OnDetailResultClickListener, CommonSearchServiceView {


    private static final String TAG = "CommonSearchServiceActivity";
    @Bind(R.id.search_edit_text)
    LimitEditText searchEditText;
    @Bind(R.id.search_edit_clean)
    ImageView searchEditClean;
    @Bind(R.id.auto_edit_wrapper)
    FrameLayout autoEditWrapper;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.detail_result_recycler_view)
    RecyclerView detailResultRecyclerView;
    @Bind(R.id.common_tagview_all)
    TagLayout commonTagviewAll;
    @Bind(R.id.delete_history)
    ImageView deleteHistory;
    @Bind(R.id.no_history_text)
    TextView noHistoryText;
    @Bind(R.id.common_tagview_history)
    TagLayout commonTagviewHistory;
    @Bind(R.id.service_type_lin)
    LinearLayout serviceTypeLin;
    @Bind(R.id.no_result_lin)
    LinearLayout noResultLin;
    @Bind(R.id.people_search)
    TextView peopleSearch;
    @Bind(R.id.common_tagview_people_search)
    TagLayout commonTagviewPeopleSearch;
    //大家都在搜的标签
    private TagBaseAdapter hotTagBaseAdapter;
    private List<String> hotServiceStrList;
    private List<ServiceItem> hotServiceItemList;
    //历史标签
    private TagBaseAdapter historyTagBaseAdapter;
    private List<String> historyServiceStrList;

    //没有结果的时候大家都在搜的标签
    private TagBaseAdapter peopleSearchTagBaseAdapter;
    private List<String> peopleSearchServiceStrList;
    private List<ServiceItem> peopleSearchServiceItemList;

    private String mLatitude;
    private String mLongitude;

    @Inject
    CommonSearchServicePresenter commonSearchServicePresenter;
    private SearchDetailResultAdapter searchDetailResultAdapter;
    private List<ServiceItem> detailResultList;

    private static final String SPLIT = "11235813211123581321";
    private static final int UPDATE_EDITTEXT = 99;
    private SharedPreferences sharedPreferences;


    public static void start(Context context, String latitude, String longitude) {
        Intent intent = new Intent(context, CommonSearchServiceActivity.class);
        intent.putExtra(StaticData.LATITUDE, latitude);
        intent.putExtra(StaticData.LONGITUDE, longitude);
        context.startActivity(intent);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_EDITTEXT:
                    if (!TextUtils.isEmpty(searchEditText.getText().toString())) {
                        if (searchEditText.getText().toString().matches("[\u4e00-\u9fa5]+") || searchEditText.getText().toString().matches("[a-zA-Z /]+")) {
                            commonSearchServicePresenter.getResultService(mLongitude, mLatitude, searchEditText.getText().toString());
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_search;
    }

    @Override
    protected void initView() {
        sharedPreferences = getSharedPreferences(StaticData.SPF_NAME, MODE_PRIVATE);
        mLatitude = getIntent().getStringExtra(StaticData.LATITUDE);
        mLongitude = getIntent().getStringExtra(StaticData.LONGITUDE);
        addKeyboard();
        setRecycleViewAdapter();
        setTagView();
        showHistorySearch();
        commonSearchServicePresenter.setCommonSearchServiceView(this);
        commonSearchServicePresenter.getHotSearchService(mLongitude, mLatitude);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(searchEditText.getText().toString())) {
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

        commonTagviewAll.setItemClickListener(new TagLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (!TextUtils.isEmpty(hotServiceItemList.get(position).getDetailUrl())) {
                    ServiceItem serviceItem = hotServiceItemList.get(position);
                    //isFlagMerchantService; 标记详情页是否出现商家的tab页面 0：不出现 1:出现
                    if (TextUtils.isEmpty(serviceItem.getIsFlagMerchantService()) || serviceItem.getIsFlagMerchantService().equals("0")) {
                        //跳转详情页面
                        DetailWebActivity.start(mContext, serviceItem);
                    } else {
                        //跳转到非标订单详情页面
                        ServiceMerchantActivity.start(mContext, serviceItem);
                    }
//                    DetailWebActivity.start(CommonSearchServiceActivity.this, hotServiceItemList.get(position));
                }
            }
        });
        commonTagviewHistory.setItemClickListener(new TagLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                Log.d(TAG, "搜索数据tag" + historyServiceStrList.get(position));
                searchEditText.setText(historyServiceStrList.get(position));
            }
        });
        commonTagviewPeopleSearch.setItemClickListener(new TagLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (!TextUtils.isEmpty(peopleSearchServiceItemList.get(position).getDetailUrl())) {
                    ServiceItem serviceItem = peopleSearchServiceItemList.get(position);
                    //isFlagMerchantService; 标记详情页是否出现商家的tab页面 0：不出现 1:出现
                    if (TextUtils.isEmpty(serviceItem.getIsFlagMerchantService()) || serviceItem.getIsFlagMerchantService().equals("0")) {
                        //跳转详情页面
                        DetailWebActivity.start(mContext, serviceItem);
                    } else {
                        //跳转到非标订单详情页面
                        ServiceMerchantActivity.start(mContext, serviceItem);
                    }
//                    DetailWebActivity.start(CommonSearchServiceActivity.this, peopleSearchServiceItemList.get(position));
                }
            }
        });
    }

    @Override
    protected void initData() {

    }


    private void addKeyboard() {
        searchEditText.setFocusable(true);
        searchEditText.setFocusableInTouchMode(true);
        searchEditText.requestFocus();
//        InputMethodManager inputManager = (InputMethodManager) searchEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.showSoftInput(searchEditText, 0);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void setSharedPreferences(String historyStr) {
        Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.NEW_HISTORY_SERVICE, historyStr);
        editor.commit();
    }

    /**
     * 详细结果
     */
    private void setRecycleViewAdapter() {
        detailResultList = new ArrayList<>();
        searchDetailResultAdapter = new SearchDetailResultAdapter();
        searchDetailResultAdapter.setOnDetailResultClickListener(this);
        detailResultRecyclerView.setAdapter(searchDetailResultAdapter);
    }

    /**
     * 添加标签：大家都在搜和历史标签
     */
    private void setTagView() {
        hotServiceStrList = new ArrayList<>();
        hotTagBaseAdapter = new TagBaseAdapter(this, hotServiceStrList);
        commonTagviewAll.setLimit(true);
        commonTagviewAll.setLimitCount(3);
        commonTagviewAll.setAdapter(hotTagBaseAdapter);
        historyServiceStrList = new ArrayList<>();
        historyTagBaseAdapter = new TagBaseAdapter(this, historyServiceStrList);
        commonTagviewHistory.setLimit(true);
        commonTagviewHistory.setLimitCount(3);
        commonTagviewHistory.setAdapter(historyTagBaseAdapter);
        peopleSearchServiceStrList = new ArrayList<>();
        peopleSearchTagBaseAdapter = new TagBaseAdapter(this, peopleSearchServiceStrList);
        commonTagviewPeopleSearch.setLimit(true);
        commonTagviewPeopleSearch.setLimitCount(3);
        commonTagviewPeopleSearch.setAdapter(peopleSearchTagBaseAdapter);
    }

    /**
     * 显示历史搜索
     */

    private void showHistorySearch() {
//        String string = AppPreferences.getHistoryTags(CommonSearchServiceActivity.this);
        String string = sharedPreferences.getString(StaticData.NEW_HISTORY_SERVICE, "");
        historyServiceStrList.clear();
        if (string.length() == 0) {
            noHistoryText.setVisibility(View.VISIBLE);
        } else {
            noHistoryText.setVisibility(View.GONE);
            String[] tags = string.split(SPLIT);
            for (int i = 0; i < tags.length; i++) {
                historyServiceStrList.add(tags[i]);
            }
            historyTagBaseAdapter.setList(historyServiceStrList);
        }
    }

    /**
     * 添加历史搜索数据
     */
    private void setHistory(String tag) {
//        String string = AppPreferences.getHistoryTags(CommonSearchServiceActivity.this);
        String string = sharedPreferences.getString(StaticData.NEW_HISTORY_SERVICE, "");
        if (string.length() > 0) {
            String[] tags = string.split(SPLIT);
            if (Arrays.asList(tags).contains(tag)) {
                if (string.startsWith(tag)) {
                } else {
                    string = string.replace(SPLIT + tag, "");
                    string = tag + SPLIT + string;
                }
            } else {
                if (tags.length > 9) {
                    string = tag;
                    for (int i = 0; i < 9; i++) {
                        string = string + SPLIT + tags[i];
                    }
                } else
                    string = tag + SPLIT + string;
            }
//            AppPreferences.setHistoryTags(CommonSearchServiceActivity.this, string);
            setSharedPreferences(string);
        } else {
            string = tag;
//            AppPreferences.setHistoryTags(CommonSearchServiceActivity.this, tag);
            setSharedPreferences(tag);
        }
        String[] tags = string.split(SPLIT);
        historyServiceStrList.clear();
        for (int i = 0; i < tags.length; i++) {
            historyServiceStrList.add(tags[i]);

        }
        historyTagBaseAdapter.setList(historyServiceStrList);
        noHistoryText.setVisibility(View.GONE);
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerNewSearchServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.search_edit_clean, R.id.cancel, R.id.delete_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_edit_clean:
                restoreData();
                break;
            case R.id.cancel:
                onBackPressed();
//                ActivityCompat.finishAfterTransition(this);
                break;
            case R.id.delete_history:
//                AppPreferences.setHistoryTags(CommonSearchServiceActivity.this, "");
                setSharedPreferences("");
                commonTagviewHistory.removeAllViews();
                noHistoryText.setVisibility(View.VISIBLE);
                break;
            default:
        }
    }

    /**
     * 还原到原始状态
     */
    private void restoreData() {
        searchEditText.setText("");
        cancel.setVisibility(View.VISIBLE);
        noResultLin.setVisibility(View.GONE);
        serviceTypeLin.setVisibility(View.VISIBLE);
        detailResultList.clear();
        searchDetailResultAdapter.setDetailResultViewList(detailResultList);
        detailResultRecyclerView.setVisibility(View.GONE);
    }

    /**
     * 详情结果点击事件
     */
    @Override
    public void detailResultClickListener(ServiceItem serviceItem) {
        if (serviceItem != null) {
            setHistory(serviceItem.getServiceName());
            //isFlagMerchantService; 标记详情页是否出现商家的tab页面 0：不出现 1:出现
            if (TextUtils.isEmpty(serviceItem.getIsFlagMerchantService()) || serviceItem.getIsFlagMerchantService().equals("0")) {
                //跳转详情页面
                DetailWebActivity.start(mContext, serviceItem);
            } else {
                //跳转到非标订单详情页面
                ServiceMerchantActivity.start(mContext, serviceItem);
            }
//            DetailWebActivity.start(this, serviceItem);
        }
    }

    /**
     * 热门搜索结果
     *
     * @param serviceItemListEntity
     */
    @Override
    public void renderHotService(ServiceItemListEntity serviceItemListEntity) {
        if (serviceItemListEntity != null) {
            hotServiceItemList = serviceItemListEntity.getItems();
            if (hotServiceItemList != null) {
                hotServiceStrList.clear();
                for (int i = 0; i < hotServiceItemList.size(); i++) {
                    hotServiceStrList.add(hotServiceItemList.get(i).getServiceName());
                }
                hotTagBaseAdapter.setList(hotServiceStrList);
            }
            peopleSearchServiceItemList = serviceItemListEntity.getItems();
            if (peopleSearchServiceItemList != null) {
                peopleSearchServiceStrList.clear();
                for (int i = 0; i < peopleSearchServiceItemList.size(); i++) {
                    peopleSearchServiceStrList.add(peopleSearchServiceItemList.get(i).getServiceName());
                }
                peopleSearchTagBaseAdapter.setList(peopleSearchServiceStrList);
            }
        }
    }

    /**
     * 输入关键字得到的结果
     *
     * @param serviceItemListEntity
     */
    @Override
    public void renderResultService(ServiceItemListEntity serviceItemListEntity) {
        if (serviceItemListEntity != null && serviceItemListEntity.getItems().size() > 0) {
            noResultLin.setVisibility(View.GONE);
            detailResultRecyclerView.setVisibility(View.VISIBLE);
            serviceTypeLin.setVisibility(View.GONE);
            searchDetailResultAdapter.setDetailResultViewList(serviceItemListEntity.getItems());
        } else {
            noResultLin.setVisibility(View.VISIBLE);
            detailResultRecyclerView.setVisibility(View.GONE);
            serviceTypeLin.setVisibility(View.GONE);
        }
    }

    /**
     * 按到键盘外隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
