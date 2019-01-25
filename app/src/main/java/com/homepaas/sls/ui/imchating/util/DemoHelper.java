package com.homepaas.sls.ui.imchating.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.homepaas.sls.R;
import com.homepaas.sls.ui.imchating.ChatActivity;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.util.StaticData;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.Notifier;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.util.CommonUtils;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.easeui.util.UserUtil;
import com.hyphenate.helpdesk.util.Log;

import org.json.JSONObject;

import java.util.List;

public class DemoHelper {

    private static final String TAG = "DemoHelper";

    public static DemoHelper instance = new DemoHelper();

    /**
     * kefuChat.MessageListener
     */
    protected ChatManager.MessageListener messageListener = null;

    /**
     * ChatClient.ConnectionListener
     */
    private ChatClient.ConnectionListener connectionListener;

    private UIProvider _uiProvider;

    public boolean isVideoCalling;
//    private CallReceiver callReceiver;
    private Context appContext;
    private CommonAppPreferences commonAppPreferences;

    private DemoHelper(){}
    public synchronized static DemoHelper getInstance() {
        return instance;
    }

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(final Context context,boolean isDebug) {
        appContext = context;
        commonAppPreferences=new CommonAppPreferences(appContext);
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey("1125170313115150#kefuchannelapp37531");
        options.setTenantId("37531");

        //在小米手机上当app被kill时使用小米推送进行消息提示，SDK已支持，可选
        options.setMipushConfig("2882303761517507836", "5631750729836");
        //在华为手机上当APP被kill时使用华为推送进行消息提示, SDK已支持,可选
//        options.setHuaweiPushAppId("10663060");

//        options.setKefuServerAddress("http://sandbox.kefu.easemob.com");
        // 环信客服 SDK 初始化, 初始化成功后再调用环信下面的内容
        if (ChatClient.getInstance().init(context, options)){

            //设为调试模式，打成正式包时，最好设为false，以免消耗额外的资源
            //如果 apk 要打包混淆的话，debug 模式要关闭，否则会报错。
            ChatClient.getInstance().setDebugMode(isDebug);
            _uiProvider = UIProvider.getInstance();
            //初始化EaseUI
            _uiProvider.init(context);
            //调用easeui的api设置providers
            setEaseUIProvider(context);
            //设置全局监听
            setGlobalListeners();
        }
    }



