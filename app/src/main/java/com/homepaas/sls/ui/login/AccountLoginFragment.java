package com.homepaas.sls.ui.login;

import android.os.Bundle;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;

/**
 * Created by JWC on 2017/4/12.
 * 账号密码登录
 */

public class AccountLoginFragment extends CommonBaseFragment {

    public static AccountLoginFragment newInstance(){
        AccountLoginFragment accountLoginFragment=new AccountLoginFragment();
        return accountLoginFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
