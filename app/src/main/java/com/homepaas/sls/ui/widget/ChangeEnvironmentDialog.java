package com.homepaas.sls.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homepaas.sls.R;
import com.homepaas.sls.data.exception.PersistDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/1.
 */
public class ChangeEnvironmentDialog extends Dialog {
    @Bind(R.id.release)
    TextView release;
    @Bind(R.id.debug)
    TextView debug;
    @Bind(R.id.developer)
    TextView developer;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.outer_layout)
    LinearLayout outerLayout;
    private Context context;
    private RestApiHelper restApiHelper;
    private PersonalInfoPDataSource personalInfoPDataSource;

    @OnClick(R.id.cancel)
    public void cancel() {
        dismiss();
    }
    @OnClick({R.id.release, R.id.debug, R.id.developer})
    public void changeEnviroment(View view) {
        switch (view.getId()){
            case R.id.release:
                try {
                    personalInfoPDataSource.clearToken();
                } catch (PersistDataException e) {
                    e.printStackTrace();
                }
                restApiHelper.changeRelease();
                Toast.makeText(context.getApplicationContext(), "已经成功切换到正式环境", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.debug:
                try {
                    personalInfoPDataSource.clearToken();
                } catch (PersistDataException e) {
                    e.printStackTrace();
                }
                restApiHelper.changeDebug();
                Toast.makeText(context.getApplicationContext(), "已经成功切换到预发环境", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.developer:
                try {
                    personalInfoPDataSource.clearToken();
                } catch (PersistDataException e) {
                    e.printStackTrace();
                }
                restApiHelper.changeDeveloper();
                Toast.makeText(context.getApplicationContext(), "已经成功切换到开发环境", Toast.LENGTH_SHORT).show();
                dismiss();
                break;

        }
    }


    public ChangeEnvironmentDialog(Context context , RestApiHelper restApiHelper, PersonalInfoPDataSource personalInfoPDataSource) {
        this(context, 0);
        this.context = context;
        this.restApiHelper = restApiHelper;
        this.personalInfoPDataSource = personalInfoPDataSource;
    }

    public ChangeEnvironmentDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected ChangeEnvironmentDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        configWindow();
        View contentView = View.inflate(getContext(), R.layout.change_enviroment_dialog, null);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        setCanceledOnTouchOutside(false);
    }

    private void configWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        Window dialogWindow = getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        //底部弹出动画
        dialogWindow.setWindowAnimations(R.style.bottom_pop_animation);
    }
}
