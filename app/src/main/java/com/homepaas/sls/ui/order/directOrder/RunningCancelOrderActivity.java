package com.homepaas.sls.ui.order.directOrder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.ui.widget.ReboundScrollView;
import com.homepaas.sls.util.StaticData;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/7/7.
 */

public class RunningCancelOrderActivity extends CommonBaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_rel)
    RelativeLayout titleRel;
    @Bind(R.id.cancel_msg)
    TextView cancelMsg;
    @Bind(R.id.contact_customer_service)
    TextView contactCustomerService;
    @Bind(R.id.scroll_view)
    ReboundScrollView scrollView;

    private String cancelMsgStr;

    public static void start(Context context, String cancelMsg) {
        Intent intent = new Intent(context, RunningCancelOrderActivity.class);
        intent.putExtra(StaticData.CANCEL_MEG, cancelMsg);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_running_cancel_order;
    }

    @Override
    protected void initView() {
        cancelMsgStr = getIntent().getStringExtra(StaticData.CANCEL_MEG);
        cancelMsg.setText(cancelMsgStr);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.back, R.id.contact_customer_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.contact_customer_service:
                dial("4008-262-056", "联系客服");
                break;
            default:
        }
    }

    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;
    private String mCallingPhone;
    private String mTitle;

    // 拨打电话
    private void dial(String phone, String title) {
        boolean ret = requestRuntimePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG}, REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        if (ret) {
            NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title);
            serviceFragment.show(getSupportFragmentManager(), null);
        } else {
            mCallingPhone = phone;
            mTitle = title;
            requestRuntimePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG}, REQUEST_PERMISSION_CALL_AND_CALL_LOG);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CALL_AND_CALL_LOG:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                dial(mCallingPhone, mTitle);
                break;
        }
    }
}
