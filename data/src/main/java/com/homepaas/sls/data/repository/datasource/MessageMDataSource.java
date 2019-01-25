package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.domain.exception.GetDataException;

import java.util.List;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
public interface MessageMDataSource {

    List<Message> getMessageList() throws GetDataException;

    int getUnreadMessageCount() throws GetDataException;
}
