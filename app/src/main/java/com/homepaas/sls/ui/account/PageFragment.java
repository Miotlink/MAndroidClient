package com.homepaas.sls.ui.account;

import android.content.Context;

import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;

/**
 * on 2016/6/30 0030
 *
 * @author zhudongjie
 */
public abstract class PageFragment extends CommonBaseFragment {

    public interface OnRefreshEndLister{

        void onRefreshEnd();
    }

    protected OnRefreshEndLister mOnRefreshEndLister;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRefreshEndLister){
            mOnRefreshEndLister = (OnRefreshEndLister) context;
        }
    }

    public abstract void refresh();

    @Override
    public void onDetach() {
        super.onDetach();
        mOnRefreshEndLister = null;
    }
}
