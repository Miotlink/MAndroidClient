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
import android.view.Window;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.di.component.DaggerLoginComponent;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.ui.adapter.ServiceItemAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectServiceItemFragment extends DialogFragment {

    @Bind(R.id.service_list)
    RecyclerView recyclerView;
    @Bind(R.id.type_name)
    TextView typeName;


    private  ServiceTypeEx serviceTypeEx;
    private ServiceItemAdapter adapter;

    public SelectServiceItemFragment() {
        // Required empty public constructor
    }

    public static SelectServiceItemFragment show(FragmentActivity activity, ServiceTypeEx serviceTypeEx) {
        SelectServiceItemFragment fragment = new SelectServiceItemFragment();

        fragment.setServiceTypeEx(serviceTypeEx);
        fragment.show(activity.getSupportFragmentManager(), null);
        return fragment;
    }

    public void setServiceTypeEx(ServiceTypeEx serviceTypeEx) {
        this.serviceTypeEx = serviceTypeEx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        inject();
    }

    private void inject() {
        DaggerLoginComponent.builder()
                .applicationComponent(SimpleTinkerInApplicationLike.getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_service_item, container, false);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        typeName.setText(serviceTypeEx.getTypeName());
        adapter = new ServiceItemAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
//        adapter.setServiceTypeExList(serviceTypeExList);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.setServiceTypeExList(serviceTypeEx.getChildren());
    }
}
