package com.homepaas.sls.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.widget.CustomDialogFragment;

/**
 * on 2016/2/29 0029
 *
 * @author zhudongjie .
 */
public class ProgressFragment extends CustomDialogFragment implements DialogInterface.OnKeyListener {

    public boolean isCancel = false;//弹框在单击返回按钮是否可以进行撤销，默认为不可以进行撤销
    public boolean isFragmentDialogRun = false;
    public FragmentActivity mActivity;

    public static ProgressFragment newInstance(FragmentActivity activity) {
        ProgressFragment fragment = new ProgressFragment();
        fragment.mActivity=activity;
        return fragment;
    }

    public static ProgressFragment newInstance(FragmentActivity activity, boolean isCancel) {
        ProgressFragment fragment = new ProgressFragment();
        fragment.isCancel = isCancel;
        fragment.mActivity=activity;
        return fragment;
    }

    @Override
    public  void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        isFragmentDialogRun = true;
    }

    public void dismissDialog() {
        if (mActivity != null && !mActivity.isFinishing()) {
            isFragmentDialogRun = false;
            super.dismissAllowingStateLoss();
        }
    }

    public Activity getmActivity() {
        return mActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SlsStyleLoadingDialog);
        setCancelable(isCancel);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getDialog().setOnKeyListener(this);
        this.getDialog().getWindow().setDimAmount(0);//设置昏暗度为0
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container);
    }

    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isCancel) {
                dismiss();
                return true;
            } else {
                return false;
            }
        } else {
            //这里注意当不是返回键时需将事件扩散，否则无法处理其他点击事件
            return false;
        }
    }

    public boolean isFragmentDialogRun() {
        return isFragmentDialogRun;
    }
}
