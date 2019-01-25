package com.homepaas.sls.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * 2017/7/25.
 */
public class ToastUtil {
    public Context context;
    private Toast result;

    public ToastUtil(Context context) {
        this.context = context;
    }

    public void showToast(CharSequence text) {
        if (!TextUtils.isEmpty(text))
            makeText(text, Toast.LENGTH_SHORT);
    }

    public void showToast(int msgRes) {
        makeText(msgRes, Toast.LENGTH_SHORT);
    }

    private void makeText(CharSequence text, int duration) {
        if (result == null) {
            result = Toast.makeText(context, text, duration);
        } else {
            result.setText(text);
        }
        result.show();
    }


    private void makeText(int red, int duration) {
        if (result == null) {
            result = Toast.makeText(context, red, duration);
        } else {
            result.setText(red);
        }
        result.show();
    }

}
