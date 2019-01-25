package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
public interface SearchView extends BaseView {

    void render(List<String> lifeService);

    void prepareSuggestion(List<String> lifeService);

    void showSearchResult(ServiceSearchInfo searchInfo);


}
