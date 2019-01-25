package com.homepaas.sls.data.repository.datasource.impl;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.MyRedPacketBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.repository.datasource.FavourNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.RedPacket;
import com.homepaas.sls.domain.entity.RedPacketInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Administrator on 2016/4/28.
 */
@Singleton
public class FavourNDataSourceImpl implements FavourNDataSource {

    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public FavourNDataSourceImpl() {
    }

    /**获取我的红包列表
     * @param status
     * @return
     * @throws GetPersistenceDataException
     * @throws IOException
     * @throws ResponseMetaAuthException
     */
    @Override
    public RedPacketInfo getMyRedPacket(String status) throws GetPersistenceDataException, IOException, ResponseMetaAuthException {
        String token = personalInfoPDataSource.getToken();
        Response<ResponseWrapper<MyRedPacketBody>> response = apiHelper.getMyRedPacket(token, status);


        // FIXME: 2016/5/4  mock some data for test

        List<RedPacket> datas = new ArrayList<>();
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        for(int i = 0;i<20;i++)
        {
            datas.add(new RedPacket());
        }
        redPacketInfo.setPackets(datas);
       /* if (response.code() == 200)
        {
            if (response.body().meta.isValid()){
              redPacketInfo.setPackets(response.body().data.getPackets());
            }
        }*/
        return redPacketInfo;
    }
}
