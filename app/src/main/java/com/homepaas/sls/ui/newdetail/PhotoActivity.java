package com.homepaas.sls.ui.newdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.newdetail.adapter.PhotoAdapter;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.photo.PhotoPreviewDialog;

import java.util.List;

import butterknife.Bind;

public class PhotoActivity extends CommonBaseActivity implements PhotoAdapter.OnZoomPictureListener {

    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.pic_count)
    TextView picCount;
    @Bind(R.id.ablum)
    RecyclerView ablum;
    @Bind(R.id.activity_photo)
    LinearLayout activityPhoto;
    public static void start(Context context,AvatarsEntity avatarsEntity){
        Intent starter = new Intent(context, PhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos",avatarsEntity);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }
    private AvatarsEntity avatarsEntity;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        avatarsEntity = (AvatarsEntity)getIntent().getSerializableExtra("photos");
        if (avatarsEntity != null && !avatarsEntity.getAvatars().isEmpty()) {
            picCount.setText("共"+avatarsEntity.getAvatars().size()+"张");
            ablum.setLayoutManager(new GridLayoutManager(getContext(), 5));
            PhotoAdapter photoAdapter = new PhotoAdapter(avatarsEntity.getAvatars());
            photoAdapter.setOnZoomPictureListener(this);
            ablum.setAdapter(photoAdapter);
        }
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
    public void zoom(int position, List<String> photos) {
        PhotoPreviewDialog previewDialog = new PhotoPreviewDialog.Builder()
                .index(position)
                .path(photos)
                .build();
        previewDialog.setIndex(position);
        previewDialog.show(getSupportFragmentManager(), null);
    }
}
