package com.homepaas.sls.ui.tag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.BusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.mvp.presenter.servicedetail.TagDetailPresenter;
import com.homepaas.sls.mvp.view.servicedetail.TagView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.tag.Adapter.TagBaseAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class TagDetailActivity extends CommonBaseActivity implements TagView {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.title_layout)
    AppBarLayout titleLayout;
    @Bind(R.id.tag)
    TagLayout tag;
    @Bind(R.id.serviceNumber)
    TextView serviceNumber;

    private String Id;
    private int tagcount;

    @Inject
    TagDetailPresenter tagDetailPresenter;

    private TagBaseAdapter mAdapter;
    private List<String> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag_detial;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String name = intent.getStringExtra("TitleName");
        int type = intent.getIntExtra("Type", 0);
        if (type == Constant.SERVICE_TYPE_WORKER_INT) {
            tagDetailPresenter.getWorkerTagsInfo(Id);
        } else {
            tagDetailPresenter.getBusinessTagsInfo(Id);
        }
        Id = intent.getStringExtra("Id");
        tagDetailPresenter.setTagView(this);
        toolbar.setTitle(name + "的标签");
//        mList = new ArrayList<>();
//        mList.add("中华人名共和国");
//        mList.add("大韩民国");
//        mList.add("日本");
//        mList.add("朝鲜");
//        mList.add("台湾");
//        mList.add("香港特别行政区");
//        mList.add("澳门特别行政区");
//        mList.add("越南");
//        mList.add("老挝");
//        mList.add("柬埔寨");
//        mList.add("泰国");
//        mList.add("缅甸");
//        mList.add("马来西亚");
//        mList.add("新加坡");
//        mList.add("印度尼西亚");
//        mList.add("文莱");
//        mList.add("菲律宾");
//        mAdapter = new TagBaseAdapter(this,mList);
//        tag.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tagDetailPresenter.destroy();
    }


    @Override
    public void render(GetBusinessTagsInfo getBusinessTagsInfo) {
        if (getBusinessTagsInfo != null) {
            tagcount = getBusinessTagsInfo.getCount();
            serviceNumber.setText("共" + tagcount + "个标签");
            mList = new ArrayList<>();
            List<BusinessTagsInfo> tagsInfos = getBusinessTagsInfo.getBusinessTagsInfos();
            for (BusinessTagsInfo businessTagsInfo : tagsInfos) {
                mList.add(businessTagsInfo.getTagName());
            }
            mAdapter = new TagBaseAdapter(this, mList);
            tag.setAdapter(mAdapter);
        }
    }

    @Override
    public void render(GetWorkerTagsInfo getWorkerTagsInfo) {
        if (getWorkerTagsInfo != null) {
            tagcount = getWorkerTagsInfo.getCount();
            serviceNumber.setText("共" + tagcount + "个标签");
            mList = new ArrayList<>();
            List<WorkerTagsInfo> tagsInfos = getWorkerTagsInfo.getWorkerTagsInfos();
            for (WorkerTagsInfo workerTagsInfo : tagsInfos) {
                mList.add(workerTagsInfo.getTagName());
            }
            mAdapter = new TagBaseAdapter(this, mList);
            tag.setAdapter(mAdapter);
        }
    }

    @Override
    public Context getContext() {
        return null;
    }
}
