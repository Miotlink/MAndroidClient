package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.PushServiceIdRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.data.repository.datasource.PushNDataSource;
import com.homepaas.sls.domain.param.Constant;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by CJJ on 2016/7/29.
 */
@Singleton
public class PushNDataSourceImpl implements PushNDataSource {

    @Inject
    RestApiHelper restApiHelper;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public PushNDataSourceImpl() {
    }

    @Override
    public String uploadClientId(String clientId) {

        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<Void>> response = restApiHelper.uploadPushDeviceId(new PushServiceIdRequest(token, clientId));
            if (response.code()==200){
                Meta meta = response.body().meta;
                if (meta.isValid())
                {
                    return meta.getErrorMsg();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return "";
    }
}
