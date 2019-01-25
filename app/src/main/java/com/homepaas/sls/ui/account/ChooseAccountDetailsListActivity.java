package com.homepaas.sls.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.homepaas.sls.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/29.
 */

public class ChooseAccountDetailsListActivity extends Activity {

    @Bind(R.id.back_ll)
    LinearLayout backLl;
    @Bind(R.id.dismiss)
    LinearLayout dismiss;
    @Bind(R.id.account_type_all)
    FrameLayout accountTypeAll;
    @Bind(R.id.wallet_choose_all)
    ImageView walletChooseAll;
    @Bind(R.id.account_type_income)
    FrameLayout accountTypeIncome;
    @Bind(R.id.wallet_choose_income)
    ImageView walletChooseIncome;
    @Bind(R.id.account_type_expend)
    FrameLayout accountTypeExpend;
    @Bind(R.id.wallet_choose_expend)
    ImageView walletChooseExpend;

    private String isMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account_details_list);
        ButterKnife.bind(this);
        isMinus = getIntent().getStringExtra(AccountListFragment.CHOOSE_TYPE);
        initView(isMinus);
    }

    private void initView(String type) {
        walletChooseAll.setVisibility(TextUtils.equals("0", type) ? View.VISIBLE : View.GONE);
        walletChooseIncome.setVisibility(TextUtils.equals("1", type) ? View.VISIBLE : View.GONE);
        walletChooseExpend.setVisibility(TextUtils.equals("2", type) ? View.VISIBLE : View.GONE);
    }


    @OnClick({R.id.back_ll, R.id.dismiss, R.id.account_type_all, R.id.account_type_income, R.id.account_type_expend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                setResultBack(AccountListFragment.GO_BACK);
                break;
            case R.id.dismiss:
                finish();
                overridePendingTransition(R.anim.pop_window_down,0);
                break;
            case R.id.account_type_all:
                setResultBack(AccountListFragment.CHOOSE_TYPE_ALL);
                break;
            case R.id.account_type_income:
                setResultBack(AccountListFragment.CHOOSE_TYPE_INCOME);
                break;
            case R.id.account_type_expend:
                setResultBack(AccountListFragment.CHOOSE_TYPE_EXPEND);
                break;
            default:
        }
    }

    private void setResultBack(String isMinus) {
        Intent intent = new Intent();
        intent.putExtra(AccountListFragment.CHOOSE_TYPE, isMinus);
        setResult(Activity.RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.pop_window_down,0);
    }
}
