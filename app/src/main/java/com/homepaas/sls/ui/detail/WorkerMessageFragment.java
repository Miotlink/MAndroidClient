package com.homepaas.sls.ui.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMerchantWorkerComponet;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.SystemCertificationMapper;
import com.homepaas.sls.mvp.presenter.servicedetail.WorkerMessagePresenter;
import com.homepaas.sls.mvp.view.servicedetail.WorkerMessageView;
import com.homepaas.sls.ui.adapter.AuthenticateIconAdapter;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.newdetail.adapter.PhotoAdapter;
import com.homepaas.sls.ui.tag.Adapter.TagBaseAdapter;
import com.homepaas.sls.ui.tag.TagLayout;
import com.homepaas.sls.ui.widget.photo.PhotoPreviewDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkerMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkerMessageFragment extends CommonBaseFragment implements WorkerMessageView, PhotoAdapter.OnZoomPictureListener {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String ARG_ID = "id";
    private static final String TAG = "WorkerMessageFragment";
    //    @Bind(R.id.toolbar)
//    CenterTitleToolbar toolbar;
    @Bind(R.id.tag)
    TagLayout tag;
    @Bind(R.id.service_time_layout)
    LinearLayout serviceTimeLayout;
    @Bind(R.id.service_time_layout_fengjiexian)
    View serviceTimeLayoutFengjiexian;
    @Bind(R.id.authentication_information_fengjiexian)
    View authenticationInformationFengjiexian;
    @Bind(R.id.auth_recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.authentication_information)
    LinearLayout authenticationInformation;
    @Bind(R.id.service_message)
    LinearLayout serviceMessage;
    @Bind(R.id.textView16)
    TextView textView16;
    @Bind(R.id.worker_message)
    LinearLayout workerMessage;
    @Bind(R.id.worker_pics)
    RecyclerView workerPics;
    @Bind(R.id.worker_picture)
    LinearLayout workerPicture;
    @Bind(R.id.native_place_ll)
    LinearLayout nativePlaceLl;
    @Bind(R.id.education_ll)
    LinearLayout educationLl;
    @Bind(R.id.height_ll)
    LinearLayout heightLl;
    @Bind(R.id.blood_type_ll)
    LinearLayout bloodTypeLl;
    @Bind(R.id.working_year_ll)
    LinearLayout workingYearLl;
    @Bind(R.id.constellation_ll)
    LinearLayout constellationLl;
    @Bind(R.id.signature_layout)
    LinearLayout signatureLayout;
    @Bind(R.id.signature_intro_divider)
    View signatureIntroDivider;
    @Bind(R.id.introduction_ll)
    LinearLayout introductionLl;
    @Bind(R.id.signature_intro_layout)
    LinearLayout signatureIntroLayout;
    @Bind(R.id.tag_divider_line)
    View tagDividerLine;
    @Bind(R.id.tag_ll)
    LinearLayout tagLl;


    private String mId;

//    @Bind(R.id.gridView)
//    GridView mGridView;

//    @Bind(R.id.worker_number)
//    TextView mJobNumber;

    @Bind(R.id.native_place)
    TextView mNativePlace;

    @Bind(R.id.working_year)
    TextView mWorkingYears;

    @Bind(R.id.education)
    TextView mEducation;

//    @Bind(R.id.hobby)
//    TextView mHobby;

    @Bind(R.id.height)
    TextView mStature;

//    @Bind(R.id.weight)
//    TextView mWeight;

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

    private TagBaseAdapter mAdapter;
    private List<String> mList;

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

    public WorkerMessageFragment() {
        // Required empty public constructor
    }

    public static WorkerMessageFragment newInstance(String id) {
        WorkerMessageFragment fragment = new WorkerMessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ARG_ID);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_worker_message;
    }

    @Override
    protected void initView() {

//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
//        //noinspection ConstantConditions
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter.getWorkerAvatars(mId);
        mPresenter.setView(this);
        mPresenter.getWorkerInfo(mId);
        mPresenter.getWorkerTagsInfo(mId);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.appPrimary);
        //暂时禁用
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected boolean usingDefaultLoading() {
        return mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing();
    }

    @Override
    protected void hideCustomLoading() {
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerMerchantWorkerComponet.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
//        getComponent(WorkerDetailComponent.class)
//                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }


    @Override
    public void render(WorkerCollectionEntity infoModel) {
        if (infoModel.noWorkerMessage()) {
            workerMessage.setVisibility(View.GONE);
        } else {
            workerMessage.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(infoModel.getBloodType()))
                bloodTypeLl.setVisibility(View.GONE);
            else {
                bloodTypeLl.setVisibility(View.VISIBLE);
                mBloodType.setText(infoModel.getBloodType());
            }
            if (TextUtils.isEmpty(infoModel.getConstellation())) {
                constellationLl.setVisibility(View.GONE);
            } else {
                constellationLl.setVisibility(View.VISIBLE);
                mConstellation.setText(infoModel.getConstellation());
            }
            if (TextUtils.isEmpty(infoModel.getEducation()))
                educationLl.setVisibility(View.GONE);
            else {
                educationLl.setVisibility(View.VISIBLE);
                mEducation.setText(infoModel.getEducation());
            }
            if (TextUtils.isEmpty(infoModel.getNativePlace()))
                nativePlaceLl.setVisibility(View.GONE);
            else {
                nativePlaceLl.setVisibility(View.VISIBLE);
                mNativePlace.setText(infoModel.getNativePlace());
            }
            if (TextUtils.isEmpty(infoModel.getStature()))
                heightLl.setVisibility(View.GONE);
            else {
                heightLl.setVisibility(View.VISIBLE);
                mStature.setText(infoModel.getStature());
            }
            if (TextUtils.isEmpty(infoModel.getWorkingYears()))
                workingYearLl.setVisibility(View.GONE);
            else {
                workingYearLl.setVisibility(View.VISIBLE);
                mWorkingYears.setText(infoModel.getWorkingYears());
            }

            if (TextUtils.isEmpty(infoModel.getIntro()) && TextUtils.isEmpty(infoModel.getSignature())) {
                signatureIntroLayout.setVisibility(View.GONE);
            } else {
                signatureIntroLayout.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(infoModel.getIntro())) {
                    introductionLl.setVisibility(View.GONE);
                    signatureIntroDivider.setVisibility(View.GONE);
                } else {
                    introductionLl.setVisibility(View.VISIBLE);
                    signatureIntroDivider.setVisibility(View.VISIBLE);
                    mIntroduction.setText(infoModel.getIntro());
                }
                if (TextUtils.isEmpty(infoModel.getSignature())) {
                    signatureIntroDivider.setVisibility(View.GONE);
                    signatureLayout.setVisibility(View.GONE);
                } else {
                    signatureIntroDivider.setVisibility(View.VISIBLE);
                    signatureLayout.setVisibility(View.VISIBLE);
                    mSignature.setText(infoModel.getSignature());
                }
            }
        }
        mAddress.setText(infoModel.getAddress());
        mWorkerServiceScope.setText(infoModel.getServiceScope());

