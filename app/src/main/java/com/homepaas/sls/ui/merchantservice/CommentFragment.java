package com.homepaas.sls.ui.merchantservice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends CommonBaseFragment {


    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    public static CommentFragment newInstance() {

        Bundle args = new Bundle();

        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        List<Object> datas= new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new Object());
        }
        CommentListAdapter adapter = new CommentListAdapter(datas,getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
