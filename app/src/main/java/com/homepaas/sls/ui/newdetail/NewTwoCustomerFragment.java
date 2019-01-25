package com.homepaas.sls.ui.newdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMerchantWorkerComponet;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.presenter.newdetail.MerchantWorkerEvaluationsPresenter;
import com.homepaas.sls.mvp.view.newdetail.MerchantWorkerEvaluationsView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.newdetail.adapter.NewCustomerReviewAdapter;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.hyphenate.helpdesk.easeui.ui.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * A simple {@link BaseFragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link NewTwoCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTwoCustomerFragment extends CommonBaseFragment implements MerchantWorkerEvaluationsView, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int ADD_COMMENT = 1;
    private static final int CODE_LOGIN = 2;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.empty_content)
    TextView emptyContent;
    @Bind(R.id.empty_view)
    FrameLayout emptyView;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.container)
    CoordinatorLayout container;
    @Inject
    protected Navigator mNavigator;
    @Inject
    MerchantWorkerEvaluationsPresenter merchantWorkerEvaluationsPresenter;

    // TODO: Rename and change types of parameters
    private String type;
    private String mId;
 private NewCustomerReviewAdapter adapter;

    public NewTwoCustomerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewTwoCustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewTwoCustomerFragment newInstance(String param1, String param2) {
        NewTwoCustomerFragment fragment = new NewTwoCustomerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM1);
            mId = getArguments().getString(ARG_PARAM2);
        }
        merchantWorkerEvaluationsPresenter.setMerchantWorkerEvaluationsView(this);
    }
    @Override
    protected void initializeInjector() {
        DaggerMerchantWorkerComponet.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }
    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_two_customer;
    }

    @Override
    protected void initView() {
        refreshLayout.setOnRefreshListener(this);
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
            merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
        else
            merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
        adapter = new NewCustomerReviewAdapter();
        recyclerView.addItemDecoration(new SimpleItemDecoration(getContext(),SimpleItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                refreshLayout.setEnabled(topRowVerticalPosition >= 0);//当recyclerview滑倒顶部再触发swiperefreshlayout
                lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                currentScrollState = newState;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
                    if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                        merchantWorkerEvaluationsPresenter.addBusinessMoreEvaluations();
                    else
                        merchantWorkerEvaluationsPresenter.addWorkerMoreEvaluations();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }


    @Override
    public void render(List<Evaluation> evaluations) {
        refreshLayout.setRefreshing(false);
        if (evaluations != null && !evaluations.isEmpty()){
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            adapter.refresh(evaluations);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

    }
//    @OnClick(R.id.fab)
    public void comment(){
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
            mNavigator.addComment(this, ADD_COMMENT, null, mId, Constant.EVALUATION_TYPE_BUSINESS);
        else
            mNavigator.addComment(this, ADD_COMMENT, null, mId, Constant.EVALUATION_TYPE_WORKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case ADD_COMMENT:
                    if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                        merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
                    else
                        merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
                    break;
                case CODE_LOGIN:
                    if (data != null && data.getBooleanExtra("Status",false) ) {
                        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                            merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
                        else
                            merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
                    }
                    break;
                default:
                    break;
            }
        }
//        if (requestCode == ADD_COMMENT && resultCode == Activity.RESULT_OK) {
//            if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
//                merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
//            else
//                merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
//        }
    }
    @Override
    public void renderMore(List<Evaluation> evaluations) {
        if (evaluations != null && !evaluations.isEmpty())
            adapter.addMore(evaluations);
        else
            showMessage("已经到底了，没有更多了哦~~");
    }
    @Override
    public void showLogin() {
        mNavigator.login(NewTwoCustomerFragment.this,CODE_LOGIN);

//        LoginDialogFragment.show(getActivity(), new LoginDialogFragment.OnLoginListener() {
//            @Override
//            public void onLogin() {
//                if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
//                    merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
//                else
//                    merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
//            }
//        });
    }

    @Override
    public void onRefresh() {
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
            merchantWorkerEvaluationsPresenter.getBusinessEvaluations(mId);
        else
            merchantWorkerEvaluationsPresenter.getWorkerEvaluations(mId);
    }
}
