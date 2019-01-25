package com.homepaas.sls.domain.repository;

import com.homepaas.sls.domain.entity.AccountDetail;
import com.homepaas.sls.domain.entity.AccountInfo;
import com.homepaas.sls.domain.entity.BalanceInfo;
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
public interface AccountInfoRepo {

    AccountInfo get()throws GetDataException,AuthException;

    List<AccountDetail> getDetails()throws GetDataException,AuthException;

    List<PayDetail> getPayDetails()throws GetDataException,AuthException;

    List<Recharge> getRecharges()throws GetDataException;

    String getAlipaySigh(RechargePaySignParam param)throws GetDataException,AuthException;

    WeChatPaySign getWeChatPaySign(RechargePaySignParam param) throws GetDataException,AuthException;

    BalanceInfo getAccountBalanceInfo() throws AuthException;
}
