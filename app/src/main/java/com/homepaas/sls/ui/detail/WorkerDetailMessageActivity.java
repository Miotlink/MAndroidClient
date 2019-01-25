package com.homepaas.sls.ui.detail;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerWorkerDetailComponent;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.presenter.servicedetail.WorkerMessagePresenter;
import com.homepaas.sls.mvp.view.servicedetail.WorkerMessageView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.detail.adapter.AuthenticateDetailAdapter;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/8.
 */
public class WorkerDetailMessageActivity extends CommonBaseActivity implements WorkerMessageView {

    private String mId;

    @Bind(R.id.gridView)
    GridView mGridView;

    @Bind(R.id.worker_number)
    TextView mJobNumber;

    @Bind(R.id.native_place)
    TextView mNativePlace;

    @Bind(R.id.working_year)
    TextView mWorkingYears;

    @Bind(R.id.education)
    TextView mEducation;

    @Bind(R.id.hobby)
    TextView mHobby;

    @Bind(R.id.height)
    TextView mStature;

    @Bind(R.id.weight)
    TextView mWeight;

    @Bind(R.id.blood_type)
    TextView mBloodType;

    @Bind(R.id.constellation)
    TextView mConstellation;

    @Bind(R.id.signature)
    TextView mSignature;

    @Bind(R.id.service_time)
    TextView mServiceTime;

    @Bind(R.id.address)
    TextView mAddress;

    @Bind(R.id.worker_distance)
    TextView mWorkerServiceScope;

    @Bind(R.id.introduction)
    TextView mIntroduction;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    WorkerMessagePresenter mPresenter;

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPresenter.getWorkerInfo(mId);
                }
            }, getResources().getInteger(R.integer.loading_duration));

        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_detail_message;
    }

    @Override
    protected void initView() {
        String mId = getIntent().getStringExtra(Navigator.WORKER_ID);
        mPresenter.setView(this);
        mPresenter.getWorkerInfo(mId);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.appPrimary);
        //暂时禁用
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerWorkerDetailComponent.builder()
                .build().inject(this);
    }

    @Override
    public void render(WorkerCollectionEntity infoModel) {
        mAddress.setText(infoModel.getAddress());
        mBloodType.setText(infoModel.getBloodType());
        mConstellation.setText(infoModel.getConstellation());
        mWorkerServiceScope.setText(infoModel.getServiceScope());
        mEducation.setText(infoModel.getEducation());
        mHobby.setText(infoModel.getHobby());
        mIntroduction.setText(infoModel.getIntro());
        mJobNumber.setText(infoModel.getJobNumber());
        mNativePlace.setText(infoModel.getNativePlace());
        mServiceTime.setText(infoModel.getServiceTime());
        mSignature.setText(infoModel.getSignature());
        mStature.setText(infoModel.getStature());
        mWeight.setText(infoModel.getWeight());
        mWorkingYears.setText(infoModel.getWorkingYears());
        AuthenticateDetailAdapter adapter = (AuthenticateDetailAdapter) mGridView.getAdapter();
        if (adapter == null) {
            adapter = new AuthenticateDetailAdapter(infoModel.getSystemCertifications());
            mGridView.setAdapter(adapter);
        } else {
            adapter.setDataList(infoModel.getSystemCertifications());
        }
    }

    @Override
    public void render(GetWorkerTagsInfo getWorkerTagsInfo) {

    }

    @Override
    public void renderAvatars(AvatarsEntity avatarsEntity) {

    }
}
