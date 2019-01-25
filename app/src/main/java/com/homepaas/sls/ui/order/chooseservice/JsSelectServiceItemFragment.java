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
import com.homepaas.sls.di.component.DaggerLoginComponent;
import com.homepaas.sls.jsinterface.entity.DirectOrderCommand;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.adapter.JsServiceItemAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/3/10.
 */

public class JsSelectServiceItemFragment extends DialogFragment implements JsServiceItemAdapter.OnItemOnClickListener {
    @Bind(R.id.service_list)
    RecyclerView recyclerView;
    @Bind(R.id.type_name)
    TextView typeName;


    private DirectOrderCommand command;
    private JsServiceItemAdapter adapter;

    public JsSelectServiceItemFragment() {
        // Required empty public constructor
    }

    public static JsSelectServiceItemFragment show(FragmentActivity activity, DirectOrderCommand command) {
        JsSelectServiceItemFragment fragment = new JsSelectServiceItemFragment();
        fragment.setDirectOrderCommand(command);
        fragment.show(activity.getSupportFragmentManager(), null);
        return fragment;
    }

    public void setDirectOrderCommand(DirectOrderCommand command) {
        this.command = command;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_service_item, container, false);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        typeName.setText(command.getTypeName());
        adapter = new JsServiceItemAdapter();
        adapter.setOnItemOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.setDirectOrderCommandList(command.getChildren());
    }

    @Override
    public void onItemClick(DirectOrderCommand command) {
        EventBus.getDefault().post(command);
        dismiss();
    }
}
