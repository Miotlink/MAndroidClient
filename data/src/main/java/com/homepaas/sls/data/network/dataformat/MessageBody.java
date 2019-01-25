package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.entity.MessageEntity;
import com.homepaas.sls.domain.entity.Message;

import java.util.List;

/**
 * on 2016/2/3 0003
 *
 * @author zhudongjie .
 */
public class MessageBody {

    @SerializedName("Messages")
    private List<Message> messageEntities;

    public List<Message> getMessageEntities() {
        return messageEntities;
    }

    public void setMessageEntities(List<Message> messageEntities) {
        this.messageEntities = messageEntities;
    }
}
