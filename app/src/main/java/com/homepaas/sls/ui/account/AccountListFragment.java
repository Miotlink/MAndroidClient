package com.homepaas.sls.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerAccountComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.AccountListEntity;
import com.homepaas.sls.mvp.presenter.account.AccountListPresenter;
import com.homepaas.sls.mvp.view.account.AccountListView;
import com.homepaas.sls.ui.account.adapter.AccountListAdapter;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/28.
 */

public class AccountListFragment extends CommonBaseFragment implements AccountListView, AccountListAdapter.OnItemOnClickListener {

    private static final int OPEN_CHOOSE_TYPE = 200;
    public static final String CHOOSE_TYPE = "choose_type";
    public static final String CHOOSE_TYPE_ALL = "0";
    public static final String CHOOSE_TYPE_INCOME = "1";
    public static final String CHOOSE_TYPE_EXPEND = "2";
    public static final String GO_BACK = "3";

    @Bind(R.id.account_title_rl)
    RelativeLayout accountTitleRl;
    @Bind(R.id.back_ll)
    LinearLayout backLl;
    @Bind(R.id.title_bar_arrow)
    ImageView titleBarArrow;
    @Bind(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.empty_view)
    NestedScrollView emptyView;

    //0 全部 1 收入 2 支出
    private String isMinus = "0";

    @Inject
    AccountListPresenter mPresenter;
    private AccountListAdapter mAccountListAdapter;


    public static AccountListFragment newInstance() {
        AccountListFragment fragment = new AccountListFragment();
        return fragment;
    }

    public AccountListFragment() {
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPresenter.getSettleList(isMinus);
        }

        @Override
        public void onLoadMore() {
            mPresenter.getMoreSettlementList(isMinus);
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_list;
    }

    @Override
    protected void initView() {
        isMinus = "0";
        mAccountListAdapter = new AccountListAdapter();
        mAccountListAdapter.setOnItemOnClickListener(this);
        recyclerView.addItemDecoration(new SimpleItemDecoration(getActivity(), SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(mAccountListAdapter);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        mPresenter.setAccountListView(this);
        mPresenter.getSettleList(isMinus);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        titleBarArrow.setRotation(0);
    }

    @OnClick({R.id.back_ll, R.id.account_title_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                getActivity().onBackPressed();
                break;
            case R.id.account_title_rl:
                titleBarArrow.setRotation(180);
                Intent intent = new Intent(getActivity(), ChooseAccountDetailsListActivity.class);
                intent.putExtra(CHOOSE_TYPE, isMinus);
                startActivityForResult(intent, OPEN_CHOOSE_TYPE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_CHOOSE_TYPE && resultCode == Activity.RESULT_OK) {
            String type = data.getStringExtra(CHOOSE_TYPE);
            switch (type) {
                case "0":
                    isMinus = "0";
                    mPresenter.getSettleList(isMinus);
                    break;
                case "1":
                    isMinus = "1";
                    mPresenter.getSettleList(isMinus);
                    break;
                case "2":
                    isMinus = "2";
                    mPresenter.getSettleList(isMinus);
                    break;
                case "3":
                    getActivity().finish();
                    break;
                default:

            }
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerAccountComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        if (refreshLayout != null) {
            refreshLayout.stopRefresh();
            refreshLayout.destory();
        }
        super.onDestroy();
        mPresenter.destroy();
    }


    @Override
    public void renderSettlementList(AccountListEntity accountListEntity) {
        refreshLayout.stopRefresh();
        refreshLayout.setCanLoadMore(true);
        if (accountListEntity.getList() == null || accountListEntity.getList().isEmpty() || accountListEntity.getList().size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mAccountListAdapter.setDetails(accountListEntity.getList());
        }
    }

    @Override
    public void renderSettlenmentMoreList(AccountListEntity accountListEntity) {
        refreshLayout.stopRefresh();
        if (accountListEntity.getList() != null) {
            refreshLayout.setCanLoadMore(!accountListEntity.getList().isEmpty());
        } else {
            refreshLayout.setCanLoadMore(false);
        }
        mAccountListAdapter.addMore(accountListEntity.getList());
    }


    @Override
    public void onItemClick(AccountListEntity.ClientSettlementDetailResponse settlementDetail) {
        AccountDetailsActivity.start(getActivity(), settlementDetail.getId());
    }
}
