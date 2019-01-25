package com.homepaas.sls.ui.order.directOrder;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.OrderCancelReasonEntity;
import com.homepaas.sls.mvp.presenter.order.OrderCancelReasonsPresenter;
import com.homepaas.sls.mvp.view.order.OrderCancelReasonsView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.adapter.OrderCancelReasonsAdapter;
import com.homepaas.sls.ui.widget.ReboundScrollView;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.util.StaticData;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/5/27.
 * 界面订单取消原因
 */

public class OrderCancelReasonsActivity extends CommonBaseActivity implements OrderCancelReasonsAdapter.OnItemClickListener, OrderCancelReasonsView {
    @Bind(R.id.close)
    TextView close;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_rel)
    RelativeLayout titleRel;
    @Bind(R.id.cancel_reasons_recyclerView)
    RecyclerView cancelReasonsRecyclerView;
    @Bind(R.id.other_reasons_edit)
    EditText otherReasonsEdit;
    @Bind(R.id.other_reasons_lin)
    LinearLayout otherReasonsLin;
    @Bind(R.id.scroll_view)
    ReboundScrollView scrollView;
    @Bind(R.id.btn_sumbit)
    Button btnSumbit;

    private OrderCancelReasonsAdapter orderCancelReasonsAdapter;
    private int lastSelectPosition = 0;
    private String serviceId;
    private String cancelReasonStr;
    private static final int SCROLL_TO = 99;
    @Inject
    OrderCancelReasonsPresenter orderCancelReasonsPresenter;

    public static void start(Context context, String serviceId) {
        Intent intent = new Intent(context, OrderCancelReasonsActivity.class);
        intent.putExtra(StaticData.SERVICE_ID, serviceId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_cancel_reasons;
    }


    @Override
    protected void initView() {
        serviceId = getIntent().getStringExtra(StaticData.SERVICE_ID);

        orderCancelReasonsAdapter = new OrderCancelReasonsAdapter();
        orderCancelReasonsAdapter.setOnItemClickListener(this);
        cancelReasonsRecyclerView.addItemDecoration(new SimpleItemDecoration(this, SimpleItemDecoration.VERTICAL_LIST));
        cancelReasonsRecyclerView.setAdapter(orderCancelReasonsAdapter);
        orderCancelReasonsPresenter.setOrderCancelReasonsView(this);
        orderCancelReasonsPresenter.getOrderCancelReasonList();
        otherReasonsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessageDelayed(SCROLL_TO, 500);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }

    /**
     * 点击原因
     *
     * @param selectPosition
     * @param s
     */
    @Override
    public void itemClickListener(int selectPosition, String s) {
        orderCancelReasonsAdapter.setSelectPositin(lastSelectPosition, selectPosition);
        cancelReasonStr = s;
        if (!TextUtils.isEmpty(s) && TextUtils.equals(s, "其他")) {
            otherReasonsLin.setVisibility(View.VISIBLE);
            addKeyboard();
            handler.sendEmptyMessageDelayed(SCROLL_TO, 500);
        } else {
            downKeyboard();
            otherReasonsLin.setVisibility(View.GONE);
        }
        lastSelectPosition = selectPosition;
    }

    /**
     * 打开软键盘
     */
    private void addKeyboard() {
        otherReasonsEdit.setFocusable(true);
        otherReasonsEdit.setFocusableInTouchMode(true);
        otherReasonsEdit.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        InputMethodManager imm = (InputMethodManager) OrderCancelReasonsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void downKeyboard() {
        InputMethodManager imm = (InputMethodManager) OrderCancelReasonsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(otherReasonsEdit.getWindowToken(), 0);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCROLL_TO:
                    scrollView.scrollTo(0, 1024);
                    break;
                default:
            }
        }
    };

    @OnClick({R.id.close, R.id.btn_sumbit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                overridePendingTransition(R.anim.anim_no, R.anim.anim_bottom_out);
                break;
            case R.id.btn_sumbit:
                submitReason();
                break;
            default:
        }
    }

    private void submitReason() {
        if (!TextUtils.isEmpty(cancelReasonStr)) {
            if (!TextUtils.equals(cancelReasonStr, "其他")) {
                orderCancelReasonsPresenter.orderCancel(serviceId, cancelReasonStr);
            } else {
                if (!TextUtils.isEmpty(otherReasonsEdit.getText().toString())) {
                    orderCancelReasonsPresenter.orderCancel(serviceId, otherReasonsEdit.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "请填写原因或者选择上面原因", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 所有取消原因
     */
    @Override
    public void renderCancelReasons(OrderCancelReasonEntity orderCancelReasonEntity) {
        if (orderCancelReasonEntity != null) {
            orderCancelReasonsAdapter.setList(orderCancelReasonEntity.getCancelReasonList());
        }
    }

    /**
     * 订单取消成功
     */
    @Override
    public void orderCancelSuccess() {
        finish();
        overridePendingTransition(R.anim.anim_no, R.anim.anim_bottom_out);
    }
}
