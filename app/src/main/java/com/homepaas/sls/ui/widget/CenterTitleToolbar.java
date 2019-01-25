package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.homepaas.sls.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * on 2015/12/31 0031
 *
 * @author zhudongjie .
 */
public class CenterTitleToolbar extends Toolbar {

    private TextView mCenterTitle;

    public CenterTitleToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public CenterTitleToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }


    private void init(final Context context, AttributeSet attrs, int defStyleAttr) {
        super.setTitle("");
        TypedArray a = context.obtainStyledAttributes(attrs, android.support.v7.appcompat.R.styleable.Toolbar, defStyleAttr, 0);
        String title = a.getString(android.support.v7.appcompat.R.styleable.Toolbar_title);
        a.recycle();
        mCenterTitle = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        mCenterTitle.setLayoutParams(layoutParams);

        //标题栏公共配置
        setNavigationIcon(R.mipmap.return_black);

        //清单中设置就用清单，没设置就统一颜色
        setBackgroundColor(context.getResources().getColor(R.color.app_title_bg));
        mCenterTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mCenterTitle.setTextColor(context.getResources().getColor(R.color.app_title_color));
        mCenterTitle.setText(title);
        addView(mCenterTitle);

    }

    @Override
    public void setTitle(CharSequence title) {
        if (mCenterTitle != null && !TextUtils.isEmpty(title)) {
            mCenterTitle.setText(title);
        }
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getResources().getString(resId));
    }

    private OnReturnClickListener onReturnClickListener;

    /**
     * 如果需要自行处理返回按钮事件只需要设置监听后在回调方法中处理即可
     *
     * @param onReturnClickListener
     */
    public void setOnReturnClickListener(OnReturnClickListener onReturnClickListener) {
        this.onReturnClickListener = onReturnClickListener;
    }

    public interface OnReturnClickListener {
        void onReturnClick();
    }
}
