package com.homepaas.sls.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;

import butterknife.ButterKnife;

/**
 * Created by JWC on 2017/4/12.
 * 快捷登录
 */

public class QuickLoginFragment extends CommonBaseFragment {

    public static QuickLoginFragment newInstance(){
        QuickLoginFragment quickLoginFragment=new QuickLoginFragment();
        return quickLoginFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quick_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}
