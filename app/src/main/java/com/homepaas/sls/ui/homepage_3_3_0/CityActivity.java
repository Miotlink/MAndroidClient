package com.homepaas.sls.ui.homepage_3_3_0;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.di.component.DaggerCityComponent;
import com.homepaas.sls.domain.entity.CityDetail;
import com.homepaas.sls.domain.entity.CityInfoEntity;
import com.homepaas.sls.domain.entity.CityListEntity;
import com.homepaas.sls.mvp.presenter.city.CityPresenter;
import com.homepaas.sls.mvp.view.city.CityView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.homepage_3_3_0.adapter.CityListAdapter;
import com.homepaas.sls.ui.homepage_3_3_0.decroation.DividerItemDecoration;
import com.homepaas.sls.ui.homepage_3_3_0.decroation.TitleItemDecoration;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.ui.widget.customview.IndexBar;
import com.homepaas.sls.util.StaticData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class CityActivity extends CommonBaseActivity implements CityListAdapter.OnChangeCity, CityView, IndexBar.onIndexPressedListener {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.current_city)
    TextView currentCity;
    @Bind(R.id.current_location)
    LinearLayout currentLocation;

    @Bind(R.id.indexBar)
    IndexBar indexBar;
    @Bind(R.id.city_list_view)
    RecyclerView cityListView;
    @Bind(R.id.tv_index_txt)
    TextView tvIndexTxt;
    private LinearLayoutManager linearLayoutManager;
    private CityListAdapter cityListAdapter;
    private List<CityDetail> cityDetailList;
    private CityDetail locationCityDetail;
    static Gson gson = new Gson();

    @Inject
    CityPresenter cityPresenter;
    private CommonAppPreferences commonAppPreferences;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_city;
    }

    @Override
    protected void initView() {
        cityPresenter.setCityView(this);
        setSupportActionBar(toolbar);
        commonAppPreferences = new CommonAppPreferences(this);
        locationCityDetail = (CityDetail) getIntent().getSerializableExtra(StaticData.LOCATION_ADDRESS);
        if (locationCityDetail != null && !TextUtils.isEmpty(locationCityDetail.getCityName())) {
            currentCity.setText(locationCityDetail.getCityName());
        } else {
            currentCity.setText(getResources().getString(R.string.location_error));
        }
        linearLayoutManager = new LinearLayoutManager(this);
        cityListAdapter = new CityListAdapter();
        cityListAdapter.setOnChangeCity(this);
        cityListView.setLayoutManager(linearLayoutManager);
        cityListView.setAdapter(cityListAdapter);

        //全部分类数据通过sp进行缓存
        String cityList = commonAppPreferences.getCityList();
        String cacheTime = commonAppPreferences.getCacheTime();
        if (TextUtils.isEmpty(cityList) || TextUtils.isEmpty(cacheTime)) {
            cityPresenter.getCityList();
        } else {
            CityListEntity cityListEntity = gson.fromJson(cityList, CityListEntity.class);
            render(cityListEntity);
            //不是同一天，重新获取一遍数据
            if (!cacheTime.equals(TimeUtils.getYearMonth())) {
                cityPresenter.getCityList();
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void initializeInjector() {
        DaggerCityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public void onChange(CityDetail cityDetail) {
        //将城市信息返回首页
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(StaticData.SELECTED_CITY, cityDetail);
        intent.putExtra(StaticData.SELECTED_CITY, bundle);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }


    @Override
    public void render(CityListEntity cityListEntity) {
        if (cityListEntity != null && cityListEntity.getCityInfoEntities() != null && !cityListEntity.getCityInfoEntities().isEmpty()) {
            cityDetailList = new ArrayList<>();
            for (CityInfoEntity cityInfoEntity : cityListEntity.getCityInfoEntities()) {
                if (cityInfoEntity != null && cityInfoEntity.getCityDetails() != null && !cityInfoEntity.getCityDetails().isEmpty()) {
                    cityDetailList.addAll(cityInfoEntity.getCityDetails());
                }
            }
            cityListView.addItemDecoration(new TitleItemDecoration(this, cityDetailList));
            cityListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            indexBar.setmOnIndexPressedListener(this);
            indexBar.setNeedRealIndex(true)//设置需要真实的索引
                    .setmLayoutManager(linearLayoutManager)//设置RecyclerView的LayoutManager
                    .setmSourceDatas(cityDetailList);//设置数据源
            cityListAdapter.setData(cityDetailList);
            //SP缓存json
            String cityList = gson.toJson(cityListEntity);
            commonAppPreferences.setCityList(cityList, TimeUtils.getYearMonth());
        }
    }

    @OnClick({R.id.current_city})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.current_city:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(StaticData.SELECTED_CITY, locationCityDetail);
                intent.putExtra(StaticData.SELECTED_CITY, bundle);
                setResult(RESULT_OK, intent);
                onBackPressed();
                break;
            default:
        }
    }

    //右侧筛选菜单按下某个时
    @Override
    public void onIndexPressed(int index, String text) {
        tvIndexTxt.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(text))
            tvIndexTxt.setText(text);
    }

    //右侧筛选菜单松开时
    @Override
    public void onMotionEventEnd() {
        tvIndexTxt.setVisibility(View.GONE);
    }
}
