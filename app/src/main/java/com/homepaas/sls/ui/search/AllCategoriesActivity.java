package com.homepaas.sls.ui.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.mvp.presenter.SearchService.AllCategoriesPresenter;
import com.homepaas.sls.mvp.view.SearchService.AllCategoriesView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.homepage_3_3_0.DetailWebActivity;
import com.homepaas.sls.ui.order.servicemerchant.ServiceMerchantActivity;
import com.homepaas.sls.ui.search.adapter.AllCategoriesItemAdapter;
import com.homepaas.sls.ui.search.adapter.AllCategoriesMenuAdapter;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.ui.widget.ReboundScrollView;
import com.homepaas.sls.ui.widget.SimpleItemDecoration;
import com.homepaas.sls.ui.widget.networkerrorview.CommonNetWorkErrorViewReplacer;
import com.homepaas.sls.util.StaticData;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/3/21.
 * 全部分类
 */

public class AllCategoriesActivity extends CommonBaseActivity implements AllCategoriesMenuAdapter.OnMenuItemClickListener, AllCategoriesItemAdapter.OnItemClickListener, AllCategoriesView {
    @Bind(R.id.menu_recyclerView)
    RecyclerView menuRecyclerView;
    @Bind(R.id.item_recyclerView)
    RecyclerView itemRecyclerView;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.scroll_view)
    ReboundScrollView scrollView;

    private CommonAppPreferences commonAppPreferences;
    static Gson gson = new Gson();
    private ServiceItemListEntity serviceItemListEntity;
    //网络错误布局替换工具
    private CommonNetWorkErrorViewReplacer commonNetWorkErrorViewReplacer;
    private AllCategoriesMenuAdapter allCategoriesMenuAdapter;
    private AllCategoriesItemAdapter allCategoriesItemAdapter;

    private String mLatitude;
    private String mLongitude;
    private int lastPosition = 0;


    @Inject
    AllCategoriesPresenter allCategoriesPresenter;

    private int mIndex = 0;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean move = false;

    public static void start(Context context, String latitude, String longitude) {
        Intent intent = new Intent(context, AllCategoriesActivity.class);
        intent.putExtra(StaticData.LATITUDE, latitude);
        intent.putExtra(StaticData.LONGITUDE, longitude);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_categories;
    }

    @Override
    protected void initView() {
        commonAppPreferences = new CommonAppPreferences(this);
        mLatitude = getIntent().getStringExtra(StaticData.LATITUDE);
        mLongitude = getIntent().getStringExtra(StaticData.LONGITUDE);
        allCategoriesMenuAdapter = new AllCategoriesMenuAdapter(this);
        allCategoriesMenuAdapter.setOnMenuItemClickListener(this);
        menuRecyclerView.addItemDecoration(new SimpleItemDecoration(this, SimpleItemDecoration.VERTICAL_LIST));
        menuRecyclerView.setAdapter(allCategoriesMenuAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        allCategoriesItemAdapter = new AllCategoriesItemAdapter(this);
        allCategoriesItemAdapter.setOnItemClickListener(this);
        itemRecyclerView.setLayoutManager(mLinearLayoutManager);
        itemRecyclerView.setAdapter(allCategoriesItemAdapter);
        itemRecyclerView.addOnScrollListener(new RecyclerViewListener());
        allCategoriesPresenter.setAllCategoriesView(this);
        commonNetWorkErrorViewReplacer=new CommonNetWorkErrorViewReplacer(this,findViewById(R.id.rl_content) , new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                allCategoriesPresenter.getAllService(null, null);
            }
        });

        //全部分类数据通过sp进行缓存
        String allServies=commonAppPreferences.getServieList();
        String cacheTime=commonAppPreferences.getCacheTime();
        if(TextUtils.isEmpty(allServies)||TextUtils.isEmpty(cacheTime)){
            allCategoriesPresenter.getAllService(null, null);
        }else {
            serviceItemListEntity=gson.fromJson(allServies,ServiceItemListEntity.class);
            if(serviceItemListEntity!=null){
                allCategoriesMenuAdapter.setMenuList(serviceItemListEntity.getItems());
                allCategoriesItemAdapter.setItemList(serviceItemListEntity.getItems());
            }
            //不是同一天，重新获取一遍数据
            if(!cacheTime.equals(TimeUtils.getYearMonth())){
                allCategoriesPresenter.getAllService(null, null);
            }
        }
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back})
    void back() {
        finish();
    }

    /**
     * 右边的recyclerView点击事件
     *
     * @param serviceItem
     */
    @Override
    public void itemClickListener(ServiceItem serviceItem) {
//        CommonPlaceOrderActivity.start(this,serviceItem.getServiceTypeId(),serviceItem.getServiceName());
        if (serviceItem != null && !TextUtils.isEmpty(serviceItem.getDetailUrl())) {
            //isFlagMerchantService; 标记详情页是否出现商家的tab页面 0：不出现 1:出现
            if (TextUtils.isEmpty(serviceItem.getIsFlagMerchantService()) || serviceItem.getIsFlagMerchantService().equals("0")) {
                //跳转详情页面
                DetailWebActivity.start(mContext, serviceItem);
            } else {
                //跳转到非标订单详情页面
                ServiceMerchantActivity.start(mContext, serviceItem);
            }
//            DetailWebActivity.start(this, serviceItem);
        }
    }

    /**
     * 左边的recyclerView点击事件
     *
     * @param menuPosition
     */
    @Override
    public void menuItemClickListener(int menuPosition) {
        allCategoriesMenuAdapter.setPosittion(lastPosition, menuPosition);
//        move(menuPosition * 2);
        lastPosition = menuPosition;
        if(itemRecyclerView.getChildAt(menuPosition * 2)!=null) {
            int scrollY = itemRecyclerView.getChildAt(menuPosition * 2).getTop();
            scrollView.scrollTo(0, scrollY);
        }
    }


    /**
     * 移动到指定位置
     *
     * @param n
     */
    private void move(int n) {
        if (n < 0 || n >= allCategoriesItemAdapter.getItemCount()) {
            Toast.makeText(this, "超出范围了", Toast.LENGTH_SHORT).show();
            return;
        }
        mIndex = n;
        itemRecyclerView.stopScroll();
        smoothMoveToPosition(n);
    }

    private void smoothMoveToPosition(int n) {

        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            itemRecyclerView.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = itemRecyclerView.getChildAt(n - firstItem).getTop();
            itemRecyclerView.smoothScrollBy(0, top);
        } else {
            itemRecyclerView.smoothScrollToPosition(n);
            move = true;
        }
    }


    /**
     * 获取到数据
     */
    @Override
    public void render(ServiceItemListEntity serviceItemListEntity) {
        commonNetWorkErrorViewReplacer.showOriginalLayout();
        if (serviceItemListEntity != null) {
            String allServices=gson.toJson(serviceItemListEntity);
            commonAppPreferences.setAllServieList(allServices, TimeUtils.getYearMonth());
            allCategoriesMenuAdapter.setMenuList(serviceItemListEntity.getItems());
            allCategoriesItemAdapter.setItemList(serviceItemListEntity.getItems());
        }
    }

    @Override
    public void showError(Throwable t) {
        super.showError(t);
        if (allCategoriesItemAdapter==null||allCategoriesItemAdapter.getItemCount()==0)
            commonNetWorkErrorViewReplacer.showErrorLayout();
    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < itemRecyclerView.getChildCount()) {
                    int top = itemRecyclerView.getChildAt(n).getTop();
                    itemRecyclerView.smoothScrollBy(0, top);
                }

            }
        }

    }
}
