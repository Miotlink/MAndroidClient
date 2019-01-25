package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.AccountModule;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.account.AccountActivity;
import com.homepaas.sls.ui.account.AccountBalanceFragment;
import com.homepaas.sls.ui.account.AccountDetailsActivity;
import com.homepaas.sls.ui.account.AccountDetailsFragment;
import com.homepaas.sls.ui.account.AccountListActivity;
import com.homepaas.sls.ui.account.AccountListFragment;
import com.homepaas.sls.ui.account.PayDetailFragment;
import com.homepaas.sls.ui.account.WalletActivity;
import com.homepaas.sls.ui.account.WalletFragment;

import dagger.Component;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules ={CameraModule.class,AccountModule.class} )
public interface AccountComponent {

    void inject(AccountBalanceFragment fragment);
    void inject(AccountActivity activity);

    void inject(PayDetailFragment fragment);

    void inject(WalletFragment fragment);
    void inject(WalletActivity activity);

    void inject(AccountListActivity activity);
    void inject(AccountListFragment fragment);

    void inject(AccountDetailsActivity activity);
    void inject(AccountDetailsFragment fragment);

}
