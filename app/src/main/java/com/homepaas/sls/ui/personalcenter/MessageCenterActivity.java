package com.homepaas.sls.ui.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMessageCenterComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.mvp.presenter.personalcenter.MessageCenterPresenter;
import com.homepaas.sls.mvp.view.personalcenter.MessageCenterView;
import com.homepaas.sls.ui.personalcenter.adapter.MessageAdapter;
import com.homepaas.sls.ui.widget.BaseListActivity;
import com.homepaas.sls.ui.widget.CommonDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * 消息中心
 */
public class MessageCenterActivity extends BaseListActivity<Message> implements MessageCenterView {

    @Inject
    MessageCenterPresenter mPresenter;
    private Menu mMenu;

    private String IsEnablePaging = "1";
    private int PageIndex = 1;
    private String PageSize = "10";


//    IsEnablePaging：0：不启用分页，1：启用分页
//    PageIndex：第几页
//    PageSize：每一页多少条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("消息中心");
        setEmptyView(R.mipmap.message_no_item_bg, R.string.no_message, R.string.no_message_explanation);
//        setMoreLoadable(false);
        mPresenter.setMessageCenterView(this);
        mPresenter.getMessageList(IsEnablePaging, PageIndex + "", PageSize);
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<Message> list) {
        if (list == null || list.size() == 0) {
            mMenu.findItem(R.id.delete_message).setVisible(false);
        } else {
            mMenu.findItem(R.id.delete_message).setVisible(true);
        }
        MessageAdapter adapter = new MessageAdapter(list);
        adapter.setOnItemOperateListener(new MessageAdapter.OnItemOperateListener() {
            @Override
            public void onDeleteClick(int position) {
                mPresenter.deleteMessage(position);
            }

            @Override
            public void onItemClick(int position) {
                mPresenter.jump(position);
            }
        });
        return adapter;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.delete_message, menu);
        mMenu.findItem(R.id.delete_message).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_message) {
            CommonDialog dialog = new CommonDialog.Builder()
                    .setTitle("")
                    .setCancelButton("取消", null)
                    .setConfirmButton("删除", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPresenter.deleteMessageAll();
                        }
                    })
                    .setContent("是否删除所有消息?删除后不可恢复")
                    .create();
            dialog.show(getSupportFragmentManager(), null);
        } else {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerMessageCenterComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    private static final int CODE_LOGIN = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_LOGIN:
                    if (data != null && data.getBooleanExtra("Status", false))
                        mPresenter.getMessageList(IsEnablePaging, PageIndex + "", PageSize);
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof AuthException) {
            mNavigator.login(MessageCenterActivity.this, CODE_LOGIN);
        } else {
            super.showError(e);
        }
    }


    @Override
    public void showShortMessage(Message message) {
        viewMessageDetail(message);
    }

    private void viewMessageDetail(Message message) {
        String jumpCode = message.getJumpCode();
        if (jumpCode == null || jumpCode.isEmpty()) {
            mNavigator.showShortMessage(this, message.getTitle(), message.getContent());
        } else {
            switch (jumpCode) {
                case "1"://内连
                    pushUtil.startInternalView(this, message.getWebViewParam());
                    break;
                case "2":
                    pushUtil.startWebView(this, message.getWebViewParam());
                    break;
            }
        }

    }

    @Override
    public void delete(int position) {
        getRecyclerView().getAdapter().notifyItemRemoved(position);
    }

    @Override
    public void readMessage(int position) {
        getRecyclerView().getAdapter().notifyItemChanged(position);
    }

    @Override
    public void initMessageList(List<Message> models) {
        if (PageIndex == 1) {
            render(models);
        } else {
            renderMore(models);
        }
    }

    @Override
    public void deleteMessageSuccess() {
        showMessage("删除成功");
        showEmpty();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new EventPersonalInfo(EventPersonalInfo.NEW_MESSAGE));
    }

    @Override
    public void onRefresh() {
        PageIndex = 1;
        mPresenter.getMessageList(IsEnablePaging, PageIndex + "", PageSize);
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        if (mMenu != null)
            mMenu.findItem(R.id.delete_message).setVisible(false);

    }

    @Override
    public void onLoadMore() {
        PageIndex++;
        mPresenter.getMessageList(IsEnablePaging, PageIndex + "", PageSize);
    }
}
