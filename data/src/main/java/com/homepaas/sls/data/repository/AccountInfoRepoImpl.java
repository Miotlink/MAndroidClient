package com.homepaas.sls.data.repository;

import com.homepaas.sls.data.exception.GetNetworkDataException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.exception.ResponseMetaDataException;
import com.homepaas.sls.data.repository.datasource.AccountNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AccountDetail;
import com.homepaas.sls.domain.entity.AccountInfo;
import com.homepaas.sls.domain.entity.BalanceInfo;
import com.homepaas.sls.domain.entity.PayDetail;
import com.homepaas.sls.domain.entity.Recharge;
import com.homepaas.sls.domain.entity.WeChatPaySign;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.param.RechargePaySignParam;
import com.homepaas.sls.domain.repository.AccountInfoRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by CMJ on 2016/6/25.
 */
@Singleton
public class AccountInfoRepoImpl implements AccountInfoRepo {

    @Inject
    AccountNDataSource nDataSource;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    public AccountInfoRepoImpl() {
    }


    /**
     * 获取账户余额
     *
     * @return
     */
    @Override
    public BalanceInfo getAccountBalanceInfo() throws AuthException {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            AccountInfo account = nDataSource.getAccount(token);
            BalanceInfo balanceInfo = new BalanceInfo(account.getEnabledBalance(), null, null);
            return balanceInfo;
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            e.printStackTrace();
        } catch (ResponseMetaDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AccountInfo get() throws GetDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            return nDataSource.getAccount(token);
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }

    @Override
    public List<AccountDetail> getDetails() throws GetDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            return nDataSource.getAccountDetails(token);
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }

    @Override
    public List<PayDetail> getPayDetails() throws GetDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            return nDataSource.getPayDetailList(token);
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }

    @Override
    public List<Recharge> getRecharges() throws GetDataException {
        try {
            return nDataSource.getRechargeList();
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }

    @Override
    public String getAlipaySigh(RechargePaySignParam param) throws GetDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            return nDataSource.getAlipaySigh(token, param);
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }

    @Override
    public WeChatPaySign getWeChatPaySign(RechargePaySignParam param) throws GetDataException, AuthException {
        try {
            String token = personalInfoPDataSource.getToken();
            return nDataSource.getWeChatPaySign(token, param);
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        } catch (GetNetworkDataException e) {
            throw new GetDataException(Constant.NETWORK_ERROR, e);
        } catch (ResponseMetaDataException e) {
            throw new GetDataException(e.getMessage(), e);
        }
    }
}
