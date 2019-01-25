package com.homepaas.sls.ui.widget;

import java.util.List;

/**
 * on 2016/7/11 0011
 *
 * @author zhudongjie
 */
public interface Refreshable<T> {

    void refresh(List<T> list);
}
