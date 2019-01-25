package com.homepaas.sls.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;

/**
 * on 2016/1/11 0011
 * 消息对话框，不支持旋转后重新创建
 * @author zhudongjie .
 */
public class InfoDialogFragment extends DialogFragment {

    private Builder mBuilder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE,0);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        @SuppressLint("InflateParams")
        View view = getActivity().getLayoutInflater().inflate(R.layout.info_dialog, null);

        TextView title = (TextView) view.findViewById(android.R.id.title);
        TextView message = (TextView) view.findViewById(android.R.id.message);

        title.setText(mBuilder.mTitle);
        message.setText(mBuilder.mMessage);
        mBuilder.mAlertDialogBuilder.setView(view);
        return mBuilder.mAlertDialogBuilder.create();
    }

    public static class Builder {

        private CharSequence mTitle;

        private CharSequence mMessage;
        private AlertDialog.Builder mAlertDialogBuilder;

        public Builder(Activity activity) {
            mAlertDialogBuilder = new AlertDialog.Builder(activity, R.style.SlsStyleDialog);
        }

        public Builder setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            mMessage = message;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener onClickListener) {
            mAlertDialogBuilder.setPositiveButton(text, onClickListener);
            return this;
        }

        public Builder setNegativeButton(CharSequence text, DialogInterface.OnClickListener onClickListener) {
            mAlertDialogBuilder.setNegativeButton(text, onClickListener);
            return this;
        }

        public InfoDialogFragment create() {
            InfoDialogFragment fragment = new InfoDialogFragment();
            fragment.mBuilder = this;
            return fragment;
        }
    }
}
