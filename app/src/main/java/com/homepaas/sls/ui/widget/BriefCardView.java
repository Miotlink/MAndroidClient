package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.IService;

import java.util.List;

import butterknife.Bind;


/**
 * Date: 2016/12/19.
 * Author: fmisser
 * Description:
 */

public class BriefCardView extends LinearLayout implements BriefCardAdapter.OnAction {

//    @Bind(R.id.viewPager_brief_card)
    private WrapContentHeightViewPager viewPager;

//    private WrapContentHeightViewPager viewPager;

    public BriefCardView(Context context) {
        super(context);
        init(context);
    }

    public BriefCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BriefCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_brief_card, this);
        viewPager = (WrapContentHeightViewPager) findViewById(R.id.viewPager_brief);
        initViewPager();
    }

//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View view = inflater.inflate(R.layout.layout_brief_card, null);
//        ButterKnife.bind(this, view);
//        addView(view);
//        initViewPager();
//    }

    public void setPosition(int position) {
        viewPager.setCurrentItem(position);
    }

    public int getPosition() {
        return viewPager.getCurrentItem();
    }

    @Override
    public void action(IService service, int actionCode) {
        if (handleManage != null) {
            handleManage.hadle(service, actionCode);
        }
    }

    public interface HandleManage {
        void hadle(IService service, int actionCode);
    }

    private HandleManage handleManage;

    public void setHandleManage(HandleManage handleManage) {
        this.handleManage = handleManage;
    }

    public interface onSelectedPositon {
        void onSelect(int position, IService service);
    }

    private onSelectedPositon onSelectedPositon;

    public void setOnSelectedPositon(BriefCardView.onSelectedPositon onSelectedPositon) {
        this.onSelectedPositon = onSelectedPositon;
    }

    private BriefCardAdapter briefCardAdapter;

    private List<IService> serviceList;
    public void setData(List<IService> services) {
        serviceList = services;
        briefCardAdapter.setData(services);
        viewPager.resetLoopForDataChanged();
    }

    //初始化ViewPager
    protected void initViewPager() {
        viewPager.setFocusable(true);
        briefCardAdapter = new BriefCardAdapter(getContext());
        viewPager.setAdapter(briefCardAdapter);
        briefCardAdapter.setOnAction(this);
        int screenWidth = Utils.getScreenWidth(getContext());
        int unit = screenWidth / 20;
        viewPager.setPageMargin(unit / 3);
        viewPager.setPadding(unit * 2, 0, unit * 2, 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (onSelectedPositon != null) {
                    onSelectedPositon.onSelect(viewPager.getCurrentItem(), serviceList.get(viewPager.getCurrentItem()));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        viewPager.setPageTransformer(true, new ZoomOutPageTransformer(viewPager));
    }

}
