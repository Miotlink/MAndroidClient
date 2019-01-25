package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.Evaluation;

import java.util.List;

/**
 * on 2016/6/17 0017
 *
 * @author zhudongjie
 */
public class EvaluationBody {

    @SerializedName("EvaluationList")
    private List<Evaluation> evaluations;


    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

}
