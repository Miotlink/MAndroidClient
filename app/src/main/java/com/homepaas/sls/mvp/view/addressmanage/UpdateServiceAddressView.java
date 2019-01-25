package com.homepaas.sls.mvp.view.addressmanage;

import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CJJ on 2016/9/13.
 *
 */

public interface UpdateServiceAddressView extends BaseView {

    void onUpdateServiceAddressSuccess(AddressEntity addressEntity);
}
