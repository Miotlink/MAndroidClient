package com.homepaas.sls.ui.widget;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.lang.reflect.Field;

/**
 * Created by mhy on 2018/1/9.
 *
 * 解决 java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState  错误
 */

public class CustomDialogFragment extends DialogFragment {

    private static final Class clz = DialogFragment.class;

    public void showAllowingStateLoss(FragmentManager manager, String tag) {
        //mDismissed = false;
        try {
            Field dismissed = clz.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(this, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //mShownByMe = true;
        try {
            Field shown = clz.getDeclaredField("mShownByMe");
            shown.setAccessible(true);
            shown.set(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

}
