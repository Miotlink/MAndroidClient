package com.homepaas.sls.mvp.view.personalcenter;

import com.homepaas.sls.mvp.model.CallLogModel;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
public interface CallLogView extends BaseView {

    void render(List<CallLogModel> logModels);

    void delete(int position);

    void dial(String phone);

    void disableCall(int position);
}
