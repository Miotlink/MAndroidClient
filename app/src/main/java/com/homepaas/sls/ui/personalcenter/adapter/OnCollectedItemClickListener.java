package com.homepaas.sls.ui.personalcenter.adapter;

import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.mvp.model.IServiceInfo;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
public interface OnCollectedItemClickListener {

    void onItemClick(int position, String id,boolean isWorker);

    /**
     * 此处不提供点赞功能
     */
    @Deprecated
    void onLikeChecked(int position, boolean checked, IServiceInfo item);

    void onItemDelete(int position,  String id,boolean isWorker);
}
