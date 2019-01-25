package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.homepaas.sls.R;

import java.io.IOException;

/**
 * Date: 2016/12/27.
 * Author: fmisser
 * Description:
 */


public class GuideVideoView extends FrameLayout implements TextureView.SurfaceTextureListener {

    private TextureView textureView;
    private MediaPlayer mediaPlayer;
//    private SurfaceTexture surfaceTexture;
    private boolean isPrepared = false;
    private boolean isRecover = false;
    private FrameLayout mask;
    int position;
    int videoId;
    private OnVideoCompletionListener listener;

    public GuideVideoView(Context context) {
        super(context);
        init(context);
    }

    public GuideVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GuideVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_guide_video_view, this);

        mask = (FrameLayout) findViewById(R.id.mask);
        mask.setVisibility(INVISIBLE);
        textureView = (TextureView) findViewById(R.id.video);
        textureView.setSurfaceTextureListener(this);
    }

    @NonNull
    public void setVideoId(int id) {
        videoId = id;
    }

    public void setOnVideoComletionListener(OnVideoCompletionListener listener) {
        this.listener = listener;
    }

    private void prepare(SurfaceTexture surfaceTexture) {
        if (!isPrepared) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getContext(), Uri.parse("android.resource://" + getContext().getPackageName() + "/" + videoId));
                mediaPlayer.setSurface(new Surface(surfaceTexture));
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (listener != null) {
                            listener.onCompletion();
                        }
                    }
                });
                mediaPlayer.prepare();
                isPrepared = true;
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private void release() {
        if (isPrepared) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPrepared = false;
        }
    }

    public void play() {

        if (!isPrepared) {
            isRecover = true;
        } else {
            mediaPlayer.start();
        }

//        videoView.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + videoId));
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                    @Override
//                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
//                            mask.setVisibility(View.INVISIBLE);
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//            }
//        });
//
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
////                videoView.start();
////                videoView.seekTo(videoView.getDuration());
//            }
//        });
//        videoView.start();
    }

    public void onResume() {
//        videoView.seekTo(position);
//        videoView.start();
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
            //如果SurfaceTexture没有destroy,则不需要重建
            isRecover = false;
        }
    }

    public void onPause() {
//        position = videoView.getCurrentPosition();
//        videoView.pause();

        if (mediaPlayer != null) {
            position = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
        //需要复原
        isRecover = true;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        prepare(surface);
        if (isRecover&&mediaPlayer != null) {
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
            isRecover = false;
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        release();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public interface OnVideoCompletionListener {
        void onCompletion();
    }
}
