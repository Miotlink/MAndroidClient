package com.homepaas.sls.ui.newdetail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMerchantWorkerComponet;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.PopuModle;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.TagsInfoMapper;
import com.homepaas.sls.mvp.presenter.newdetail.MerchantWorkerDetailPresenter;
import com.homepaas.sls.mvp.view.newdetail.MerchantWorkerView;
import com.homepaas.sls.socialization.ShareDialog;
import com.homepaas.sls.ui.adapter.ItemsPagerAdapter;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.detail.BusinessMessageFragment;
import com.homepaas.sls.ui.detail.WorkerMessageFragment;
import com.homepaas.sls.ui.newdetail.adapter.ActionAdapter;
import com.homepaas.sls.ui.tag.Adapter.TagBaseAdapter;
import com.homepaas.sls.ui.tag.TagLayout;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.MorePopuWindow;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.glide.ImageTarget;
import com.makeramen.roundedimageview.RoundedImageView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link MerchantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerchantDetailFragment extends CommonBaseFragment implements MerchantWorkerView, MorePopuWindow.OnItemClickListener, TagLayout.getLineNumListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "mId";
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.photo_image)
    RoundedImageView photoImage;
    @Bind(R.id.tag)
    TagLayout tag;
    @Bind(R.id.call_button)
    ImageView callButton;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.action_number)
    TextView actionNumber;
    @Bind(R.id.action_show)
    ImageView actionShow;
    @Bind(R.id.action_number_layout)
    LinearLayout actionNumberLayout;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.certification)
    TextView certification;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.describe)
    TextView describe;
    @Bind(R.id.action_layout)
    LinearLayout actionLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    // TODO: Rename and change types of parameters
    private String type;
    private String mId;
    @Inject
    MerchantWorkerDetailPresenter merchantWorkerDetailPresenter;
    private static final int REQUEST_PERMISSION = 3;
    private Fragment[] mFragments = new Fragment[3];
    private String[] titles;
    private List<String> mList;
    private TagBaseAdapter mAdapter;
    private MorePopuWindow morePopuWindow;

    public MerchantDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type Parameter 1.
     * @param mId  Parameter 2.
     * @return A new instance of fragment MerchantDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MerchantDetailFragment newInstance(String type, String mId) {
        MerchantDetailFragment fragment = new MerchantDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, type);
        args.putString(ARG_PARAM2, mId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initializeInjector() {
        DaggerMerchantWorkerComponet.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM1);
            mId = getArguments().getString(ARG_PARAM2);
        }
//        EventBus.getDefault().register(this);
    }

    /***
     * 显示/关闭Popu
     */
    public void showMore() {
            morePopuWindow = new MorePopuWindow(getContext(), this);
            morePopuWindow.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
            morePopuWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            morePopuWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            morePopuWindow.showAtLocation(appBarLayout, Gravity.RIGHT | Gravity.TOP, 10, toolbar.getHeight());
            morePopuWindow.update();
            morePopuWindow.setList(curPopuModles);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchant_detail;
    }

    @Override
    protected void initView() {

//        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //折叠时你想要的颜
        toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.app_title_color));
        //展开始颜色
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.translucent));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLayout.getTotalScrollRange() + verticalOffset <= 20) {
//                    toolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
                    toolbar.setTitle(R.string.space);
                    toolbarLayout.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    toolbarLayout.setTitle("  ");
                    isShow = false;
                }
            }
        });
        merchantWorkerDetailPresenter.setMerchantWorkerView(this);
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type)) {
            merchantWorkerDetailPresenter.getBusinessInfo(mId);
//            merchantWorkerDetailPresenter.getBusinessTagsInfo(mId);
//            merchantWorkerDetailPresenter.getBusinessAvatars(mId);
            titles = new String[]{"服务", "评价", "商户"/*,"PPING"*/};
        } else {
            merchantWorkerDetailPresenter.getWorkerInfo(mId);
//            merchantWorkerDetailPresenter.getWorkerTagsInfo(mId);
//            merchantWorkerDetailPresenter.getWorkerAvatars(mId);
            titles = new String[]{"服务", "评价", "工人"/*,"PPING"*/};
        }
