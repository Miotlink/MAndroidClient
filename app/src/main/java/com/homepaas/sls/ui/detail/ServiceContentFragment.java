package com.homepaas.sls.ui.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.ui.detail.adapter.ServiceContentAdapter;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceContentFragment extends Fragment {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.service_content)
    View serviceContent;
    private ServiceContentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_contents, container, false);
        ButterKnife.bind(this,view);
        adapter = new ServiceContentAdapter();
        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setList(List<ServiceContent> list) {
        if (list.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            serviceContent.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            serviceContent.setVisibility(View.GONE);
            adapter.setList(list);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
