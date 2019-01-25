package com.homepaas.sls.ui.search;

import android.app.Activity;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.PushInfo;
import butterknife.ButterKnife;

import javax.inject.Inject;

import butterknife.Bind;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by CJJ on 2016/7/20.
 * 确认完成和评价的窗口
 */
public class ConfirmCompleteWindow extends PopupWindow {


    private Activity activity;

    @Inject
    public ConfirmCompleteWindow(Activity activity) {
        super(MATCH_PARENT, WRAP_CONTENT);
        this.activity = activity;
    }

    /**
     * @param pushInfo
     * @return true if all params is okay
     */
    public boolean bindData(PushInfo pushInfo) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.confirm_complete_popupwindow_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
       //数据绑定
        setContentView(viewHolder.contentView);
        return true;
    }


    public static class ViewHolder {

        @Bind(R.id.content)
        View contentView;
        @Bind(R.id.complete_text)
        TextView completeText;
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.age)
        TextView age;
        @Bind(R.id.service_type)
        TextView serviceType;
        @Bind(R.id.create_time)
        TextView createTime;
        @Bind(R.id.confirmOrder)
        Button confirmOrder;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
