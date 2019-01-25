package com.homepaas.sls.util.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.homepaas.sls.R;

/**
 * Created by mhy on 2017/9/27.
 */

public class DialogUtils {
    /**
     * 电话未接通弹框
     */

    public static void showCallPhoneBusy(Context context, final View.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_call_phone_busy, null);
        Button button = (Button) mView.findViewById(R.id.btn_phone_error);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag("0");
                //号码错误
                onClickListener.onClick(v);
            }
        });
        Button button2 = (Button) mView.findViewById(R.id.btn_phone_unmanned);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag("1");
                //无人接听
                onClickListener.onClick(v);
            }
        });
        builder.setView(mView);
        builder.create().show();
    }
}
