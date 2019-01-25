package com.homepaas.sls.mvp.view.comment;

import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/4/18 0018
 *
 * @author zhudongjie .
 */
public interface CommentView extends BaseView {

    void  render(List<Evaluation> evaluationList);

    void renderMore(List<Evaluation> evaluationList);
}
