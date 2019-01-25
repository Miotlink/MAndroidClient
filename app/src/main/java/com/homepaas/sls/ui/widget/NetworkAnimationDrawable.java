package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * on 2016/1/29 0029
 *
 * @author zhudongjie .
 */
public class NetworkAnimationDrawable extends AnimationDrawable {

    private Context mContext;

    private int count;

    public NetworkAnimationDrawable(Context context) {
        this.mContext = context;

    }

    public void load(String... urls) {
        count = urls.length;
        for (int i = 0; i < urls.length; i++) {
            Glide.with(mContext).load(urls[i])
                    .bitmapTransform(new RoundedCornersTransformation(mContext, 8, 4))
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            addFrame(resource, 1000);
                            count--;
                            if (count == 0)
                                start();
                        }
                    });
        }
    }

}
