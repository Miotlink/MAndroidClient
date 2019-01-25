package com.homepaas.sls.mvp.model.mapper;

import android.support.annotation.DrawableRes;

import com.homepaas.sls.R;
import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.mvp.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
@Singleton
public class MessageMapper {

    @Inject
    public MessageMapper() {
    }

    public List<MessageModel> transform(List<Message> messageList) {
        List<MessageModel> modelList = new ArrayList<>();
        if (messageList != null) {
            for (Message message : messageList) {
                modelList.add(transform(message));
            }
        }
        return modelList;
    }

    public MessageModel transform(Message message) {
        MessageModel messageModel = null;
        if (message != null) {
            messageModel = new MessageModel(message.getId());
            messageModel.setContent(message.getContent());
            messageModel.setDate(formatDate(message.getDate()));
            messageModel.setJumpCode(message.getJumpCode());
//            messageModel.setJumpParam(message.getJumpParam());
            messageModel.setTitle(message.getTitle());
            messageModel.setIconResource(chooseIcon(message.getType()));
            messageModel.setRead(message.isRead());
        }
        return messageModel;
    }

    @DrawableRes
    private int chooseIcon(String typeCode){
        switch (typeCode){
            case "1"://活动消息
                return R.mipmap.message_icon_activity;
            case "2"://订单消息
                return R.mipmap.message_icon_evalution;
            case "3"://优惠消息
                return R.mipmap.message_icon_red_package;
            case "4"://账户消息
                return R.mipmap.message_icon_balance;
            default:
                return R.mipmap.message_icon_activity;
        }
    }

    private String formatDate(String timestamp){
        return TimeUtils.formatDate(timestamp);
    }
}
