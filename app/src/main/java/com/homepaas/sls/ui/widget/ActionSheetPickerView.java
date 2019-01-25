package com.homepaas.sls.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.AddViewFrameLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CJJ on 2016/9/10.
 *
 */
public class ActionSheetPickerView extends ActionSheet implements PickerView.onSelectListener, View.OnClickListener {

    PickerView pickerView;
    List<String> datas = Arrays.asList("无","家","公司","无","家","公司","无","家","公司","无","家","公司");

    @Bind(R.id.cancel)
    TextView cancelBtn;
    @Bind(R.id.confirm)
    TextView confirmBtn;
    private String address;

    public static ActionSheetPickerView newInstance() {
        
        Bundle args = new Bundle();
        
        ActionSheetPickerView fragment = new ActionSheetPickerView();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getLayoutRes() {
        return R.layout.picker_view;
    }

    @Override
    protected void init() {
        pickerView = (PickerView) getSheetView().findViewById(R.id.picker_view);
        ButterKnife.bind(this,getSheetView());
        pickerView.setData(datas);
        pickerView.setSelected(0);
        pickerView.setOnSelectListener(this);
        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
    }

    private OnItemSelectListener onItemSelectListener;

    @Override
    public void onSelect(String adress) {
        this.address = adress;
    }

    public interface OnItemSelectListener{
        void onItemSelect(String address);
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.confirm:
                pickerView.performSelect();
                onItemSelectListener.onItemSelect(address);
                dismiss();
                break;
        }
    }

}
