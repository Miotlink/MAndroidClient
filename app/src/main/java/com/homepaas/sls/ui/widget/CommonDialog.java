package com.homepaas.sls.ui.widget;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homepaas.sls.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * on 2016/4/1 0001
 *
 * @author zhudongjie .
 */
public class CommonDialog extends CustomDialogFragment {

    private static final String KEY_PARAM = "PARAM";

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.content)
    TextView mContent;

    @Bind(R.id.button_cancel)
    TextView mButtonCancel;

    @Bind(R.id.button_confirm)
    TextView mButtonConfirm;

    View.OnClickListener mCancelListener;

    View.OnClickListener mConfirmListener;

    private DialogParam mParam;
    private boolean mShowAction;

    static CommonDialog newInstance(DialogParam param) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_PARAM, param);
        CommonDialog fragment = new CommonDialog();
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
        View view = inflater.inflate(R.layout.dialog_common, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mParam = getArguments().getParcelable(KEY_PARAM);
            mTitle.setVisibility(mParam.mShowTitle ? View.VISIBLE : View.GONE);
            mContent.setVisibility(mParam.mShowContent ? View.VISIBLE : View.GONE);
            if (TextUtils.isEmpty(mParam.mTitle)) {
                mTitle.setVisibility(View.GONE);
            } else {
                mTitle.setVisibility(View.VISIBLE);
                mTitle.setText(mParam.mTitle);
            }
            mContent.setText(mParam.mContent);
            mButtonCancel.setText(mParam.mCancel);
            mButtonConfirm.setText(mParam.mConfirm);
            mButtonConfirm.setTextColor(getResources().getColor(mParam.mConfirmTextColor));
            mButtonCancel.setTextColor(getResources().getColor(mParam.mCancelTextColor));
            mShowAction = mParam.mShowAction;
            mButtonCancel.setVisibility(mShowAction ? View.VISIBLE : View.GONE);
            mButtonConfirm.setVisibility(mShowAction ? View.VISIBLE : View.GONE);

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

    static class DialogParam implements Parcelable {

        String mTitle = "";

        boolean mShowTitle = true;

        boolean mShowContent = true;

        String mContent = "";

        String mCancel = "";

        String mConfirm = "";

        int mConfirmTextColor = R.color.black;
        int mCancelTextColor = R.color.black;
        public boolean mShowAction = true;

        public DialogParam() {
        }

        protected DialogParam(Parcel in) {
            mTitle = in.readString();
            mShowTitle = in.readByte() != 0;
            mShowContent = in.readByte() != 0;
            mContent = in.readString();
            mCancel = in.readString();
            mConfirm = in.readString();
            mConfirmTextColor = in.readInt();
            mCancelTextColor = in.readInt();
            mShowAction = in.readInt() == 0 ? false : true;
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
            dest.writeByte((byte) (mShowContent ? 1 : 0));
            dest.writeString(mContent);
            dest.writeString(mCancel);
            dest.writeString(mConfirm);
            dest.writeInt(mConfirmTextColor);
            dest.writeInt(mCancelTextColor);
            dest.writeInt(mShowAction ? 0 : 1);
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
            return this;
        }

        public Builder showTitle(boolean show) {
            mParam.mShowTitle = show;
            return this;
        }

        public Builder showContent(boolean show) {
            mParam.mShowContent = show;
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
            mParam.mConfirmTextColor = color;
            return this;
        }

        public Builder setCancelTextColor(int color) {
            mParam.mCancelTextColor = color;
            return this;
        }

        public Builder showAction(boolean whetherShowAction) {
            mParam.mShowAction = whetherShowAction;
            return this;
        }

        public CommonDialog create() {
            CommonDialog fragment = CommonDialog.newInstance(mParam);
            fragment.setCancelListener(mCancelListener);
            fragment.setConfirmListener(mConfirmListener);
            return fragment;
        }

    }
}
