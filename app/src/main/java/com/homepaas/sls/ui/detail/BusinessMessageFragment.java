package com.homepaas.sls.ui.detail;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMerchantWorkerComponet;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.BusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.mapper.SystemCertificationMapper;
import com.homepaas.sls.mvp.presenter.servicedetail.BusinessMessagePresenter;
import com.homepaas.sls.mvp.view.servicedetail.BusinessMessageView;
import com.homepaas.sls.ui.adapter.AuthenticateIconAdapter;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.newdetail.PhotoActivity;
import com.homepaas.sls.ui.newdetail.adapter.PhotoAdapter;
import com.homepaas.sls.ui.tag.Adapter.TagBaseAdapter;
import com.homepaas.sls.ui.tag.TagLayout;
import com.homepaas.sls.ui.widget.photo.PhotoPreviewDialog;
import com.hyphenate.helpdesk.easeui.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link BusinessMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusinessMessageFragment extends CommonBaseFragment implements BusinessMessageView, PhotoAdapter.OnZoomPictureListener {

    private static final String ARG_PARAM1 = "param1";
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "BusinessMessageFragment";
    //    @Bind(R.id.toolbar)
//    CenterTitleToolbar toolbar;
    @Bind(R.id.auth_recyclerView)
    RecyclerView mAuthenticateRecyclerView;
    @Bind(R.id.service_message)
    LinearLayout serviceMessage;
    @Bind(R.id.textView16)
    TextView textView16;
    @Bind(R.id.merchant_message)
    LinearLayout merchantMessage;
    //    @Bind(R.id.merchant_pics)
//    RecyclerView merchantPics;
//    @Bind(R.id.merchant_picture)
//    LinearLayout merchantPicture;
    @Bind(R.id.authentication_information_fengjiexian)
    View authenticationInformationFengjiexian;
    @Bind(R.id.service_time_layout)
    LinearLayout serviceTimeLayout;
    @Bind(R.id.service_time_layout_fengjiexian)
    View serviceTimeLayoutFengjiexian;
    @Bind(R.id.tag)
    TagLayout tag;
    @Bind(R.id.found_time_layout)
    LinearLayout foundTimeLayout;
    @Bind(R.id.area_layout)
    LinearLayout areaLayout;
    @Bind(R.id.scale_layout)
    LinearLayout scaleLayout;
    @Bind(R.id.property_layout)
    LinearLayout propertyLayout;
    @Bind(R.id.textView17)
    TextView textView17;
    @Bind(R.id.staff_count_layout)
    LinearLayout staffCountLayout;
    @Bind(R.id.signature_layout)
    LinearLayout signatureLayout;
    @Bind(R.id.signature_intro_divider)
    View signatureIntroDivider;
    @Bind(R.id.introduction_layout)
    LinearLayout introductionLayout;
    @Bind(R.id.signature_intro_layout)
    LinearLayout signatureIntroLayout;
    @Bind(R.id.merchant_picture)
    RelativeLayout merchantPicture;
    @Bind(R.id.pic_count)
    TextView picCount;
    @Bind(R.id.allow)
    ImageView allow;
    @Bind(R.id.bussines_license)
    RelativeLayout bussinesLicense;
    @Bind(R.id.tag_divider_line)
    View tagDividerLine;
    @Bind(R.id.tag_ll)
    LinearLayout tagLl;
    @Bind(R.id.authentication_information)
    LinearLayout authenticationInformation;

    private String mBusinessId;


//    @Bind(R.id.gridView)
//    GridView mGridView;

//    @Bind(R.id.business_number)
//    TextView mBusinessNumber;

    @Bind(R.id.found_time)
    TextView mFoundTime;

    @Bind(R.id.property)
    TextView mBusinessProperty;

    @Bind(R.id.area)
    TextView mArea;

    @Bind(R.id.staff_count)
    TextView mStaffCount;

    @Bind(R.id.scale)
    TextView mScale;

    @Bind(R.id.signature)
    TextView mSignature;

    @Bind(R.id.service_time)
    TextView mServiceTime;

    @Bind(R.id.address)
    TextView mAddress;

    @Bind(R.id.business_distance)
    TextView mDistance;

    @Bind(R.id.introduction)
    TextView mIntroduction;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    BusinessMessagePresenter mPresenter;

    private TagBaseAdapter mAdapter;
    private List<String> mList;
    private int tagcounts;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param businessId Parameter 1.
     * @return A new instance of fragment BusinessMessageFragment.
     */
    public static BusinessMessageFragment newInstance(String businessId) {
        BusinessMessageFragment fragment = new BusinessMessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, businessId);
        fragment.setArguments(args);
        return fragment;
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPresenter.getBusinessInfo(mBusinessId);
                }
            }, getResources().getInteger(R.integer.loading_duration));

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBusinessId = getArguments().getString(ARG_PARAM1);
        }
        mPresenter.setView(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_business_message;
    }

    @Override
    protected void initView() {
        mPresenter.getBusinessAvatars(mBusinessId);
        mPresenter.getBusinessInfo(mBusinessId);
        mPresenter.getBusinessTagsInfo(mBusinessId);
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
        DaggerMerchantWorkerComponet.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
//        getComponent(BusinessDetailComponent.class)
//                .inject(this);
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
    public void render(BusinessInfoModel infoModel) {
//        mBusinessNumber.setText(infoModel.getNumber());
        if (infoModel.noBusinessMessage()) {
            merchantMessage.setVisibility(View.GONE);
        } else {
            merchantMessage.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(infoModel.getProperty())) {
                propertyLayout.setVisibility(View.GONE);
            } else {
                propertyLayout.setVisibility(View.VISIBLE);
                mBusinessProperty.setText(infoModel.getProperty());
            }
            if (TextUtils.isEmpty(infoModel.getArea()))
                areaLayout.setVisibility(View.GONE);
            else {
                areaLayout.setVisibility(View.VISIBLE);
                mArea.setText(infoModel.getArea());
            }
            if (TextUtils.isEmpty(infoModel.getStaffNumber()))
                staffCountLayout.setVisibility(View.GONE);
            else {
                staffCountLayout.setVisibility(View.VISIBLE);
                mStaffCount.setText(infoModel.getStaffNumber());
            }
            if (TextUtils.isEmpty(infoModel.getEstablishedTime())) {
                foundTimeLayout.setVisibility(View.GONE);
            } else {
                foundTimeLayout.setVisibility(View.VISIBLE);
                mFoundTime.setText(infoModel.getEstablishedTime());
            }
            if (TextUtils.isEmpty(infoModel.getScale()))
                scaleLayout.setVisibility(View.GONE);
            else {
                scaleLayout.setVisibility(View.VISIBLE);
                mScale.setText(infoModel.getScale());
            }
            if (TextUtils.isEmpty(infoModel.getIntro()) && TextUtils.isEmpty(infoModel.getSignature())) {
                signatureIntroLayout.setVisibility(View.GONE);
            } else {
                signatureIntroLayout.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(infoModel.getIntro())) {
                    introductionLayout.setVisibility(View.GONE);
                    signatureIntroDivider.setVisibility(View.GONE);
                } else {
                    introductionLayout.setVisibility(View.VISIBLE);
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
        mDistance.setText(infoModel.getServiceScope());
        mAddress.setText(infoModel.getAddress());
        if (!TextUtils.isEmpty(infoModel.getServiceTime())) {
            serviceTimeLayout.setVisibility(View.VISIBLE);
            serviceTimeLayoutFengjiexian.setVisibility(View.VISIBLE);
            mServiceTime.setText(infoModel.getServiceTime());
        } else {
            serviceTimeLayoutFengjiexian.setVisibility(View.GONE);
            serviceTimeLayout.setVisibility(View.GONE);
        }


//        AuthenticateDetailAdapter adapter = (AuthenticateDetailAdapter) mGridView.getAdapter();
//        if (adapter == null) {
//            adapter = new AuthenticateDetailAdapter(infoModel.getSystemCertifications());
//            mGridView.setAdapter(adapter);
//        } else {
//            adapter.setDataList(infoModel.getSystemCertifications());
//        }
//        if (infoModel.getSystemCertifications() != null && !infoModel.getSystemCertifications().isEmpty()) {
//            authenticationInformation.setVisibility(View.VISIBLE);
//            authenticationInformationFengjiexian.setVisibility(View.VISIBLE);
        if (infoModel.getSystemCertifications() != null && !infoModel.getSystemCertifications().isEmpty()){
            authenticationInformationFengjiexian.setVisibility(View.VISIBLE);
            authenticationInformation.setVisibility(View.VISIBLE);
            AuthenticateIconAdapter adapter = (AuthenticateIconAdapter) mAuthenticateRecyclerView.getAdapter();
            SystemCertificationMapper systemCertificationMapper = new SystemCertificationMapper();
            if (adapter == null) {
                adapter = new AuthenticateIconAdapter(systemCertificationMapper.transform(infoModel));
                mAuthenticateRecyclerView.setAdapter(adapter);
            } else {
                adapter.setSystemCertificationList(systemCertificationMapper.transform(infoModel));
            }
        } else {
            authenticationInformationFengjiexian.setVisibility(View.GONE);
            authenticationInformation.setVisibility(View.GONE);
        }
        if (infoModel.getGetBusinessTagsInfo() != null && !infoModel.getGetBusinessTagsInfo().getBusinessTagsInfos().isEmpty()){
            tagLl.setVisibility(View.VISIBLE);
            tagDividerLine.setVisibility(View.VISIBLE);
        } else {
            tagLl.setVisibility(View.GONE);
            tagDividerLine.setVisibility(View.GONE);
        }

//        } else {
//            authenticationInformation.setVisibility(View.GONE);
//            authenticationInformationFengjiexian.setVisibility(View.GONE);
//        }

        if (infoModel.getLicencePhotos() != null && !infoModel.getLicencePhotos().isEmpty()) {
            bussinesLicense.setVisibility(View.VISIBLE);
        } else {
            bussinesLicense.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.bussines_license)
    public void showLicense() {
        zoom(0, mPresenter.getLicenseHqic());
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
    public void render(GetBusinessTagsInfo getBusinessTagsInfo) {
        if (getBusinessTagsInfo != null) {
            tagcounts = getBusinessTagsInfo.getCount();
            mList = new ArrayList<>();
            List<BusinessTagsInfo> tagsInfos = getBusinessTagsInfo.getBusinessTagsInfos();
            for (BusinessTagsInfo businessTagsInfo : tagsInfos) {
                mList.add(businessTagsInfo.getTagName());
            }
            mAdapter = new TagBaseAdapter(getActivity(), mList);
            tag.setAdapter(mAdapter);
        }
    }

    private AvatarsEntity photos;

    @OnClick(R.id.merchant_picture)
    public void show() {
        PhotoActivity.start(getActivity(), photos);
    }

    @Override
    public void renderAvatars(AvatarsEntity avatarsEntity) {
        if (avatarsEntity == null || avatarsEntity.getAvatars().isEmpty()) {
            merchantPicture.setVisibility(View.GONE);
            picCount.setVisibility(View.GONE);
        } else {
            merchantPicture.setVisibility(View.VISIBLE);
            picCount.setVisibility(View.VISIBLE);
            picCount.setText(avatarsEntity.getAvatars().size() + "张");
            photos = avatarsEntity;

//            merchantPics.setLayoutManager(new GridLayoutManager(getContext(), 5));
//            PhotoAdapter photoAdapter = new PhotoAdapter(avatarsEntity.getAvatars());
//            photoAdapter.setOnZoomPictureListener(this);
//            merchantPics.setAdapter(photoAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
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
