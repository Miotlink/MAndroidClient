package com.homepaas.sls.ui.imchating;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.homepaas.sls.R;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.util.PermissionUtil;
import com.homepaas.sls.util.StaticData;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.Error;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.ContentFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/3/30.
 * 环信登录
 */

public class ImLoginActivity extends CommonBaseActivity {

    private static final String TAG = "ImLoginActivity";
    private ProgressDialog progressDialog;
    private LoginDialogFragment loginDialogFragment;
    private boolean progressShow;
    private String imUserName;
    private String imPwd;
    private CommonAppPreferences commonAppPreferences;
    private ServiceItem serviceItem;
    private PersonalInfo personalInfo;
    private String phoneNumber = "";
    private String nickName = "";
    private String token;
    private static final int REQUEST_CODE_PHONE = 6;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;


    public static void start(Context context, ServiceItem serviceItem) {
        Intent intent = new Intent(context, ImLoginActivity.class);
        intent.putExtra(StaticData.SERVICE_ITEM, serviceItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonAppPreferences = new CommonAppPreferences(this);
        serviceItem = (ServiceItem) getIntent().getSerializableExtra(StaticData.SERVICE_ITEM);
        try {
            personalInfo = personalInfoPDataSource.get();
            token = personalInfoPDataSource.getToken();
            if (personalInfo != null) {
                phoneNumber = personalInfo.getPhoneNumber();
                nickName = personalInfo.getNickName();
            }
        } catch (GetPersistenceDataException e) {
        } catch (InvalidPersistenceDataException e) {
            //没有用户信息，就给空信息
        } finally {
            login();
        }
    }

    @Override
    protected int getLayoutId() {
        return -1;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 登录
     */
    private void login() {
        imUserName = commonAppPreferences.getImUserName();
        imPwd = commonAppPreferences.getImPwd();
        //ChatClient.getInstance().isLoggedInBefore() 可以检测是否已经登录过环信，如果登录过则环信SDK会自动登录，不需要再次调用登录操作
        if (!TextUtils.isEmpty(imUserName) && !TextUtils.isEmpty(imPwd)) {
            if (ChatClient.getInstance().isLoggedInBefore()) {
                progressDialog = getProgressDialog();
                progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
                progressDialog.show();
                toChatActivity();
            } else {
                //随机创建一个用户并登录环信服务器
                login(imUserName, imPwd);
            }
        } else {
            String tem_imUserName = commonAppPreferences.getTemporaryImUserName();
            String tem_imPwd = commonAppPreferences.getTemporaryImPwd();
            if (!TextUtils.isEmpty(tem_imUserName) && !TextUtils.isEmpty(tem_imPwd)) {
                if (ChatClient.getInstance().isLoggedInBefore()) {
                    progressDialog = getProgressDialog();
                    progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
                    progressDialog.show();
                    toChatActivity();
                } else {
                    //随机创建一个用户并登录环信服务器
                    login(tem_imUserName, tem_imPwd);
                }
            } else {
                getDeviceId();
            }
        }
    }

    private void getDeviceId() {
        List<String> group = new ArrayList<>();
        group.add(Manifest.permission_group.PHONE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), REQUEST_CODE_PHONE)) {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            assert tm != null;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String iMEI = tm.getDeviceId();
            createRandomAccountThenLoginChatServer("Android" + iMEI, "123456");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PHONE:
                if (grantResults.length > 0) {
                    for (int gra : grantResults) {
                        if (gra != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                }
                getDeviceId();
                break;

        }
    }


    @Override
    protected void retrieveData() {
        super.retrieveData();
        login();
    }

    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }


    private void createRandomAccountThenLoginChatServer(final String uname, final String upwd) {
        // 自动生成账号,此处每次都随机生成一个账号,为了演示.正式应从自己服务器获取账号
        progressDialog = getProgressDialog();
        progressDialog.setMessage(getString(R.string.system_is_regist));
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        // createAccount to huanxin server
        // if you have a account, this step will ignore
        if (ChatClient.getInstance() != null) {
            ChatClient.getInstance().createAccount(uname, upwd, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "demo register success");
                    commonAppPreferences.setTemporaryImInfo(uname, upwd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //登录环信服务器
                            login(uname, upwd);

                        }
                    });
                }

