package com.homepaas.sls.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.fragment.SimpleLazyLoadFragment;
import com.homepaas.sls.util.LogUtils;

import butterknife.Bind;


/**
 * Created by mhy on 2017/8/16.
 */

public class TestCFragment extends SimpleLazyLoadFragment {
    @Bind(R.id.tv_txt)
    TextView tvTxt;
    private String tag = "TestCFragment";

    public static TestCFragment newInstance() {
        Bundle args = new Bundle();
        TestCFragment fragment = new TestCFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i("TAG", "onDestroyView:" + tag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("TAG", "onCreate:" + tag);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment;
    }

    @Override
    protected void initView() {
        LogUtils.i("TAG", "initView" + tag);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.i("onDetach", "onCreate:" + tag);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("onDestroy", "onCreate:" + tag);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i("TAG", "onViewCreated" + tag);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            LogUtils.i("TAG", "setUserVisibleHint:可见" + tag);

        } else {
            LogUtils.i("TAG", "setUserVisibleHint:不可见" + tag);

        }
    }

    @Override
    protected void onVisible() {
        super.onVisible();
//        LogUtils.i("TAG", "SimpleBaseFragment:可见" + tag);

    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
//        LogUtils.i("TAG", "SimpleBaseFragment:不可见" + tag);

    }

    @Override
    protected void initData() {
        tvTxt.setText("初始化数据");
        LogUtils.i("TAG", "initData" + tag);
    }
}
