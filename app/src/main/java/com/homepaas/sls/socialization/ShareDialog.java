package com.homepaas.sls.socialization;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 分享Dialog
 *
 * @author zhudongjie
 */
public class ShareDialog extends BottomSheetDialog {

    private static final String TAG = "ShareDialog";
    private static final boolean DEBUG = BuildConfig.DEBUG;

    private String text = "分享";

    private String url;

    private UMImage image;

    private Activity activity;

    private String title;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShareAction shareAction = new ShareAction(activity).setCallback(umShareListener);
            shareAction.withText(text)
                    .withTitle(title);
            switch (v.getId()) {
                case R.id.qq_friend:
                    shareAction.setPlatform(SHARE_MEDIA.QQ);
                    break;
                case R.id.wechat:
                    shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
                    break;
                case R.id.wechat_circle:
                    shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                    break;
                case R.id.sina_weibo:
                default:
                    shareAction.setPlatform(SHARE_MEDIA.SINA);
                    break;
            }

            if (url != null) {
                if (DEBUG)
                    Log.d(TAG, "url: "+url);
                shareAction.withTargetUrl(url);

            }
            if (image != null) {
                if (DEBUG)
                    Log.d(TAG, "image: "+image);
                shareAction.withMedia(image);
            }
            shareAction.share();
        }
    };

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getContext(), " 分享成功啦", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onResult: " + platform);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getContext(), " 分享失败啦", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onError: ");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getContext(), " 分享取消了", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCancel: ");
        }
    };

    @SuppressWarnings("ConstantConditions")
    public ShareDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
        setContentView(R.layout.share_bottom_sheet_dialog);
        findViewById(R.id.qq_friend).setOnClickListener(clickListener);
        findViewById(R.id.wechat).setOnClickListener(clickListener);
        findViewById(R.id.wechat_circle).setOnClickListener(clickListener);
        findViewById(R.id.sina_weibo).setOnClickListener(clickListener);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(UMImage image) {
        this.image = image;
    }

    public void setUmShareListener(UMShareListener listener){
        umShareListener = listener;
    }

    public static class SimpleUMShareListener implements UMShareListener{

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    }
}