                @Override
                public void onError(final int errorCode, String error) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            hideProgress();
                            if (errorCode == Error.NETWORK_ERROR) {
                                Toast.makeText(getApplicationContext(), "网络不可用", Toast.LENGTH_SHORT).show();
                            } else if (errorCode == Error.USER_ALREADY_EXIST) {
                                commonAppPreferences.setTemporaryImInfo(uname, upwd);
                                login(uname, upwd);
//                            Toast.makeText(getApplicationContext(), "用户已经存在", Toast.LENGTH_SHORT).show();
                            } else if (errorCode == Error.USER_AUTHENTICATION_FAILED) {
                                Toast.makeText(getApplicationContext(), "无开放注册权限", Toast.LENGTH_SHORT).show();
                            } else if (errorCode == Error.USER_ILLEGAL_ARGUMENT) {
                                Toast.makeText(getApplicationContext(), "用户名非法", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.register_user_fail), Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                    });
                }

                @Override
                public void onProgress(int progress, String status) {

                }
            });
        } else {
            hideProgress();
        }
    }

    /**
     * 登录环信
     *
     * @param uname
     * @param upwd
     */
    private void login(final String uname, final String upwd) {
        if (!ImLoginActivity.this.isFinishing()) {
            //show dialog
            progressShow = true;
            progressDialog = getProgressDialog();
            progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
            if (!ImLoginActivity.this.isFinishing() && !progressDialog.isShowing()) {
                progressDialog.show();
            }
            // login huanxin server
            if (ChatClient.getInstance() != null) {
                ChatClient.getInstance().login(uname, upwd, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "demo login success!");
                        if (!progressShow) {
                            return;
                        }
                        toChatActivity();
                    }

                    @Override
                    public void onError(int code, String error) {
                        Log.e(TAG, "login fail,code:" + code + ",error:" + error);
                        if (!progressShow) {
                            return;
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                hideProgress();
                                Toast.makeText(ImLoginActivity.this,
                                        getResources().getString(R.string.is_contact_customer_failure_seconed),
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
            } else {
                hideProgress();
            }
        }
    }

    public void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) { //check if dialog is showing.
                //get the Context object that was used to great the dialog
                Context context = ((ContextWrapper) progressDialog.getContext()).getBaseContext();
                //if the Context used here was an activity AND it hasn't been finished or destroyed
                //then dismiss it
                if (context instanceof Activity) {
                    if (Build.VERSION.SDK_INT < 17) {
                        if (!((Activity) context).isFinishing())
                            progressDialog.dismiss();
                    } else {
                        if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed())
                            progressDialog.dismiss();
                    }
                } else //if the Context used wasnt an Activity, then dismiss it too
                    progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }

    private void toChatActivity() {
        if (TextUtils.isEmpty(nickName)) {
            nickName = "助家生活Android客户";
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                // 进入主页面
                Bundle bundle = new Bundle();
                bundle.putSerializable(StaticData.SERVICE_ITEM, serviceItem);
                Intent intent = new IntentBuilder(ImLoginActivity.this)
                        .setTargetClass(ChatActivity.class)
                        .setServiceIMNumber(StaticData.IM_SERVICE)
                        .setVisitorInfo(ContentFactory.createVisitorInfo(null)
                                .companyName("huanxin")
                                .name("助家Android客户端")
                                .nickName(nickName)
                                .phone(phoneNumber))
                        .setShowUserNick(true)
                        .setBundle(bundle)
                        .build();
                startActivity(intent);
                finish();
            }
        });
    }


    private ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(ImLoginActivity.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressShow = false;
                }
            });
        }
        return progressDialog;
    }
}
