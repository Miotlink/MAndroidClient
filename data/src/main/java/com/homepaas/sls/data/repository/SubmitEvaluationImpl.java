package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.request.SubmitEvaluationRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.repository.SubmitEvaluationRepo;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by JWC on 2017/7/24.
 */

public class SubmitEvaluationImpl implements SubmitEvaluationRepo {
    @Inject
    RestApiHelper apiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public SubmitEvaluationImpl() {
    }

    @Override
    public Observable<String> submitEvaluation(String orderCode, String starLevel, String content, String evaluatedTag) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            SubmitEvaluationRequest request = new SubmitEvaluationRequest(token, orderCode, starLevel, content, evaluatedTag);
            return apiHelper.restApi()
                    .submitEvaluation(request)
                    .flatMap(new RestApiHelper.ResponseParseFunc<String>());
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
