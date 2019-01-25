package com.homepaas.sls.ui.widget;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Administrator on 2016/12/1.
 */

public class AnimationUtil {
    private static final String TAG = AnimationUtil.class.getSimpleName();

    public static TranslateAnimation moveToViewBottom(float fromX,float toX,float fromY,float toY) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fromX,
                Animation.RELATIVE_TO_SELF, toX, Animation.RELATIVE_TO_SELF,
                fromY, Animation.RELATIVE_TO_SELF, toY);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }


    /**
     * 移动控件
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation(float fromX,float toX,float fromY,float toY) {
        TranslateAnimation mHiddenAction=new TranslateAnimation(fromX, toX, fromY, toY);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }


}
