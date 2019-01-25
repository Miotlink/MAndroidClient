package com.homepaas.sls.ui.order.servicemerchant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.adapter.ServicePhotoAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.photo.PhotoPreviewDialog;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mhy on 2017/12/28.
 */

public class MerchantPhotoPreviewActivity extends CommonBaseActivity implements BaseRecyclerAdapter.OnItemClickListener {
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.tv_photo_number)
    TextView tvPhotoNumber;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    private ArrayList<String> mList = new ArrayList<>();
    private ServicePhotoAdapter servicePhotoAdapter;

    public static void start(Context context, BusinessExInfoOutput businessExInfoOutput) {
        Intent starter = new Intent(context, MerchantPhotoPreviewActivity.class);
        starter.putExtra("businessExInfoOutput", businessExInfoOutput);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_merchant_photo_preview;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        servicePhotoAdapter = new ServicePhotoAdapter(mContext);
        servicePhotoAdapter.setOnItemClickListener(this);

        recycleView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recycleView.setHasFixedSize(true);
        recycleView.setAdapter(servicePhotoAdapter);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        BusinessExInfoOutput businessExInfoOutput = (BusinessExInfoOutput) intent.getSerializableExtra("businessExInfoOutput");
        if (businessExInfoOutput != null) {
            List<BusinessExInfoOutput.PhotoUrlsBean> photoUrls = businessExInfoOutput.getPhotoUrls();
            tvPhotoNumber.setText("共" + photoUrls.size() + "张");
            for (int i = 0; i < photoUrls.size(); i++) {
                //预览图集合
                mList.add(photoUrls.get(i).getHqPic());
            }
            servicePhotoAdapter.setData(photoUrls);
        }
    }

    @Override
    public void onItemClick(View itemView, int pos) {
        PhotoPreviewDialog previewDialog = new PhotoPreviewDialog.Builder()
                .index(pos)
                .path(mList)
                .build();
        previewDialog.setIndex(pos);
        previewDialog.show(getSupportFragmentManager(), null);
    }
}
