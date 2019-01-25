package com.homepaas.sls.ui.common.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.newmvp.base.BaseView;

import butterknife.ButterKnife;

/**
 * 结合viewpager的 懒加载fragment 没有p层的参与
 */
public abstract class SimpleLazyLoadFragment extends CommonBaseFragment implements BaseView {
    //fragment是否可见
    protected boolean isVisible = false;
    // 标志位，标志view已经初始化完成。
    private boolean isPrepared = false;
    private boolean isLoadData = false;//是否加载数据
    private boolean isViewCreated = false;
    //rootView,缓存加载后的view 当fragment结合viewpager 销毁和创建的时候，把之前缓存的view重新返回 进行优化
    private View rootView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        if (!isViewCreated) {
//            ButterKnife.bind(this, view);
            initView();
            isPrepared = true;
            isViewCreated = true;
            onVisible();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //避免
        if (rootView == null) {
            //第一次创建fragment layoutview调用
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        } else {
            //第一次以后使用历史保存的rootView，但是需要再次绑定view 的引用，否则会出现空指针            ButterKnife.bind(this, rootView);
            ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    //fragment被设置为可见时调用
    protected void onVisible() {
        if (isPrepared && isVisible) {
            if (!isLoadData) //firstLazyLoad（）,只会在fragment第一次显示的时候进行调用
            {
                firstLazyLoad();
            } else {
                lazyLoad();
            }
        }
    }
    //除了第一次懒加载，以后每次调用这个方法进行数据绑定
    public void lazyLoad() {

    }

    //懒加载方法,只会在fragment第一次显示的时候进行调用
    public void firstLazyLoad() {
        isLoadData = true;
        initData();
    }


    //fragment被设置为不可见时调用
    protected void onInvisible() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        //fragment是否可见
//        isVisible = false;
//        // 标志位，标志view已经初始化完成。
//        isPrepared = false;
//        isLoadData = false;//是否加载数据

        ((ViewGroup) rootView.getParent()).removeView(rootView);
    }
}
