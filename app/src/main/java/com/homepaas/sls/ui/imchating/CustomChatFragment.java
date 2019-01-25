package com.homepaas.sls.ui.imchating;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.homepaas.sls.R;
import com.homepaas.sls.ui.imchating.chatrow.ChatRowEvaluation;
import com.homepaas.sls.ui.imchating.chatrow.ChatRowForm;
import com.homepaas.sls.ui.imchating.chatrow.ChatRowLocation;
import com.homepaas.sls.ui.imchating.chatrow.ChatRowOrder;
import com.homepaas.sls.ui.imchating.chatrow.ChatRowTrack;
import com.homepaas.sls.ui.imchating.util.DemoHelper;
import com.homepaas.sls.ui.location.location.LocationHelper;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.util.StaticData;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.Message;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.provider.CustomChatRowProvider;
import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.hyphenate.helpdesk.easeui.widget.chatrow.ChatRow;
import com.hyphenate.helpdesk.model.MessageHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by JWC on 2017/3/30.
 */

public class CustomChatFragment extends ChatFragment implements ChatFragment.EaseChatFragmentListener {
    //避免和基类定义的常量可能发生冲突,常量从11开始定义
    private static final int ITEM_MAP = 11;
    private static final int ITEM_SHORTCUT = 12;
    private static final int ITEM_VIDEO = 13;

    private static final int REQUEST_CODE_SELECT_MAP = 11;
    private static final int REQUEST_CODE_SHORTCUT = 12;

    public static final int REQUEST_CODE_CONTEXT_MENU = 13;

    //message type 需要从1开始
    public static final int MESSAGE_TYPE_SENT_MAP = 1;
    public static final int MESSAGE_TYPE_RECV_MAP = 2;
    public static final int MESSAGE_TYPE_SENT_ORDER = 3;
    public static final int MESSAGE_TYPE_RECV_ORDER = 4;
    public static final int MESSAGE_TYPE_SENT_EVAL = 5;
    public static final int MESSAGE_TYPE_RECV_EVAL = 6;
    public static final int MESSAGE_TYPE_SENT_TRACK = 7;
    public static final int MESSAGE_TYPE_RECV_TRACK = 8;
    public static final int MESSAGE_TYPE_SENT_FORM = 9;
    public static final int MESSAGE_TYPE_RECV_FORM = 10;