    private void setEaseUIProvider(final Context context){
        //设置头像和昵称 某些控件可能没有头像和昵称，需要注意
        UIProvider.getInstance().setUserProfileProvider(new UIProvider.UserProfileProvider() {
            @Override
            public void setNickAndAvatar(Context context, Message message, final ImageView userAvatarView, TextView usernickView) {
                if (message.direct() == Message.Direct.RECEIVE) {
                    //设置接收方的昵称和头像
                    UserUtil.setAgentNickAndAvatar(context, message, userAvatarView, usernickView);
                } else {
                    //此处设置当前登录用户的头像，
                    if (userAvatarView != null){
                        Glide.with(context).load(commonAppPreferences.getHeadPortrait()).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.customer_head).into(new BitmapImageViewTarget(userAvatarView) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(appContext.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                userAvatarView.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }
                }
            }
        });


        //设置通知栏样式
        _uiProvider.getNotifier().setNotificationInfoProvider(new Notifier.NotificationInfoProvider() {
            @Override
            public String getTitle(Message message) {
                //修改标题,这里使用默认
                return null;
            }

            @Override
            public int getSmallIcon(Message message) {
                //设置小图标，这里为默认
                return 0;
            }

            @Override
            public String getDisplayedText(Message message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = CommonUtils.getMessageDigest(message, context);
                if (message.getType() == Message.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                return message.getFrom() + ": " + ticker;
            }

            @Override
            public String getLatestText(Message message, int fromUsersNum, int messageNum) {
                return null;
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
            }

            @Override
            public Intent getLaunchIntent(Message message) {
                Intent intent;
                if (isVideoCalling){
//                    intent = new Intent(context, VideoCallActivity.class);
                    intent=new Intent();
                }else{
                    //设置点击通知栏跳转事件
                    intent = new IntentBuilder(context)
                            .setTargetClass(ChatActivity.class)
                            .setServiceIMNumber(message.getFrom())
                            .setShowUserNick(true)
                            .build();
                }
                return intent;
            }
        });

        //不设置,则使用默认, 声音和震动设置
//        _uiProvider.setSettingsProvider(new UIProvider.SettingsProvider() {
//            @Override
//            public boolean isMsgNotifyAllowed(Message message) {
//                return false;
//            }
//
//            @Override
//            public boolean isMsgSoundAllowed(Message message) {
//                return false;
//            }
//
//            @Override
//            public boolean isMsgVibrateAllowed(Message message) {
//                return false;
//            }
//
//            @Override
//            public boolean isSpeakerOpened() {
//                return false;
//            }
//        });
//        ChatClient.getInstance().getChat().addMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(List<Message> msgs) {
//
//            }
//
//            @Override
//            public void onCmdMessage(List<Message> msgs) {
//
//            }
//
//            @Override
//            public void onMessageSent() {
//
//            }
//
//            @Override
//            public void onMessageStatusUpdate() {
//
//            }
//        });
    }


    private void setGlobalListeners(){
        // create the global connection listener
        /*connectionListener = new ChatClient.ConnectionListener(){

            @Override
            public void onConnected() {
                //onConnected
            }

            @Override
            public void onDisconnected(int errorcode) {
                if (errorcode == Error.USER_REMOVED){
                    //账号被移除
                }else if (errorcode == Error.USER_LOGIN_ANOTHER_DEVICE){
                    //账号在其他地方登陆
                }
            }
        };

        //注册连接监听
        ChatClient.getInstance().addConnectionListener(connectionListener);*/

        //注册消息事件监听
        registerEventListener();
//
//        IntentFilter callFilter = new IntentFilter(ChatClient.getInstance().callManager().getIncomingCallBroadcastAction());
//        if (callReceiver == null){
//            callReceiver = new CallReceiver();
//        }
//        // register incoming call receiver
//        appContext.registerReceiver(callReceiver, callFilter);
    }

    /**
     * 全局事件监听
     * 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理
     * activityList.size() <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
     */
    protected void registerEventListener(){
        messageListener = new ChatManager.MessageListener(){

            @Override
            public void onMessage(List<Message> msgs) {
                for (Message message : msgs){
                    Log.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    Conversation conversation = ChatClient.getInstance().getChat().getConversation(StaticData.IM_SERVICE);
                    int unreadMsgCount=conversation.getUnreadMsgCount();
                    if(addRedDot!=null&&hotWhere){
                        addRedDot.addRedDot();
                    }
                    //这里全局监听通知类消息,通知类消息是通过普通消息的扩展实现
                    if (message.isNotificationMessage()){
                        // 检测是否为留言的通知消息
                        String eventName = getEventNameByNotification(message);
                        if (!TextUtils.isEmpty(eventName)){
                            if (eventName.equals("TicketStatusChangedEvent") || eventName.equals("CommentCreatedEvent")){
                                // 检测为留言部分的通知类消息,刷新留言列表
                                JSONObject jsonTicket = null;
                                try{
                                    jsonTicket = message.getJSONObjectAttribute("weichat").getJSONObject("event").getJSONObject("ticket");
                                }catch (Exception e){}
                                ListenerManager.getInstance().sendBroadCast(eventName, jsonTicket);
                            }
                        }
                    }

                }
            }

            @Override
            public void onCmdMessage(List<Message> msgs) {
                for (Message message : msgs){
                    Log.d(TAG, "收到透传消息");
                    //获取消息body
                    EMCmdMessageBody cmdMessageBody = (EMCmdMessageBody) message.getBody();
                    String action = cmdMessageBody.action(); //获取自定义action
                    Log.d(TAG, String.format("透传消息: action:%s,message:%s", action, message.toString()));
                }
            }

            @Override
            public void onMessageStatusUpdate() {
            }

            @Override
            public void onMessageSent() {
            }
        };

        ChatClient.getInstance().chatManager().addMessageListener(messageListener);
    }


    /**
     * 获取EventName
     * @param message
     * @return
     */
    public String getEventNameByNotification(Message message){

        try {
            JSONObject weichatJson = message.getJSONObjectAttribute("weichat");
            if (weichatJson != null && weichatJson.has("event")) {
                JSONObject eventJson = weichatJson.getJSONObject("event");
                if (eventJson != null && eventJson.has("eventName")){
                    return eventJson.getString("eventName");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void pushActivity(Activity activity){
        _uiProvider.pushActivity(activity);
    }

    public void popActivity(Activity activity){
        _uiProvider.popActivity(activity);
    }

    public Notifier getNotifier(){
        return _uiProvider.getNotifier();
    }

    /**
     * 显示红点
     */
    public interface AddRedDot{
        void addRedDot();
    }
    private  static AddRedDot addRedDot;
    public static void setAddRedDot(AddRedDot addRedDot1){
        addRedDot=addRedDot1;
    }
    private static boolean hotWhere=false;
    public static void sethotWhere(boolean hotWhere1){
        hotWhere=hotWhere1;
    }



}
