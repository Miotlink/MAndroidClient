package com.homepaas.sls.data.entity.mapper;

import com.homepaas.sls.data.entity.MessageEntity;
import com.homepaas.sls.domain.entity.Message;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/2/3 0003
 *
 * @author zhudongjie .
 */
@Singleton
public class MessageMapper {

    @Inject
    public MessageMapper() {

    }

    public Message transform(MessageEntity entity) {
        if (entity != null) {
            Message message = new Message();
            message.setId(entity.getId());
            message.setContent(entity.getContent());
            message.setTitle(entity.getTitle());
            message.setJumpCode(entity.getJumpCode());
//            message.setJumpParam(entity.getJumpParam());
            message.setDate(entity.getDate());
            message.setType(entity.getType());
            message.setRead(entity.isRead());
            return message;
        }
        return null;
    }

    public List<Message> transform(List<MessageEntity> entityList) {
        List<Message> messages = new ArrayList<>();
        if (entityList != null) {
            for (MessageEntity entity : entityList) {
                messages.add(transform(entity));
            }
        }
        return messages;
    }

}
