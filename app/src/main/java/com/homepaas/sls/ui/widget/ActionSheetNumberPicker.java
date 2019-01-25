package com.homepaas.sls.ui.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CJJ on 2016/9/23.
 */

public class ActionSheetNumberPicker extends ActionSheet implements View.OnClickListener {

    private List<String> datas;
    private OnItemClickListener onItemClickListener;
    @Bind(R.id.number_picker)
    PickerView pickerView;
    @Bind(R.id.confirm)
    TextView confirm;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.picker_title)
    TextView pickerTitle;
    String title;
    String str;
    private String prefix;
    private String suffixes;

    @Override
    protected int getLayoutRes() {
        return R.layout.number_picker_layout;
    }

    @Override
    protected void init() {
        super.init();
        ButterKnife.bind(this, getSheetView());
        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                str = text;
            }
        });
        confirm.setOnClickListener(this);
        pickerView.setData(datas);
        pickerView.setSuffixes(suffixes==null?"":suffixes);
        pickerView.setPrefix(prefix==null?"":prefix);
        pickerTitle.setText(title);

    }

    public ActionSheetNumberPicker setData(List<String> datas) {
        this.datas = datas;
        return this;
    }
    public ActionSheetNumberPicker setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public ActionSheetNumberPicker setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    @Override
    public void onClick(View v) {
        pickerView.performSelect();
        onItemClickListener.onItemClick(str);
        dismiss();
    }

    public static ActionSheetNumberPicker newInstance() {

        Bundle args = new Bundle();

        ActionSheetNumberPicker fragment = new ActionSheetNumberPicker();
        fragment.setArguments(args);
        return fragment;
    }

    public ActionSheetNumberPicker setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public ActionSheetNumberPicker setSuffixes(String suffixes) {
       this.suffixes = suffixes;
        return this;
    }

    public interface OnItemClickListener {
        void onItemClick(String str);
    }
}
