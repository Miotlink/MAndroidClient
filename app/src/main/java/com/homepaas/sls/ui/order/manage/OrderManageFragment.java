package com.homepaas.sls.ui.order.manage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.OrderListPopEntity;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.di.component.DaggerMainComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.event.ConfirmCompletedEvent;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.mvp.presenter.MessagePresenter;
import com.homepaas.sls.mvp.presenter.order.OrderPresenter;
import com.homepaas.sls.mvp.view.UnReadMessageCountView;
import com.homepaas.sls.mvp.view.order.OrderActionView;
import com.homepaas.sls.ui.bottomsheet.ConfirmCompletedBottomSheetDialogFragment;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.order.directOrder.EvaluationOrderActivity;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OrderManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderManageFragment extends CommonBaseFragment implements UnReadMessageCountView, OrderActionView {


    @Bind(R.id.tv_history_order)
    TextView tvHistoryOrder;
    @Bind(R.id.unreadIcon)
    View unreadIcon;
    @Bind(R.id.message)
    FrameLayout message;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.indicator)
    TabLayout indicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private boolean isShow = true;
    private static final int REQUEST_LOGIN = 1;
    private static final int REQUEST_MESSAGE = 2;

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    private OrderManageAdapter adapter;
    @Inject
    MessagePresenter messagePresenter;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    OrderPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("TAG", "OrderManageFragment:onCreate()");
    }

    public OrderManageFragment() {
    }

    public static OrderManageFragment newInstance() {
        OrderManageFragment fragment = new OrderManageFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        LogUtils.i("OrderManageFragment:initView");
        messagePresenter.setUnReadMessageCountView(this);
        presenter.setOrderActionView(this);

        EventBus.getDefault().register(this);
    }


    @Override
    protected void initData() {
        viewpager.setOffscreenPageLimit(4);//必须加上不然会引起子fragment加载混乱
        fragmentList.add(OrderCurrentFragment.newInstance());
        fragmentList.add(OrderHistoryFragment.newInstance());
//        fragmentList.add(OrderToPayFragment.newInstance());
//        fragmentList.add(OrderToConfirmFragment.newInstance());
//        fragmentList.add(OrderToEvaluateFragment.newInstance());
        titleList.add("当前订单");
        titleList.add("历史订单");

//        titleList.add("全部");
//        titleList.add("待接单");
//        titleList.add("待付款");
//        titleList.add("待确认");
//        titleList.add("待评价");
        adapter = new OrderManageAdapter(getChildFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        indicator.removeAllTabs();
        indicator.setupWithViewPager(viewpager);
    }

    @Subscribe
    public void onEvent(EventPersonalInfo event) {
        if (event.isLogin())
            messagePresenter.getUnreadMessageCount();
    }

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        if (PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE)) {
            messagePresenter.getUnreadMessageCount();
        }
    }


    /**
     * 获取是否弹订单框接口
     */
    public void getOrderListPop() {
        if (PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE))
            messagePresenter.getOrderListPop();
    }

    private String popDescription;
    @Override
    public void initOrderListPop(OrderListPopEntity data) {
        if (data == null) {
            return;
        }
        if (TextUtils.equals("1", data.getPopType())) {//1：弹确认服务完成页
            //确认完成弹框
            popDescription=data.getPopDescription();
            ConfirmCompletedBottomSheetDialogFragment fragment =
                    ConfirmCompletedBottomSheetDialogFragment.newInstance(ConfirmCompletedEvent.ORDER_MAINACTIVITY, popDescription, data.getOrderId());
            fragment.setConfirmCompletedListener(new ConfirmCompletedBottomSheetDialogFragment.onConfirmCompletedListener() {
                @Override
                public void onConfirmCompleted(String flag, String orderID, String orderCode) {
                    presenter.confirmOrder(new ConfirmCompletedEvent(flag, orderID, orderCode));
                }
            });
            fragment.setOrderCode(data.getOrderCode());
            fragment.show(getActivity().getSupportFragmentManager(), ConfirmCompletedBottomSheetDialogFragment.class.getSimpleName());
        } else if (TextUtils.equals("2", data.getPopType())) {//2：弹评价页
            //服务ID暂时咩用到
            EvaluationOrderActivity.start(getActivity(), "", data.getOrderCode(),data.getPopDescription());
            getActivity().overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);
        }
    }

//    //确认完成接口调用
//    @Subscribe
//    public void confrimCompleted(ConfirmCompletedEvent completedEvent) {
//        if (completedEvent.getFlag().equals(ConfirmCompletedEvent.ORDER_MAINACTIVITY)) {
//            presenter.confirmOrder(completedEvent);
//        }
//    }

    /**
     * 订单确认完成后的回调进入评价
     *
     * @param position
     */
    @Override
    public void onOrderConfirm(String orderId, int position) {
        EvaluationOrderActivity.start(getActivity(), "", orderId,popDescription);
        getActivity().overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);

    }

    @Override
    protected void initializeInjector() {
        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_LOGIN:
                    if (data != null && data.getBooleanExtra("Status", false))
                        messagePresenter.getUnreadMessageCount();
                    break;
                case REQUEST_MESSAGE:
                    if (data != null && data.getBooleanExtra("Status", false))
                        mNavigator.viewMessage(getActivity());
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void showError(Throwable e) {
        if (!(e instanceof AuthException)) {
            super.showError(e);
        } else {
            unreadIcon.setVisibility(View.INVISIBLE);
            if (isShow) {
                mNavigator.login(OrderManageFragment.this, REQUEST_LOGIN);
                isShow = false;
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.message)
    void checkMessage() {
        if (View.VISIBLE == unreadIcon.getVisibility()) {
            unreadIcon.setVisibility(View.GONE);
        }
        try {
            if (!TextUtils.isEmpty(personalInfoPDataSource.getTelphone()))
                mNavigator.viewMessage(getActivity());
            else
                mNavigator.login(OrderManageFragment.this, REQUEST_MESSAGE);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderUnreadMsgCount(boolean hasUnread) {
        unreadIcon.setVisibility(hasUnread ? View.VISIBLE : View.INVISIBLE);
    }


    @OnClick(R.id.tv_history_order)
    public void onViewClicked() {
        //历史订单 展示不做
//        if (PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE)) {
//            历史订单
//            mNavigator.startHistoryActivity(mContext);
//        } else {
//            mNavigator.login(OrderManageFragment.this, REQUEST_LOGIN);
//        }

        //增加服务数量
//        mNavigator.addServiceNum(mContext, "12323");
        //确认完成弹框
//        ConfirmCompletedBottomSheetDialogFragment fragment = ConfirmCompletedBottomSheetDialogFragment.newInstance(, "132546");
//        fragment.show(getActivity().getSupportFragmentManager(), ConfirmCompletedBottomSheetDialogFragment.class.getSimpleName());
    }

    @Override
    public void onOrderCancel(String msg, int position) {

    }

    @Override
    public void onOrderDelete(String msg, int position) {

    }


}
