package com.homepaas.sls.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.MainActivity;

import java.util.List;

/**
 * Created by Sherily on 2016/5/12.
 */
public class ViewPagerAdapter extends PagerAdapter {

    //引导页面列表
    private List<View> views;
    private Activity activity;

    //pref文件记录首次载入标志位
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    public ViewPagerAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

    /**
     *
     * @return 返回当前viewpage页面数
     */
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        } else  return 0;
    }

    /**
     * 判断是否由对象生成的页面
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 销毁position位置的页面
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(views.get(position));
    }

    /**
     * 初始化position位置的页面
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        if (position == 0){
            final TextView skip = (TextView) container.findViewById(R.id.skip);
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳过引导
                    setGuided();
                    goHome();
                }
            });
        }
        if (position == views.size() - 1) {
            final Button btnStartMainActivity = (Button) container.findViewById(R.id.btnStartMainActivity);
            btnStartMainActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 设置已经引导

                    setGuided();
                    goHome();
                }
            });
        }
        return views.get(position);
    }

    /**
     * 跳转到主页面
     */
    private void goHome() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        //currentAction.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        activity.finish();
    }

    /**
     * 跳转到引导页面
     */
    private void setGuided() {
        SharedPreferences preferences = activity.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //走完一遍引导页面后设置标志为false
        editor.putBoolean("isFirstIn", false);
        editor.commit();
    }
}
