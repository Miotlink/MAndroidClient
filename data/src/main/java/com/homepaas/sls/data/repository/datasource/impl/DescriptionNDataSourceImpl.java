package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.DescriptionNDataSource;
import com.homepaas.sls.domain.entity.DescriptionInfo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by CJJ on 2016/7/13.
 */
@Singleton
public class DescriptionNDataSourceImpl implements DescriptionNDataSource {

    @Inject
    RestApiHelper apiHelper;

    @Inject
    public DescriptionNDataSourceImpl() {
    }

    @Override
    public String getDescription(String code) {
        try {

            Response<ResponseWrapper<DescriptionInfo>> response = apiHelper
                    .getDescription(code);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    {
                        return response.body().data.getDescription();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "";
    }
}
