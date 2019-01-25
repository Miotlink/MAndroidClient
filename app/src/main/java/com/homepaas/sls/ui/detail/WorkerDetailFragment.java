package com.homepaas.sls.ui.detail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.WorkerDetailComponent;
import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.SystemCertificationMapper;
import com.homepaas.sls.mvp.presenter.servicedetail.WorkerDetailPresenter;
import com.homepaas.sls.mvp.view.servicedetail.WorkerView;
import com.homepaas.sls.socialization.ShareDialog;
import com.homepaas.sls.ui.adapter.ActionInfoAdapter;
import com.homepaas.sls.ui.adapter.AuthenticateIconAdapter;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.tag.Adapter.TagBaseAdapter;
import com.homepaas.sls.ui.tag.TagDetailActivity;
import com.homepaas.sls.ui.tag.TagLayout;
import com.homepaas.sls.ui.widget.ManuallyCheckBox;
import com.homepaas.sls.ui.widget.TintableImageButton;
import com.homepaas.sls.ui.widget.glide.ImageTarget;
import com.homepaas.sls.ui.widget.refreshview.HeaderViewLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnShowMessageListener} interface
 * to handle interaction events.
 * Use the {@link WorkerDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Deprecated
public class WorkerDetailFragment extends CommonBaseFragment implements WorkerView {

    private static final String ARG_ID = "id";

    private static final String TAG = "WorkerDetailFragment";

    private static final int REQUEST_PERMISSION = 3;
    private static final int ADD_COMMENT = 1;
    @Bind(R.id.top_detail)
    PercentRelativeLayout topDetail;
    @Bind(R.id.detail_message_image1)
    ImageView detailMessageImage1;
    @Bind(R.id.detail_message_image2)
    ImageView detailMessageImage2;
    @Bind(R.id.tag)
    TagLayout tag;
    @Bind(R.id.tag_fengjie)
    View tagFengjie;
    @Bind(R.id.tag_count)
    TextView tagCount;
    @Bind(R.id.tag_btn)
    LinearLayout tagBtn;
    @Bind(R.id.coupon_layout)
    LinearLayout couponLayout;
    @Bind(R.id.headerView)
    NestedScrollView headerView;
    @Bind(R.id.activities_list)
    RecyclerView activitiesList;
    @Bind(R.id.participate_in_activity)
    ImageView participateInActivity;
    @Bind(R.id.activity_info)
    LinearLayout activityInfo;
    @Bind(R.id.tag_ll)
    LinearLayout tagLl;

    private WorkerCollectionEntity currentWorkerInfoModel;
    private int tagcounts;


    private String mId;

    private static final boolean DEBUG = BuildConfig.DEBUG;

    @Bind(R.id.photo_image)
    ImageView mPhoto;

    @Bind(R.id.photo_count)
    TextView mPhotoCount;

    @Bind(R.id.worker_name)
    TextView mWorkerName;

    @Bind(R.id.worker_age)
    TextView mAge;

    @Bind(R.id.worker_type)
    TextView mCraft;

    @Bind(R.id.service_price)
    TextView mHourlyWage;

    @Bind(R.id.message_item1)
    TextView mMessageItem1;

    @Bind(R.id.message_item2)
    TextView mMessageItem2;

    @Bind(R.id.message_item3)
    TextView mMessageItem3;

    @Bind(R.id.like)
    ManuallyCheckBox mLike;

    @Bind(R.id.collection)
    ManuallyCheckBox mCollection;
    //==============排名============
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
    RecyclerView mRecyclerView;

    @Bind(R.id.coupon)
    TextView mCoupon;

    @Bind(R.id.viewStub)
    ViewStub mGalleryViewStub;

    @Bind(R.id.call_button)
    View mCallButton;

    @Bind(R.id.place_order_button)
    View mPlaceOrder;

    @Bind(R.id.review_button)
    TintableImageButton mCommentButton;

    @Bind(R.id.comment_count)
    TextView commentCount;

    @Bind(R.id.swipe_refresh)
    HeaderViewLayout mSwipeRefreshLayout;

    @Bind(R.id.headerContent)
    LinearLayout headerContentLayout;

    // @BindColor(R.color.decorateGreen)
    int mColorGreen;

    //  @BindColor(R.color.decorateOrange)
    int mColorOrange;

    //  @BindColor(R.color.decorateYellow)
    int mColorYellow;

    private TagBaseAdapter mAdapter;
    private List<String> mList;
    private String workerName;

    private CustomerReviewFragment mEvaluationFragment;
    private ActionInfoAdapter actionInfoAdapter;

    @Inject
    WorkerDetailPresenter mWorkerDetailPresenter;

    private OnShowMessageListener mListener;


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mWorkerDetailPresenter.getWorkerEvaluations(mId);
        }

        @Override
        public void onLoadMore() {
            mWorkerDetailPresenter.loadMoreEvaluations();
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
    public WorkerDetailFragment() {
        // Required empty public constructor
    }


    public static WorkerDetailFragment newInstance(String workerId) {
        WorkerDetailFragment fragment = new WorkerDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, workerId);
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
        return R.layout.fragment_worker_detail;
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
        mWorkerDetailPresenter.setWorkerView(this);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mEvaluationFragment = (CustomerReviewFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        mWorkerDetailPresenter.getWorkerInfo(mId);
        mWorkerDetailPresenter.getWorkerEvaluations(mId);
        mWorkerDetailPresenter.getWorkerTagsInfo(mId);
        mWorkerDetailPresenter.getWorkerAvatars(mId);

        tagFengjie.setVisibility(View.GONE);
        tagBtn.setVisibility(View.GONE);
        activityInfo.setVisibility(View.GONE);
        tagLl.setVisibility(View.GONE);
//        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    @OnClick(R.id.tag_btn)
    public void checkTag() {
        Intent intent = new Intent(getActivity(), TagDetailActivity.class);
        intent.putExtra("TitleName", getWorkerName());
        intent.putExtra("Id", mId);
        intent.putExtra("Type", Constant.SERVICE_TYPE_WORKER_INT);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.stopRefresh();
            mSwipeRefreshLayout.destory();
        }
        mWorkerDetailPresenter.destroy();
    }

    @OnClick(R.id.top_detail)
    void onDetailClick() {
        if (mListener != null) {
            mListener.onShowMessage();
        }
    }

    @Override
    protected void initializeInjector() {
        getComponent(WorkerDetailComponent.class).inject(this);
    }


    void report() {
        mWorkerDetailPresenter.report(mId);
    }


    @OnClick(R.id.like)
    void onLikeClick(View view) {
        boolean checked = mLike.isChecked();
        mWorkerDetailPresenter.like(mId, !checked);
        throttle(view);
    }

    @OnClick(R.id.collection)
    void onCollectionClick(View view) {
        boolean checked = mCollection.isChecked();
        mWorkerDetailPresenter.collect(mId, !checked);
        throttle(view);
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
                List<IServiceInfo.SystemCertification> list = mWorkerDetailPresenter.getSystemCertificationList();
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

    @OnClick(R.id.coupon_layout)
    void showCoupon() {

    }

    @OnClick(R.id.review_button)
    void review() {
        if (showNetWorkInfo()) ;
        else return;
        if (mSwipeRefreshLayout.getMode() == HeaderViewLayout.MODE_TOP) {
            mNavigator.addComment(this, ADD_COMMENT, null, mId, Constant.EVALUATION_TYPE_WORKER);
        } else {
            WorkerCollectionEntity infoModel = mWorkerDetailPresenter.getWorkerInfoModel();
            mNavigator.directpay(getContext(), infoModel.getId(), Constant.PAY_TYPE_WORKER, infoModel.getDefaultServiceId(), infoModel.getName());
        }
    }

    @OnClick(R.id.call_button)
    void call(View view) {
        mWorkerDetailPresenter.attemptCall();
        view.setEnabled(false);
    }

    @OnClick(R.id.place_order_button)
    void placeOrder() {
        if(currentWorkerInfoModel!=null) {
            mNavigator.getOrder(getActivity(), Constant.SERVICE_TYPE_WORKER_INT, mId, currentWorkerInfoModel.getGender());
        }
    }

    @OnClick(R.id.photo)
    void showBigPhoto() {
        if (photos!=null&&photos.size() >= 3) {
            GalleryFragment fragment = GalleryFragment.newInstance(photos);
            getFragmentManager().beginTransaction()
                    .addToBackStack(TAG)
                    .add(R.id.container, fragment, TAG)
                    .commit();
        }
    }


    @OnClick(R.id.participate_in_activity)
    public void participate() {
        String workerId = currentWorkerInfoModel.getId();
        //立即参加，跳转到指定下单页面
        mNavigator.getOrder(getContext(),Constant.SERVICE_TYPE_WORKER_INT,workerId, currentWorkerInfoModel.getGender());
    }

    @Override
    public void render(WorkerCollectionEntity infoModel) {

//        int size = infoModel.getPhotos().size();
        currentWorkerInfoModel = infoModel;
        mPhotoCount.setText(String.valueOf(infoModel.getPhotoCount()));
        Glide.with(this)
                .load(infoModel.getPhoto())
                .placeholder(R.mipmap.worker_portrait_default)
                .into(new ImageTarget(mPhoto));
        String genderAge = infoModel.getGender() + infoModel.getAge();
        mAge.setText(genderAge);
        mWorkerName.setText(infoModel.getName());
//        setWorkerName(infoModel.getName());
        mCraft.setText(infoModel.getSplicedServices());
        mHourlyWage.setText(infoModel.getWage());
        mMessageItem1.setText(infoModel.getNativePlace());
        mMessageItem2.setText(infoModel.getWorkingYears());
        mMessageItem3.setText(infoModel.getDistance());
        mLike.setText(String.valueOf(infoModel.getLikeCount()));
        mLike.setChecked(infoModel.isLike());
        mCollection.setText(String.valueOf(infoModel.getCollectedCount()));
        mCollection.setChecked(infoModel.isCollected());
        mItem1.setText(infoModel.getOrderCount());
        mItem2.setText(infoModel.getPhoneCount());
        mItem3.setText(infoModel.getGrade());
        mRank1.setText(createSpannable(infoModel.getOrderRank()));
        mRank2.setText(createSpannable(infoModel.getPhoneRank()));
        mRank3.setText(createSpannable(infoModel.getGradeRank()));
        mCallButton.setEnabled(infoModel.isCallable());
        mPlaceOrder.setEnabled(infoModel.isPlaceOrderEnable());
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

        if (infoModel.getActionEntity() != null) {
            List<String> list1 = new ArrayList<>();
            if (infoModel.getActionEntity().getSpecialRule() != null && !infoModel.getActionEntity().getSpecialRule().isEmpty()){
                for (ActionEntity.ActRule actRule : infoModel.getActionEntity().getSpecialRule()) {
                    list1.add("满" + actRule.getUpper() + "减" + actRule.getMinus());
                }
            }
            if (infoModel.getActionEntity().getPromotionRule() != null && !infoModel.getActionEntity().getPromotionRule().isEmpty()){
                for (ActionEntity.ActRule actRule : infoModel.getActionEntity().getPromotionRule()) {
                    list1.add("满" + actRule.getUpper() + "返" + actRule.getMinus());
                }
            }
           if (!list1.isEmpty()){
               activityInfo.setVisibility(View.VISIBLE);
               actionInfoAdapter = new ActionInfoAdapter(list1);
               activitiesList.setAdapter(actionInfoAdapter);
           }

        } else {
            activityInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void render(GetWorkerTagsInfo getWorkerTagsInfo) {
        if (getWorkerTagsInfo != null) {
            tagcounts = getWorkerTagsInfo.getCount();
            //标签
            mList = new ArrayList<>();
            List<WorkerTagsInfo> tags = getWorkerTagsInfo.getWorkerTagsInfos();
            if (tags.isEmpty()){
                tagLl.setVisibility(View.GONE);
            } else {
                tagLl.setVisibility(View.VISIBLE);
                for (WorkerTagsInfo workerTagsInfo : tags) {
                    mList.add(workerTagsInfo.getTagName());
//                Log.d("workerTagsInfo", "render: " + workerTagsInfo.getTagName());
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
                            tagCount.setText("全部标签（" + tagcounts + "）");
                        }
                    }
                });
            }
        }
    }

    @Override
    public void collectWorker(WorkerCollectionEntity infoModel) {
        String count = String.valueOf(infoModel.getCollectedCount());
        mCollection.setText(count);
        mCollection.setChecked(infoModel.isCollected());
    }

    @Override
    public void likeWorker(WorkerCollectionEntity infoModel) {
        String count = String.valueOf(infoModel.getLikeCount());
        mLike.setText(count);
        mLike.setChecked(infoModel.isLike());
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
        mSwipeRefreshLayout.setCanLoadMore(!evaluations.isEmpty());
        String count;
//        if (evaluations.isEmpty()) {
        count = "客户评价";
//        } else {
//            count = String.format(Locale.CHINA, "客户评价(%d)", evaluations.size());
//        }
        commentCount.setText(count);
        mEvaluationFragment.setList(evaluations);
    }

    @Override
    public void addMoreEvaluations(List<Evaluation> evaluations) {
        mSwipeRefreshLayout.stopRefresh();
        mSwipeRefreshLayout.setCanLoadMore(!evaluations.isEmpty());
        mEvaluationFragment.add(evaluations);
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
                mWorkerDetailPresenter.uploadShareInfo();
            }
        });
        shareDialog.show();
    }

    private List<String> photos;

    @Override
    public void renderAvatars(AvatarsEntity avatarsEntity) {
        if (avatarsEntity != null) {
            photos = avatarsEntity.getAvatars();
        }
    }

    @Override
    public void showLogin() {
        LoginDialogFragment.show(getActivity(), new LoginDialogFragment.OnLoginListener() {
            @Override
            public void onLogin() {
                mWorkerDetailPresenter.getWorkerInfo(mId);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_COMMENT && resultCode == Activity.RESULT_OK) {
            mWorkerDetailPresenter.getWorkerEvaluations(mId);
        }
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
                dial(mWorkerDetailPresenter.getPhoneNumber());
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
            mWorkerDetailPresenter.startDial();
           /* Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);*/
            mNavigator.call(getActivity(), phone);
        } else {
            requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnShowMessageListener) {
            mListener = (OnShowMessageListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    void share() {
        mWorkerDetailPresenter.share();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the currentAction and potentially other fragments contained in that
     * currentAction.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnShowMessageListener {

        void onShowMessage();
    }
}
