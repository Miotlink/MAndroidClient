package com.homepaas.sls.ui.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.homepaas.sls.R;
import com.homepaas.sls.util.CountDownTimerUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sherily on 2016/11/3.
 */

public class NewCommonDialog extends DialogFragment implements CountDownTimerUtils.OnFinishListner {
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
    private CountDownTimerUtils countDownTimerUtils;

    private DialogParam mParam;

    static NewCommonDialog newInstance(DialogParam param) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_PARAM, param);
        NewCommonDialog fragment = new NewCommonDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SlsStyleDialog);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void setConfirmListener(View.OnClickListener confirmListener) {
        mConfirmListener = confirmListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        mCancelListener = cancelListener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WindowManager.LayoutParams attributes = getDialog().getWindow().getAttributes();
        attributes.width = getResources().getDisplayMetrics().widthPixels*80/100;
        getDialog().getWindow().setAttributes(attributes);
    }

    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {
            //  容错处理,不做操作
        }
    }
    public void dismissDialog() {
        if ( getActivity() != null && !getActivity().isFinishing()) {
            super.dismissAllowingStateLoss();
            if (countDownTimerUtils != null){
                countDownTimerUtils.cancel();
                countDownTimerUtils = null;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_dialog_common, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mParam = getArguments().getParcelable(KEY_PARAM);
            mTitle.setVisibility(mParam.mShowTitle ? View.VISIBLE : View.GONE);
            mButtonCancel.setVisibility(mParam.mShowButton ? View.VISIBLE : View.GONE);
            mContent.setGravity(mParam.mGravity);
            if ( mParam.mContentNoHighLight ){
                if (mParam.mCountDown){
                    long curTime = System.currentTimeMillis()/1000;
                    long offsetTime = (long) Math.ceil(curTime - mParam.createDialogTime/1000);
                    long curCountDowmTime = mParam.countDownTime - 500;
                    if (curCountDowmTime > 0){
                        countDownTimerUtils = new CountDownTimerUtils(curCountDowmTime, mParam.countDownInterval, mContent);
                        countDownTimerUtils.setStyle(mParam.keyColor, mParam.textSize,mParam.mContent);
                        countDownTimerUtils.setOnFinishListner(this);
                        countDownTimerUtils.start();
                    }
                } else {
                    mContent.setText(mParam.mContent);
                }
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
                mContent.setTextColor(getResources().getColor(mParam.contentColor));

        }
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelListener != null) {
                    mCancelListener.onClick(v);
                }
                dismissDialog();
            }
        });
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirmListener != null) {
                    mConfirmListener.onClick(v);
                }
                dismissDialog();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        if (countDownTimerUtils != null){
            countDownTimerUtils.cancel();
            countDownTimerUtils = null;
        }
        super.onDestroy();
    }

    public SpannableStringBuilder matcherSearchText(int color, float textSize, String text, String keyword) {
        if ( textSize == 0.0f )
            textSize = 15f;
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

    @Override
    public void onFinish(boolean isFinish) {
        countDownTimerUtils.cancel();
        countDownTimerUtils = null;
        dismissDialog();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(countDownTimerUtils!=null){
            countDownTimerUtils.cancel();
            countDownTimerUtils=null;
        }
    }

    static class DialogParam implements Parcelable {

        String mTitle = "";

        boolean mShowTitle = true;

        boolean mShowButton = true;

        boolean mShowContent = true;

        boolean mContentNoHighLight = true;

        boolean mCountDown = false;

        long countDownTime;

        long countDownInterval;

        long createDialogTime;

        String mKeyword = "";

        float textSize;

        int keyColor;

        String mContent = "";

        String mCancel = "";

        String mConfirm = "";

        int confirmColor = R.color.appPrimary;

        int cancelColor = R.color.appPrimary;

        int contentColor = R.color.appTextNormal;

        int mGravity;

        public DialogParam() {
        }

        protected DialogParam(Parcel in) {
            mTitle = in.readString();
            mShowTitle = in.readByte() != 0;
            mShowButton = in.readByte() != 0;
            mShowContent = in.readByte() != 0;
            mContentNoHighLight = in.readByte() != 0;
            mCountDown = in.readByte() != 0;
            countDownTime  = in.readLong();
            countDownInterval = in.readLong();
            createDialogTime = in.readLong();
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
            public DialogParam createFromParcel(Parcel in) {
                return new DialogParam(in);
            }

            @Override
            public DialogParam[] newArray(int size) {
                return new DialogParam[size];
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
            dest.writeByte((byte)(mCountDown ? 1 : 0));
            dest.writeLong(countDownTime);
            dest.writeLong(countDownInterval);
            dest.writeLong(createDialogTime);
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

        private DialogParam mParam;

        private View.OnClickListener mCancelListener;

        private View.OnClickListener mConfirmListener;

        public Builder() {
            mParam = new DialogParam();
        }

        public Builder setTitle(String title) {
            mParam.mTitle = title;
            showTitle(true);
            return this;
        }

        public Builder showTitle(boolean show) {
            mParam.mShowTitle = show;
            return this;
        }

        public Builder showButton(boolean show) {
            mParam.mShowButton = show;
            return this;
        }

        public Builder showContent(boolean show){
            mParam.mShowContent = show;
            return this;
        }

        public Builder setContentNoHighlLight(boolean noHighLight){
            mParam.mContentNoHighLight = noHighLight;
            return this;
        }

         public Builder setmCountDown(boolean isCountDown){
             mParam.mCountDown = isCountDown;
             return this;
         }

         public Builder setCountDown(long countDownTime, long countDownInterval, long createTime){
             mParam.countDownTime = countDownTime;
             mParam.countDownInterval = countDownInterval;
             mParam.createDialogTime = createTime;
             if (countDownTime <= 0){
                 mParam.mCountDown = false;
             }else {
                 mParam.mCountDown = true;
             }
             return this;
         }

        public Builder setKeyword(String keyword){
            mParam.mKeyword = keyword;
            return this;
        }

        public Builder setKeySize(float size){
            mParam.textSize = size;
            return this;
        }

        public Builder setKeyColor(int color){
            mParam.keyColor = color;
            return this;
        }
        public Builder setContentGravity(int gravity) {
            mParam.mGravity = gravity;
            return this;
        }

        public Builder setContent(String content) {
            mParam.mContent = content;
            return this;
        }

        public Builder setCancelButton(String text, View.OnClickListener listener) {
            mParam.mCancel = text;
            mCancelListener = listener;
            return this;
        }

        public Builder setConfirmButton(String text, View.OnClickListener listener) {
            mParam.mConfirm = text;
            mConfirmListener = listener;
            return this;
        }

        public Builder setConfirmTextColor(int color) {
            mParam.confirmColor = color;
            return this;
        }
        public Builder setCancelTextColor (int color) {
            mParam.cancelColor = color;
            return this;
        }

        public Builder setContentColor(@ColorRes int colorRes) {
            mParam.contentColor = colorRes;
            return this;
        }

        public NewCommonDialog create() {
            NewCommonDialog fragment = newInstance(mParam);
            fragment.setCancelListener(mCancelListener);
            fragment.setConfirmListener(mConfirmListener);
            return fragment;
        }

    }

}
