package com.homepaas.sls.mvp.presenter.personalcenter;

import android.util.Log;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.data.network.dataformat.MessageBody;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.DeleteMessageInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.ReadMessageInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.mvp.model.mapper.MessageMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.personalcenter.MessageCenterView;
import com.homepaas.sls.newmvp.contract.MessageContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
@ActivityScope
public class MessageCenterPresenter implements Presenter {

    private static final String TAG = "MessageCenterPresenter";

    private static final boolean DEBUG = BuildConfig.DEBUG;


    private MessageCenterView messageCenterView;

    private Interactor getMessageListInteractor;

    private DeleteMessageInteractor deleteMessageInteractor;

    private ReadMessageInteractor readMessageInteractor;

    private List<Message> messageModels;

    @Inject
    MessageMapper messageMapper;

    private MessageContract.Model messageModel = ModelFactory.getInstance().getMessageModel();

    @Inject
    public MessageCenterPresenter(@Named("GetMessageListInteractor") Interactor getMessageListInteractor,
                                  DeleteMessageInteractor deleteMessageInteractor,
                                  ReadMessageInteractor readMessageInteractor) {
        this.getMessageListInteractor = getMessageListInteractor;
        this.deleteMessageInteractor = deleteMessageInteractor;
        this.readMessageInteractor = readMessageInteractor;
    }

    public void setMessageCenterView(MessageCenterView messageCenterView) {
        this.messageCenterView = messageCenterView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if (messageModel != null)
            messageModel.dispose();
//        getMessageListInteractor.unsubscribe();
        deleteMessageInteractor.unsubscribe();
        readMessageInteractor.unsubscribe();
    }

    @SuppressWarnings("unchecked")
    public void getMessageList(String IsEnablePaging, String PageIndex, String PageSize) {
        messageCenterView.showLoading(true);
        messageModel.getListData(messageCenterView, IsEnablePaging, PageIndex, PageSize, new RetrofitRequestCallBack<MessageBody>() {
            @Override
            public void successRequest(MessageBody data) {
                messageModels = data.getMessageEntities();
                messageCenterView.initMessageList(messageModels);
            }
        });
    }


    public void deleteMessageAll() {
        messageCenterView.showLoading(true);
        messageModel.deleteMessageAll(messageCenterView, new RetrofitRequestCallBack<String>() {
            @Override
            public void successRequest(String s) {
                messageCenterView.deleteMessageSuccess();
            }
        });
    }

    public void deleteMessage(final int position) {
        messageCenterView.showLoading();
        deleteMessageInteractor.setIds(messageModels.get(position).getId());
        deleteMessageInteractor.execute(new OldBaseObserver<String>(messageCenterView) {

            @Override
            public void showError(Throwable t) {
                super.showError(t);
                if (t instanceof AuthException) {
                    messageCenterView.showLogin();
                }
            }

            @Override
            public void onNext(String s) {
                messageModels.remove(position);
                messageCenterView.delete(position);
            }
        });
    }

    public void jump(final int position) {
        final Message model = messageModels.get(position);

//        switch (data.getJumpCode()) {
//            case "0"://nothing
//                break;
//            case "1"://link to app
//                break;
//            case "2"://link to web view
//                break;
//            case "3"://link to
//                break;
//        }
        messageCenterView.showShortMessage(model);
        if (DEBUG)
            Log.d(TAG, "jump: isRead = " + model.isRead());
        if (!model.isRead()) {
            readMessageInteractor.setIds(model.getId());
            readMessageInteractor.execute(new OldBaseObserver<String>(messageCenterView) {
                @Override
                public void onNext(String s) {
                    model.setRead(true);
                    messageCenterView.readMessage(position);
                }

                @Override
                public void showError(Throwable t) {
//                    super.showError(t);
                }
            });
        }
    }


}
