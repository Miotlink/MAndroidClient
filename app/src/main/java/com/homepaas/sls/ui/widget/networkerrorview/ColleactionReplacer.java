package com.homepaas.sls.ui.widget.networkerrorview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ethanhua.skeleton.ViewReplacer;
import com.homepaas.sls.R;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;

/**
 * Created by mhy on 2017/8/12.
 * 网络错误并且没有缓存提示view 占位布局封装
 */

public class ColleactionReplacer {
    private Context mContext;
    private View originalView;
    private View netErrorView;//网络错误并且没有缓存提示view
    private View emptyView;//后台返回数据为空时提示
    private ViewReplacer mViewReplacer;
    private TextView tvEmptyView;

    public ColleactionReplacer(Context context, View originalView, RxBindingViewClickHelper.OnRxClick onRxClick) {
        this.mContext = context;
        this.originalView = originalView;
        //参数为你需要占位替换的view
        mViewReplacer = new ViewReplacer(originalView);
        netErrorView = LayoutInflater.from(context).inflate(R.layout.select_worker_service_network_error_hint, null);

        emptyView = LayoutInflater.from(context).inflate(R.layout.empty_colleaction, null);
        tvEmptyView = (TextView) emptyView.findViewById(R.id.no_collection_explanation);
        Button btnView = (Button) netErrorView.findViewById(R.id.btn_refresh);
        RxBindingViewClickHelper.onClick(btnView, onRxClick);
    }

    public void showErrorLayout() {
        mViewReplacer.replace(netErrorView);
    }

    public void showOriginalLayout() {
        //回复最初时初始化的view
        mViewReplacer.restore();
    }


    public void showEmptyView() {
        mViewReplacer.replace(emptyView);
    }

    public void showEmptyView(String emptyHint) {
        if (!TextUtils.isEmpty(emptyHint))
            tvEmptyView.setText(emptyHint);
        mViewReplacer.replace(emptyView);
    }

    public void showEmptyView(int emptyHintRes) {
        tvEmptyView.setText(mContext.getResources().getString(emptyHintRes));
        mViewReplacer.replace(emptyView);
    }

    public void showLoadLayout(View view) {
        mViewReplacer.replace(view);
    }

    public void showLoadLayout(int res) {
        mViewReplacer.replace(res);
    }
}
