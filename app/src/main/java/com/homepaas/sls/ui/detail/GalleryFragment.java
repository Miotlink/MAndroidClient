package com.homepaas.sls.ui.detail;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.R;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.ui.detail.adapter.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * on 2016/3/4 0004
 *
 * @author zhudongjie .
 */
public class GalleryFragment extends Fragment {

    private static final String KEY_PHOTOS = "PHOTOS";

    private List<String> mPhotoList;

    public static GalleryFragment newInstance(List<String> photos) {

        Bundle args = new Bundle();
        if (photos instanceof ArrayList) {
            args.putStringArrayList(KEY_PHOTOS,(ArrayList<String>) photos);
//            args.putParcelableArrayList(KEY_PHOTOS, (ArrayList<String>) photos);
        } else {
            args.putStringArrayList(KEY_PHOTOS, new ArrayList<String>(photos));
//            args.putParcelableArrayList(KEY_PHOTOS, new ArrayList<Parcelable>(photos));
        }
        GalleryFragment fragment = new GalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhotoList = getArguments().getStringArrayList(KEY_PHOTOS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_stub_gallery, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.gallery);
        GalleryAdapter adapter = new GalleryAdapter(mPhotoList);
        recyclerView.setAdapter(adapter);

        recyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }
}
