package com.homepaas.sls.ui.detail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.BusinessDetailComponent;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.BusinessTagsInfo;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.IServiceInfo.SystemCertification;
import com.homepaas.sls.mvp.model.mapper.SystemCertificationMapper;
import com.homepaas.sls.mvp.presenter.servicedetail.BusinessDetailPresenter;
import com.homepaas.sls.mvp.view.servicedetail.BusinessView;
import com.homepaas.sls.socialization.ShareDialog;
import com.homepaas.sls.ui.adapter.AuthenticateIconAdapter;
import com.homepaas.sls.ui.adapter.ItemsPagerAdapter;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.newdetail.NewCustomerReviewFragment;
import com.homepaas.sls.ui.order.directOrder.BusinessOrderActivity;
import com.homepaas.sls.ui.tag.Adapter.TagBaseAdapter;
import com.homepaas.sls.ui.tag.TagDetailActivity;
import com.homepaas.sls.ui.tag.TagLayout;
import com.homepaas.sls.ui.widget.ManuallyCheckBox;
import com.homepaas.sls.ui.widget.TintableImageButton;
import com.homepaas.sls.ui.widget.glide.ImageTarget;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.hyphenate.helpdesk.easeui.ui.BaseFragment;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnPageChange;
import butterknife.OnTouch;

/**
 * A simple {@link BaseFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnShowMessageListener} interface
 * to handle interaction events.
 */
@Deprecated
public class BusinessDetailFragment extends CommonBaseFragment implements BusinessView {

    private static final String TAG = "BusinessDetailFragment";

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final int ADD_COMMENT = 1;

    @Bind(R.id.services)
    TextView mServices;
    @Bind(R.id.business_message)
    PercentRelativeLayout businessMessage;
    @Bind(R.id.detail_message_image1)
    ImageView detailMessageImage1;
    @Bind(R.id.detail_message_image2)
    ImageView detailMessageImage2;
    @Bind(R.id.tag)
    TagLayout tag;
    @Bind(R.id.tag_fengjie)
    View tagFengjie;
    @Bind(R.id.tag_btn)
    LinearLayout tagBtn;
    @Bind(R.id.tag_count)
    TextView tagCount;

    private OnShowMessageListener mListener;

    private static final String ARG_KEY = "businessId";

    private static final int REQUEST_PERMISSION = 3;

    private String mBusinessId;

    @Bind(R.id.photo_image)
    ImageView mBusinessPhoto;

    @Bind(R.id.photo_count)
    TextView mPhotoCount;

    @Bind(R.id.shop_name)
    TextView mBusinessName;

    @Bind(R.id.shop_address)
    TextView mBusinessAddress;

    @Bind(R.id.business_distance)
    TextView mBusinessDistance;

    @Bind(R.id.like)
    ManuallyCheckBox mLike;

    @Bind(R.id.collection)
    ManuallyCheckBox mCollection;
    //==============排名============
    //图片先固定不变
//    @Bind(R.id.detail_message_image1)
//    ImageView mImage1;
//
//    @Bind(R.id.detail_message_image2)
//    ImageView mImage2;
//
//    @Bind(R.id.detail_message_image3)
//    ImageView mImage3;

    @Bind(R.id.detail_message_item1)
    TextView mItem1;

    @Bind(R.id.detail_message_item2)
    TextView mItem2;

    @Bind(R.id.detail_message_item3)
    TextView mItem3;

    @Bind(R.id.detail_message_rank1)
    TextView mRank1;

    @Bind(R.id.detail_message_rank2)
    TextView mRank2;

    @Bind(R.id.detail_message_rank3)
    TextView mRank3;

    //==========================
    //===================认证====
    @Bind(R.id.auth_recyclerView)
    RecyclerView mAuthenticateRecyclerView;

    @Bind(R.id.coupon)
    TextView mCoupon;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Bind(R.id.tab)
    TabLayout mTab;

    @Bind(R.id.viewStub)
    ViewStub mGalleryViewStub;

    @Bind(R.id.call_button)
    View mCallButton;

    @Bind(R.id.place_order_button)
    View mPlaceOrder;

    @Bind(R.id.review_button)
    TintableImageButton mCommentButton;

    // @BindColor(R.color.decorateGreen)
    int mColorGreen;

    // @BindColor(R.color.decorateOrange)
    int mColorOrange;

    // @BindColor(R.color.decorateYellow)
    int mColorYellow;

    @Bind(R.id.swipe_refresh)
    HeaderViewLayout mSwipeRefreshLayout;

    private TagBaseAdapter mAdapter;
    private List<String> mList;

