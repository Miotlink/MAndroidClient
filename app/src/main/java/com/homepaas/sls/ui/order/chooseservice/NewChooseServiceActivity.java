package com.homepaas.sls.ui.order.chooseservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.mvp.presenter.ServiceListPresenter;
import com.homepaas.sls.mvp.view.ServiceListView;
import com.homepaas.sls.ui.adapter.NewServiceTypeAdapter;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.ConfirmOrderActivity;
import com.homepaas.sls.ui.search.adapter.NewAutoCompleteAdapter;
import com.homepaas.sls.ui.widget.ParallelLinesDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class NewChooseServiceActivity extends CommonBaseActivity implements ServiceListView {
    private static final int REQUEST_CODE_SEARCH = 0x10;
    @Bind(R.id.father_rl)
    RelativeLayout fatherRl;
    @Bind(R.id.topPanel)
    LinearLayout topPanel;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.search_text)
    AutoCompleteTextView mSearchTextView;
    @Bind(R.id.detele)
    ImageView detele;
    @Bind(R.id.search_edit_ll)
    LinearLayout searchEditLl;
    @Bind(R.id.cancel_search)
    TextView cancelSearch;
    @Bind(R.id.service_type)
    RelativeLayout serviceType;
    @Bind(R.id.service_list)
    RecyclerView mServiceList;


    private NewServiceTypeAdapter adapter;
    private ServiceListListener listener;
    private List<String> serviceList;
    private NewAutoCompleteAdapter autoCompleteAdapter;
    private boolean isFirst = false;
    private List<ServiceTypeEx> serviceTypeExListAll;

    @Inject
    ServiceListPresenter presenter;

    private boolean isFirstOrder = false;

    public void setListener(ServiceListListener listener) {
        this.listener = listener;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_service;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        isFirstOrder = getIntent().getBooleanExtra("isFirstOrder",false);
        adapter = new NewServiceTypeAdapter(this);
        mServiceList.addItemDecoration(new ParallelLinesDecoration(getContext()));
        mServiceList.setAdapter(adapter);
        this.presenter.setView(this);
        this.presenter.loadServiceList();

        mSearchTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    show();
                    if (TextUtils.isEmpty(mSearchTextView.getText().toString())) {
                        mSearchTextView.showDropDown();
                    }
                } else {
                }

            }
        });

        mSearchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isFirst) {
                    if (serviceList==null||serviceList.isEmpty()) {
                        serviceList = transformToItemStringList(serviceTypeExList);
                    }
                    autoCompleteAdapter.setList(serviceList, true);
                    isFirst = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSearchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // XXX: 2016/5/11 0011
                if (position == 0 && parent.getAdapter().getItem(0).toString().equals("没有相应服务，请换个关键词试试")) {
                    mSearchTextView.setText("");
                    return;
                }
                backOneClickOrder(parent.getAdapter().getItem(position).toString());
            }
        });


        mSearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“完成”键*/
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.destroy();

    }



    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void render(List<ServiceTypeEx> serviceTypeExList) {
        serviceTypeExListAll = serviceTypeExList;
        adapter.setServiceTypeExList(serviceTypeExList);
        serviceList = transformToItemStringList(serviceTypeExList);
        autoCompleteAdapter = new NewAutoCompleteAdapter();
        mSearchTextView.setAdapter(autoCompleteAdapter);
        mSearchTextView.setDropDownWidth(topPanel.getWidth());
        mSearchTextView.showDropDown();
    }

    private List<ServiceTypeEx> serviceTypeExList;
    private List<ServiceTypeEx> serviceTypeExListFourch;

    public List<String> transformToItemStringList(List<ServiceTypeEx> list) {
        serviceTypeExListFourch = new ArrayList<>();
        serviceTypeExList = new ArrayList<>();
        if(list!=null) {
            for (ServiceTypeEx serviceTypeEx : list) {
                if (serviceTypeEx.getChildren() != null &&
                        !serviceTypeEx.getChildren().isEmpty()) {
                    serviceTypeExList.add(serviceTypeEx);
                }
            }
        }
        for (ServiceTypeEx serviceTypeExSecond : serviceTypeExList) {
            for (ServiceTypeEx serviceTypeExListThird : serviceTypeExSecond.getChildren()) {
                if (serviceTypeExListThird.getChildren() != null &&
                        !serviceTypeExListThird.getChildren().isEmpty()) {
                    serviceTypeExListFourch.addAll(serviceTypeExListThird.getChildren());
                } else {
                    serviceTypeExListFourch.add(serviceTypeExListThird);
                }
            }
        }
        List<String> serciceList = new ArrayList<>();
        for (ServiceTypeEx service : serviceTypeExListFourch) {
            serciceList.add(service.getTypeName());
        }
        return serciceList;
    }


    /**
     * 返回一键下单页面
     */

    private void backOneClickOrder(String typeName) {
        for (ServiceTypeEx serviceTypeEx : serviceTypeExListFourch) {
            if (serviceTypeEx.getTypeName().equals(typeName)) {
                if (serviceTypeEx.getChildren() != null &&
                        !serviceTypeEx.getChildren().isEmpty()) {
                    SelectServiceItemFragment.show(this, serviceTypeEx);
                } else {

                        EventBus.getDefault().post(serviceTypeEx);


                }
            }
        }
    }

    private void jumpToConfirmOrder(ServiceTypeEx serviceTypeEx){
        Intent intent = new Intent(NewChooseServiceActivity.this, ConfirmOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFirstOrder",isFirstOrder);
        bundle.putSerializable("serviceTypeEx",serviceTypeEx);
        intent.putExtras(bundle);
        startActivity(intent);
        ActivityCompat.finishAfterTransition(this);
    }

    @OnClick({R.id.back, R.id.search_text, R.id.cancel_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_text:
                show();
                if (TextUtils.isEmpty(mSearchTextView.getText().toString())) {
                    mSearchTextView.showDropDown();
                }
                break;
            case R.id.cancel_search:
                backUp();
                break;
        }
    }


    private void show() {
        back.setVisibility(View.GONE);
        serviceType.setVisibility(View.GONE);
        cancelSearch.setVisibility(View.VISIBLE);
        fatherRl.setBackgroundColor(Color.parseColor("#FFFFFF"));
        isFirst = false;
    }

    private void backUp() {
        back.setVisibility(View.VISIBLE);
        serviceType.setVisibility(View.VISIBLE);
        cancelSearch.setVisibility(View.GONE);
        fatherRl.setBackgroundColor(Color.parseColor("#e2e1e1"));
        mSearchTextView.setText("");
        isFirst = false;
        autoCompleteAdapter.setList(null, false);
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }




    public interface ServiceListListener {
        void select(final ServiceTypeEx model);
    }

    /**
     * EventBus 直接监听事件
     *
     * @param serviceTypeEx 传递的数据结构
     */
    @Subscribe
    public void onEventMainThread(ServiceTypeEx serviceTypeEx) {
        if (listener != null) {
            listener.select(serviceTypeEx);
        }
        if (isFirstOrder){
            jumpToConfirmOrder(serviceTypeEx);
        }else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("service", serviceTypeEx);
            setResult(Activity.RESULT_OK, resultIntent);
            ActivityCompat.finishAfterTransition(this);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


}
