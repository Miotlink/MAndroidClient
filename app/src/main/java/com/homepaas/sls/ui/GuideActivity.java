package com.homepaas.sls.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.adapter.ViewPagerAdapter;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.GuideVideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class GuideActivity extends CommonBaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    //pref文件记录首次载入标志位
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    @Bind(R.id.viewPage)
    ViewPager viewPage;
    @Bind(R.id.viewDot)
    LinearLayout viewDot;
    @Bind(R.id.go)
    TextView tvGo;

    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private List<GuideVideoView> videoViews = new ArrayList<>();

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    private int scrollState = ViewPager.SCROLL_STATE_IDLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState);
 }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        initViews();
        initDots();
        tvGo.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        for (GuideVideoView videoView: videoViews) {
            videoView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (GuideVideoView videoView: videoViews) {
            videoView.onResume();
        }
    }

    /**
     * 初始化引导页面
     */
    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<>();

        //初始化引导页面列表
        View view;

        view = inflater.inflate(R.layout.guide_page_1, null);
        //保存视频View
        GuideVideoView videoView = (GuideVideoView) view.findViewById(R.id.video_view);
        videoView.setVideoId(R.raw.guide_1);
        videoView.setOnVideoComletionListener(new GuideVideoView.OnVideoCompletionListener() {
            @Override
            public void onCompletion() {
                if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
                    //在用户没有滑动时第一页引导完毕后跳到第二页
                    if (viewPage.getCurrentItem() == 0) {
                        viewPage.setCurrentItem(1);
                    }
                }
            }
        });
        videoView.play();
        videoViews.add(videoView);
        views.add(view);

//        views.add(inflater.inflate(R.layout.guide_page_2, null));
//        views.add(inflater.inflate(R.layout.guide_page_3, null));

        view = inflater.inflate(R.layout.guide_page_4, null);
        //保存视频View
        videoView = (GuideVideoView) view.findViewById(R.id.video_view);
        videoView.setVideoId(R.raw.guide_2);
        videoView.setOnVideoComletionListener(new GuideVideoView.OnVideoCompletionListener() {
            @Override
            public void onCompletion() {
                if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
                    if (viewPage.getCurrentItem() == 1) {
                        setGuided();
                        goHome();
                    }
                }
            }
        });
        videoViews.add(videoView);
        views.add(view);

        //初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);
        viewPage.setAdapter(vpAdapter);
        viewPage.addOnPageChangeListener(this);
    }

    /**
     * 初始化引导页底部圆点图标
     */
    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) viewDot.getChildAt(i);
            dots[i].setEnabled(true);//初始化所有的图标都为圆点
            dots[i].setOnClickListener(this);//给每个底部小点设置监听器
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为，即选中状态

    }

    /**
     * 设置当前位置页面
     * @param position
     */
    private void setCurrentView(int position) {
        if (position<0 || position >= views.size()) {
            return;
        }
        viewPage.setCurrentItem(position);
    }

    /**
     * 设置当前位置页面的图标状态
     * @param position
     */
    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1 || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex = position;
    }
    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {
//        if (position == 2){
//            viewDot.setVisibility(View.GONE);
//        } else {
//            viewDot.setVisibility(View.VISIBLE);
//        }
        setCurrentDot(position);
        if (position == views.size() - 1) {
            tvGo.setVisibility(View.VISIBLE);
        } else {
            tvGo.setVisibility(View.INVISIBLE);
        }
        videoViews.get(position).play();
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {
        scrollState = state;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurrentView(position);
        setCurrentDot(position);
    }

    @OnClick(R.id.go)
    public void goClick() {
        setGuided();
        goHome();
    }

    /**
     * 跳转到主页面
     */
    private void goHome() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);
        //currentAction.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        finish();
    }

    /**
     * 跳转到引导页面
     */
    private void setGuided() {
        SharedPreferences preferences = getContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //走完一遍引导页面后设置标志为false
        editor.putBoolean("isFirstIn", false);
        editor.apply();
    }

}
