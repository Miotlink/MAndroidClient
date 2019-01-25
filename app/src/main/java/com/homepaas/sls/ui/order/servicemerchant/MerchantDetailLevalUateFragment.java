package com.homepaas.sls.ui.order.servicemerchant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.data.entity.BusinessOrderServiceListEntity;
import com.homepaas.sls.data.entity.BusinessSecServiceEntity;
import com.homepaas.sls.data.entity.BusinessServiceListEntity;
import com.homepaas.sls.domain.entity.BusinessCommentListOutput;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.newmvp.contract.MerchantContract;
import com.homepaas.sls.newmvp.presenter.MerchantPresenter;
import com.homepaas.sls.ui.common.fragment.CommonMvpLazyLoadFragment;
import com.homepaas.sls.ui.order.adapter.NewMerchantLevaluateAdapter;
import com.homepaas.sls.ui.widget.networkerrorview.CommonNetWorkErrorViewReplacer;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mhy on 2017/12/27.
 * 服务详情评价tab界面
 */

public class MerchantDetailLevalUateFragment extends CommonMvpLazyLoadFragment<MerchantContract.Presenter> implements HeaderViewLayout.OnRefreshListener, MerchantContract.View {
    @Bind(R.id.rl_levaluate_parent)
    LinearLayout relativeLayout;
    @Bind(R.id.listView)
    ListView listView;

    private View footView;
    private CommonNetWorkErrorViewReplacer commonNetWorkErrorViewReplacer;
    private NewMerchantLevaluateAdapter merchantAdapter;

    private int currentPage = 1;
    private int pageSize = 10;
    private String IsEnablePaging = "1";
    private String userType;
    private String merchantOrWorkerId;

    public static MerchantDetailLevalUateFragment newInstance(String userType, String merchantOrWorkerId) {

        Bundle args = new Bundle();
        args.putString("userType", userType);
        args.putString("merchantOrWorkerId", merchantOrWorkerId);
        MerchantDetailLevalUateFragment fragment = new MerchantDetailLevalUateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchant_detail_levaluate;
    }

    @Override
    protected void initView() {
        //设置适配器
        merchantAdapter = new NewMerchantLevaluateAdapter(mContext, null);
        listView.setAdapter(merchantAdapter);
        footView = LayoutInflater.from(mContext).inflate(R.layout.load_view, null);
        Button button = footView.findViewById(R.id.btn_load_more);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadList();
            }
        });
        commonNetWorkErrorViewReplacer = new CommonNetWorkErrorViewReplacer(mContext, relativeLayout, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                updateList();
            }
        });
        Bundle arguments = getArguments();
        userType = arguments.getString("userType");
        merchantOrWorkerId = arguments.getString("merchantOrWorkerId");
    }

    @Override
    protected void initData() {
        updateList();
    }

    @Override
    protected MerchantContract.Presenter getPresenter() {
        return new MerchantPresenter();
    }

    public void updateList() {
        currentPage = 1;
        mPresenter.getBusinessComment(userType, merchantOrWorkerId, IsEnablePaging + "", currentPage + "", pageSize + "");
    }

    public void loadList() {
        currentPage++;
        mPresenter.getBusinessComment(userType, merchantOrWorkerId, IsEnablePaging + "", currentPage + "", pageSize + "");
    }

    @Override
    public void onRefresh() {
        updateList();
    }

    @Override
    public void onLoadMore() {
        loadList();
    }


    @Override
    public void initBusinessCommentList(BusinessCommentListOutput businessCommentListOutput) {
        if ((businessCommentListOutput == null || businessCommentListOutput.getList() == null || businessCommentListOutput.getList().size() == 0) && (merchantAdapter.getData() == null || merchantAdapter.getData().size() == 0)) {
            commonNetWorkErrorViewReplacer.showNoEvaluateEmptyView();
            return;
        }
        commonNetWorkErrorViewReplacer.showOriginalLayout();
        List<BusinessCommentListOutput.ListBean> chooseWorkerOrMerchantInfos = businessCommentListOutput.getList();
        if (currentPage == 1)//刷新
        {
            merchantAdapter.setData(chooseWorkerOrMerchantInfos);
        } else {//加载
            merchantAdapter.addData(chooseWorkerOrMerchantInfos);
        }
        //是否可以加载
        if (businessCommentListOutput.getCount() > merchantAdapter.getData().size()) {
            listView.addFooterView(footView);
        } else {
            listView.removeFooterView(footView);
        }
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        if (merchantAdapter.getData() == null || merchantAdapter.getData().size() == 0) {
            commonNetWorkErrorViewReplacer.showErrorLayout();
        }
    }

    public void onDestroyView() {
        if (commonNetWorkErrorViewReplacer != null)
            commonNetWorkErrorViewReplacer.showOriginalLayout();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onModeChanged(int mode) {

    }

    @Override
    public void initBusinessOrderServiceList(BusinessOrderServiceListEntity businessOrderServiceListEntity) {

    }

    @Override
    public void initMerchantOrWorkerInfo(BusinessExInfoOutput businessExInfoOutput) {

    }

    @Override
    public void initBusinessSecService(BusinessSecServiceEntity businessSecServiceEntity) {

    }

    @Override
    public void initBusinessServiceList(BusinessServiceListEntity businessServiceListEntity) {

    }
}
