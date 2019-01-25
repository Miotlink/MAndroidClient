package com.homepaas.sls.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.ui.detail.adapter.CustomerReviewAdapter;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工人、商户评价列表
 *
 * @author zhudongjie .
 */
public class CustomerReviewFragment extends Fragment {

    private static final String TAG = "CustomerReviewFragment";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    @Bind(R.id.service_content)
    View serviceContent;

    private CustomerReviewAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_reviews, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new CustomerReviewAdapter();
        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    public void setList(List<Evaluation> evaluations) {
        if (evaluations.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            serviceContent.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
             serviceContent.setVisibility(View.GONE);
            mAdapter.setList(evaluations);
        }
    }

    public void add(List<Evaluation> evaluations) {
        mAdapter.addMore(evaluations);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
