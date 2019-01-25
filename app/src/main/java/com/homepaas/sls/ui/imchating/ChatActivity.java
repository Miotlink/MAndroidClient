package com.homepaas.sls.ui.imchating;

import android.os.Bundle;

import com.homepaas.sls.R;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.hyphenate.helpdesk.easeui.util.Config;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.VisitorTrack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by JWC on 2017/3/30.
 * 环信客服
 */

public class ChatActivity extends CommonBaseActivity {
    private String imService;
    private ChatFragment chatFragment;
    private ServiceItem serviceItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView() {
        sendDefaultWelcomMessage();
        imService = StaticData.IM_SERVICE;
        chatFragment = new CustomChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        Bundle bundle = getIntent().getBundleExtra(Config.EXTRA_BUNDLE);
        if (bundle != null) {
            serviceItem = (ServiceItem) bundle.getSerializable(StaticData.SERVICE_ITEM);
        }
        if (serviceItem != null) {
            sendTrackMessage(serviceItem);
        }
    }

    @Override
    protected void initData() {

    }


//    /**
//     * 发送订单消息
//     *
//     * 不发送则是saveMessage
//     * @param serviceName
//     */
//    private void sendOrderMessage(String serviceName){
//        Message message = Message.createTxtSendMessage(serviceName, imService);
//        message.addContent(MessageHelper.createOrderInfo(selectedIndex));
//        ChatClient.getInstance().chatManager().saveMessage(message);
//    }


    /**
     * 发送轨迹消息
     *
     * @param serviceItem
     */
    private void sendTrackMessage(ServiceItem serviceItem) {
        Message message = Message.createTxtSendMessage(serviceItem.getServiceName(), imService);
        message.addContent(createVisitorTrack(serviceItem));
        ChatClient.getInstance().chatManager().sendMessage(message);
    }

    public void sendDefaultWelcomMessage() {
        //本地插入小秘书打招呼消息 未登录和登录都需要提示，只提示一次除非切换账号
        if (!PreferencesUtil.getBoolean(StaticData.IM_WELCOME)) {
            Message message = Message.createReceiveMessage(Message.Type.TXT);  //创建消息
            EMTextMessageBody body = new EMTextMessageBody(getResources().getString(R.string.im_welcome));
            message.setFrom(StaticData.IM_SERVICE);     //  设置消息发送方为IM服务号
            message.addBody(body);
            JSONObject weichat = new JSONObject();
            JSONObject agent = new JSONObject();
            try {
                agent.put("userNickname", getResources().getString(R.string.im_welcome_name));
                weichat.put("agent", agent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            message.setAttribute("weichat", weichat);
            message.setStatus(Message.Status.SUCCESS);
            message.setMsgId(UUID.randomUUID().toString());//设置消息id
            ChatClient.getInstance().chatManager().saveMessage(message);     //插入到本地
            PreferencesUtil.saveBoolean(StaticData.IM_WELCOME, true);
        }
    }

    public static VisitorTrack createVisitorTrack(ServiceItem serviceItem) {
        VisitorTrack track = ContentFactory.createVisitorTrack(null);
        track.title(serviceItem.getServiceName())
                .price(serviceItem.getPrice())
                .desc(serviceItem.getServiceName())
                .imageUrl(serviceItem.getIconUrl())
                .itemUrl(serviceItem.getDetailUrl());
        return track;
    }
}
