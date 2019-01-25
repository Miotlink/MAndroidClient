package com.homepaas.sls.domain.repository;

import rx.Observable;

/**
 * Created by JWC on 2017/7/24.
 */

public interface SubmitEvaluationRepo {
    Observable<String> submitEvaluation(String orderCode, String starLevel, String content, String evaluatedTag);
}
