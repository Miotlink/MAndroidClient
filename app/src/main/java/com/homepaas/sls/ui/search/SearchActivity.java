package com.homepaas.sls.ui.search;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerSearchServiceComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.exception.NoServiceException;
import com.homepaas.sls.domain.exception.OutOfServiceException;
import com.homepaas.sls.event.NoSearchServiceEvent;
import com.homepaas.sls.mvp.presenter.SearchPresenter;
import com.homepaas.sls.mvp.view.SearchView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.search.adapter.AutoCompleteAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class SearchActivity extends CommonBaseActivity implements SearchView {

    private static final String TAG = "SearchActivity";

    private static final String SEARCH_CONTENT = Navigator.SEARCH_CONTENT;
    private static final String ILLEGAL_CHARACTER = "'";

    @Bind(R.id.search_text)
    AutoCompleteTextView mSearchTextView;

    @Bind(R.id.search_or_cancel)
    TextView mSearchOrCancel;

    @Bind(R.id.recommend_content)
    View mRecommend;

    @Bind(R.id.service_item_list)
    GridView mGridView;

    @Bind(R.id.no_result_bg)
    View mNoResult;

    @Bind(R.id.auto_edit_wrapper)
    FrameLayout mAutoEditWrapper;

    @Inject
    SearchPresenter mPresenter;

    @Bind(R.id.no_result)
    TextView mNoResultText;

    @Bind(R.id.no_result_recommend)
    TextView mNoResultRecommend;

    private double mLatitude;

    private double mLongitude;

    private String mSearchType;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mLatitude = getIntent().getDoubleExtra(Navigator.SEARCH_LAT, 0);
        mLongitude = getIntent().getDoubleExtra(Navigator.SEARCH_LNG, 0);
        mSearchType = getIntent().getStringExtra(Navigator.SEARCH_TYPE);


        mSearchTextView.setDropDownBackgroundResource(R.drawable.drop_background);
        mSearchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // XXX: 2016/5/11 0011
                if (position == 0 && parent.getAdapter().getItem(0).toString().equals("没有相应服务，请换个关键词试试")) {
                    mSearchTextView.setText("");
                    return;
                }
                search();
            }
        });
        mPresenter.setSearchView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }



    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerSearchServiceComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.clear_recommend)
    void clear() {
        mRecommend.setVisibility(View.GONE);
    }

//    @OnFocusChange(R.id.search_text)
//    void showSearchPrompt(boolean focused) {
//        if (focused) {
//
//        }
//    }


    @OnEditorAction(R.id.search_text)
    boolean search() {
        mNoResult.setVisibility(View.GONE);
        mPresenter.searchByText(mSearchTextView.getText().toString().trim(), mLatitude, mLongitude, mSearchType);
        return true;
    }

    @OnClick(R.id.search_text)
    void showPop() {
        if (TextUtils.isEmpty(mSearchTextView.getText().toString().trim())) {
            mSearchTextView.showDropDown();
        }
    }

    @OnTextChanged(value = {R.id.search_text}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onEditSearchWord(Editable editable) {
        if (TextUtils.isEmpty(editable.toString())) {
            mSearchOrCancel.setText("取消");
        } else {
            mSearchOrCancel.setText("搜索");
        }
    }

    @OnClick(R.id.search_or_cancel)
    void cancel() {
        if (TextUtils.isEmpty(mSearchTextView.getText().toString().trim())) {
            Intent intent = getIntent();
            intent.putExtra(SEARCH_CONTENT, "");
            setResult(RESULT_OK, intent);
            ActivityCompat.finishAfterTransition(this);
        } else {
            mNoResult.setVisibility(View.GONE);
            mPresenter.searchByText(filter(mSearchTextView.getText().toString().trim()), mLatitude, mLongitude, mSearchType);
        }
    }

    //过滤拼音输入时可能会加入的分割符（非法字符）
    private String filter(String content) {
        int index;
        StringBuilder stringBuilder = new StringBuilder(content.length());
        if (content.contains(ILLEGAL_CHARACTER)) {
            while ((index = content.indexOf(ILLEGAL_CHARACTER)) > -1) {
                stringBuilder.append(content.substring(0, index));
                content = content.substring(index+1);
            }
            stringBuilder.append(content);

            return stringBuilder.toString();
        }else return content;
    }

    @OnClick(R.id.delete_text)
    void onDelete() {
        mSearchTextView.setText("");
    }

    @OnItemClick(R.id.service_item_list)
    void selectItem(int position) {
        String lifeServiceItem = mPresenter.selectService(position);
        mSearchTextView.setText(lifeServiceItem);
        mSearchTextView.setSelection(lifeServiceItem.length());
    }


    @Override
    public void render(List<String> lifeService) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.search_hot_recommend_item, android.R.id.text1, lifeService);
        mGridView.setAdapter(arrayAdapter);
    }

    @Override
    public void prepareSuggestion(List<String> lifeService) {

        AutoCompleteAdapter adapter = new AutoCompleteAdapter(lifeService);
        mSearchTextView.setAdapter(adapter);
        mSearchTextView.setDropDownWidth(mAutoEditWrapper.getWidth());
        mSearchTextView.showDropDown();
    }

    @Override
    public void showSearchResult(ServiceSearchInfo serviceSearchInfo) {

        SearchServiceHolder.put(serviceSearchInfo);
        Intent intent = getIntent();
        intent.putExtra(SEARCH_CONTENT, mSearchTextView.getText().toString().trim());
        setResult(RESULT_OK, intent);
        ActivityCompat.finishAfterTransition(this);
    }

    private void showNoService() {
        mNoResult.setVisibility(View.VISIBLE);
        mRecommend.setVisibility(View.GONE);
        mNoResultText.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.search_no_result_bg, 0, 0);
        mNoResultRecommend.setText("请换个关键词试试");
    }

    private void showOutOfService() {
        mNoResult.setVisibility(View.VISIBLE);
        mRecommend.setVisibility(View.GONE);
        mNoResultText.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.search_out_of_service_bg, 0, 0);
        mNoResultRecommend.setText("当前城市还未开通相应服务，敬请期待");
    }


    @Override
    public void showError(Throwable e) {
        if (e instanceof NoServiceException) {
            EventBus.getDefault().post(new NoSearchServiceEvent(mSearchTextView.getText().toString().trim()));
            ActivityCompat.finishAfterTransition(this);
//            showNoService();
        } else if (e instanceof OutOfServiceException) {
            showOutOfService();
        } else {
            super.showError(e);
        }

    }
}
