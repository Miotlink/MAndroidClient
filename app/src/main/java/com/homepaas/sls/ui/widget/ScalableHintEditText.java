package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;


import com.homepaas.sls.R;

/**
 * Created by CJJ on 2016/9/10.
 *
 */
public class ScalableHintEditText extends AppCompatEditText {

    private float hintTextSize;

    public ScalableHintEditText(Context context) {
        super(context);
    }

    public ScalableHintEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScalableHintEditText);
        hintTextSize = array.getDimension(
                R.styleable.ScalableHintEditText_hintTextSize,
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP,
                        8,
                        getResources().getDisplayMetrics())
        );
        setHint();

    }
    public void setHint(){
        SpannableString ss = new SpannableString(getHint());//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) hintTextSize,false);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.setHint(ss);
    }

}
