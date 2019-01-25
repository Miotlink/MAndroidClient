package com.homepaas.sls.ui.order.directOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.ReboundScrollView;
import com.homepaas.sls.util.StaticData;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/5/27.
 * 界面留言备注
 */

public class MessageRemarkActivity extends CommonBaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_rel)
    RelativeLayout titleRel;
    @Bind(R.id.remark_edit)
    EditText remarkEdit;
    @Bind(R.id.btn_sumbit)
    Button btnSumbit;
    @Bind(R.id.scroll_view)
    ReboundScrollView scrollView;

    private String remarkStr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_remark;
    }

    @Override
    protected void initView() {
        remarkStr=getIntent().getStringExtra(StaticData.ORDER_REMARK);
        remarkEdit.setText(remarkStr);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        remarkEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(remarkEdit.getWindowToken(), 0); //强制隐藏键盘
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.back, R.id.btn_sumbit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(remarkEdit.getWindowToken(), 0); //强制隐藏键盘
                setRemark(remarkEdit.getText().toString());
                break;
            case R.id.btn_sumbit:
                setRemark(remarkEdit.getText().toString());
                break;
            default:
        }
    }

    /**
     * 返回备注信息
     */
    private void setRemark(String remarkStr) {
        Intent intent = new Intent();
        intent.putExtra(StaticData.ORDER_REMARK, remarkStr);
        setResult(Activity.RESULT_OK, intent);
        ActivityCompat.finishAfterTransition(this);
    }
}
