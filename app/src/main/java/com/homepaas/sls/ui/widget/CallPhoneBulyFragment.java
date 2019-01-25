package com.homepaas.sls.ui.widget;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.util.WindowUtil;

/**
 * on 2016/4/1 0001
 *
 * @author zhudongjie .
 */
public class CallPhoneBulyFragment extends DialogFragment {
    public static CallPhoneBulyFragment newInstance() {

        Bundle args = new Bundle();

        CallPhoneBulyFragment fragment = new CallPhoneBulyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SlsStyleDialog);
    }

    //
    private View.OnClickListener onclick;

    public void setConfirmListener(View.OnClickListener onclick) {
        this.onclick = onclick;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.dialog_call_phone_busy, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button button = (Button) mView.findViewById(R.id.btn_phone_error);

        LinearLayout lyDgHeight = (LinearLayout) mView.findViewById(R.id.ly_dg_height);
        int screenHeight = WindowUtil.getInstance().getScreenHeight(getActivity());
        lyDgHeight.setMinimumHeight((int) (screenHeight*0.287));
        RelativeLayout rl_close = (RelativeLayout) mView.findViewById(R.id.rl_close);
        rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag("0");
                //号码错误
                if (onclick != null)
                    onclick.onClick(v);
                dismiss();
            }
        });
        Button button2 = (Button) mView.findViewById(R.id.btn_phone_unmanned);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag("1");
                //无人接听
                if (onclick != null)
                    onclick.onClick(v);
                dismiss();
            }
        });
        return mView;
    }
}