//        mFragments[0] = new ServiceContentFragment();
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type)) {
            mFragments[0] = MerchantServiceFragment.newInstance(mId);
        } else {
            mFragments[0] = WorkerServiceFragment.newInstance(mId);
        }

        mFragments[1] = NewTwoCustomerFragment.newInstance(type, mId);
//        mFragments[1] = NewCustomerReviewFragment.newInstance(type, mId);
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
            mFragments[2] = BusinessMessageFragment.newInstance(mId);
        else
            mFragments[2] = WorkerMessageFragment.newInstance(mId);
//        mFragments[3] = NewTwoCustomerFragment.newInstance(type, mId);
        ItemsPagerAdapter adapter = new ItemsPagerAdapter(getActivity().getSupportFragmentManager(), mFragments);
        adapter.setTitles(titles);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1)
                    fab.setVisibility(View.VISIBLE);
                else
                    fab.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.fab)
    public void comment(){
        if (mFragments[1] == null)
            mFragments[1] = NewTwoCustomerFragment.newInstance(type, mId);
        ((NewTwoCustomerFragment)mFragments[1]).comment();
    }

    private static final int CODE_LOGIN = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case CODE_LOGIN:
                    if (data != null && data.getBooleanExtra("Status",false) ) {
                        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                            merchantWorkerDetailPresenter.getBusinessInfo(mId);
                        else
                            merchantWorkerDetailPresenter.getWorkerInfo(mId);
                    }
                    break;
            }

        }
    }

    @Override
    public void showLogin() {
        mNavigator.login(MerchantDetailFragment.this,CODE_LOGIN);
//        LoginDialogFragment.show(getActivity(), new LoginDialogFragment.OnLoginListener() {
//            @Override
//            public void onLogin() {
//                if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
//                    merchantWorkerDetailPresenter.getBusinessInfo(mId);
//                else
//                    merchantWorkerDetailPresenter.getWorkerInfo(mId);
//            }
//        });
    }

    @Override
    public void renderTags(List<TagsInfoMapper.TagsInfo> tagsInfos) {
        if (tagsInfos != null && !tagsInfos.isEmpty()) {
            mList = new ArrayList<>();
            for (TagsInfoMapper.TagsInfo tagsInfo : tagsInfos) {
                mList.add(tagsInfo.getTagName());
            }
            mAdapter = new TagBaseAdapter(getActivity(), mList);
            mAdapter.setStyle(R.color.appText6,"#ffffff","#00000000");
            tag.setLimit(true);
            tag.setLimitCount(1);
            tag.setLineNumListener(this);
            tag.setAdapter(mAdapter);
        }

    }

    private String getCount(String ss) {
        String s = "";
        String rex = "-*\\d+(\\.\\d+)?";
        if (!TextUtils.isEmpty(ss)) {
            Pattern pattern = Pattern.compile(rex);
            Matcher matcher = pattern.matcher(ss);
            if (matcher.find()) {
                s = matcher.group();
            }
//            s = pattern.pattern();
        }
        return s;
    }


    private ActionAdapter actionAdapter;
    private List<ActivityNgInfoNew.ActivityNgDetail> fullActivityNgInfos;
    private List<ActivityNgInfoNew.ActivityNgDetail> activityNgInfos;
    private String title;

    private List<ActivityNgInfoNew.ActivityNgDetail> getAllActivityInfo(List<ActivityNgInfoNew.AcitivityNgOfServiceType> acitivityNgOfServiceTypeList) {
        List<ActivityNgInfoNew.ActivityNgDetail> activityNgDetails = new ArrayList<>();
        for (ActivityNgInfoNew.AcitivityNgOfServiceType acitivityNgOfServiceType : acitivityNgOfServiceTypeList) {
            if (acitivityNgOfServiceType != null && acitivityNgOfServiceType.getActivityNgDetailList() != null && !acitivityNgOfServiceType.getActivityNgDetailList().isEmpty()) {
                for (ActivityNgInfoNew.ActivityNgDetail activityNgDetail : acitivityNgOfServiceType.getActivityNgDetailList()) {
                    if (activityNgDetail != null) {
                        activityNgDetails.add(activityNgDetail);
                    }
                }
            }
        }
        return activityNgDetails;
    }

    @Override
    public void renderBusinessInfo(BusinessInfoModel businessInfoModel) {
        curPopuModles = merchantWorkerDetailPresenter.getPopuModule(type);
        Glide.with(this)
                .load(businessInfoModel.getPhoto())
                .placeholder(R.mipmap.business_portrait_default)
                .into(new ImageTarget(photoImage));
        ((MerchantServiceFragment) mFragments[0]).setJudgePayInStore(businessInfoModel.getIsStorePay(), businessInfoModel.getStorePayStr(), businessInfoModel.getName(), businessInfoModel.getAddress(), businessInfoModel.getPhoto());
        if (businessInfoModel.getSystemCertifications() != null && !businessInfoModel.getSystemCertifications().isEmpty()) {
            certification.setVisibility(View.VISIBLE);
            certification.setText(businessInfoModel.getSystemCertifications().size() + "项认证");
        } else {
            certification.setVisibility(View.GONE);
        }
        int count = businessInfoModel.getSystemCertifications().size();
        name.setText(businessInfoModel.getName());
        title = businessInfoModel.getName();
        describe.setText("接单数：" + /*getCount(businessInfoModel.getOrderCount())*/businessInfoModel.getOrderCount2() +
                "/电话数：" + /*getCount(businessInfoModel.getPhoneCount())*/ businessInfoModel.getPhoneCount2() + "/评分："
                +/* getCount(businessInfoModel.getGrade())*/businessInfoModel.getGrade2());
        if (businessInfoModel.getActivityNgInfos() != null && businessInfoModel.getActivityNgInfos().getAcitivityNgOfServiceTypeList() != null && !businessInfoModel.getActivityNgInfos().getAcitivityNgOfServiceTypeList().isEmpty()) {
            businessInfoModel.getActivityNgInfos().setFullcategoryFlag();
            fullActivityNgInfos = getAllActivityInfo(businessInfoModel.getActivityNgInfos().getAcitivityNgOfServiceTypeList());
        }
        if (fullActivityNgInfos != null && !fullActivityNgInfos.isEmpty()) {
            actionLayout.setVisibility(View.VISIBLE);
            actionNumber.setText(fullActivityNgInfos.size() + "个活动");
            actionAdapter = new ActionAdapter();
            recyclerView.setAdapter(actionAdapter);
            actionShow.setSelected(false);
            if (fullActivityNgInfos.size() <= 2) {
                actionShow.setVisibility(View.GONE);
                actionAdapter.setDatas(fullActivityNgInfos);
            } else {
                actionShow.setVisibility(View.VISIBLE);
                activityNgInfos = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    activityNgInfos.add(fullActivityNgInfos.get(i));
                }
                actionAdapter.setDatas(activityNgInfos);
            }
        } else {
            actionLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void renderWorkerInfo(WorkerCollectionEntity infoModel) {
        curPopuModles = merchantWorkerDetailPresenter.getPopuModule(type);
        Glide.with(this)
                .load(infoModel.getPhoto())
                .placeholder(R.mipmap.worker_portrait_default)
                .into(new ImageTarget(photoImage));
        ((WorkerServiceFragment) mFragments[0]).setgender(infoModel.getGender());
        if (infoModel.getSystemCertifications() != null && !infoModel.getSystemCertifications().isEmpty()) {
            certification.setVisibility(View.VISIBLE);
            certification.setText(infoModel.getSystemCertifications().size() + "项认证");
        } else {
            certification.setVisibility(View.GONE);
        }
        name.setText(infoModel.getName());
        title = infoModel.getName();
        describe.setText("接单数：" +/* getCount(infoModel.getOrderCount())*/ infoModel.getOrderCount2() +
                "/电话数：" + /*getCount(infoModel.getPhoneCount())*/ infoModel.getPhoneCount2() + "/评分："
                + /*getCount(infoModel.getGrade())*/infoModel.getGrade2());
        if (infoModel.getActivityNgInfos() != null && infoModel.getActivityNgInfos().getAcitivityNgOfServiceTypeList() != null && !infoModel.getActivityNgInfos().getAcitivityNgOfServiceTypeList().isEmpty()) {
            infoModel.getActivityNgInfos().setFullcategoryFlag();
            fullActivityNgInfos = getAllActivityInfo(infoModel.getActivityNgInfos().getAcitivityNgOfServiceTypeList());
        }
        if (fullActivityNgInfos != null && !fullActivityNgInfos.isEmpty()) {
            actionLayout.setVisibility(View.VISIBLE);
            actionNumber.setText(fullActivityNgInfos.size() + "个活动");
            actionAdapter = new ActionAdapter();
            recyclerView.setAdapter(actionAdapter);
            actionShow.setSelected(false);
            if (fullActivityNgInfos.size() <= 2) {
                canHandler = false;
                actionShow.setVisibility(View.GONE);
                actionAdapter.setDatas(fullActivityNgInfos);
            } else {
                canHandler = true;
                actionShow.setVisibility(View.VISIBLE);
                activityNgInfos = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    activityNgInfos.add(fullActivityNgInfos.get(i));
                }
                actionAdapter.setDatas(activityNgInfos);
            }
        } else {
            actionLayout.setVisibility(View.GONE);
        }
    }

    private boolean canHandler = false;

    @OnClick(R.id.action_number_layout)
    public void showOrClose() {
        if (canHandler) {
            if (actionShow.isSelected()) {
                actionShow.setSelected(false);
                actionAdapter.setDatas(activityNgInfos);
            } else {
                actionShow.setSelected(true);
                actionAdapter.setDatas(fullActivityNgInfos);
            }
        }
    }

    private List<PopuModle> curPopuModles;
    private boolean isShow = false;
    private boolean isSelfDismiss = true;



    @Override
    public void collectBusiness(Boolean sucess, boolean status) {
        if (sucess) {
            curPopuModles.get(0).setStatus(status);
            if (status) {
                showMessage("收藏成功");
            } else {
                showMessage("取消收藏成功");
            }
        }
    }

    @Override
    public void likeBusiness(Boolean sucess, boolean status) {
        if (sucess) {
            curPopuModles.get(1).setStatus(status);
            if (status) {
                showMessage("点赞成功");
            } else {
                showMessage("取消点赞成功");
            }
        }
    }


    @OnClick(R.id.call_button)
    public void call() {
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
            merchantWorkerDetailPresenter.attemptBusinessCall();
        else
            merchantWorkerDetailPresenter.attemptCall();
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
                if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                    dial(merchantWorkerDetailPresenter.getBusinessPhoneNumber());
                else
                    dial(merchantWorkerDetailPresenter.getPhoneNumber());
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
            if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                merchantWorkerDetailPresenter.startBusinessDial();
            else
                merchantWorkerDetailPresenter.startWorkerDial();
          /*  Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);*/
            mNavigator.call(getActivity(), phone);
        } else {
            requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION);
        }
    }

    @Override
    public void collectWorker(Boolean sucess, boolean status) {
        if (sucess) {
            curPopuModles.get(0).setStatus(status);
            if (status) {
                showMessage("收藏成功");
            } else {
                showMessage("取消收藏成功");
            }
        }
    }

    @Override
    public void likeWorker(Boolean sucess, boolean status) {
        if (sucess) {
            curPopuModles.get(1).setStatus(status);
            if (status) {
                showMessage("点赞成功");
            } else {
                showMessage("取消点赞成功");
            }
        }
    }

    @Override
    public void callIfEnabled(boolean enabled, String phone) {
        if (enabled)
            dial(phone);
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
                merchantWorkerDetailPresenter.uploadShareInfo();
            }
        });
        shareDialog.show();

    }

    @Override
    public void renderAvatars(AvatarsEntity avatarsEntity) {

    }

    @Override
    public void clickItem(int position, boolean status) {
        switch (position) {
            case 0:
                if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                    merchantWorkerDetailPresenter.collectBusiness(mId, status);
                else
                    merchantWorkerDetailPresenter.collect(mId, status);
                break;
//            case 1:
//                merchantWorkerDetailPresenter.share();
//                break;
            case 1:
                if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type))
                    merchantWorkerDetailPresenter.likeBusiness(mId, status);
                else
                    merchantWorkerDetailPresenter.like(mId, status);
                break;
        }
        morePopuWindow.dismiss();
    }

    @Override
    public void getLineNum(int line) {

    }
}
