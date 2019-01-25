package com.homepaas.sls.ui.order.chooseservice;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerLoginComponent;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.ui.adapter.TitleServiceItemAdapter;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TitleServiceItemFragment extends DialogFragment implements TitleServiceItemAdapter.GoNextOnClickListener,TitleServiceItemAdapter.ItemOnclickListener {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;


    @Bind(R.id.go_back_fl)
    FrameLayout goBackFl;
    @Bind(R.id.cancel_fl)
    FrameLayout cancelFl;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.head_text)
    TextView headText;
    @Bind(R.id.cancel)
    ImageView cancel;




    private  ServiceTypeEx addServiceTypeEx;
    private TitleServiceItemAdapter mTitleServiceItemAdapter;
    private Map<Integer,ServiceTypeEx> map;
    private int lastPosition=0;


    public TitleServiceItemFragment() {
        // Required empty public constructor
    }

    public static TitleServiceItemFragment show(FragmentActivity activity, ServiceTypeEx serviceTypeEx) {
        TitleServiceItemFragment fragment = new TitleServiceItemFragment();
        fragment.setServiceTypeEx(serviceTypeEx);
        fragment.show(activity.getSupportFragmentManager(), null);
        return fragment;
    }

    public void setServiceTypeEx(ServiceTypeEx addServiceTypeEx) {
        this.addServiceTypeEx = addServiceTypeEx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        inject();
    }

    private void inject() {
        DaggerLoginComponent.builder()
                .applicationComponent(((CommonBaseActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title_service_item, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        map=new HashMap<Integer, ServiceTypeEx>() ;
        mTitleServiceItemAdapter = new TitleServiceItemAdapter();
        mTitleServiceItemAdapter.setGoNextOnClickListener(this);
        mTitleServiceItemAdapter.setItemOnclickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mTitleServiceItemAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        map.put(0,addServiceTypeEx);
        mTitleServiceItemAdapter.setServiceTypeExList(lastPosition,addServiceTypeEx.getChildren());
        headText.setText(addServiceTypeEx.getTypeName());
        goBackUse(lastPosition);
    }

    @Override
    public void goNextClick(int goNextPosition, ServiceTypeEx itemService) {
        this.lastPosition=goNextPosition;
        map.put(goNextPosition,itemService);
        mTitleServiceItemAdapter.setServiceTypeExList(lastPosition,itemService.getChildren());
        headText.setText(itemService.getTypeName());
        goBackUse(lastPosition);
    }

    /**
     * 后退键是否可用
     *
     */

    private void goBackUse(int position){
        if((position)==0){
            back.setVisibility(View.GONE);
            goBackFl.setEnabled(false);
        }else{
            back.setVisibility(View.VISIBLE);
            goBackFl.setEnabled(true);
        }
    }


    @Override
    public void itemClick(ServiceTypeEx itemService) {
        dismiss();
        EventBus.getDefault().post(itemService);
    }

    @OnClick({R.id.go_back_fl,R.id.cancel_fl})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.go_back_fl:
                lastPosition=lastPosition-1;
                addServiceTypeEx=map.get(lastPosition);
                mTitleServiceItemAdapter.setServiceTypeExList(lastPosition,addServiceTypeEx.getChildren());
                headText.setText(addServiceTypeEx.getTypeName());
                goBackUse(lastPosition);
                break;
            case R.id.cancel_fl:
                dismiss();
                break;
        }
    }






}
