package com.homepaas.sls.ui.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerMyCommentComponent;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.mvp.presenter.comment.CommentPresenter;
import com.homepaas.sls.mvp.view.comment.CommentView;
import com.homepaas.sls.ui.comment.adapter.CommentAdapter;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.widget.BaseListActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MyCommentActivity extends BaseListActivity<Evaluation> implements CommentView {

    private static final int ADD_COMMENT = 1;

    @Inject
    CommentPresenter commentPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEmptyView(R.layout.activity_my_comment_empty_view);
        setTitle("我的评价");
        setMoreLoadable(true);
        commentPresenter.setCommentView(this);
        commentPresenter.getEvaluations();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commentPresenter.destroy();
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerMyCommentComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build().inject(this);
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<Evaluation> list) {
        CommentAdapter  adapter = new CommentAdapter(list);
        adapter.setAddCommentClickListener(new CommentAdapter.AddCommentClickListener() {
            @Override
            public void onAdd(int position) {
                startActivityForResult(new Intent(MyCommentActivity.this, AddEvaluateActivity.class), ADD_COMMENT);
            }
        });
        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COMMENT && resultCode == RESULT_OK) {

        }
    }

    @Override
    protected void retrieveData() {
        commentPresenter.getEvaluations();
    }

    @Override
    public void onRefresh() {
        commentPresenter.getEvaluations();
    }

    @Override
    public void onLoadMore() {
        commentPresenter.loadMore();
    }

}
