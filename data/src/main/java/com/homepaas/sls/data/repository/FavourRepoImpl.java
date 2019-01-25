package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.repository.datasource.FavourNDataSource;
import com.homepaas.sls.domain.entity.RedPacket;
import com.homepaas.sls.domain.entity.RedPacketInfo;
import com.homepaas.sls.domain.repository.FavourRepo;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/4/28.
 * 优惠活动数据仓库，包括红包列表，优惠信息等内容
 */
@Singleton
public class FavourRepoImpl implements FavourRepo {

    @Inject
    FavourNDataSource favourNDataSource;

    @Inject
    public FavourRepoImpl() {
    }

    @Override
    public RedPacketInfo getMyRedPackets(String status) throws IOException {
        try {
            RedPacketInfo myRedPacket = favourNDataSource.getMyRedPacket(status);

            return myRedPacket;
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }
}
