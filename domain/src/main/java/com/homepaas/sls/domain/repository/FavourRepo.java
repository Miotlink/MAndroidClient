package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.RedPacket;
import com.homepaas.sls.domain.entity.RedPacketInfo;

import java.io.IOException;
import java.util.List;

/**
 * redpacket ,redpacket activity and so on
 */
public interface FavourRepo {

    /**
     * fetch my red-packet
     */
    RedPacketInfo getMyRedPackets(String status) throws  IOException;
}
