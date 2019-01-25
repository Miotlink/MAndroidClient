package com.homepaas.sls.ui.order.directOrder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.ClaimsInfo;
import com.homepaas.sls.domain.entity.ReasonsEntity;
import com.homepaas.sls.mvp.presenter.order.SubmitApplyClaimsPresenter;
import com.homepaas.sls.mvp.view.order.SubmitApplyClaimsView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.adapter.CompensationReasonAdapter;
import com.homepaas.sls.ui.widget.ReboundScrollView;
import com.homepaas.sls.util.StaticData;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2017/6/29.
 * 申请赔付
 */

public class ApplyCompensationActivity extends CommonBaseActivity implements CompensationReasonAdapter.HostAction, SubmitApplyClaimsView {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.title_rel)
    RelativeLayout titleRel;
    @Bind(R.id.apply_recyclerView)
    RecyclerView applyRecyclerView;
    @Bind(R.id.text_number)
    TextView textNumber;
    @Bind(R.id.edit_compensation_explain)
    EditText editCompensationExplain;
    @Bind(R.id.scroll_view)
    ReboundScrollView scrollView;
    @Bind(R.id.btn_sumbit)
    TextView btnSumbit;
    private CompensationReasonAdapter compensationReasonAdapter;
    private List<ReasonsEntity> list;
    private String orderId;
    List<ClaimsInfo.ClaimsManagementInfo.ClaimsManagementTypeInfo> claimsReasons;
    private String serviceTime;  //正常服务时间
    private String normalTime;     //正常服务时间
    private String lateTwentyTime; //超过20分钟的时间
    private String lateFortyTime; //超多40分钟的时间
    private String reasonType = "";
    private String workerLaterTime = "";

    @Inject
    SubmitApplyClaimsPresenter submitApplyClaimsPresenter;


    public static void start(Context context, String orderId, String serviceTime, List<ClaimsInfo.ClaimsManagementInfo.ClaimsManagementTypeInfo> claimsReasons) {
        Intent intent = new Intent(context, ApplyCompensationActivity.class);
        intent.putExtra(StaticData.ORDER_ID, orderId);
        intent.putExtra(StaticData.SERVICE_ITEM, serviceTime);
        intent.putExtra(StaticData.CLAIMS_REASONS, (Serializable) claimsReasons);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_compensation;
    }

    @Override
    protected void initView() {
        color = textNumber.getCurrentTextColor();
        orderId=getIntent().getStringExtra(StaticData.ORDER_ID);
        serviceTime=getIntent().getStringExtra(StaticData.SERVICE_ITEM);
        claimsReasons= (List<ClaimsInfo.ClaimsManagementInfo.ClaimsManagementTypeInfo>) getIntent().getSerializableExtra(StaticData.CLAIMS_REASONS);

        btnSumbit.setEnabled(false);
        submitApplyClaimsPresenter.setSubmitApplyClaimsView(this);
        compensationReasonAdapter = new CompensationReasonAdapter();
        compensationReasonAdapter.setHostAction(this);
        applyRecyclerView.setAdapter(compensationReasonAdapter);
        if (!TextUtils.isEmpty(serviceTime)) {
            BigDecimal serviceTimeDecimal = new BigDecimal(serviceTime);
            BigDecimal addTimeDecimal = new BigDecimal("1200");
            BigDecimal serviceTime1Decimal = serviceTimeDecimal.add(addTimeDecimal);
            BigDecimal serviceTime2Decimal = serviceTimeDecimal.add(addTimeDecimal).add(addTimeDecimal);
            normalTime = TimeUtils.formatLateTime(String.valueOf(serviceTimeDecimal));
            lateTwentyTime = TimeUtils.formatLateTime(String.valueOf(serviceTime1Decimal));
            lateFortyTime = TimeUtils.formatLateTime(String.valueOf(serviceTime2Decimal));
        }
        list = new ArrayList<>();
        if (claimsReasons != null && !claimsReasons.isEmpty()) {
            for (ClaimsInfo.ClaimsManagementInfo.ClaimsManagementTypeInfo claimsManagementTypeInfo : claimsReasons) {
                if (claimsManagementTypeInfo != null) {
                    if (!TextUtils.isEmpty(claimsManagementTypeInfo.getName()) && TextUtils.equals("工人迟到", claimsManagementTypeInfo.getName())) {
                        List<String> timeList = new ArrayList<>();
                        timeList.add(normalTime + "-" + lateTwentyTime);
                        timeList.add(lateTwentyTime + "-" + lateFortyTime);
                        timeList.add(lateFortyTime + "以后");
                        ReasonsEntity entity1 = new ReasonsEntity();
                        entity1.setReason(claimsManagementTypeInfo.getName());
                        entity1.setReasonTime("上门时间");
                        entity1.setLateTime(timeList);
                        entity1.setId(claimsManagementTypeInfo.getId());
                        list.add(entity1);
                    } else {
                        ReasonsEntity entity2 = new ReasonsEntity();
                        entity2.setReason(claimsManagementTypeInfo.getName());
                        entity2.setReasonTime("");
                        entity2.setLateTime(null);
                        entity2.setId(claimsManagementTypeInfo.getId());
                        list.add(entity2);
                    }
                }
            }
        }
        compensationReasonAdapter.setList(list);
    }

    @Override
    protected void initData() {

    }

    int color;

    @OnTextChanged(R.id.edit_compensation_explain)
    public void onTextChange(Editable editable) {
        int length = editable.toString().length();
        textNumber.setText(length + "");
        textNumber.setTextColor(length > 80 ? getResources().getColor(R.color.red) : color);
    }

    /**
     * 选择的原因
     *
     * @param position
     */
    @Override
    public void onReasonSelect(int position, String id) {
        btnSumbit.setEnabled(true);
        compensationReasonAdapter.setReasonSelectPosition(position);
        reasonType = id;
    }

    /**
     * 选择迟到时间
     *
     * @param position
     */
    @Override
    public void onLateTimeSelect(int position) {
        workerLaterTime = position + "";
    }

    @OnClick({R.id.back, R.id.btn_sumbit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_sumbit:
                submit();
                break;
            default:
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }

    private void submit() {
        String compensationExplain = editCompensationExplain.getText().toString();
        submitApplyClaimsPresenter.submitApplyClaims(orderId, reasonType, workerLaterTime, compensationExplain);
    }

    @Override
    public void renderSuccess() {
        Toast.makeText(getApplicationContext(), "提交成功！客服将在24小时内处理您的问题",
                Toast.LENGTH_SHORT).show();
        finish();
    }
}
