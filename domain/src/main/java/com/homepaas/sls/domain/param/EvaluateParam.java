package com.homepaas.sls.domain.param;

import java.util.List;

/**
 * on 2016/6/23 0023
 *
 * @author zhudongjie
 */
public class EvaluateParam {

    public final String id;

    public final String type;

    public final String score;

    public final String content;

    public final String orderId;

    public final List<String> pictures;

    public EvaluateParam(String id, String type, String score, String content, String orderId, List<String> pictures) {
        this.id = id;
        this.type = type;
        this.score = score;
        this.content = content;
        this.orderId = orderId;
        this.pictures = pictures;
    }
}
