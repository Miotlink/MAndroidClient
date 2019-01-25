package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.homepaas.sls.R;
import com.homepaas.sls.mvp.model.PopuModle;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Sherily on 2017/2/13.
 */

public class MorePopuWindow extends PopupWindow implements MorePopuWindowAdapter.OnAction {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private MorePopuWindowAdapter morePopuWindowAdapter;

    @Override
    public void handler(int position,boolean status) {
        if (onItemClickListener != null)
            onItemClickListener.clickItem(position,status);
    }

    public interface OnItemClickListener {
        void clickItem(int position,boolean status);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MorePopuWindow(Context context, OnItemClickListener onItemClickListener) {
        super(context);
        this.onItemClickListener = onItemClickListener;
        View view = LayoutInflater.from(context).inflate(R.layout.more_popu_window_layout, null);
        setContentView(view);
        ButterKnife.bind(this, view);
//        setWidth(WRAP_CONTENT);
//        setHeight(WRAP_CONTENT);
        //让popup把焦点从外部抢夺过来，setFocusable(true)即可，至于setTouchable(true)，不用也行。这个时候也不用判断状态了，按钮中只处理显示代码就行了
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
//        setFocusable(false);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        initView(context);
    }
    private void initView(Context context){
        morePopuWindowAdapter = new MorePopuWindowAdapter();
        morePopuWindowAdapter.setOnAction(this);
        recyclerView.setAdapter(morePopuWindowAdapter);

    }
    public void addItemDecoration(SimpleItemDecoration simpleItemDecoration){
        recyclerView.addItemDecoration(simpleItemDecoration);
    }
    public void setList(List<PopuModle> datas){
        morePopuWindowAdapter.setDatas(datas);
    }

}
