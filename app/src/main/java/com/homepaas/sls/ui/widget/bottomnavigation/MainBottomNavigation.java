package com.homepaas.sls.ui.widget.bottomnavigation;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.widget.BadgeView;
import com.homepaas.sls.util.DensityUtil;

/**
 * Created by mhy on 2017/8/23.
 */

public class MainBottomNavigation extends RelativeLayout {
    protected OnBottomNavigationSelectedListener mSelectionListener;
    //首页
    public static final int TAB_HOME = 0;
    //附近
    public static final int NEARBY = 1;
    //小秘书
    public static final int MY_TIP = 2;
    //订单
    public static final int ORDER = 3;
    //我的
    public static final int MY = 4;

    private RadioButton mRadioButtonHome;
    private RadioButton mRadioButtonNearby;
    private RadioButton mRadioButtonSecretary;
    private RadioButton mRadioButtonOrder;
    private RadioButton mRadioButtonMy;
    public RadioGroup radioGroup;
    private View redDotLayout;
    private BadgeView badge;


    public MainBottomNavigation(Context context) {
        super(context);
        initView(context);
    }

    public MainBottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MainBottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.main_tab_layout, this);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        mRadioButtonHome = (RadioButton) view.findViewById(R.id.rb1);
        mRadioButtonNearby = (RadioButton) view.findViewById(R.id.rb2);
        mRadioButtonSecretary = (RadioButton) view.findViewById(R.id.rb3);
        mRadioButtonOrder = (RadioButton) view.findViewById(R.id.rb4);
        mRadioButtonMy = (RadioButton) view.findViewById(R.id.rb5);
        redDotLayout = (View) view.findViewById(R.id.red_dot_layout);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == mRadioButtonHome.getId()) {
                    mSelectionListener.onValueSelected(TAB_HOME);
                } else if (checkedId == mRadioButtonNearby.getId()) {
                    mSelectionListener.onValueSelected(NEARBY);
                } else if (checkedId == mRadioButtonSecretary.getId()) {
                    mSelectionListener.onValueSelected(MY_TIP);
                } else if (checkedId == mRadioButtonOrder.getId()) {
                    mSelectionListener.onValueSelected(ORDER);
                } else if (checkedId == mRadioButtonMy.getId()) {
                    mSelectionListener.onValueSelected(MY);
                }
            }
        });


        badge = new BadgeView(context);
        badge.setTargetView(redDotLayout);
        badge.setBadgeCount(1);
        badge.setWidth(DensityUtil.dip2px(context, 8));
        badge.setHeight(DensityUtil.dip2px(context, 8));
        badge.setTextColor(Color.TRANSPARENT); // 文本颜色
        badge.setBadgeMargin(0, 0, 40, 0);
        setTipRedDotShow(false);
    }

    public void setBottomNavigationSelectedListener(OnBottomNavigationSelectedListener l) {
        this.mSelectionListener = l;
    }


    public void setBottomNavigationClick(int index) {
        switch (index) {
            case TAB_HOME:
                mRadioButtonHome.setChecked(true);
                break;
            case NEARBY:
                mRadioButtonNearby.setChecked(true);
                break;
            case MY_TIP:
                mRadioButtonSecretary.setChecked(true);
                break;
            case ORDER:
                mRadioButtonOrder.setChecked(true);
                break;
            case MY:
                mRadioButtonOrder.setChecked(true);
                break;
        }
    }

    public RadioButton getmRadioButtonHome() {
        return mRadioButtonHome;
    }

    public RadioButton getmRadioButtonNearby() {
        return mRadioButtonNearby;
    }

    public RadioButton getmRadioButtonSecretary() {
        return mRadioButtonSecretary;
    }

    public RadioButton getmRadioButtonOrder() {
        return mRadioButtonOrder;
    }

    public RadioButton getmRadioButtonMy() {
        return mRadioButtonMy;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setTipRedDotShow(boolean isShow) {
        badge.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

}
