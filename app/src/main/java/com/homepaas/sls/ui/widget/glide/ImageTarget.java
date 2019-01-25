package com.homepaas.sls.ui.widget.glide;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * 由于图片源大小不一致，直接使用Transformation处理会导致圆角大小不一致，不再使用Transformation来处理圆角，
 * 直接into(ImageView)第一次加载时会导致RoundedImageView的圆角效果失效
 */
public class ImageTarget extends SimpleTarget<GlideDrawable>{

    private final ImageView mImageView;

    public ImageTarget(ImageView imageView){
        mImageView = imageView;
    }

    @Override
    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
        mImageView.setImageDrawable(resource);
    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
       mImageView.setImageDrawable(errorDrawable);
    }
}
