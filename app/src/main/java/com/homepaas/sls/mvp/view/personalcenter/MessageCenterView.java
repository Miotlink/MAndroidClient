package com.homepaas.sls.mvp.view.personalcenter;

import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
public interface MessageCenterView extends BaseView {

    void render(List<Message> models);

    void showShortMessage(Message message);

    void delete(int position);

    void readMessage(int position);

    void initMessageList(List<Message> models);
    void deleteMessageSuccess();

}
