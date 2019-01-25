package com.homepaas.sls.ui.qrcode;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homepaas.sls.R;
import com.homepaas.sls.common.QrCodeGenerator;
import com.homepaas.sls.mvp.model.PersonalInfoModel;
import com.homepaas.sls.ui.widget.glide.ImageTarget;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 展示我的二维码
 */
public class MyQrCodeFragment extends DialogFragment {

    private static final String ARG_KEY = "data";

    @Bind(R.id.photo)
    ImageView mPhoto;

    @Bind(R.id.name)
    TextView mName;

    @Bind(R.id.phone)
    TextView mPhone;

    @Bind(R.id.qr_code)
    ImageView mQrCode;

    private PersonalInfoModel mInfoModel;

    public static MyQrCodeFragment newInstance(PersonalInfoModel infoModel) {
        MyQrCodeFragment fragment = new MyQrCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_KEY, infoModel);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        if (getArguments() != null) {
            mInfoModel = getArguments().getParcelable(ARG_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_qr_code, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Glide.with(this)
                .load(mInfoModel.getHqPic())
                .placeholder(R.mipmap.default_user_icon)
                .into(new ImageTarget(mPhoto));
        int size = getActivity().getResources().getDimensionPixelSize(R.dimen.dialog_qr_code_size);
        Bitmap bitmap = QrCodeGenerator.createQRCode(mInfoModel.getQrCode(), size, size);
        mQrCode.setImageBitmap(bitmap);
        mName.setText(mInfoModel.getNickName());
        mPhone.setText(mInfoModel.getPhoneNumber());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
