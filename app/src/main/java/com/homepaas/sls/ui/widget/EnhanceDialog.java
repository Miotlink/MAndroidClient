package com.homepaas.sls.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.homepaas.sls.R;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/5.
 *
 */
public class EnhanceDialog extends DialogFragment {


    private static String PARAM="PARAM";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.button_cancel)
    TextView buttonCancel;
    @Bind(R.id.button_confirm)
    TextView buttonConfirm;
    private Builder builder;
    private View contentView;

    private Param param;

    public static EnhanceDialog newInstance(Param param) {

        Bundle args = new Bundle();
        EnhanceDialog fragment = new EnhanceDialog();
        args.putSerializable(PARAM,param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SlsStyleDialog);
        param = (Param) getArguments().getSerializable(PARAM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_enhance, container,false);
        ButterKnife.bind(this,view);
        boolean showtitle = param.showtitle;
        View ctView = param.contentView;
        String contentString = param.contentString;
        String cancelString = param.cancelString;
        String confirmString = param.confirmString;
        View.OnClickListener cancelClickListener = param.cancelClickListener;
        View.OnClickListener confirmClickListener = param.confirmClickListener;
        String titleStr = param.title;
        int contentLayoutId = param.contentLayoutId;
        if (ctView !=null)
        {
            content.removeAllViews();
//            contentView = inflater.inflate(contentLayoutId, container, false);
            content.addView(ctView,FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        }else{
            text.setText(contentString);
        }
        if (!showtitle){title.setVisibility(View.GONE);}else{title.setText(titleStr);}
        buttonCancel.setText("取消");
        buttonCancel.setOnClickListener(cancelClickListener);
        buttonConfirm.setText("确认");
        buttonConfirm.setOnClickListener(confirmClickListener);
        return view;
    }

    public View getContentView() {
        return contentView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        content.removeAllViews();
        ButterKnife.unbind(this);
    }

    public static class Builder {
        private Param param;

        public  Builder() {
             param = new Param();
        }
        public Builder setContentLayoutId(int contentLayoutId) {
            param.setContentLayoutId(contentLayoutId);
            return this;
        }

        public Builder setContentView(View contentView)
        {
            param.contentView = contentView;
            return this;
        }

        public Builder setCancelButton(String cancelText, View.OnClickListener cancelClickListener) {
            param.cancelString = cancelText;
            param.cancelClickListener = cancelClickListener;
            return this;
        }

        public Builder setConfirmButton(String confrimText, View.OnClickListener confirmClickListener) {
            param.confirmClickListener = confirmClickListener;
            param.confirmString = confrimText;
            return this;
        }

        public Builder setTitle(String title) {
            param.title = title;
            return this;
        }

        public Builder setShowtitle(boolean showtitle) {
            param.showtitle = showtitle;
            return this;
        }

        public Builder setContent(String contenttext){
            param.contentString = contenttext;
            return this;
        }

        public EnhanceDialog create(){
            EnhanceDialog dialog = EnhanceDialog.newInstance(param);
            return dialog;
        }
    }

    private static class Param implements Serializable {
        public View contentView;
        public int contentLayoutId;
        public View.OnClickListener cancelClickListener;
        public View.OnClickListener confirmClickListener;
        public String confirmString;
        public String cancelString;
        public String title;
        public String contentString;
        public boolean showtitle = true;

        public void setContentLayoutId(int contentLayoutId) {
            this.contentLayoutId = contentLayoutId;
        }

        public void setContentView(View contentView) {
            this.contentView = contentView;
        }

        public void setCancelButton(String cancelText, View.OnClickListener cancelClickListener) {
            this.cancelString = cancelText;
            this.cancelClickListener = cancelClickListener;
        }

        public void setConfirmButton(String confrimText, View.OnClickListener confirmClickListener) {
            this.confirmClickListener = confirmClickListener;
            this.confirmString = confrimText;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setShowtitle(boolean showtitle) {
            this.showtitle = showtitle;
        }

        public void setContent(String contenttext){
            this.contentString = contenttext;
        }
    }
}
