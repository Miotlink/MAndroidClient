package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.domain.entity.RedPacket;
import com.homepaas.sls.domain.entity.RedPacketInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/4/28.
 */
public interface FavourNDataSource {

    RedPacketInfo getMyRedPacket(String status) throws GetPersistenceDataException, IOException, ResponseMetaAuthException;
}
