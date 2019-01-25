package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.LifeService;

import java.util.List;

/**
 * on 2016/2/14 0014
 *
 * @author zhudongjie .
 */
public interface LifeServiceNDataSource {

    List<LifeService> getLifeServiceList() throws GetNetworkDataException,  ResponseMetaDataException;

    List<LifeService> getHotLifeServiceList() throws GetNetworkDataException,  ResponseMetaDataException;
 }
