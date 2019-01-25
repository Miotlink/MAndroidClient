package com.homepaas.sls.ui.widget;

/**
 * on 2016/7/29 0029
 *
 * @author zhudongjie
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.homepaas.sls.common.PhoneNumberUtils.encryptPhone;

/**
 * on 2016/7/7 0007
 *
 * @author zhudongjie
 */
public class CallDialogFragment extends DialogFragment {

    private static final String KEY_PHONE = "phone";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.content)
    TextView contentTextView;


    private String phone;

    public static CallDialogFragment newInstance(String phoneNumber) {

        Bundle args = new Bundle();
        args.putString(KEY_PHONE, phoneNumber);
        CallDialogFragment fragment = new CallDialogFragment();
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
        View view = inflater.inflate(R.layout.dialog_common, container, false);
        ButterKnife.bind(this, view);
        if (getArguments()!=null){
            phone = getArguments().getString(KEY_PHONE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String content = "是否要拨打";
        SpannableString ss = new SpannableString(content + encryptPhone(phone));
        ss.setSpan(new ForegroundColorSpan(Color.RED), content.length(), ss.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        contentTextView.setGravity(Gravity.CENTER);
        contentTextView.setText(ss);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.button_cancel,R.id.button_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.button_cancel:
                dismiss();
                break;
            case R.id.button_confirm:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);
                dismiss();
        }
    }


}
