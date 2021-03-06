package com.homepaas.sls.ui.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.homepaas.sls.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/14.
 */

public class FilletCommonDialog extends DialogFragment {
    private static final String KEY_PARAM = "PARAM";
    View.OnClickListener mCancelListener;

    View.OnClickListener mConfirmListener;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.content)
    TextView mContent;
    @Bind(R.id.button_cancel)
    TextView mButtonCancel;
    @Bind(R.id.button_confirm)
    TextView mButtonConfirm;
    @Bind(R.id.container)
    LinearLayout parent;

    private FilletCommonDialog.DialogParam mParam;

    static FilletCommonDialog newInstance(FilletCommonDialog.DialogParam param) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_PARAM, param);
        FilletCommonDialog fragment = new FilletCommonDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SlsStyleDialog);
    }


    public void setConfirmListener(View.OnClickListener confirmListener) {
        mConfirmListener = confirmListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        mCancelListener = cancelListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fillet_dialog_common, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mParam = getArguments().getParcelable(KEY_PARAM);
            mTitle.setVisibility(mParam.mShowTitle ? View.VISIBLE : View.GONE);
            mContent.setVisibility(mParam.mShowContent? View.VISIBLE: View.GONE);
            mButtonCancel.setVisibility(mParam.mShowButton ? View.VISIBLE : View.GONE);
            mContent.setGravity(mParam.mGravity);
            if ( mParam.mContentNoHighLight ){
                mContent.setText(mParam.mContent);
            } else {
                mContent.setText(matcherSearchText(mParam.keyColor,mParam.textSize,mParam.mContent,mParam.mKeyword));
            }
            mContent.setVisibility(mParam.mShowContent ? View.VISIBLE : View.GONE);
            mTitle.setText(mParam.mTitle);
            mButtonCancel.setText(mParam.mCancel);
            mButtonConfirm.setText(mParam.mConfirm);
            if (mParam.confirmColor != 0)
                mButtonConfirm.setTextColor(getResources().getColor(mParam.confirmColor));
            if (mParam.confirmColor != 0)
                mButtonCancel.setTextColor(getResources().getColor(mParam.cancelColor));
            if (mParam.contentColor != 0)
                mContent.setTextColor(mParam.contentColor);

        }
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelListener != null) {
                    mCancelListener.onClick(v);
                }
                dismissAllowingStateLoss();
            }
        });
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirmListener != null) {
                    mConfirmListener.onClick(v);
                }
                dismissAllowingStateLoss();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public SpannableStringBuilder matcherSearchText(int color, float textSize, String text, String keyword) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, getResources().getDisplayMetrics());
        SpannableStringBuilder ss = new SpannableStringBuilder(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(size), start , end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }
    static class DialogParam implements Parcelable {

        String mTitle = "";

        boolean mShowTitle = true;

        boolean mShowButton = true;

        boolean mShowContent = true;

        boolean mContentNoHighLight = true;

        String mKeyword = "";

        float textSize;

        int keyColor;

        String mContent = "";

        String mCancel = "";

        String mConfirm = "";

        int confirmColor;

        int cancelColor;

        int contentColor;

        int mGravity;

        public DialogParam() {
        }

        protected DialogParam(Parcel in) {
            mTitle = in.readString();
            mShowTitle = in.readByte() != 0;
            mShowButton = in.readByte() != 0;
            mShowContent = in.readByte() != 0;
            mContentNoHighLight = in.readByte() != 0;
            mKeyword = in.readString();
            textSize = in.readFloat();
            keyColor = in.readInt();
            mContent = in.readString();
            mCancel = in.readString();
            mConfirm = in.readString();
            confirmColor = in.readInt();
            cancelColor = in.readInt();
            mGravity = in.readInt();
            contentColor = in.readInt();
        }

        public static final Creator<DialogParam> CREATOR = new Creator<DialogParam>() {
            @Override
            public FilletCommonDialog.DialogParam createFromParcel(Parcel in) {
                return new FilletCommonDialog.DialogParam(in);
            }

            @Override
            public FilletCommonDialog.DialogParam[] newArray(int size) {
                return new FilletCommonDialog.DialogParam[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mTitle);
            dest.writeByte((byte) (mShowTitle ? 1 : 0));
            dest.writeByte((byte) (mShowButton ? 1 : 0));
            dest.writeByte((byte)(mShowContent ? 1: 0));
            dest.writeByte((byte)(mContentNoHighLight ? 1 : 0));
            dest.writeString(mKeyword);
            dest.writeFloat(textSize);
            dest.writeInt(keyColor);
            dest.writeString(mContent);
            dest.writeString(mCancel);
            dest.writeString(mConfirm);
            dest.writeInt(confirmColor);
            dest.writeInt(cancelColor);
            dest.writeInt(mGravity);
            dest.writeInt(contentColor);
        }
    }
    public static class Builder {

        private FilletCommonDialog.DialogParam mParam;

        private View.OnClickListener mCancelListener;

        private View.OnClickListener mConfirmListener;

        public Builder() {
            mParam = new FilletCommonDialog.DialogParam();
        }

        public FilletCommonDialog.Builder setTitle(String title) {
            mParam.mTitle = title;
            showTitle(true);
            return this;
        }

        public FilletCommonDialog.Builder showTitle(boolean show) {
            mParam.mShowTitle = show;
            return this;
        }


        public FilletCommonDialog.Builder showButton(boolean show) {
            mParam.mShowButton = show;
            return this;
        }

        public FilletCommonDialog.Builder showContent(boolean show){
            mParam.mShowContent = show;
            return this;
        }

        public FilletCommonDialog.Builder setContentNoHighlLight(boolean noHighLight){
            mParam.mContentNoHighLight = noHighLight;
            return this;
        }

        public FilletCommonDialog.Builder setKeyword(String keyword){
            mParam.mKeyword = keyword;
            return this;
        }

        public FilletCommonDialog.Builder setKeySize(float size){
            mParam.textSize = size;
            return this;
        }

        public FilletCommonDialog.Builder setKeyColor(int color){
            mParam.keyColor = color;
            return this;
        }
        public FilletCommonDialog.Builder setContentGravity(int gravity) {
            mParam.mGravity = gravity;
            return this;
        }

        public FilletCommonDialog.Builder setContent(String content) {
            mParam.mContent = content;
            return this;
        }

        public FilletCommonDialog.Builder setCancelButton(String text, View.OnClickListener listener) {
            mParam.mCancel = text;
            mCancelListener = listener;
            return this;
        }

        public FilletCommonDialog.Builder setConfirmButton(String text, View.OnClickListener listener) {
            mParam.mConfirm = text;
            mConfirmListener = listener;
            return this;
        }

        public FilletCommonDialog.Builder setConfirmTextColor(int colorRes) {
            mParam.confirmColor = colorRes;
            return this;
        }

        public Builder setCancelTextColor(int colorRes) {
            mParam.cancelColor = colorRes;
            return this;
        }

        public FilletCommonDialog.Builder setContentColor(@ColorRes int colorRes) {
            mParam.contentColor = colorRes;
            return this;
        }

        public FilletCommonDialog create() {
            FilletCommonDialog fragment = newInstance(mParam);
            fragment.setCancelListener(mCancelListener);
            fragment.setConfirmListener(mConfirmListener);
            return fragment;
        }

    }

}
