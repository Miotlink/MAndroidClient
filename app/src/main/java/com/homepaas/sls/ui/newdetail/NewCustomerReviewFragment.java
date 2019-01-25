package com.homepaas.sls.ui.newdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMerchantWorkerComponet;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.presenter.newdetail.MerchantWorkerEvaluationsPresenter;
import com.homepaas.sls.mvp.view.newdetail.MerchantWorkerEvaluationsView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.newdetail.adapter.NewCustomerReviewAdapter;
import com.homepaas.sls.ui.common.fragment.BaseListFragment;
import com.homepaas.sls.ui.widget.ParallelLinesDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link NewCustomerReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewCustomerReviewFragment extends BaseListFragment<Evaluation> implements MerchantWorkerEvaluationsView, BaseListFragment.OnListIsEmpty {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "id";
    private static final int ADD_COMMENT = 1;
    @Bind(R.id.fab)
    FloatingActionButton fab;
//    @Bind(R.id.container)
//    FrameLayout container;
    @Inject
    protected Navigator mNavigator;
    private boolean isLoaded = false;
    // TODO: Rename and change types of parameters
    private String type;
    private String mId;
    @Inject
    MerchantWorkerEvaluationsPresenter merchantWorkerEvaluationsPresenter;

    public NewCustomerReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type Parameter 1.
     * @param mId Parameter 2.
     * @return A new instance of fragment NewCustomerReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewCustomerReviewFragment newInstance(String type, String mId) {
        NewCustomerReviewFragment fragment = new NewCustomerReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, type);
        args.putString(ARG_PARAM2, mId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_customer_review;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM1);
            mId = getArguments().getString(ARG_PARAM2);
        }
        setOnListIsEmpty(this);
       merchantWorkerEvaluationsPresenter.setMerchantWorkerEvaluationsView(this);

    }

    @Override
    protected void initializeInjector() {
        DaggerMerchantWorkerComponet.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_customer_review, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setMoreLoadable(true);
        setEmptyView(R.mipmap.client_v3_1_0_ic_homepage_no_evaluate, "暂无评价");
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onRefresh();
            isLoaded = true;
        }
//        load();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
            merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
        else
            merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<Evaluation> list) {
        NewCustomerReviewAdapter adapter = new NewCustomerReviewAdapter();
//        if (list != null){
            adapter.setList(list);
//        }
        return adapter;
    }
    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new ParallelLinesDecoration(getContext());
    }

    @Override
    public void onRefresh() {
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
            merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
        else
            merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);

    }

    @Override
    public void onLoadMore() {
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type)){
            merchantWorkerEvaluationsPresenter.addBusinessMoreEvaluations();
        } else {
            merchantWorkerEvaluationsPresenter.addWorkerMoreEvaluations();
        }

    }

    @OnClick(R.id.fab)
    public void comment(){
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
            mNavigator.addComment(this, ADD_COMMENT, null, mId, Constant.EVALUATION_TYPE_BUSINESS);
        else
            mNavigator.addComment(this, ADD_COMMENT, null, mId, Constant.EVALUATION_TYPE_WORKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case CODE_LOGIN:
                    if (data != null && data.getBooleanExtra("Status",false) ) {
                        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                            merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
                        else
                            merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
                    }
                    break;
                case ADD_COMMENT:
                    if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                        merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
                    else
                        merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
                    break;
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private static final int CODE_LOGIN = 2;

    @Override
    public void showLogin() {
        mNavigator.login(NewCustomerReviewFragment.this,CODE_LOGIN);
     /*   LoginDialogFragment.show(getActivity(), new LoginDialogFragment.OnLoginListener() {
            @Override
            public void onLogin() {
                if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                    merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
                else
                    merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
            }
        });*/
    }

    @Override
    public void listIsEmpty() {

    }
}