//        mHobby.setText(infoModel.getHobby());

//        mJobNumber.setText(infoModel.getJobNumber());
        if (TextUtils.isEmpty(infoModel.getServiceTime())) {
            serviceTimeLayout.setVisibility(View.GONE);
            serviceTimeLayoutFengjiexian.setVisibility(View.GONE);
        } else {
            serviceTimeLayout.setVisibility(View.VISIBLE);
            serviceTimeLayoutFengjiexian.setVisibility(View.VISIBLE);
            mServiceTime.setText(infoModel.getServiceTime());
        }

//        mWeight.setText(infoModel.getWeight());

//        AuthenticateDetailAdapter adapter = (AuthenticateDetailAdapter) mGridView.getAdapter();
//        if (adapter == null) {
//            adapter = new AuthenticateDetailAdapter(infoModel.getSystemCertifications());
//            mGridView.setAdapter(adapter);
//        } else {
//            adapter.setDataList(infoModel.getSystemCertifications());
//        }
        if (infoModel.getSystemCertifications() != null && !infoModel.getSystemCertifications().isEmpty()){
            authenticationInformation.setVisibility(View.VISIBLE);
            authenticationInformationFengjiexian.setVisibility(View.VISIBLE);
            AuthenticateIconAdapter adapter = (AuthenticateIconAdapter) mRecyclerView.getAdapter();
//        List<IServiceInfo.SystemCertification> list = infoModel.getSystemCertifications();
            SystemCertificationMapper systemCertificationMapper = new SystemCertificationMapper();
            List<SystemCertificationMapper.SystemCertification> list = systemCertificationMapper.transform(infoModel);
            if (list.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                if (adapter == null) {
                    adapter = new AuthenticateIconAdapter(list);
                    mRecyclerView.setAdapter(adapter);
                } else {
                    adapter.setSystemCertificationList(list);
                }
            }
        } else {
            authenticationInformation.setVisibility(View.GONE);
            authenticationInformationFengjiexian.setVisibility(View.GONE);
        }

        if (infoModel.getGetWorkerTagsInfo() != null && !infoModel.getGetWorkerTagsInfo().getWorkerTagsInfos().isEmpty()){
            tagDividerLine.setVisibility(View.VISIBLE);
            tagLl.setVisibility(View.VISIBLE);
        } else {
            tagDividerLine.setVisibility(View.GONE);
            tagLl.setVisibility(View.GONE);
        }


    }

    @OnTouch(R.id.auth_recyclerView)
    boolean showAuthenticateDetail(MotionEvent event) {
        if (DEBUG)
            Log.d(TAG, "showAuthenticateDetail: ");
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                List<IServiceInfo.SystemCertification> list = mPresenter.getSystemCertificationList();
                if (list.isEmpty()) {
                    return true;
                }
                AuthenticateDetailFragment fragment;
                if (list instanceof ArrayList) {
                    fragment = AuthenticateDetailFragment.newInstance((ArrayList<IServiceInfo.SystemCertification>) list);
                } else {
                    fragment = AuthenticateDetailFragment.newInstance(new ArrayList<>(list));
                }
                fragment.show(getActivity().getSupportFragmentManager(), "");
                return true;
        }
        return false;
    }

    @Override
    public void render(GetWorkerTagsInfo getWorkerTagsInfo) {
        if (getWorkerTagsInfo != null) {
            mList = new ArrayList<>();
            List<WorkerTagsInfo> tagsInfos = getWorkerTagsInfo.getWorkerTagsInfos();
            for (WorkerTagsInfo workerTagsInfo : tagsInfos) {
                mList.add(workerTagsInfo.getTagName());
            }
            mAdapter = new TagBaseAdapter(getActivity(), mList);
            tag.setAdapter(mAdapter);
        }

    }

    @Override
    public void renderAvatars(AvatarsEntity avatarsEntity) {
        if (avatarsEntity == null || avatarsEntity.getAvatars().isEmpty()) {
            workerPicture.setVisibility(View.GONE);
        } else {
            workerPicture.setVisibility(View.VISIBLE);
            workerPics.setLayoutManager(new GridLayoutManager(getContext(), 5));
            PhotoAdapter photoAdapter = new PhotoAdapter(avatarsEntity.getAvatars());
            photoAdapter.setOnZoomPictureListener(this);
            workerPics.setAdapter(photoAdapter);
        }
    }

    @Override
    public void zoom(int position, List<String> photos) {
        PhotoPreviewDialog previewDialog = new PhotoPreviewDialog.Builder()
                .index(position)
                .path(photos)
                .build();
        previewDialog.setIndex(position);
        previewDialog.show(getFragmentManager(), null);
    }
}
