package com.homepaas.sls.ui.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.ComplaintListEntity;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.newmvp.contract.ComplaintContract;
import com.homepaas.sls.newmvp.presenter.ComplaintPresenter;
import com.homepaas.sls.ui.adapter.ComplaintAdapter;
import com.homepaas.sls.ui.common.activity.CommonMvpBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.MyTextWatcher;
import com.homepaas.sls.util.KeyBoardUtil;
import com.homepaas.sls.util.StaticData;

import java.util.List;

import butterknife.Bind;

/**
 * Created by mhy on 2017/8/26.
 * 投诉
 */

public class ComplaintActivity extends CommonMvpBaseActivity<ComplaintContract.Presenter> implements ComplaintAdapter.OnSelectClickListener, ComplaintContract.View {
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.ed_remark)
    EditText edRemark;
    @Bind(R.id.btn_sumbit)
    Button btnSumbit;
    @Bind(R.id.other_reasons_lin)
    LinearLayout otherReasonsLin;

    private ComplaintAdapter complaintAdapter;
    private String orderID;

    @Override
    protected ComplaintContract.Presenter getPresenter() {
        return new ComplaintPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complaint;
    }

    @Override
    protected void initView() {
        orderID = getIntent().getStringExtra(StaticData.ORDER_ID);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initListener();

        complaintAdapter = new ComplaintAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(complaintAdapter);
        complaintAdapter.setOnSelectClickListener(this);
    }

    private void initListener() {
        RxBindingViewClickHelper.onClick(btnSumbit, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                StringBuffer indexs = new StringBuffer();
                StringBuffer titles = new StringBuffer();


                for (int i = 0; i < complaintAdapter.getData().size(); i++) {
                    ComplaintListEntity.ListBean complaintEntity = complaintAdapter.getData().get(i);
                    if (complaintEntity.isCheck()) {
                        titles.append(complaintEntity.getTitle() + ",");
                        indexs.append(complaintEntity.getIndex() + ",");
                    }
                }
                String trim = edRemark.getText().toString().trim();
                //.substring(0,indexs.toString().length()-2)去除租后一个逗号
                mPresenter.submit(orderID, indexs.toString().substring(0, indexs.toString().length() - 1), titles.toString().substring(0, titles.toString().length() - 1), trim.equals("") ? "" : trim);
            }
        });

        edRemark.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnSumbit.setEnabled(TextUtils.isEmpty(s.toString().trim()) ? false : true);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getComplaintList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        KeyBoardUtil.closeKeybord(edRemark, this);
    }

    /*
    1、原因选项为多选，且必有1项被选中时才能提交
     2、当选项 “其他原因（备注说明）”被选中时，文本框输入内容不为空才能提交
   */
    @Override
    public void onSelectClick(int position, ComplaintListEntity.ListBean item) {
        item.setCheck(!item.isCheck());
        int selectNum = -1;
        for (int i = 0; i < complaintAdapter.getData().size(); i++) {
            ComplaintListEntity.ListBean complaintEntity = complaintAdapter.getData().get(i);
            if (complaintEntity.isCheck()) {
                selectNum = i;
            }
        }

        //2、当选项 “其他原因（备注说明）”被选中时，文本框输入内容不为空才能提交
        if (selectNum == complaintAdapter.getData().size() - 1) {
            btnSumbit.setEnabled(TextUtils.isEmpty(edRemark.getText().toString().trim()) ? false : true);
            otherReasonsLin.setVisibility(View.VISIBLE);
        } else {
            btnSumbit.setEnabled(selectNum != -1 ? true : false);
            otherReasonsLin.setVisibility(View.GONE);
        }
        //刷新列表
        complaintAdapter.notifyDataSetChanged();
    }

    @Override
    public void initComplaintList(List<ComplaintListEntity.ListBean> listBeen) {
        complaintAdapter.setData(listBeen);
    }

    @Override
    public void submitSucess() {
        showMessage("投诉成功");
        finish();
    }

}
