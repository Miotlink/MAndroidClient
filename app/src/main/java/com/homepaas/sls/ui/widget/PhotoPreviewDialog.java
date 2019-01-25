package com.homepaas.sls.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.di.module.FirstCouponModule;
import com.homepaas.sls.ui.merchantservice.GalleryAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CJJ on 2016/9/22.
 */

public class PhotoPreviewDialog extends DialogFragment {

    private static String KEY="ARGS_KEY";
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private Activity context;
    private int index = -1;

    public static PhotoPreviewDialog newInstance(Param param) {

        Bundle args = new Bundle();
        args.putParcelable(KEY,param);
        PhotoPreviewDialog fragment = new PhotoPreviewDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(STYLE_NO_TITLE, R.style.SlsStyleDialog);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        setCancelable(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.photo_preview_layout, container,false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    private void resetStatusBar() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle args = getArguments();
        Param param = args.getParcelable(KEY);
        List<String> filePaths = param.filePaths;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int count = param.filePaths.size();
        index = index == -1?param.index:index;
        List<View> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ImageView img = new ImageView(context);
            img.setMaxWidth(metrics.widthPixels);
            img.setMaxHeight(metrics.heightPixels);
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.width = ViewPager.LayoutParams.MATCH_PARENT;;
//            params.width = metrics.widthPixels;
            params.height = ViewPager.LayoutParams.WRAP_CONTENT;
            img.setLayoutParams(params);
            img.setAdjustViewBounds(true);
            Glide.with(getContext()).load(filePaths.get(i)).into(img);
            views.add(img);
        }
        viewPager.setAdapter(new GalleryAdapter(views));
        viewPager.setCurrentItem(index);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public static class Builder {
        Param param;
        int index;
        List<String> filePaths;

        public Builder() {
            param = new Param();
        }

        public Builder index(int index){this.index = index;return this;}
        public Builder path(List<String> filePaths){this.filePaths = filePaths;return this;}

        public PhotoPreviewDialog build(){
            param.index = index;
            param.filePaths = filePaths;
            PhotoPreviewDialog dialog = PhotoPreviewDialog.newInstance(param);
            return dialog;
        }
    }

    public static class Param implements Parcelable {
        public int index;
        public List<String> filePaths;


        public Param() {
        }

        protected Param(Parcel in) {
            index = in.readInt();
            filePaths = in.createStringArrayList();
        }

        public static final Creator<Param> CREATOR = new Creator<Param>() {
            @Override
            public Param createFromParcel(Parcel in) {
                return new Param(in);
            }

            @Override
            public Param[] newArray(int size) {
                return new Param[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(index);
            dest.writeStringList(filePaths);
        }
    }
}
