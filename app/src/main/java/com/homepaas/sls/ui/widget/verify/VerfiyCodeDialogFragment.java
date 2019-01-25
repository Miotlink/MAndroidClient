package com.homepaas.sls.ui.widget.verify;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.homepaas.sls.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerfiyCodeDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerfiyCodeDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.code_input)
    EditText codeInput;
    @Bind(R.id.verify_code)
    VerifyCode verifyCode;
    @Bind(R.id.button_cancel)
    TextView buttonCancel;
    @Bind(R.id.button_confirm)
    TextView buttonConfirm;
    @Bind(R.id.container)
    LinearLayout container;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VerfiyCodeDialogFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment VerfiyCodeDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerfiyCodeDialogFragment newInstance() {
        VerfiyCodeDialogFragment fragment = new VerfiyCodeDialogFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SlsStyleDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verfiy_code_dialog, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, view);
        return view;
    }
    private void verifyImageCode(){
        if (verifyCode.isEqualsIgnoreCase(codeInput.getText().toString())){
            if (onVerifyInviladListener != null){
                onVerifyInviladListener.onVerifyInvilad(true);
            }
        } else {
            Toast.makeText(getContext(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            verifyCode.refresh();
        }
    }
    public interface OnVerifyInviladListener {
        void onVerifyInvilad(boolean isVerifyInvilad);
    }
    private OnVerifyInviladListener onVerifyInviladListener;

    public void setOnVerifyInviladListener(OnVerifyInviladListener onVerifyInviladListener) {
        this.onVerifyInviladListener = onVerifyInviladListener;
    }

    @OnClick({R.id.button_cancel,R.id.button_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_cancel:
                dismissAllowingStateLoss();
                break;
            case R.id.button_confirm:
                verifyImageCode();
               break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }
}
