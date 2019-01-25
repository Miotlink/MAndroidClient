package com.homepaas.sls.ui.order.servicemerchant;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.ui.common.fragment.CommonMvpLazyLoadFragment;
import com.homepaas.sls.ui.order.adapter.BusinessCertificationsAdapter;
import com.homepaas.sls.ui.widget.photo.PhotoPreviewDialog;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mhy on 2017/12/27.
 * 服务详情商户tab界面
 */

public class MerchantDetailMerchantFragment extends CommonMvpLazyLoadFragment implements BaseRecyclerAdapter.OnItemClickListener {
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_service_address)
    TextView tvServiceAddress;
    @Bind(R.id.tv_service_scope)
    TextView tvServiceScope;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.rl_merchant_or_worker_icon)
    RelativeLayout rlMerchantOrWorkerIcon;
    @Bind(R.id.tv_photo_number)
    TextView tvPhotoNumber;
    @Bind(R.id.rv_businessCertifications)
    RecyclerView rvBusinessCertifications;
    @Bind(R.id.ly_business_certifications)
    LinearLayout lyBusinessCertifications;

    private BusinessExInfoOutput businessExInfoOutput;
    private List<String> mList = new ArrayList<>();
    private BusinessCertificationsAdapter businessCertificationsAdapter;
    private boolean isSetData;//是否绑定过数据

    public static MerchantDetailMerchantFragment newInstance() {
        Bundle args = new Bundle();
        MerchantDetailMerchantFragment fragment = new MerchantDetailMerchantFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchant_detail_merchant;
    }

    @Override
    protected void initView() {
        businessCertificationsAdapter = new BusinessCertificationsAdapter(mContext);
        businessCertificationsAdapter.setOnItemClickListener(this);
        rvBusinessCertifications.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvBusinessCertifications.setHasFixedSize(true);
        rvBusinessCertifications.setAdapter(businessCertificationsAdapter);
    }

    @Override
    protected void initData() {
        setData();
    }

    @Override
    public void lazyLoad() {
        super.lazyLoad();
        setData();
    }

    public void setData() {
        if (businessExInfoOutput == null || isSetData)
            return;
        String userType = businessExInfoOutput.getUserType();
        //UserType (string, optional): 类型 2：工人，3：商户 ,
        if (TextUtils.equals(userType, "2")) {
            tvStartTime.setText("从业年限:" + businessExInfoOutput.getWorkeOfYears());
        } else {
            tvStartTime.setText("成立时间:" + businessExInfoOutput.getWorkeOfYears());
        }
        if (!TextUtils.isEmpty(businessExInfoOutput.getBusinessProperty()))
            tvType.setText("商家性质:" + businessExInfoOutput.getBusinessProperty());
//         * Address (string, optional): 商家地址。 商户显示的是“商户地址”。普通工人显示的是”现居地址“。 ,
//     * ServiceAera (string, optional): 服务范围。 商户显示的是“提供服务距离”。普通工人显示的是”可上门距离“。

        if (!TextUtils.isEmpty(businessExInfoOutput.getAddress()))
            tvServiceAddress.setText("商家地址:" + businessExInfoOutput.getAddress());
        if (!TextUtils.isEmpty(businessExInfoOutput.getServiceAera()))
            tvServiceScope.setText("服务范围:" + businessExInfoOutput.getServiceAera());
        if (businessExInfoOutput.getPhotoUrls() != null && businessExInfoOutput.getPhotoUrls().size() > 0) {
            tvPhotoNumber.setText(businessExInfoOutput.getPhotoUrls().size() + "张");
        } else {
            tvPhotoNumber.setText("0张");
        }

        //商家资质。只有商户有该模块，若部分商家没上传也不显示该模块。
        if (TextUtils.equals(userType, "2"))
            return;
        List<BusinessExInfoOutput.BusinessCertificationsBean> businessCertifications = businessExInfoOutput.getBusinessCertifications();
        if (businessCertifications != null && businessCertifications.size() > 0) {
            lyBusinessCertifications.setVisibility(View.VISIBLE);
            for (int i = 0; i < businessCertifications.size(); i++) {
                mList.add(businessCertifications.get(i).getHqPic());
            }
            businessCertificationsAdapter.setData(businessCertifications);
        } else {
            lyBusinessCertifications.setVisibility(View.GONE);
        }
        isSetData = true;
    }

    public void setRlMerchantOrWorkerDetail(BusinessExInfoOutput businessExInfoOutput) {
        if (businessExInfoOutput == null)
            return;
        this.businessExInfoOutput = businessExInfoOutput;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }


    @OnClick({R.id.rl_merchant_or_worker_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_merchant_or_worker_icon:
                //进入预览界面
                if (businessExInfoOutput == null)
                    return;
                List<BusinessExInfoOutput.PhotoUrlsBean> photoUrls = businessExInfoOutput.getPhotoUrls();
                if (photoUrls == null || photoUrls.size() == 0)
                    return;
                MerchantPhotoPreviewActivity.start(mContext, businessExInfoOutput);
                break;
        }
    }

    @Override
    public void onItemClick(View itemView, int pos) {
        PhotoPreviewDialog previewDialog = new PhotoPreviewDialog.Builder()
                .index(pos)
                .path(mList)
                .build();
        previewDialog.setIndex(pos);
        previewDialog.show(getChildFragmentManager(), null);
    }
}
