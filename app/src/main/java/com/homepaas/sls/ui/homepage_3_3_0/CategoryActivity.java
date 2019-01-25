package com.homepaas.sls.ui.homepage_3_3_0;

import android.os.Bundle;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.CategoryComponet;
import com.homepaas.sls.di.component.DaggerCategoryComponet;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

public class CategoryActivity extends CommonBaseActivity implements HasComponent<CategoryComponet>{

    private static final String BD_TAG = "CategoryFragment";
    private CategoryComponet categoryComponet;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initView() {
        String serviceId = getIntent().getStringExtra(Navigator.SERVICE_ID);
        String longtitude = getIntent().getStringExtra(Navigator.LONGTITUDE);
        String latitude = getIntent().getStringExtra(Navigator.LATITUDE);
        CategoryFragment fragment = CategoryFragment.newInstance(serviceId,longtitude,latitude);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment,BD_TAG)
                .commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        categoryComponet = DaggerCategoryComponet.builder()
                .applicationComponent(getApplicationComponent())
                .build();
        categoryComponet.inject(this);
    }

    @Override
    public CategoryComponet getComponent() {
        return categoryComponet;
    }
}
