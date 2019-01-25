package com.homepaas.sls.ui.search;

import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.mvp.model.IServiceInfo;

import java.util.List;

/**
 * 用于暂存搜索的服务数据
 * 避免使用Intent传递大数据
 *
 * @author zhudongjie .
 */
public class SearchServiceHolder {

    private static ServiceSearchInfo serviceSearchInfo;

    private SearchServiceHolder(){}

    public static void put(ServiceSearchInfo serviceSearchInfo) {
        SearchServiceHolder.serviceSearchInfo = serviceSearchInfo;
    }

    public static ServiceSearchInfo take() {
        return serviceSearchInfo;
    }
}
