package com.homepaas.sls.ui.widget;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;

import java.util.List;

import butterknife.Bind;

/**
 * on 2016/6/29 0029
 *
 * @author zhudongjie
 */
@SuppressWarnings("ConstantConditions")
public abstract class BaseListActivity<T> extends CommonBaseActivity implements HeaderViewLayout.OnRefreshListener {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Nullable
    @Bind(R.id.empty_content)
    View emptyContent;
    @Nullable
    @Bind(R.id.empty_image)
    ImageView emptyImage;
    @Nullable
    @Bind(R.id.empty_message)
    TextView emptyMessage;
    @Nullable
    @Bind(R.id.empty_description)
    TextView emptyDescription;
    @Bind(R.id.empty_container)
    NestedScrollView containerView;
    @Bind(R.id.refreshLayout)
    HeaderViewLayout refresh;

    private boolean moreLoadable = true;
    protected RecyclerView.Adapter adapter;

    @Override
    protected int getLayoutId() {
        return getContentRes();
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        refresh.setOnRefreshListener(this);
        recyclerView.addItemDecoration(getItemDecoration());
    }

    @Override
    protected void initData() {

    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST);
    }

    protected int getContentRes() {
        return R.layout.activity_with_list;
    }

    @SuppressWarnings("unchecked")
    public void render(@Nullable List<T> list) {
        refresh.stopRefresh();
        if (list == null || list.isEmpty()) {
            showEmpty();
            if (moreLoadable) {
                refresh.setCanLoadMore(false);
            }
        } else {
            if (moreLoadable) {
                refresh.setCanLoadMore(true);
            }
            recyclerView.setVisibility(View.VISIBLE);
            emptyContent.setVisibility(View.GONE);
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter == null) {
                adapter = initAdapter(list);
                recyclerView.setAdapter(adapter);
            } else {
                ((Refreshable<T>) adapter).refresh(list);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void renderMore(@Nullable List<T> list) {
        refresh.stopRefresh();
        refresh.setCanLoadMore(!(list == null || list.isEmpty()));
        adapter = recyclerView.getAdapter();
        ((MoreLoadable<T>) adapter).addMore(list);
    }

    public abstract RecyclerView.Adapter initAdapter(List<T> list);

    @Override
    public void setTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        toolbar.setTitle(titleId);
    }

    public void setEmptyView(@DrawableRes int resId, String message, String description) {
        emptyImage.setImageResource(resId);
        emptyMessage.setText(message);
        if (!TextUtils.isEmpty(description)) {
            emptyDescription.setVisibility(View.VISIBLE);
            emptyDescription.setText(description);
        } else {
            emptyDescription.setVisibility(View.GONE);
        }

    }

    public void setEmptyView(@DrawableRes int resId, @StringRes int messageId, @StringRes int descriptionId) {
        emptyImage.setImageResource(resId);
        emptyMessage.setText(messageId);
        emptyDescription.setText(descriptionId);
    }

    public void setEmptyView(@LayoutRes int resId) {
        containerView.removeView(emptyContent);
        emptyContent = getLayoutInflater().inflate(resId, containerView, false);
        containerView.addView(emptyContent);
    }

    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        //empty
    }

    @Override
    protected void onDestroy() {
        if (refresh != null) {
            refresh.stopRefresh();
            refresh.destory();
        }
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        /* no-op */
    }

    public void showEmpty() {
        recyclerView.setVisibility(View.GONE);
        emptyContent.setVisibility(View.VISIBLE);
    }

    public void setMoreLoadable(boolean moreLoadable) {
        this.moreLoadable = moreLoadable;
        refresh.setCanLoadMore(moreLoadable);
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }
}
