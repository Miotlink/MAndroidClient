package com.homepaas.sls.ui.order.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.homepaas.sls.R;
import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.util.recycleviewutils.BaseRecyclerAdapter;
import com.homepaas.sls.util.recycleviewutils.RecyclerViewHolder;

/**
 * Created by Administrator on 2016/4/14.
 */
public class BusinessCertificationsAdapter extends BaseRecyclerAdapter<BusinessExInfoOutput.BusinessCertificationsBean> {


    public BusinessCertificationsAdapter(Context context) {
        super(context, null);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.business_certifications_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, BusinessExInfoOutput.BusinessCertificationsBean bean) {
        final ImageView imageView = holder.getImageView(R.id.img_phone);

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
