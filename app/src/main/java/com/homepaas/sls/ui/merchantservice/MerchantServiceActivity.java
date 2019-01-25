package com.homepaas.sls.ui.merchantservice;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MerchantServiceActivity extends CommonBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.collapsingbar)
    CollapsingToolbarLayout collapsingbar;
    @Bind(R.id.gallery)
    ViewPager gallery;
    @Bind(R.id.headerView)
    LinearLayout headerView;

    private boolean hasSet = false;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_merchant_service;
    }

    @Override
    protected void initView() {

        //FIXME OH GOD!!
        mockData();
        setUpGallery();
    }

    @Override
    protected void initData() {

    }

    private void setUpGallery() {
        int[] imgs = new int[]{R.mipmap.authenticate_business_license, R.mipmap.authenticate_worker, R.mipmap.authenticate_tool, R.mipmap.authenticate_tax, R.mipmap.authenticate_ticket};
        List<View> views = new ArrayList<>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgs[i]);
            views.add(imageView);
        }
        gallery.setCurrentItem(Integer.MAX_VALUE / 2);
        GalleryAdapter adapter = new GalleryAdapter(views);
        gallery.setAdapter(adapter);

    }

    private void mockData() {
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        collapsingbar.setTitleEnabled(false);
        collapsingbar.setTitle(null);
        collapsingbar.setExpandedTitleColor(0xccc);
        CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        params.height = getToolbarHeight(this) * 2;
        toolbar.setLayoutParams(params);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(CommentFragment.newInstance());
        fragments.add(ServiceDetailFragment.newInstance());
        List<String> titles = new ArrayList<>();
        titles.add("AAA");
        titles.add("BBB");
        PageAdapter adapter = new PageAdapter(fragments, titles, getSupportFragmentManager());

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!hasSet) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) headerView.getLayoutParams();
            layoutParams.topMargin += toolbar.getHeight() / 2;
            headerView.requestLayout();
            hasSet = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.merchant_service_menu, menu);
        return true;
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
