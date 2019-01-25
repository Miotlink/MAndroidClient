package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.domain.entity.AccountDetail;
import com.homepaas.sls.domain.entity.AccountInfo;
import com.homepaas.sls.domain.entity.PayDetail;
import com.homepaas.sls.domain.entity.Recharge;
import com.homepaas.sls.domain.entity.WeChatPaySign;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.param.RechargePaySignParam;

import java.util.List;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public interface AccountNDataSource {

    AccountInfo getAccount(String token)throws GetNetworkDataException,ResponseMetaDataException,ResponseMetaAuthException;

    List<AccountDetail> getAccountDetails(String token)throws GetNetworkDataException,ResponseMetaDataException,ResponseMetaAuthException;

    List<PayDetail> getPayDetailList(String token)throws GetNetworkDataException,ResponseMetaDataException,ResponseMetaAuthException;

    List<Recharge> getRechargeList()throws GetNetworkDataException,ResponseMetaDataException;

    String getAlipaySigh(String token,RechargePaySignParam param)throws GetNetworkDataException,ResponseMetaDataException,ResponseMetaAuthException;

    WeChatPaySign getWeChatPaySign(String token,RechargePaySignParam param)throws GetNetworkDataException,ResponseMetaDataException,ResponseMetaAuthException;
}
