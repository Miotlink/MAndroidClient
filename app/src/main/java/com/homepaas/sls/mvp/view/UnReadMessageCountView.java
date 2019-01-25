package com.homepaas.sls.mvp.view;

import com.homepaas.sls.data.entity.OrderListPopEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CJJ on 2016/9/9.
 */
public interface UnReadMessageCountView extends BaseView {

   void renderUnreadMsgCount(boolean hasUnread);

   void initOrderListPop(OrderListPopEntity data);
}
