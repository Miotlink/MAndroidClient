package com.homepaas.sls.ui.order.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.util.WindowUtil;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;
import com.hyphenate.util.DensityUtil;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ServicePhotoAdapter extends BaseRecyclerAdapter<BusinessExInfoOutput.PhotoUrlsBean> {

    private int recyleViewSpace;//
    private int imgWidth;

    public ServicePhotoAdapter(Context context) {
        super(context, null);
        recyleViewSpace = DensityUtil.dip2px(context, 43);
        imgWidth = (WindowUtil.getInstance().getScreenWidth((Activity) mContext) - recyleViewSpace) / 4;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.merchant_or_worker_photo;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, BusinessExInfoOutput.PhotoUrlsBean bean) {
        final ImageView imageView = holder.getImageView(R.id.img_phone);

        //保持屏幕四张图展示[宽高保持一致]
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(imgWidth, imgWidth));
            }
        });

        Glide.with(mContext).load(bean.getSmallPic())
                .asBitmap()
                .fitCenter()
                .placeholder(R.mipmap.default_no_circular_image)
                .error(R.mipmap.default_no_circular_image)
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        imageView.setImageBitmap(resource);
                    }
                });
    }
}