    private LocationHelper mLocationHelper;
    private String curLongtitude;
    private String curLatitude;
    private String imFirstSendAddress;
    private static final int REQUEST_PERMISSION_LOCATION = 1;
    private CommonAppPreferences commonAppPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DemoHelper.sethotWhere(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setUpView() {
        //这是新添加的扩展点击事件
        setChatFragmentListener(this);
        super.setUpView();
        commonAppPreferences=new CommonAppPreferences(getActivity());
        //可以在此处设置titleBar(标题栏)的属性
        titleBar.setBackgroundColor(getResources().getColor(R.color.app_title_bg));
        titleBar.setTitle("助家小秘书");
        titleBar.setLeftImageResource(R.mipmap.return_black);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }


    private void setLocalAddress() {
        curLongtitude=commonAppPreferences.getLongitude();
        curLatitude=commonAppPreferences.getLatitude();
        if (!TextUtils.isEmpty(curLongtitude) && !TextUtils.isEmpty(curLatitude)) {
            Message message = Message.createLocationSendMessage(Double.valueOf(curLatitude), Double.valueOf(curLongtitude), "用户地址", toChatUsername);
            ChatClient.getInstance().getChat().sendMessage(message, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("111", "地址发送回调：onSuccess");
                }

                @Override
                public void onError(int i, String s) {
                    Log.d("111", "地址发送回调：onError" + i + "====:" + s);
                }

                @Override
                public void onProgress(int i, String s) {
                    Log.d("111", "地址发送回调：onProgress" + i + "====:" + s);
                }
            });
        }
    }

    @Override
    public void onAvatarClick(String username) {
        //头像点击事情
    }

    @Override
    public boolean onMessageBubbleClick(Message message) {
        //消息框点击事件,return true
//        if (message.getType() == Message.Type.TXT) {
//            EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
//            String htmlStr= KeywordUtil.matcherHtml(txtBody.getMessage());
//            if(!TextUtils.isEmpty(htmlStr)){
//                PushInfo pushInfo = new PushInfo();
//                pushInfo.setUrl(htmlStr);
//                pushInfo.setTitle("支付");
//                pushInfo.setIsShare(null);
//                WebViewActivity.start(getActivity(), pushInfo);
//                return true;
//            }
//        }
        return false;
    }


    @Override
    public void onMessageBubbleLongClick(Message message) {
        //消息框长按
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_MAP: //地图
                break;
            case ITEM_SHORTCUT:
                break;
            case ITEM_VIDEO:
//                startVideoCall();
                break;
//            case ITEM_FILE:
//                //如果需要覆盖内部的,可以return true
//                //demo中通过系统API选择文件,实际app中最好是做成qq那种选择发送文件
//                return true;
            default:
                break;
        }
        //不覆盖已有的点击事件
        return false;
    }


    private void startVideoCall() {

        if (!ChatClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
            return;
        }
        inputMenu.hideExtendMenuContainer();

        Message message = Message.createTxtSendMessage("邀请客服进行实时视频", toChatUsername);
        JSONObject jsonInvit = new JSONObject();
        try {
            JSONObject jsonMsg = new JSONObject();
            jsonMsg.put("msg", "邀请客服进行实时视频");
            String appKeyStr[] = ChatClient.getInstance().getAppKey().split("#");
            jsonMsg.put("orgName", appKeyStr[0]);
            jsonMsg.put("appName", appKeyStr[1]);
            jsonMsg.put("userName", ChatClient.getInstance().getCurrentUserName());
            jsonMsg.put("resource", "mobile");
            jsonInvit.put("liveStreamInvitation", jsonMsg);
            message.setAttribute("msgtype", jsonInvit);
            message.setAttribute("type", "rtcmedia/video");
            ChatClient.getInstance().chatManager().sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public CustomChatRowProvider onSetCustomChatRowProvider() {
        return new DemoCustomChatRowProvider();
    }

    @Override
    protected void registerExtendMenuItem() {
        //demo 这里不覆盖基类已经注册的item, item点击listener沿用基类的
        super.registerExtendMenuItem();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMessageSent() {
        imFirstSendAddress=commonAppPreferences.getImSendAddress();
        if(TextUtils.isEmpty(imFirstSendAddress)||TextUtils.equals(imFirstSendAddress,"0")){
            commonAppPreferences.setImSendAddress("1");
            setLocalAddress();
        }
        messageList.refreshSelectLast();
    }

    @Override
    public void onMessage(List<Message> msgs) {
        super.onMessage(msgs);
        Conversation conversation = ChatClient.getInstance().getChat().getConversation(StaticData.IM_SERVICE);
    }

    public boolean checkFormChatRow(Message message) {
        if (message.getStringAttribute("type", null) != null) {
            try {
                String type = message.getStringAttribute("type");
                if (type.equals("html/form")) {
                    return true;
                }
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    /**
     * chat row provider
     */
    private final class DemoCustomChatRowProvider implements CustomChatRowProvider {

        @Override
        public int getCustomChatRowTypeCount() {
            //地图 和 满意度 发送接收 共4种
            //订单 和 轨迹 发送接收共4种
            // form 发送接收2种
            return 11;
        }

        @Override
        public int getCustomChatRowType(Message message) {
            //此处内部有用到,必须写否则可能会出现错位
            if (message.getType() == Message.Type.LOCATION) {
                return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_MAP : MESSAGE_TYPE_SENT_MAP;
            } else if (message.getType() == Message.Type.TXT) {
                if (MessageHelper.getEvalRequest(message) != null) {
                    return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_EVAL : MESSAGE_TYPE_SENT_EVAL;
                } else if (MessageHelper.getOrderInfo(message) != null) {
                    return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_ORDER : MESSAGE_TYPE_SENT_ORDER;
                } else if (MessageHelper.getVisitorTrack(message) != null) {
                    return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TRACK : MESSAGE_TYPE_SENT_TRACK;
                } else if (checkFormChatRow(message)) {
                    return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_FORM : MESSAGE_TYPE_SENT_FORM;
                }
            }

            return -1;
        }

        @Override
        public ChatRow getCustomChatRow(Message message, int position, BaseAdapter adapter) {
            if (message.getType() == Message.Type.LOCATION) {
                return new ChatRowLocation(getActivity(), message, position, adapter);
            } else if (message.getType() == Message.Type.TXT) {
                if (MessageHelper.getEvalRequest(message) != null) {
                    return new ChatRowEvaluation(getActivity(), message, position, adapter);
                } else if (MessageHelper.getOrderInfo(message) != null) {
                    return new ChatRowOrder(getActivity(), message, position, adapter);
                } else if (MessageHelper.getVisitorTrack(message) != null) {
                    return new ChatRowTrack(getActivity(), message, position, adapter);
                } else if (checkFormChatRow(message)) {
                    return new ChatRowForm(getActivity(), message, position, adapter);
                }
            }
            return null;
        }
    }
}
