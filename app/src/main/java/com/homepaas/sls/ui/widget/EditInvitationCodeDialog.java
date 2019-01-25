package com.homepaas.sls.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.homepaas.sls.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class EditInvitationCodeDialog extends DialogFragment {

    private static final String KEY_PARAM = "IT_CODE";
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.Invitation_code)
    EditText etInvitationCode;
    @Bind(R.id.button_cancel)
    TextView buttonCancel;
    @Bind(R.id.button_confirm)
    TextView buttonConfirm;

    private String it_code;  //邀请码

    public static EditInvitationCodeDialog newInstance(String it_code) {
        Bundle args = new Bundle();
        args.putString(KEY_PARAM, it_code);
        EditInvitationCodeDialog fragment = new EditInvitationCodeDialog();
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
        View view = inflater.inflate(R.layout.confirm_invitation_code_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            it_code = getArguments().getString(KEY_PARAM);
            etInvitationCode.setText(it_code);
            buttonConfirm.setEnabled(false);
        }
    }

    @OnTextChanged(R.id.Invitation_code)
    public void enableConfirmbtn(){
        if ( !TextUtils.isEmpty(etInvitationCode.getText().toString()) ){
            it_code = etInvitationCode.getText().toString();
            buttonConfirm.setEnabled(true);
        }else {
            buttonConfirm.setEnabled(false);
        }
    }

    public interface OnConfirmListener{
        void comfirm(String it_code);
    }
    private OnConfirmListener onConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public interface OnCancelListener{
        void cancel();
    }
    private OnCancelListener onCancelListener;

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    @OnClick({R.id.button_cancel,R.id.button_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_cancel:

                if ( onCancelListener != null ){
                    onCancelListener.cancel();
                }
                break;
            case R.id.button_confirm:
                if ( onConfirmListener != null){
                    onConfirmListener.comfirm(etInvitationCode.getText().toString());
                }
                break;
        }


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
