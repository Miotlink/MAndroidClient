package com.homepaas.sls.ui.order.directOrder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerPlaceOrderComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.LableEntity;
import com.homepaas.sls.domain.entity.StarLevelInfo;
import com.homepaas.sls.mvp.presenter.order.EvaluationOrderPresenter;
import com.homepaas.sls.mvp.view.order.StarLevelView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.adapter.ChooseLableAdapter;
import com.homepaas.sls.ui.order.directOrder.adapter.StarLevelAdapter;
import com.homepaas.sls.ui.widget.ReboundScrollView;
import com.homepaas.sls.util.StaticData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2017/7/21.
 * 评价界面
 */

public class EvaluationOrderActivity extends CommonBaseActivity implements StarLevelView, StarLevelAdapter.HostAction, ChooseLableAdapter.LableSelect {

    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.title_rel)
    RelativeLayout titleRel;
    @Bind(R.id.star_recyclerView)
    RecyclerView starRecyclerView;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.label_recyclerView)
    RecyclerView labelRecyclerView;
    @Bind(R.id.remark_edit)
    EditText remarkEdit;
    @Bind(R.id.remaining_words)
    TextView remainingWords;
    @Bind(R.id.remark_lin)
    RelativeLayout remarkLin;
    @Bind(R.id.submit)
    TextView submit;
    @Bind(R.id.show_button_lin)
    LinearLayout showButtonLin;
    @Bind(R.id.scroll_view)
    ReboundScrollView scrollView;
    @Bind(R.id.tv_service_title)
    TextView tvServiceTitle;
    @Bind(R.id.tv_service_details)
    TextView tvServiceDetails;
    private String serviceId;
    private String orderCode;
    private int selectPostion;
    private StarLevelAdapter starLevelAdapter;
    private List<LableEntity> lableEntityList;
    private ChooseLableAdapter chooseLableAdapter;
    @Inject
    EvaluationOrderPresenter evaluationOrderPresenter;

    public static void start(Context context, String serviceId, String orderCode,String serviceDetails) {
        Intent intent = new Intent(context, EvaluationOrderActivity.class);
        intent.putExtra(StaticData.SERVICE_ID, serviceId);
        intent.putExtra(StaticData.ORDER_CODE, orderCode);
        intent.putExtra(StaticData.EVALUATION_SERVICE_DETAILS, serviceDetails);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluation_order;
    }


    @Override
    protected void initView() {
        orderCode = getIntent().getStringExtra(StaticData.ORDER_CODE);
        String stringExtra = getIntent().getStringExtra(StaticData.EVALUATION_SERVICE_DETAILS);
        if (!TextUtils.isEmpty(stringExtra))
            tvServiceDetails.setText(stringExtra);
        lableEntityList = new ArrayList<>();
        starLevelAdapter = new StarLevelAdapter();
        starLevelAdapter.setHostAction(this);
        starRecyclerView.setAdapter(starLevelAdapter);
        chooseLableAdapter = new ChooseLableAdapter();
        chooseLableAdapter.setLateTimeSelect(this);
        labelRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        labelRecyclerView.setAdapter(chooseLableAdapter);
        evaluationOrderPresenter.setStarLevelView(this);
        evaluationOrderPresenter.getStarLevel(serviceId);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerPlaceOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderStarLevelInfo(StarLevelInfo starInfo) {
        if (starInfo != null && starInfo.getStarLevelLabelInfoList() != null) {
            starLevelAdapter.setStarLevelLabelInfoList(starInfo.getStarLevelLabelInfoList());
            lableEntityList.clear();
            int size = starInfo.getStarLevelLabelInfoList().size();

            //默认给与好评
            description.setText(starInfo.getStarLevelLabelInfoList().get(size - 1).getDescription());
            List<String> lables = starInfo.getStarLevelLabelInfoList().get(size - 1).getLabels();
            if (lables != null) {
                for (int i = 0; i < lables.size(); i++) {
                    LableEntity lableEntity = new LableEntity();
                    lableEntity.setLable(lables.get(i));
                    lableEntity.setIsCheck("0");
                    lableEntity.setPosition(i + "");
                    lableEntityList.add(lableEntity);
                }
            }
            selectPostion = lableEntityList.size();
            chooseLableAdapter.setLableEntityList(lableEntityList);
        }

    }

    @Override
    public void SubmitEvaluationSuccess() {
        Toast.makeText(this, "感谢评价", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(R.anim.anim_no, R.anim.anim_bottom_out);
    }

    /**
     * 用户对确认完成的订单打星星评价 用户选择对应的星星等级，显示等级对应的标签给用户选择
     *
     * @param position
     * @param starLevelLabelInfo
     */
    @Override
    public void selectWhat(int position, StarLevelInfo.StarLevelLabelInfo starLevelLabelInfo) {
        selectPostion = position;
        starLevelAdapter.setSelectPosition(position);
        description.setText(starLevelLabelInfo.getDescription());
        lableEntityList.clear();
        List<String> lables = starLevelLabelInfo.getLabels();
        if (lables != null) {
            for (int i = 0; i < lables.size(); i++) {
                LableEntity lableEntity = new LableEntity();
                lableEntity.setLable(lables.get(i));
                lableEntity.setIsCheck("0");
                lableEntity.setPosition(i + "");
                lableEntityList.add(lableEntity);
            }
        }
        chooseLableAdapter.setLableEntityList(lableEntityList);

    }

    @Override
    public void lableSelect(String position) {
        for (LableEntity lableEntity : lableEntityList) {
            //防止两个一样的标签，所以不用名字
            if (TextUtils.equals(position, lableEntity.getPosition())) {
                if (TextUtils.equals("0", lableEntity.getIsCheck())) {
                    lableEntity.setIsCheck("1");
                } else {
                    lableEntity.setIsCheck("0");
                }
            }
        }
        chooseLableAdapter.setLableEntityList(lableEntityList);
    }

    @OnClick({R.id.close, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                overridePendingTransition(R.anim.anim_no, R.anim.anim_bottom_out);
                break;
            case R.id.submit:
                submit();
                break;
            default:
        }
    }

    @OnTextChanged(R.id.remark_edit)
    public void onTextChange(Editable editable) {
        int length = editable.toString().length();
        remainingWords.setText((40 - length) + "");
    }

    /**
     * 提交订单
     */
    private void submit() {
        String evaluatedTag = "";
        String content = remarkEdit.getText().toString();
        StringBuilder stringBuilder = new StringBuilder();
        if (lableEntityList != null) {
            for (LableEntity lableEntity : lableEntityList) {
                if (TextUtils.equals("1", lableEntity.getIsCheck())) {
                    stringBuilder.append(lableEntity.getLable());
                    stringBuilder.append(",");
                }
            }
        }
        evaluatedTag = stringBuilder.toString();
        evaluationOrderPresenter.SubmitEvaluation(orderCode, String.valueOf(selectPostion + 1), content, evaluatedTag);
    }
}
