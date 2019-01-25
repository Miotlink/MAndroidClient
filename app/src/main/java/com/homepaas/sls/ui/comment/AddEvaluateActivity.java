package com.homepaas.sls.ui.comment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import butterknife.Bind;
import butterknife.OnTextChanged;

/**
 * 追评页面
 */
public class AddEvaluateActivity extends CommonBaseActivity {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;

    @Bind(R.id.rating_bar)
    RatingBar ratingBar;

    @Bind(R.id.rating_text)
    TextView ratingText;

    @Bind(R.id.evaluate_time)
    TextView evaluateTime;

    @Bind(R.id.evaluate_content)
    TextView evaluateContent;

    @Bind(R.id.additional_evaluate_content)
    EditText additionalEvaluateContent;

    @Bind(R.id.action_button)
    Button actionButton;

    @Bind(R.id.picture)
    RecyclerView mPicture;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_evaluate;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText(String.valueOf(rating));
            }
        });
    }

    @Override
    protected void initData() {

    }


    @OnTextChanged(R.id.additional_evaluate_content)
    public void checkSubmitAddtionalEnable() {
        String evaluation = additionalEvaluateContent.getText().toString();
        actionButton.setEnabled(!TextUtils.isEmpty(evaluation));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
