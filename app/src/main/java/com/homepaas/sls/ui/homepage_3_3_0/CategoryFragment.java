package com.homepaas.sls.ui.homepage_3_3_0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerCategoryComponet;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.mvp.presenter.CategoryPresenter;
import com.homepaas.sls.mvp.view.CategoryView;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.homepage_3_3_0.adapter.CategoryAdapter;
import com.homepaas.sls.ui.imchating.ImLoginActivity;
import com.homepaas.sls.ui.order.servicemerchant.ServiceMerchantActivity;
import com.homepaas.sls.ui.widget.BlankDecoration;
import com.homepaas.sls.ui.widget.networkerrorview.CommonNetWorkErrorViewReplacer;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link CommonBaseFragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends CommonBaseFragment implements CategoryView, CategoryAdapter.OnShowDetailListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SERVICE_ID = "serviceId";
    private static final String LONGTITUDE = "longtitude";
    private static final String LATITUDE = "latitude";
    private static final int REQUEST_LOGIN = 1;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.call_secretary)
    ImageView callSecretary;
    @Bind(R.id.unreadIcon)
    View unreadIcon;
    @Bind(R.id.message)
    FrameLayout message;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Inject
    CategoryPresenter categoryPresenter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private CommonNetWorkErrorViewReplacer commonNetWorkErrorViewReplacer;


    // TODO: Rename and change types of parameters
    private String serviceId;
    private String longtitude;
    private String latitude;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * @param serviceId
     * @param longtitude
     * @param latitude
     * @return
     */
    public static CategoryFragment newInstance(String serviceId, String longtitude, String latitude) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(SERVICE_ID, serviceId);
        args.putString(LONGTITUDE, longtitude);
        args.putString(LATITUDE, latitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            serviceId = getArguments().getString(SERVICE_ID);
            longtitude = getArguments().getString(LONGTITUDE);
            latitude = getArguments().getString(LATITUDE);
        }
        categoryPresenter.setCategoryView(this);
    }

    @OnClick({R.id.back, R.id.message})
    public void opreate(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.message:
                //进入客服页面
                ImLoginActivity.start(getActivity(),null);
                break;
        }
    }

    @Override
    protected void initializeInjector() {
//        getComponent(CategoryComponet.class)
//                .inject(this);
        DaggerCategoryComponet.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);

    }

    private boolean isShow = false;

    @Override
    public void showError(Throwable e) {
        if (!(e instanceof AuthException)) {
            super.showError(e);
            if (categoryAdapter==null||categoryAdapter.getItemCount()==0)
                commonNetWorkErrorViewReplacer.showErrorLayout();
        } else {
            if (!isShow) {
                mNavigator.login(CategoryFragment.this, REQUEST_LOGIN);
                isShow = true;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_LOGIN:
                    if (data != null && data.getBooleanExtra("Status",false) )
                        categoryPresenter.getQueryService(longtitude, latitude, serviceId);
                    break;
                default:
                    break;
            }

        }
    }

    CategoryAdapter categoryAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView() {
        recyclerView.addItemDecoration(new BlankDecoration(getContext()));
        commonNetWorkErrorViewReplacer=new CommonNetWorkErrorViewReplacer(getActivity(),recyclerView , new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                categoryPresenter.getQueryService(longtitude, latitude, serviceId);
            }
        });
        categoryPresenter.getQueryService(longtitude, latitude, serviceId);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void render(List<ServiceItem> serviceItems) {
        commonNetWorkErrorViewReplacer.showOriginalLayout();
        if (serviceItems != null && serviceItems.size() > 0){
            categoryAdapter = new CategoryAdapter(serviceItems);
            categoryAdapter.setOnShowDetailListener(this);
            recyclerView.setAdapter(categoryAdapter);
        }
//        categoryAdapter.refresh(serviceItems);
    }

    @Override
    public void renderTitle(String name) {
        isShow = false;
        title.setText(name);
    }

    @Override
    public void showDetail(ServiceItem serviceItem) {
        //isFlagMerchantService; 标记详情页是否出现商家的tab页面 0：不出现 1:出现
        if (TextUtils.isEmpty(serviceItem.getIsFlagMerchantService()) || serviceItem.getIsFlagMerchantService().equals("0")) {
            //跳转详情页面
            DetailWebActivity.start(mContext, serviceItem);
        } else {
            //跳转到非标订单详情页面
            ServiceMerchantActivity.start(mContext, serviceItem);
        }
//        进入详情页面
//        DetailWebActivity.start(getContext(), serviceItem);
    }
}
