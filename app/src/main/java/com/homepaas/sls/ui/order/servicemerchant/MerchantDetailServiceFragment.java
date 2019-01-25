package com.homepaas.sls.ui.order.servicemerchant;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import com.homepaas.sls.ui.login.FastLoginActivity;
import com.homepaas.sls.ui.order.adapter.MerchantDetailServiceAdapter;
import com.homepaas.sls.ui.order.adapter.MerchantDetailsFilterAdapter;
import com.homepaas.sls.ui.order.adapter.MerchantDetailsFilterTagAdapter;
import com.homepaas.sls.ui.order.directOrder.CommonPlaceOrderActivity;
import com.homepaas.sls.ui.widget.CustomLinearLayoutManager;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.networkerrorview.CommonNetWorkErrorViewReplacer;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mhy on 2017/12/27.
 * 服务详情服务tab界面
 */

public class MerchantDetailServiceFragment extends CommonMvpLazyLoadFragment<MerchantContract.Presenter> implements MerchantContract.View, MerchantDetailServiceAdapter.onMerchantClickListener, BaseRecyclerAdapter.OnItemClickListener, HeaderViewLayout.OnRefreshListener {

    @Bind(R.id.rl_top_filter_view)
    RecyclerView rlTopFilterView;
    @Bind(R.id.rl_filter)
    RelativeLayout rlFilter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.rvServiceFilter)
    RecyclerView rvServiceFilter;
    @Bind(R.id.img_filter)
    ImageView imgFilter;
    @Bind(R.id.rl_filter_layout)
    RelativeLayout rlFilterLayout;
    @Bind(R.id.rl_top_filter_parent)
    RelativeLayout rlFilterLayoutParent;


    private int currentPage = 1;
    private int pageSize = 10;
    private int filtrateFlag = 0;
    private String orderType;
    private String secondLevel;
    private String IsEnablePaging = "1";
    private int currentFilterIndex = 0;//筛选列表当前选择的位置
    private boolean ppWindowShow = false;

    private CommonNetWorkErrorViewReplacer commonNetWorkErrorViewReplacer;
    private MerchantDetailServiceAdapter merchantServiceAdapter;
    private MerchantDetailsFilterAdapter merchantDetailsFilterAdapter;
    private MerchantDetailsFilterTagAdapter bottomMerchantDetailsFilterAdapter;
    private BusinessServiceListEntity businessServiceListEntity;
    private ObjectAnimator anim = null;
    private String userType;//用户类型，2：工人，3：商户
    private String merchantOrWorkerId;//必填，商户或者普通工人的Id//查询信息的id
    private String userId;//下单的id
    private double Latitude;
    private double Longitude;
    private boolean isOrderActivity;//进入该节目入口
    private String merchantOrWorkerName;//商户或工人的名称
    private String ServiceId = "0";//必填，二级服务的Id。0：全部 详情见 BusinessEx/GetBusinessSecService 接口

    public static MerchantDetailServiceFragment newInstance(String userType, String merchantOrWorkerId, boolean isOrderActivity, String orderType, String secondLevel, double Latitude, double Longitude) {
        Bundle args = new Bundle();
        args.putString("userType", userType);
        args.putString("merchantOrWorkerId", merchantOrWorkerId);
        args.putString(StaticData.ORDER_TYPE, orderType);
        args.putString(StaticData.SECOND_LEVEL, secondLevel);
        args.putBoolean(StaticData.IS_ORDER_ACTIVITY, isOrderActivity);
        args.putDouble(StaticData.LATITUDE, Latitude);
        args.putDouble(StaticData.LONGITUDE, Longitude);
        MerchantDetailServiceFragment fragment = new MerchantDetailServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchant_detail_service;
    }

    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        userType = arguments.getString("userType");
        merchantOrWorkerId = arguments.getString("merchantOrWorkerId");
        orderType = arguments.getString(StaticData.ORDER_TYPE);
        secondLevel = arguments.getString(StaticData.SECOND_LEVEL);
        isOrderActivity = arguments.getBoolean(StaticData.IS_ORDER_ACTIVITY);
        Latitude = arguments.getDouble(StaticData.LATITUDE);
        Longitude = arguments.getDouble(StaticData.LONGITUDE);
        merchantDetailsFilterAdapter = new MerchantDetailsFilterAdapter(mContext);
        bottomMerchantDetailsFilterAdapter = new MerchantDetailsFilterTagAdapter(mContext);

        CustomLinearLayoutManager customLinearLayoutManager = new CustomLinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        customLinearLayoutManager.setScrollEnabled(false);
        rlTopFilterView.setLayoutManager(customLinearLayoutManager);
        rlTopFilterView.setHasFixedSize(true);
        rlTopFilterView.setAdapter(merchantDetailsFilterAdapter);

        rvServiceFilter.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvServiceFilter.setHasFixedSize(true);
        rvServiceFilter.setAdapter(bottomMerchantDetailsFilterAdapter);

        merchantServiceAdapter = new MerchantDetailServiceAdapter(mContext);
        merchantServiceAdapter.setOnMerchantClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(merchantServiceAdapter);
        bottomMerchantDetailsFilterAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                if (currentFilterIndex == pos)
                    return;
                currentFilterIndex = pos;
                String name = bottomMerchantDetailsFilterAdapter.getData().get(pos).getName();
                filterGetList(itemView, pos, name);
            }
        });
        merchantDetailsFilterAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                if (currentFilterIndex == pos)
                    return;
                currentFilterIndex = pos;
                String name = merchantDetailsFilterAdapter.getData().get(pos).getName();
                filterGetList(itemView, pos, name);
            }
        });
        commonNetWorkErrorViewReplacer = new CommonNetWorkErrorViewReplacer(mContext, recyclerView, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                updateList();
            }
        });
    }

    public void filterGetList(View itemView, int pos, String name) {
        merchantDetailTagFilter(itemView, pos);
        merchantDetailFilter(itemView, pos);
        if (businessServiceListEntity == null) //服务列表数据为空通过网络获取，否则直接本地进行筛选对应的服务数据【后台将所有的服务类型数据通过一页进行返回，可以本地直接进行筛选】
        {
            updateList();
        } else {
            //通过本地数据筛选出对应父级数据
            List<BusinessServiceListEntity.ListBean> oldList = businessServiceListEntity.getList();
            if (pos == 0) {
                //全部
                merchantServiceAdapter.updateList(oldList);
                return;
            }
            List<BusinessServiceListEntity.ListBean> newList = new ArrayList<>();
            for (int i = 0; i < oldList.size(); i++) {
                BusinessServiceListEntity.ListBean listBean = oldList.get(i);
                if (listBean.getParentName().equals(name))
                    newList.add(listBean);
            }
            merchantServiceAdapter.updateList(newList);
        }
    }

    public void updateList() {
        currentPage = 1;
        mPresenter.getBusinessServiceList(ServiceId, userType, merchantOrWorkerId, Latitude, Longitude, IsEnablePaging + "", currentPage + "", pageSize + "");
    }

    public void loadList() {
        currentPage++;
        mPresenter.getBusinessServiceList(ServiceId, userType, merchantOrWorkerId, Latitude, Longitude, IsEnablePaging + "", currentPage + "", pageSize + "");
    }

    public void merchantDetailTagFilter(View itemView, int pos) {
        List<BusinessSecServiceEntity.SecServicesBean> data = bottomMerchantDetailsFilterAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            BusinessSecServiceEntity.SecServicesBean secServicesBean = data.get(i);
            if (i == pos) {
                secServicesBean.setSelect(true);
                ServiceId = secServicesBean.getId();
            } else
                secServicesBean.setSelect(false);
        }
        bottomMerchantDetailsFilterAdapter.notifyDataSetChanged();
        if (ppWindowShow)
            closePPWindow();
    }

    public void merchantDetailFilter(View itemView, int pos) {
        List<BusinessSecServiceEntity.SecServicesBean> data = merchantDetailsFilterAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            BusinessSecServiceEntity.SecServicesBean secServicesBean = data.get(i);
            if (i == pos) {
                secServicesBean.setSelect(true);
                ServiceId = secServicesBean.getId();
            } else
                secServicesBean.setSelect(false);
        }
        merchantDetailsFilterAdapter.notifyDataSetChanged();
        if (ppWindowShow)
            closePPWindow();
    }


    @Override
    protected void initData() {
        //获取二级服务分类
        getBusinessSecService();
        updateList();
    }

    private void getBusinessSecService() {
        mPresenter.getBusinessSecService(userType, merchantOrWorkerId);
    }

    @Override
    protected MerchantContract.Presenter getPresenter() {
        return new MerchantPresenter();
    }


    private void showPPWindow() {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        anim = ObjectAnimator.ofFloat(imgFilter, "rotation", 0f, 180f);
        anim.setDuration(100).start();
        ppWindowShow = true;
        filtrateFlag++;
        rlFilterLayout.setVisibility(View.VISIBLE);
    }

    private void closePPWindow() {
        anim = ObjectAnimator.ofFloat(imgFilter, "rotation", 180f, 0f);
        anim.setDuration(100).start();
        ppWindowShow = false;
        filtrateFlag++;
        rlFilterLayout.setVisibility(View.GONE);
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
    public void onDestroyView() {
        if (anim != null) {
            anim.cancel();
        }
        if (commonNetWorkErrorViewReplacer != null)
            commonNetWorkErrorViewReplacer.showOriginalLayout();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View itemView, int pos) {

    }

    @Override
    public void initBusinessSecService(BusinessSecServiceEntity businessSecServiceEntity) {
        List<BusinessSecServiceEntity.SecServicesBean> secServices = businessSecServiceEntity.getSecServices();
        if (secServices != null && secServices.size() > 0) {
            BusinessSecServiceEntity.SecServicesBean secServicesBean = secServices.get(0);
            secServicesBean.setSelect(true);
        }
        merchantDetailsFilterAdapter.setData(secServices);
        bottomMerchantDetailsFilterAdapter.setData(secServices);
        rlFilterLayoutParent.setVisibility(View.VISIBLE);
    }


    @Override
    public void initBusinessServiceList(BusinessServiceListEntity businessServiceListEntity) {
        this.businessServiceListEntity = businessServiceListEntity;
        if ((businessServiceListEntity.getList() == null || businessServiceListEntity.getList() == null || businessServiceListEntity.getList().size() == 0) && (merchantServiceAdapter.getData() == null || merchantServiceAdapter.getData().size() == 0)) {
            commonNetWorkErrorViewReplacer.showEmptyView(R.string.empty_hint4);
            return;
        }
        commonNetWorkErrorViewReplacer.showOriginalLayout();
        List<BusinessServiceListEntity.ListBean> chooseWorkerOrMerchantInfos = businessServiceListEntity.getList();
        if (currentPage == 1)//刷新
        {
            merchantServiceAdapter.updateList(chooseWorkerOrMerchantInfos);
        } else {//加载
            merchantServiceAdapter.append(chooseWorkerOrMerchantInfos);
        }
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        if (merchantServiceAdapter.getData() == null || merchantServiceAdapter.getData().size() == 0) {
            commonNetWorkErrorViewReplacer.showErrorLayout();
        }
    }

    @OnClick({R.id.rl_filter, R.id.rl_filter_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_filter:
                //筛选菜单
                if (filtrateFlag % 2 == 0) {//打开筛选菜单
                    showPPWindow();
                } else {
                    closePPWindow();
                }
                break;
            case R.id.rl_filter_layout:
                closePPWindow();
                break;
        }
    }

    @Override
    public void onModeChanged(int mode) {

    }

    @Override
    public void initBusinessCommentList(BusinessCommentListOutput businessCommentListOutput) {

    }

    @Override
    public void initBusinessOrderServiceList(BusinessOrderServiceListEntity businessOrderServiceListEntity) {

    }

    @Override
    public void initMerchantOrWorkerInfo(BusinessExInfoOutput businessExInfoOutput) {

    }

    public void setMerchantOrWorkerNameAndUserId(String merchantOrWorkerName, String userId) {
        this.merchantOrWorkerName = merchantOrWorkerName;
        this.userId = userId;
    }

    @Override
    public void subscribe(String serviceId, String serviceName) {
        if (TextUtils.isEmpty(merchantOrWorkerName)) {
            showMessage("正在获取商户信息");
            return;
        }
        if (!PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE)) {
            startActivity(new Intent(mContext, FastLoginActivity.class));
            return;
        }
        if (isOrderActivity) {
            //返回更新预约的服务类型下单信息
            Activity activity = (Activity) mContext;
            activity.setResult(Activity.RESULT_OK, new Intent().putExtra(StaticData.MERCHANT_ID, userId)
                    .putExtra(StaticData.MERCHANT_NAME, merchantOrWorkerName).putExtra(StaticData.SERVICE_ID, serviceId)
                    .putExtra(StaticData.SECOND_LEVEL, secondLevel)
                    .putExtra(StaticData.PROVIDER_USER_TYPE, userType));
            activity.finish();
        } else {
            //预约进入下单页
            CommonPlaceOrderActivity.start(getActivity(), serviceId, serviceName, orderType, userId, merchantOrWorkerName, secondLevel, userType, "");
            getActivity().finish();
        }
    }
}
