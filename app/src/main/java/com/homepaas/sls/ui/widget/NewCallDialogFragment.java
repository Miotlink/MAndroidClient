package com.homepaas.sls.ui.widget;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewCallDialogFragment extends DialogFragment {

    private static final String KEY_PHONE = "phone";
    private static final String KEY_TITLE = "title";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.content)
    TextView contentTextView;


    private String phone;
    private String titleString;


    public static NewCallDialogFragment newInstance(String phoneNumber, String title, OnCallPhoneListener onCallPhoneListener) {

        Bundle args = new Bundle();
        args.putString(KEY_PHONE, phoneNumber);
        args.putString(KEY_TITLE, title);
        NewCallDialogFragment fragment = new NewCallDialogFragment();
        fragment.onCallPhoneListener = onCallPhoneListener;
        fragment.setArguments(args);
        return fragment;
    }

    public static NewCallDialogFragment newInstance(String phoneNumber) {

        Bundle args = new Bundle();
        args.putString(KEY_PHONE, phoneNumber);
        NewCallDialogFragment fragment = new NewCallDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static NewCallDialogFragment newInstance(String phoneNumber, String title) {

        Bundle args = new Bundle();
        args.putString(KEY_PHONE, phoneNumber);
        args.putString(KEY_TITLE, title);
        NewCallDialogFragment fragment = new NewCallDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SlsStyleDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fillet_dialog_common, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            phone = getArguments().getString(KEY_PHONE);
            titleString = getArguments().getString(KEY_TITLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        title.setText(titleString);
        String content = "";
        SpannableString ss = null;
        if (getString(R.string.service_phone_number).equals(phone)) {
            ss = new SpannableString(phone);
        } else {
            ss = new SpannableString(phone);
//            ss = new SpannableString(PhoneNumberUtils.encryptPhone(phone));
        }
        ss.setSpan(new ForegroundColorSpan(Color.BLACK), content.length(), ss.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        contentTextView.setText(ss);
    }

    public OnCallPhoneListener onCallPhoneListener;

    public interface OnCallPhoneListener {
        void onCallPhone();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.button_cancel, R.id.button_confirm})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel:
                dismiss();
                break;
            case R.id.button_confirm:
                if (onCallPhoneListener != null) {
                    onCallPhoneListener.onCallPhone();
                }
                //or ACTION_DIAL ??
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                dismiss();
        }
    }
}
