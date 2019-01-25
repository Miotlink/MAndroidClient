package com.homepaas.sls.ui.newdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sherily on 2016/9/20.
 */
public class GallerySingleFragment extends Fragment {
    private static final String KEY_PHOTOS = "PHOTO";
    @Bind(R.id.photo)
    ImageView photoIv;
    @Bind(R.id.other_zone)
    FrameLayout otherZone;

    private String photo;

    public static GallerySingleFragment newInstance(String photo) {

        Bundle args = new Bundle();
        if ( photo != null )
            args.putString(KEY_PHOTOS, photo);
        GallerySingleFragment fragment = new GallerySingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getArguments() != null ) {
            photo = getArguments().getString(KEY_PHOTOS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gallery_fragment_2, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPicture(photo);
    }

    public void setPicture(String photo) {
        if ( photo != null ){
            Glide.with(this)
                    .load(photo)
                    .placeholder(R.mipmap.default_image)
                    .into(photoIv);
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}