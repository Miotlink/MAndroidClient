package com.homepaas.sls.ui.common.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.R;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.ui.widget.MoreLoadable;
import com.homepaas.sls.ui.widget.Refreshable;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.networkerrorview.CommonNetWorkErrorViewReplacer;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * on 2016/7/12 0012
 *
 * @author zhudongjie
 */
public abstract class BaseListFragment<T> extends CommonBaseFragment implements HeaderViewLayout.OnRefreshListener {

    @Bind(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    protected HeaderViewLayout refreshLayout;
    private boolean moreLoadable = false;
    //网络错误布局替换工具
    protected CommonNetWorkErrorViewReplacer baseListFragmentViewReplacer;
    private List<T> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_with_list;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addItemDecoration(getItemDecoration());
        baseListFragmentViewReplacer = new CommonNetWorkErrorViewReplacer(getActivity(), refreshLayout, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                //刷新数据
                networkErrorRefresh();
            }
        });
        return view;
    }

    //网络错误布局点击刷新数据方法
    public void networkErrorRefresh() {

    }

    public interface OnListIsEmpty {
        void listIsEmpty();
    }

    private OnListIsEmpty onListIsEmpty;

    public void setOnListIsEmpty(OnListIsEmpty onListIsEmpty) {
        this.onListIsEmpty = onListIsEmpty;
    }

    @SuppressWarnings("unchecked")
    public void render(@Nullable List<T> list) {
        onStopRefresh();
        if (list == null || list.isEmpty()) {
            if (onListIsEmpty != null) {
                onListIsEmpty.listIsEmpty();
            }
            if (moreLoadable) {
                refreshLayout.setCanLoadMore(false);
            }
            setEmptyView();
        } else {
            showOriginalLayout();//还原之前的布局
            if (moreLoadable) {
                refreshLayout.setCanLoadMore(true);
            }
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter == null) {
                adapter = initAdapter(list);
                mList = list;
                recyclerView.setAdapter(adapter);
            } else {
                ((Refreshable<T>) adapter).refresh(list);
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (refreshLayout != null) {
            refreshLayout.stopRefresh();
            refreshLayout.destory();
        }
        super.onDestroyView();
    }

    public void onStopRefresh() {
        if (refreshLayout != null) {
            refreshLayout.stopRefresh();
        }
    }


    @Override
    public void showError(Throwable e) {
        onStopRefresh();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null && mList.size() == 0)//没有历史数据网络错误通过布局提示，有历史数据通过toast进行提示
        {
            showNetWorkError();
        }
        super.showError(e);
    }

    @SuppressWarnings("unchecked")
    public void renderMore(@Nullable List<T> list) {
        refreshLayout.stopRefresh();
        mList.addAll(list);
        refreshLayout.setCanLoadMore(!(list == null || list.isEmpty()));
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        ((MoreLoadable<T>) adapter).addMore(list);
    }

    public abstract RecyclerView.Adapter initAdapter(List<T> list);


    public void setEmptyView(@DrawableRes int resId, @StringRes int msg) {
        if (baseListFragmentViewReplacer != null) {
            baseListFragmentViewReplacer.showEmptyView(resId, msg);
        }
    }

    public void setEmptyView() {
        if (baseListFragmentViewReplacer != null) {
            baseListFragmentViewReplacer.showEmptyView();
        }
    }

    public void setEmptyView(@DrawableRes int resId, String msg) {
        if (baseListFragmentViewReplacer != null) {
            baseListFragmentViewReplacer.showEmptyView(resId, msg);
        }
    }

    public void setEmptyView(String msg) {
        if (baseListFragmentViewReplacer != null) {
            baseListFragmentViewReplacer.showEmptyView(msg);
        }
    }

    public void showNetWorkError() {
        if (baseListFragmentViewReplacer != null)
            baseListFragmentViewReplacer.showErrorLayout();
    }

    public void showOriginalLayout() {
        if (baseListFragmentViewReplacer != null)
            baseListFragmentViewReplacer.showOriginalLayout();
    }

    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST);
    }

    public void setMoreLoadable(boolean moreLoadable) {
        this.moreLoadable = moreLoadable;
        refreshLayout.setCanLoadMore(moreLoadable);
    }

    @Override
    public void onRefresh() {
        mList.clear();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onModeChanged(@HeaderViewLayout.Mode int mode) {

    }

    /**
     * 网络发生变化时刻调用
     */
    public void notifyNetWorkChange(boolean isConnected) {
    }
}