    private boolean canLoadMore;
    private Fragment[] mFragments = new Fragment[3];
    private String businessName;

    private int tagcounts;

    @Inject
    BusinessDetailPresenter mBusinessDetailPresenter;


    private HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mBusinessDetailPresenter.getBusinessEvaluations(mBusinessId);
        }

        @Override
        public void onLoadMore() {
            mBusinessDetailPresenter.addMoreEvaluations();
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
            if (mode == HeaderViewLayout.MODE_BOTTOM) {
                mCommentButton.setImageResource(R.mipmap.icon_pay);
                mCommentButton.setEnabled(false);
            } else {
                mCommentButton.setImageResource(R.mipmap.ic_comment_nor);
                mCommentButton.setEnabled(true);
            }
        }
    };


    public static BusinessDetailFragment newInstance(String businessId) {
        BusinessDetailFragment fragment = new BusinessDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_KEY, businessId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBusinessId = getArguments().getString(ARG_KEY);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_business_detail;
    }

    @Override
    protected void initView() {
        mColorGreen = ContextCompat.getColor(getActivity(), R.color.decorateGreen);
        mColorOrange = ContextCompat.getColor(getActivity(), R.color.decorateOrange);
        mColorYellow = ContextCompat.getColor(getActivity(), R.color.decorateYellow);
        mCommentButton.setEnabled(false);

        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBusinessDetailPresenter.setBusinessView(this);
        mBusinessDetailPresenter.getBusinessInfo(mBusinessId);
        mBusinessDetailPresenter.getBusinessTagsInfo(mBusinessId);
        mBusinessDetailPresenter.getBusinessAvatars(mBusinessId);
        mFragments[0] = new ServiceContentFragment();
        mFragments[1] = new CustomerReviewFragment();
        mFragments[2] = NewCustomerReviewFragment.newInstance(Constant.SERVICE_TYPE_BUSINESS, mBusinessId);
        ItemsPagerAdapter adapter = new ItemsPagerAdapter(getActivity().getSupportFragmentManager(), mFragments);
        adapter.setTitles(new String[]{"服务内容", "客户评价","NEW"});
        mViewPager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewPager);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mBusinessDetailPresenter.getBusinessEvaluations(mBusinessId);
        mBusinessDetailPresenter.getServiceContents(mBusinessId);

        tagFengjie.setVisibility(View.GONE);
        tagBtn.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @OnClick(R.id.tag_btn)
    public void checkTag() {
        Intent intent = new Intent(getActivity(), TagDetailActivity.class);
        intent.putExtra("TitleName", getBusinessName());
        intent.putExtra("Id", mBusinessId);
        intent.putExtra("Type",Constant.SERVICE_TYPE_BUSINESS_INT);
        startActivity(intent);

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        getComponent(BusinessDetailComponent.class)
                .inject(this);
    }


    @Override
    public void render(GetBusinessTagsInfo getBusinessTagsInfo) {
        if (getBusinessTagsInfo != null){
            tagcounts = getBusinessTagsInfo.getCount();
            mList = new ArrayList<>();
            List<BusinessTagsInfo> tagsInfos = getBusinessTagsInfo.getBusinessTagsInfos();
            for (BusinessTagsInfo businessTagsInfo : tagsInfos){
                mList.add(businessTagsInfo.getTagName());
            }
            mAdapter = new TagBaseAdapter(getActivity(), mList);
            tag.setLimit(true);
            tag.setLimitCount(3);
            tag.setAdapter(mAdapter);
            tag.setLineNumListener(new TagLayout.getLineNumListener() {
                @Override
                public void getLineNum(int line) {

                    if (line > tag.getLimitCount()) {
                        tagFengjie.setVisibility(View.VISIBLE);
                        tagBtn.setVisibility(View.VISIBLE);
                        tagCount.setText("全部标签（"+tagcounts+"）");
                    }
                }
            });
        }


    }

    @Override
    public void render(BusinessInfoModel infoModel) {
//        int size = infoModel.getPhotos().size();
        mPhotoCount.setText(infoModel.getPhotoCount());
        Glide.with(this)
                .load(infoModel.getPhoto())
                .placeholder(R.mipmap.business_portrait_default)
                .into(new ImageTarget(mBusinessPhoto));
        mBusinessName.setText(infoModel.getName());
        setBusinessName(infoModel.getName());
        mBusinessAddress.setText(infoModel.getAddress());
        mBusinessDistance.setText(infoModel.getDistance());
        mLike.setChecked(infoModel.isLike());
        mLike.setText(String.valueOf(infoModel.getLikeCount()));
        mCollection.setChecked(infoModel.isCollected());
        mCollection.setText(String.valueOf(infoModel.getCollectedCount()));
        mItem1.setText(infoModel.getOrderCount());
        mItem2.setText(infoModel.getPhoneCount());
        mItem3.setText(infoModel.getGrade());
        mRank1.setText(createSpannable(infoModel.getOrderRank()));
        mRank2.setText(createSpannable(infoModel.getPhoneRank()));
        mRank3.setText(createSpannable(infoModel.getGradeRank()));
        mServices.setText(infoModel.getSplicedServices());
        mCallButton.setEnabled(infoModel.isCallable());
        mPlaceOrder.setEnabled(true);
        AuthenticateIconAdapter adapter = (AuthenticateIconAdapter) mAuthenticateRecyclerView.getAdapter();
        SystemCertificationMapper systemCertificationMapper = new SystemCertificationMapper();
        if (adapter == null) {
            adapter = new AuthenticateIconAdapter(systemCertificationMapper.transform(infoModel));
            mAuthenticateRecyclerView.setAdapter(adapter);
        } else {
            adapter.setSystemCertificationList(systemCertificationMapper.transform(infoModel));
        }
    }


    @OnPageChange(R.id.viewPager)
    void onPageSelected(int pageIndex) {
        if (pageIndex == 0) {
            //服务内容不支持分页加载
            mSwipeRefreshLayout.setCanLoadMore(false);
        } else {
            mSwipeRefreshLayout.setCanLoadMore(canLoadMore);
        }
    }


    @OnClick(R.id.like)
    public void onLikeClick(View view) {
        boolean checked = mLike.isChecked();
        mBusinessDetailPresenter.likeBusiness(mBusinessId, !checked);
        throttle(view);
    }

    @OnClick(R.id.collection)
    public void onCollectClick(View view) {
        boolean checked = mCollection.isChecked();
        mBusinessDetailPresenter.collectBusiness(mBusinessId, !checked);
        throttle(view);
    }

    @OnClick({R.id.review_button, R.id.place_order_button, R.id.call_button})
    public void operate(View view) {
        switch (view.getId()) {
            case R.id.review_button:
                if (mSwipeRefreshLayout.getMode() == HeaderViewLayout.MODE_TOP) {
                    mNavigator.addComment(this, ADD_COMMENT, null, mBusinessId, Constant.EVALUATION_TYPE_BUSINESS);
                } else {
                    BusinessInfoModel infoModel = mBusinessDetailPresenter.getBusinessInfoModel();
                    mNavigator.directpay(getContext(), infoModel.getId(), Constant.PAY_TYPE_WORKER, infoModel.getDefaultServiceId(), infoModel.getName());
                }
                break;
            case R.id.place_order_button:
//                mNavigator.getOrder(getActivity(), Constant.SERVICE_TYPE_BUSINESS_INT, mBusinessId);
                BusinessOrderActivity.start(getContext(), Constant.SERVICE_TYPE_BUSINESS_INT, mBusinessId);
                break;
            case R.id.call_button:
                mBusinessDetailPresenter.attemptCall();
                view.setEnabled(false);
                break;
            default:
        }
    }

    @OnClick(R.id.business_message)
    void showBusinessMessage() {
        if (mListener != null) {
            mListener.showMessage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_COMMENT && resultCode == Activity.RESULT_OK) {

            mBusinessDetailPresenter.getBusinessEvaluations(mBusinessId);
            mViewPager.setCurrentItem(1);

        }
    }

    @OnClick(R.id.photo)
    void showBigPhoto() {
        if (photos.size() >= 3) {
            GalleryFragment fragment = GalleryFragment.newInstance(photos);
            getFragmentManager().beginTransaction()
                    .addToBackStack(TAG)
                    .add(R.id.container, fragment, TAG)
                    .commit();
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
                List<SystemCertification> list = mBusinessDetailPresenter.getSystemCertificationList();
                if (list.isEmpty()) {
                    return true;
                }
                AuthenticateDetailFragment fragment;
                if (list instanceof ArrayList) {
                    fragment = AuthenticateDetailFragment.newInstance((ArrayList<SystemCertification>) list);
                } else {
                    fragment = AuthenticateDetailFragment.newInstance(new ArrayList<>(list));
                }
                fragment.show(getActivity().getSupportFragmentManager(), "");
                return true;
        }
        return false;
    }

    void report() {
        mBusinessDetailPresenter.report(mBusinessId);
    }

    @Override
    public void collectBusiness(BusinessInfoModel businessInfoModel) {
        String count = String.valueOf(businessInfoModel.getCollectedCount());
        mCollection.setText(count);
        mCollection.setChecked(businessInfoModel.isCollected());
    }

    @Override
    public void likeBusiness(BusinessInfoModel businessInfoModel) {
        String count = String.valueOf(businessInfoModel.getLikeCount());
        mLike.setText(count);
        mLike.setChecked(businessInfoModel.isLike());
    }

    @Override
    public void callIfEnabled(boolean enabled, String phone) {
        mCallButton.setEnabled(enabled);
        if (enabled) {
            dial(phone);
        }
    }

    @Override
    public void renderEvaluations(List<Evaluation> evaluations) {
        mSwipeRefreshLayout.stopRefresh();
        canLoadMore = !evaluations.isEmpty();
        mSwipeRefreshLayout.setCanLoadMore(canLoadMore);
        String count;
//        if (evaluations.isEmpty()) {
        count = "客户评价";
//        } else {
//            count = String.format(Locale.CHINA, "客户评价(%d)", evaluations.size());
//        }
        //noinspection ConstantConditions
        mTab.getTabAt(1).setText(count);
        ((CustomerReviewFragment) mFragments[1]).setList(evaluations);
    }

    @Override
    public void addMoreEvaluations(List<Evaluation> evaluations) {
        mSwipeRefreshLayout.stopRefresh();
        canLoadMore = !evaluations.isEmpty();
        mSwipeRefreshLayout.setCanLoadMore(canLoadMore);
        ((CustomerReviewFragment) mFragments[1]).add(evaluations);
    }

    @Override
    public void renderServiceContents(List<ServiceContent> serviceContents) {
        mSwipeRefreshLayout.stopRefresh();
        String count;
//        if (serviceContents.isEmpty()) {
        count = "服务内容";
//        } else {
//            count = String.format(Locale.CHINA, "服务内容(%d)", serviceContents.size());
//        }
        //noinspection ConstantConditions
        mTab.getTabAt(0).setText(count);
        ((ServiceContentFragment) mFragments[0]).setList(serviceContents);
    }

    @Override
    public void share(ShareInfo shareInfo) {
        ShareDialog shareDialog = new ShareDialog(getActivity());
        shareDialog.setTitle(shareInfo.getTitle());
        shareDialog.setUrl(shareInfo.getUrl());
        shareDialog.setText(shareInfo.getDescription());
        shareDialog.setImage(new UMImage(getActivity(), shareInfo.getPicture()));
        shareDialog.setUmShareListener(new ShareDialog.SimpleUMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA share_media) {
                mBusinessDetailPresenter.uploadShareInfo();
            }
        });
        shareDialog.show();
    }

    private List<String> photos;
    @Override
    public void renderBusinessAvatars(AvatarsEntity avatarsEntity) {
        if (avatarsEntity != null){
            photos = avatarsEntity.getAvatars();
        }
    }

    void share() {
        mBusinessDetailPresenter.share();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnShowMessageListener) {
            mListener = (OnShowMessageListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnShowMessageListener");
        }
    }

    @Override
    public void onDestroyView() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.stopRefresh();
            mSwipeRefreshLayout.destory();
        }
        super.onDestroyView();
        mBusinessDetailPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                dial(mBusinessDetailPresenter.getPhoneNumber());
                break;
        }
    }

    private void dial(String phone) {
        List<String> permissions = new ArrayList<>(2);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //Service 中无法回调onRequestPermissionsResult，提前请求
            permissions.add(Manifest.permission.READ_CALL_LOG);
        }
        if (permissions.isEmpty()) {
            mBusinessDetailPresenter.startDial();
          /*  Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);*/
            mNavigator.call(getActivity(), phone);
        } else {
            requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION);
        }
    }



    @Override
    public void showLogin() {


        LoginDialogFragment.show(getActivity(), new LoginDialogFragment.OnLoginListener() {
            @Override
            public void onLogin() {
                mBusinessDetailPresenter.getBusinessInfo(mBusinessId);
            }
        });
    }

    //处理字体颜色
    private SpannableString createSpannable(CharSequence string) {
        SpannableString spannableRank = new SpannableString(string + "%");
        int intRank = Integer.parseInt(string.toString());
        int color;
        if (intRank <= 30) {
            color = mColorOrange;
        } else if (intRank <= 70) {
            color = mColorYellow;
        } else {
            color = mColorGreen;
        }
        spannableRank.setSpan(new ForegroundColorSpan(color), 0, string.length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableRank;
    }

    public interface OnShowMessageListener {

        void showMessage();
    }
}
