package com.homepaas.sls.ui.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerNewSearchServiceComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.mvp.presenter.SearchService.SearchServicePresenter;
import com.homepaas.sls.mvp.view.SearchService.SearchServiceView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.BusinessOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ConfirmOrderActivity;
import com.homepaas.sls.ui.search.adapter.SearchServiceResultAdapter;
import com.homepaas.sls.ui.tag.Adapter.NewTagBaseAdapter;
import com.homepaas.sls.ui.tag.TagLayout;
import com.homepaas.sls.ui.widget.LimitEditText;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.tag.Tag;
import com.homepaas.sls.util.StaticData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_DEFAULT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_TEXT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_VOICE;

/**
 * Created by JWC on 2016/12/20.
 */

public class NewSearchActivity extends CommonBaseActivity implements SearchServiceView, SearchServiceResultAdapter.OnButtonClickListener, SearchServiceResultAdapter.OnGoDetailClickListener {
    private static final String TAG = "NewSearchActivity";

    @Bind(R.id.search_edit_text)
    LimitEditText searchEditText;
    @Bind(R.id.search_edit_clean)
    ImageView searchEditClean;
    @Bind(R.id.search_or_cancel)
    TextView searchOrCancel;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.delete_history)
    ImageView deleteHistory;
    @Bind(R.id.service_type_lin)
    LinearLayout serviceTypeLin;
    @Bind(R.id.no_result)
    TextView noResult;
    @Bind(R.id.no_result_bg)
    LinearLayout noResultBg;
    @Bind(R.id.tagview1)
    TagLayout tagview1;
    @Bind(R.id.tagview2)
    TagLayout tagview2;
    @Bind(R.id.show)
    LinearLayout mShow;
    private double mLatitude;
    private double mLongitude;
    private String mSearchType;
    private String mEdittextStr;

    //判断搜索有没有结果
    private boolean hasService = true;
    private boolean clickSearch = false;

    private static final String SEARCH_CONTENT = Navigator.SEARCH_CONTENT;
    public static final String GO_BACK_TYPE = "go_back_type";
    private static final String SPLIT = "11235813211123581321";
    private ArrayList<Tag> mTags = new ArrayList<Tag>();
    private ArrayList<Tag> mTagsHot = new ArrayList<Tag>();
    long time1 = 0;

    private SoundPool mSoundPool;
    private SparseIntArray mSoundPoolMap;
    private static final int SOUND_NO_SERVICE = 1;
    private static final int SOUND_OUT_OF_SERVICE = 2;

    @SearchServicePresenter.QueryType
    private String mQueryType = QUERY_MODE_DEFAULT;
    @Inject
    Navigator mNavigator;
    private static int count = 0;
    private boolean isPause = false;
    private static int delay = 500;
    private static int period = 500;
    private static final int UPDATE_TEXTVIEW = 0;
    private SharedPreferences sharedPreferences;

    @Inject
    SearchServicePresenter searchServicePresenter;

    private SearchServiceResultAdapter searchServiceResultAdapter;
    private String mReturnBackContent;

    //热门标签
    private NewTagBaseAdapter mHotTagBaseAdapter;
    private List<String> mHotServiceInfoList;
    private List<HotServiceInfo> mHotServiceInfos;

    //历史标签
    private NewTagBaseAdapter mHistoryTagBaseAdapter;
    private List<String> mHistoryServiceInfoList;

    //计时功能
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXTVIEW:
                    if (!TextUtils.isEmpty(searchEditText.getText())) {
                        if (searchEditText.getText().toString().matches("[\u4e00-\u9fa5]+") || searchEditText.getText().toString().matches("[a-zA-Z /]+")) {
                            mQueryType = QUERY_MODE_TEXT;
                            searchServiceResultAdapter.setEdittextText(searchEditText.getText().toString());
                            getSearchServiceList(searchEditText.getText().toString(), mQueryType);
                        } else {
                            Toast.makeText(getApplicationContext(), "请输入汉字",
                                    Toast.LENGTH_SHORT).show();
                            restoreFfirst();
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
        return R.layout.activity_newsearch;
    }

    @Override
    protected void initView() {
        sharedPreferences = getSharedPreferences(StaticData.SPF_NAME, MODE_PRIVATE);
        mLatitude = getIntent().getDoubleExtra(Navigator.SEARCH_LAT, 0);
        mLongitude = getIntent().getDoubleExtra(Navigator.SEARCH_LNG, 0);
        mSearchType = getIntent().getStringExtra(Navigator.SEARCH_TYPE);
        mEdittextStr = getIntent().getStringExtra(Navigator.SEARCH_EDITTEXTSTR);
        searchServiceResultAdapter = new SearchServiceResultAdapter(this);
        searchServiceResultAdapter.setOnButtonClickListener(this);
        searchServiceResultAdapter.setOnGoDetailClickListener(this);
        searchServiceResultAdapter.setEdittextText(mEdittextStr);
        recyclerView.addItemDecoration(new SimpleItemDecoration(this, SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(searchServiceResultAdapter);
        searchServicePresenter.setSearchServiceView(this);
        searchServicePresenter.getHotServiceInfo(String.valueOf(mLatitude), String.valueOf(mLongitude));
        mHotServiceInfoList = new ArrayList<String>();
        mHotTagBaseAdapter = new NewTagBaseAdapter(this, mHotServiceInfoList);
        tagview1.setLimit(true);
        tagview1.setLimitCount(3);
        tagview1.setAdapter(mHotTagBaseAdapter);
        mHistoryServiceInfoList = new ArrayList<String>();
        mHistoryTagBaseAdapter = new NewTagBaseAdapter(this, mHistoryServiceInfoList);
        tagview2.setLimit(true);
        tagview2.setLimitCount(3);
        tagview2.setAdapter(mHistoryTagBaseAdapter);
        showHistorySearch();
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (searchEditText.getText().toString().matches("[\u4e00-\u9fa5]+") || searchEditText.getText().toString().matches("[a-zA-Z /]+")) {
                        clickSearch = true;
                        if (System.currentTimeMillis() - time1 > 1500) {
                            time1 = System.currentTimeMillis();
                            mReturnBackContent = searchEditText.getText().toString().trim();
                            returnMapBack(mReturnBackContent, Constant.QUERY_MODE_TEXT, "");
                            return true;
                        }
                    } else if (!hasService) {
                        return true;
                    } else {
                        Toast.makeText(getApplicationContext(), "请输入汉字或字母",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(searchEditText.getText().toString())) {
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(UPDATE_TEXTVIEW, 500);
                    } else {
                        mHandler.sendEmptyMessageDelayed(UPDATE_TEXTVIEW, 500);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tagview1.setItemClickListener(new TagLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                clickSearch = false;
                mQueryType = QUERY_MODE_TEXT;
                mReturnBackContent = mHotServiceInfos.get(position).getName();
                returnMapBack(mReturnBackContent, mQueryType, mHotServiceInfos.get(position).getServiceId());
            }
        });
        tagview2.setItemClickListener(new TagLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                clickSearch = false;
                mQueryType = QUERY_MODE_TEXT;
                mReturnBackContent = mHistoryServiceInfoList.get(position);
                returnMapBack(mReturnBackContent, mQueryType, "");
            }
        });
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap = new SparseIntArray(3);
        mSoundPoolMap.put(SOUND_NO_SERVICE, mSoundPool.load(this, R.raw.no_service, 1));
        mSoundPoolMap.put(SOUND_OUT_OF_SERVICE, mSoundPool.load(this, R.raw.out_of_service, 1));
        if (!TextUtils.isEmpty(mEdittextStr)) {
            searchEditText.setText(mEdittextStr);
            int position = mEdittextStr.length();
            searchEditText.setSelection(position);
            getSearchServiceList(mEdittextStr, mQueryType);
        }

        addKeyboard();
    }

    @Override
    protected void initData() {

    }


    private void addKeyboard() {
        searchEditText.setFocusable(true);
        searchEditText.setFocusableInTouchMode(true);
        searchEditText.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void setSharedPreferences(String historyStr) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.OLD_HISTORY_SERVICE, historyStr);
        editor.commit();
    }

    /**
     * 显示历史搜索
     */

    private void showHistorySearch() {
//        String string = AppPreferences.getTags(NewSearchActivity.this);
        String string = sharedPreferences.getString(StaticData.OLD_HISTORY_SERVICE, "");
        Log.d("111", "历史结果：" + string);
        mHistoryServiceInfoList.clear();
        if (string.length() == 0) {
            mShow.setVisibility(View.GONE);
        } else {
            mShow.setVisibility(View.GONE);
            String[] tags = string.split(SPLIT);
            for (int i = 0; i < tags.length; i++) {
                mHistoryServiceInfoList.add(tags[i]);
            }
            mHistoryTagBaseAdapter.setList(mHistoryServiceInfoList);
        }
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

    @Override
    public void render(ServiceSearchInfo serviceSearchInfo) {
        hasService = true;
        serviceTypeLin.setVisibility(View.GONE);
        if (serviceSearchInfo == null || serviceSearchInfo.getSearchServiceList().isEmpty()) {
            noResultBg.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noResultBg.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            List<IService> services = serviceSearchInfo.getSearchServiceList();
            searchServiceResultAdapter.setData(services);
        }
    }

    @Override
    public void renderHotService(List<HotServiceInfo> hotServiceInfos) {
        this.mHotServiceInfos = hotServiceInfos;
        mHotServiceInfoList.clear();
        if (hotServiceInfos != null) {
            for (int i = 0; i < hotServiceInfos.size(); i++) {
                mHotServiceInfoList.add(hotServiceInfos.get(i).getName());
            }
        }
        mHotTagBaseAdapter.setList(mHotServiceInfoList);

    }

    @Override
    public void renderRetrurnBack(ServiceSearchInfo serviceSearchInfo) {
        serviceTypeLin.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        if (serviceSearchInfo == null || serviceSearchInfo.getWrapperList().isEmpty()) {
            noResultBg.setVisibility(View.VISIBLE);
        } else {
            closeKeyboard();
            if (clickSearch = true) {
                setHistory(mReturnBackContent);
            }
            noResultBg.setVisibility(View.GONE);
            SearchServiceHolder.put(serviceSearchInfo);
            Intent intent = getIntent();
            intent.putExtra(SEARCH_CONTENT, mReturnBackContent);
            intent.putExtra(GO_BACK_TYPE, "0");
            setResult(RESULT_OK, intent);
            ActivityCompat.finishAfterTransition(this);
        }

    }

    @Override
    public void renderNoResult() {
        hasService = false;
        serviceTypeLin.setVisibility(View.GONE);
        noResultBg.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchEditText, InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }


    @Override
    public void ItemClick(WorkerBussinesModel workerBussinesModel, int type) {
        if (type == Constant.SERVICE_TYPE_INT) {
            Intent intent = new Intent(this, ConfirmOrderActivity.class);
            intent.putExtra(StaticData.SEARCH_SERVICE_TYPE_ID, workerBussinesModel.getId());
            intent.putExtra(StaticData.SEARCH_SERVICE_NAME, workerBussinesModel.getName());
            intent.putExtra(StaticData.SEARCH_OR_BUTTON, "0");
            startActivity(intent);
        } else if (type == Constant.SERVICE_TYPE_WORKER_INT || type == Constant.WHOLE_SERVICE_TYPE_WORKER_INT) {
            mNavigator.getOrder(getContext(), workerBussinesModel.getType(), workerBussinesModel.getId(), workerBussinesModel.getGender());
        } else {
            BusinessOrderActivity.start(getContext(), Constant.SERVICE_TYPE_BUSINESS_INT, workerBussinesModel.getId());
        }
    }


    @OnClick({R.id.delete_history, R.id.search_or_cancel, R.id.search_edit_clean})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_or_cancel:
                Intent intent = getIntent();
                intent.putExtra(SEARCH_CONTENT, searchEditText.getText().toString());
                intent.putExtra(GO_BACK_TYPE, "1");
                setResult(RESULT_OK, intent);
                ActivityCompat.finishAfterTransition(this);
                break;
            case R.id.delete_history:
//                AppPreferences.setTags(NewSearchActivity.this, "");
                setSharedPreferences("");
                mTags.clear();
                tagview2.removeAllViews();
                mShow.setVisibility(View.GONE);
                break;
            case R.id.search_edit_clean:
                restoreFfirst();
                break;
            default:
        }
    }

    private void restoreFfirst() {
        searchEditText.setText("");
        serviceTypeLin.setVisibility(View.VISIBLE);
        noResultBg.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }


    /**
     * 获取列表请求
     *
     * @param editText
     * @param queryType
     */
    private void getSearchServiceList(String editText, String queryType) {
        searchServicePresenter.getSearchServiceList(String.valueOf(mLatitude), String.valueOf(mLongitude), Constant.SERVICE_TYPE_ALL, editText, queryType, "");
    }

    /**
     * 返回地图
     *
     * @param editText
     * @param queryType
     */
    private void returnMapBack(String editText, String queryType, String serviceId) {
        searchServicePresenter.rerutnBackMap(String.valueOf(mLatitude), String.valueOf(mLongitude), mSearchType, editText, queryType, serviceId);
    }

    private void setHistory(String tag) {
//        String string = AppPreferences.getTags(NewSearchActivity.this);
        String string = sharedPreferences.getString(StaticData.OLD_HISTORY_SERVICE, "");
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
//            AppPreferences.setTags(NewSearchActivity.this, string);
            setSharedPreferences(string);
        } else {
            string = tag;
//            AppPreferences.setTags(NewSearchActivity.this, tag);
            setSharedPreferences(tag);
        }
        String[] tags = string.split(SPLIT);
        mHistoryServiceInfoList.clear();
        for (int i = 0; i < tags.length; i++) {
            mHistoryServiceInfoList.add(tags[i]);

        }
        mHistoryTagBaseAdapter.setList(mHistoryServiceInfoList);
        mShow.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        if (mQueryType.equals(QUERY_MODE_VOICE)) {
            searchEditText.setText("");
            if (e instanceof NoServiceException) {
                play(SOUND_NO_SERVICE);
            } else if (e instanceof OutOfServiceException) {
                play(SOUND_OUT_OF_SERVICE);
            }
        } else {
            super.showError(e);
        }
    }

    /*
     *播放语音提示
     * @param soundType SOUND_NO_SERVICE ， SOUND_OUT_OF_SERVICE
     */
    private void play(int soundType) {
        mSoundPool.play(mSoundPoolMap.get(soundType), 1, 0.5f, 1, 0, 1);
    }


    @Override
    public void goDetailClick(WorkerBussinesModel workerBussinesModel, int type) {
        if (type == Constant.SERVICE_TYPE_WORKER_INT || type == Constant.WHOLE_SERVICE_TYPE_WORKER_INT) {
            mNavigator.showMerchantWorkerDetail(this, Constant.SERVICE_TYPE_WORKER, workerBussinesModel.getId());
//            mNavigator.showWorkerDetail(this, workerBussinesModel.getId());
        } else if (type == Constant.SERVICE_TYPE_BUSINESS_INT || type == Constant.WHOLE_SERVICE_TYPE_BUSINESS_INT) {
            mNavigator.showMerchantWorkerDetail(this, Constant.SERVICE_TYPE_BUSINESS, workerBussinesModel.getId());
//            mNavigator.showBusinessDetail(this, workerBussinesModel.getId());
        } else {
            clickSearch = false;
            mQueryType = QUERY_MODE_TEXT;
            mReturnBackContent = workerBussinesModel.getName();
            returnMapBack(mReturnBackContent, mQueryType, workerBussinesModel.getId());
        }
    }
}
