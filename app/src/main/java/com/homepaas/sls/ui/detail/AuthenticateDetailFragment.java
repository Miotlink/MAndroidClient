package com.homepaas.sls.ui.detail;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.homepaas.sls.R;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.ui.detail.adapter.AuthenticateDetailAdapter;

import java.util.ArrayList;

/**
 * on 2015/12/25 0025
 *
 * @author zhudongjie .
 */
public class AuthenticateDetailFragment extends DialogFragment {

    private static final String LIST_KEY = "list";


    public static AuthenticateDetailFragment newInstance(ArrayList<IServiceInfo.SystemCertification> certificationList) {
        AuthenticateDetailFragment fragment = new AuthenticateDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_KEY, certificationList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authenticate_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        if (getArguments() != null) {
            ArrayList<IServiceInfo.SystemCertification> list = getArguments().getParcelableArrayList(LIST_KEY);
            AuthenticateDetailAdapter adapter = new AuthenticateDetailAdapter(list);
            gridView.setAdapter(adapter);
        }
    }
}
