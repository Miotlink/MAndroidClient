package com.homepaas.sls.mvp.view.addressmanage;

import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by CJJ on 2016/9/13.
 * 我的服务地址列表 View
 */

public interface ManageAddressView extends BaseView {
    void renderAddress(List<AddressEntity> addressCollection);

    void deleteSuccess(int delIndex);
}
